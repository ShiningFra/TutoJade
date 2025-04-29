from experta import *
import json
import time
import os
import re

# === PARAMÈTRES ===
HOST = '127.0.0.1'
PORT = 5040
SLEEP_TIME = 5
KNOWLEDGE_FILE = './Knowledge_engine.txt'
SEND_FILE = 'send_by_doctor_expert.txt'
RECEIVE_FILE = 'send_by_patient.txt'

# === OUTILS GÉNÉRAUX ===

def safe_load_knowledge(file_path):
    """Lecture sécurisée du fichier de connaissance"""
    try:
        with open(file_path, 'r') as file:
            content = file.read()
        content = re.sub(r'(\n|end)', '', content)
        return eval(content)  # 🔥 À remplacer par du JSON dans le futur pour plus de sécurité
    except Exception as e:
        print(f"Erreur de chargement de la base de connaissance: {e}")
        return []

def read_file(filepath):
    """Lecture simple de fichier texte"""
    try:
        with open(filepath, 'r') as f:
            return f.read()
    except Exception as e:
        print(f"Erreur de lecture fichier : {e}")
        return None

def write_file(filepath, data):
    """Écriture sécurisée dans un fichier texte"""
    try:
        with open(filepath, 'w') as f:
            f.write(data)
            f.flush()
        time.sleep(SLEEP_TIME)
    except Exception as e:
        print(f"Erreur d'écriture fichier : {e}")

def file_modified(filepath, last_timestamp):
    """Vérifie si un fichier a été modifié"""
    try:
        return os.path.getmtime(filepath) > last_timestamp
    except Exception:
        return False

# === FACTS ===

class Cause(Fact): pass
class Effet(Fact): pass
class Potential_cause(Fact): pass
class Launch_find_causes(Fact): pass
class liste_Effets(Fact): pass
class Launch(Fact): pass

# === BASE DE CONNAISSANCE ===

class KnowledgeEngineExpert(base_connaissances):

    def __init__(self):
        super().__init__()
        self.last_updated_date = None
        self.list_causes_effets = safe_load_knowledge(KNOWLEDGE_FILE)

    @DefFacts()
    def _initial_action(self):
        yield Fact(action="greet")

    def send_and_wait_response(self, message):
        """Envoie une requête et attend une réponse du fichier"""
        payload = json.dumps({"request": message})
        write_file(SEND_FILE, payload)

        response = None
        while response is None:
            if self.last_updated_date is None:
                self.last_updated_date = os.path.getmtime(RECEIVE_FILE)

            if file_modified(RECEIVE_FILE, self.last_updated_date):
                self.last_updated_date = os.path.getmtime(RECEIVE_FILE)
                content = read_file(RECEIVE_FILE)
                if content:
                    try:
                        response_data = json.loads(content)
                        response = response_data.get('response') or response_data.get('pb')
                        print(f"Réponse reçue : {response}")
                    except json.JSONDecodeError:
                        print("Erreur de décodage JSON. Ignoré.")
            time.sleep(1)
        return response

    @Rule(Fact(action="greet"))
    def greetings(self):
        problem = self.send_and_wait_response("Décrivez les symptômes de votre maladie")
        if problem and problem != "refresh":
            self.declare(Launch(action="launch", pb=problem))

    @Rule(Launch(action="launch", pb=MATCH.pb))
    def ask_effect(self, pb):
        """Détecte les effets liés aux mots-clés du problème"""
        detected_effects = []
        pb_lower = pb.lower()

        for cause_effet in self.list_causes_effets:
            for effet, mots_cles in cause_effet.get("mots_cles", {}).items():
                if any(mot in pb_lower for mot in mots_cles):
                    detected_effects.append(effet)

        if detected_effects:
            self.declare(liste_Effets(effets=detected_effects))
        else:
            error_message = json.dumps({"potential_cause": "Nous sommes désolés, nous ne pouvons résoudre votre problème."})
            write_file(SEND_FILE, error_message)

    @Rule(liste_Effets(effets=MATCH.effets))
    def generation_Effets(self, effets):
        for effet in effets:
            self.declare(Effet(effet=effet))
        self.declare(Launch_find_causes(action="go"))

    @Rule(Launch_find_causes(action="go"))
    def find_causes(self):
        """Recherche des causes potentielles"""
        user_effects = [fact["effet"] for fact in self.facts.values() if isinstance(fact, Effet)]

        for idx, item in enumerate(self.list_causes_effets):
            matching_effects = [e for e in user_effects if e in item.get("symptomes", [])]
            if matching_effects:
                self.declare(Potential_cause(position_cause_in_list_causes_effets=idx, list_effets=matching_effects))

    @Rule(Potential_cause(position_cause_in_list_causes_effets=MATCH.idx, list_effets=MATCH.list_effets))
    def verification(self, idx, list_effets):
        """Vérifie la présence de symptômes complémentaires"""
        maladie = self.list_causes_effets[idx]["maladie"]
        solutions = self.list_causes_effets[idx]["solution"]
        prevention = self.list_causes_effets[idx]["prevention"]

        effects_list = "\n".join(f" - {effet}" for effet in list_effets)
        request_msg = f"Potentielle maladie: {maladie}\nPrésentez-vous également les symptômes suivants?\n{effects_list}\n(Oui/Non)"

        val = self.send_and_wait_response(request_msg)

        if val == 'oui':
            response = {
                "maladie": maladie,
                "solution": solutions,
                "prevention": prevention
            }
            write_file(SEND_FILE, json.dumps(response))
            print(f"Cause confirmée : {response}")
        elif val == 'refresh':
            self.reset()
        else:
            info_message = {"answer": "Votre réponse suggère d'envisager d'autres possibilités."}
            write_file(SEND_FILE, json.dumps(info_message))
            print("Redirection vers une autre hypothèse.")

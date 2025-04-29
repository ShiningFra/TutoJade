from experta import *
import json
import time
import re
import os

# Définition de l'adresse IP et du port du dzeestinataire
HOST = '127.0.0.1'
PORT = 5040  
s=5
# Ouvrir le fichier .txt
with open('./Knowledge_engine.txt', 'r') as file:
    content = file.read()

# Suppression des caractères de fin de ligne et de la balise 'end'
content = re.sub(r'(\n|end)', '', content)

# Conversion du contenu en une liste de dictionnaires
list_causes_effets = eval(content)

def lire_fichier_texte(nom_fichier):
    with open(nom_fichier, 'r') as fichier:
        contenu = fichier.read()
    return contenu

def convertir_en_json(contenu_texte):
    try:
        structure_donnees = json.loads(contenu_texte)
        json_data = json.dumps(structure_donnees, indent=4)
        return json_data
    except json.JSONDecodeError as e:
        print("Erreur lors de la conversion en JSON :", e)
        return None

def verifier_modification_fichier(nom_fichier, date_precedente):
    # Récupérer la date de dernière modification du fichier
    try:
        with open(nom_fichier,"r+") as send_by_patient:
            date_modification = os.path.getmtime(nom_fichier)
    except (IOError, PermissionError, FileNotFoundError) as e:
        print("Erreur lors de la récupération de la dernière date de modification du fichier :", e)
        return False    
    
        # Comparer la date de dernière modification actuelle avec la date précédente
    if date_modification > date_precedente:
        #print("Le fichier a été modifié. Date de dernière modification : ", date_modification_formattee)
        print("date_modification: ")
        print(date_modification)
        print("date_precedente")
        print(date_precedente)
        print("true")
        return True
    else:
        #print("Le fichier n'a pas été modifié.")
        return False

class Cause(Fact):
    pass

class Effet(Fact):
    pass

class Potential_cause(Fact):
    pass

class Launch_find_causes(Fact):
    pass

class liste_Effets(Fact):
    pass
#ici, effets implique causes, causes implique solutions
class Launch(Fact):
    pass

class base_connaissances(KnowledgeEngine):
    def __init__(self):
        super().__init__()
        self.last_updated_date = None
    @DefFacts()
    def _initial_action(self):
       yield Fact(action="greet")
    

    def send_and_receive(self, message):
        data = "{'request': "+message+",}"

        try:
            with open("send_by_doctor_expert.txt", "w") as send_by_doctor_expert:
                send_by_doctor_expert.write(data)
                send_by_doctor_expert.flush()
                time.sleep(s)
        except (IOError, PermissionError, FileNotFoundError) as e:
            print("Erreur lors de l'écriture dans le fichier :", e)

        # Attente de la réponse
        response = None
        while response is None:
            # Réception de la réponse
            if self.last_updated_date == None :
                self.last_updated_date = os.path.getmtime("send_by_patient.txt")

            if verifier_modification_fichier("send_by_patient.txt", self.last_updated_date):
                self.last_updated_date = os.path.getmtime("send_by_patient.txt")
                # Lire le contenu du fichier texte
                contenu_texte = lire_fichier_texte("send_by_patient.txt")
                dict_response = eval(contenu_texte)#cette ligne génère une erreur lorsqu'on a une ligne vide dans le fichier
                # Vérification si la réponse est présente dans les données reçues
                if 'response' in dict_response:
                    response = dict_response['response']
                    print(response)
                if 'pb' in dict_response:
                    response = dict_response['pb']
                    print(response)
            else:
                response

        return response
    @Rule(Fact(action="greet"))
    def greetings(self):
        problem=self.send_and_receive("Décrivez les symptômes de votre maladie")
        print("problem", problem)
        if(problem!="refresh"):
            self.declare(Launch(action="launch", pb=problem))

    @Rule(Launch(action="launch", pb=MATCH.pb))
    def ask_effect(self, pb):
        #pb=input("Décrivez votre problème: ")
        pb1 = pb.lower()
        effets=""#effets doit contenir tous les effets recupérés dans list_causes_effets associés aux mots clés présents dans la variable pb
        for elt_list_causes_effets in list_causes_effets:
            for effet,mots_cles_effet in elt_list_causes_effets["mots_cles"].items():
                for mot_cle in mots_cles_effet:
                    if mot_cle in pb1:
                        effets+=effet+","
                        break
        effets = effets.split(",")
        effets = effets[:-1]
        if(len(effets)!=0):
            self.declare(liste_Effets(effets=effets))
        else:
            data = "{'potential_cause': 'Nous sommes désolé mais nous ne pouvons résoudre votre problème',}"
            try:
                with open("send_by_doctor_expert.txt", "w") as send_by_doctor_expert:
                    send_by_doctor_expert.write(data)
                    send_by_doctor_expert.flush()
                    time.sleep(s)
            except (IOError, PermissionError, FileNotFoundError) as e:
                print("Erreur lors de l'écriture dans le fichier :", e)
    
    @Rule(liste_Effets(effets=MATCH.effets))
    def  generation_Effets(self,effets):
        for i in range(len(effets)):
            self.declare(Effet(effet=effets[i]))
        self.declare(Launch_find_causes(action="go"))
    
    @Rule(Potential_cause(position_cause_in_list_causes_effets=MATCH.position_cause_in_list_causes_effets, list_effets=MATCH.list_effets))
    #pour lancer le potentiel cause, il faudrait que j'ai également reçu un fait de type MessagePotential qui sera envoyé à partir de l'interface
    #es ce que le premier potential cause va attendre le premier MessagePotential avant de lancer la règle ci
    #et de façon générale de i eme potential cause?
    def verification(self,position_cause_in_list_causes_effets, list_effets):
        list_effets_str=""
        for i in range(len(list_effets)):
            if i==0:
                list_effets_str+="\t -\t"+list_effets[i]
            else:
                list_effets_str+=",\n \t -\t"+list_effets[i]
        msg_request = "Potientielle maladie: \n"+list_causes_effets[position_cause_in_list_causes_effets]["maladie"]+"\n Cette maladie produit généralement d'autres symptômes. Pour nous rassurer que vous souffrez effectivement de cette maladie, nous avons besoin de savoir si vous notez simultanément les symptômes suivants (repondez par oui ou non si c'est le cas)?:\n "+ list_effets_str
        val = self.send_and_receive(msg_request)

        if (val=='oui'):
            cause="Votre réponse nous permet conclure que la cause suivante est effectivement une cause de votre problème: \n"+list_causes_effets[position_cause_in_list_causes_effets]["maladie"]+" \n Les solutions à envisager sont les suivantes: \n"+list_causes_effets[position_cause_in_list_causes_effets]["solution"]+". \n Comme, méthodes préventives: "+list_causes_effets[position_cause_in_list_causes_effets]['prevention']
            data ="{'maladie': "+cause+",}"
            try:
                with open("send_by_doctor_expert.txt", "w") as send_by_doctor_expert:
                    send_by_doctor_expert.write(data)
                    send_by_doctor_expert.flush()
                    time.sleep(s)
            except (IOError, PermissionError, FileNotFoundError) as e:
                print("Erreur lors de l'écriture dans le fichier :", e)

            print("\n")
            print(cause)
            print("\n")
        elif (val=='refresh'):
            self.reset()
        else:
            print("\n")
            cause="Votre réponse nous fait penser que le problème est ailleurs"
            data = "{'answer': "+cause+",}"
            try:
                with open("send_by_doctor_expert.txt", "w") as send_by_doctor_expert:
                    send_by_doctor_expert.write(data)
                    send_by_doctor_expert.flush()
                    time.sleep(s)
            except (IOError, PermissionError, FileNotFoundError) as e:
                print("Erreur lors de l'écriture dans le fichier :", e)
            print("Votre réponse nous fait penser que le problème est ailleurs, Envisageons une autre possibilité")
            print("\n")

    #for i in range(len(list_causes_effets)):
     #   for j in range(len(list_causes_effets[i]["symptomes"])):
     #           @Rule(Effet(effet=(list_causes_effets[i]["symptomes"])[j]))
     #           def regle1(self):
     #               self.declare(Cause(cause=list_causes_effets[i]["maladie"]))

     #           @Rule(Effet(cause=(list_causes_effets[i]["maladie"])))
      #          def regle2(self):
       #             print(list_causes_effets[i]["solution"])

    #@Rule(Effet(effet='écran noir'))
    #def regle1(self):
    #    self.declare(Cause(cause="problème d'alimentation"))

    #@Rule(Cause(cause="problème d'alimentation"))
    #def regle2(self):
    #    print("Mettez votre machine en charge")
            
    @Rule(Launch_find_causes(action="go"))
    def find_causes(self):
        #nous allons utiliser le chaînage arrière
        #la liste des causes est notre liste de buts, il faut alors l'extraire est notre liste de buts, cherchons toutes les règles dans lesquelles, elles apparaissent comme conséquences
        #ici, vu qu'ils n'y a pas de règles effets implique effets, on ne fera alors qu'un seule itération dans le chaînage arrière
        list_effets_utilisateur = []
        for i in range(len(self.facts)):
            if isinstance(self.facts[i],Effet):
                list_effets_utilisateur.append(self.facts[i]["effet"])
        #list_effets_utilisateur est bien définie
                
        for i in range(len(list_causes_effets)):
            result_verify_cause=[]#on doit allr checker self.facts pour filtrer 
            position=i
            #récupérons tous les effets de list_effets_utilisateur qui sont dans list_causes_effets[i]
            list_elts_in_i=[]
            for elt in list_effets_utilisateur:
                if elt in list_causes_effets[i]["symptomes"]:
                    list_elts_in_i.append(elt)
            result_verify_cause = list(set(list_causes_effets[i]["symptomes"])-set(list_elts_in_i))

            if(list_elts_in_i!=[] and len(list_elts_in_i)!=len(list_causes_effets[i]["symptomes"])):
                self.declare(Potential_cause(position_cause_in_list_causes_effets=position, list_effets=result_verify_cause))
            elif len(list_elts_in_i)==len(list_causes_effets[i]["symptomes"]):
                #on a trouver la cause du problème
                # Send the message to the specified IP address$
                cause="Nous pouvons dire que vous souffrez de la maladie suivante: "+list_causes_effets[i]["maladie"]+'('+list_causes_effets[i]['organe']+')'+" et les solutions que vous pouvez envisager sont: "+list_causes_effets[i]["solution"]+". Comme, méthodes préventives: "+list_causes_effets[i]['prevention']
                data = "{'maladie': "+cause+",}"
                try:
                    with open("send_by_doctor_expert.txt", "w") as send_by_doctor_expert:
                        send_by_doctor_expert.write(data)
                        send_by_doctor_expert.flush()
                        time.sleep(s)
                except (IOError, PermissionError, FileNotFoundError) as e:
                    print("Erreur lors de l'écriture dans le fichier :", e)

                print("\n")
                print(cause)
                print("\n")


engine = base_connaissances()
engine.reset()
#on met un écouteur de messages ici et quand je reçois le premier message,
#je penses que je peux faire en sorte que lorsque je reçois le premier message, qui est la description du problème, qu'on ajoute un fait de type FACT(action="launch", pb=contenu_message)
while True:
    engine.run()#chercher à faire que lorsqu'il n'y a plus de règle activable, que le moteur d'inférence reste quand même en running
    #chercher comment déclarer un fait à partir d'un autre fichier
    engine.reset()
    data = "{'refresh': 'refresh',}"
    try:
        with open("send_by_doctor_expert.txt", "w") as send_by_doctor_expert:
            send_by_doctor_expert.write(data)
            send_by_doctor_expert.flush()
            time.sleep(s)
    except (IOError, PermissionError, FileNotFoundError) as e:
        print("Erreur lors de l'écriture dans le fichier :", e)

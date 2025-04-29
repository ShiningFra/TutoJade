package medecin;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import javax.swing.*;
import java.awt.event.*;

public class Medecin_Container {
    // Fichiers (peu utilisés ici)
    public String fichierLecture = "C:/Users/dell/Desktop/.../send_by_doctor_expert.txt";
    public String fichierEcriture = "C:/Users/dell/Desktop/.../send_by_patient.txt";

    private MedecinInterface medecin_interface;
    private Medecin_Agent medecin_agent;

    public Medecin_Container() {
        // Lancer le container JADE puis l'interface Swing
        startContainer();
        SwingUtilities.invokeLater(() -> {
            medecin_interface = new MedecinInterface();
            initSwingListeners();
            medecin_interface.setVisible(true);
        });
    }

    public static void main(String[] args) {
        new Medecin_Container();
    }

    private void startContainer() {
        try {
            jade.core.Runtime runtime = jade.core.Runtime.instance();
            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            AgentContainer container = runtime.createAgentContainer(profile);
            AgentController controller = container.createNewAgent(
                "Medecin_Agent",
                "medecin.Medecin_Agent",
                new Object[]{this}
            );
            controller.start();
        } catch (ControllerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Erreur lors du démarrage du container JADE: " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initSwingListeners() {
        // Envoi du message quand on clique sur le bouton "Envoyer"
        medecin_interface.getBenvoyer().addActionListener(e -> {
            String message = medecin_interface.getZoneSaisie().getText().trim();
            if (!message.isEmpty()) {
                System.out.println("Je suis le medecin et j'ai écrit: " + message);
                ACLMessage acl = new ACLMessage(ACLMessage.REQUEST);
                acl.setContent(message);
                acl.addReceiver(new AID("Patient_Agent", AID.ISLOCALNAME));
                if (medecin_agent != null) {
                    medecin_agent.send(acl);
                }
                medecin_interface.getListeConversationPatientMedecin().addElement(message);
                medecin_interface.getZoneSaisie().setText("");
            }
        });
    }

    // Méthode appelée par l'agent pour afficher un message reçu
    public void viewMessage(GuiEvent event) {
        if (event.getType() == 1) {
            String msg = event.getParameter(0).toString();
            SwingUtilities.invokeLater(() -> {
                medecin_interface.getListeConversationPatientMedecin().addElement(msg);
            });
            System.out.println("Message reçu: " + msg);
        }
    }

    public void setMedecin_agent(Medecin_Agent agent) {
        this.medecin_agent = agent;
    }

    public Medecin_Agent getMedecin_agent() {
        return medecin_agent;
    }

    // Formatte un message en JSON simple
    public String format_message(String message) {
        return "{\"pb\":\"" + message.replace("\"", "\\\"") + "\"}";
    }
}

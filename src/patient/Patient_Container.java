package patient;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import javax.swing.*;
import java.awt.event.*;

public class Patient_Container {
    private Patient_Agent patientAgent;

    private inscriptionPatientInterface inscriptionInterface;
    private InterfaceConsultation interfaceConsultation;

    private boolean verrou_infoFormulaire = false;

    public Patient_Container() {
        startContainer();

        SwingUtilities.invokeLater(() -> {
            inscriptionInterface = new inscriptionPatientInterface();
            interfaceConsultation = inscriptionInterface.getInterfaceConsultation();

            initSwingListeners();

            inscriptionInterface.setVisible(true);
        });
    }

    public static void main(String[] args) {
        new Patient_Container();
    }

    private void startContainer() {
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            AgentContainer container = runtime.createAgentContainer(profile);
            AgentController controller = container.createNewAgent(
                "Patient_Agent",
                "patient.Patient_Agent",
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
        // Envoi de message de consultation
        interfaceConsultation.getConsultationSendButton().addActionListener(e -> {
            String message = interfaceConsultation.getConsultationTextArea().getText().trim();
            if (!message.isEmpty() && patientAgent != null) {
                System.out.println("Je suis le patient et j'ai écrit: " + message);
                ACLMessage acl = new ACLMessage(ACLMessage.REQUEST);
                acl.setContent(message);
                acl.addReceiver(new AID("Medecin_Agent", AID.ISLOCALNAME));
                patientAgent.send(acl);

                interfaceConsultation.getListeConversationModel().addElement(message);
                interfaceConsultation.getConsultationTextArea().setText("");
            }
        });

        // Gestion du sexe
        inscriptionInterface.getRBfeminin().addActionListener(e -> {
            if (patientAgent != null) patientAgent.setSexe(inscriptionInterface.getRBfeminin().getText());
        });
        inscriptionInterface.getRBmasculin().addActionListener(e -> {
            if (patientAgent != null) patientAgent.setSexe(inscriptionInterface.getRBmasculin().getText());
        });

        // Enregistrer informations patient
        inscriptionInterface.getBenregistrer().addActionListener(e -> {
            if (inscriptionInterface.getTFnom().getText().trim().isEmpty()) {
                inscriptionInterface.getLinformation().setText("Veuillez entrer vos informations");
            } else if (patientAgent != null) {
                patientAgent.setNom(inscriptionInterface.getTFnom().getText().trim());
                patientAgent.setPrenom(inscriptionInterface.getTFprenom().getText().trim());
                patientAgent.setAge(inscriptionInterface.getTFage().getText().trim());
                patientAgent.setAdresse(inscriptionInterface.getTFadresse().getText().trim());
                patientAgent.setTelephone(inscriptionInterface.getTFtelephone().getText().trim());

                System.out.println(
                    "nom: " + patientAgent.getNom() +
                    " prenom: " + patientAgent.getPrenom() +
                    " age: " + patientAgent.getAge() +
                    " sexe: " + patientAgent.getSexe() +
                    " adresse: " + patientAgent.getAdresse() +
                    " telephone: " + patientAgent.getTelephone()
                );

                if (!verrou_infoFormulaire) {
                    inscriptionInterface.getLinformation().setText("Informations enregistrées");
                    verrou_infoFormulaire = true;
                } else {
                    inscriptionInterface.getLinformation().setText("Nouvelles informations enregistrées");
                }
            }
        });

        // Consulter : passer à l'interface consultation
        inscriptionInterface.getBconsulter().addActionListener(e -> {
            if (!verrou_infoFormulaire) {
                inscriptionInterface.getLinformation().setText("Veuillez entrer vos informations");
            } else {
                inscriptionInterface.setVisible(false);
                interfaceConsultation.setVisible(true);
            }
        });

        // Bouton Partir sur consultation
        interfaceConsultation.getConsultationLeaveButton().addActionListener(e -> {
            interfaceConsultation.setVisible(false);
            inscriptionInterface.setVisible(true);
        });
    }

    // Méthode appelée par l'agent pour afficher les messages reçus
    public void viewMessage(GuiEvent event) {
        if (event.getType() == 1) {
            String message = event.getParameter(0).toString();
            SwingUtilities.invokeLater(() -> {
                interfaceConsultation.getListeConversationModel().addElement(message);
            });
            System.out.println("Message reçu: " + message);
        }
    }

    // Junction pour l'agent
    public void setPatientAgent(Patient_Agent agent) {
        this.patientAgent = agent;
    }
    public Patient_Agent getPatientAgent() {
        return patientAgent;
    }
}

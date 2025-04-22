/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medecin;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

/**
 *
 * @author Roddier
 */
public class Medecin_Agent extends GuiAgent {
        private Medecin_Container gui;
        medecinFichier medecin_fichier = new medecinFichier();
        public static boolean fichierModifierMedecin = false;
        
        @Override
        protected void setup(){
            gui=(Medecin_Container) getArguments()[0];
            gui.setMedecin_agent(this);
            System.out.println("Initialisation de l'agent "+this.getAID().getName());
            
            addBehaviour (new CyclicBehaviour(){
                @Override
                public void action() {
                    ACLMessage message=receive();
                    if(message!=null){
                        int i = 0;
                        System.out.println("reception d'un message "+message.getContent());
                        GuiEvent guiEvent = new GuiEvent(this, 1);
                        guiEvent.addParameter(message.getContent());
                        gui.viewMessage(guiEvent);
                        String format_message = gui.format_message(message.getContent());
                        medecin_fichier.write(gui.fichierEcriture, format_message);
                        System.out.println("Le fichier vient d'être modifié");
                        medecinFichier medecinFichier = new medecinFichier();
                        String reponseExperta = medecinFichier.read(gui.fichierLecture);
                        
                        ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                        aclMessage.setContent(reponseExperta);
                        aclMessage.addReceiver(new AID("Patient_Agent", AID.ISLOCALNAME));
                        
                        send(aclMessage);
                        GuiEvent guiEvent2 = new GuiEvent(this, 1);
                        guiEvent2.addParameter(reponseExperta);
                        gui.viewMessage(guiEvent2);
                        fichierModifierMedecin = false;
                    }
                }
            });
        }
        
        @Override
        protected void onGuiEvent(GuiEvent guiEvent){
            throw new UnsupportedOperationException("Not supported yet.");
        }
}

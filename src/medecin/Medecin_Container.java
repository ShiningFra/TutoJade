/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medecin;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 *
 * @author Roddier
 */
public class Medecin_Container extends Application {
    String fichierLecture = "D:\\Workspace\\cabinetmedical"+"\\special\\send_by_doctor_expert.txt";
    String fichierEcriture = "D:\\Workspace\\cabinetmedical"+"\\special\\send_by_patient.txt";
    
    MedecinInterface medecin_interface = new MedecinInterface();
    
    private Medecin_Agent medecin_agent;
    
    public void setMedecin_Agent(Medecin_Agent medecin_agent) {
        this.medecin_agent = medecin_agent;
    }
    
    public Medecin_Agent getMedecin_Agent() {
        return this.medecin_agent;
    }
    
    public static void main (String[] args) {
        launch(Medecin_Container.class);
    }
    
    public void startContainer(){
        try {
            jade.core.Runtime runtime = jade.core.Runtime.instance();
            
            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            AgentContainer Medecin_Container = runtime.createAgentContainer(profile);
            AgentController agentController = Medecin_Container.createNewAgent("Medecin_Agent", "medecin.Medecin_Agent", new Object[]{});
            agentController.start();
        } catch (ControllerException e){
            // TODO Autogenerate catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        medecin_interface.show();
        
        medecin_interface.Benvoyer.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               String message = medecin_interface.getZoneSaisie().getText();
               System.out.println("Je suis le medecin et j'ai ecrit : "+message);
               ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
               aclMessage.setContent(message);
               aclMessage.addReceiver(new AID("Patient_Agent", AID.ISLOCALNAME));
               
               medecin_agent.send(aclMessage);
               
               medecin_interface.listeConversationPatientMedecin.add(message);
               medecin_interface.getZoneSaisie().clear();
           }
        });
    }
    
    public void viewMessage(GuiEvent guiEvent){
        if(guiEvent.getType()==1){
            String message = guiEvent.getParameter(0).toString();
            medecin_interface.listeConversationPatientMedecin.add(message);
            System.out.println(message);
        }
    }
    
    public String format_message(String message){
        String messageFormated = ("{\"pb\":\""+message+"\"}");
        
        return messageFormated;
    }
}

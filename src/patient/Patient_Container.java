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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

public class Patient_Container extends Application {
    public Patient_Agent getPatientAgent() {
        return patientAgent;
    }

    public void setPatientAgent(Patient_Agent patientAgent) {
        this.patientAgent = patientAgent;
    }

    private Patient_Agent patientAgent;

    inscriptionPatientInterface inscriptionInterface = new inscriptionPatientInterface();
    InterfaceConsultation interfaceConsultation = new InterfaceConsultation();

    boolean verrou_infoFormulaire = false;
    static boolean verrou_Consultation = false;


    public void viewMessage(GuiEvent guiEvent){

    }

    public void startContainer(){
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl((false));
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            AgentContainer Patient_container = runtime.createAgentContainer(profile);
            AgentController Patient_agentController = Patient_container.createNewAgent("Patient_Agent", "patient.Patient_Agent", new Object[]{});
            Patient_agentController.start();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }

     public static void main (String[] args){
        launch(Patient_Container.class);
     }

     @Override
    public void start(Stage primaryStage) {
        startContainer();
        inscriptionInterface.show();
        interfaceConsultation.consultationSendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        inscriptionInterface.toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

        });
        inscriptionInterface.Benregistrer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        inscriptionInterface.Bconsulter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        interfaceConsultation.consultationLeaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
     }
}

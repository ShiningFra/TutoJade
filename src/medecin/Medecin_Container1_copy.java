/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medecin;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Medecin_Container1_copy extends Application {
    
    public  Medecin_Agent medecin_agent;
    
     
public static void main (String[] args){
    launch(Medecin_Container1_copy.class); // POur lancer l'interface Graphique , par Appel de la méthode Start definie plus bas. 

}



public void start (Stage stage ) throws Exception {
        startContainer();
        FXMLLoader fxmlLoader = new FXMLLoader(Medecin_Container1_copy.class.getResource("Interface_Medecin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 560);
        stage.setTitle("Page Médecin");
        stage.setScene(scene);
        stage.show();
    
}

public void startContainer(){
try{
    Runtime runtime= Runtime.instance();
    Profile profile=new ProfileImpl(false);
    profile.setParameter(Profile.MAIN_HOST,"localhost");
    AgentContainer Medecin_Container=runtime.createAgentContainer(profile);
   AgentController Medecin_agentController=
            Medecin_Container.createNewAgent("Medecin_Agent","medecin.Medecin_Agent"
                                            ,new Object []{this});

    Medecin_agentController.start();
    }
catch (ControllerException e){
     e.printStackTrace();
        
}
}


    public Medecin_Agent getMedecin_agent() {
        return medecin_agent;
    }

    public  void setMedecin_agent(Medecin_Agent medecin_agent) {
        this.medecin_agent = medecin_agent;
    }


    


}


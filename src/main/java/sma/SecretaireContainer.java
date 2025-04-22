package sma;

import jade.wrapper.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class SecretaireContainer {
    public static void main(String[] args){
        try {
            Runtime runtime = Runtime.instance();
            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "false");
            Profile profile = new ProfileImpl(properties);
            AgentContainer secretaireContainer = runtime.createAgentContainer(profile);
            AgentController agentController = secretaireContainer.createNewAgent("secretaire", "sma.agents.SecretaireAgent", new Object[]{});
            agentController.start();
            secretaireContainer.start();
        } catch (ControllerException e){
            // TODO Autogenerate catch block
            e.printStackTrace();
        }
    }
}

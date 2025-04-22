package sma;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class MedecinContainer {
    public static void main(String[] args){
        try {
            jade.core.Runtime runtime = Runtime.instance();
            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "false");
            Profile profile = new ProfileImpl(properties);
            AgentContainer medecinContainer = runtime.createAgentContainer(profile);
            AgentController agentController = medecinContainer.createNewAgent("medecin", "sma.agents.MedecinAgent", new Object[]{});
            agentController.start();
            medecinContainer.start();
        } catch (ControllerException e){
            // TODO Autogenerate catch block
            e.printStackTrace();
        }
    }
}

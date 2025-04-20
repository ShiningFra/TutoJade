/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tutojade;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

/**
 *
 * @author Roddier
 */
public class agentContainer {
    public static void main (String[] args) throws ControllerException{
        Runtime runtime = Runtime.instance();
        Properties properties = new ExtendedProperties();
        properties.setProperty(Profile.GUI, "true");
        Profile profile = new ProfileImpl(properties);
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController agentController = agentContainer.createNewAgent("agent", "src.agent", new Object[]{});
        agentContainer.start();
    }
}

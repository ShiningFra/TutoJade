/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion_cabinet_medical_final;

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
public class MainContainer {
    public static void main(String[] args){
        try {
            Runtime runtime = Runtime.instance();
            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");
            Profile profile = new ProfileImpl(properties);
            AgentContainer mainContainer = runtime.createMainContainer(profile);
            // juste après avoir obtenu mainContainer
            AgentController rma = mainContainer.getAgent("rma");
            if (rma != null) {
                try {
                    rma.start();
                } catch (ControllerException ce) {
                    ce.printStackTrace();
                }
            } else {
                System.err.println("L'agent RMA n'existe pas : vérifie ton classpath (jadeTools.jar) ou le Profile.GUI");
            }
            
            mainContainer.start();
        } catch (ControllerException e){
            // TODO Autogenerate catch block
            e.printStackTrace();
        }
    }
}

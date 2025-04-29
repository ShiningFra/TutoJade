/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

public class MainContainer {
    
    public static void main( String [] args){
       try{
        Runtime runtime=Runtime.instance();
        Properties properties=new ExtendedProperties();
        properties.setProperty(Profile.GUI,"true");
        Profile profile=new ProfileImpl(properties);
        AgentContainer mainContainer=runtime.createMainContainer(profile);
        mainContainer.start();
        AgentController rma = mainContainer.getAgent("rma");
        rma.start();
    
    } catch (ControllerException e){
           e.printStackTrace();
        
    }
    }
    
}

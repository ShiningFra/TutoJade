/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tutojade;

import jade.wrapper.ControllerException;
import jade.core.Agent;

/**
 *
 * @author Roddier
 */
public class agent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Initialisation de l'agent "+this.getAID().getName());
        
    }
    @Override
    protected void takeDown(){
        System.out.println("Destruction de l'agent");
        
    }
    @Override
    protected void beforeMove() {
        try {
            System.out.println("Avant la migration... du conteneur "+this.getContainerController().getContainerName());
            
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void afterMove() {
        try {
            System.out.println("Après la migration... du conteneur "+this.getContainerController().getContainerName());
            
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }
}

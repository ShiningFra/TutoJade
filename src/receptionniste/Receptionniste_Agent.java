/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receptionniste;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;


public class Receptionniste_Agent extends GuiAgent{
    
   private Receptionniste_Container gui;
   
    @Override
    protected void setup(){
        
        gui=(Receptionniste_Container) getArguments()[0];
        gui.setReceptionnisteAgent(this);
      System.out.println("Initialisation de l'agent"+this.getAID().getName());
    }
    @Override
    protected void takeDown(){
        System.out.println("Destruction agent");
        
    }
    @Override
    protected void beforeMove(){
    }
    @Override
    protected void afterMove(){}

    
    public void onGuiEvent(GuiEvent guiEvent){
        
        if(guiEvent.getType()==1){
        ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
        String livre=guiEvent.getParameter(0).toString();
        aclMessage.setContent(livre);
        aclMessage.addReceiver(new AID("rma",AID.ISLOCALNAME));
        send(aclMessage);
         }
    
}
}

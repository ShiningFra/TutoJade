/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package receptionniste;

import jade.core.AID;
import jade.core.Agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiEvent;
import jade.gui.GuiAgent;
import jade.lang.acl.ACLMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Roddier
 */
public class Receptionniste_Agent extends GuiAgent{
    @Override
    protected void setup() {
        Receptionniste_Container gui = (Receptionniste_Container) getArguments()[0];
        gui.setReceptionnisteAgent(this);
        System.out.println("Initiallisation de l'agent"+this.getAID().getName());

        addBehaviour (new CyclicBehaviour(){
           @Override
           public void action(){
               
               ACLMessage message = receive();
               if(message!=null){
                   String sender = message.getSender().getName();
                   System.out.print("on a reçu le message de quelqu'un "+sender);
                   switch(sender.substring(0, 12)){
                       case ("Patient_Agent"):
                           if(message.getPerformative()==16){
                               String userRecv = message.getContent();
                               JSONParser parser =  new JSONParser();
                               try{
                                   Object obj = parser.parse(userRecv);
                                   JSONObject userJson = (JSONObject) obj;
                                   
                                   User user = new User(1, userJson.get("nom").toString(), Integer.parseInt(userJson.get("age").toString()), userJson.get("sexe").toString());
                                   // gui.user1.add(user);
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                           }
                   }
               }
           }
        });
    }

    @Override
    protected void takeDown(){
        System.out.println("Destruction de l'agent");
    }

    @Override
    protected void afterMove() {
        super.afterMove();
    }

    @Override
    protected void beforeMove(){

    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        if (guiEvent.getType()==1){
            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
            String livre = guiEvent.getParameter(0).toString();
            aclMessage.setContent(livre);
            aclMessage.addReceiver(new AID("rma", AID.ISLOCALNAME));
            send(aclMessage);
        }
    }
}

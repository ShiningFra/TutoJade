/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medecin;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;


public class Medecin_Agent extends GuiAgent{
    
   private Medecin_Container gui; 
    medecinFichier medecin_fichier =new medecinFichier();
   
    
    public static boolean fichierModifierMedecin=false;
    
    @Override
    protected void setup(){
        gui=(Medecin_Container) getArguments()[0];
        gui.setMedecin_agent(this);
        System.out.println("Initialisation de l'agent"+this.getAID().getName());
      
        addBehaviour (new CyclicBehaviour(){
            @Override
            public void action(){
                
                //System.out.println(fichierModifierMedecin);
                MessageTemplate messageTemplate=MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                                                  MessageTemplate.MatchPerformative(ACLMessage.REFUSE));
                  ACLMessage message=receive(messageTemplate);
                  if(message!=null){
                      
                      int i=0;
                  
                      // si message vient du patient, alors 
                      System.out.println("reception d'un message"+message.getContent());
                      GuiEvent guiEvent=new GuiEvent(this,1);
                      guiEvent.addParameter(message.getContent());
                      gui.viewMessage(guiEvent);
                       String format_message=gui.format_message(message.getContent());
                       medecin_fichier.write(gui.fichierEcriture, format_message);
                  
                  System.out.println("le fichier vient d'être modefier"); 
                  medecinFichier medecinFichier =new medecinFichier();
                  String reponseExperta=medecinFichier.read(gui.fichierLecture);
                  System.out.println(reponseExperta);
                  
                  
                      GuiEvent guiEvent2=new GuiEvent(this,1);
                      guiEvent2.addParameter(reponseExperta);
                      gui.viewMessage(guiEvent2);
                  fichierModifierMedecin=false;
                  }
                  
                  
                  
            }
        });
    }
    @Override
    protected void takeDown(){
        System.out.println("Destruction agent");
        
    }
    @Override
    protected void beforeMove(){
        
    try{
        System.out.println("Avant migration du container"
        +this.getContainerController().getContainerName());
        }
    catch(ControllerException e){
        e.printStackTrace();
    }
    }
    @Override
    protected void afterMove(){
    
    try{
        System.out.println("Après migration vers le  container"
                +this.getContainerController().getContainerName());
        }
    catch(ControllerException e){
        e.printStackTrace();
    }   }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }
}
   /*System.out.println("je suis dans le suis onGuiEvent de l'agent");
    System.out.println("je suis dans le if(guiEvent...)");           
    */

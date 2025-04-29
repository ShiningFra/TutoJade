/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patient;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

/**
 *
 * @author dell
 */
public class Patient_Agent extends GuiAgent {
     
   
     private Patient_Container gui;
     private String Nom ;
     private String Prenom;
     private String age;
     private String sexe;
     private String adresse;
     private String telephone;

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
     
   
    @Override
    protected void setup(){
        
        gui=(Patient_Container) getArguments()[0];
        gui.setPatientAgent(this);
        System.out.println("Initialisation "+this.getAID().getName());
    
        
        addBehaviour (new CyclicBehaviour(){
            @Override
            public void action(){
                
                  MessageTemplate messageTemplate=MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                                                  MessageTemplate.MatchPerformative(ACLMessage.REFUSE));
                  ACLMessage message=receive(messageTemplate);
                  if(message!=null){
                      System.out.println("reception d'un message "+message.getContent());
                      GuiEvent guiEvent=new GuiEvent(this,1);
                      guiEvent.addParameter(message.getContent());
                      gui.viewMessage(guiEvent);
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
    }}
    @Override
    protected void afterMove(){
    try{
        System.out.println("Après migration vers le  container"
                +this.getContainerController().getContainerName());
        }
    catch(ControllerException e){
        e.printStackTrace();
    }    }

    @Override
    protected void onGuiEvent(GuiEvent ge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}


    



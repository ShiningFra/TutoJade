package patient;

import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class Patient_Agent extends GuiAgent{
    private Patient_Container gui;

    private String Nom;
    private String Prenom;
    private String age;

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    private String sexe;
    private String adresse;
    private String telephone;

    @Override
    protected void setup(){
        gui = (Patient_Container) getArguments()[0];
        gui.setPatientAgent(this);

        System.out.println("Initialisation de l'agent "+this.getAID().getName());

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action(){
                ACLMessage message = receive();
                if(message != null) {
                    String sender = message.getSender().getName();
                    System.out.println("On a reçu le message de quelqu'un "+sender);
                    switch(sender.substring(0, 6)){
                        case "Recepti":
                            if(message.getPerformative()==4){
                                GuiEvent guiEvent = new GuiEvent(this, 2);
                                guiEvent.addParameter(message.getContent());
                                gui.viewMessage(guiEvent);
                            }
                            break;
                        case "Medecin":
                            if(message.getPerformative()==7){
                                GuiEvent guiEvent = new GuiEvent(this, 1);
                                guiEvent.addParameter(message.getContent());
                                gui.viewMessage(guiEvent);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void takeDown() {
        System.out.println("Destruction de l'agent");
    }
    @Override
    protected void beforeMove() {
        try {
            System.out.println("Avant la migration ... du conteneur "+this.getContainerController().getContainerName());
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void afterMove() {
        try {
            System.out.println("Après la migration ... le conteneur "+this.getContainerController().getContainerName());
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }
}

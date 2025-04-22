package sma.agents;

import jade.core.Agent;
import jade.wrapper.ControllerException;

public class MedecinAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Initialisation de l'agent "+this.getAID().getName());
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

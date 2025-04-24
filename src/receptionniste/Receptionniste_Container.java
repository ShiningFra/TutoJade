package receptionniste;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class Receptionniste_Container extends Application {
    public Receptionniste_Agent getReceptionnisteAgent() {
        return receptionnisteAgent;
    }

    public void setReceptionnisteAgent(Receptionniste_Agent receptionnisteAgent) {
        this.receptionnisteAgent = receptionnisteAgent;
    }

    private Receptionniste_Agent receptionnisteAgent;

    public void startContainer() {
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(false);
            profile.setParameter(profile.MAIN_HOST, "localhost");
            AgentContainer Receptionniste_container = runtime.createAgentContainer(profile);
            AgentController Receptionniste_agentController = Receptionniste_container.createNewAgent("Receptionniste_Agent", "receptionniste.Receptionniste_Agent", new Object[]{});
            Receptionniste_agentController.start();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }
    public static void main (String[] args) {
        launch(Receptionniste_Container.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        startContainer();

        TableView<User> tableView1;
        TableView<User> tableView2;
        ObservableList<User> user1;
        ObservableList<User> user2;

        TableColumn<User,Integer> numeroCol1 = new TableColumn<User,Integer>("Numéro");
        TableColumn<User,Integer> nomCol1 = new TableColumn<User,Integer>("Nom");
        TableColumn<User,Integer> ageCol1 = new TableColumn<User,Integer>("Âge");
        TableColumn<User,Integer> sexeCol1 = new TableColumn<User,Integer>("Sexe");

        numeroCol1.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomCol1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ageCol1.setCellValueFactory(new PropertyValueFactory<>("age"));
        sexeCol1.setCellValueFactory(new PropertyValueFactory<>("sexe"));

        TableColumn<User,Integer> numeroCol2 = new TableColumn<User,Integer>("Numéro");
        TableColumn<User,Integer> nomCol2 = new TableColumn<User,Integer>("Nom");
        TableColumn<User,Integer> ageCol2 = new TableColumn<User,Integer>("Âge");
        TableColumn<User,Integer> sexeCol2 = new TableColumn<User,Integer>("Sexe");
        TableColumn<User,Integer> status= new TableColumn<User,Integer>("Statut");

        numeroCol2.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomCol2.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ageCol2.setCellValueFactory(new PropertyValueFactory<>("age"));
        sexeCol2.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        status.setCellValueFactory(new PropertyValueFactory<>("statut"));

        tableView1 = new TableView<>();
        boolean addAll;
        addAll = tableView1.getColumns().addAll(numeroCol1,nomCol1,ageCol1,sexeCol1);
        tableView1.setPrefWidth(400);

        tableView2 = new TableView<>();
        boolean addAll1;
        addAll1 = tableView2.getColumns().addAll(numeroCol2,nomCol2,ageCol2,sexeCol2);
        tableView2.setPrefWidth(400);

        user1 = FXCollections.observableArrayList(new User(1, "Lea", 20, "F"),
                new User(21,"Line", 21, "F"),
                new User(3, "Nassair", 22, "M"));
        user2 = FXCollections.observableArrayList();

        tableView1.setItems(user1);
        tableView2.setItems(user2);

        Label label1 = new Label("DEMANDE DE CONSULTAITON");
        Label label2 = new Label("ETAT DE CONSULTATION");

        Button buttonAccepte = new Button("Accepté");
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.getChildren().addAll(
                new HBox(label1),
                new HBox(tableView1),
                new HBox(buttonAccepte),
                new HBox(label2),
                new HBox(tableView2)
        );

        Scene scene = new Scene(vBox, 400, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("RECEPTION");
        primaryStage.show();

        buttonAccepte.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User selectedUser = tableView1.getSelectionModel().getSelectedItem();
                if (selectedUser!=null){
                    tableView1.getItems().remove(selectedUser);
                    selectedUser.setStatus("En attente");
                    user2.add(selectedUser);
                    tableView2.setItems(user2);
                    ACLMessage aclMessage = new ACLMessage(ACLMessage.CONFIRM);
                    aclMessage.setContent("Votre consultation a été acceptee");
                    aclMessage.addReceiver(new AID("Patient_Agent", AID.ISLOCALNAME));
                    receptionnisteAgent.send(aclMessage);
                }
            }
        });
    }
}

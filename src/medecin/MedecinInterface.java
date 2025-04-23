package medecin;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Interface graphique pour l'application Medecin.
 * Conçue pour être instanciée et affichée depuis un container JADE
 */
public class MedecinInterface extends Application {
    private Stage window;
    private final ObservableList<User> users = FXCollections.observableArrayList();
    // Liste des messages pour le chat (exposée pour add/remove)
    public ObservableList<String> listeConversationPatientMedecin;

    // Composants UI accessibles depuis le container
    private ListView<String> conversationListView;
    public TextArea zoneSaisie;
    public Button Benvoyer;

    public MedecinInterface() {
        // Construction manuelle de l'interface pour permettre new MedecinInterface().show();
        window = new Stage();
        window.setTitle("Cabinet Médical");
        initComponents();
        BorderPane root = createContent();
        window.setScene(new Scene(root, 800, 600));
    }

    @Override
    public void start(Stage primaryStage) {
        // Pour l'exécution autonome via Application.launch()
        window = primaryStage;
        window.setTitle("Cabinet Médical");
        initComponents();
        BorderPane root = createContent();
        window.setScene(new Scene(root, 800, 600));
        window.show();
    }

    /** Affiche l'UI construite en mode container */
    
    public void show() {
        if (window != null) {
            window.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private TableView<User> userTable;
    private TabPane tabPane;

    private void initComponents() {
        // TableView Utilisateurs
        userTable = new TableView<>();
        TableColumn<User, Integer> colNum = new TableColumn<>("N°");
        colNum.setCellValueFactory(new PropertyValueFactory<>("numero"));
        TableColumn<User, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TableColumn<User, Integer> colAge = new TableColumn<>("Age");
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        TableColumn<User, String> colSexe = new TableColumn<>("Sexe");
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        TableColumn<User, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(param -> new ButtonCell());
        userTable.getColumns().addAll(colNum, colNom, colAge, colSexe, colAction);
        userTable.setItems(users);

        // Chat
        listeConversationPatientMedecin = FXCollections.observableArrayList();
        conversationListView = new ListView<>(listeConversationPatientMedecin);
        zoneSaisie = new TextArea();
        zoneSaisie.setPromptText("Tapez votre message...");
        zoneSaisie.setPrefRowCount(3);
        Benvoyer = new Button("Envoyer");

        // Onglets
        tabPane = new TabPane();
        Tab tabUsers = new Tab("Utilisateurs", userTable);
        tabUsers.setClosable(false);

        HBox inputBox = new HBox(10, zoneSaisie, Benvoyer);
        HBox.setHgrow(zoneSaisie, Priority.ALWAYS);
        VBox chatBox = new VBox(10, conversationListView, inputBox);
        VBox.setVgrow(conversationListView, Priority.ALWAYS);
        Tab tabChat = new Tab("Chat", chatBox);
        tabChat.setClosable(false);

        tabPane.getTabs().addAll(tabUsers, tabChat);
    }

    private BorderPane createContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setCenter(tabPane);
        return root;
    }

    /** Accès à la liste des utilisateurs pour initialisation */
    public ObservableList<User> getUsers() {
        return users;
    }

    /** Accès au champ de saisie */
    public TextArea getZoneSaisie() {
        return zoneSaisie;
    }
}

/**
 * Cellule personnalisée avec boutons 'C' (consulter) et 'R' (répondre).
 */
class ButtonCell extends TableCell<User, Void> {
    private final Button btnC = new Button("C");
    private final Button btnR = new Button("R");
    private final HBox pane = new HBox(5, btnC, btnR);

    public ButtonCell() {
        btnC.setOnAction((ActionEvent event) -> {
            User user = getTableView().getItems().get(getIndex());
            System.out.println("Consulter utilisateur n°" + user.getNumero());
        });
        btnR.setOnAction((ActionEvent event) -> {
            User user = getTableView().getItems().get(getIndex());
            System.out.println("Répondre à utilisateur n°" + user.getNumero());
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : pane);
    }
}

package patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class InterfaceConsultation extends Stage {
    TabPane tabPane = new TabPane();
    Tab consultationTab = new Tab("Consultation");
    Tab prescriptionTab = new Tab ("Prescription(s)");
    AnchorPane consultationContent = new AnchorPane();
    GridPane gridPane = new GridPane();
    ObservableList<String> listeConversationPatientMedecin = FXCollections.observableArrayList();
    ListView<String> listViewMessages = new ListView<>(listeConversationPatientMedecin);
    TextArea consultationTextArea = new TextArea();
    Button consultationLeaveButton = new Button("Partir");
    Button consultationSendButton = new Button("Envoyer");

    Label consultationTitleLabel = new Label("*** Consultation en cours ***");

    AnchorPane prescriptionContent = new AnchorPane();
    Label diagnosisLabel = new Label("Diagnostic : ");
    Label prescriptionLabel = new Label("Ordonnance :");
    TextFlow diagnostisTextFlow = new TextFlow();
    TextFlow prescriptionTextFlow = new TextFlow();
    Button prescriptionLeaveButton = new Button("Partir");

    public TextArea getConsultationTextArea() {
        return consultationTextArea;
    }

    public void setConsultationTextArea(TextArea consultationTextArea) {
        this.consultationTextArea = consultationTextArea;
    }

    public InterfaceConsultation() {

    }
}

package patient;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class inscriptionPatientInterface extends Stage {
    InterfaceConsultation interfaceConsltation = new InterfaceConsultation();

    AnchorPane anchorPane = new AnchorPane();
    Label Ltitre = new Label();
    Pane Pinscription = new Pane();
    Label Lnom = new Label();
    TextField TFnom = new TextField();
    Label lprenom = new Label();
    TextField TFprenom = new TextField();
    Label Lage = new Label();
    TextField TFage = new TextField();
    Label Lsexe = new Label();
    RadioButton RBfeminin = new RadioButton();
    RadioButton RBmasculin = new RadioButton();
    ToggleGroup toggleGroup = new ToggleGroup();
    Label Ladresse = new Label();
    TextField TFadresse = new TextField();
    Label Ltelephone = new Label();
    TextField TFtelephone = new TextField();
    Button Benregistrer = new Button("Enregistrer");
    Button Bconsulter = new Button("Consulter");
    static Label Linformation = new Label();
    public InterfaceConsultation getInterfaceConsltation(){
        return this.interfaceConsltation;
    }
    public inscriptionPatientInterface (){

    }
}

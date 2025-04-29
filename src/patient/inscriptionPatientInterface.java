package patient;

import javax.swing.*;
import java.awt.*;

public class inscriptionPatientInterface extends JFrame {
    private InterfaceConsultation interfaceConsultation = new InterfaceConsultation();

    // Conteneurs
    private JPanel anchorPane = new JPanel(null);
    private JPanel Pinscription = new JPanel(null);

    // Champs de saisie
    private JLabel Ltitre = new JLabel("Mes Informations", SwingConstants.CENTER);
    private JLabel Lnom = new JLabel("Nom(s)", SwingConstants.CENTER);
    private JTextField TFnom = new JTextField();
    private JLabel Lprenom = new JLabel("Prénom(s)", SwingConstants.CENTER);
    private JTextField TFprenom = new JTextField();
    private JLabel Lage = new JLabel("Âge", SwingConstants.CENTER);
    private JTextField TFage = new JTextField();
    private JLabel Lsexe = new JLabel("Sexe", SwingConstants.CENTER);
    private JRadioButton RBfeminin = new JRadioButton("F");
    private JRadioButton RBmasculin = new JRadioButton("M");
    private ButtonGroup toggleGroup = new ButtonGroup();
    private JLabel Ladresse = new JLabel("Adresse", SwingConstants.CENTER);
    private JTextField TFadresse = new JTextField();
    private JLabel Ltelephone = new JLabel("Téléphone", SwingConstants.CENTER);
    private JTextField TFtelephone = new JTextField();

    private JButton Benregistrer = new JButton("Enregistrer");
    private JButton Bconsulter = new JButton("Consulter");
    private JLabel Linformation = new JLabel(".........", SwingConstants.CENTER);

    public inscriptionPatientInterface() {
        setTitle("Patient");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 450);
        setLocationRelativeTo(null);

        // Configure anchorPane
        setContentPane(anchorPane);

        // Configure Ltitre
        Ltitre.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 15));
        Ltitre.setBounds(150, 10, 200, 26);

        // Configure Pinscription panel
        Pinscription.setBackground(Color.WHITE);
        Pinscription.setBounds(0, 38, 386, 400);
        // Ajout des RadioButtons au groupe
        toggleGroup.add(RBfeminin);
        toggleGroup.add(RBmasculin);

        // Positionnement des composants
        Lnom.setBounds(37, 38, 59, 26);
        TFnom.setBounds(107, 38, 240, 25);

        Lprenom.setBounds(37, 78, 61, 26);
        TFprenom.setBounds(107, 80, 240, 25);

        Lage.setBounds(37, 119, 59, 26);
        TFage.setBounds(107, 119, 75, 25);

        Lsexe.setBounds(37, 156, 59, 26);
        RBfeminin.setBounds(108, 157, 32, 25);
        RBmasculin.setBounds(159, 157, 32, 25);

        Ladresse.setBounds(37, 195, 59, 26);
        TFadresse.setBounds(107, 195, 240, 25);

        Ltelephone.setBounds(36, 235, 61, 26);
        TFtelephone.setBounds(107, 235, 239, 25);

        Benregistrer.setBounds(130, 270, 147, 31);
        Bconsulter.setBounds(130, 320, 147, 31);
        Linformation.setBounds(100, 370, 300, 26);

        // Styles des boutons
        Benregistrer.setBackground(Color.GREEN);
        Benregistrer.setForeground(Color.WHITE);
        Bconsulter.setBackground(Color.GREEN);
        Bconsulter.setForeground(Color.WHITE);

        // Ajout aux panels
        Pinscription.add(Lnom);
        Pinscription.add(TFnom);
        Pinscription.add(Lprenom);
        Pinscription.add(TFprenom);
        Pinscription.add(Lage);
        Pinscription.add(TFage);
        Pinscription.add(Lsexe);
        Pinscription.add(RBfeminin);
        Pinscription.add(RBmasculin);
        Pinscription.add(Ladresse);
        Pinscription.add(TFadresse);
        Pinscription.add(Ltelephone);
        Pinscription.add(TFtelephone);
        Pinscription.add(Benregistrer);
        Pinscription.add(Bconsulter);
        Pinscription.add(Linformation);

        anchorPane.add(Ltitre);
        anchorPane.add(Pinscription);

        setVisible(true);
    }

    // Getters pour accès extérieur
    public InterfaceConsultation getInterfaceConsultation() { return interfaceConsultation; }
    public JTextField getTFnom() { return TFnom; }
    public JTextField getTFprenom() { return TFprenom; }
    public JTextField getTFage() { return TFage; }
    public JRadioButton getRBfeminin() { return RBfeminin; }
    public JRadioButton getRBmasculin() { return RBmasculin; }
    public JTextField getTFadresse() { return TFadresse; }
    public JTextField getTFtelephone() { return TFtelephone; }
    public JButton getBenregistrer() { return Benregistrer; }
    public JButton getBconsulter() { return Bconsulter; }
    public JLabel getLinformation() { return Linformation; }
}

package patient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InterfaceConsultation extends JFrame {
    // Composants Swing équivalents
    private JTabbedPane tabPane = new JTabbedPane();
    private JPanel consultationContent = new JPanel(new BorderLayout());
    private JPanel prescriptionContent = new JPanel(new GridBagLayout());
    private JPanel gridPane = new JPanel(new BorderLayout());

    private DefaultListModel<String> listeConversationPatientMedecin = new DefaultListModel<>();
    private JList<String> listViewMessages = new JList<>(listeConversationPatientMedecin);

    private JTextArea consultationTextArea = new JTextArea(3, 30);
    private JButton consultationLeaveButton = new JButton("Partir");
    private JButton consultationSendButton = new JButton("Envoyer");
    private JLabel consultationTitleLabel = new JLabel("*** Consultation en cours ***", SwingConstants.CENTER);

    private JLabel diagnosisLabel = new JLabel("Diagnostic : ");
    private JLabel prescriptionLabel = new JLabel("Ordonnance : ");
    private JTextArea diagnosisTextFlow = new JTextArea(5, 30);
    private JTextArea prescriptionTextFlow = new JTextArea(5, 30);
    private JButton prescriptionLeaveButton = new JButton("Partir");

    public InterfaceConsultation() {
        // Fenêtre principale
        setTitle("Patient");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 430);
        setLocationRelativeTo(null);

        // --- Onglet Consultation ---
        consultationTitleLabel.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 15));
        consultationContent.add(consultationTitleLabel, BorderLayout.NORTH);

        gridPane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        gridPane.add(new JScrollPane(listViewMessages), BorderLayout.CENTER);
        consultationContent.add(gridPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JScrollPane inputScroll = new JScrollPane(consultationTextArea);
        inputScroll.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        inputPanel.add(inputScroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.add(consultationSendButton);
        buttonPanel.add(consultationLeaveButton);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        consultationContent.add(inputPanel, BorderLayout.SOUTH);
        tabPane.addTab("Consultation", consultationContent);

        // --- Onglet Prescription(s) ---
        diagnosisTextFlow.setEditable(false);
        prescriptionTextFlow.setEditable(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        prescriptionContent.add(diagnosisLabel, gbc);
        gbc.gridx = 1;
        prescriptionContent.add(new JScrollPane(diagnosisTextFlow), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        prescriptionContent.add(prescriptionLabel, gbc);
        gbc.gridx = 1;
        prescriptionContent.add(new JScrollPane(prescriptionTextFlow), gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        prescriptionContent.add(prescriptionLeaveButton, gbc);
        tabPane.addTab("Prescription(s)", prescriptionContent);

        // Ajout du JTabbedPane
        getContentPane().add(tabPane);
        setVisible(true);
    }

    // Getters pour manipuler l'interface
    public JTextArea getConsultationTextArea() { return consultationTextArea; }
    public DefaultListModel<String> getListeConversationModel() { return listeConversationPatientMedecin; }
    public JList<String> getListViewMessages() { return listViewMessages; }
    public JButton getConsultationSendButton() { return consultationSendButton; }
    public JButton getConsultationLeaveButton() { return consultationLeaveButton; }
    public JButton getPrescriptionLeaveButton() { return prescriptionLeaveButton; }
    public JTextArea getDiagnosisTextFlow() { return diagnosisTextFlow; }
    public JTextArea getPrescriptionTextFlow() { return prescriptionTextFlow; }
}

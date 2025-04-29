package medecin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MedecinInterface extends JFrame {
    // Conversation list
    private DefaultListModel<String> listeConversationPatientMedecin = new DefaultListModel<>();
    private JList<String> listViewMessages = new JList<>(listeConversationPatientMedecin);

    // Titles
    private JLabel TTitre = new JLabel("Bureau Du Méd\u00e9cin", SwingConstants.CENTER);
    private JLabel TConsultation = new JLabel("Liste Consultations", SwingConstants.LEFT);

    // Table
    private JTable tableView;
    private DefaultTableModel tableModel;
    private static final String[] COLUMN_NAMES = {"Numero", "Nom", "Age", "Sexe", "Action"};

    // Tabs and their components
    private JTabbedPane SecondPane = new JTabbedPane();

    // Discussion Tab
    private JPanel Pdiscussion = new JPanel(new BorderLayout(5, 5));
    private JPanel zoneReponse = new JPanel(new BorderLayout());
    private JTextArea zoneSaisie = new JTextArea(3, 50);

    public JTextArea getZoneSaisie() {
        return zoneSaisie;
    }

    public void setZoneSaisie(JTextArea zoneSaisie) {
        this.zoneSaisie = zoneSaisie;
    }

    public JButton getBenvoyer() {
        return Benvoyer;
    }

    public void setBenvoyer(JButton Benvoyer) {
        this.Benvoyer = Benvoyer;
    }
    private JButton Benvoyer = new JButton("Envoyer");

    // Diagnostic-Prescription Tab
    private JPanel PDiagPresc = new JPanel(new GridBagLayout());
    private JTextArea zoneSaisieDiag = new JTextArea(5, 20);
    private JTextArea zoneSaisiePresc = new JTextArea(5, 20);
    private JLabel LDiagnostic = new JLabel("Diagnostic");
    private JLabel LPrescription = new JLabel("Prescription");
    private JButton BenvoyerDiagPresc = new JButton("Envoyer");

    // Infos-Patients Tab
    private JPanel PInfoPatient = new JPanel(new GridLayout(4, 1, 5, 5));
    private JLabel LInfoPatientnom = new JLabel("Nom: ");
    private JLabel LInfoPatientprenom = new JLabel("Pr\u00e9nom: ");
    private JLabel LInfoPatientsexe = new JLabel("Sexe: ");
    private JLabel LInfoPatientIdConsult = new JLabel("IdConsultation: ");

    // Infos-Consultations Tab
    private JPanel PInfoConsult = new JPanel(new GridBagLayout());
    private JLabel LInfoConsultNomPrenom = new JLabel("Nom Pr\u00e9nom: ");
    private JTextField TFInfoConsultNomPrenom = new JTextField(20);
    private JLabel LInfoConsultAge = new JLabel("Age: ");
    private JTextField TFInfoConsultAge = new JTextField(5);
    private JLabel LInfoConsultSexe = new JLabel("Sexe: ");
    private JTextField TFInfoConsultSexe = new JTextField(5);
    private JLabel LInfoConsultDate = new JLabel("Date: ");
    private JTextField TFInfoConsultDate = new JTextField(10);
    private JLabel LInfoConsultDiagnostic = new JLabel("Diagnostic: ");
    private JTextArea TFInfoConsultDiagnostic = new JTextArea(3, 20);
    private JLabel LInfoConsultPrescription = new JLabel("Prescription: ");
    private JTextArea TFInfoConsultPrescription = new JTextArea(3, 20);
    private JLabel LInfoConsultId = new JLabel("IdConsultation: ");
    private JTextField TFInfoConsultId = new JTextField(10);
    private JLabel LInfoConsultRendezVous = new JLabel("Rendez-Vous: ");
    private JTextField TFInfoConsultRendezVous = new JTextField(10);

    // Bottom Buttons
    private JButton Bterminé = new JButton("Terminé");
    private JButton BrendezVous = new JButton("Rendez-Vous");

    public MedecinInterface() {
        setTitle("Medecin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(620, 620);
        setLocationRelativeTo(null);

        // Top Panel with Titles
        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        TTitre.setFont(new Font("Arial", Font.BOLD, 22));
        topPanel.add(TTitre, BorderLayout.NORTH);
        TConsultation.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(TConsultation, BorderLayout.SOUTH);

        // Table Setup
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // only "Action" column editable
            }
        };
        fillSampleData();
        tableView = new JTable(tableModel);
        // Add custom renderer/editor for action buttons
        tableView.getColumn("Action").setCellRenderer(new ActionRenderer());
        tableView.getColumn("Action").setCellEditor(new ActionEditor(tableView));
        JScrollPane tableScroll = new JScrollPane(tableView);
        tableScroll.setPreferredSize(new Dimension(580, 140));

        // Build Discussion Tab
        zoneReponse.add(new JScrollPane(listViewMessages), BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new BorderLayout(5,5));
        inputPanel.add(zoneSaisie, BorderLayout.CENTER);
        inputPanel.add(Benvoyer, BorderLayout.EAST);
        Pdiscussion.add(zoneReponse, BorderLayout.CENTER);
        Pdiscussion.add(inputPanel, BorderLayout.SOUTH);
        SecondPane.addTab("Discussion", Pdiscussion);

        // Build Diagnostic-Prescription Tab
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0; PDiagPresc.add(LDiagnostic, gbc);
        gbc.gridx = 1; gbc.gridy = 0; PDiagPresc.add(LPrescription, gbc);
        gbc.gridx = 0; gbc.gridy = 1; PDiagPresc.add(new JScrollPane(zoneSaisieDiag), gbc);
        gbc.gridx = 1; gbc.gridy = 1; PDiagPresc.add(new JScrollPane(zoneSaisiePresc), gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        PDiagPresc.add(BenvoyerDiagPresc, gbc);
        SecondPane.addTab("DIAGNOSTIC-PRESCRIPTION", PDiagPresc);

        // Build Infos-Patients Tab
        PInfoPatient.setBorder(new EmptyBorder(10,10,10,10));
        PInfoPatient.add(LInfoPatientnom);
        PInfoPatient.add(LInfoPatientprenom);
        PInfoPatient.add(LInfoPatientsexe);
        PInfoPatient.add(LInfoPatientIdConsult);
        SecondPane.addTab("INFOS-PATIENTS", PInfoPatient);

        // Build Infos-Consultations Tab
        PInfoConsult.setBorder(new EmptyBorder(10,10,10,10));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx=0; gbc.gridy=0; PInfoConsult.add(LInfoConsultNomPrenom, gbc);
        gbc.gridx=1; gbc.gridy=0; PInfoConsult.add(TFInfoConsultNomPrenom, gbc);
        gbc.gridx=0; gbc.gridy=1; PInfoConsult.add(LInfoConsultAge, gbc);
        gbc.gridx=1; gbc.gridy=1; PInfoConsult.add(TFInfoConsultAge, gbc);
        gbc.gridx=0; gbc.gridy=2; PInfoConsult.add(LInfoConsultSexe, gbc);
        gbc.gridx=1; gbc.gridy=2; PInfoConsult.add(TFInfoConsultSexe, gbc);
        gbc.gridx=0; gbc.gridy=3; PInfoConsult.add(LInfoConsultDate, gbc);
        gbc.gridx=1; gbc.gridy=3; PInfoConsult.add(TFInfoConsultDate, gbc);
        gbc.gridx=0; gbc.gridy=4; PInfoConsult.add(LInfoConsultDiagnostic, gbc);
        gbc.gridx=1; gbc.gridy=4; PInfoConsult.add(new JScrollPane(TFInfoConsultDiagnostic), gbc);
        gbc.gridx=0; gbc.gridy=5; PInfoConsult.add(LInfoConsultPrescription, gbc);
        gbc.gridx=1; gbc.gridy=5; PInfoConsult.add(new JScrollPane(TFInfoConsultPrescription), gbc);
        gbc.gridx=0; gbc.gridy=6; PInfoConsult.add(LInfoConsultId, gbc);
        gbc.gridx=1; gbc.gridy=6; PInfoConsult.add(TFInfoConsultId, gbc);
        gbc.gridx=0; gbc.gridy=7; PInfoConsult.add(LInfoConsultRendezVous, gbc);
        gbc.gridx=1; gbc.gridy=7; PInfoConsult.add(TFInfoConsultRendezVous, gbc);
        SecondPane.addTab("INFOS-CONSULTATIONS", PInfoConsult);

        // Layout main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(SecondPane, BorderLayout.SOUTH);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(Bterminé);
        bottomPanel.add(BrendezVous);

        // Add to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public DefaultListModel<String> getListeConversationPatientMedecin() {
        return listeConversationPatientMedecin;
    }

    public void setListeConversationPatientMedecin(DefaultListModel<String> listeConversationPatientMedecin) {
        this.listeConversationPatientMedecin = listeConversationPatientMedecin;
    }

    // Fill table with sample data
    private void fillSampleData() {
        java.util.List<User> users = Arrays.asList(
            new User(1, "Lea", 20, "F"),
            new User(2, "Line", 21, "F"),
            new User(3, "Nassair", 22, "M")
        );
        for (User u : users) {
            tableModel.addRow(new Object[]{u.getNumero(), u.getNom(), u.getAge(), u.getSexe(), ""});
        }
    }

    // User model unchanged
    public static class User {
        private final int numero;
        private final String nom;
        private final int age;
        private final String sexe;

        public User(int numero, String nom, int age, String sexe) {
            this.numero = numero;
            this.nom = nom;
            this.age = age;
            this.sexe = sexe;
        }
        public int getNumero() { return numero; }
        public String getNom() { return nom; }
        public int getAge() { return age; }
        public String getSexe() { return sexe; }
    }

    // Renderer for action column
    private class ActionRenderer extends JPanel implements TableCellRenderer {
        public ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5,0));
            add(new JButton("C"));
            add(new JButton("R"));
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    // Editor for action column
    private class ActionEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton cBtn, rBtn;

        public ActionEditor(JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5,0));
            cBtn = new JButton("C");
            rBtn = new JButton("R");
            panel.add(cBtn);
            panel.add(rBtn);

            cBtn.addActionListener(e -> {
                int row = table.getEditingRow();
                int numero = (int) table.getValueAt(row, 0);
                System.out.println("C clicked on user " + numero);
            });
            rBtn.addActionListener(e -> {
                int row = table.getEditingRow();
                int numero = (int) table.getValueAt(row, 0);
                System.out.println("R clicked on user " + numero);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MedecinInterface::new);
    }
}

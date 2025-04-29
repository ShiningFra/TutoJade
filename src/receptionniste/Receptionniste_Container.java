package receptionniste;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class Receptionniste_Container {
    private Receptionniste_Agent receptionnisteAgent;

    public void setReceptionnisteAgent(Receptionniste_Agent agent) {
        this.receptionnisteAgent = agent;
    }

    public Receptionniste_Agent getReceptionnisteAgent() {
        return receptionnisteAgent;
    }

    private JTable tableView1, tableView2;
    private DefaultTableModel model1, model2;
    private JButton buttonAccepte;

    public Receptionniste_Container() {
        startContainer();
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    public static void main(String[] args) {
        new Receptionniste_Container();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("RECEPTION");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 650);
        frame.setLocationRelativeTo(null);

        // Modèles et Tableaux
        String[] cols1 = {"Numéro", "Nom", "Age", "Sexe"};
        model1 = new DefaultTableModel(cols1, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tableView1 = new JTable(model1);

        String[] cols2 = {"Numéro", "Nom", "Age", "Sexe", "Statut"};
        model2 = new DefaultTableModel(cols2, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tableView2 = new JTable(model2);

        // Données de démonstration
        Object[][] sample = {
            {1, "Lea", 20, "F"},
            {2, "Line", 21, "F"},
            {3, "Nassair", 22, "M"}
        };
        for (Object[] row : sample) {
            model1.addRow(row);
        }

        JScrollPane scroll1 = new JScrollPane(tableView1);
        scroll1.setPreferredSize(new Dimension(380, 150));
        JScrollPane scroll2 = new JScrollPane(tableView2);
        scroll2.setPreferredSize(new Dimension(380, 150));

        // Bouton d'acceptation
        buttonAccepte = new JButton("Accepté");

        // Layout principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(new JLabel("DEMANDE DE CONSULTATION"));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(scroll1);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonAccepte);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(new JLabel("ETAT DE CONSULTATION"));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(scroll2);

        // Gestionnaire d'événements
        buttonAccepte.addActionListener(e -> {
            int row = tableView1.getSelectedRow();
            if (row != -1) {
                Object[] rowData = new Object[4];
                for (int i = 0; i < 4; i++) {
                    rowData[i] = model1.getValueAt(row, i);
                }
                model1.removeRow(row);
                Object[] rowData2 = Arrays.copyOf(rowData, 5);
                rowData2[4] = "En attente";
                model2.addRow(rowData2);

                // Envoi d'un événement JADE
                GuiEvent guiEvent = new GuiEvent(this, 1);
                guiEvent.addParameter("Consultation acceptée pour : " + rowData[1]);
                if (receptionnisteAgent != null) {
                    receptionnisteAgent.onGuiEvent(guiEvent);
                }
            }
        });

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    private void startContainer() {
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            AgentContainer container = runtime.createAgentContainer(profile);
            AgentController controller = container.createNewAgent(
                "Receptionniste_Agent",
                "receptionniste.Receptionniste_Agent",
                new Object[]{this}
            );
            controller.start();
        } catch (ControllerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Erreur démarrage container JADE: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

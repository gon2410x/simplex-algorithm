package gui;

import modelo.ILinearModel;
import modelo.IModel;
import modelo.LinearModel;
import twoPhaseMethod.TwoPhaseMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EnterData extends JFrame {

    private final JFrame previousFrame; // Reference to the previous screen.

    // Components for the Objective Function
    private JComboBox<String> objetivoComboBox;
    private JTextField[] objXField;

    // Components for Restrictions
    private JTextField[][] constraintFields; // [num_restriccion][coef_x1]
    private JComboBox<String>[] inequalityComboBoxes; // [num_restriccion]

    private final int NUM_RESTRICTIONS; //Number of constraints
    private final int NUM_VARIABLES; //Number of variables


    public EnterData(int variables, int restrictions, JFrame previousFrame) {
        setTitle("Linear Programming Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 600); // Initial size
        setLocationRelativeTo(null); // Center the window

        this.NUM_VARIABLES = variables;
        this.NUM_RESTRICTIONS = restrictions;
        this.previousFrame = previousFrame; // Save the reference to the previous screen


        // Main panel with GridBagLayout for precise control
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5); // Padding between components

        // --- General Objective Section and Edit Problem Button ---
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcTop = new GridBagConstraints();
        gbcTop.insets = new Insets(5, 5, 5, 5);

        // Objetivo:
        gbcTop.gridx = 0;
        gbcTop.gridy = 0;
        gbcTop.anchor = GridBagConstraints.WEST;
        topPanel.add(new JLabel("Objetivo:"), gbcTop);

        objetivoComboBox = new JComboBox<>(new String[]{"MAXIMIZE", "MINIMIZE"});
        gbcTop.gridx = 0;
        gbcTop.gridy = 1;
        gbcTop.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(objetivoComboBox, gbcTop);

        // "Back to Home Screen" button
        JButton backButton = new JButton("Restart");
        backButton.setBackground(new Color(66, 139, 202)); // Blue
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  dispose(); // Close this screen (ModelDetailsScreen)
               if (previousFrame != null) {
                  previousFrame.setVisible(true); // Displays the previous screen
               }
            }
        });

        gbcTop.gridx = 1;
        gbcTop.gridy = 0;
        gbcTop.gridheight = 2;
        gbcTop.anchor = GridBagConstraints.NORTHEAST;
        //gbcTop.weightx = 1.0; // Push the button to the right
        topPanel.add(backButton, gbcTop);


        // Add the top panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // It occupies the entire available width
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(topPanel, gbc);


        // --- Objective Function Section ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("<html><br><b>Objective Function:</b></html>"), gbc);

        JPanel objFuncPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //FlowBagLayout to align

        objXField = new JTextField[NUM_VARIABLES];

        for (int i = 0; i < NUM_VARIABLES; i++) {
            objXField[i] = new JTextField(5);
            objFuncPanel.add(objXField[i]);
            objFuncPanel.add(new JLabel(i < NUM_VARIABLES - 1 ? "X"+(i+1)+" + " : "X"+(i+1)+" "));
        }


        gbc.gridy = 2;
        mainPanel.add(objFuncPanel, gbc);

        // --- Restrictions Section ---
        gbc.gridy = 3;
        mainPanel.add(new JLabel("<html><br><b>Restrictions</b></html>"), gbc);

        constraintFields = new JTextField[NUM_RESTRICTIONS][NUM_VARIABLES+1]; // x1, x2, rhs
        inequalityComboBoxes = new JComboBox[NUM_RESTRICTIONS];

        String[] inequalityOptions = {"=<", "=>", "="};

        for (int i = 0; i < NUM_RESTRICTIONS; i++) {
            JPanel constraintPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //FlowBagLayout to align
            constraintPanel.add(new JLabel("Restriction " + (i + 1) + ":"));
            
            for (int j = 0; j < NUM_VARIABLES; j++) {
                constraintFields[i][j] = new JTextField(5);
            }

            constraintFields[i][NUM_VARIABLES] = new JTextField(5); // RHS

            inequalityComboBoxes[i] = new JComboBox<>(inequalityOptions);

            for (int j = 0; j < NUM_VARIABLES; j++) {
                constraintPanel.add(constraintFields[i][j]);
                constraintPanel.add(new JLabel( j < NUM_VARIABLES-1 ? "X"+(j+1)+" + " : "X"+(j+1)));
            }

            constraintPanel.add(inequalityComboBoxes[i]);
            constraintPanel.add(constraintFields[i][NUM_VARIABLES]);

            gbc.gridy = 4 + i;
            mainPanel.add(constraintPanel, gbc);
        }

        // --- Non-Negativity Restriction ---
        gbc.gridy = 4 + NUM_RESTRICTIONS;

        String noNeg2 = "";
        for (int i = 0; i < NUM_VARIABLES; i++) {
            noNeg2 += i < NUM_VARIABLES - 1 ? "X"+(i+1)+", " : "X"+(i+1);
        }
        mainPanel.add(new JLabel("<html><br>"+noNeg2+" ≥ 0</html>"), gbc);

        // --- Bottom Buttons Section ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton solveButton = new JButton("Solve");
        solveButton.setBackground(new Color(66, 139, 202)); // Azul
        solveButton.setForeground(Color.WHITE);
        solveButton.setFocusPainted(false);
        solveButton.setBorderPainted(false);
        solveButton.setPreferredSize(new Dimension(120, 35));

        JButton clearButton = new JButton("Clean");
        clearButton.setBackground(new Color(92, 184, 92)); // Verde
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setPreferredSize(new Dimension(120, 35));

        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectAndDisplayData(); // Call the method to collect and display the data
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields(); // Call the method to clear all fields
            }
        });

        gbc.gridy = 5 + NUM_RESTRICTIONS; // Place in the last row
        gbc.fill = GridBagConstraints.NONE; // Do not expand
        gbc.anchor = GridBagConstraints.CENTER; // Center
        mainPanel.add(buttonPanel, gbc);


        add(mainPanel, BorderLayout.CENTER);


        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);

        pack(); // Adjust the frame size to its components
        setVisible(true);
    }

    // Method for collecting and displaying data
    private void collectAndDisplayData() {
        StringBuilder data = new StringBuilder("Problem Data :\n\n");

        // Objetivo
        data.append("Objetivo: ").append(objetivoComboBox.getSelectedItem()).append("\n");

        // Objective Function
        data.append("Objective Function: ");

        for (int i = 0; i < NUM_VARIABLES; i++) {
            if(i < NUM_VARIABLES - 1)
                data.append(objXField[i].getText()).append(" X"+(i+1)+"+ ");
            else
                data.append(objXField[i].getText()).append(" X"+(i+1)+"\n");
        }

        // Restrictions
        data.append("\nRestrictions:\n");
        for (int row = 0; row < NUM_RESTRICTIONS; row++) {
            data.append("R").append(row + 1).append(": ");

            for (int col = 0; col < NUM_VARIABLES; col++) {
                data.append(constraintFields[row][col].getText()).append(col < NUM_VARIABLES - 1 ? " X"+(col+1)+" + ":" X"+(col+1)+" ");
            }

            data.append(inequalityComboBoxes[row].getSelectedItem()).append(" ");
            data.append(constraintFields[row][NUM_VARIABLES].getText()).append("\n");
        }

        String noNeg = "";
        for (int col = 0; col < NUM_VARIABLES; col++) {
            noNeg += col < NUM_VARIABLES - 1 ? "X"+(col+1)+", " : "X"+(col+1);
        }
        data.append("\nNo Negativity: "+ noNeg +" >= 0\n");

        JOptionPane.showMessageDialog(this, data.toString(), "Data Collected", JOptionPane.INFORMATION_MESSAGE);

        ILinearModel linealModelo = new LinearModel(NUM_VARIABLES, NUM_RESTRICTIONS, objetivoComboBox.getSelectedItem().toString());
        loadModel(linealModelo);

        IModel model = new TwoPhaseMethod(linealModelo);
        model = model.standardize();
        List<Double> result = model.metodoSimple();

        StringBuilder dataResult = new StringBuilder("Result :\t");
        dataResult.append(" (Z = "+result.get(0));
        for (int i = 1; i < result.size(); i++) {
            dataResult.append(" ,X"+i+" = "+result.get(i));
        }
        dataResult.append(" )");


        JOptionPane.showMessageDialog(this, dataResult, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to clear all fields.
    private void clearFields() {

        for (int row = 0; row < NUM_VARIABLES; row++) {
            objXField[row].setText("");
        }
        objetivoComboBox.setSelectedIndex(0); // Restart to "MAXIMIZE"

        for (int row = 0; row < NUM_RESTRICTIONS; row++) {
            for (int col = 0; col <= NUM_VARIABLES; col++) {
                constraintFields[row][col].setText("");
            }

            inequalityComboBoxes[row].setSelectedIndex(0); // Restart to "<="
        }
        JOptionPane.showMessageDialog(this, "All fields have been cleared.");
    }

    private void loadModel(ILinearModel m){

        ArrayList<Double> z =new ArrayList<Double>();
        IntStream.range(0, NUM_VARIABLES)
                .forEach( num -> { z.add(Double.parseDouble(objXField[num].getText())); } );
        m.setListZ(z);

        List<Double> r = new ArrayList<Double>();
        List<String> ineQ = new ArrayList<String>();
        for (int row = 0; row < NUM_RESTRICTIONS; row++) {
            List<Double> x = new ArrayList<Double>();
            for (int col = 0; col < NUM_VARIABLES; col++) {
                x.add(Double.parseDouble(constraintFields[row][col].getText()));
            }
            m.getListaX().set(row, x);
            ineQ.add(inequalityComboBoxes[row].getSelectedItem().toString());
            r.add(Double.parseDouble(constraintFields[row][NUM_VARIABLES].getText()));
        }
        m.setListaDesigualdad(ineQ);
        m.setResources(r);
    }
}

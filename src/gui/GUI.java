package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private JSpinner variableSpinner;
    private JSpinner restrictionSpinner;

    public GUI() {
        setTitle("Simplex Method");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Initial size
        setLocationRelativeTo(null); // Center the window

        // Create main panel with GridBagLayout for better control
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // "Example" link (simulated with a JLabel)
        JLabel exampleLabel = new JLabel("<html><a href=\"#\">Start</a></html>");
        exampleLabel.setForeground(Color.BLUE);
        exampleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exampleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(mainPanel, "Example click!");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(exampleLabel, gbc);

        // Quantity of Variables
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Number of Variables:"), gbc);

        SpinnerModel variableModel = new SpinnerNumberModel(1, 1, 20, 1); // initial, min, max, step
        variableSpinner = new JSpinner(variableModel);
        JFormattedTextField variableTextField = ((JSpinner.DefaultEditor) variableSpinner.getEditor()).getTextField();
        variableTextField.setColumns(5); // Adjust width
        variableTextField.setEditable(false); // Make it non-editable to only use spinner buttons
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(variableSpinner, gbc);
        JLabel variableMaxLabel = new JLabel("Max. 20");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(variableMaxLabel, gbc);


        // Quantity of Restrictions
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Number of Restrictions:"), gbc);

        SpinnerModel restrictionModel = new SpinnerNumberModel(1, 1, 50, 1); // initial, min, max, step
        restrictionSpinner = new JSpinner(restrictionModel);
        JFormattedTextField restrictionTextField = ((JSpinner.DefaultEditor) restrictionSpinner.getEditor()).getTextField();
        restrictionTextField.setColumns(5); // Adjust width
        restrictionTextField.setEditable(false); // Make it non-editable
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(restrictionSpinner, gbc);
        JLabel restrictionMaxLabel = new JLabel("Max. 50");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(restrictionMaxLabel, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Centered, with spacing
        JButton generateButton = new JButton("Enter Data");
        generateButton.setBackground(new Color(66, 139, 202)); // Blue color
        generateButton.setForeground(Color.WHITE); // White text
        generateButton.setFocusPainted(false); // Remove border around text when focused
        generateButton.setBorderPainted(false); // Remove border
        generateButton.setPreferredSize(new Dimension(150, 35)); // Set preferred size

        JButton clearButton = new JButton("Clean");
        clearButton.setBackground(new Color(92, 184, 92)); // Green color
        clearButton.setForeground(Color.WHITE); // White text
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setPreferredSize(new Dimension(150, 35)); // Set preferred size

        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);

        // Add action listeners
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int variables = (Integer) variableSpinner.getValue();
                int restrictions = (Integer) restrictionSpinner.getValue();

                // 1. Create an instance of the new screen, passing it the data and the reference to this screen
                EnterData nextScreen = new EnterData(variables, restrictions, GUI.this);

                // 2. Make the new screen visible
                nextScreen.setVisible(true);

                // 3. Hide the current screen
                setVisible(false);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                variableSpinner.setValue(1); // Reset to initial value
                restrictionSpinner.setValue(1); // Reset to initial value
                JOptionPane.showMessageDialog(mainPanel, "Cleared fields.");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3; // Span across all three columns
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);


        add(mainPanel); // Add the main panel to the frame
    }
}

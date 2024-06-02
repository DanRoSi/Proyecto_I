package uptc.edu.co.gui;


import uptc.edu.co.calculadora.Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazGrafica extends JFrame {
    private JTextField textField;
    private Calculadora calculadora;
    private String baseActual = "DEC";

    public InterfazGrafica() {
        calculadora = new Calculadora();
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel calculatorPanel = new JPanel();
        calculatorPanel.setLayout(null);
        tabbedPane.addTab("Calculator", calculatorPanel);

        JPanel historyPanel = new JPanel();
        tabbedPane.addTab("History", historyPanel);

        JPanel settingsPanel = new JPanel();
        tabbedPane.addTab("Settings", settingsPanel);

        textField = new JTextField();
        textField.setBounds(50, 40, 300, 30);
        calculatorPanel.add(textField);

        String[] buttonLabels = {
                "7", "8", "9", "/", "C",
                "4", "5", "6", "*", "-",
                "1", "2", "3", "+", "√",
                "0", ".", "^", "=", "BIN",
                "OCT", "HEX"
        };

        int x = 50, y = 100, width = 60, height = 40;
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setBounds(x, y, width, height);
            button.addActionListener(new ButtonClickListener());
            calculatorPanel.add(button);

            x += 70;
            if ((i + 1) % 5 == 0) {
                x = 50;
                y += 50;
            }
        }

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "C":
                    textField.setText("");
                    break;
                case "=":
                    try {
                        String expresion = textField.getText();
                        double resultado = calculadora.evaluarExpresion(expresion);
                        textField.setText(String.valueOf(resultado));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "BIN":
                    baseActual = "BIN";
                    textField.setText("");
                    break;
                case "OCT":
                    baseActual = "OCT";
                    textField.setText("");
                    break;
                case "HEX":
                    baseActual = "HEX";
                    textField.setText("");
                    break;
                default:
                    textField.setText(textField.getText() + command);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new InterfazGrafica();
    }
}


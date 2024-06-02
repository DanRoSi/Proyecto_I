package uptc.edu.co.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import uptc.edu.co.logic.Calculator;

public class InterfazGrafica extends JFrame {
    private JTextField textField;
    private Calculator calculator;
    private String currentBase = "DEC";

    public InterfazGrafica() {
        calculator = new Calculator();
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

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    evaluateExpression();
                }
            }
        });

        String[] buttonLabels = {
                "7", "8", "9", "/", "C",
                "4", "5", "6", "*", "-",
                "1", "2", "3", "+", "√",
                "(", ")", "0", ".", "^",
                "=", "BIN", "OCT", "HEX", "←"
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

    private void evaluateExpression() {
        try {
            String expression = textField.getText();
            double result = calculator.evaluateExpression(expression);
            String resultStr = String.valueOf(result);
            textField.setText(resultStr);
            currentBase = "DEC"; // Reset to DEC after evaluation
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void convertBase(String targetBase) {
        try {
            String number = textField.getText();
            if (!number.isEmpty()) {
                String result = calculator.convertBase(number, currentBase, targetBase);
                textField.setText(result);
                currentBase = targetBase;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "C":
                    textField.setText("");
                    currentBase = "DEC"; // Reset to DEC when clearing
                    break;
                case "=":
                    evaluateExpression();
                    break;
                case "BIN":
                case "OCT":
                case "HEX":
                    convertBase(command);
                    break;
                case "←":
                    if (textField.getText().length() > 0) {
                        textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                    }
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

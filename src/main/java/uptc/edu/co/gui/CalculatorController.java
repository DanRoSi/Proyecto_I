package uptc.edu.co.gui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import uptc.edu.co.logic.Calculator;

public class CalculatorController {
    @FXML
    private TextField display;
    private Calculator calculator;
    private String currentBase = "DEC";

    public CalculatorController() {
        this.calculator = new Calculator();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String command = ((javafx.scene.control.Button) event.getSource()).getText();

        switch (command) {
            case "C":
                display.setText("");
                currentBase = "DEC";
                break;
            case "=":
                evaluateExpression();
                break;
            case "BIN":
            case "OCT":
            case "DEC":
            case "HEX":
                convertBase(command);
                break;
            case "←":
                if (display.getText().length() > 0) {
                    display.setText(display.getText().substring(0, display.getText().length() - 1));
                }
                break;
            default:
                display.setText(display.getText() + command);
                break;
        }
    }

    @FXML
    private void handleEnterKey(ActionEvent event) {
        evaluateExpression();
    }

    private void evaluateExpression() {
        try {
            String expression = display.getText();
            double result = calculator.evaluateExpression(expression);
            String resultStr = String.valueOf(result);
            display.setText(resultStr);
            currentBase = "DEC"; // Reset to DEC after evaluation
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private void convertToDecimal() {
        try {
            String number = display.getText();
            if (!number.isEmpty()) {
                int decimal = calculator.convertToDecimal(number, currentBase);
                display.setText(String.valueOf(decimal));
                currentBase = "DEC";
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private void convertBase(String targetBase) {
        try {
            String number = display.getText();
            if (!number.isEmpty()) {
                double doubleNumber = Double.parseDouble(number);
                if (doubleNumber == Math.floor(doubleNumber)) {
                    number = String.valueOf((int) doubleNumber);
                }
                String result = calculator.convertBase(number, currentBase, targetBase);
                display.setText(result);
                currentBase = targetBase;
            }
        } catch (Exception ex) {
            display.setText("Syntax Error");

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> display.clear());
            pause.play();
        }
    }
}


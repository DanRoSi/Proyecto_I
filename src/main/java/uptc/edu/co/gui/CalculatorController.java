package uptc.edu.co.gui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uptc.edu.co.logic.Calculator;

import java.util.ArrayList;
import java.util.List;

public class CalculatorController {


    private List<String> history = new ArrayList<>();
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
        String errorMessage = null;

        if (Character.isDigit(command.charAt(0)) || command.equals(".")) {
            if (currentBase.equals("BIN") && !command.matches("[01]*")) {
                errorMessage = "Error: Solo se permiten ceros y unos en la base binaria";
            }

            if (currentBase.equals("OCT") && !command.matches("[0-7]*")) {
                errorMessage = "Error: Solo se permiten números del 0 al 7 en la base octal";
            }

            if (currentBase.equals("DEC") && !command.matches("\\d*\\.?\\d*")) {
                errorMessage = "Error: Solo se permiten números decimales";
            }

            if (currentBase.equals("HEX") && !command.matches("[0-9A-Fa-f]*")) {
                errorMessage = "Error: Solo se permiten números hexadecimales";
            }
        }

        if (errorMessage != null) {
            String previousText = display.getText();
            display.setText(errorMessage);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> display.setText(previousText));
            pause.play();
            return;
        }

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
            currentBase = "DEC";

            history.add(expression + " = " + resultStr);
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private void handleColorPicker() {
        Color color = colorPicker.getValue();
        String rgb = String.format("rgb(%d, %d, %d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        rootPane.setStyle("-fx-background-color: " + rgb);
        historyPane.setStyle("-fx-background-color: " + rgb);
        settingsPane.setStyle("-fx-background-color: " + rgb);
    }

    @FXML
    private AnchorPane historyPane;

    @FXML
    private AnchorPane settingsPane;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextArea historyArea;

    @FXML
    private Tab historyTab;

    @FXML
    private void handleTabChange() {
        if (historyTab.isSelected()) {
            // Update the history area when the history tab is selected
            historyArea.setText(String.join("\n", history));
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
        String previousDisplay = display.getText();
        try {
            String number = display.getText();
            if (!number.isEmpty()) {
                if (number.endsWith(".0")) {
                    number = number.substring(0, number.length() - 2);
                }
                String result = calculator.convertBase(number, currentBase, targetBase);
                display.setText(result);
                currentBase = targetBase;
            }
        } catch (Exception ex) {
            display.setText("Cannot convert decimal number to " + targetBase);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> display.setText(previousDisplay));
            pause.play();
        }
    }
}


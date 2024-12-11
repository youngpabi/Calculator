package com.example.calculator;

import java.math.BigDecimal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class App extends Application {

    private BigDecimal left;
    private String selectedOperator;
    private boolean numberInputting;

    @FXML
    private TextField display;

    public App() {
        this.left = BigDecimal.ZERO;
        this.selectedOperator = "";
        this.numberInputting = false;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculator");
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });
        stage.setResizable(false);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("main.fxml"))));
        stage.show();
    }

    @FXML
    protected void handleOnAnyButtonClicked(ActionEvent evt) {
        Button button = (Button)evt.getSource();
        final String buttonText = button.getText();
        if (buttonText.equals("C") || buttonText.equals("AC")) {
            if (buttonText.equals("AC")) {
                left = BigDecimal.ZERO;
            }
            selectedOperator = "";
            numberInputting = false;
            display.setText("0");;
            return;
        }
        if (buttonText.matches("[0-9\\.]")) {
            if (!numberInputting) {
                numberInputting = true;
                display.clear();
            }
            display.appendText(buttonText);
            return;
        }
        if (buttonText.matches("[＋－×÷]")) {
            left = new BigDecimal(display.getText());
            selectedOperator = buttonText;
            numberInputting = false;
            return;
        }
        if (buttonText.equals("=")) {
            final BigDecimal right = numberInputting ? new BigDecimal(display.getText()) : left;
            left = calculate(selectedOperator, left, right);
            display.setText(left.toString());
            numberInputting = false;
            return;
        }
    }

    static BigDecimal calculate(String operator, BigDecimal left, BigDecimal right) {
        switch (operator) {
            case "＋":
                return left.add(right);
            case "－":
                return left.subtract(right);
            case "×":
                return left.multiply(right);
            case "÷":
                return left.divide(right);
            default:
        }
        return right;
    }

    public static void main(String[] args) {
        launch(args);
    }

}

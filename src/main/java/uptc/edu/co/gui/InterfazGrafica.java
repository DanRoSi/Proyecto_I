package uptc.edu.co.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class InterfazGrafica extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/uptc/edu/co/proyecto_i/Calculadora.fxml")));
        primaryStage.setTitle("Calculadora");
        primaryStage.setScene(new Scene(root));

        // Establecemos el tamaño de la ventana y asi hacerla no redimensionable
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(320);
        primaryStage.setMinHeight(430);
        primaryStage.setMaxWidth(320);
        primaryStage.setMaxHeight(420);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
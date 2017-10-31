package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    static String url="../sample/View/";
    @Override

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(url.concat("login.fxml")));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
        primaryStage.setResizable(true);



    }

    public static void main(String[] args) {
        launch(args);

    }

    public void autentificar(ActionEvent actionEvent) throws IOException {
        Parent segunda = FXMLLoader.load(getClass().getResource(url.concat("home.fxml")));
        Scene nueva = new Scene(segunda);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();
    }
}

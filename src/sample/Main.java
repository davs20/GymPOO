package sample;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import static java.lang.Integer.toBinaryString;

public class Main extends Application {
    private final static String usuario = "root";
    private final static String pass = "";
    private static String conexion = "jdbc:mysql://localhost:3306/Gym";
    private static Connection connection;
    public static Statement comando = null;
    private static ResultSet registro;

    static String url = "../sample/View/";

    @Override

    public void start(Stage primaryStage) throws Exception {
        abrirConexion();
        Parent root = FXMLLoader.load(getClass().getResource("../sample/View/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
        primaryStage.setResizable(false);



    }

    public static void main(String[] args) {
        launch(args);

    }

    private static Statement abrirConexion() {

        try {
            connection = DriverManager.getConnection(conexion, usuario, pass);
            comando = connection.createStatement();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dialogo de Error");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText("No se ha podido establecer conexion con la base de datos");
            alert.showAndWait();

        }
        return null;
    }


    public void autentificar(ActionEvent actionEvent) throws IOException {
        Parent segunda = FXMLLoader.load(getClass().getResource(url.concat("home.fxml")));
        Scene nueva = new Scene(segunda);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();
    }
}

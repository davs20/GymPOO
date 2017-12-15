package sample.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Model.ModelUsuario;
import sample.Validator.ValidatorLogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import static sample.Controller.Controller.urlbase;


public class ControllerLogin implements  Initializable {
    public JFXTextField usuario;
    public JFXPasswordField contrasen;
    public Button entrar;
    public Label error;
    public AnchorPane panel;

    public static  String idUsuario;
    public static  String nombreUsuario;

    private void nuevaScena(String scena, ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        Scene nueva = new Scene(fxml);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();

    }

    public void autentificar(KeyEvent key) {


        if (ValidatorLogin.validacion("[0-9]{13,13}", usuario.getText())) {
            error.setText("Correcto");
            usuario.setFocusColor(new Color(0.3294, 0.1255, 1, 1));
        } else {
            error.setText("Incorrecto");
            usuario.setFocusColor(new Color(1, 0, 0.0235, 1));
        }

        this.estadoBoton();


    }

    private void estadoBoton() {
        if (ValidatorLogin.validacion("[0-9]{13,13}", usuario.getText()) &&
                ValidatorLogin.validacion("[A-Z,a-z,0-9]{10,}", contrasen.getText())) {
            entrar.setDisable(false);
        } else {
            entrar.setDisable(true);
        }
    }

    public void autentificarPass(KeyEvent key) {

        estadoBoton();
    }


    public void iniciarSesion(ActionEvent actionEvent) {
        try {
            ModelUsuario user=ModelUsuario.validarCredenciales(new ValidatorLogin(), usuario.getText(), contrasen.getText());
            idUsuario=usuario.getText();
            switch (user.getIdPrivilegio()) {
                case "1":
                    nuevaScena("home.fxml", actionEvent);
                    break;
                case "2":
                    nuevaScena("Cajero/HomeCajero.fxml", actionEvent);
                    break;
                case "3":
                    nuevaScena("Entrenador/homeEntrenador.fxml", actionEvent);
                    break;

            }
        } catch (NullPointerException a) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dialogo de Error");
            alert.setHeaderText("Ha ocurrido error");
            alert.setContentText("Fue imposible  Validar sus Credenciales ");
            alert.showAndWait();

        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dialogo de Error");
            alert.setHeaderText("Ha ocurrido error");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundImage myBI= new BackgroundImage(new Image("/sample/Controller/hello-world.png",650,400,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        panel.setBackground(new Background(myBI));

    }
}


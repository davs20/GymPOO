package sample.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Validator.Validator;
import sample.Validator.ValidatorLogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Boolean.TRUE;
import static sample.Controller.Controller.urlbase;


public class ControllerLogin implements Initializable {
    public JFXTextField usuario;
    public JFXTextField contrasena;
    public Button entrar;
    public Label error;

    private void nuevaScena(String scena, ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        Scene nueva = new Scene(fxml);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();
    }

    private void mensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }

    public void autentificar(KeyEvent key) throws IOException {
        ValidatorLogin login = new ValidatorLogin();
        error.setText(login.usuario(usuario.getText()));
        if(login.usuario(usuario.getText()).equals("Correcto") && login.contrasena(contrasena.getText()).equals("Correcto") ){
            entrar.setDisable(false);
        }else{
            entrar.setDisable(true);
        }


    }
    public void autentificarPass(KeyEvent key){
        ValidatorLogin login =new ValidatorLogin();
        login.contrasena(contrasena.getText());
        if(login.usuario(usuario.getText()).equals("Correcto") && login.contrasena(contrasena.getText()).equals("Correcto") ){
            entrar.setDisable(false);
        }else{
            entrar.setDisable(true);
        }
    }

    public void btn() {


    }

    public void entrenador(ActionEvent actionEvent) throws IOException {
        nuevaScena("Entrenador/inicio.fxml", actionEvent);
    }

    public void clientes(ActionEvent actionEvent) throws IOException {
        nuevaScena("Administrador/Clientes/inicio.fxml", actionEvent);

    }

    public void configuracion(ActionEvent actionEvent) throws IOException {
        nuevaScena("Configuracion/inicio.fxml", actionEvent);
    }

    public void ventas(ActionEvent actionEvent) throws IOException {
        nuevaScena("Ventas/inicio.fxml", actionEvent);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entrar.setDisable(true);
    }
}

package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static sample.Controller.Controller.url;


public class ControllerLogin {

    public Button entrar;


    private void nuevaScena (String scena,ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(url.concat(scena)));
        Scene nueva = new Scene(fxml);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();
    }
    private void mensaje(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }
    public void autentificar(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }

    public void entrenador(ActionEvent actionEvent) throws IOException {
        nuevaScena("Entrenador/inicio.fxml",actionEvent );
    }

    public void clientes(ActionEvent actionEvent) throws IOException {
        nuevaScena("Administrador/Clientes/inicio.fxml",actionEvent);

    }

    public void configuracion(ActionEvent actionEvent) throws IOException {
        nuevaScena("Configuracion/inicio.fxml",actionEvent);
    }

    public void ventas(ActionEvent actionEvent) throws IOException {
        nuevaScena("Ventas/inicio.fxml",actionEvent);

    }

}

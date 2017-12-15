package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static sample.Controller.Controller.urlbase;

public class ControllerHomeCajero extends ControllerUsuarios {


    private void nuevaScena(String scena, ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        Scene nueva = new Scene(fxml);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();

    }

    public void menu(ActionEvent actionEvent){
        try {
            nuevaScena("../View/Cajero/HomeCajero.fxml",actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void asistencia(ActionEvent actionEvent) {
        try {
            nuevaScena("../View/Cajero/Asistencia.fxml",actionEvent);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void venta(ActionEvent actionEvent){
        try {
            nuevaScena("../View/Cajero/factura.fxml",actionEvent);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

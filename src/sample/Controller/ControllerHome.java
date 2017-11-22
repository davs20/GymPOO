package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

public class ControllerHome implements Initializable {



    private void nuevaScena(ActionEvent actionEvent,String scena,String titulo){
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nueva = new Scene(fxml);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setTitle(titulo);
        inicio.setScene(nueva);
        inicio.show();
    }


    public void ventas(ActionEvent actionEvent){

    }
    public void clientes(ActionEvent actionEvent){
    nuevaScena(actionEvent,"Administrador/Cliente/generalCliente.fxml","Datos Del Cliente");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

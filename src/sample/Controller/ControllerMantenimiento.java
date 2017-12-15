package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static sample.Controller.Controller.urlbase;

public class ControllerMantenimiento {


    private void nuevaScena(ActionEvent actionEvent, String scena, String titulo) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nueva = new Scene(fxml);
        Stage inicio2 = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio2.setTitle(titulo);
        inicio2.setScene(nueva);
        inicio2.show();
    }

    public void enfermedades(ActionEvent actionEvent){
     nuevaScena(actionEvent,"Administrador/Configuracion/Enfermedad/enfermedades.fxml","Enfermedad");
    }

    public void impuesto(ActionEvent actionEvent){
        nuevaScena(actionEvent,"Administrador/Configuracion/Impuesto/impuesto.fxml","impuesto");
    }

    public void usuarios(ActionEvent actionEvent){
        nuevaScena(actionEvent,"Administrador/Configuracion/Usuarios/Usuarios.fxml","Usuarios");
    }

    public void cajero(ActionEvent actionEvent){
      nuevaScena(actionEvent,"Administrador/Configuracion/PuntoEmision/puntoEmision.fxml","Cajero");
    }

    public void home(ActionEvent actionEvent){
     nuevaScena(actionEvent,"home.fxml","Home");
    }
    public void membresia(ActionEvent actionEvent){
     nuevaScena(actionEvent,"Administrador/Configuracion/Membresia/membresia.fxml","Membresia");
    }


}

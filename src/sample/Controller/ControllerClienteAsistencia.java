package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.ModelAsistencia;
import sample.Model.ModelCliente;
import sample.Validator.Validator;
import sample.Validator.ValidatorAsistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

public class ControllerClienteAsistencia  implements Initializable {
    public TextField buscar;
    public JFXTextField nombreC;
    public JFXTextField tel;
    public JFXTextField id;
    public JFXTextField diasRestantes;
    public JFXButton confirmar;
    public ImageView foto;


    public JFXTextField idUsuario;
    private ValidatorAsistencia asistencia;


    private void nuevaScena(String scena, ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        Scene nueva = new Scene(fxml);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();

    }


    public void menu(ActionEvent actionEvent) {
        try {
            nuevaScena("../View/Cajero/HomeCajero.fxml", actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buscarCliente(ActionEvent actionEvent) {

        if(Validator.validacion("([0-9]){13,13}",buscar.getText())){


            try {

                ResultSet cliente = ModelCliente.mostrarClienteAsistencia(buscar.getText());
                   cliente.next();

                   if(cliente.getInt("diasRestantes")<=((cliente.getInt("Servicio.duracion")*30))){
                       nombreC.setText(cliente.getString("nombre") + " " + cliente.getString("apellido"));
                       tel.setText(cliente.getString("telefono"));
                       id.setText(cliente.getString("idCliente"));
                       diasRestantes.setText(""+cliente.getInt("diasRestantes"));
                       foto.setImage(new Image(new FileInputStream(cliente.getString("foto"))));
                       confirmar.setDisable(false);

                   }else {
                       Alert alert = new Alert(Alert.AlertType.WARNING);
                       alert.setTitle("Advertencia");
                       alert.setHeaderText("Atencion");
                       alert.setContentText("La membresia para este cliente ha expirado");
                       alert.showAndWait();
                       confirmar.setDisable(true);


                   }




            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialogo de Error");
                alert.setHeaderText("Ha ocurrido un error");
                alert.setContentText("Cliente no encontrado");
                alert.showAndWait();
                e.printStackTrace();


            }catch (NullPointerException a){

            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialogo de Error");
                alert.setHeaderText("Ha ocurrido un error");
                alert.setContentText("Imagen no Encontrada");
                alert.showAndWait();
                e.printStackTrace();
            }
        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dialogo de Error");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText("Campo de Busqueda Incorrecto");
            alert.showAndWait();
            id.setText("");
            nombreC.setText("");
            tel.setText("");



        }


    }


    public void confirmarAsistencia(ActionEvent actionEvent)  {

        try {

            ModelAsistencia.confirmarAsistencia(id.getText(),Integer.parseInt(diasRestantes.getText()),idUsuario.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idUsuario.setText(ControllerLogin.idUsuario);
        confirmar.setDisable(true);
    }
}

package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Model.ModelCliente;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static sample.Controller.Controller.urlbase;

public class ControllerClienteAsistencia {
    public TextField buscar;
    public  TextField nombreC;
    public TextField  tel;
    public  TextField id;
    public ImageView foto;

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

    public void buscarCliente(ActionEvent actionEvent){
        ResultSet cliente= ModelCliente.mostrarCliente(buscar.getText());
        try {
            cliente.next();
            nombreC.setText(cliente.getString("nombre") + " "+ cliente.getString("apellido"));
            tel.setText(cliente.getString("telefono"));
            id.setText(cliente.getString("idCliente"));
          //  foto.imageProperty().set(new Image(cliente.getString("foto")));
            try {
                foto.setImage(new Image(new FileInputStream(cliente.getString("foto"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

package sample.Controller;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.config.Conexion;
import sample.config.OperacionesSQl;
import sample.modelo.Model;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;

public class Controller {
    @FXML
    public TableView<Model> tabla;
    @FXML
    public TableColumn<Model, String> nombre;
    @FXML
    public TableColumn<Model, String> pass;
    private ObservableList<Model> data;
    public Button boton;

    public void prueba() throws SQLException {
        data = FXCollections.observableArrayList();
        Conexion consulta = OperacionesSQl.select("*", "administrador").get();
        while (consulta.registro.next()) {
            data.add(new Model(consulta.registro.getString("ID_Usuario"), consulta.registro.getString("Contrasena")));
        }

//        tabla.setItems(data);
    }


    public void da(ActionEvent actionEvent) throws SQLException {
        data = FXCollections.observableArrayList();
        Conexion consulta = OperacionesSQl.select("*", "administrador").get();
        while (consulta.registro.next()) {
            data.add(new Model(consulta.registro.getString("ID_Usuario"), consulta.registro.getString("Contrasena")));
        }
        nombre.setCellValueFactory(new PropertyValueFactory<>("estado"));
        pass.setCellValueFactory(new PropertyValueFactory<>("pass"));
        tabla.setItems(data);
    }
}

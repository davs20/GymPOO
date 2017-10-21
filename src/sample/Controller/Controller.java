package sample.Controller;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.config.Conexion;
import sample.config.OperacionesSQl;
import sample.Model.Model;

import java.io.IOException;
import java.sql.SQLException;

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

    protected static String urlbase="../sample/View/";


    protected void da(ActionEvent actionEvent) throws SQLException {
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

package sample.Controller;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.Main;
import sample.Model.ModelCliente;
import sample.config.Conexion;
import sample.config.OperacionesSQl;
import sample.Validator.ValidatorCliente;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerCliente implements Initializable {
    ObservableList<ModelCliente> lista;
    public TableView tblCliente;
    public TableColumn<ModelCliente, String> nombre;
    public TableColumn<ModelCliente, Date> fechaIngreso;
    public TableColumn<ModelCliente, String> id;
    public TableColumn<ModelCliente, String> memb;

    public void inicio() {

    }

    public Conexion insertar(ValidatorCliente validatorcliente) throws SQLException {
        Conexion consulta = OperacionesSQl.select("*", "administrador").get();
        return consulta;

    }


    public void actualizar() {

    }


    public void mostrarCliente(ActionEvent actionEvent) {
        System.out.println(tblCliente);

    }


    public void editar() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lista = FXCollections.observableArrayList();
        tblCliente.setItems(lista);
        try {
            ModelCliente.mostrarTodos(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblCliente.setEditable(true);
        nombre.setCellValueFactory(new PropertyValueFactory<ModelCliente, String>("nombre"));
        memb.setCellValueFactory(new PropertyValueFactory<>("membresia"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fechaIngreso.setCellValueFactory(new PropertyValueFactory<ModelCliente, Date>("fecha"));
        nombre.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}

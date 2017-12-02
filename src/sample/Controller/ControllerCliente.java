package sample.Controller;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import sample.Model.ModelCliente;
import sample.config.Conexion;
import sample.config.OperacionesSQl;
import sample.Validator.ValidatorCliente;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

public class ControllerCliente implements Initializable {
    //-----------TableView---------------///
    ObservableList<ModelCliente> lista;
    public TableView tblCliente;
    public TableColumn<ModelCliente, String> nombre;
    public TableColumn<ModelCliente, Date> fechaIngreso;
    public TableColumn<ModelCliente, String> id;
    public TableColumn<ModelCliente, String> memb;
    public TableColumn<ModelCliente, String> acciones;
    public TextField buscar;
    public static Button boton;
    ////---------------------------------///
    public String Id;


    //-----------datosMedicos--------///
    public TextField enfermedad;
    public ComboBox<String> tipo;

    //-------------------------------///


    public void insertarCliente(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Administrador/Cliente/insertarCliente.fxml"));
        try {
            VBox page = (VBox) loader.load();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Nuevo Cliente");
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner((Stage) ((Button) actionEvent.getSource()).getScene().getWindow());
            primaryStage.setScene(new Scene(page, 720, 710));
            ControllerDetalleCliente controller = loader.getController();
            controller.setStage(primaryStage);

            primaryStage.showAndWait();
            primaryStage.setResizable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void insertarDatosMedicos() {

    }

    private void insertarDatosSeguimiento() {

    }

    public void regresarMenu(ActionEvent actionEvent) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("../View/home.fxml"));
            Scene nueva = new Scene(fxml);
            Stage menu = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            menu.setTitle("Inicio");
            menu.setScene(nueva);
            menu.show();
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void buscar(KeyEvent keyEvent) {
        lista.clear();
        ModelCliente.buscar(buscar.getText(), lista,tblCliente);


    }

    public Button getBoton() {
        return boton;
    }



    public void recargarTabla(ActionEvent actionEvent) {
        lista.clear();
        try {
            ModelCliente.mostrarTodos(lista,tblCliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lista = FXCollections.observableArrayList();
        try {
            ModelCliente.mostrarTodos(lista,tblCliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblCliente.setItems(lista);
        nombre.setCellValueFactory(new PropertyValueFactory<ModelCliente, String>("nombre"));
        memb.setCellValueFactory(new PropertyValueFactory<>("membresia"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fechaIngreso.setCellValueFactory(new PropertyValueFactory<ModelCliente, Date>("fecha"));
        acciones.setCellValueFactory(new PropertyValueFactory<ModelCliente, String>("acciones"));


    }


    public void actualizarNombre(ActionEvent actionEvent) throws SQLException {

        if (!tblCliente.getSelectionModel().getSelectedCells().isEmpty()) {
//            Runtime garbage = Runtime.getRuntime();
//            garbage.gc();

            String id = lista.get(tblCliente.getSelectionModel().getSelectedIndex()).getId();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Administrador/Cliente/Cliente.fxml"));
            try {
                VBox page = (VBox) loader.load();
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Login");
                primaryStage.initModality(Modality.WINDOW_MODAL);
                primaryStage.initOwner((Stage) ((Button) actionEvent.getSource()).getScene().getWindow());
                primaryStage.setScene(new Scene(page, 900, 900));
                ControllerDetalleCliente controller = loader.getController();
                controller.setStage(primaryStage);
                controller.setCliente(id);
                primaryStage.showAndWait();
                primaryStage.setResizable(true);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void setdatosPersonales(String id) {
        try {
            ResultSet datospersonales = ModelCliente.mostrarCliente(id);
            datospersonales.next();
//            System.out.println(datospersonales.getString("nombre"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setdatosMedicos(String id) {

    }

    private void setdatosSeguimiento(String id) {

    }


}

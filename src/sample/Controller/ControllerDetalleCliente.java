package sample.Controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.ModelCliente;
import sample.Validator.ValidatorCliente;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerDetalleCliente implements Initializable {

    public JFXTextField nombretxt;
    public JFXTextField apellidotxt;
    public JFXTextField telefonotxt;
    public JFXTextField correotxt;
    public JFXTextField idtxt;
    public Tab datPersonales;
    public Tab datMedicos;
    public Tab datServicio;
    public TextArea direcciontxt;
    public JFXComboBox padecimiento;


    public JFXTextField alturatxt;
    public JFXTextField pesotxt;
    public JFXTextField fuerzatxt;


    public TextArea comentariostxt;

    private Stage stage;
    private String id;

    private ObservableList<String> enfermedades;

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    public void setCliente(String id) {
        ResultSet datosCliente = ModelCliente.mostrarCliente(id);
        try {
            datosCliente.next();
            nombretxt.setText(datosCliente.getString("nombre"));
            telefonotxt.setText(datosCliente.getString("telefono"));
            idtxt.setText(datosCliente.getString("idCliente"));
            correotxt.setText(datosCliente.getString("correo"));
            direcciontxt.setText(datosCliente.getString("direccion"));


            alturatxt.setText("" + datosCliente.getString("altura"));
            pesotxt.setText(datosCliente.getString("peso"));
            fuerzatxt.setText(datosCliente.getString("fuerza"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void datosPersonales(ActionEvent actionEvent) {
        datMedicos.getTabPane().getSelectionModel().selectPrevious();
        datMedicos.setDisable(true);
        datServicio.setDisable(true);
        datPersonales.setDisable(false);
    }

    public void atrasDatMedicos(ActionEvent actionEvent) {
        datServicio.getTabPane().getSelectionModel().selectPrevious();
        datServicio.setDisable(true);
        datMedicos.setDisable(false);
        datPersonales.setDisable(true);
    }


    public void datosServicio(ActionEvent actionEvent) {

        datMedicos.getTabPane().getSelectionModel().selectNext();
        datMedicos.setDisable(true);
        datPersonales.setDisable(true);
        datServicio.setDisable(false);

    }


    public void datosMedicos(ActionEvent actionEvent) {

        datPersonales.getTabPane().getSelectionModel().selectNext();
        datMedicos.setDisable(false);
        datPersonales.setDisable(true);
        datServicio.setDisable(true);


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enfermedades= FXCollections.observableArrayList();
        datMedicos.setDisable(true);
        datServicio.setDisable(true);
        try {
            ModelCliente.listaEnfermedades(this.enfermedades);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        padecimiento.setItems(this.enfermedades);
    }
}

package sample.Controller;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.BorderPane;

import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.util.StringConverter;
import sample.Main;
import sample.Model.ModelCliente;
import sample.Model.ModelEnfermedades;
import sample.Model.ModelMembresia;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

public class ControllerDetalleCliente extends ControllerCliente implements Initializable {

    public JFXTextField nombretxt;
    public JFXTextField apellidotxt;
    public JFXTextField telefonotxt;
    public JFXTextField correotxt;
    public JFXTextField idtxt;
    public Tab datPersonales;
    public Tab datMedicos;
    public Tab datServicio;
    public Tab datFoto;

    public JFXComboBox<ModelEnfermedades> padecimiento;
    public JFXComboBox<ModelMembresia> membresias;
    public JFXDatePicker fecha;
    public JFXTextField edadtxt;
    public JFXTextField alturatxt;
    public JFXTextField pesotxt;
    public JFXTextField fuerzatxt;


    public TextArea comentariostxt;

    private Stage stage;
    private String id;

    private ObservableList<ModelEnfermedades> enfermedades;
    private ObservableList<ModelMembresia> membresia;

    public void setStage(Stage stage) {

        this.stage = stage;
    }


    public void guardarTodo(ActionEvent actionEvent) {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Dialogo de Confirmacion");
            alert.setHeaderText("Confimacion");
            alert.setContentText("Esta Seguro de Guardar a este Cliente?");
            alert.showAndWait();
            Main.comando.execute("INSERT INTO `Cliente` (`idCliente`, `fechaNacimiento`, `nombre`, `apellido`, " +
                    "`telefono`, `Membresia_idMembresia`, `Clientecol`, `edad`, `Enfermedades_idEnfermedades`, `peso`, " +
                    "`fuerza`, `masaMuscular`, `correo`, `direccion`, `altura`, `foto`) VALUES ('" + idtxt.getText() + "'," +
                    " '" + fecha.getValue() + "', '" + nombretxt.getText() + "', '" + apellidotxt.getText() + "', '"
                    + telefonotxt.getText() + "'," + " '1', NULL, 33, '1', '" + pesotxt.getText() + "', '" + fuerzatxt.getText()
                    + "', 123, '" + correotxt.getText() + "'" +
                    ", NULL, '" + alturatxt.getText() + "'," + " NULL);");
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void nuevaScena(String scena, ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        Scene nueva = new Scene(fxml);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();
        fxml=null;
        nueva=null;
        inicio=null;
        System.gc();

    }


    public void setFoto(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Administrador/Cliente/WebCamPreview.fxml"));
        try {
            BorderPane page = (BorderPane) loader.load();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Nuevo Cliente");
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner((Stage) ((Button) actionEvent.getSource()).getScene().getWindow());
            primaryStage.setScene(new Scene(page, 720, 710));
            ControllerClienteFoto controller = loader.getController();
            controller.setDireccion(this.idtxt.getText());
            primaryStage.showAndWait();
            primaryStage.setResizable(true);
            page=null;
            loader=null;
            primaryStage=null;
            Runtime rt = Runtime.getRuntime();
            System.out.println(rt.freeMemory());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCliente(Integer index) {
        ResultSet datosCliente = ModelCliente.mostrarCliente(id);
        try {
            datosCliente.next();
            nombretxt.setText(lista.get(index).getNombre());
            telefonotxt.setText(datosCliente.getString("telefono"));
            idtxt.setText(datosCliente.getString("idCliente"));
            correotxt.setText(datosCliente.getString("correo"));



            alturatxt.setText("" + datosCliente.getString("altura"));
            pesotxt.setText(datosCliente.getString("peso"));
            fuerzatxt.setText(datosCliente.getString("fuerza"));
            datosCliente.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setdatosServicio(String string) {
        try {
            ModelMembresia.mostrarMembresia(this.membresia,string);
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

    public void datoFoto(ActionEvent actionEvent){
        datFoto.getTabPane().getSelectionModel().selectNext();
        datFoto.setDisable(false);
        datPersonales.setDisable(true);
        datMedicos.setDisable(true);
        datServicio.setDisable(true);

    }

    public void atrasDatFoto(ActionEvent actionEvent){
        datFoto.getTabPane().getSelectionModel().selectPrevious();
        datFoto.setDisable(true);
        datServicio.setDisable(false);
        datPersonales.setDisable(true);
        datMedicos.setDisable(true);
    }

    public void datosMedicos(ActionEvent actionEvent) {

        datPersonales.getTabPane().getSelectionModel().selectNext();
        datMedicos.setDisable(false);
        datPersonales.setDisable(true);
        datServicio.setDisable(true);


    }

    private void setCamposMedicos(String id) {
        System.out.println(id);
        try {

            ResultSet datosMedicos = ModelEnfermedades.getDatosMedicos(id);
            datosMedicos.next();
            comentariostxt.setText(datosMedicos.getString("cuidado"));
            datosMedicos.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enfermedades = FXCollections.observableArrayList();
        membresia = FXCollections.observableArrayList();
        datMedicos.setDisable(true);
        datServicio.setDisable(true);


        padecimiento.setConverter(new StringConverter<ModelEnfermedades>() {
            @Override
            public String toString(ModelEnfermedades object) {
                return object.getId();
            }

            @Override
            public ModelEnfermedades fromString(String string) {
                return padecimiento.getItems().stream().filter(ap ->
                        ap.getNombre().equals(string)).findFirst().orElse(null);
            }
        });

        membresias.setConverter(new StringConverter<ModelMembresia>() {
            @Override
            public String toString(ModelMembresia object) {
                return object.getNombre();
            }

            @Override
            public ModelMembresia fromString(String string) {
                return membresias.getItems().stream().filter(ap ->
                        ap.getNombre().equals(string)).findFirst().orElse(null);
            }
        });

        padecimiento.valueProperty().addListener(new ChangeListener<ModelEnfermedades>() {
            @Override
            public void changed(ObservableValue<? extends ModelEnfermedades> observable, ModelEnfermedades oldValue, ModelEnfermedades newValue) {

                setCamposMedicos(newValue.getNombre());
            }
        });

        membresias.valueProperty().addListener(new ChangeListener<ModelMembresia>() {
            @Override
            public void changed(ObservableValue<? extends ModelMembresia> observable, ModelMembresia oldValue, ModelMembresia newValue) {
                System.out.println(newValue.getId());
            }
        });

        try {
            ModelEnfermedades.listaEnfermedades(this.enfermedades);
            ModelMembresia.listaMembresias(this.membresia);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        padecimiento.setItems(this.enfermedades);
        membresias.setItems(this.membresia);
    }
}

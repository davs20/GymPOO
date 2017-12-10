package sample.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import sample.Model.ModelCliente;
import sample.Validator.ValidatorCliente;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;


public class ControllerEntrenador implements Initializable {
    public TableView<ModelCliente> tbl;
    @FXML
    private TableColumn<ModelCliente, String> clNombre;
    public TableColumn<ModelCliente, String> clApellido;
    public TableColumn<ModelCliente, String> clMembresia;
    public TableColumn<ModelCliente, String> clfecha;

    private ObservableList<ModelCliente> listaclientes;
    public JFXTextField buscar;
    public Button boton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        listaclientes = FXCollections.observableArrayList();
        try {
            boton.setStyle("-fx-background-color: #3793ff; -fx-background-radius: 17%;");
            boton.setTextFill(new Color(1, 1, 1, 1));

            boton.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("hola");
                }
            });

            ModelCliente.mostrarTodos(listaclientes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tbl.setItems(listaclientes);

        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));


    }

    public void inicio(ActionEvent actionEvent) throws IOException {
        ///cambiar nombre de urlbase no esta bien..
        Parent entrenador = FXMLLoader.load(getClass().getResource(urlbase.concat("Entrenador/Entrenador.fxml")));
        Scene nueva = new Scene(entrenador);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();


    }


    public void insertar() {
        //ModelEntrenador entrenador1 = new ModelEntrenador();
        //entrenador1.agregar();
    }


    public void editar(ActionEvent actionEvent) {
        try {
            ModelCliente editar = tbl.getSelectionModel().getSelectedItem();
            editar.getApellido();
        } catch (NullPointerException e) {
            seleccionError();
        }
    }


    public void mostrar(ActionEvent actionEvent) {
        try {
            ModelCliente mostrarCliente = tbl.getSelectionModel().getSelectedItem();
            System.out.println(mostrarCliente.getApellido());
        } catch (NullPointerException e) {
            seleccionError();
        }

    }

    private void seleccionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Dialogo de Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText("No ha seleccionado algun cliente");
        alert.showAndWait();
    }

    public void buscar(KeyEvent keyEvent) {
        if (ValidatorCliente.validacion("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+",buscar.getText())) {
            buscar.setFocusColor(new Color(0,0,1,1));
        } else {
            buscar.setFocusColor(new Color(1,0,0,1));
        }
        Runtime garbage = Runtime.getRuntime();
        System.out.println("memoria libre "+garbage.freeMemory());

        garbage.gc();

    }

    public void verCliente() {

    }


}

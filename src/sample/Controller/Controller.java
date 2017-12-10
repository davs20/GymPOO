package sample.Controller;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.config.Conexion;
import sample.Model.Model;

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

    protected static String urlbase="../View/";





}

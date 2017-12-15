package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.ModelMembresia;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

public class MembresiaController implements Initializable {
    public JFXButton nuevoMembresiaBT;
    public JFXButton modificarMembresiaBT;
    public JFXButton eliminarMembresiaBT;
    public JFXButton guardarMembresiaBT;
    public String idMembresia;
    //TextFields Datos Membresia
    public JFXTextField duracionMembresiaTF;
    //Tabla y Columnas de Lista de Membresia
    public JFXTextField nombreMembresiaTF;
    public TableView<ModelMembresia> tablaMembresia;
    public JFXTextField precioMembresiaTF;
    public TableColumn nombreMembresiaCL;
    public JFXTextArea descripcionMembresiaTF;
    public TableColumn precioMembresiaCL;
    public TableColumn descripcionMembresiaCL;
    public TableColumn duracionMembresiaCL;

    //Colecciones - listas observables
    private ObservableList<ModelMembresia> membresias;

    int id;

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    //Metodos para los Botones
    private void nuevaScena(ActionEvent actionEvent, String scena, String titulo) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nueva = new Scene(fxml);
        Stage inicio2 = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio2.setTitle(titulo);
        inicio2.setScene(nueva);
        inicio2.show();
    }

    public void modificarMembresiaBT(ActionEvent event) {
        System.out.println("adadasdasdadad");
        ModelMembresia m = new ModelMembresia(
                nombreMembresiaTF.getText(),
                descripcionMembresiaTF.getText(),
                Double.parseDouble(precioMembresiaTF.getText()),
                Integer.parseInt(getIdMembresia()),
                Integer.parseInt(duracionMembresiaTF.getText())
        );

        //Llamar al metodo guardarMembresia de la clase Membresia
        int resultado = m.actualizarRegistros();

        if (resultado == 1) {
            membresias.set(tablaMembresia.getSelectionModel().getSelectedIndex(), m);
            //tablaEnfermedad.setItems(membresias);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Actualizado");
            mensaje.setContentText("El Registro ha sido actualizado exitosamente");
            mensaje.show();
        }
    }

    public void eliminarMembresiaBT(ActionEvent event) {

        //Obtenemos la instancia del registro que esta actualmente seleccionado
        int resultado = tablaMembresia.getSelectionModel().getSelectedItem().eliminarRegistro(getIdMembresia());

        if (resultado == 1) {
            membresias.remove(tablaMembresia.getSelectionModel().getSelectedIndex());
            //tablaEnfermedad.setItems(membresias);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Eliminado");
            mensaje.setContentText("El Registro ha sido eliminado exitosamente");
            mensaje.show();
        }
    }

    public void nuevoMembresiaBT(ActionEvent event) {
        //  idMembresiaFT.setText("");
        nombreMembresiaTF.setText("");
        precioMembresiaTF.setText("");
        descripcionMembresiaTF.setText("");
        descripcionMembresiaTF.setText("");

        //Botones Habilitados y Desahabilitados
        modificarMembresiaBT.setDisable(true);
        eliminarMembresiaBT.setDisable(true);
        guardarMembresiaBT.setDisable(false);
    }

    public void guardarMembresiaBT(ActionEvent event) {

        // if (!nombreEnfermedadTF.getText().isEmpty() || !precioMembresiaFT.getText().isEmpty() || !duracionMembresiaFT.getText().isEmpty()) {

        //Llamar al metodo guardarMembresia de la clase Membresia

        //Crear una nueva instancia del tipo membresia

        try {
            ModelMembresia m = new ModelMembresia(
                    nombreMembresiaTF.getText(),
                    descripcionMembresiaTF.getText(),
                    Double.parseDouble(precioMembresiaTF.getText()),
                    ModelMembresia.obtenerId(id) + 1,
                    Integer.parseInt(duracionMembresiaTF.getText())
            );

            Boolean accion = m.guardarMembresia();
            if (accion) {
                Alert mensaje = new Alert(Alert.AlertType.WARNING);
                mensaje.setTitle("Campos Vacios");
                mensaje.setContentText("Debe llenar los campos");
                mensaje.show();

            } else {
                membresias.add(m);
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Registro Agregado ");
                mensaje.setContentText("El Registro ha sido agregado exitosamente");
                mensaje.show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void inicializarTablaMembresia() {

        //Inicializamos las colleciones - listas observables

        nombreMembresiaCL.setCellValueFactory(new PropertyValueFactory<ModelMembresia, String>("nombre"));
        descripcionMembresiaCL.setCellValueFactory(new PropertyValueFactory<ModelMembresia, String>("descripcion"));
        precioMembresiaCL.setCellValueFactory(new PropertyValueFactory<ModelMembresia, Double>("precio"));
        duracionMembresiaCL.setCellValueFactory(new PropertyValueFactory<ModelMembresia, Integer>("duracion"));
        escucharEventos();

    }

    public void escucharEventos() {
        tablaMembresia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelMembresia>() {
            @Override
            public void changed(ObservableValue<? extends ModelMembresia> observable, ModelMembresia valorAnterior, ModelMembresia valorSeleccionado) {
                if (valorSeleccionado != null) {//Validamos que la variable valorSelecionado no vaya NULL;
                    setIdMembresia(valorSeleccionado.getId());
                    nombreMembresiaTF.setText(valorSeleccionado.getNombre());
                    precioMembresiaTF.setText(valorSeleccionado.getDescripcion());
                    precioMembresiaTF.setText("" + valorSeleccionado.getPrecio());
                    duracionMembresiaTF.setText(valorSeleccionado.getDuracion().toString());
                    guardarMembresiaBT.setDisable(true);
                    eliminarMembresiaBT.setDisable(false);
                    modificarMembresiaBT.setDisable(false);
                }
            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        membresias = FXCollections.observableArrayList();
        try {
            ModelMembresia.listaMembresias(membresias);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tablaMembresia.setItems(membresias);
        this.inicializarTablaMembresia();


    }

}

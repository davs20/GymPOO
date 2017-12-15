package sample.Controller;

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
import sample.Controller.ConexionDB;
import sample.Model.Enfermedad;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

/**
 * Created by User on 12/12/2017.
 */
public class enfermedadController implements Initializable {
    //Botones Datos Membresia
    public Button nuevoMembresiaBT;
    public Button modificarEnfermedadBT;
    public Button eliminarEnfermedadBT;
    public Button guardarEnfermedadBT;

    //TextFields Datos Membresia
    public TextField nombreEnfermedadTF;
    public TextArea cuidadoEnfermedadTA;
    public TextArea impedimentoEnfermedadTA;
    public TextField duracionMembresiaFT;
    public TextField idEnfermedadTF;

    //Tabla y Columnas de Lista de Membresia
    public TableView<Enfermedad> tablaEnfermedad;
    public TableColumn nombreEnfermedadCL;
    public TableColumn cuidadoEnfermedadCL;
    public TableColumn impedimentoEnfermedadCL;


    //Colecciones - listas observables
    private ObservableList<Enfermedad> enfermedades;

    //Conexion
    private ConexionDB conexion;

    public int id;

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

    public void atras(ActionEvent actionEvent){
        nuevaScena(actionEvent,"Administrador/Configuracion/VistaMantenimiento.fxml","Home");

    }
    public void modificarEnfermedadBT(ActionEvent event) {
        Enfermedad e = new Enfermedad(Integer.parseInt(idEnfermedadTF.getText()), nombreEnfermedadTF.getText(), impedimentoEnfermedadTA.getText(), cuidadoEnfermedadTA.getText());


        //Llamar al metodo guardarMembresia de la clase Membresia
        conexion.establecerConexion();
        int resultado = e.actualizarRegistros(conexion.getConnection());
        conexion.cerrarConexion();
        if (resultado == 1) {
            enfermedades.set(tablaEnfermedad.getSelectionModel().getSelectedIndex(),e);
            //tablaEnfermedad.setItems(enfermedades);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Actualizado");
            mensaje.setContentText("El Registro ha sido actualizado exitosamente");
            mensaje.show();
        }
    }

    public void eliminarEnfermedadBT(ActionEvent event) {
        conexion.establecerConexion();
        //Obtenemos la instancia del registro que esta actualmente seleccionado
        int resultado = tablaEnfermedad.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            enfermedades.remove(tablaEnfermedad.getSelectionModel().getSelectedIndex());
            //tablaEnfermedad.setItems(enfermedades);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Eliminado");
            mensaje.setContentText("El Registro ha sido eliminado exitosamente");
            mensaje.show();
        }
    }

    public void nuevoEnfermedadBT(ActionEvent event) {
        //  idMembresiaFT.setText("");
        nombreEnfermedadTF.setDisable(false);
        impedimentoEnfermedadTA.setDisable(false);
        cuidadoEnfermedadTA.setDisable(false);
        nombreEnfermedadTF.setText("");
        cuidadoEnfermedadTA.setText("");
        impedimentoEnfermedadTA.setText("");

        //Botones Habilitados y Desahabilitados
        modificarEnfermedadBT.setDisable(true);
        eliminarEnfermedadBT.setDisable(true);
        guardarEnfermedadBT.setDisable(false);
    }

    public void guardarEnfermedadBT(ActionEvent event) {

        // if (!nombreEnfermedadTF.getText().isEmpty() || !precioMembresiaFT.getText().isEmpty() || !duracionMembresiaFT.getText().isEmpty()) {

        //Llamar al metodo guardarMembresia de la clase Membresia
        conexion.establecerConexion();
        //Crear una nueva instancia del tipo membresia
        Enfermedad e = new Enfermedad(Enfermedad.obtenerId(conexion.getConnection(),id)+1, nombreEnfermedadTF.getText(), impedimentoEnfermedadTA.getText(), cuidadoEnfermedadTA.getText());

        int resultado = e.guardarEnfermedad(conexion.getConnection());

        if (resultado == 1) {
            enfermedades.add(e);
            //tablaEnfermedad.setItems(enfermedades);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Agregado ");
            mensaje.setContentText("El Registro ha sido agregado exitosamente");
            mensaje.show();
            conexion.cerrarConexion();

            //   }
//                else {
//                Alert mensaje = new Alert(Alert.AlertType.WARNING);
//                mensaje.setTitle("Campos Vacios");
//                mensaje.setContentText("Debe llenar los campos");
//                mensaje.show();
//
//
//            }
        }
    }


    public void inicializarTablaEnfermedad() {

        //Inicializamos las colleciones - listas observables

        nombreEnfermedadCL.setCellValueFactory(new PropertyValueFactory<Enfermedad, String>("nombreEnfermedad"));
        impedimentoEnfermedadCL.setCellValueFactory(new PropertyValueFactory<Enfermedad, String>("impedimentoEnfermedad"));
        cuidadoEnfermedadCL.setCellValueFactory(new PropertyValueFactory<Enfermedad, String>("cuidadoEnfermedad"));
        escucharEventos();

    }


    public void escucharEventos() {
        tablaEnfermedad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Enfermedad>() {
            @Override
            public void changed(ObservableValue<? extends Enfermedad> observable, Enfermedad valorAnterior, Enfermedad valorSeleccionado) {
                if (valorSeleccionado!=null) {//Validamos que la variable valorSelecionado no vaya NULL
                    idEnfermedadTF.setText(String.valueOf(valorSeleccionado.getIdEnfermedades()));
                    nombreEnfermedadTF.setText(valorSeleccionado.getNombreEnfermedad());
                    impedimentoEnfermedadTA.setText(valorSeleccionado.getImpedimentoEnfermedad());
                    cuidadoEnfermedadTA.setText(valorSeleccionado.getCuidadoEnfermedad());
                    guardarEnfermedadBT.setDisable(true);
                    eliminarEnfermedadBT.setDisable(false);
                    modificarEnfermedadBT.setDisable(false);
                    nombreEnfermedadTF.setDisable(false);
                    impedimentoEnfermedadTA.setDisable(false);
                    cuidadoEnfermedadTA.setDisable(false);

                }
            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new ConexionDB();
        conexion.establecerConexion();
        enfermedades = FXCollections.observableArrayList();
        Enfermedad.llenarTablaEnfermedad(conexion.getConnection(),enfermedades);
        tablaEnfermedad.setItems(enfermedades);
        this.inicializarTablaEnfermedad();
        conexion.cerrarConexion();
        idEnfermedadTF.setVisible(false);//ID de la Membresia no visible
        nombreEnfermedadTF.setDisable(true);
        impedimentoEnfermedadTA.setDisable(true);
        cuidadoEnfermedadTA.setDisable(true);
        guardarEnfermedadBT.setDisable(true);

    }
}

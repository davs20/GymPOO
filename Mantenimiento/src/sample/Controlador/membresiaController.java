package sample.Controlador;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Modelo.ConexionDB;
import sample.Modelo.Membresia;

import java.net.URL;
import java.util.ResourceBundle;

public class membresiaController implements Initializable {
    //Botones Datos Membresia
    public Button nuevoMembresiaBT;
    public Button modificarMembresiaBT;
    public Button eliminarMembresiaBT;
    public Button guardarMembresiaBT;

    //TextFields Datos Membresia
    public JFXTextField nombreMembresiaFT;
    public JFXTextField precioMembresiaFT;
    public JFXTextArea descripcionMembresiaFT;
    public JFXTextField duracionMembresiaFT;
    public TextField idMembresiaFT;

    //Tabla y Columnas de Lista de Membresia
    public TableView<Membresia> tablaMembresia;
    public TableColumn nombreMembresiaCL;
    public TableColumn precioMembresiaCL;
    public TableColumn descripcionMembresiaCL;
    public TableColumn duracionMembresiaCL;

    //Colecciones - listas observables
    private ObservableList<Membresia> membresias;

    //Conexion
    private ConexionDB conexion;

    public int id;

    //Metodos para los Botones

    public void modificarMembresiaBT(ActionEvent event) {
        Membresia m = new Membresia(Integer.parseInt(idMembresiaFT.getText()), nombreMembresiaFT.getText(), descripcionMembresiaFT.getText(), Double.parseDouble(precioMembresiaFT.getText()), Integer.parseInt(duracionMembresiaFT.getText()));

        //Llamar al metodo guardarMembresia de la clase Membresia
        conexion.establecerConexion();
        int resultado = m.actualizarRegistros(conexion.getConnection());
        conexion.cerrarConexion();
        if (resultado == 1) {
            membresias.set(tablaMembresia.getSelectionModel().getSelectedIndex(),m);
            //tablaEnfermedad.setItems(membresias);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Actualizado");
            mensaje.setContentText("El Registro ha sido actualizado exitosamente");
            mensaje.show();
        }
    }

    public void eliminarMembresiaBT(ActionEvent event) {
        conexion.establecerConexion();
        //Obtenemos la instancia del registro que esta actualmente seleccionado
        int resultado = tablaMembresia.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

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
        nombreMembresiaFT.setText("");
        precioMembresiaFT.setText("");
        descripcionMembresiaFT.setText("");
        duracionMembresiaFT.setText("");

        //Botones Habilitados y Desahabilitados
        modificarMembresiaBT.setDisable(true);
        eliminarMembresiaBT.setDisable(true);
        guardarMembresiaBT.setDisable(false);
    }

    public void guardarMembresiaBT(ActionEvent event) {

       // if (!nombreEnfermedadTF.getText().isEmpty() || !precioMembresiaFT.getText().isEmpty() || !duracionMembresiaFT.getText().isEmpty()) {

            //Llamar al metodo guardarMembresia de la clase Membresia
            conexion.establecerConexion();
        //Crear una nueva instancia del tipo membresia
        Membresia m = new Membresia(Membresia.obtenerId(conexion.getConnection(),id)+1, nombreMembresiaFT.getText(), descripcionMembresiaFT.getText(), Double.parseDouble(precioMembresiaFT.getText()), Integer.parseInt(duracionMembresiaFT.getText()));

        int resultado = m.guardarMembresia(conexion.getConnection());

            if (resultado == 1) {
                membresias.add(m);
                //tablaEnfermedad.setItems(membresias);
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


    public void inicializarTablaMembresia() {

        //Inicializamos las colleciones - listas observables

        nombreMembresiaCL.setCellValueFactory(new PropertyValueFactory<Membresia, String>("nombreMembresia"));
        descripcionMembresiaCL.setCellValueFactory(new PropertyValueFactory<Membresia, String>("descripcionMembresia"));
        precioMembresiaCL.setCellValueFactory(new PropertyValueFactory<Membresia, Double>("precioMembresia"));
        duracionMembresiaCL.setCellValueFactory(new PropertyValueFactory<Membresia, Integer>("duracionMembresia"));
        escucharEventos();

    }

    public void escucharEventos() {
        tablaMembresia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Membresia>() {
            @Override
            public void changed(ObservableValue<? extends Membresia> observable, Membresia valorAnterior, Membresia valorSeleccionado) {
                if (valorSeleccionado!=null) {//Validamos que la variable valorSelecionado no vaya NULL
                    idMembresiaFT.setText(valorSeleccionado.getidMembresia().toString());
                    nombreMembresiaFT.setText(valorSeleccionado.getNombreMembresia());
                    descripcionMembresiaFT.setText(valorSeleccionado.getDescripcionMembresia());
                    precioMembresiaFT.setText(valorSeleccionado.getPrecioMembresia().toString());
                    duracionMembresiaFT.setText(valorSeleccionado.getDuracionMembresia().toString());
                    guardarMembresiaBT.setDisable(true);
                    eliminarMembresiaBT.setDisable(false);
                    modificarMembresiaBT.setDisable(false);
                }
            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new ConexionDB();
        conexion.establecerConexion();
        membresias = FXCollections.observableArrayList();
        Membresia.llenarTablaMembresia(conexion.getConnection(), membresias);
        tablaMembresia.setItems(membresias);
        this.inicializarTablaMembresia();
        conexion.cerrarConexion();
        idMembresiaFT.setVisible(false);//ID de la Membresia no visible

    }
}

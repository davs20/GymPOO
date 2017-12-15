package sample.Controlador;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Modelo.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Created by User on 12/14/2017.
 */
public class seguimientoController implements Initializable{

    //Botones Datos Membresia
    public Button nuevoUsuarioBT;
    public Button modificarBT;
    public Button eliminarBT;
    public Button guardarBT;
    public Button buscar;

    //TextFields Datos Membresia
    //public JFXTextField idSeguimientoTF;
    public JFXTextField idClienteSeguimientoTF;
//    public JFXTextField nombreClienteSeguimientoTF;
    public JFXTextField pesoSeguimientoTF;
    public JFXTextField grasaSeguimientoTF;
    public JFXTextField alturaSeguimientoTF;
    public JFXTextField buscarID;


    //Tabla y Columnas de Lista de Membresia
    public TableView<Seguimiento> tablaSeguimiento;
    public TableColumn idClienteSeguimientoCL;
    public TableColumn nombreClienteSeguimientoCL;
    public TableColumn pesoSeguimientoCL;
    public TableColumn grasaSeguimientoCL;
    public TableColumn alturaSeguimientoCL;
    public TableColumn fechaModificacionCL;

    public Timestamp fecha;
    //Coleccion Privilegio
    private ObservableList<ClienteSeguimiento> clienteSeguimientos;
    public ComboBox<ClienteSeguimiento> cmbNombreCliente;


    //Colecciones - listas observables
    private ObservableList<Seguimiento> seguimientos;

    //Conexion
    private ConexionDB conexion;


    //Metodos para los Botones

    public void modificarBT(ActionEvent event) {
        Seguimiento s = new Seguimiento(0,Double.valueOf(pesoSeguimientoTF.getText()),Double.valueOf(grasaSeguimientoTF.getText()),Double.valueOf(alturaSeguimientoTF.getText()),fecha,cmbNombreCliente.getSelectionModel().getSelectedItem());

        //Llamar al metodo guardarMembresia de la clase Membresia
        conexion.establecerConexion();
        int resultado = s.actualizarRegistros(conexion.getConnection());
        conexion.cerrarConexion();
        if (resultado == 1) {
            seguimientos.set(tablaSeguimiento.getSelectionModel().getSelectedIndex(), s);
            //tablaEnfermedad.setItems(membresias);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Actualizado");
            mensaje.setContentText("El Registro ha sido actualizado exitosamente");
            mensaje.show();
        }
        nuevoBT(event);
    }



    public void nuevoBT(ActionEvent event) {

        idClienteSeguimientoTF.setText("");
        cmbNombreCliente.setValue(null);
        pesoSeguimientoTF.setText("");
        grasaSeguimientoTF.setText("");
        alturaSeguimientoTF.setText("");
        //telefonoUsuarioTF.setText("");

        //Botones Habilitados y Desahabilitados
        modificarBT.setDisable(true);
        eliminarBT.setDisable(true);
        guardarBT.setDisable(false);
    }

    public void guardarBT(ActionEvent event) {

        // if (!nombreEnfermedadTF.getText().isEmpty() || !precioMembresiaFT.getText().isEmpty() || !duracionMembresiaFT.getText().isEmpty()) {
        if (!idClienteSeguimientoTF.getText().equals("")&&!pesoSeguimientoTF.getText().equals("")&&!grasaSeguimientoTF.getText().equals("")) {
            //    if(validarCorreo()){
            //Llamar al metodo guardarMembresia de la clase Membresia
            conexion.establecerConexion();
            //Crear una nueva instancia del tipo membresia
            Seguimiento s =  new Seguimiento(0,Double.valueOf(pesoSeguimientoTF.getText()),Double.valueOf(grasaSeguimientoTF.getText()),Double.valueOf(alturaSeguimientoTF.getText()),fecha,cmbNombreCliente.getSelectionModel().getSelectedItem());
            int resultado = s.guardarRegistro(conexion.getConnection());
            conexion.cerrarConexion();
            if (resultado == 1) {
                seguimientos.add(s);
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Registro Agregado ");
                mensaje.setContentText("El Registro ha sido agregado exitosamente");
                mensaje.show();
                //}
            }
        }else {
            Alert mensaje = new Alert(Alert.AlertType.WARNING);
            mensaje.setTitle("Campos Vacios");
            mensaje.setContentText("Debe llenar los campos");
            mensaje.show();


        }

    }


    public void inicializarTabla() {

        //Inicializamos las colleciones - listas observables

        idClienteSeguimientoCL.setCellValueFactory(new PropertyValueFactory<Seguimiento, String>("nombresUsuario"));
        nombreClienteSeguimientoCL.setCellValueFactory(new PropertyValueFactory<Seguimiento, String>("apellidosUsuario"));
        grasaSeguimientoCL.setCellValueFactory(new PropertyValueFactory<Seguimiento, Double>("contrasenaUsuario"));
        pesoSeguimientoCL.setCellValueFactory(new PropertyValueFactory<Seguimiento, Double>("idUsuario"));
        fechaModificacionCL.setCellValueFactory(new PropertyValueFactory<Seguimiento, LocalDateTime>("telefonoUsuario"));
        alturaSeguimientoCL.setCellValueFactory(new PropertyValueFactory<Seguimiento, Double>("correoUsuario"));
        //privilegioUsuarioCL.setCellValueFactory(new PropertyValueFactory<Usuario, String>("privilegio"));
        escucharEventos();

    }

    private void escucharEventos() {
        tablaSeguimiento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Seguimiento>() {
            @Override
            public void changed(ObservableValue<? extends Seguimiento> observable, Seguimiento valorAnterior, Seguimiento valorSeleccionado) {
                if (valorSeleccionado != null) {//Validamos que la variable valorSelecionado no vaya NULL

                    idClienteSeguimientoTF.setText(String.valueOf(valorSeleccionado.getClienteSeguimiento().idClienteSeguimiento));
                    pesoSeguimientoTF.setText(String.valueOf(valorSeleccionado.getPesoSeguimiento()));
                    grasaSeguimientoTF.setText(String.valueOf(valorSeleccionado.getGrasaSeguimiento()));
                    alturaSeguimientoTF.setText(String.valueOf(valorSeleccionado.getAlturaSeguimiento()));
                    cmbNombreCliente.setValue(valorSeleccionado.getClienteSeguimiento());
                    guardarBT.setDisable(true);
                    eliminarBT.setDisable(false);
                    modificarBT.setDisable(false);
                }
            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new ConexionDB();
        conexion.establecerConexion();
        seguimientos = FXCollections.observableArrayList();
        clienteSeguimientos = FXCollections.observableArrayList();
        Seguimiento.llenarinformacion(conexion.getConnection(), seguimientos);
        ClienteSeguimiento.llenarTabla(conexion.getConnection(), clienteSeguimientos);
        cmbNombreCliente.setItems(clienteSeguimientos);
        tablaSeguimiento.setItems(seguimientos);
        this.inicializarTabla();
        conexion.cerrarConexion();
        modificarBT.setDisable(true);
        eliminarBT.setDisable(true);

    }



}

package sample.Controlador;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Modelo.ConexionDB;
import sample.Modelo.Impuesto;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Created by User on 12/12/2017.
 */
public class impuestoController implements Initializable {
    //Botones Datos Impuesto
    public Button btnActualizar;

    //TextFields Datos Impuesto
    public TextField nuevoValorTF;

    //
    public Timestamp fecha ;

    //Tabla y Columnas de Lista de Impuesto
    public TableView<Impuesto> tablaImpuesto;
    public TableColumn valorCL;
    public TableColumn fechaModificacionCL;

    //Colecciones - listas observables
    private ObservableList<Impuesto> impuestos;

    //Conexion
    private ConexionDB conexion;

    //Metodos para los Botones

    public void btnActualizar (ActionEvent event) {
        conexion.establecerConexion();
        Impuesto i = new Impuesto(Double.valueOf(nuevoValorTF.getText()),Impuesto.obtenerFecha(conexion.getConnection(), fecha ));

        //Llamar al metodo actualizarRegistros de la clase Impuesto
        int resultado=0;

        resultado = i.actualizarRegistros(conexion.getConnection());
        conexion.cerrarConexion();
        if (resultado == 1) {
            impuestos.set(tablaImpuesto.getSelectionModel().getSelectedIndex(),i);
            //tablaEnfermedad.setItems(membresias);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Actualizacion de Impuesto");
            mensaje.setHeaderText("IMPUESTO ACTUALIZADO");
            mensaje.setContentText("El impuesto ha sido actualizado exitosamente");
            mensaje.show();
            nuevoValorTF.setDisable(true);
            btnActualizar.setDisable(true);

        }
    }



    public void inicializarTablaImpuesto() {

        //Inicializamos las colleciones - listas observables

        valorCL.setCellValueFactory(new PropertyValueFactory<Impuesto, Double>("valor"));
        fechaModificacionCL.setCellValueFactory(new PropertyValueFactory<Impuesto, LocalDateTime>("fechaModificacion"));
        escucharEventos();

    }

    public void escucharEventos() {
        tablaImpuesto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Impuesto>() {
            @Override
            public void changed(ObservableValue<? extends Impuesto> observable, Impuesto valorAnterior, Impuesto valorSeleccionado) {
                if (valorSeleccionado!=null) {//Validamos que la variable valorSelecionado no vaya NULL
                    nuevoValorTF.setText(String.valueOf(valorSeleccionado.getValor()));
                    nuevoValorTF.setDisable(false);
                    btnActualizar.setDisable(false);

                }
            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new ConexionDB();
        conexion.establecerConexion();
        impuestos = FXCollections.observableArrayList();
        Impuesto.llenarTablaImpuesto(conexion.getConnection(), impuestos);
        tablaImpuesto.setItems(impuestos);
        this.inicializarTablaImpuesto();
        conexion.cerrarConexion();
        //idMembresiaFT.setVisible(false);//ID de la Membresia no visible
        nuevoValorTF.setDisable(true);
        btnActualizar.setDisable(true);

    }

}

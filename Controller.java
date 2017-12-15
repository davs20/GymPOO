package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    //Botones Datos Membresia
    public Button nuevoMembresiaBT;
    public Button modificarMembresiaBT;
    public Button eliminarMembresiaBT;
    public Button guardarMembresiaBT;

    //TextFields Datos Membresia
    public TextField nombreMembresiaFT;
    public TextField precioMembresiaFT;
    public TextArea descripcionMembresiaFT;
    public TextField duracionMembresiaFT;

    //Tabla y Columnas de Lista de Membresia
    public TableView<Membresia> tablaMembresia;
    public TableColumn nombreMembresiaCL;
    public TableColumn precioMembresiaCL;
    public TableColumn descripcionMembresiaCL;
    public TableColumn duracionMembresiaCL;

    //Colecciones - listas observables
    ObservableList<Membresia> membresias;

    //Conexion
    private ConexionDB conexion;

    //ObservableList<Membresia> listaMembresia;-------------------

    public int posicionMembresiaEnTabla;

    //Metodos para los Botones

    public void modificarMembresiaBT(ActionEvent event) {
//        Membresia membresia = new Membresia();
//        membresia.nombreMembresia.set(nombreMembresiaFT.getText());
//        membresia.descripcionMembresia.set(descripcionMembresiaFT.getText());
//        membresia.precioMembresia.set(Double.parseDouble(precioMembresiaFT.getText()));
//        membresia.duracionMembresia.set(Integer.parseInt(duracionMembresiaFT.getText()));
//        membresias.set(posicionMembresiaEnTabla, membresia);
    }

    public void eliminarMembresiaBT(ActionEvent event) {
        membresias.remove(posicionMembresiaEnTabla);
    }

    public void nuevoMembresiaBT(ActionEvent event) {
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
//        Membresia membresia = new Membresia();
//        membresia.nombreMembresia.set(nombreMembresiaFT.getText());
//        membresia.descripcionMembresia.set(descripcionMembresiaFT.getText());
//        membresia.precioMembresia.set(Double.parseDouble(precioMembresiaFT.getText()));
//        membresia.duracionMembresia.set(Integer.parseInt(duracionMembresiaFT.getText()));
//        membresias.add(membresia);
    }

    // Seleccionar una celda de la tabla "tablaMembresia"
    public final ListChangeListener<Membresia> selectorTablaMembresia = new ListChangeListener<Membresia>() {
        @Override
        public void onChanged(Change<? extends Membresia> c) {
            ponerMembresiaSellecionada();
        }
    };

    public Membresia getTablaMembresiaSeleccionada() {
        if (tablaMembresia != null) {
            List<Membresia> tabla = tablaMembresia.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Membresia membresiaSeleccionada = tabla.get(0);
                return membresiaSeleccionada;
            }

        }
        return null;
    }

    public void ponerMembresiaSellecionada() {
        final Membresia membresia = getTablaMembresiaSeleccionada();
        posicionMembresiaEnTabla = membresias.indexOf(membresia);

        if (membresia != null) {

            //Pongo los TextFields con los datos correspondientes
            nombreMembresiaFT.setText(membresia.getNombreMembresia());
            precioMembresiaFT.setText(membresia.getPrecioMembresia().toString());
            descripcionMembresiaFT.setText(membresia.getDescripcionMembresia());
            duracionMembresiaFT.setText(membresia.getDuracionMembresia().toString());

            //Pongo los botones en su estado disponible o correspondiente
            modificarMembresiaBT.setDisable(false);
            eliminarMembresiaBT.setDisable(false);
            guardarMembresiaBT.setDisable(true);
        }
    }



    public void inicializarTablaMembresia() {

        //Inicializamos las colleciones - listas observables
        //listaMembresia = FXCollections.observableArrayList();**************************************************************


        nombreMembresiaCL.setCellValueFactory(new PropertyValueFactory<Membresia, String>("nombreMembresia"));
        descripcionMembresiaCL.setCellValueFactory(new PropertyValueFactory<Membresia, String>("descripcionMembresia"));
        precioMembresiaCL.setCellValueFactory(new PropertyValueFactory<Membresia, Double>("precioMembresia"));
        duracionMembresiaCL.setCellValueFactory(new PropertyValueFactory<Membresia, Integer>("duracionMembresia"));



    }

    public void initialize() {


        //Selecionar los registros de la tabla
//        final ObservableList<Membresia> tablaMembresiaSel = tablaMembresia.getSelectionModel().getSelectedItems();
//        tablaMembresiaSel.addListener(selectorTablaMembresia);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new ConexionDB();
        conexion.establecerConexion();
        ObservableList<Membresia> membresias;
        membresias = FXCollections.observableArrayList();
        Membresia.llenarTablaMembresia(conexion.getConnection(),membresias);
        tablaMembresia.setItems(membresias);
        this.inicializarTablaMembresia();
        conexion.cerrarConexion();

    }
}

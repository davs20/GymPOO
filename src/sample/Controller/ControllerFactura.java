package sample.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.webkit.Timer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import sample.Main;
import sample.Model.*;
import sample.Validator.ValidatorVenta;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerFactura implements Initializable {

    public JFXTextField efectivo;
    public JFXTextField cambio;
    public JFXTextField subtotal2;
    public JFXTextField sucursal;
    public JFXTextField noFactura1;
    public JFXTextField precio1;
    public JFXComboBox<ModelImpuesto> impuesto1;


    public JFXComboBox<ModelMembresia> servicio1;
    public JFXComboBox<ModelFormasPago> formaPago;

    private ObservableList<ModelCliente> lista;
    private ObservableList<ModelFactura> listaFactura;
    private ObservableList<ModelFormasPago> listaPago;
    private ObservableList<ModelMembresia> listaServicio;
    private ObservableList<ModelImpuesto> listaImpuesto;
    private Double impuesto3;
    public TableView tblCliente;
    public TableView tblFactura;
    public TableColumn<ModelFactura, Integer> noFactura;
    public TableColumn<ModelFactura, String> servicio;
    public TableColumn<ModelFactura, Date> fechaVenta;
    public TableColumn<ModelFactura, Date> fechaVencimiento;
    public TableColumn<ModelFactura, Double> precio;
    public TableColumn<ModelFactura, Double> impuesto;
    public TableColumn<ModelFactura, Double> subtotal;

    private String idCl;

    public JFXTextField buscar;
    public TableColumn<ModelCliente, String> nombre;
    public TableColumn<ModelCliente, String> id;
    public JFXDatePicker fechaVenta1;

    public String getIdCl() {
        return idCl;
    }

    public void setIdCl(String idCl) {
        this.idCl = idCl;
    }

    private void recargarTablaFactura(){
    listaFactura.clear();
        try {
            ModelFactura.mostrarFactura(idCl,listaFactura);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void nuevaVenta(ActionEvent actionEvent) {

        ValidatorVenta venta = new ValidatorVenta(
                Double.parseDouble(
                        precio1.getText()),
                impuesto3,
                Double.parseDouble(cambio.getText()),
                Double.parseDouble(subtotal2.getText()),
                Double.parseDouble(efectivo.getText())
        );


        try {
            ModelFactura.insertarFactura(venta, servicio1.valueProperty().get().getId(), "LI132-DASd", getIdCl(),Integer.parseInt(noFactura1.getText()));
            recargarTablaFactura();
        } catch (SQLException e) {
            e.printStackTrace();

        }


    }

    private void imprimirFactura(){

    }

    private void tablaFactura(String id) {
        try {
            ModelFactura.mostrarFactura(id, listaFactura);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblFactura.setItems(listaFactura);
        noFactura.setCellValueFactory(new PropertyValueFactory<ModelFactura, Integer>("noFactura"));
        servicio.setCellValueFactory(new PropertyValueFactory<ModelFactura, String>("servicio"));
        fechaVenta.setCellValueFactory(new PropertyValueFactory<ModelFactura, Date>("fechaCompra"));
        fechaVencimiento.setCellValueFactory(new PropertyValueFactory<ModelFactura, Date>("fechaVencimiento"));
        precio.setCellValueFactory(new PropertyValueFactory<ModelFactura, Double>("precio"));
        impuesto.setCellValueFactory(new PropertyValueFactory<ModelFactura, Double>("impuesto"));
        subtotal.setCellValueFactory(new PropertyValueFactory<ModelFactura, Double>("subtotal"));


    }

    private void setImpuesto1(Double impuesto) {
        this.impuesto3 = impuesto;
    }

    private Double getImpuesto() {
        return impuesto3;
    }

    public void calculoFactura(KeyEvent keyEvent) {

        if (keyEvent.getCode().isDigitKey() || keyEvent.getCode().isFunctionKey()) {
            Double precio = Double.parseDouble(precio1.getText());
            Double efectivo1 = Double.parseDouble(efectivo.getText());
            Double subtotal = Double.parseDouble(precio1.getText()) * (1 + getImpuesto());
            Double cambio1 = efectivo1 - subtotal;

            if (cambio1 >= 0) {

                cambio.setText(cambio1.toString());
                subtotal2.setText(subtotal.toString());
            } else {
                cambio.setText("Efectivo no es suficiente");
            }

        }


    }


    private void setTablaCliente() {
        try {
            ModelCliente.mostrarTodos(lista);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        tblCliente.setItems(lista);
        nombre.setCellValueFactory(new PropertyValueFactory<ModelCliente, String>("nombre"));
        id.setCellValueFactory(new PropertyValueFactory<ModelCliente, String>("id"));


    }
    private void setNoFactura(){
        try {
            noFactura1.setText(ModelFactura.getNoFactura2());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDatosPrecio(String id) {
        try {
            ResultSet datoPrecio = ModelMembresia.mostrarMembresia(id);
            datoPrecio.next();
            precio1.setText("" + datoPrecio.getDouble("precio"));
            datoPrecio.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void llenarListaImpuesto() {
        try {
            ModelImpuesto.mostrarImpuesto(listaImpuesto);
            impuesto1.setItems(listaImpuesto);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listaPago = FXCollections.observableArrayList();
        listaServicio = FXCollections.observableArrayList();
        lista = FXCollections.observableArrayList();
        listaFactura = FXCollections.observableArrayList();
        listaImpuesto = FXCollections.observableArrayList();
        llenarListaPago();
        llenarListaServicio();
        llenarListaImpuesto();
        setTablaCliente();

        formaPago.setConverter(new StringConverter<ModelFormasPago>() {
            @Override
            public String toString(ModelFormasPago object) {
                return object.getNombre();
            }

            @Override
            public ModelFormasPago fromString(String string) {
                return formaPago.getItems().stream().filter(ap ->
                        ap.getId().equals(string)).findFirst().orElse(null);
            }
        });


        impuesto1.setConverter(new StringConverter<ModelImpuesto>() {
            @Override
            public String toString(ModelImpuesto object) {
                return object.getValorImpuesto().toString();
            }

            @Override
            public ModelImpuesto fromString(String string) {
                return impuesto1.getItems().stream().filter(ap ->
                        ap.getIdImpuesto().equals(string)).findFirst().orElse(null);
            }
        });

        servicio1.valueProperty().addListener(new ChangeListener<ModelMembresia>() {
            @Override
            public void changed(ObservableValue<? extends ModelMembresia> observable, ModelMembresia oldValue, ModelMembresia newValue) {

                setDatosPrecio(newValue.getId());
                setNoFactura();

            }
        });


        servicio1.setConverter(new StringConverter<ModelMembresia>() {


            @Override
            public String toString(ModelMembresia object) {
                return object.getNombre();
            }

            @Override
            public ModelMembresia fromString(String string) {
                return servicio1.getItems().stream().filter(ap ->
                        ap.getId().equals(string)).findFirst().orElse(null);
            }
        });


        tblCliente.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelCliente>() {
            @Override
            public void changed(ObservableValue<? extends ModelCliente> observable, ModelCliente oldValue, ModelCliente newValue) {
                listaFactura.clear();
                setIdCl(newValue.getId());
                tablaFactura(getIdCl());


            }


        });
        impuesto1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelImpuesto>() {
            @Override
            public void changed(ObservableValue<? extends ModelImpuesto> observable, ModelImpuesto oldValue, ModelImpuesto newValue) {
                setImpuesto1(newValue.getValorImpuesto());
            }
        });


    }

    private void llenarListaServicio() {
        try {
            ModelMembresia.listaMembresias(listaServicio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        servicio1.setItems(listaServicio);
    }

    private void llenarListaPago() {
        try {
            ModelFormasPago.ListaFormasPago(listaPago);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        formaPago.setItems(listaPago);

    }

    public void buscar(KeyEvent keyEvent) {
        lista.clear();
        ModelCliente.buscar(buscar.getText(), lista);


    }


    public void nuevoCliente(ActionEvent actionEvent) {
    }
}

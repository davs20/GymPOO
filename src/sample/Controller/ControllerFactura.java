package sample.Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.webkit.Timer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.util.StringConverter;
import org.omg.CORBA.MARSHAL;
import sample.Main;
import sample.Model.*;
import sample.Validator.ValidatorVenta;

import javax.xml.transform.Result;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

public class ControllerFactura implements Initializable {

    public JFXTextField efectivo;
    public JFXTextField cambio;
    public JFXTextField subtotal2;
    public JFXTextField sucursal;
    public JFXTextField noFactura1;
    public JFXTextField precio1;



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
    public Label error;
    private String idCl;
    private String CAI;
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

    private void recargarTablaFactura() {
        listaFactura.clear();
        try {
            ModelFactura.mostrarFactura(idCl, listaFactura);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getCAI() {
        try {
            ResultSet datoCAI = ModelAsignacionCAI.CAI();
            datoCAI.next();
            this.CAI = datoCAI.getString("CAI");
            return this.CAI;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertarCliente(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Administrador/Cliente/insertarCliente.fxml"));
        try {
            VBox page = (VBox) loader.load();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Nuevo Cliente");
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner((Stage) ((Button) actionEvent.getSource()).getScene().getWindow());
            primaryStage.setScene(new Scene(page, 720, 710));
            ControllerDetalleCliente controller = loader.getController();
            controller.setStage(primaryStage);


            primaryStage.showAndWait();
            primaryStage.setResizable(true);
            page = null;
            primaryStage = null;
            loader = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void nuevaVenta(ActionEvent actionEvent) {

        if (Double.parseDouble(cambio.getText()) >= 0) {


            ValidatorVenta venta = new ValidatorVenta(
                    Double.parseDouble(
                            precio1.getText()),
                    impuesto3,
                    Double.parseDouble(cambio.getText()),
                    Math.floor(Double.parseDouble(precio1.getText())*(1+impuesto3)),
                    Double.parseDouble(efectivo.getText())
            );


            try {
                if (ModelFactura.insertarFactura(venta, listaFactura, servicio1.valueProperty().get().getId(),
                        getCAI(), getIdCl(), Integer.parseInt(noFactura1.getText()))) {
                    imprimirFactura(noFactura1.getText(),actionEvent);
                    setNoFactura();
                    limpiarCampos();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Dialogo de Advertencia");
                    alert.setHeaderText("Advertencia");
                    alert.setContentText("El CAI ha Expirado");
                    alert.showAndWait();

                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialogo de Excepcion");
                alert.setHeaderText("Ha ocurrido un problema");
                alert.setContentText("La comuniacion con la base de datos ha fallado");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("The exception stacktrace was:");

                TextArea textArea = new TextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0, 1);


                alert.getDialogPane().setExpandableContent(expContent);

                alert.showAndWait();
                ex.printStackTrace();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dialogo de Advertencia");
            alert.setHeaderText("Advertencia");
            alert.setContentText("El efectivo no es suficiente");
            alert.showAndWait();


        }

    }
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
        nuevaScena(actionEvent,"Cajero/HomeCajero.fxml","Home");

    }

    public void imprimirFactura(String id,ActionEvent actionEvent) {
        Document documento = new Document();


        try {
            String noFactura;
            ResultSet dato = ModelFactura.imprimirFactura(id);
            dato.next();
            DirectoryChooser fileChooser = new DirectoryChooser();
            fileChooser.setTitle("Buscar Carpeta");
            Stage stage=new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) ((Button) actionEvent.getSource()).getScene().getWindow());
            stage.setResizable(false);
            File doc= fileChooser.showDialog(stage);
            FileOutputStream ficheroPdf = new FileOutputStream(doc.getAbsolutePath()+"/"+dato.getString("idVenta") + ".pdf");
            PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
            documento.open();
            Paragraph encabezado = new Paragraph("  GymTek" + "      \n" +
                    "Direccion:" + dato.getString("Sucursal_direccion") + "                              \n " +
                    "RTN:" + dato.getString("rtn") + "                                 \n" +
                    "Telefono:" + dato.getString("telefono") + "                            \n");
            encabezado.setAlignment(Element.ALIGN_CENTER);
            documento.add(encabezado);
            Paragraph info = new Paragraph("CAI:" + dato.getString("CAI") +
                    "                               \n" +
                    "Factura: " + dato.getString("idPuntoEmision") + "-01-" + dato.getString("idVenta") + "                        \n" +
                    "Cliente:" + dato.getString("idCliente") + "                         \n" +
                    "\n"+"Cajero: "+ControllerLogin.idUsuario+"\n"+"\n");
            documento.add(info);
            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Duracion(Meses)");
            tabla.addCell("Servicio");
            tabla.addCell("Impuesto");
            tabla.addCell("Precio");
            tabla.addCell("Total");
            tabla.addCell(dato.getInt("Servicio.duracion")+"");
            tabla.addCell(dato.getString("Servicio.nombre"));
            tabla.addCell(dato.getString("valor"));
            tabla.addCell(dato.getString("precio"));
            tabla.addCell(dato.getString("subtotal"));
            documento.add(tabla);
            Paragraph texto=new Paragraph("\n"+"Rango Autorizado: "+dato.getInt("idVenta")+"-"+dato.getInt("rangoOtorgado")+"\n"+"Fecha Limite :"+dato.getDate("fechaLimite"));
             documento.add(texto);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        documento.close();

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


        double precio = Math.round(Double.parseDouble(precio1.getText()));
        double efectivo1 = Math.round(Double.parseDouble(efectivo.getText()));
        double subtotal=Math.round(Double.parseDouble(subtotal2.getText()));
        double cambio1 = Math.round(efectivo1 - subtotal);

        if (cambio1 >= 0) {
            cambio.setText(""+cambio1);
            error.setText("");
        } else {
            cambio.setText(""+cambio1);
            error.setText("Efectivo no es Suficiente");
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

    private void setNoFactura() {
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
            double precioConImpuesto=Math.round(datoPrecio.getDouble("precio")*(datoPrecio.getDouble("valor")+1));
            impuesto3=datoPrecio.getDouble("valor");
            precio1.setText(""+datoPrecio.getDouble("precio"));
            subtotal2.setText("" + precioConImpuesto);
            datoPrecio.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void limpiarCampos() {

        cambio.setText("");
        efectivo.setText("");


    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listaPago = FXCollections.observableArrayList();
        listaServicio = FXCollections.observableArrayList();
        lista = FXCollections.observableArrayList();
        listaFactura = FXCollections.observableArrayList();
        //llenarListaPago();
        llenarListaServicio();
        setTablaCliente();



        servicio1.valueProperty().addListener(new ChangeListener<ModelMembresia>() {
            @Override
            public void changed(ObservableValue<? extends ModelMembresia> observable, ModelMembresia oldValue, ModelMembresia newValue) {
                if (newValue.getId() != null) {
                    setDatosPrecio(newValue.getId());
                    setNoFactura();
                }

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
                if(newValue.getId()!=null){
                    setIdCl(newValue.getId());
                    tablaFactura(getIdCl());

                }


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


    public void buscar(KeyEvent keyEvent) {
        tblCliente.getSelectionModel().clearSelection();
        lista.clear();
        ModelCliente.buscar(buscar.getText(), lista);


    }


    public void nuevoCliente(ActionEvent actionEvent) {
    }
}

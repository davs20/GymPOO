package sample.Model;


import javafx.beans.property.*;
import javafx.collections.ObservableList;

import sample.Controller.ControllerFactura;
import sample.Main;
import sample.Validator.ValidatorVenta;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class ModelFactura {


    private IntegerProperty noFactura;
    private DoubleProperty subtotal;
    private DoubleProperty impuesto;
    private DoubleProperty precio;
    private StringProperty servicio;
    private Date fechaCompra;
    private Date fechaVencimiento;
    private StringProperty idServicio;
    ;

    public String getIdServicio() {
        return idServicio.get();
    }

    public StringProperty idServicioProperty() {
        return idServicio;
    }

    public ModelFactura(Integer noFactura, Double subtotal, Double impuesto, Double precio, String servicio, Date fechaCompra, Date fechaVencimiento, String idServicio) {
        this.noFactura = new SimpleIntegerProperty(noFactura);
        this.subtotal = new SimpleDoubleProperty(subtotal);
        this.impuesto = new SimpleDoubleProperty(impuesto);
        this.precio = new SimpleDoubleProperty(precio);
        this.servicio = new SimpleStringProperty(servicio);
        this.fechaCompra = fechaCompra;
        this.fechaVencimiento = fechaVencimiento;
        this.idServicio = new SimpleStringProperty(idServicio);
    }

    public int getNoFactura() {
        return noFactura.get();
    }

    public IntegerProperty noFacturaProperty() {
        return noFactura;
    }


    public double getSubtotal() {
        return subtotal.get();
    }

    public DoubleProperty subtotalProperty() {
        return subtotal;
    }


    public double getImpuesto() {
        return impuesto.get();
    }

    public DoubleProperty impuestoProperty() {
        return impuesto;
    }


    public double getPrecio() {
        return precio.get();
    }

    public DoubleProperty precioProperty() {
        return precio;
    }


    public String getServicio() {
        return servicio.get();
    }

    public StringProperty servicioProperty() {
        return servicio;
    }


    public Date getFechaCompra() {
        return fechaCompra;
    }


    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }


    public static String getNoFactura2() throws SQLException {
        ResultSet dato = Main.comando.executeQuery("SELECT idVenta FROM Factura ORDER BY Factura.idVenta DESC LIMIT 1");

        dato.next();
      int noFactura=  dato.getInt("idVenta")+1;
      dato.close();
        return ""+noFactura;
    }


    public static void insertarFactura(ValidatorVenta datosValidados, String servicio1, String emision, String cliente,Integer noFactura) throws SQLException {


        ResultSet datosCai = Main.comando.executeQuery("SELECT  rangoOtorgado FROM AsignacionCai ORDER BY  fechaOtorgado DESC  LIMIT  1");
        datosCai.next();
        int rango=datosCai.getInt("rangoOtorgado");
        datosCai.close();

        if (noFactura <= rango) {
            Main.comando.execute("INSERT  INTO  Factura " +
                    "( Sucursal_idSucursal,  subtotal, cambio, efectivo, Cliente_idCliente, " +
                    " PuntoEmision_AsignacionCai_CAI,idServicio)  VALUES(" +
                    "1,'" + datosValidados.getSubtotal() + "','" + datosValidados.getCambio() + "','" + datosValidados.getEfectivo() + "','" + cliente + "','" + emision + "','" + servicio1 + "') ");
        }


    }

    public static void mostrarFactura(String id, ObservableList<ModelFactura> listaFactura) throws SQLException {
        ResultSet datosFactura = Main.comando.executeQuery("SELECT  * FROM  Factura INNER  JOIN Servicio ON Servicio.idMembresia=Factura.idServicio INNER join Impuesto on Impuesto.idImpuesto=Servicio.idImpuesto WHERE Cliente_idCliente='" + id + "'");
        while (datosFactura.next()) {
            listaFactura.add(new ModelFactura(
                    datosFactura.getInt("idVenta"),
                    datosFactura.getDouble("subtotal"),
                    datosFactura.getDouble("valor"),
                    datosFactura.getDouble("precio"),
                    datosFactura.getString("nombre"),
                    datosFactura.getDate("fecha"),
                    datosFactura.getDate("fecha"),
                    datosFactura.getString("idServicio")

            ));

        }
        datosFactura.close();
    }


    public static ResultSet setPrecio(String id) throws SQLException {

        ResultSet datoPrecio = Main.comando.executeQuery("SELECT  * from Servicio WHERE  idMembresia='" + id + "'");
        return datoPrecio;

    }


}

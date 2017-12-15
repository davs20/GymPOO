package sample.Model;


import javafx.beans.property.*;
import javafx.collections.ObservableList;

import javafx.scene.control.Alert;
import sample.Controller.ControllerFactura;
import sample.Main;
import sample.Validator.ValidatorVenta;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


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
        int noFactura = dato.getInt("idVenta") + 1;
        dato.close();
        return "" + noFactura;
    }


    public static Boolean insertarFactura(ValidatorVenta datosValidados, ObservableList<ModelFactura> listaFactura, String servicio1, String emision, String cliente, Integer noFactura) throws SQLException {


        ResultSet datosCai = Main.comando.executeQuery("SELECT  rangoOtorgado,CURRENT_DATE AS fechaActual,fechaLimite  FROM AsignacionCai ORDER BY  fechaLimite DESC  LIMIT  1");
        datosCai.next();

        int rango = datosCai.getInt("rangoOtorgado");
        if (noFactura <= rango && datosCai.getDate("fechaActual").before(datosCai.getDate("fechaLimite"))) {
            Main.comando.execute("INSERT  INTO  Factura " +
                    "( Sucursal_idSucursal,  subtotal, cambio, efectivo, Cliente_idCliente, " +
                    " PuntoEmision_AsignacionCai_CAI,idServicio)  VALUES(" +
                    "1,'" + datosValidados.getSubtotal() + "','" + datosValidados.getCambio() + "','" + datosValidados.getEfectivo() + "','" + cliente + "','" + emision + "','" + servicio1 + "') ");
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = new GregorianCalendar();
            datosCai.close();
            ResultSet fechaEx=Main.comando.executeQuery("SELECT DATE_ADD(NOW(), INTERVAL duracion MONTH) as fechaExperiacion,CURRENT_DATE AS fechaActual,Servicio.nombre FROM Servicio WHERE idMembresia='"+servicio1+"' ");
            fechaEx.next();
            listaFactura.add(new ModelFactura(
                    noFactura,
                    datosValidados.getSubtotal(),
                    datosValidados.getImpuesto(),
                    datosValidados.getPrecio(),
                    fechaEx.getString("nombre"),
                    fechaEx.getDate("fechaExperiacion"),
                    fechaEx.getDate("fechaActual"),
                    servicio1));
            fechaEx.close();

            return true;
        } else {
            return false;
        }


    }

    public static void mostrarFactura(String id, ObservableList<ModelFactura> listaFactura) throws SQLException {
        ResultSet datosFactura = Main.comando.executeQuery("SELECT  *,DATE_ADD(NOW(), INTERVAL Servicio.duracion MONTH) as fechaExpiracion FROM  Factura INNER  JOIN Servicio ON Servicio.idMembresia=Factura.idServicio INNER join Impuesto on Impuesto.idImpuesto=Servicio.idImpuesto WHERE Cliente_idCliente='" + id + "'");
        while (datosFactura.next()) {
            listaFactura.add(new ModelFactura(
                    datosFactura.getInt("idVenta"),
                    datosFactura.getDouble("subtotal"),
                    datosFactura.getDouble("valor"),
                    datosFactura.getDouble("precio"),
                    datosFactura.getString("nombre"),
                    datosFactura.getDate("fecha"),
                    datosFactura.getDate("fechaExpiracion"),
                    datosFactura.getString("idServicio")

            ));

        }
        datosFactura.close();
    }

    public static ResultSet imprimirFactura(String id) throws SQLException {
        ResultSet datoFactura = Main.comando.executeQuery("Select * FROM Factura INNER JOIN Servicio " +
                "ON  Servicio.idMembresia=Factura.idServicio INNER  JOIN Impuesto " +
                "ON Servicio.idImpuesto = Impuesto.idImpuesto INNER  JOIN Cliente " +
                "ON Factura.Cliente_idCliente = Cliente.idCliente INNER JOIN AsignacionCai " +
                "ON AsignacionCai.CAI=Factura.PuntoEmision_AsignacionCai_CAI INNER  JOIN PuntoEmision ON PuntoEmision.AsignacionCai_CAI=AsignacionCai.CAI WHERE idVenta='" + id + "' GROUP BY Cliente.idCliente ");
        return datoFactura;
    }

    public static String getCAI(String id) throws SQLException {
        ResultSet datoCAI = Main.comando.executeQuery("SELECT CAI FROM Factura WHERE idVenta='" + id + "'");
        return "";
    }

    public static ResultSet setPrecio(String id) throws SQLException {

        ResultSet datoPrecio = Main.comando.executeQuery("SELECT  * from Servicio WHERE  idMembresia='" + id + "'");
        return datoPrecio;

    }


}

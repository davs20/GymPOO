package sample.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import sample.Main;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ModelCliente {

    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty membresia;
    private StringProperty id;
    private Date fecha;
    private StringProperty telefono;
    private StringProperty correo;
    private StringProperty comentario;
    private DoubleProperty altura;
    private DoubleProperty peso;
    private DoubleProperty fuerza;

    public double getAltura() {
        return altura.get();
    }

    public DoubleProperty alturaProperty() {
        return altura;
    }

    public double getPeso() {
        return peso.get();
    }

    public DoubleProperty pesoProperty() {
        return peso;
    }

    public double getFuerza() {
        return fuerza.get();
    }

    public DoubleProperty fuerzaProperty() {
        return fuerza;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public String getCorreo() {
        return correo.get();
    }

    public StringProperty correoProperty() {
        return correo;
    }

    public String getComentario() {
        return comentario.get();
    }

    public StringProperty comentarioProperty() {
        return comentario;
    }


    public ModelCliente(String nombre, String apellido, String membresia, Date fecha, String id, String telefono, String correo, Double altura, Double fuerza, Double peso) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.membresia = new SimpleStringProperty(membresia);
        this.id = new SimpleStringProperty(id);
        this.fecha = fecha;
        this.telefono=new SimpleStringProperty(telefono);
        this.correo=new SimpleStringProperty(correo);
        this.altura=new SimpleDoubleProperty(altura);
        this.fuerza=new SimpleDoubleProperty(fuerza);
        this.peso=new SimpleDoubleProperty(peso);

    }


    public static void buscar(String parametro, ObservableList<ModelCliente> list) {

        /// hacer validacion con clase Validator
        try {
            ResultSet datosCliente = Main.comando.executeQuery("SELECT  Cliente.nombre,telefono,idCliente,apellido,edad,telefono,fechaNacimiento,correo,peso,fuerza,altura,Servicio.nombre as nomMem,descripcion,duracion " +
                    "FROM Cliente INNER  JOIN Factura On Cliente.idCliente = Factura.Cliente_idCliente INNER  JOIN Servicio" +
                    " ON Factura.idServicio = Servicio.idMembresia WHERE  Cliente.nombre LIKE '%" + parametro + "%' OR  Cliente.apellido LIKE '%" + parametro + "%' OR Cliente.idCliente LIKE '%" + parametro + "%'");

            while (datosCliente.next()) {
                list.add( new ModelCliente(
                        datosCliente.getString("nombre"),
                        datosCliente.getString("apellido"),
                        datosCliente.getString("nomMem"),
                        datosCliente.getDate("fechaNacimiento"),
                        datosCliente.getString("idCliente"),
                        datosCliente.getString("telefono"),
                        datosCliente.getString("correo"),
                        datosCliente.getDouble("peso"),
                        datosCliente.getDouble("fuerza"),
                        datosCliente.getDouble("altura")

                ));

            }

            datosCliente.close();
            datosCliente=null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ResultSet mostrarCliente(String id) {
        try {
            ResultSet datosCliente = Main.comando.executeQuery(
                    "SELECT * FROM  Cliente LEFT JOIN Enfermedades  ON Cliente.Enfermedades_idEnfermedades=Enfermedades.idEnfermedades " +
                            " INNER  JOIN Factura on Cliente.idCliente = Factura.Cliente_idCliente INNER  JOIN Servicio on Factura.idServicio = Servicio.idMembresia " +
                            "WHERE idCliente ='" + id + "'");
            return datosCliente;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static void listaMembresias(ObservableList listaMembresia) throws SQLException {
        ResultSet datosCliente = Main.comando.executeQuery("SELECT nombre,idMembresia FROM Servicio");
        while (datosCliente.next()) {
            listaMembresia.add(datosCliente.getString("nombre"));
        }
        datosCliente.close();

    }

    public static void mostrarTodos(ObservableList<ModelCliente> listaclientes) throws SQLException {
        ResultSet datosCliente = Main.comando.executeQuery("SELECT  Cliente.nombre,telefono,idCliente,apellido,edad,correo,telefono,fechaNacimiento,peso,altura,fuerza,Servicio.nombre AS nomMem,descripcion,duracion " +
                "FROM Cliente INNER JOIN Factura on Cliente.idCliente = Factura.Cliente_idCliente INNER JOIN Servicio" +
                " ON Factura.idServicio = Servicio.idMembresia GROUP BY Cliente.idCliente");


        while (datosCliente.next()) {

            listaclientes.add(new ModelCliente(
                    datosCliente.getString("nombre"),
                    datosCliente.getString("apellido"),
                    datosCliente.getString("nomMem"),
                    datosCliente.getDate("fechaNacimiento"),
                    datosCliente.getString("idCliente"),
                    datosCliente.getString("telefono"),
                    datosCliente.getString("correo"),
                    datosCliente.getDouble("peso"),
                    datosCliente.getDouble("fuerza"),
                    datosCliente.getDouble("altura")
            ));

        }
        datosCliente.close();

    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getApellido() {
        return apellido.get();
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public String getMembresia() {
        return membresia.get();
    }

    public StringProperty membresiaProperty() {
        return membresia;
    }


}



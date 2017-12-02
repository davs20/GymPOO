package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

import sample.Main;


import java.beans.EventHandler;
import java.io.IOException;
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


    public ModelCliente(String nombre, String apellido, String membresia, Date fecha, String id) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.membresia = new SimpleStringProperty(membresia);
        this.id = new SimpleStringProperty(id);
        this.fecha = fecha;


    }

    public static void buscar(String parametro, ObservableList<ModelCliente> list, TableView tblCliente) {

        /// hacer validacion con clase Validator
        try {
            ResultSet datosCliente = Main.comando.executeQuery("SELECT  Cliente.nombre,telefono,idCliente,apellido,edad,telefono,fechaNacimiento,Servicio.nombre as nomMem,descripcion,duracion " +
                    "FROM Cliente INNER  JOIN Servicio" +
                    " ON Cliente.Membresia_idMembresia = Servicio.idMembresia WHERE  Cliente.nombre LIKE '%" + parametro + "%' OR  Cliente.apellido LIKE '%" + parametro + "%' OR Cliente.idCliente LIKE '%" + parametro + "%'");

            while (datosCliente.next()) {
                ModelCliente cliente = new ModelCliente(
                        datosCliente.getString("nombre"),
                        datosCliente.getString("apellido"),
                        datosCliente.getString("nomMem"),
                        datosCliente.getDate("fechaNacimiento"),
                        datosCliente.getString("idCliente")

                );

                list.add(cliente);
                cliente = null;
                try {
                    cliente.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }


            }

            datosCliente.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ResultSet mostrarCliente(String id) {
        try {
            ResultSet datosCliente = Main.comando.executeQuery(
                    "SELECT * FROM  Cliente LEFT JOIN Enfermedades  ON Cliente.Enfermedades_idEnfermedades=Enfermedades.idEnfermedades " +
                            "INNER  JOIN Servicio on Cliente.Membresia_idMembresia = Servicio.idMembresia " +
                            "WHERE idCliente ='" + id + "'");
            return datosCliente;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void listaEnfermedades(ObservableList listaEnfermedades) throws SQLException {

        ResultSet datosCliente = Main.comando.executeQuery("SELECT nombre,idEnfermedades FROM Enfermedades");
        while (datosCliente.next()) {
            listaEnfermedades.add(datosCliente.getString("nombre"));
        }
        datosCliente.close();
    }

    public static void mostrarTodos(ObservableList<ModelCliente> listaclientes, TableView tblCliente) throws SQLException {
        ResultSet datosCliente = Main.comando.executeQuery("SELECT  Cliente.nombre,telefono,idCliente,apellido,edad,telefono,fechaNacimiento,Servicio.nombre AS nomMem,descripcion,duracion " +
                "FROM Cliente INNER JOIN Servicio" +
                " ON Cliente.Membresia_idMembresia = Servicio.idMembresia");


        while (datosCliente.next()) {
            ModelCliente cliente = new ModelCliente(datosCliente.getString("nombre"),
                    datosCliente.getString("apellido"),
                    datosCliente.getString("nomMem"),
                    datosCliente.getDate("fechaNacimiento"),
                    datosCliente.getString("idCliente")
            );

            listaclientes.add(cliente);

            try {
                cliente.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
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


package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import sample.Main;
import sample.config.Conexion;
import sample.config.OperacionesSQl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
        this.id=new SimpleStringProperty(id);
        this.fecha = fecha;

    }

    public static void mostrarTodos(ObservableList<ModelCliente> listaclientes) throws SQLException {
        ResultSet datosCliente = Main.comando.executeQuery("SELECT  Cliente.nombre,telefono,idCliente,apellido,edad,telefono,fechaNacimiento,Membresia.nombre as nomMem,descripcion,duracion " +
                "FROM Cliente INNER  JOIN Membresia" +
                " ON Cliente.Membresia_idMembresia = Membresia.idMembresia");
        while (datosCliente.next()) {
            listaclientes.add(
                    new ModelCliente(
                            datosCliente.getString("nombre"),
                            datosCliente.getString("apellido"),
                            datosCliente.getString("nomMem"),
                            datosCliente.getDate("fechaNacimiento"),
                            datosCliente.getString("idCliente")
                    )
            );
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


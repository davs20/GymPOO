package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import sample.config.Conexion;
import sample.config.OperacionesSQl;

import java.sql.SQLException;
import java.util.Date;

public class ModelCliente {
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty membresia;
    private StringProperty fecha;
    private StringProperty telefono;
    private StringProperty correo;

    public ModelCliente(String nombre, String apellido, String membresia, String fecha) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.membresia = new SimpleStringProperty(membresia);
        this.fecha = new SimpleStringProperty(fecha);

    }

    public static void mostrarTodos(ObservableList<ModelCliente> listaclientes) throws SQLException {
        Conexion cliente= OperacionesSQl.select("nombre_cliente,apellido_cliente","Cliente").get();

        while (cliente.registro.next()){
            listaclientes.add(new ModelCliente(
                    cliente.registro.getString("nombre_cliente"),
                    cliente.registro.getString("apellido_cliente"),
                    cliente.registro.getString("nombre_cliente"),
                    cliente.registro.getString("nombre_cliente")
                    ));
        }

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

    public String getFecha() {
        return fecha.get();
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

}


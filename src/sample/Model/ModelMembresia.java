package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelMembresia {

    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer id;

    public ModelMembresia(String nombre, String descripcion, Integer precio, Integer id) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id = id;
    }

    public static void listaMembresias(ObservableList listaMembresia) throws SQLException {
        ResultSet datosCliente = Main.comando.executeQuery("SELECT * FROM Servicio");
        while (datosCliente.next()) {
            listaMembresia.add(new ModelMembresia(
                    datosCliente.getString("nombre"),
                    datosCliente.getString("descripcion"),
                    datosCliente.getInt("precio"),
                    datosCliente.getInt("idMembresia")
            ));
        }
        datosCliente.close();

    }

    public static void mostrarMembresia(ObservableList<ModelMembresia> listaServicio, String id) throws SQLException {
        ResultSet datoServicio = Main.comando.executeQuery("select * FROM Servicio where idMembresia='" + id + "'");
        while (datoServicio.next()) {
            listaServicio.add(new ModelMembresia(
                    datoServicio.getString("nombre"),
                    datoServicio.getString("descripcion"),
                    datoServicio.getInt("precio"),
                    datoServicio.getInt("idMembresia")
            ));
        }
        datoServicio.close();

    }

    public static ResultSet mostrarMembresia(String id) throws SQLException {
        ResultSet datoServicio = Main.comando.executeQuery("SELECT * FROM Servicio WHERE idMembresia='" + id + "'");
        return datoServicio;
    }

    public String getNombre() {
        return nombre;
    }


    public String getId() {
        return "" + id;
    }


}

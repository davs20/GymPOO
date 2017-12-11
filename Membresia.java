package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by User on 12/10/2017.
 */
public class Membresia {

    public SimpleStringProperty nombreMembresia = new SimpleStringProperty();
    public SimpleStringProperty descripcionMembresia = new SimpleStringProperty();
    public SimpleDoubleProperty precioMembresia = new SimpleDoubleProperty();
    public SimpleIntegerProperty duracionMembresia = new SimpleIntegerProperty();

    public Membresia(String nombre, String descripcion, double precio, int duracion) {
        this.nombreMembresia = new SimpleStringProperty();
        this.descripcionMembresia = new SimpleStringProperty();
        this.precioMembresia = new SimpleDoubleProperty();
        this.duracionMembresia = new SimpleIntegerProperty();
    }


    public String getNombreMembresia(){
        return nombreMembresia.get();
    }

    public Double getPrecioMembresia(){
        return precioMembresia.get();
    }

    public String getDescripcionMembresia(){
        return descripcionMembresia.get();
    }

    public Integer getDuracionMembresia(){
        return duracionMembresia.get();
    }

    public static void llenarTablaMembresia(Connection connection, ObservableList<Membresia> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT m.nombre,m.descripcion,m.precio ,m.duracion FROM servicio as m");
            while(resultado.next()){
                lista.add(
                        new Membresia(
                                resultado.getString("m.nombre"),
                                resultado.getString("m.descripcion"),
                                resultado.getDouble("m.precio"),
                                resultado.getInt("m.duracion")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


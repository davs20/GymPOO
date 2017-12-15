package sample.Model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by User on 12/10/2017.
 */
public class Membresia {

    public StringProperty nombreMembresia = new SimpleStringProperty();
    public StringProperty descripcionMembresia = new SimpleStringProperty();
    public DoubleProperty precioMembresia = new SimpleDoubleProperty();
    public IntegerProperty duracionMembresia = new SimpleIntegerProperty();
    public IntegerProperty idMembresia = new SimpleIntegerProperty();

    public Membresia(Integer id, String nombre, String descripcion, double precio, int duracion) {
        this.idMembresia = new SimpleIntegerProperty(id);
        this.nombreMembresia = new SimpleStringProperty(nombre);
        this.descripcionMembresia = new SimpleStringProperty(descripcion);
        this.precioMembresia = new SimpleDoubleProperty(precio);
        this.duracionMembresia = new SimpleIntegerProperty(duracion);
    }


    public String getNombreMembresia() {
        return nombreMembresia.get();
    }

    public String getDescripcionMembresia() {
        return descripcionMembresia.get();
    }

    public Double getPrecioMembresia() {
        return precioMembresia.get();
    }

    public Integer getDuracionMembresia() {
        return duracionMembresia.get();
    }

    public Integer getidMembresia() {
        return idMembresia.get();
    }

    public static void llenarTablaMembresia(Connection connection, ObservableList<Membresia> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT idMembresia, nombre,descripcion,precio ,duracion FROM Servicio WHERE estado = 1 ");
            while (resultado.next()) {
                lista.add(
                        new Membresia(
                                resultado.getInt("idMembresia"),
                                resultado.getString("nombre"),
                                resultado.getString("descripcion"),
                                resultado.getDouble("precio"),
                                resultado.getInt("duracion")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int obtenerId(Connection connection, int id) {


        Statement instruccion = null;
        try {
            instruccion = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultado = null;
        try {
            resultado = instruccion.executeQuery("SELECT `idMembresia` FROM `Servicio` ORDER BY `idMembresia` DESC Limit 1");
            if (resultado.next()){
                id = resultado.getInt("idMembresia");}

            return  id;
        } catch (SQLException e) {
            e.printStackTrace();
        }return 0;

    }




    public int guardarMembresia(Connection connection) {
        try {
            PreparedStatement instruccion = // Evita el Inyeccion
                    connection.prepareStatement(
                            "INSERT INTO `Servicio`(`nombre`, `descripcion`, `duracion`, `precio`, `idImpuesto`,`estado`) " +
                                    "VALUES (?,?,?,?,(SELECT idImpuesto FROM impuesto ORDER BY idImpuesto DESC Limit 1),1)");

            //Los signos el motivo es parametrizarlos con los componentes de la interfaz grafica de usuarios
            instruccion.setString(1, nombreMembresia.get());
            instruccion.setString(2, descripcionMembresia.get());
            instruccion.setInt(3, duracionMembresia.get());
            instruccion.setDouble(4, precioMembresia.get());
            return instruccion.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;


    }


    public int actualizarRegistros(Connection connection) {
        try {
            PreparedStatement instruccion =
                    connection.prepareStatement(
                            "UPDATE `Servicio` " +
                                    "SET `nombre` = ?, " +
                                    "`descripcion` = ?, " +
                                    "`duracion` = ?, " +
                                    "`precio` = ? " +
                                    "WHERE `Servicio`.`idMembresia` = ?;"
                    );
            instruccion.setString(1, nombreMembresia.get());
            instruccion.setString(2, descripcionMembresia.get());
            instruccion.setInt(3, duracionMembresia.get());
            instruccion.setDouble(4, precioMembresia.get());
            instruccion.setInt(5, idMembresia.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int eliminarRegistro(Connection connection){
        try {
            PreparedStatement instruccion = connection.prepareStatement(
                    "UPDATE Servicio " +
                            "SET estado = ? " +
                            "WHERE idMembresia = ?"
            );
            instruccion.setInt(1, 0);
            instruccion.setInt(2,idMembresia.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}


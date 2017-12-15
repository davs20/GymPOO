package sample.Modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by User on 12/12/2017.
 */
public class Impuesto {


    public DoubleProperty valor = new SimpleDoubleProperty();


    public  Timestamp fechaModificacion;

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }
    public double getValor() {
        return valor.get();
    }

    public Impuesto(double valor, Timestamp fechaModificacion) {
        this.valor = new SimpleDoubleProperty(valor);
        this.fechaModificacion = fechaModificacion;
    }

    public static void llenarTablaImpuesto(Connection connection, ObservableList<Impuesto> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT  `valor`, `fechaModificacion` FROM `impuesto`");
            while (resultado.next()) {
                lista.add(
                        new Impuesto(
                                resultado.getDouble("valor"),
                                resultado.getTimestamp("fechaModificacion")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int actualizarRegistros(Connection connection) {
        try {
            PreparedStatement instruccion =
                    connection.prepareStatement(
                            "UPDATE `impuesto` " +
                                    "SET `valor` = ?," +
                                    "`fechaModificacion` = (SELECT NOW())"
                    );
            instruccion.setDouble(1, valor.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static Timestamp obtenerFecha(Connection connection, Timestamp fecha) {
        Statement instruccion = null;

        try {
            instruccion = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultado = null;
        try {
            resultado = instruccion.executeQuery("SELECT NOW() as fecha");
            if (resultado.next()){
                fecha = resultado.getTimestamp("fecha");}

            return  fecha;
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;


    }
}

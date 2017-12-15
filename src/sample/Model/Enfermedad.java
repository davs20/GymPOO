package sample.Model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by User on 12/12/2017.
 */
public class Enfermedad {
    public String getNombreEnfermedad() {
        return nombreEnfermedad.get();
    }

    public StringProperty nombreEnfermedadProperty() {
        return nombreEnfermedad;
    }

    public String getImpedimentoEnfermedad() {
        return impedimentoEnfermedad.get();
    }

    public StringProperty impedimentoEnfermedadProperty() {
        return impedimentoEnfermedad;
    }

    public String getCuidadoEnfermedad() {
        return cuidadoEnfermedad.get();
    }

    public StringProperty cuidadoEnfermedadProperty() {
        return cuidadoEnfermedad;
    }

    public int getIdEnfermedades() {
        return idEnfermedad.get();
    }

    public IntegerProperty idEnfermedadesProperty() {
        return idEnfermedad;
    }

    public StringProperty nombreEnfermedad = new SimpleStringProperty();
    public StringProperty impedimentoEnfermedad = new SimpleStringProperty();
    public StringProperty cuidadoEnfermedad = new SimpleStringProperty();
    public IntegerProperty idEnfermedad = new SimpleIntegerProperty();

    public Enfermedad(Integer id, String nombre, String impedimento, String cuidado) {
        this.idEnfermedad = new SimpleIntegerProperty(id);
        this.nombreEnfermedad = new SimpleStringProperty(nombre);
        this.impedimentoEnfermedad = new SimpleStringProperty(impedimento);
        this.cuidadoEnfermedad = new SimpleStringProperty(cuidado);

    }

    public static void llenarTablaEnfermedad(Connection connection, ObservableList<Enfermedad> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT `idEnfermedades`, `nombre`, `impedimento`, `cuidado` FROM `Enfermedades` WHERE  `estado`=1");
            while (resultado.next()) {
                lista.add(
                        new Enfermedad(
                                resultado.getInt("idEnfermedades"),
                                resultado.getString("nombre"),
                                resultado.getString("impedimento"),
                                resultado.getString("cuidado")
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
            resultado = instruccion.executeQuery("SELECT `idEnfermedades` FROM `Enfermedades` ORDER BY `idEnfermedades` DESC Limit 1");
            if (resultado.next()){
                id = resultado.getInt("idEnfermedades");}

            return  id;
        } catch (SQLException e) {
            e.printStackTrace();
        }return 0;

    }

    public int guardarEnfermedad(Connection connection) {
        try {
            PreparedStatement instruccion = // Evita el Inyeccion
                    connection.prepareStatement(
                            "INSERT INTO `Enfermedades` (`nombre`, `impedimento`, `cuidado`) " +
                                    "VALUES ( ?, ?, ?)");

            //Los signos el motivo es parametrizarlos con los componentes de la interfaz grafica de usuarios
            instruccion.setString(1, nombreEnfermedad.get());
            instruccion.setString(2, impedimentoEnfermedad.get());
            instruccion.setString(3, cuidadoEnfermedad.get());
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
                            "UPDATE `Enfermedades` " +
                                    "SET `nombre` = ?, " +
                                    "`impedimento` = ?, " +
                                    "`cuidado` = ? " +
                                    "WHERE `idEnfermedades` = ?;"
                    );
            instruccion.setString(1, nombreEnfermedad.get());
            instruccion.setString(2, impedimentoEnfermedad.get());
            instruccion.setString(3, cuidadoEnfermedad.get());
            instruccion.setInt(4, idEnfermedad.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public int eliminarRegistro(Connection connection){
        try {
            PreparedStatement instruccion = connection.prepareStatement(
                    "UPDATE Enfermedades " +
                            "SET estado = ? " +
                            "WHERE idEnfermedades = ?"
            );
            instruccion.setInt(1, 0);
            instruccion.setInt(2,idEnfermedad.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}

package sample.Modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by User on 12/14/2017.
 */
public class ClienteSeguimiento {
    public String getIdClienteSeguimiento() {
        return idClienteSeguimiento.get();
    }

    public StringProperty idClienteSeguimientoProperty() {
        return idClienteSeguimiento;
    }

    public String getNombreClienteSeguimiento() {
        return nombreClienteSeguimiento.get();
    }

    public StringProperty nombreClienteSeguimientoProperty() {
        return nombreClienteSeguimiento;
    }

    public String getApellidoClienteSeguimiento() {
        return apellidoClienteSeguimiento.get();
    }

    public StringProperty apellidoClienteSeguimientoProperty() {
        return apellidoClienteSeguimiento;
    }

    public ClienteSeguimiento(String idClienteSeguimiento, String nombreClienteSeguimiento, String apellidoClienteSeguimiento) {
        this.idClienteSeguimiento = new SimpleStringProperty(idClienteSeguimiento);
        this.nombreClienteSeguimiento = new SimpleStringProperty(nombreClienteSeguimiento);
        this.apellidoClienteSeguimiento = new SimpleStringProperty(apellidoClienteSeguimiento);
    }

    public StringProperty idClienteSeguimiento = new SimpleStringProperty();
    public StringProperty nombreClienteSeguimiento = new SimpleStringProperty();
    public StringProperty apellidoClienteSeguimiento = new SimpleStringProperty();

    public static void llenarTabla(Connection connection, ObservableList<ClienteSeguimiento> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT `idCliente`, `nombre`, `apellido` FROM `cliente`");
            while (resultado.next()) {
                lista.add(
                        new ClienteSeguimiento(
                                resultado.getString("idCliente"),
                                resultado.getString("nombre"),
                                resultado.getString("apellido")

                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String toString(){
        return nombreClienteSeguimiento.get();
    }
}

package sample.Modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by User on 12/13/2017.
 */
public class Privilegio {
    public int getIdPrivilegio() {
        return idPrivilegio.get();
    }

    public IntegerProperty idPrivilegioProperty() {
        return idPrivilegio;
    }

    public String getNombrePrivilegio() {
        return nombrePrivilegio.get();
    }

    public StringProperty nombrePrivilegioProperty() {
        return nombrePrivilegio;
    }

    //Constructor Clase Privilegio
    public Privilegio(Integer idPrivilegio, String nombrePrivilegio) {

        this.idPrivilegio = new SimpleIntegerProperty(idPrivilegio);
        this.nombrePrivilegio = new SimpleStringProperty(nombrePrivilegio);

    }

    public IntegerProperty idPrivilegio = new SimpleIntegerProperty();
    public StringProperty nombrePrivilegio = new SimpleStringProperty();


    public static void llenarInformacion(Connection connection, ObservableList<Privilegio> lista) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT `idPrivilegio`, " +
                            "`privilegiocol` " +
                            "FROM `privilegio`"
            );
            while (resultado.next()) {
                lista.add(
                        new Privilegio(
                                resultado.getInt("idPrivilegio"),
                                resultado.getString("privilegiocol")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String toString(){
        return nombrePrivilegio.get();
    }
}

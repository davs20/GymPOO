package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by User on 12/13/2017.
 */
public class Usuario {

    public String getIdUsuario() {
        return idUsuario.get();
    }

    public StringProperty idUsuarioProperty() {
        return idUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario.get();
    }

    public StringProperty contrasenaUsuarioProperty() {
        return contrasenaUsuario;
    }

    public String getNombresUsuario() {
        return nombresUsuario.get();
    }

    public StringProperty nombresUsuarioProperty() {
        return nombresUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario.get();
    }

    public StringProperty apellidosUsuarioProperty() {
        return apellidosUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario.get();
    }

    public StringProperty correoUsuarioProperty() {
        return correoUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario.get();
    }

    public StringProperty telefonoUsuarioProperty() {
        return telefonoUsuario;
    }

    public StringProperty idUsuario = new SimpleStringProperty();
    public StringProperty contrasenaUsuario = new SimpleStringProperty();
    public StringProperty nombresUsuario = new SimpleStringProperty();
    public StringProperty apellidosUsuario = new SimpleStringProperty();
    public StringProperty correoUsuario = new SimpleStringProperty();
    public StringProperty telefonoUsuario = new SimpleStringProperty();

    public Privilegio getPrivilegio() {
        return privilegio;
    }

    public Privilegio privilegio;

    //Constructor Usuario utilizado para crear objetos de la clase Usuario
    public Usuario(String id,String contrasena,String nombres, String apellidos, String correo, String telefono, Privilegio privilegio){
        this.idUsuario = new SimpleStringProperty(id);
        this.contrasenaUsuario = new SimpleStringProperty(contrasena);
        this.nombresUsuario = new SimpleStringProperty(nombres);
        this.apellidosUsuario = new SimpleStringProperty(apellidos);
        this.correoUsuario = new SimpleStringProperty(correo);
        this.telefonoUsuario = new SimpleStringProperty(telefono);
        this.privilegio = privilegio;
    }

    public static void llenarTabla(Connection connection, ObservableList<Usuario> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT idUsuarios,contrasena, nombres,apellidos,correo ,telefono, idPrivilegio, privilegiocol " +
                            "FROM Usuarios as u,privilegio as p " +
                            "WHERE u.privilegio_idPrivilegio=p.idPrivilegio AND u.estado = 1");
            while (resultado.next()) {
                lista.add(
                        new Usuario(
                                resultado.getString("idUsuarios"),
                                resultado.getString("contrasena"),
                                resultado.getString("nombres"),
                                resultado.getString("apellidos"),
                                resultado.getString("correo"),
                                resultado.getString("telefono"),
                                new Privilegio(
                                        resultado.getInt("idPrivilegio"),
                                        resultado.getString("privilegiocol")
                                )
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int guardarRegistro(Connection connection){
        try {
            //Evitar inyeccion SQL.
            PreparedStatement instruccion =
                    connection.prepareStatement("INSERT INTO `Usuarios` (`idUsuarios`, `contrasena`, `nombres`, `apellidos`, `correo`, `telefono`, `privilegio_idPrivilegio`) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?);");
            instruccion.setString(1, idUsuario.get());
            instruccion.setString(2, contrasenaUsuario.get());
            instruccion.setString(3, nombresUsuario.get());
            instruccion.setString(4, apellidosUsuario.get());
            instruccion.setString(5, correoUsuario.get());
            instruccion.setString(6, telefonoUsuario.get());
            instruccion.setInt(7, privilegio.getIdPrivilegio());

            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int actualizarRegistros(Connection connection) {
        try {
            PreparedStatement instruccion =
                    connection.prepareStatement(
                            "UPDATE `Usuarios` " +
                                    "SET `idUsuarios` = ?, " +
                                    "`contrasena` = ?, " +
                                    "`nombres` = ?, " +
                                    "`apellidos` = ?, " +
                                    "`correo` = ?, " +
                                    "`telefono` = ?, " +
                                    "`privilegio_idPrivilegio` = ? " +
                                    "WHERE `idUsuarios` = ?;"
                    );
            instruccion.setString(1, idUsuario.get());
            instruccion.setString(2, contrasenaUsuario.get());
            instruccion.setString(3, nombresUsuario.get());
            instruccion.setString(4, apellidosUsuario.get());
            instruccion.setString(5, correoUsuario.get());
            instruccion.setString(6, telefonoUsuario.get());
            instruccion.setInt(7, privilegio.getIdPrivilegio());
            instruccion.setString(8, idUsuario.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int eliminarRegistro(Connection connection){
        try {
            PreparedStatement instruccion = connection.prepareStatement(
                    "UPDATE Usuarios " +
                            "SET estado = ? " +
                            "WHERE idUsuarios = ?"
            );
            instruccion.setInt(1, 0);
            instruccion.setString(2,idUsuario.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }



}

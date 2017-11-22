package sample.Model;

import sample.Main;
import sample.Validator.ValidatorLogin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelUsuario {
    private String usuario;
    private String contrasen;
    private String nombre;
    private String apellido;
    private int intentos;
    private String telefono;
    private String correo;
    private String estado;
    private String id;

    public ModelUsuario(String text, String text1) {
        this.usuario = text;
        this.contrasen = text1;

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean validarCredenciales(ValidatorLogin login) {

        if (login.usuario(this.usuario).equals("Correcto") && login.contrasena(this.contrasen).equals("Correcto")) {

            try {
                Boolean loginCajero = intentoCajero();

                if (loginCajero) return loginCajero;
                Boolean loginGerente = intentoGerente();
                if (loginGerente) return loginGerente;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }

    private Boolean intentoGerente() throws SQLException {
        ResultSet datoGerente = Main.comando.executeQuery("SELECT COUNT(*) as resultado,intentos,idGerente,nombre,apellido,idGerente FROM Gerente " +
                "WHERE  idGerente='" + this.usuario + "' AND contrasena='" + this.contrasen + "'");
        datoGerente.next();
        if (datoGerente.getInt("resultado") == 1) {
            setIntentos(datoGerente.getInt("intentos"));
            setNombre(datoGerente.getString("nombre"));
            setApellido(datoGerente.getString("apellido"));
            setId(datoGerente.getString("idGerente"));
            datoGerente.close();
            return true;
        }
        Main.comando.execute("UPDATE Gerente SET intentos=(1+Gerente.intentos) WHERE idGerente='" + this.usuario + "'");
        return false;

    }


    private Boolean intentoCajero() throws SQLException {

        ResultSet datoCajero = Main.comando.executeQuery("SELECT COUNT(*) as resultado,intentos,idCajeto,nombre FROM Cajero " +
                "WHERE  idCajeto='" + this.usuario + "' AND contrasena='" + this.contrasen + "'");
            datoCajero.first();
        if (datoCajero.getInt("resultado")==1) {
            setCorreo(datoCajero.getString("correo"));
            setNombre(datoCajero.getString("nombre"));
            datoCajero.close();
            return true;
        }

        Main.comando.execute("UPDATE Cajero SET intentos=(1+Cajero.intentos) WHERE idCajeto='" + this.usuario + "'");

        return false;

    }


    private ResultSet intentoEntrenador() throws SQLException {

        ResultSet dato12 = Main.comando.executeQuery("SELECT COUNT(*) as resultado,intentos,idCajeto,nombre,apellido FROM Entrenador " +
                "WHERE  idEntrenador='" + this.usuario + "' AND contrasena='" + this.contrasen + "'");
        if (dato12.getInt("intentos") == 1) {

            return dato12;
        } else {

        }

        return dato12;
    }


}

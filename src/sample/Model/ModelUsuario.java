package sample.Model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.Controller.ControllerUsuarios;
import sample.Main;
import sample.Validator.ValidatorLogin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelUsuario {
    private String contrasen;
    private String usuario;
    private String token;
    private String nombre;
    private String apellido;
    private int intentos;
    private String telefono;
    private String correo;
    private String estado;
    private String id;
    private String idPrivilegio;

    public ModelUsuario(String usuario, String token, String nombres, String apellidos, String correo, String telefono, String idPrivilegio) {
        this.usuario = usuario;
        this.token = token;
        this.nombre = nombres;
        this.correo = correo;
        this.apellido = apellidos;
        this.telefono = telefono;
        this.idPrivilegio = idPrivilegio;

    }

    public String getContrasen() {
        return contrasen;
    }

    public void setContrasen(String contrasen) {
        this.contrasen = contrasen;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getIdPrivilegio() {
        return idPrivilegio;
    }

    public void setIdPrivilegio(String idPrivilegio) {
        this.idPrivilegio = idPrivilegio;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }


    public static ModelUsuario validarCredenciales(ValidatorLogin login, String user, String pass) {

        if (login.usuario(user).equals("Correcto") && login.contrasena(pass).equals("Correcto")) {
            ResultSet datoUsuario = null;
            try {
                datoUsuario = Main.comando.executeQuery("SELECT COUNT(*) as resultado,intentos,idUsuarios,nombres,apellidos,correo,telefono,privilegio.idPrivilegio,privilegio.privilegiocol FROM  " +
                        "Usuarios INNER  JOIN privilegio on privilegio.idPrivilegio=Usuarios.privilegio_idPrivilegio WHERE  idUsuarios='" + user + "' AND contrasena='" + pass + "'");
                datoUsuario.next();
                if (datoUsuario.getInt("resultado") == 1 && datoUsuario.getInt("intentos") < 10) {

                    ModelUsuario usuario = new ModelUsuario(
                            datoUsuario.getString("idUsuarios"),
                            datoUsuario.getString("apellidos"),
                            datoUsuario.getString("nombres"),
                            datoUsuario.getString("apellidos"),
                            datoUsuario.getString("correo"),
                            datoUsuario.getString("telefono"),
                            datoUsuario.getString("idPrivilegio")
                    );
                    return usuario;
                }
                Main.comando.execute("UPDATE Usuarios SET intentos=(1+Usuarios.intentos) WHERE idUsuarios='" + user + "'");
                return null;
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialogo de Error");
                alert.setHeaderText("Ha ocurrido error");
                alert.setContentText("Fue imposible  conectarse ala base de datos ");
                alert.showAndWait();
            }

        }

        return null;
    }


    public static void mostrarDatosUsuarioCajero(ObservableList listaUsuarios) throws SQLException {

        ResultSet datoUsuario= Main.comando.executeQuery("SELECT  * FROM Usuarios where privilegio_idPrivilegio=2");
        while (datoUsuario.next()){
            listaUsuarios.add(
                    new ModelUsuario(
                    datoUsuario.getString("idUsuarios"),
                    datoUsuario.getString("apellidos"),
                    datoUsuario.getString("nombres"),
                    datoUsuario.getString("apellidos"),
                    datoUsuario.getString("correo"),
                    datoUsuario.getString("telefono"),
                    datoUsuario.getString("privilegio_idPrivilegio")

            ));

        }
    }

}

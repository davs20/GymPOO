package sample.Model;

import sample.Main;
import sample.Validator.ValidatorAsistencia;

import java.sql.SQLException;

public class ModelAsistencia {

    public static void confirmarAsistencia(String idCliente,int dias,String idUsuario) throws SQLException {


        Main.comando.execute("INSERT into Asistencia (Cliente_idCliente, diasRestantes, Usuarios_idUsuarios) " +
                "VALUES('"+idCliente+"','"+dias+"','"+idUsuario+"')");

    }
}

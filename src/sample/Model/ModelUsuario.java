package sample.Model;

import javafx.collections.ObservableList;
import sample.Validator.ValidatorLogin;
import sample.config.OperacionesSQl;

import java.sql.SQLException;

public class ModelUsuario {



    public void intentarLogin(String usuario, String contrasena, ValidatorLogin login) {
        if (login.usuario(usuario).equals("Correcto")) {
            consultarCredenciales();
        }

    }


    private void consultarCredenciales() {
        try {
            OperacionesSQl.select("idUsuario", "Entrenador").get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

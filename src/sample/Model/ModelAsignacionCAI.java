package sample.Model;

import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelAsignacionCAI {
    private  String G;



    public static ResultSet CAI() throws SQLException {
        ResultSet datoCAI= Main.comando.executeQuery("SELECT * FROM AsignacionCai ORDER BY fechaOtorgado DESC LIMIT 1");
        return  datoCAI;
    }


}

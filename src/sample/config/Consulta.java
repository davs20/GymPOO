package sample.config;

import java.sql.SQLException;
import java.util.ArrayList;

public class Consulta  {




    public static ArrayList get(ArrayList<String> cadenas, String consulta) throws SQLException {
        consulta = "";
        ArrayList resultado=new ArrayList();
        for (int i = 0; i < cadenas.size(); i++) {
            consulta.concat(cadenas.get(i));
        }
        Conexion db = new Conexion();
        db.MySQLConnect();
        db.comando = db.conexion.createStatement();
        db.registro = db.comando.executeQuery(consulta);
        int cont = 0;
        while (db.registro.next()) {
            for (int p = 0; p < 1; p++) {

            }

        }
        return resultado;




    }

}

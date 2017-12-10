package sample.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion extends  Configuracion {
   public Connection conexion = null;
   public Statement comando = null;
   public ResultSet registro;

    public Connection MySQLConnect()  {
        try {
            Class.forName(DbDriver);
            String servidor = "jdbc:mysql://localhost:3306/Gym";
            String usuario = "root";
            String pass = "";
            conexion = DriverManager.getConnection(servidor, usuario, pass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;

        } finally {
            //System.out.printf("Conexion exitosa");
            return conexion;
        }

    }



}

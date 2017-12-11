package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by User on 12/11/2017.
 */
public class ConexionDB {

    private Connection connection;
    private static final String CONNECTION_STRING="jdbc:mysql://127.0.0.1:3306/gymtek";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";
    private static Connection conexion;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void establecerConexion(){
        //Referencia al Driver | Cargar el Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING,USUARIO,CLAVE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cerrarConexion(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

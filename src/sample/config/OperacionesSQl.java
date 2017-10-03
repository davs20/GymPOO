package sample.config;

import java.sql.SQLException;
import java.util.ArrayList;


public class OperacionesSQl extends Conexion {
    private  String select;
    private static String where;
    private static String and;
    private static String update;
    private static String insert;
    private String like;
    private String join;
    private String joinleft;
    private String joinright;
    private static String consulta="";


    public void update(ArrayList<String> columnas, String tabla, ArrayList<String> valor) {

    }


    public void desabilitar() {

    }

    public static OperacionesSQl select(String columnas, String tabla) {
        consulta+="select " + columnas + " from " + tabla;
        return new OperacionesSQl();
    }



    public OperacionesSQl insert(String columnas, String tabla, ArrayList<String> valor) {
        insert = "insert into " + tabla;
        return new OperacionesSQl();
    }

    public static OperacionesSQl join() {
        return new OperacionesSQl();
    }

    public OperacionesSQl joinleft() {
        return new OperacionesSQl();
    }

    public OperacionesSQl joinright() {
        return new OperacionesSQl();
    }


    public  OperacionesSQl where(String columna, String operador, String valor) {
        consulta += " where "+columna + " " + operador + " " + "\""+valor+"\"";
        return new OperacionesSQl();
    }

    public static OperacionesSQl Owhere(String columna,String operador,String valor){
        where+=" "+columna+" "+operador+" "+valor;
        return new OperacionesSQl();
    }

    public String and(String columna, String operador, String valor) {
        String and = "and " + columna + operador + valor;
        return and;
    }

    public String habilitar(String columnas, String tabla, String where) {
        return "Update into ";

    }

    public Conexion get() throws SQLException {
     Conexion conexion =new Conexion();
     conexion.MySQLConnect();
        conexion.comando = conexion.conexion.createStatement();
        conexion.registro = conexion.comando.executeQuery(consulta);
        consulta="";
        return conexion;
    }



}
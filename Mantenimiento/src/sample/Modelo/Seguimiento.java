package sample.Modelo;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by User on 12/14/2017.
 */
public class Seguimiento {
    public double getPesoSeguimiento() {
        return pesoSeguimiento.get();
    }

    public DoubleProperty pesoSeguimientoProperty() {
        return pesoSeguimiento;
    }

    public double getGrasaSeguimiento() {
        return grasaSeguimiento.get();
    }

    public DoubleProperty grasaSeguimientoProperty() {
        return grasaSeguimiento;
    }

    public double getAlturaSeguimiento() {
        return alturaSeguimiento.get();
    }

    public DoubleProperty alturaSeguimientoProperty() {
        return alturaSeguimiento;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public Seguimiento(Integer idSeguimiento, Double pesoSeguimiento, Double grasaSeguimiento, Double alturaSeguimiento, Timestamp fechaModificacion, ClienteSeguimiento idClienteSeguimiento) {
        this.idSeguimiento = new SimpleIntegerProperty(idSeguimiento);
        this.pesoSeguimiento = new SimpleDoubleProperty(pesoSeguimiento);
        this.grasaSeguimiento = new SimpleDoubleProperty(grasaSeguimiento);
        this.alturaSeguimiento = new SimpleDoubleProperty(alturaSeguimiento);
        this.fechaModificacion = fechaModificacion;
        this.idClienteSeguimiento=idClienteSeguimiento;
    }

    public int getIdSeguimiento() {
        return idSeguimiento.get();
    }

    public IntegerProperty idSeguimientoProperty() {
        return idSeguimiento;
    }

    public IntegerProperty idSeguimiento = new SimpleIntegerProperty();
    public  DoubleProperty pesoSeguimiento = new SimpleDoubleProperty();
    public  DoubleProperty grasaSeguimiento = new SimpleDoubleProperty();
    public  DoubleProperty alturaSeguimiento = new SimpleDoubleProperty();
    public Timestamp fechaModificacion;

    public ClienteSeguimiento getClienteSeguimiento() {
        return idClienteSeguimiento;
    }

    public ClienteSeguimiento getNombreClienteSeguimiento() {
        return nombreClienteSeguimiento;
    }

    public ClienteSeguimiento nombreClienteSeguimiento;
    public ClienteSeguimiento idClienteSeguimiento;

    public static Timestamp obtenerFecha(Connection connection, Timestamp fecha) {
        Statement instruccion = null;

        try {
            instruccion = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultado = null;
        try {
            resultado = instruccion.executeQuery("SELECT NOW() as fecha");
            if (resultado.next()){
                fecha = resultado.getTimestamp("fecha");}

            return  fecha;
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;


    }


       public static void llenarinformacion(Connection connection, ObservableList<Seguimiento> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT seguimiento.idSeguimiento,seguimiento.peso, porcentajeGrasa, seguimiento.altura, fecha \n" +
                            "FROM seguimiento ,cliente \n" +
                            "WHERE seguimiento.Cliente_idCliente='0101199602775' AND cliente.estado=1 ");
            while (resultado.next()) {
                lista.add(
                        new Seguimiento(
                                resultado.getInt("seguimiento.idSeguimiento"),
                                resultado.getDouble("seguimiento.peso"),
                                resultado.getDouble("porcentajeGrasa"),
                                resultado.getDouble("seguimiento.altura"),
                                resultado.getTimestamp("fecha"),
                                new ClienteSeguimiento(
                                        resultado.getString("idCliente"),
                                        resultado.getString("nombre"),
                                        resultado.getString("apellido")
                                )
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static int obtenerId(Connection connection, int id) {
//
//
//        Statement instruccion = null;
//        try {
//            instruccion = connection.createStatement();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        ResultSet resultado = null;
//        try {
//            resultado = instruccion.executeQuery("SELECT `idMembresia` FROM `servicio` ORDER BY `idMembresia` DESC Limit 1");
//            if (resultado.next()){
//                id = resultado.getInt("idMembresia");}
//
//            return  id;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }return 0;
//
//    }




    public int guardarRegistro(Connection connection){
        try {
            //Evitar inyeccion SQL.
            PreparedStatement instruccion =
                    connection.prepareStatement("INSERT INTO `seguimiento`(`idSeguimiento`, `Cliente_idCliente`, `peso`, `porcentajeGrasa`, `altura`, `Usuarios_idUsuarios`) " +
                            "VALUES (0,?,?,?,?,?);");
            instruccion.setString(1, idClienteSeguimiento.getIdClienteSeguimiento());
            instruccion.setDouble(2, pesoSeguimiento.get());
            instruccion.setDouble(3, grasaSeguimiento.get());
            instruccion.setDouble(4, alturaSeguimiento.get());
            //instruccion.setString(5, .get());------------------------------------------AQUI VA TU CODIGO DAVID---------------

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
                            "UPDATE `seguimiento` " +
                                    "SET `Cliente_idCliente`=?," +
                                    "`peso`=?,`porcentajeGrasa`=?," +
                                    "`altura`=?," +
                                    "`Usuarios_idUsuarios`=?," +
                                    "`fecha`=(SELECT NOW()" +
                                    " WHERE seguimiento.idSeguimiento=?;"
                    );
            instruccion.setString(1, idClienteSeguimiento.getIdClienteSeguimiento());
            instruccion.setDouble(2, grasaSeguimiento.get());
            instruccion.setDouble(3, alturaSeguimiento.get());
            //instruccion.setDouble(4, precioMembresia.get());----------------------------------------------AQUI VA TU CODIGO DAVID-----------------------------------------
            instruccion.setInt(5, idSeguimiento.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

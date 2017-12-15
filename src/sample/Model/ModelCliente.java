package sample.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import sample.Main;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class ModelCliente {

    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty membresia;
    private StringProperty id;
    private Date fecha;
    private StringProperty telefono;
    private StringProperty correo;
    private StringProperty comentario;
    private DoubleProperty altura;
    private DoubleProperty peso;
    private DoubleProperty fuerza;
    private StringProperty estado;

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public double getAltura() {
        return altura.get();
    }

    public DoubleProperty alturaProperty() {
        return altura;
    }

    public double getPeso() {
        return peso.get();
    }

    public DoubleProperty pesoProperty() {
        return peso;
    }

    public double getFuerza() {
        return fuerza.get();
    }

    public DoubleProperty fuerzaProperty() {
        return fuerza;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public String getCorreo() {
        return correo.get();
    }

    public StringProperty correoProperty() {
        return correo;
    }

    public String getComentario() {
        return comentario.get();
    }

    public StringProperty comentarioProperty() {
        return comentario;
    }


    public ModelCliente(String nombre, String apellido, String membresia, Date fecha, String id, String telefono, String correo, Double altura, Double fuerza, Double peso,String estado) {

        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.membresia = new SimpleStringProperty(membresia);
        this.id = new SimpleStringProperty(id);
        this.fecha = fecha;
        this.telefono=new SimpleStringProperty(telefono);
        this.correo=new SimpleStringProperty(correo);
        this.altura=new SimpleDoubleProperty(altura);
        this.fuerza=new SimpleDoubleProperty(fuerza);
        this.peso=new SimpleDoubleProperty(peso);
        this.estado=new SimpleStringProperty(estado);
    }


    public static  void updateCliente(String id,String telefono,String correo,String comentarios,Date fecha,String padecimiento,String apellido,String foto,String altura,String nombre,String membresias,int edad,String peso,String fuerza) throws SQLException {

     Main.comando.execute("UPDATE  Cliente SET telefono='"+telefono+"',correo='"+correo+"',fechaNacimiento='"+fecha+"',Enfermedades_idEnfermedades='"+padecimiento+"',nombre='"+nombre+"',Membresia_idMembresia='"+membresias+"',edad='"+edad+"',fuerza='"+fuerza+"',foto='"+foto+"'  WHERE idCliente='"+id+"'");

    }

    public void insertarCliente(ObservableList<ModelCliente> listaCliente){


    }

    public static  void insertarFoto(String id,String telefono,String correo,String comentarios,Date fecha,String padecimiento,String apellido,String foto,String altura,String nombre,String membresias,int edad,String peso,String fuerza) throws SQLException {
        Main.comando.execute("INSERT INTO `Cliente` (`idCliente`, `fechaNacimiento`, `nombre`, `apellido`, " +
                "`telefono`, `Membresia_idMembresia`, `Clientecol`, `edad`, `Enfermedades_idEnfermedades`, `peso`, " +
                "`fuerza`, `masaMuscular`, `correo`, `direccion`, `altura`, `foto`) VALUES ('" + id + "'," +
                " '" + fecha + "', '" + nombre + "', '" + apellido + "', '"
                + telefono + "'," + " '"+membresias+"', NULL, '"+edad+"', '"+padecimiento+"', '" + peso + "', '" + fuerza
                + "',NULL, '" + correo + "'" +
                ", '"+comentarios+"', '" + altura + "'," + " '"+foto+"')");
    }

    public static  void updateFoto(String foto,String id) throws SQLException {
        Main.comando.execute("UPDATE  Cliente SET foto='"+foto+"' WHERE Cliente.idCliente='"+id+"'");
    }


    public static  void desabilitar(String id) throws SQLException {
        Main.comando.execute("UPDATE Cliente set estado=0 WHERE idCliente='"+id+"'");

    }
    public static void habilitar(String id ) throws SQLException {
        Main.comando.execute("UPDATE  Cliente SET  estado=1 WHERE idCliente='"+id+"'");
    }
    public static void buscar(String parametro, ObservableList<ModelCliente> list) {

        /// hacer validacion con clase Validator
        try {
            ResultSet datosCliente = Main.comando.executeQuery("SELECT  Cliente.nombre,IF(estado=1,'Habilitado','Desabilitado') as estado,telefono,idCliente,apellido,edad,telefono,fechaNacimiento,correo,peso,fuerza,altura,Servicio.nombre as nomMem,descripcion,duracion " +
                    "FROM Cliente LEFT  JOIN Factura On Cliente.idCliente = Factura.Cliente_idCliente INNER  JOIN Servicio" +
                    " ON Factura.idServicio = Servicio.idMembresia WHERE  Cliente.nombre LIKE '%" + parametro + "%' OR  Cliente.apellido LIKE '%" + parametro + "%' OR Cliente.idCliente LIKE '%" + parametro + "%' GROUP BY Cliente.idCliente");

            while (datosCliente.next()) {
                list.add( new ModelCliente(
                        datosCliente.getString("nombre"),
                        datosCliente.getString("apellido"),
                        datosCliente.getString("nomMem"),
                        datosCliente.getDate("fechaNacimiento"),
                        datosCliente.getString("idCliente"),
                        datosCliente.getString("telefono"),
                        datosCliente.getString("correo"),
                        datosCliente.getDouble("peso"),
                        datosCliente.getDouble("fuerza"),
                        datosCliente.getDouble("altura"),
                        datosCliente.getString("estado")
                ));

            }

            datosCliente.close();
            datosCliente=null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static ResultSet mostrarClienteAsistencia(String id) {
        try {
            ResultSet datosCliente = Main.comando.executeQuery(
                    "SELECT *,DATEDIFF(CURRENT_TIMESTAMP,Factura.fecha) as diasRestantes  FROM  Cliente LEFT JOIN Enfermedades  ON Cliente.Enfermedades_idEnfermedades=Enfermedades.idEnfermedades " +
                            "  INNER JOIN Factura ON Cliente.idCliente = Factura.Cliente_idCliente INNER  JOIN Servicio on Factura.idServicio = Servicio.idMembresia " +
                            "WHERE idCliente ='" + id + "'   GROUP BY  Cliente.idCliente ORDER BY Factura.fecha DESC LIMIT 1" );
            return datosCliente;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet mostrarCliente(String id) {
        try {
            ResultSet datosCliente = Main.comando.executeQuery(
                    "SELECT *  FROM  Cliente LEFT JOIN Enfermedades  ON Cliente.Enfermedades_idEnfermedades=Enfermedades.idEnfermedades " +
                            "  INNER JOIN Factura ON Cliente.idCliente = Factura.Cliente_idCliente INNER  JOIN Servicio on Factura.idServicio = Servicio.idMembresia " +
                            "WHERE idCliente ='" + id + "'   GROUP BY  Cliente.idCliente ORDER BY Factura.fecha DESC LIMIT 1" );
            return datosCliente;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static void listaMembresias(ObservableList listaMembresia) throws SQLException {
        ResultSet datosCliente = Main.comando.executeQuery("SELECT nombre,idMembresia FROM Servicio");
        while (datosCliente.next()) {
            listaMembresia.add(datosCliente.getString("nombre"));
        }
        datosCliente.close();

    }

    public static void mostrarTodos(ObservableList<ModelCliente> listaclientes) throws SQLException {
        ResultSet datosCliente = Main.comando.executeQuery("SELECT  Cliente.nombre,IF(Cliente.estado=1,'Habilitado','Desabilitado') as estado2,telefono,idCliente,apellido,edad,correo,telefono,fechaNacimiento,peso,altura,fuerza,Servicio.nombre AS nomMem,descripcion,duracion " +
                "FROM Cliente LEFT JOIN Factura on Cliente.idCliente = Factura.Cliente_idCliente LEFT JOIN Servicio" +
                " ON Factura.idServicio = Servicio.idMembresia GROUP BY Cliente.idCliente");


        while (datosCliente.next()) {

            listaclientes.add(new ModelCliente(
                    datosCliente.getString("nombre"),
                    datosCliente.getString("apellido"),
                    datosCliente.getString("nomMem"),
                    datosCliente.getDate("fechaNacimiento"),
                    datosCliente.getString("idCliente"),
                    datosCliente.getString("telefono"),
                    datosCliente.getString("correo"),
                    datosCliente.getDouble("peso"),
                    datosCliente.getDouble("fuerza"),
                    datosCliente.getDouble("altura"),
                    datosCliente.getString("estado2")
            ));

        }
        datosCliente.close();

    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getApellido() {
        return apellido.get();
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public String getMembresia() {
        return membresia.get();
    }

    public StringProperty membresiaProperty() {
        return membresia;
    }


    public static void updateCliente(String id, String telefono, String correo, String idPadecimiento, LocalDate fecha, String foto2, String text4, String idMembresia, String nombre,String apellido, String fuerza,String edad) throws SQLException {
        Main.comando.execute("UPDATE  Cliente SET telefono='"+telefono+"',correo='"+correo+"',fechaNacimiento='"+fecha+"',Enfermedades_idEnfermedades='"+idPadecimiento+"',nombre='"+nombre+"',apellido='"+apellido+"',Membresia_idMembresia='"+idMembresia+"',edad='"+edad+"',fuerza='"+fuerza+"',foto='"+foto2+"',altura='"+text4+"'  WHERE idCliente='"+id+"'");

    }
}



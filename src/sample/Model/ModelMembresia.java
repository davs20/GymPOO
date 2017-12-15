package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelMembresia {

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer id;
    private Integer duracion;

    public ModelMembresia(String nombre, String descripcion, Double precio, Integer id,Integer duracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id = id;
        this.duracion=duracion;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public static void listaMembresias(ObservableList listaMembresia) throws SQLException {
        ResultSet datosCliente = Main.comando.executeQuery("SELECT * FROM Servicio");
        while (datosCliente.next()) {
            listaMembresia.add(new ModelMembresia(
                    datosCliente.getString("nombre"),
                    datosCliente.getString("descripcion"),
                    datosCliente.getDouble("precio"),
                    datosCliente.getInt("idMembresia"),
                    datosCliente.getInt("duracion")

            ));
        }
        datosCliente.close();

    }

    public static void mostrarMembresia(ObservableList<ModelMembresia> listaServicio, String id) throws SQLException {
        ResultSet datoServicio = Main.comando.executeQuery("select * FROM Servicio where idMembresia='" + id + "'");
        while (datoServicio.next()) {
            listaServicio.add(new ModelMembresia(
                    datoServicio.getString("nombre"),
                    datoServicio.getString("descripcion"),
                    datoServicio.getDouble("precio"),
                    datoServicio.getInt("idMembresia"),
                    datoServicio.getInt("duracion")
            ));
        }
        datoServicio.close();

    }
    public Boolean guardarMembresia() throws SQLException {

        return Main.comando.execute("INSERT INTO `Servicio`(`nombre`, `descripcion`, `duracion`, `precio`, `idImpuesto`,`estado`) " +
                 "VALUES ('"+getNombre()+"','"+getDescripcion()+"','"+getDuracion()+"','"+getPrecio()+"',(SELECT idImpuesto FROM Impuesto ORDER BY idImpuesto DESC Limit 1),1)");


    }

    public static int obtenerId( int id) {


        try {
            ResultSet resultado = Main.comando.executeQuery("SELECT `idMembresia` FROM `Servicio` ORDER BY `idMembresia` DESC Limit 1");
            if (resultado.next()){
                id = resultado.getInt("idMembresia");}

            return  id;
        } catch (SQLException e) {
            e.printStackTrace();
        }return 0;

    }


    public static ResultSet mostrarMembresia(String id) throws SQLException {
        ResultSet datoServicio = Main.comando.executeQuery("SELECT * FROM Servicio Inner Join Impuesto on Impuesto.idImpuesto=Servicio.idImpuesto WHERE idMembresia='" + id + "'");
        return datoServicio;
    }
    public int actualizarRegistros(){
        return 0;

    }
    public int eliminarRegistro(String id){
        try {

            Main.comando.execute("UPDATE Servicio " +
                            "SET estado = 0 " +
                            "WHERE idMembresia = '"+id+"'");
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getNombre() {
        return nombre;
    }


    public String getId() {
        return "" + id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}

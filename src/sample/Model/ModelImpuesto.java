package sample.Model;

import javafx.collections.ObservableList;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class ModelImpuesto {

    private Double valorReal;
    private Double valorImpuesto;
    private Integer idImpuesto;
    private Date fecha;

    public Double getValorImpuesto() {
        return valorImpuesto;
    }

    public Integer getIdImpuesto() {
        return idImpuesto;
    }

    public Date getFecha() {
        return fecha;
    }

    public ModelImpuesto(Double valorImpuesto, Integer idImpuesto, Date fecha) {
        this.valorImpuesto = valorImpuesto;
        this.idImpuesto = idImpuesto;
        this.fecha = fecha;
    }


    public static void  mostrarImpuesto(ObservableList<ModelImpuesto> lista) throws SQLException {
        ResultSet datoImpuesto=Main.comando.executeQuery("SELECT  *  FROM Impuesto WHERE  estado=1");
       while (datoImpuesto.next()){
           lista.add(
                   new ModelImpuesto(
                           datoImpuesto.getDouble("valor"),
                           datoImpuesto.getInt("idImpuesto"),
                           datoImpuesto.getDate("fechaModificacion")
                   ));
       }
       datoImpuesto.close();

    }

    public static ResultSet mostrarImpuesto(String id) throws SQLException {
        ResultSet datoImpuesto= Main.comando.executeQuery("SELECT * FROM Impuesto WHERE idImpuesto='"+id+"'");
        return datoImpuesto;
    }
}

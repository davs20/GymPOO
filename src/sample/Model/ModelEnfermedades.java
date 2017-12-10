package sample.Model;

import javafx.collections.ObservableList;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelEnfermedades {

    private String id;
    private String nombre;
    private String cuidado;
    private String impedimiento;

    public ModelEnfermedades(String id, String nombre,String impedimiento,String cuidado) {
     this.id=id;
     this.nombre=nombre;
     this.cuidado=cuidado;
     this.impedimiento=impedimiento;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static ResultSet getDatosMedicos(String id) throws SQLException {
      ResultSet datosMedicos=Main.comando.executeQuery("SELECT * FROM Enfermedades WHERE idEnfermedades='"+id+"'");
      return datosMedicos;

    }

    public static void listaEnfermedades(ObservableList listaEnfermedades) throws SQLException {

        ResultSet datosCliente = Main.comando.executeQuery("SELECT * FROM Enfermedades");
        while (datosCliente.next()) {
            listaEnfermedades.add(new ModelEnfermedades(
                    datosCliente.getString("nombre"),
                    datosCliente.getString("idEnfermedades"),
                    datosCliente.getString("impedimiento"),
                    datosCliente.getString("cuidado")

            ));
        }
        datosCliente.close();
    }

}

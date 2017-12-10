package sample.Model;

import javafx.collections.ObservableList;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelFormasPago {
    private String id;
    private String nombre;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ModelFormasPago(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    public static void ListaFormasPago(ObservableList<ModelFormasPago> list) throws SQLException {
        ResultSet datosformasPago = Main.comando.executeQuery("SELECT * FROM  FormasPago");
        while (datosformasPago.next()) {
            list.add(new ModelFormasPago(
                    datosformasPago.getString("idFormasPago"),
                    datosformasPago.getString("nombre")
            ));
        }
        datosformasPago.close();

    }


}

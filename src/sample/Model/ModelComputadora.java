package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelComputadora {

    private SimpleStringProperty nombre;
    private SimpleStringProperty marca;
    private SimpleStringProperty modelo;
    public SimpleIntegerProperty id;

    public String getId() {

        return ""+id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public String getMarca() {
        return marca.get();
    }

    public SimpleStringProperty marcaProperty() {
        return marca;
    }

    public String getModelo() {
        return modelo.get();
    }

    public SimpleStringProperty modeloProperty() {
        return modelo;
    }


    public ModelComputadora(String nombre, String marca, String modelo,Integer id) {
        this.nombre = new SimpleStringProperty(nombre);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.id=new SimpleIntegerProperty(id);
    }


    public static void mostrarComputadoras(ObservableList<ModelComputadora> listaComputadoras) throws SQLException {
        ResultSet listaCompus= Main.comando.executeQuery("SELECT  * FROM  Computada");
        while (listaCompus.next()){
            listaComputadoras.add(new ModelComputadora(
               listaCompus.getString("nombre"),
               listaCompus.getString("marca"),
               listaCompus.getString("modelo"),
               listaCompus.getInt("computadoraId")
            ));
        }
    }


}

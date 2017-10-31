package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import sample.config.OperacionesSQl;

import java.util.Date;

public class ModelEntrenador {

    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty membresia;
    private StringProperty fecha;



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

    public String getFecha() {
        return fecha.get();
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public ModelEntrenador(String nombre, String apellido, String membresia, String fecha) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.membresia = new SimpleStringProperty(membresia);
        this.fecha = new SimpleStringProperty(fecha);
    }

    public void agregar(){

    }

    public void buscar(){

    }


}

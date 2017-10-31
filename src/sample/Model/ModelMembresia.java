package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelMembresia {

    private StringProperty nombre;
    private StringProperty descripcion;
    private IntegerProperty precio;
    private IntegerProperty id;

    public ModelMembresia(String nombre, String descripcion, Integer precio, Integer id) {
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion =new SimpleStringProperty(descripcion);
        this.precio = new SimpleIntegerProperty(precio);
        this.id = new SimpleIntegerProperty(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public int getPrecio() {
        return precio.get();
    }

    public IntegerProperty precioProperty() {
        return precio;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }
}

package sample.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ModelSeguimiento {

    private StringProperty idEntrenador;
    private StringProperty idCliente;
    private DoubleProperty peso;
    private DoubleProperty altura;

    public ModelSeguimiento(StringProperty idEntrenador, StringProperty idCliente, DoubleProperty peso, DoubleProperty altura) {
        this.idEntrenador = idEntrenador;
        this.idCliente = idCliente;
        this.peso = peso;
        this.altura = altura;
    }
}

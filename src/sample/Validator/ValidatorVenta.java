package sample.Validator;

import java.time.LocalDate;
import java.util.Date;

public class ValidatorVenta extends Validator {

    private Double precio;
    private Double impuesto;
    private Double cambio;
    private LocalDate fechaVenta;
    private Double subtotal;

    public Double getEfectivo() {
        if(validacion("/^[0-9]+(\\\\.[0-9]+)?$",""+efectivo)){
            return efectivo;

        }
        return null;
    }

    private Double efectivo;

    public Double getPrecio() {
        if (validacion("/^[0-9]+(\\\\.[0-9]+)?$", "" + precio)) {
            return precio;
        }
        return null;
    }

    public Double getImpuesto() {
        if (validacion("/^[0-9]+(\\\\.[0-9]+)?$", "" + impuesto)) {
            return impuesto;

        }
        return null;
    }

    public Double getCambio() {
        if (validacion("/^[0-9]+(\\\\.[0-9]+)?$", "" + cambio)) {
            return cambio;
        }
        return null;
    }

    public LocalDate getFechaVenta() {
        if (validacion("([0-9]{2})\\\\([0-9]{2})\\\\([0-9]{4})", "" + fechaVenta)) {
            return fechaVenta;

        }
        return null;
    }

    public Double getSubtotal() {
        validacion("/^[0-9]+(\\\\.[0-9]+)?$", "" + subtotal);
        return subtotal;
    }

    public ValidatorVenta(Double precio, Double impuesto, Double cambio, LocalDate fechaVenta, Double subtotal,Double efectivo) {
        this.precio = precio;
        this.impuesto = impuesto;
        this.cambio = cambio;
        this.fechaVenta = fechaVenta;
        this.subtotal = subtotal;
        this.efectivo=efectivo;
    }

    public void ventaValidacion() {


    }


}

package sample.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Date;

public class ControllerAsignacionCAI {

    private SimpleIntegerProperty CAI;
    private Date fechaOtorgado;
    private  Date fechaLimite;
    private SimpleIntegerProperty rangoInferior;
    private SimpleIntegerProperty rangoOtorgado;
    private SimpleIntegerProperty solicitud;

    public int getCAI() {
        return CAI.get();
    }

    public SimpleIntegerProperty CAIProperty() {
        return CAI;
    }

    public void setCAI(int CAI) {
        this.CAI.set(CAI);
    }

    public Date getFechaOtorgado() {
        return fechaOtorgado;
    }

    public void setFechaOtorgado(Date fechaOtorgado) {
        this.fechaOtorgado = fechaOtorgado;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int getRangoInferior() {
        return rangoInferior.get();
    }

    public SimpleIntegerProperty rangoInferiorProperty() {
        return rangoInferior;
    }

    public void setRangoInferior(int rangoInferior) {
        this.rangoInferior.set(rangoInferior);
    }

    public int getRangoOtorgado() {
        return rangoOtorgado.get();
    }

    public SimpleIntegerProperty rangoOtorgadoProperty() {
        return rangoOtorgado;
    }

    public void setRangoOtorgado(int rangoOtorgado) {
        this.rangoOtorgado.set(rangoOtorgado);
    }

    public int getSolicitud() {
        return solicitud.get();
    }

    public SimpleIntegerProperty solicitudProperty() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud.set(solicitud);
    }

    public ControllerAsignacionCAI(SimpleIntegerProperty CAI, Date fechaOtorgado, Date fechaLimite, SimpleIntegerProperty rangoInferior, SimpleIntegerProperty rangoOtorgado, SimpleIntegerProperty solicitud) {
        this.CAI = CAI;
        this.fechaOtorgado = fechaOtorgado;
        this.fechaLimite = fechaLimite;
        this.rangoInferior = rangoInferior;
        this.rangoOtorgado = rangoOtorgado;
        this.solicitud = solicitud;
    }






}

package sample.Validator;

import javafx.scene.control.Alert;

public class ValidatorAsistencia extends Validator {

    private String idUsuario;
    private String idCliente;

    public String getIdUsuario() {
        if (validacion("([0-9]){13,13}", this.idUsuario)) {
            return this.idUsuario;
        }else {

            return null;
        }
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCliente() {
        if (validacion("([0-9]){13,13}", "0105198502415")) {
            return this.idCliente;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dialogo de Error");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText("Campo de de Busqueda incorrecto ");
            alert.showAndWait();
            return null;
        }
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void validacionCampos() {

    }

}

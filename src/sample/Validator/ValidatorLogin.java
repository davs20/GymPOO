package sample.Validator;

import com.jfoenix.controls.JFXTextField;

public class ValidatorLogin extends Validator {


    public String usuario(String usuario) {
        String menErrorUsuario = "";

        if (this.validacion("([0-9]){13}", usuario)) {

        return "Correcto";
        }
        return "Incorrecto";

    }

    public String contrasena(String contrasena) {
        String menErrorPass = "";

        if (this.validacion("([A-Z,a-z,0-9]{12})\\w+", contrasena)) {
            return  "Correcto";

        }
        return "Incorrecto";

    }


}

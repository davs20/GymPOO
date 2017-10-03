package sample.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public String nombre_usuario;

    public Validator(String nombre_usuario) {
        this.nombre_usuario=nombre_usuario;
    }

    private Boolean validacion(String exRegular) {
        Pattern valida = Pattern.compile(exRegular);
        Matcher comprobacion = valida.matcher(exRegular);
        if (comprobacion.matches()) {
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getDato() {

        return "das..dasd";
    }


}


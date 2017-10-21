package sample.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {



    protected Boolean validacion(String exRegular,String cadena) {
        Pattern valida = Pattern.compile(exRegular);
        Matcher comprobacion = valida.matcher(cadena);
        if (comprobacion.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public String getDato() {

        return "das..dasd";
    }


}


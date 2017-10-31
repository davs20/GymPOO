package sample.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


    public static Boolean validacion(String exRegular,String cadena) {
        Boolean h;
        Pattern valida = Pattern.compile(exRegular);
        Matcher comprobacion = valida.matcher(cadena);
        if (comprobacion.matches()) {
            return true;
        } else {
            return false;

        }
    }


}


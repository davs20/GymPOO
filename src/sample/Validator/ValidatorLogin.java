package sample.Validator;


public class ValidatorLogin extends Validator {


    public String usuario(String usuario) {

        if (this.validacion("([0-9]){13,13}", usuario)) {

            return usuario;
        }

        if (usuario.contains("\\s")) {

           return usuario = usuario.replaceAll("\\s", "");

        }

        if (usuario.length() > 13) {
            String valida []=usuario.split("\\s");

        }

        return "Incorrecto";

    }




    public String contrasena(String contrasena) {
        String menErrorPass = "";

        if (this.validacion("([A-Z,a-z,0-9]{12})\\w+", contrasena)) {
            return "Correcto";

        }
        return "Incorrecto";

    }


}

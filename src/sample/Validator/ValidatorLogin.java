package sample.Validator;


public class ValidatorLogin extends Validator {


    public String usuario(String usuario) {

        if (this.validacion("([0-9]){13,13}", usuario)) {

            return "Correcto";
        }

        if (usuario.contains("\\s")) {

           return usuario = usuario.replaceAll("\\s", "");

        }


        return null;

    }




    public String contrasena(String contrasena) {

        if (this.validacion("[A-Z,a-z,0-9]{10,}", contrasena)) {
            return "Correcto";

        }
        return null;

    }


}

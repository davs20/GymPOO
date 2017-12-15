package sample.Validator;

public class ValidatorCliente extends Validator {

    public String datosPersonales(String nombres) {
      if(Validator.validacion("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+",nombres)){
          return nombres;
      }
      return null;
    }



}

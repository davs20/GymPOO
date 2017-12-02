package sample.Model;

public class ModelCajero extends ModelUsuario {
    private String cai;


    public ModelCajero(String usuario, String token, String nombres, String apellidos, String correo, String telefono, String idPrivilegio) {
        super(usuario, token, nombres, apellidos, correo, telefono, idPrivilegio);
    }
}

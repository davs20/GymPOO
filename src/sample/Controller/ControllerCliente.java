package sample.Controller;

import sample.config.Conexion;
import sample.config.OperacionesSQl;
import sample.Validator.ValidatorCliente;

import java.sql.SQLException;

public class ControllerCliente {

    public void inicio(){

    }

    public Conexion insertar(ValidatorCliente validatorcliente) throws SQLException {
        Conexion consulta = OperacionesSQl.select("*", "administrador").get();
        return consulta;

    }


    public void actualizar(){

    }


    public void mostrar(){

    }


    public  void  editar(){

    }




}

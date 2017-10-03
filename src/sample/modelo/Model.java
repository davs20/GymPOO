package sample.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import sample.config.Conexion;
import sample.config.Consulta;
import sample.config.OperacionesSQl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Model implements Initializable{

    public StringProperty pass;

    public StringProperty estado;

    public Model(String estado,String pass) {
        this.estado=new SimpleStringProperty(estado);
        this.pass=new SimpleStringProperty(pass);
    }





    public String getPass() {
        return pass.get();
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty(){
        return estado;
    }
    public StringProperty passProperty(){
        return pass;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

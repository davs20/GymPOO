package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelPuntoEmision {
    public StringProperty id;
    public StringProperty direccion;
    public StringProperty usuarioId;
    public StringProperty rtn;
    public int computadora;
    public StringProperty asignacion;
    public StringProperty nombreUsuario;
    public StringProperty CAI;
    public IntegerProperty numeroUser;

    public int getNumeroUser() {
        return numeroUser.get();
    }

    public IntegerProperty numeroUserProperty() {
        return numeroUser;
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public String getUsuarioId() {
        return usuarioId.get();
    }

    public StringProperty usuarioIdProperty() {
        return usuarioId;
    }

    public String getRtn() {
        return rtn.get();
    }

    public StringProperty rtnProperty() {
        return rtn;
    }

    public int getComputadora() {
        return computadora;
    }

    public String getAsignacion() {
        return asignacion.get();
    }

    public StringProperty asignacionProperty() {
        return asignacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario.get();
    }

    public StringProperty nombreUsuarioProperty() {
        return nombreUsuario;
    }

    public String getCAI() {
        return CAI.get();
    }

    public StringProperty CAIProperty() {
        return CAI;
    }

    public ModelPuntoEmision(String id, String direccion, String usuarioId, String rtn, int computadora, String cai, Integer numero) {
        this.id = new SimpleStringProperty(id);
        this.direccion = new SimpleStringProperty(direccion);
        this.usuarioId = new SimpleStringProperty(usuarioId);
        this.rtn = new SimpleStringProperty(rtn);
        this.computadora = computadora;
        this.CAI= new SimpleStringProperty(cai);
        this.numeroUser=new SimpleIntegerProperty(numero);
    }

    public void nuevoPunto(ModelPuntoEmision m) throws SQLException {
        System.out.println(m.usuarioId.toString());
        Main.comando.execute("INSERT INTO PuntoEmision ( Sucursal_direccion, Usuarios_idUsuarios, " +
                "AsignacionCai_CAI, Computada_computadoraId, rtn) VALUES ('"+m.getDireccion().toString()+"','"+m.getUsuarioId().toString()+"','"+m.getCAI().toString()+"','"+getComputadora()+"','"+m.getRtn().toString()+"')");



    }

    public static ResultSet CAI() throws SQLException {
        ResultSet datoSolicitud=Main.comando.executeQuery("SELECT * FROM AsignacionCai ORDER BY fechaOtorgado DESC  LIMIT 1");
        return datoSolicitud;
    }

    public void actualizar(String id) throws SQLException {
        Main.comando.execute("UPDATE PuntoEmision SET  Sucursal_direccion='"+getDireccion()+"', Usuarios_idUsuarios='"+getUsuarioId()+"',Computada_computadoraId='"+getComputadora()+"',rtn='"+getRtn()+"',AsignacionCai_CAI='"+getCAI()+"'  WHERE idPuntoEmision='"+id+"'");
    }
     public static void  mostrarPuntos(ObservableList listaEmision) throws SQLException {

        ResultSet datoPuntoEmision=Main.comando.executeQuery("SELECT  *,(SELECT @rownum:=@rownum+1 AS rownum" +
                ""+
                " FROM (SELECT @rownum:=0) r, Usuarios WHERE idUsuarios=PuntoEmision.Usuarios_idUsuarios) as numero From PuntoEmision");
        while (datoPuntoEmision.next()){
            listaEmision.add(new ModelPuntoEmision(
                   datoPuntoEmision.getString("idPuntoEmision"),
                   datoPuntoEmision.getString("Sucursal_direccion"),
                   datoPuntoEmision.getString("Usuarios_idUsuarios"),
                   datoPuntoEmision.getString("rtn"),
                   datoPuntoEmision.getInt("Computada_computadoraId"),
                   datoPuntoEmision.getString("AsignacionCai_CAI"),
                    datoPuntoEmision.getInt("numero")

            ));
  //          datoPuntoEmision.close();

        }


    }






}

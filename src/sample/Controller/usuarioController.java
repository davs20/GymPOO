package sample.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Controller.ConexionDB;
import sample.Model.Membresia;
import sample.Model.Privilegio;
import sample.Model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.Controller.Controller.urlbase;

/**
 * Created by User on 12/13/2017.
 */
public class usuarioController implements Initializable {
    //Botones Datos Membresia
    public Button nuevoUsuarioBT;
    public Button modificarUsuarioBT;
    public Button eliminarUsuarioBT;
    public Button guardarUsuarioBT;

    //TextFields Datos Membresia
    public JFXTextField nombresUsuarioTF;
    public JFXTextField apellidosUsuarioTF;
    public JFXTextField idUsuarioTF;
    public JFXTextField contrasenaUsuarioTF;
    public JFXTextField correoUsuarioTF;
    public JFXTextField telefonoUsuarioTF;


    //Tabla y Columnas de Lista de Membresia
    public TableView<Usuario> tablaUsuario;
    public TableColumn nombresUsuarioCL;
    public TableColumn apellidosUsuarioCL;
    public TableColumn idUsuarioCL;
    public TableColumn contrasenaUsuarioCL;
    public TableColumn correoUsuarioCL;
    public TableColumn telefonoUsuarioCL;
    public TableColumn privilegioUsuarioCL;

    //ComboBox Privilegios
    public ComboBox<Privilegio> cmbPrivilegio;

    //Coleccion Privilegio
    private ObservableList<Privilegio> privilegios;


    //Colecciones - listas observables
    private ObservableList<Usuario> usuarios;


    //Conexion
    private ConexionDB conexion;
    private void nuevaScena(ActionEvent actionEvent, String scena, String titulo) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nueva = new Scene(fxml);
        Stage inicio2 = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio2.setTitle(titulo);
        inicio2.setScene(nueva);
        inicio2.show();
    }

    public void atras(ActionEvent actionEvent){
        nuevaScena(actionEvent,"Administrador/Configuracion/VistaMantenimiento.fxml","Home");

    }


    public boolean validarCorreo(){
        Pattern p = Pattern.compile("^ [_ A-z0-9 -] + ([_ a-z0-9 -]. +) * @ [A-z0-9 -] + ([a-z0-9 -]. +). * ([Az ] {2,4}) $ ");
        Matcher m = p.matcher(correoUsuarioTF.getText());
        if (m.find()&& m.group().equals(correoUsuarioTF.getText())){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Correo Electronico");
            alert.setHeaderText(null);
            alert.setContentText("Por favor introduzco una direccion de correo valido" +
                    "Ejemplo: juan@gmail.com " +
                    "         juan@yahoo.com ");

            return false;
        }

//        Comienzo del patrón
//                ^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;
//        Caracteres por los que puede comenzar la dirección del correo electrónico
//                ^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;
//        Caracteres opcionales.
//^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;
//        La dirección de correo debe contener el símbolo @
//^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;
//        El nombre del dominio del correo debe comenzar por estos caracteres
//                ^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;
//        El primer dominio de nivel superior o TLD debe de comenzar por punto seguido por los caracteres resaltados
//^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;
//        El primer nivel TLD puede ir seguido opcionalmente por un segundo nivel TLD que debe ser de tener 2 o más caracteres
//                ^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;
    }

    //Metodos para los Botones

    public void modificarBT(ActionEvent event) {
        Usuario u = new Usuario(idUsuarioTF.getText(), contrasenaUsuarioTF.getText(), nombresUsuarioTF.getText(), apellidosUsuarioTF.getText(), correoUsuarioTF.getText(), telefonoUsuarioTF.getText(), cmbPrivilegio.getSelectionModel().getSelectedItem());

        //Llamar al metodo guardarMembresia de la clase Membresia
        conexion.establecerConexion();
        int resultado = u.actualizarRegistros(conexion.getConnection());
        conexion.cerrarConexion();
        if (resultado == 1) {
            usuarios.set(tablaUsuario.getSelectionModel().getSelectedIndex(), u);
            //tablaEnfermedad.setItems(membresias);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Actualizado");
            mensaje.setContentText("El Registro ha sido actualizado exitosamente");
            mensaje.show();
        }
        nuevoBT(event);
    }

    public void eliminarBT(ActionEvent event) {
        conexion.establecerConexion();
        //Obtenemos la instancia del registro que esta actualmente seleccionado
        int resultado = tablaUsuario.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            usuarios.remove(tablaUsuario.getSelectionModel().getSelectedIndex());

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Eliminado");
            mensaje.setContentText("El Registro ha sido eliminado exitosamente");
            mensaje.show();
        }
    }

    public void nuevoBT(ActionEvent event) {

        nombresUsuarioTF.setText("");
        apellidosUsuarioTF.setText("");
        idUsuarioTF.setText("");
        contrasenaUsuarioTF.setText("");
        correoUsuarioTF.setText("");
        telefonoUsuarioTF.setText("");
        cmbPrivilegio.setValue(null);

        //Botones Habilitados y Desahabilitados
        modificarUsuarioBT.setDisable(true);
        eliminarUsuarioBT.setDisable(true);
        guardarUsuarioBT.setDisable(false);
    }

    public void guardarBT(ActionEvent event) {

        // if (!nombreEnfermedadTF.getText().isEmpty() || !precioMembresiaFT.getText().isEmpty() || !duracionMembresiaFT.getText().isEmpty()) {
        if (!nombresUsuarioTF.getText().equals("")&&!idUsuarioTF.getText().equals("")&&!contrasenaUsuarioTF.getText().equals("")) {
          //    if(validarCorreo()){
            //Llamar al metodo guardarMembresia de la clase Membresia
            conexion.establecerConexion();
            //Crear una nueva instancia del tipo membresia
            Usuario u = new Usuario(idUsuarioTF.getText(), contrasenaUsuarioTF.getText(), nombresUsuarioTF.getText(), apellidosUsuarioTF.getText(), correoUsuarioTF.getText(), telefonoUsuarioTF.getText(), cmbPrivilegio.getSelectionModel().getSelectedItem());
            int resultado = u.guardarRegistro(conexion.getConnection());
            conexion.cerrarConexion();
            if (resultado == 1) {
                usuarios.add(u);
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Registro Agregado ");
                mensaje.setContentText("El Registro ha sido agregado exitosamente");
                mensaje.show();
            //}
            }
        }else {
                Alert mensaje = new Alert(Alert.AlertType.WARNING);
                mensaje.setTitle("Campos Vacios");
                mensaje.setContentText("Debe llenar los campos");
                mensaje.show();


            }

    }


    public void inicializarTabla() {

        //Inicializamos las colleciones - listas observables

        nombresUsuarioCL.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombresUsuario"));
        apellidosUsuarioCL.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellidosUsuario"));
        contrasenaUsuarioCL.setCellValueFactory(new PropertyValueFactory<Usuario, String>("contrasenaUsuario"));
        idUsuarioCL.setCellValueFactory(new PropertyValueFactory<Usuario, String>("idUsuario"));
        telefonoUsuarioCL.setCellValueFactory(new PropertyValueFactory<Usuario, String>("telefonoUsuario"));
        correoUsuarioCL.setCellValueFactory(new PropertyValueFactory<Usuario, String>("correoUsuario"));
        privilegioUsuarioCL.setCellValueFactory(new PropertyValueFactory<Usuario, String>("privilegio"));


        escucharEventos();

    }

    public void escucharEventos() {
        tablaUsuario.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {
            @Override
            public void changed(ObservableValue<? extends Usuario> observable, Usuario valorAnterior, Usuario valorSeleccionado) {
                if (valorSeleccionado != null) {//Validamos que la variable valorSelecionado no vaya NULL

                    nombresUsuarioTF.setText(valorSeleccionado.getNombresUsuario());
                    apellidosUsuarioTF.setText(valorSeleccionado.getApellidosUsuario());
                    idUsuarioTF.setText(valorSeleccionado.getIdUsuario());
                    contrasenaUsuarioTF.setText(valorSeleccionado.getContrasenaUsuario());
                    correoUsuarioTF.setText(valorSeleccionado.getCorreoUsuario());
                    telefonoUsuarioTF.setText(valorSeleccionado.getTelefonoUsuario());
                    cmbPrivilegio.setValue(valorSeleccionado.getPrivilegio());


                    guardarUsuarioBT.setDisable(true);
                    eliminarUsuarioBT.setDisable(false);
                    modificarUsuarioBT.setDisable(false);
                }
            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new ConexionDB();
        conexion.establecerConexion();
        usuarios = FXCollections.observableArrayList();
        privilegios = FXCollections.observableArrayList();
        Usuario.llenarTabla(conexion.getConnection(), usuarios);
        Privilegio.llenarInformacion(conexion.getConnection(), privilegios);
        tablaUsuario.setItems(usuarios);
        cmbPrivilegio.setItems(privilegios);
        this.inicializarTabla();
        conexion.cerrarConexion();
        modificarUsuarioBT.setDisable(true);
        eliminarUsuarioBT.setDisable(true);

    }

}

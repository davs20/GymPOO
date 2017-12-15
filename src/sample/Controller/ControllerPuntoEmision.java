package sample.Controller;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.util.StringConverter;
import sample.Model.Model;
import sample.Model.ModelComputadora;
import sample.Model.ModelPuntoEmision;
import sample.Model.ModelUsuario;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

public class ControllerPuntoEmision implements Initializable {

    public TextArea direccion2;
    public TableColumn<ModelPuntoEmision, String> usuario2;
    private String idEmision;
    private String idUsuario;
    private int numeroUsuario;
    public Button actualizar;
    public Button nuevo;
    public Button limpiar;
    public String idPunto;
    private String idComputadora;
    public TextField CAI;
    private ObservableList<ModelPuntoEmision> listEmision;
    private ObservableList<ModelUsuario> listUsuario;
    private ObservableList<ModelComputadora> listaComputadoras;
    public TableView<ModelPuntoEmision> puntoEmision;
    public TableColumn<ModelPuntoEmision, String> id;
    public TableColumn<ModelPuntoEmision, String> direccion;
    public TableColumn<ModelPuntoEmision, String> cai;
    public TableColumn<ModelPuntoEmision,String> rtn;
    public JFXComboBox<ModelUsuario> usuario;
    public JFXComboBox<ModelComputadora> computadora;

    public String getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(String idPunto) {
        this.idPunto = idPunto;
    }

    private int idcomputadora;
    public TextField rtn2;
    public String usuarioListener;

    public String getUsuarioListener() {
        return usuarioListener;
    }

    public void setUsuarioListener(String usuarioListener) {
        this.usuarioListener = usuarioListener;
    }

    public String getIdComputadora() {
        return idComputadora;
    }

    public void setIdComputadora(String idComputadora) {
        this.idComputadora = idComputadora;
    }

    public int getNumeroUsuario() {
        return numeroUsuario;
    }

    public void setNumeroUsuario(int numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
    }

    public String getIdEmision() {
        return idEmision;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdEmision(String idEmision) {
        this.idEmision = idEmision;
    }

    public int getIdcomputadora() {
        return idcomputadora;
    }

    public void setIdcomputadora(int idcomputadora) {
        this.idcomputadora = idcomputadora;
    }

    private String obtenerCAI() {
        try {
            ResultSet datoCAI=ModelPuntoEmision.CAI();
            datoCAI.next();
            return datoCAI.getString("CAI");
        } catch (SQLException e) {
            e.printStackTrace();
          return null;
        }
    }
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

    public void guardar(ActionEvent actionEvent) {


    }

    public void actualizar( ActionEvent actionEvent){

        ModelPuntoEmision emision = new ModelPuntoEmision(
                idEmision,
                direccion2.getText(),
                getUsuarioListener(),
                rtn2.getText(),
                getIdcomputadora(),
                obtenerCAI(),
                getNumeroUsuario()


        );

        try {
            emision.actualizar(getIdPunto());
            listEmision.clear();
            llenarListaPunto();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void nuevoPunto(ActionEvent actionEvent) {
        ModelPuntoEmision emision = new ModelPuntoEmision(
                idEmision,
                direccion2.getText(),
                getIdUsuario(),
                rtn.getText(),
                getIdcomputadora(),
                obtenerCAI(),
                getNumeroUsuario()


        );
        try {
            emision.nuevoPunto(emision);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listEmision.add(emision);


    }

    public void limpiar(ActionEvent actionEvent) {
        direccion2.setText("");
        usuario.getSelectionModel().clearSelection();
        usuario2.getTableView().selectionModelProperty().get().clearSelection();
        CAI.setText("");
        rtn.setText("");
        computadora.getSelectionModel().clearSelection();
        nuevo.setDisable(false);


    }


    private void setCampos() {

    }

    private void llenarListaPunto() {
        try {
            ModelPuntoEmision.mostrarPuntos(listEmision);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void llenarListaUsuarios() {
        try {

            ModelUsuario.mostrarDatosUsuarioCajero(listUsuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void llenarListaComputadoras(){
        try {

            ModelComputadora.mostrarComputadoras(listaComputadoras);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getIdUsuario(String idUsuario) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nuevo.setDisable(false);
        cai.setCellValueFactory(new PropertyValueFactory<ModelPuntoEmision, String>("CAI"));
        usuario2.setCellValueFactory(new PropertyValueFactory<>("usuarioId"));
        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        rtn.setCellValueFactory(new PropertyValueFactory<>("rtn"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        listEmision = FXCollections.observableArrayList();
        listUsuario = FXCollections.observableArrayList();
        listaComputadoras=FXCollections.observableArrayList();
        llenarListaPunto();
        llenarListaUsuarios();
        llenarListaComputadoras();
        puntoEmision.setItems(listEmision);
        usuario.setItems(listUsuario);
        computadora.setItems(listaComputadoras);

         computadora.setConverter(new StringConverter<ModelComputadora>() {
             @Override
             public String toString(ModelComputadora object) {
                 return object.getId();
             }

             @Override
             public ModelComputadora fromString(String string) {
                 return computadora.getItems().stream().filter(ap ->
                         ap.getNombre().equals(string)).findFirst().orElse(null);
             }
         });
        usuario.setConverter(new StringConverter<ModelUsuario>() {
            @Override
            public String toString(ModelUsuario object) {
                return object.getNombre();
            }


            @Override
            public ModelUsuario fromString(String string) {

                return usuario.getItems().stream().filter(ap ->
                        ap.getUsuario().equals(string)).findFirst().orElse(null);

            }
        });

        computadora.setConverter(new StringConverter<ModelComputadora>() {
            @Override
            public String toString(ModelComputadora object) {
                return object.getNombre();
            }

            @Override
            public ModelComputadora fromString(String string) {

                return computadora.getItems().stream().filter(ap ->
                        ap.getId().equals(string)).findFirst().orElse(null);
            }
        });

        computadora.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelComputadora>() {
            @Override
            public void changed(ObservableValue<? extends ModelComputadora> observable, ModelComputadora oldValue, ModelComputadora newValue) {
                setIdcomputadora(Integer.parseInt(newValue.getId()));

            }
        });

        usuario.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelUsuario>() {
            @Override
            public void changed(ObservableValue<? extends ModelUsuario> observable, ModelUsuario oldValue, ModelUsuario newValue) {
                if (newValue != null) {
                    setIdUsuario(newValue.getUsuario());

                    setNumeroUsuario(usuario.getSelectionModel().getSelectedIndex());
                    setUsuarioListener(newValue.getUsuario());
                    System.out.println(newValue.getUsuario());

                }

            }
        });


        puntoEmision.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelPuntoEmision>() {
            @Override
            public void changed(ObservableValue<? extends ModelPuntoEmision> observable, ModelPuntoEmision oldValue, ModelPuntoEmision newValue) {
                if (newValue != null) {
                    nuevo.setDisable(true);
                    setIdcomputadora(newValue.getComputadora());
                    computadora.getSelectionModel().select(newValue.getComputadora()-1);
                    setIdEmision(newValue.id.toString());
                    direccion2.setText(newValue.direccion.get());
                    usuario.getSelectionModel().select(newValue.getNumeroUser()-1);
                    setIdEmision(newValue.getId());
                    rtn2.setText(newValue.getRtn());
                    setIdPunto(""+Integer.parseInt(newValue.getId()));





                }
            }
        });
    }


}

package sample.Controller;


import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import sample.Main;
import sample.Model.ModelCliente;
import sample.Model.ModelEnfermedades;
import sample.Model.ModelMembresia;
import sample.Validator.Validator;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Controller.Controller.urlbase;

public class ControllerDetalleCliente  implements Initializable {

    @FXML
    Button btnStartCamera;

    @FXML
    Button btnStopCamera;

    @FXML
    Button btnDisposeCamera;

    @FXML
    ComboBox<WebCamInfo> cbCameraOptions;

    @FXML
    BorderPane bpWebCamPaneHolder;

    @FXML
    FlowPane fpBottomPane;

    @FXML
    ImageView imgWebCamCapturedImage;


    public static class WebCamInfo {

        private String webCamName;
        private int webCamIndex;


        public String getWebCamName() {
            return webCamName;
        }

        public void setWebCamName(String webCamName) {
            this.webCamName = webCamName;
        }

        public int getWebCamIndex() {
            return webCamIndex;
        }

        public void setWebCamIndex(int webCamIndex) {
            this.webCamIndex = webCamIndex;
        }

        @Override
        public String toString() {
            return webCamName;
        }
    }

    public String idMembresia;
    public String ipPadecimiento;
    public JFXTextField nombretxt;
    public JFXTextField apellidotxt;
    public JFXTextField telefonotxt;
    public JFXTextField correotxt;
    public JFXTextField idtxt;
    public Tab datPersonales;
    public Tab datMedicos;
    public Tab datServicio;
    public Tab datFoto;
    private String foto;
    private String idPadecimiento;
    public JFXTextField duracion;
    public JFXTextField precio;
    private BufferedImage grabbedImage;
    private Webcam selWebCam = null;
    private boolean stopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

    private String cameraListPromptText = "Elija una Camara";


    public JFXComboBox<ModelEnfermedades> padecimiento;
    public JFXComboBox<ModelMembresia> membresias;
    public JFXDatePicker fecha;
    public JFXTextField edadtxt;
    public JFXTextField alturatxt;
    public JFXTextField pesotxt;
    public JFXTextField fuerzatxt;
    public Boolean nombres = true;
    public Boolean apellidos = true;
    public Boolean telefono = true;
    public Boolean correo = true;
    public Boolean edad = true;
    public Boolean idbol = true;
    public Boolean peso;
    public Boolean altura;
    public Boolean fuerza;
    public Boolean padecimientos;
    public Boolean comentarios;
    private  String foto2;
    public TextArea comentariostxt;

    private Stage stage;
    private String id;

    private ObservableList<ModelEnfermedades> enfermedades;
    private ObservableList<ModelMembresia> membresia;

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void guardarTodo() {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Dialogo de Confirmacion");
            alert.setHeaderText("Confimacion");
            alert.setContentText("Esta Seguro de Guardar a este Cliente?");
            alert.showAndWait();




            Main.comando.execute("INSERT INTO `Cliente` (`idCliente`, `fechaNacimiento`, `nombre`, `apellido`, " +
                    "`telefono`, `Membresia_idMembresia`, `Clientecol`, `edad`, `Enfermedades_idEnfermedades`, `peso`, " +
                    "`fuerza`, `masaMuscular`, `correo`, `direccion`, `altura`, `foto`) VALUES ('" + idtxt.getText() + "'," +
                    " '" + fecha.getValue() + "', '" + nombretxt.getText() + "', '" + apellidotxt.getText() + "', '"
                    + telefonotxt.getText() + "'," + " '" + membresias.getSelectionModel().getSelectedItem().getId() + "', NULL, '" + edadtxt.getText() + "', '" + padecimiento.getSelectionModel().getSelectedItem().getNombre() + "', '" + pesotxt.getText() + "', '" + fuerzatxt.getText()
                    + "',NULL, '" + correotxt.getText() + "'" +
                    ", '" + comentariostxt.getText() + "', '" + alturatxt.getText() + "'," + " '" + foto2 + "')");

            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void nuevaScena(String scena, ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(urlbase.concat(scena)));
        Scene nueva = new Scene(fxml);
        Stage inicio = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        inicio.setScene(nueva);
        inicio.show();
        fxml = null;
        nueva = null;
        inicio = null;
        System.gc();

    }


    public String getIdPadecimiento() {
        return idPadecimiento;
    }

    public void setIdPadecimiento(String idPadecimiento) {
        this.idPadecimiento = idPadecimiento;
    }

    public void setCliente(Integer index) {
        ResultSet datosCliente = ModelCliente.mostrarCliente(id);
        try {
            datosCliente.next();
            nombretxt.setText(datosCliente.getString("nombre"));
            telefonotxt.setText(datosCliente.getString("telefono"));
            idtxt.setText(datosCliente.getString("idCliente"));
            correotxt.setText(datosCliente.getString("correo"));


            alturatxt.setText("" + datosCliente.getString("altura"));
            pesotxt.setText(datosCliente.getString("peso"));
            fuerzatxt.setText(datosCliente.getString("fuerza"));
            datosCliente.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setdatosServicio(String string) {
        try {
            ModelMembresia.mostrarMembresia(this.membresia, string);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void datosPersonales(ActionEvent actionEvent) {
        datMedicos.getTabPane().getSelectionModel().selectPrevious();
        datMedicos.setDisable(true);
        datServicio.setDisable(true);
        datPersonales.setDisable(false);
        datFoto.setDisable(true);
    }

    public void atrasDatMedicos(ActionEvent actionEvent) {

        datServicio.getTabPane().getSelectionModel().selectPrevious();
        datServicio.setDisable(true);
        datMedicos.setDisable(false);
        datPersonales.setDisable(true);
        datFoto.setDisable(true);


    }

    public void atrasDatosServicio(ActionEvent actionEvent) {


    }

    public void updateCliete(ActionEvent actionEvent){
        try {
            ModelCliente.updateCliente(
                    idtxt.getText(),
                    telefonotxt.getText(),
                    correotxt.getText(),
                    this.getIdPadecimiento(),
                    fecha.getValue(),
                    foto2,
                    alturatxt.getText(),
                    getIdMembresia(),
                    nombretxt.getText(),
                    apellidotxt.getText(),
                    fuerzatxt.getText(),
                    edadtxt.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void datosServicio(ActionEvent actionEvent) {

        if (!Validator.validacion("^[0-9]*(\\.[0-9]+)", pesotxt.getText())) {
            pesotxt.setFocusColor(new Color(1, 0, 0, 1));
            peso = false;
        } else {
            peso = true;
            pesotxt.setFocusColor(new Color(0, 0, 1, 1));

        }
        if (!Validator.validacion("^[0-9]*(\\.[0-9]+)", alturatxt.getText())) {
            alturatxt.setFocusColor(new Color(1, 0, 0, 1));
            altura = false;
        } else {
            altura = true;
            alturatxt.setFocusColor(new Color(0, 0, 1, 1));

        }
        if (!Validator.validacion("^[0-9]*(\\.[0-9]+)", fuerzatxt.getText())) {
            fuerzatxt.setFocusColor(new Color(1, 0, 0, 1));
            fuerza = false;
        } else {
            fuerzatxt.setFocusColor(new Color(0, 0, 1, 1));
            fuerza = true;
        }

        if (!Validator.validacion("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+", comentariostxt.getText())) {
            comentarios = false;
        } else {
            comentarios = true;
        }


        if (peso && altura && fuerza && comentarios) {
            datMedicos.getTabPane().getSelectionModel().selectNext();
            datMedicos.setDisable(true);
            datPersonales.setDisable(true);
            datServicio.setDisable(false);

        }


    }

    public void datoFoto(ActionEvent actionEvent) {
        datFoto.getTabPane().getSelectionModel().selectNext();
        datFoto.setDisable(false);
        datPersonales.setDisable(true);
        datMedicos.setDisable(true);
        datServicio.setDisable(true);

    }

    public void atrasDatFoto(ActionEvent actionEvent) {
        datFoto.getTabPane().getSelectionModel().selectPrevious();
        datFoto.setDisable(true);
        datServicio.setDisable(false);
        datPersonales.setDisable(true);
        datMedicos.setDisable(true);
    }

    public void datosMedicos(ActionEvent actionEvent) {

        if (!Validator.validacion("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+", nombretxt.getText())) {
            nombres = false;
            nombretxt.setFocusColor(new Color(1, 0, 0, 1));

        } else {
            nombres = true;
            nombretxt.setFocusColor(new Color(0, 0, 1, 1));
        }
        if (!Validator.validacion("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+", apellidotxt.getText())) {
            apellidos = false;
            apellidotxt.setFocusColor(new Color(1, 0, 0, 1));
            System.out.println("ggfasdf");
        } else {
            apellidos = true;
            apellidotxt.setFocusColor(new Color(0, 0, 1, 1));

        }
        if (!Validator.validacion("([0-9]){13,13}", idtxt.getText())) {
            idbol = false;
            idtxt.setFocusColor(new Color(1, 0, 0, 1));
        } else {
            idbol = true;
            idtxt.setFocusColor(new Color(0, 0, 1, 1));

        }
        if (!Validator.validacion("([0-9]){2,2}", edadtxt.getText())) {
            edad = false;

            edadtxt.setFocusColor(new Color(1, 0, 0, 1));
        } else {
            edad = true;
            edadtxt.setFocusColor(new Color(0, 0, 1, 1));

        }
        if (!Validator.validacion("([0-9]){8,8}", telefonotxt.getText()) )  {
            telefono = false;
            telefonotxt.setFocusColor(new Color(1, 0, 0, 1));

        } else {
            telefono = true;
            telefonotxt.setFocusColor(new Color(0, 0, 1, 1));

        }
        if (!Validator.validacion("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$", correotxt.getText())) {
            correo = false;
            correotxt.setFocusColor(new Color(1, 0, 0, 1));

        } else {
            correo = true;
            correotxt.setFocusColor(new Color(0, 0, 1, 1));

        }


        if (idbol && nombres && telefono && edad && apellidos && correo  || correotxt.getText().isEmpty() || telefonotxt.getText().isEmpty()) {
            datPersonales.getTabPane().getSelectionModel().selectNext();
            datServicio.setDisable(true);
            datMedicos.setDisable(false);
            datPersonales.setDisable(true);


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dialogo de Advertencia");
            alert.setHeaderText("Atencion!");
            alert.setContentText("Verifique que los campos sean los adecuados");
            alert.showAndWait();



        }


    }

    private void setCamposMedicos(String id) {
        System.out.println(id);
        try {

            ResultSet datosMedicos = ModelEnfermedades.getDatosMedicos(id);
            datosMedicos.next();
            if(datosMedicos.getString("cuidado").equals("NULL")){
                comentariostxt.setText("Sin Comentarios");
            }else{
                comentariostxt.setText(datosMedicos.getString("cuidado"));
            }
            datosMedicos.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void setMembresias(String id){
        try {
            ResultSet datoServicio=ModelMembresia.mostrarMembresia(id);
            datoServicio.next();
            precio.setText(""+datoServicio.getDouble("precio"));
            duracion.setText(""+datoServicio.getInt("duracion"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setServicio(){

    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        enfermedades = FXCollections.observableArrayList();
        membresia = FXCollections.observableArrayList();
        datMedicos.setDisable(true);
        datServicio.setDisable(true);
        datFoto.setDisable(true);


        padecimiento.setConverter(new StringConverter<ModelEnfermedades>() {
            @Override
            public String toString(ModelEnfermedades object) {
                return object.getId();
            }

            @Override
            public ModelEnfermedades fromString(String string) {
                return padecimiento.getItems().stream().filter(ap ->
                        ap.getNombre().equals(string)).findFirst().orElse(null);
            }
        });

        membresias.setConverter(new StringConverter<ModelMembresia>() {
            @Override
            public String toString(ModelMembresia object) {
                return object.getNombre();
            }

            @Override
            public ModelMembresia fromString(String string) {
                return membresias.getItems().stream().filter(ap ->
                        ap.getNombre().equals(string)).findFirst().orElse(null);
            }
        });

        padecimiento.valueProperty().addListener(new ChangeListener<ModelEnfermedades>() {
            @Override
            public void changed(ObservableValue<? extends ModelEnfermedades> observable, ModelEnfermedades oldValue, ModelEnfermedades newValue) {

                setCamposMedicos(newValue.getNombre());
                setIdPadecimiento(newValue.getNombre());


            }
        });

        membresias.valueProperty().addListener(new ChangeListener<ModelMembresia>() {
            @Override
            public void changed(ObservableValue<? extends ModelMembresia> observable, ModelMembresia oldValue, ModelMembresia newValue) {
                setMembresias(newValue.getId());


            }
        });

        try {
            ModelEnfermedades.listaEnfermedades(this.enfermedades);
            ModelMembresia.listaMembresias(this.membresia);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        padecimiento.setItems(this.enfermedades);
        membresias.setItems(this.membresia);

        fpBottomPane.setDisable(true);
        ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
        int webCamCounter = 0;
        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamInfo webCamInfo = new WebCamInfo();
            webCamInfo.setWebCamIndex(webCamCounter);
            webCamInfo.setWebCamName(webcam.getName());
            options.add(webCamInfo);
            webCamCounter++;
        }
        cbCameraOptions.setItems(options);
        cbCameraOptions.setPromptText(cameraListPromptText);
        cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ControllerDetalleCliente.WebCamInfo>() {

            @Override
            public void changed(ObservableValue<? extends WebCamInfo> arg0, ControllerDetalleCliente.WebCamInfo arg1, ControllerDetalleCliente.WebCamInfo arg2) {
                if (arg2 != null) {
                    System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": Nombre:" + arg2.getWebCamName());
                    initializeWebCam(arg2.getWebCamIndex());
                    stopCamera = false;
                    startWebCamStream();
                    btnStartCamera.setDisable(true);
                    btnStopCamera.setDisable(false);
                }
            }
        });
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                setImageViewSize();
            }
        });


    }

    protected void setImageViewSize() {

        double height = bpWebCamPaneHolder.getHeight();
        double width = bpWebCamPaneHolder.getWidth();
        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(height);
        imgWebCamCapturedImage.prefWidth(width);
        imgWebCamCapturedImage.setPreserveRatio(true);

    }

    protected void initializeWebCam(final int webCamIndex) {

        Task<Void> webCamIntilizer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (selWebCam == null) {
                    selWebCam = Webcam.getWebcams().get(webCamIndex);
                    selWebCam.open();
                } else {
                    closeCamera();
                    selWebCam = Webcam.getWebcams().get(webCamIndex);
                    selWebCam.open();
                }
                startWebCamStream();
                return null;
            }

        };

        new Thread(webCamIntilizer).start();
        fpBottomPane.setDisable(false);
        btnStartCamera.setDisable(true);
    }

    protected void startWebCamStream() {

        stopCamera = false;
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (!stopCamera) {
                    try {
                        if ((grabbedImage = selWebCam.getImage()) != null) {

                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    final Image mainiamge = SwingFXUtils
                                            .toFXImage(grabbedImage, null);
                                    imageProperty.set(mainiamge);
                                }
                            });

                            grabbedImage.flush();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);

    }

    private void closeCamera() {
        if (selWebCam != null) {
            selWebCam.close();
        }
    }

    public void stopCamera(ActionEvent event) {
        stopCamera = true;
        btnStartCamera.setDisable(false);
        btnStopCamera.setDisable(true);
    }

    public void startCamera(ActionEvent event) {
        stopCamera = false;
        startWebCamStream();
        btnStartCamera.setDisable(true);
        btnStopCamera.setDisable(false);
    }

    public void disposeCamera(ActionEvent event) {

        try {

            DirectoryChooser fileChooser = new DirectoryChooser();
            fileChooser.setTitle("Buscar Carpeta");
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) ((Button) event.getSource()).getScene().getWindow());
            stage.setResizable(false);
            File doc = fileChooser.showDialog(stage);
            String foto = doc.getAbsolutePath() + "/" + this.idtxt.getText() + ".png";
            this.foto2=foto;
            ImageIO.write(selWebCam.getImage(), "PNG", new File(this.foto2));
            guardarTodo();

        } catch (IOException e) {
            e.printStackTrace();
        }

        stopCamera = true;
        closeCamera();
        btnStopCamera.setDisable(true);
        btnStartCamera.setDisable(true);

    }
    public void actualizar(ActionEvent event) {

        try {

            DirectoryChooser fileChooser = new DirectoryChooser();
            fileChooser.setTitle("Buscar Carpeta");
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) ((Button) event.getSource()).getScene().getWindow());
            stage.setResizable(false);
            File doc = fileChooser.showDialog(stage);
            String foto = doc.getAbsolutePath() + "/" + this.idtxt.getText() + ".png";
            this.foto2=foto;
            ImageIO.write(selWebCam.getImage(), "PNG", new File(this.foto2));
            updateCliete(event);

        } catch (IOException e) {
            e.printStackTrace();
        }

        stopCamera = true;
        closeCamera();
        btnStopCamera.setDisable(true);
        btnStartCamera.setDisable(true);

    }

}

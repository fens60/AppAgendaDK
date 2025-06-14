package es.ieslosmontecillos;

import com.gluonhq.connect.GluonObservableObject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class InicialSecionController {

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField tfContrasena;

    @FXML
    private Button btnIniciar;

    private Pane rootMain = new Pane();
    private DataUtil dataUtil;
    ObservableList olProv;
    ObservableList olPers;
    ObservableList olUsua;

    public void setRootMain(Pane rootMain) {
        this.rootMain = rootMain;
    }

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public void setOlPers(ObservableList olPers) {
        this.olPers = olPers;
    }

    public void setOlProv(ObservableList olProv) {
        this.olProv = olProv;
    }

    public void setOlUsua(ObservableList olUsua) {
        this.olUsua = olUsua;
    }

    public ObservableList getOlPers() {
        return olPers;
    }

    public ObservableList getOlProv() {
        return olProv;
    }
    public ObservableList getOlUsua() {
        return olUsua;
    }

    @FXML
    public void asceder() {
        String nombreUsuario = tfUsuario.getText().trim();
        String contrasenaIngresada = tfContrasena.getText().trim();

        if (nombreUsuario.isEmpty() || contrasenaIngresada.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obligatorios", "Por favor, introduzca usuario y contraseña.");
            return;
        }
        GluonObservableObject<Usuario> usuarioObservable = dataUtil.findUsuariobyUsuarioNombreAsync(nombreUsuario);
        usuarioObservable.initializedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal && usuarioObservable.get() != null) {
                Usuario usuarioEncontrado = usuarioObservable.get();
                if (usuarioEncontrado.getContrasena().equals(tfContrasena.getText().trim())) {
                    cargarVista(usuarioEncontrado);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Acceso denegado", "Contraseña incorrecta.");
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Usuario no encontrado.");
            }
        });
    }

    @FXML
    public void crear(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/CrearUsuario.fxml"));
            VBox rootCrearUsuarioView = fxmlLoader.load();
            rootCrearUsuarioView.setStyle("-fx-background-color: rgba(0, 255, 0, 0.2);");
            rootMain.getChildren().setAll(rootCrearUsuarioView);
            CrearUsuarioController controller = (CrearUsuarioController) fxmlLoader.getController();
            controller.setDataUtil(dataUtil);
            controller.setRootMain(rootMain);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarVista(Usuario usuario) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AgendaView.fxml"));
            AnchorPane rootAgendaView = fxmlLoader.load();
            rootAgendaView.setStyle("-fx-background-color: rgba(0, 255, 0, 0.2);");
            rootMain.getChildren().setAll(rootAgendaView);
            AgendaViewController controller = (AgendaViewController) fxmlLoader.getController();
            controller.setDataUtil(dataUtil);
            controller.setOlProvincias(olProv);
            controller.setOlPersonas(olPers);
            controller.setUsuario(usuario);
            controller.cargarTodasPersonas();
            controller.setRootAgendaView(rootMain);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}

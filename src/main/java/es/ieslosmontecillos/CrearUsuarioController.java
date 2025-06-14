package es.ieslosmontecillos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CrearUsuarioController {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pwContrasena;
    @FXML
    private PasswordField pwReContrasena;

    // Botones (opcional tener referencia si solo se usan para obtener el Stage)
    @FXML
    private javafx.scene.control.Button btnCrear;
    @FXML
    private javafx.scene.control.Button btnSalir;
    private Pane rootMain = new Pane();
    private DataUtil dataUtil;


    @FXML
    private void crearUsuario(ActionEvent event) {
        boolean error = false;
        // Validar nombre de usuario no vacío
        String usuario = tfUsuario.getText();
        if (usuario == null || usuario.trim().isEmpty()) {
            error = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario no válido");
            alert.showAndWait();
            tfUsuario.requestFocus();
        }

        // Validar contraseñas no vacías
        String pass1 = pwContrasena.getText();
        String pass2 = pwReContrasena.getText();
        if (!error && (pass1 == null || pass1.isEmpty() || pass2 == null || pass2.isEmpty())) {
            error = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contraseña no válida");
            alert.showAndWait();
            // Poner el foco en el primer campo de contraseña vacío
            if (pass1 == null || pass1.isEmpty()) {
                pwContrasena.requestFocus();
            } else {
                pwReContrasena.requestFocus();
            }
        }

        // Validar que ambas contraseñas coinciden
        if (!error && !pass1.equals(pass2)) {
            error = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Las contraseñas no coinciden");
            alert.showAndWait();
            pwReContrasena.requestFocus();
        }

        if (!error) {
            // Crear objeto Usuario y establecer sus propiedades
            Usuario newUser = new Usuario();
            newUser.setUsuario(usuario.trim());
            newUser.setContrasena(pass1);
            newUser.setTipo("I");
            dataUtil.addUsuario(newUser);

            // Guardar el nuevo usuario (por ejemplo, añadir a la lista o base de datos)
            // dataUtil.addUsuario(newUser);  // si existiera un DataUtil para usuarios

            // Ir a la vista de inicio de sesión
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/InicialSecion.fxml"));
                Parent rootAgendaView = fxmlLoader.load();
                rootAgendaView.setStyle("-fx-background-color: rgba(0, 255, 0, 0.2);");
                rootMain.getChildren().setAll(rootAgendaView);
                InicialSecionController controller = fxmlLoader.getController();
                controller.setDataUtil(dataUtil);
                controller.setRootMain(rootMain);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        // Volver a la pantalla de inicio de sesión sin crear usuario
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/InicialSecion.fxml"));
            Parent rootAgendaView = fxmlLoader.load();
            rootAgendaView.setStyle("-fx-background-color: rgba(0, 255, 0, 0.2);");
            rootMain.getChildren().setAll(rootAgendaView);
            InicialSecionController controller = fxmlLoader.getController();
            controller.setDataUtil(dataUtil);
            controller.setRootMain(rootMain);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public DataUtil getDataUtil() {
        return dataUtil;
    }

    public Pane getRootMain() {
        return rootMain;
    }

    public void setRootMain(Pane rootMain) {
        this.rootMain = rootMain;
    }
}

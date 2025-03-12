package es.ieslosmontecillos;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import es.ieslosmontecillos.Login;
import javafx.collections.FXCollections;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;

public class LoginViewController implements Initializable {

    private DataUtil dataUtil;
    private ObservableList<Login> olLogins;
    private Login loginSeleccionada;

    @FXML
    private TextField textFieldEmail;
    @FXML
    private TableView <Login>tableViewLogin;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TableColumn <Login,String> columnId;
    @FXML
    private TableColumn <Login,String> columnVigencia;
    @FXML
    private TableColumn <Login,String> columnPassword;
    @FXML
    private TableColumn columnEmail;
    private final Pane rootMain = new Pane();

    public void setDataUtil(DataUtil dataUtil){
            this.dataUtil=dataUtil;
        }
    public void setOlLogins(ObservableList<Login> olLogins) {
            this.olLogins = olLogins;
        }

    @FXML
    public void onActionButtonSalir(Event actionEvent) {
        System.out.println("Salir");
        System.exit(0);
        /*
        try {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/inicio.fxml"));
        Pane rootInicioView = fxmlLoader.load();
        rootMain.getChildren().add(rootInicioView);
        return;
        }catch (IOException ex){
            System.out.println("IOException: " + ex);
        }*/
    }

    @FXML
    public void onActionButtonGuardar(Event actionEvent) {
        if (loginSeleccionada == null) {
            return;
        }
            loginSeleccionada.setemail(textFieldEmail.getText());
            loginSeleccionada.setclave(textFieldPassword.getText());
            dataUtil.actualizarLogin(loginSeleccionada);
            int numFilaSeleccionada = tableViewLogin.getSelectionModel().getSelectedIndex();
            tableViewLogin.getItems().set(numFilaSeleccionada,loginSeleccionada);
            TablePosition<Login, String> pos = new TablePosition<>(tableViewLogin,numFilaSeleccionada,null);
            tableViewLogin.getFocusModel().focus(pos);
            tableViewLogin.requestFocus();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("clave"));
        columnVigencia.setCellValueFactory(new PropertyValueFactory<>("vigencia"));

        tableViewLogin.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    loginSeleccionada = newValue;
                    if (loginSeleccionada != null) {
                        textFieldEmail.setText(loginSeleccionada.getemail());
                        textFieldPassword.setText(loginSeleccionada.getclave());
                    } else {
                        textFieldEmail.setText("");
                        textFieldPassword.setText("");
                    }
                });
    }

    public void cargarTodasLogins(){
        tableViewLogin.setItems(FXCollections.observableArrayList(olLogins));
    }
}

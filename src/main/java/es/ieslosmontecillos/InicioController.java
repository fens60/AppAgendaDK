package es.ieslosmontecillos;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class InicioController {

    @FXML
    private View inicio;
    @FXML
    private TextField TextFieldUsuario;
    @FXML
    private TextField TextFieldContrasena;

    private DataUtil dataUtil;
    ObservableList olProv;
    ObservableList olPers;
    ObservableList olLogin;

    private final String usuario = "Admin";
    private final String contrasena = "Admin";

    private Pane rootMain = new Pane();


    @FXML
    public void iniciaApp(Event event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AgendaView.fxml"));
            Pane rootAgendaView = fxmlLoader.load();
            rootMain.getChildren().add(rootAgendaView);
            AgendaViewController agendaViewController= (AgendaViewController) fxmlLoader.getController();
            agendaViewController.setDataUtil(dataUtil);
            agendaViewController.setOlProvincias(olProv);
            agendaViewController.setOlPersonas(olPers);
            agendaViewController.cargarTodasPersonas();


        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    public void iniciaLogin(Event event){
        if (TextFieldUsuario.getText().equals(usuario) && TextFieldContrasena.getText().equals(contrasena)){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/LoginView.fxml"));
            Pane rootAgendaView = fxmlLoader.load();
            rootMain.getChildren().add(rootAgendaView);
            LoginViewController loginViewController= (LoginViewController) fxmlLoader.getController();
            loginViewController.setDataUtil(dataUtil);
            loginViewController.setOlLogins(olLogin);
            loginViewController.cargarTodasLogins();


        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        }else {
            System.out.println("Error de usuario o contraseña");
        }
    }

    public void setRootMain(Pane rootMain) {
        this.rootMain = rootMain;
    }
    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }
    public void setOlProv(ObservableList olProv) {
        this.olProv = olProv;
    }
    public void setOlPers(ObservableList olPers) {
        this.olPers = olPers;
    }
    public void setOlLogin(ObservableList olLogin) {
        this.olLogin = olLogin;
    }

}

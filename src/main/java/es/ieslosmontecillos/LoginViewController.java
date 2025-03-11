package es.ieslosmontecillos;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import com.gluonhq.charm.glisten.mvc.View;

import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;


public class LoginViewController extends VBox {
    @FXML
    private TextField TextFieldUsuario;
    @FXML
    private TextField TextFieldContrasena;

    private Login LoginInsectado = new Login();
    private Pane rootAgendaView;
    private AppAgendaDK appAgendaDK;
    private DataUtil dataUtil;
    private ObservableList<Login> olLogins;
    private Pane rootMain = new Pane();
    ObservableList olProv;
    ObservableList olPers;


    public LoginViewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/LoginViewController.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


   @FXML
    public void InicialSecion() {

        if (TextFieldUsuario != null) {
            LoginInsectado.setemail(TextFieldUsuario.getText());
            for (Login login : olLogins) {
                if (!login.getemail().equals(TextFieldUsuario.getText())) {
                    System.out.println("No existe en este usuario");
                    return;
                }
            }
            if (TextFieldContrasena != null) {
                LoginInsectado.setclave(TextFieldContrasena.getText());
                for (Login login : olLogins) {
                    if (!login.getclave().equals(TextFieldContrasena.getText())) {
                        System.out.println("No existe en la base de datos");
                        return;
                    }else if (login.getVigencia()) {
                        iniciaApp();
                    }

                }
            }

        }
    }

    public void iniciaApp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AgendaView.fxml"));
            Pane rootAgendaView = fxmlLoader.load();
            rootMain.getChildren().add(rootAgendaView);
            AgendaViewController agendaViewController = (AgendaViewController) fxmlLoader.getController();
            agendaViewController.setDataUtil(dataUtil);
            agendaViewController.setOlProvincias(olProv);
            agendaViewController.setOlPersonas(olPers);
            agendaViewController.cargarTodasPersonas();


        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    public AppAgendaDK getAppAgendaDK() {
        return appAgendaDK;
    }
    public void setAppAgendaDK(AppAgendaDK appAgendaDK) {
        this.appAgendaDK = appAgendaDK;
    }
    public DataUtil getDataUtil() {
        return dataUtil;
    }
    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }
    public ObservableList<Login> getOlLogins() {
        return olLogins;
    }
    public void setOlLogins(ObservableList<Login> olLogins) {
        this.olLogins = olLogins;
    }

    public Pane getRootAgendaView() {
        return rootAgendaView;
    }
    public void setRootAgendaView(Pane rootAgendaView) {
        this.rootAgendaView = rootAgendaView;
    }

    public void setRootMain(Pane rootMain) {
        this.rootMain = rootMain;
    }
    public void setOlProv(ObservableList olProv) {
        this.olProv = olProv;
    }
    public void setOlPers(ObservableList olPers) {
        this.olPers = olPers;
    }
}


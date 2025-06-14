package es.ieslosmontecillos;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class InicioController {
    @FXML
    private View inicio;
    @FXML
    private Label label;
    private DataUtil dataUtil;
    ObservableList olProv;
    ObservableList olPers;
    ObservableList olUsua;

    private Pane rootMain = new Pane();
    @FXML
    public void iniciaApp(Event event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/InicialSecion.fxml"));
            Pane rootAgendaView = fxmlLoader.load();
            rootMain.getChildren().setAll(rootAgendaView);
            InicialSecionController inicialSecionController= (InicialSecionController) fxmlLoader.getController();
            inicialSecionController.setDataUtil(dataUtil);
            inicialSecionController.setOlProv(olProv);
            inicialSecionController.setOlPers(olPers);
            inicialSecionController.setOlUsua(olUsua);
            inicialSecionController.setRootMain(rootMain);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
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
    public void setOlUsua(ObservableList olUsua) {
        this.olUsua = olUsua;
    }

}

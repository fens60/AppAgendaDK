package es.ieslosmontecillos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.sun.glass.ui.Cursor.setVisible;

public class PersonaDetalleViewController {
    private TableView tableViewPrevio;
    private Persona persona;
    private DataUtil dataUtil;
    private boolean nuevaPersona;
    private Pane rootAgendaView;


    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellidos;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldNumHijos;
    @FXML
    private TextField textFieldSalario;
    @FXML
    private CheckBox checkBoxJubilado;
    @FXML
    private ComboBox<Provincia> comboBoxProvincia;
    @FXML
    private RadioButton radioButtonSoltero;
    @FXML
    private RadioButton radioButtonCasado;
    @FXML
    private RadioButton radioButtonViudo;
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private ImageView imageViewFoto;


    @FXML
    private void onActionButtonGuardar(ActionEvent event){
        FXMLLoader fxmlLoader = new
                FXMLLoader(getClass().getResource("fxml/PersonaDetalleView.fxml"));
        PersonaDetalleViewController personaDetalleViewController =
                (PersonaDetalleViewController) fxmlLoader.getController();
        personaDetalleViewController.setRootAgendaView(rootAgendaView);

        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);

    }
    @FXML
    private void onActionButtonCancelar(ActionEvent event){
        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);

    }


    public void setTableViewPrevio(TableView tableViewPrevio){
        this.tableViewPrevio=tableViewPrevio;
    }
    public void setPersona(Persona persona, Boolean nuevaPersona){
        if (!nuevaPersona){
            this.persona= persona;
        } else {
            this.persona = new Persona();
        }
        this.nuevaPersona=nuevaPersona;
    }

    public void setRootAgendaView(Pane rootAgendaView){
        this.rootAgendaView = rootAgendaView;
    }



}

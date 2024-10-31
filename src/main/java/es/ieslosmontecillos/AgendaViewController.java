package es.ieslosmontecillos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AgendaViewController implements Initializable {
    private ObservableList<Persona> olPersonas;
    private Persona personaSeleccionada;
    private Pane rootAgendaView;

    @FXML
    private TableView<Persona> tableViewAgenda;
    @FXML
    private TableColumn<Persona,String> columnNombre;
    @FXML
    private TableColumn<Persona,String> columnApellidos;
    @FXML
    private TableColumn<Persona,String> columnEmail;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellido;
    @FXML
    private Button Suprimir;
    @FXML
    private Button Nuevo;
    @FXML
    private Button Editar;

    public void setDataUtil(DataUtil dataUtil){
    }

    @Deprecated
    private void onActionButtonNuevo(ActionEvent event){
        try{

            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("fxml/PersonaDetalleView.fxml"));
            PersonaDetalleViewController personaDetalleViewController =
                    (PersonaDetalleViewController) fxmlLoader.getController();
            personaDetalleViewController.setRootAgendaView(rootAgendaView);
            personaSeleccionada = new Persona();
            personaDetalleViewController.setPersona(personaSeleccionada,true);

            Parent rootDetalleView=fxmlLoader.load();
            // Ocultar la vista de la lista
            rootAgendaView.setVisible(false);
            //Añadir la vista detalle al StackPane principal para que se muestre
            StackPane rootMain =
                    (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
            personaDetalleViewController.setPersona(personaSeleccionada,false);
            personaDetalleViewController.mostrarDatos();

        } catch (IOException ex){
            System.out.println("Error volcado"+ex);}
    }

    @Deprecated
    private void onActionButtonEditar(ActionEvent event){
        FXMLLoader fxmlLoader = new
                FXMLLoader(getClass().getResource("fxml/PersonaDetalleView.fxml"));
        PersonaDetalleViewController personaDetalleViewController =
                (PersonaDetalleViewController) fxmlLoader.getController();
        personaDetalleViewController.setRootAgendaView(rootAgendaView);
        personaDetalleViewController.setPersona(personaSeleccionada,false);
        personaDetalleViewController.mostrarDatos();

    }

    @Deprecated
    private void onActionButtonSuprimir(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("¿Desea suprimir el siguiente registro?");
        alert.setContentText(personaSeleccionada.getNombre() + " "+ personaSeleccionada.getApellidos());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // Acciones a realizar si el usuario acepta
            dataUtil.eliminarPersona(personaSeleccionada);
            tableViewAgenda.getItems().remove(personaSeleccionada);
            tableViewAgenda.getFocusModel().focus(null);
            tableViewAgenda.requestFocus();

        } else {
            // Acciones a realizar si el usuario cancela
            int numFilaSeleccionada=
                    tableViewAgenda.getSelectionModel().getSelectedIndex();
            tableViewAgenda.getItems().set(numFilaSeleccionada,personaSeleccionada);
            TablePosition pos = new TablePosition(tableViewAgenda,
                    numFilaSeleccionada,null);
            tableViewAgenda.getFocusModel().focus(pos);
            tableViewAgenda.requestFocus();
        }

    }



    public void setOlProvincias(ObservableList<Provincia> olProvincias) {
    }
    public void setOlPersonas(ObservableList<Persona> olPersonas) {
        this.olPersonas = olPersonas;
    }

    @FXML
    private TableColumn<Persona,String> columnProvincia;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new
                PropertyValueFactory<>("apellidos"));
        columnEmail.setCellValueFactory(new
                PropertyValueFactory<>("email"));
        columnProvincia.setCellValueFactory(
                cellData->{
                    SimpleStringProperty property=new SimpleStringProperty();
                    if (cellData.getValue().getProvincia()!= null){
                        property.setValue(cellData.getValue().getProvincia().getNombre());
                    }
                    return property;
                });
        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue)->{
                    personaSeleccionada=newValue;
                });

    }

    public void cargarTodasPersonas(){
        tableViewAgenda.setItems(FXCollections.observableArrayList(olPersonas));
    }

    public void setRootAgendaView(Pane rootAgendaView){
        this.rootAgendaView = rootAgendaView;
    }

    @FXML
    public void onActionButtonGuardar(ActionEvent actionEvent) {
    }
}
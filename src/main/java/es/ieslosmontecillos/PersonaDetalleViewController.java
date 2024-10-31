package es.ieslosmontecillos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.sun.glass.ui.Cursor.setVisible;

public class PersonaDetalleViewController {
    private TableView tableViewPrevio;
    private Persona persona;
    private DataUtil dataUtil;
    private boolean nuevaPersona;
    private Pane rootAgendaView;
    public static final String CASADO = "C";
    public static final String SOLTERO = "S";
    public static final String VIUDO = "V";


    @FXML
    private AnchorPane rootPersonaDetalleView;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private RadioButton radioButtonViudo;
    @FXML
    private TextField textFieldNumHijos;
    @FXML
    private CheckBox checkBoxJubilado;
    @FXML
    private RadioButton radioButtonSoltero;
    @FXML
    private TextField textFieldSalario;
    @FXML
    private RadioButton radioButtonCasado;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private ComboBox comboBoxProvincia;
    @FXML
    private TextField textFieldApellidos;


    @Deprecated
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
        persona.setNombre(textFieldNombre.getText());
        persona.setApellidos(textFieldApellidos.getText());
        persona.setTelefono(textFieldTelefono.getText());
        persona.setEmail(textFieldEmail.getText());
        if (nuevaPersona){
            dataUtil.addPersona(persona);
        } else {
            dataUtil.actualizarPersona(persona);
        }
        int numFilaSeleccionada;
        if (nuevaPersona){
            tableViewPrevio.getItems().add(persona);
            numFilaSeleccionada = tableViewPrevio.getItems().size()- 1;
            tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
            tableViewPrevio.scrollTo(numFilaSeleccionada);
        } else {
            numFilaSeleccionada=
                    tableViewPrevio.getSelectionModel().getSelectedIndex();
            tableViewPrevio.getItems().set(numFilaSeleccionada,persona);
        }
        TablePosition pos = new TablePosition(tableViewPrevio,
                numFilaSeleccionada,null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();


        // Recoger datos de pantalla
        if (!errorFormato) { // Los datos introducidos son correctos
            try {
// Aquí va el código para guardar el objeto en BD, enviar al servidor
// y ocultar la vista actual
            } catch (Exception ex) { //Los datos introducidos no cumplen requisitos
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios. " + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }
        }

        if (!textFieldNumHijos.getText().isEmpty()){
            try {
                persona.setNumHijos(Integer.valueOf(textFieldNumHijos.getText()));
            } catch(NumberFormatException ex){
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Número de hijos noválido");
                alert.showAndWait();
                textFieldNumHijos.requestFocus();
            }
        }

        if (!textFieldSalario.getText().isEmpty()){
            try {
                Double dSalario =
                        Double.valueOf(Double.valueOf(textFieldSalario.getText()).doubleValue()
                        );
                persona.setSalario(dSalario);
            } catch(NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Salario no válido");
                alert.showAndWait();
                textFieldSalario.requestFocus();
            }
        }


        if(checkBoxJubilado.isSelected()){
            Integer jubilado = 1;
            persona.setJubilado(jubilado);
        };

        if (radioButtonCasado.isSelected()){
            persona.setEstadoCivil(CASADO);
        } else if (radioButtonSoltero.isSelected()){
            persona.setEstadoCivil(SOLTERO);
        } else if (radioButtonViudo.isSelected()){
            persona.setEstadoCivil(VIUDO);
        }

        if (datePickerFechaNacimiento.getValue() != null){
            LocalDate localDate = datePickerFechaNacimiento.getValue();
            ZonedDateTime zonedDateTime =
                    localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaComoCadena = sdf.format(date);
            persona.setFechaNacimiento(fechaComoCadena);
        } else {
            persona.setFechaNacimiento(null);
        }

        if (comboBoxProvincia.getValue() != null){
            persona.setProvincia(comboBoxProvincia.getValue());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Debe indicar una provincia");
            alert.showAndWait();
            errorFormato = true;
        }

    }
    @Deprecated
    private void onActionButtonCancelar(ActionEvent event){
        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);
    }

    public void mostrarDatos() {
        // Configurar TextFields
        boolean errorFormato = false;

        textFieldNombre.setText(persona.getNombre());
        textFieldApellidos.setText(persona.getApellidos());
        textFieldTelefono.setText(persona.getTelefono());
        textFieldEmail.setText(persona.getEmail());
        textFieldNumHijos.setText(String.valueOf(persona.getNumHijos()));
        textFieldSalario.setText(String.valueOf(persona.getSalario()));

        if (persona.getNumHijos() != null){
            textFieldNumHijos.setText(persona.getNumHijos().toString());
        }
        if (persona.getSalario() != null){
            textFieldSalario.setText(persona.getSalario().toString());
        }
        if (persona.getJubilado() != null && persona.getJubilado() == 1) {
            checkBoxJubilado.setSelected(true);
        } else {
            checkBoxJubilado.setSelected(false);
        }
        if (persona.getEstadoCivil() != null) {
            switch (persona.getEstadoCivil()) {
                case CASADO:
                    radioButtonCasado.setSelected(true);
                    break;
                case SOLTERO:
                    radioButtonSoltero.setSelected(true);
                    break;
                case VIUDO:
                    radioButtonViudo.setSelected(true);
                    break;
                default:
                    // Deseleccionar si no coincide con ninguno de los valores esperados
                    radioButtonCasado.setSelected(false);
                    radioButtonSoltero.setSelected(false);
                    radioButtonViudo.setSelected(false);
            }
        } else {
            radioButtonCasado.setSelected(false);
            radioButtonSoltero.setSelected(false);
            radioButtonViudo.setSelected(false);
        }
        if (persona.getFechaNacimiento() != null){
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fecNac = formato.parse(persona.getFechaNacimiento());
            LocalDate fechaNac =
                    fecNac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePickerFechaNacimiento.setValue(fechaNac);
        }

        comboBoxProvincia.setCellFactory((ListView<Provincia> l)-> new ListCell<Provincia>(){
                    @Override
                    protected void updateItem(Provincia provincia, boolean empty){
                        super.updateItem(provincia, empty);
                        if (provincia == null || empty){
                            setText("");
                        } else {
                            setText(provincia.getCodigo()+"-"+provincia.getNombre());
                        }
                    }
                });


        comboBoxProvincia.setConverter(new StringConverter<Provincia>(){
            @Override
            public String toString(Provincia provincia){
                if (provincial == null){
                    return null;
                } else {
                    return provincia.getCodigo()+"-"+provincia.getNombre();
                }
            }
            @Override
            public Provincia fromString(String userId){
                return null;
            }
        });

        if (persona.getFoto() != null){
            String imageFileName=persona.getFoto();
            File file = new File(CARPETA_FOTOS+"/"+imageFileName);
            if (file.exists()){
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"No se encuentra laimagen en "+file.toURI().toString());
                        alert.showAndWait();
            }
        }


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
    public void setDataUtil(DataUtil dataUtil){
        this.dataUtil = dataUtil;
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event){
        File carpetaFotos = new File(CARPETA_FOTOS);
        if (!carpetaFotos.exists()){
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg",
                        "*.png"),
                new FileChooser.ExtensionFilter("Todos los archivos","*.*"));
        File file = fileChooser.showOpenDialog(
                rootPersonaDetalleView.getScene().getWindow());
        if (file != null){
            try {
                Files.copy(file.toPath(),new File(CARPETA_FOTOS+ "/"+file.getName()).toPath());
                persona.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Nombre de archivo duplicado");
                        alert.showAndWait();
            } catch (IOException ex){
                Alert alert = new Alert(Alert.AlertType.WARNING,"No se ha podido guardar la imagen");
                        alert.showAndWait();
            }
        }
    }



    @FXML
    private void onActionSuprimirFoto(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresión de imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen,\n"+ "quitar la foto pero MANTENER el archivo, \no CANCELAR la operación?");
                alert.setContentText("Elija la opción deseada:");
        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar",
                ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener,
                buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeEliminar){
            String imageFileName = persona.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                file.delete();
            }
            persona.setFoto(null);
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener){
            persona.setFoto(null);
            imageViewFoto.setImage(null);
        }
    }


}

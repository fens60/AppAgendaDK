package es.ieslosmontecillos;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlElement;

public class Login {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty clave = new SimpleStringProperty();
    private final BooleanProperty vigencia = new SimpleBooleanProperty();

    // Campo id
    @XmlElement(name = "id")
    public Integer getId(){
        return id.get();
    }
    public IntegerProperty idProperty(){
        return id;
    }
    public void setId(Integer id){
        this.id.set(id);
    }

    // Campo email
    @XmlElement(name = "email")
    public String getemail(){
        return email.get();
    }
    public StringProperty emailProperty(){
        return email;
    }
    public void setemail(String email){
        this.email.set(email);
    }

    // Campo clave
    @XmlElement(name = "clave")
    public String getclave(){
        return clave.get();
    }
    public StringProperty claveProperty(){
        return clave;
    }
    public void setclave(String clave){
        this.clave.set(clave);
    }

    // Campo vigencia
    @XmlElement(name = "vigencia")
    public Boolean getVigencia(){
        return vigencia.get();
    }
    public BooleanProperty vigenciaProperty(){
        return vigencia;
    }
    public void setVigencia(Boolean vigencia){
        this.vigencia.set(vigencia);
    }

}

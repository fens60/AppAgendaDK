package es.ieslosmontecillos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlElement;

public class Usuario {
    private StringProperty usuario = new SimpleStringProperty();
    private StringProperty contrasena = new SimpleStringProperty();
    private StringProperty tipo = new SimpleStringProperty();


    @XmlElement(name = "usuario")
    public String getUsuario() {
        return usuario.get();
    }
    public StringProperty usuarioProperty(){
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }
    //campo codigo
    @XmlElement(name = "contrasena")
    public String getContrasena() {
        return contrasena.get();
    }
    public StringProperty contrasenaProperty(){
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena.set(contrasena);
    }
    //campo nombre
    @XmlElement(name = "tipo")
    public String getTipo() {
        return tipo.get();
    }
    public StringProperty tipoProperty(){
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }
}

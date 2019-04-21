package mx.edu.ittepic.tpdm_u3_practica1_delarosaguerrerogabriela;

public class Docentes {
    String rfc, nombre,apellido, telefono,id;

    public Docentes(String rfc, String nombre, String apellido, String telefono){
        this.rfc = rfc;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }
    public Docentes(){

    }
    public void setId(String id){this.id=id;}
    public String getId(){return id;}
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

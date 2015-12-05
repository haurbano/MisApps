package movil.salt.misapps.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Hamilton Urbano on 5/12/2015.
 */
public class App {
    String nombre,paquete;
    Drawable icono;

    public App() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public Drawable getIcono() {
        return icono;
    }

    public void setIcono(Drawable icono) {
        this.icono = icono;
    }
}

package data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Usuario {

    private final int id;
    private HashMap<Integer, Integer> calificaciones;
    private int sumaValoraciones;
    private final int numValoraciones;
    private final float mediaValoraciones;

    public Usuario(int id, HashMap<Integer, Integer> calificaciones) {
        this.id = id;
        this.calificaciones = new HashMap<>(calificaciones);
        this.sumaValoraciones = 0;

        ArrayList<Integer> valores = new ArrayList<>(calificaciones.values());
        for (int valor : valores) {
            this.sumaValoraciones += valor;
        }

        this.numValoraciones = this.calificaciones.size();
        this.mediaValoraciones = this.sumaValoraciones / this.numValoraciones;
    }

    public int getId() {
        return this.id;
    }

    public int getSumaValoraciones() {
        return this.sumaValoraciones;
    }

    public int getNumValoraciones() {
        return this.numValoraciones;
    }

    public float getMediaValoraciones() {
        return this.mediaValoraciones;
    }

    public HashMap<Integer, Integer> getCalificaciones() {
        return this.calificaciones;
    }
}

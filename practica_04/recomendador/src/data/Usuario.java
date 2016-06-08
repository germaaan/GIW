package data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Usuario {

    private final int id;
    private HashMap<Pelicula, Integer> calificaciones;
    private int calificacionTotal;
    private final int numCalificaciones;
    private final float calificacionPromedio;

    public Usuario(int id, HashMap<Pelicula, Integer> calificaciones) {
        this.id = id;
        this.calificaciones.putAll(calificaciones);
        this.calificacionTotal = 0;

        ArrayList<Integer> valoraciones = new ArrayList<>(calificaciones.values());
        for (int d : valoraciones) {
            this.calificacionTotal += d;
        }

        this.numCalificaciones = this.calificaciones.size();
        this.calificacionPromedio = this.calificacionTotal / this.numCalificaciones;
    }
}

package data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Usuario implements Comparable<Usuario> {

    private final int id;
    private HashMap<Integer, Integer> calificaciones;
    private double similitud;
    private int sumaValoraciones;
    private final int numValoraciones;
    private final double mediaValoraciones;

    public Usuario(int id, HashMap<Integer, Integer> calificaciones) {
        this.id = id;
        this.calificaciones = new HashMap<>(calificaciones);
        this.similitud = 0.0;
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

    public double getMediaValoraciones() {
        return this.mediaValoraciones;
    }

    public HashMap<Integer, Integer> getCalificaciones() {
        return this.calificaciones;
    }

    public double getSimilitud() {
        return this.similitud;
    }

    public void setSimilitud(double similitud) {
        this.similitud = similitud;
    }

    @Override
    public int compareTo(Usuario otro) {
        int returnVal = 0;

        if (this.similitud < otro.similitud) {
            returnVal = 1;
        } else if (this.similitud > otro.similitud) {
            returnVal = -1;
        } else if (this.similitud == otro.similitud) {
            returnVal = 0;
        }
        return returnVal;

    }
}

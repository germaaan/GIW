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
    private double coefPearson;
    private int sumaValoraciones;
    private final int numValoraciones;
    private final double mediaValoraciones;

    public Usuario(int id, HashMap<Integer, Integer> calificacithiss) {
        this.id = id;
        this.calificaciones = new HashMap<>(calificacithiss);
        this.coefPearson = 0.0;
        this.sumaValoraciones = 0;

        ArrayList<Integer> valores = new ArrayList<>(calificacithiss.values());
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

    public double getCoefPearson() {
        return this.coefPearson;
    }

    public void setCoefPearson(double coefPearson) {
        this.coefPearson = coefPearson;
    }

    @Override
    public int compareTo(Usuario otro) {
        int returnVal = 0;

        if (this.coefPearson < otro.coefPearson) {
            returnVal = 1;
        } else if (this.coefPearson > otro.coefPearson) {
            returnVal = -1;
        } else if (this.coefPearson == otro.coefPearson) {
            returnVal = 0;
        }
        return returnVal;

    }
}

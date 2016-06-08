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
    private int valorTotal;
    private final int numValor;
    private final float promedioValor;

    public Usuario(int id, HashMap<Integer, Integer> calificaciones) {
        this.id = id;

        this.calificaciones = new HashMap<>();
        this.calificaciones.putAll(calificaciones);

        this.valorTotal = 0;

        ArrayList<Integer> valoraciones = new ArrayList<>(calificaciones.values());
        for (int d : valoraciones) {
            this.valorTotal += d;
        }

        this.numValor = this.calificaciones.size();
        this.promedioValor = this.valorTotal / this.numValor;
    }

    public int getId() {
        return this.id;
    }

    public int getValorTotal() {
        return this.valorTotal;
    }

    public int getNumValor() {
        return this.numValor;
    }

    public float getPromedioValor() {
        return this.promedioValor;
    }
}

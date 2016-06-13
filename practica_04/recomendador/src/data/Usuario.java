package data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Usuarios existentes en la base de datos.
 *
 * @author Germán Martínez Maldonado
 */
public class Usuario implements Comparable<Usuario> {

    // Identificador del usuario
    private final int id;
    // Parejas película-valoración dada por el usuario
    private final HashMap<Integer, Integer> valoraciones;
    // Grado de similitud con el usuario de la aplicación
    private double similitud;
    // Suma del valor total de las valoraciones
    private int sumaValoraciones;
    // Número de valoraciones dadas
    private final int numValoraciones;
    // Valoración media dada por el usuario
    private final double mediaValoraciones;

    /**
     * Crea un usuario nuevo a partir de un identificador y un conjunto de
     * parejas de valoraciones.
     *
     * @param id
     * @param valoraciones
     */
    public Usuario(int id, HashMap<Integer, Integer> valoraciones) {
        this.id = id;
        this.valoraciones = new HashMap<>(valoraciones);
        this.similitud = 0.0;
        this.sumaValoraciones = 0;

        ArrayList<Integer> valores = new ArrayList<>(valoraciones.values());
        for (int valor : valores) {
            this.sumaValoraciones += valor;
        }

        this.numValoraciones = this.valoraciones.size();
        this.mediaValoraciones = this.sumaValoraciones / this.numValoraciones;
    }

    /**
     * Obtiene el idenficador del usuario.
     *
     * @return Identificador del usuario
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtiene la suma del total de valoraciones del usuario.
     *
     * @return Suma total de valoraciones del usuario
     */
    public int getSumaValoraciones() {
        return this.sumaValoraciones;
    }

    /**
     * Obtiene el número de valoraciones del usuario.
     *
     * @return Número total de valoraciones del usuario
     */
    public int getNumValoraciones() {
        return this.numValoraciones;
    }

    /**
     * Obtiene la valoración media del usuario.
     *
     * @return Valoración media dada por el usuario
     */
    public double getMediaValoraciones() {
        return this.mediaValoraciones;
    }

    /**
     * Obtiene las valoraciones dadas por el usuario.
     *
     * @return Conjunto de parejas de valoraciones dadas por el usuario
     */
    public HashMap<Integer, Integer> getValoraciones() {
        return this.valoraciones;
    }

    /**
     * Obtiene el grado de similitud con el usuario de la aplicación.
     *
     * @return Grado de similitud con el usuario de la aplicación
     */
    public double getSimilitud() {
        return this.similitud;
    }

    /**
     * Establece el grado de similitud con el usuario de la aplicación.
     *
     * @param similitud Nuevo valor para el grado de similitud
     */
    public void setSimilitud(double similitud) {
        this.similitud = similitud;
    }

    /**
     * Devuelve si un usuario es "menor" o "mayor" de otro respecto de su
     * similitud con el usuario de la aplicación.
     *
     * @param otro Usuario con el que se realiza la comparación
     * @return 1 si el usuario tiene una similitud menor con el usuario de la
     * aplicación que el otro usuario, -1 en caso contrario y 0 en caso de que
     * ambos tengan la misma similitu con el usuario de la aplicación
     */
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

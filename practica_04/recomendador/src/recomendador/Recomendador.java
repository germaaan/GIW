package recomendador;

import utils.Utils;
import data.Pelicula;
import data.Usuario;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase principal del sistema de recomendación.
 *
 * @author Germán Martínez Maldonado
 */
public class Recomendador {

    // Métodos de apoyo.
    private final Utils utils = new Utils();

    /**
     * Método principal del sistema de recomendación.
     */
    public static void main(String[] args) {
        Recomendador recomendador = new Recomendador();
    }

    /**
     * Ejecuta el sistema de recomendación para mostrar recomendaciones después
     * de introducir las valoraciones de 20 películas aleatorias.
     */
    public Recomendador() {
        // Carga todas las películas
        ArrayList<Pelicula> peliculas = new ArrayList<>(utils.cargaPeliculas());
        // Carga todos los usuarios con sus valoraciones
        ArrayList<Usuario> usuarios = new ArrayList<>(utils.cargaUsuarios());
        HashMap<Integer, Integer> valoracionesUsuario = new HashMap<>(utils.introduceValoraciones(peliculas));

        // Calcula la similitud de todos los usuarios con el usuario actual
        utils.calculaSimilitudes(usuarios, valoracionesUsuario);
        // Selecciona los 10 usuarios más similares al usuario actual
        ArrayList<Usuario> vecinos = new ArrayList<>(utils.seleccionaVecinos(usuarios));

        // Selecciona las 5 películas más recomendables en función de las
        // valoraciones dadas por los usuarios similares
        utils.muestraRecomendaciones(vecinos, valoracionesUsuario, peliculas);
    }
}

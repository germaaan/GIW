package recomendador;

import utils.Utils;
import data.Pelicula;
import data.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal del sistema de recomendación.
 *
 * @author Germán Martínez Maldonado
 */
public class Recomendador {

    // Clase de métodos de apoyo.
    private final Utils utils = new Utils();

    /**
     * Método principal del sistema de recomendación.
     */
    public static void main(String[] args) {
        Recomendador recomendador = new Recomendador();
    }

    /**
     * Ejecuta el sistema de recomendación para mostrar recomendaciones después
     * de introducir las valoraciones de 15 películas aleatorias.
     */
    public Recomendador(){
        try {
            // Carga todas las películas
            ArrayList<Pelicula> peliculas = new ArrayList<>(utils.cargarPeliculas());
            // Carga todos los usuarios con sus valoraciones
            ArrayList<Usuario> usuarios = new ArrayList<>(utils.cargarUsuarios());
            HashMap<Integer, Integer> calificacionesUsuario = new HashMap<>(utils.introducirValoraciones(peliculas));
            
            // Calcula la similitud de todos los usuarios con el usuario actual
            utils.calculaSimilitudes(usuarios, calificacionesUsuario);
            // Selecciona los 10 usuarios más similares al usuario actual
            ArrayList<Usuario> vecinos = new ArrayList<>(utils.seleccionarVecinos(usuarios));
            
            // Selecciona las 5 películas más recomendables en función de las
            // valoraciones dadas por los usuarios similares
            utils.seleccionarRecomendaciones(vecinos, calificacionesUsuario, peliculas);
        } catch (IOException ex) {
            Logger.getLogger(Recomendador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package recomendador;

import Utils.Utils;
import data.Pelicula;
import data.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Recomendador {

    private Utils utils = new Utils();

    public static void main(String[] args) throws IOException {
        Recomendador recomendador = new Recomendador();
    }

    public Recomendador() throws IOException {
        ArrayList<Pelicula> peliculas = new ArrayList<>(utils.cargarPeliculas());
        ArrayList<Usuario> usuarios = new ArrayList<>(utils.cargarUsuarios());
        HashMap<Integer, Integer> calificacionesUsuario = new HashMap<>(utils.introducirValoraciones(peliculas));

        utils.calculaSimilitudes(usuarios, calificacionesUsuario);
        ArrayList<Usuario> vecinos = new ArrayList<>(utils.seleccionarVecinos(usuarios));

        utils.seleccionarRecomendaciones(vecinos, calificacionesUsuario, peliculas);
    }
}

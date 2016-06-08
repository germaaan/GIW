package recomendador;

import Utils.Utils;
import data.Pelicula;
import data.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

        HashSet<Integer> candidatas = new HashSet<>();

        Iterator<Usuario> it = vecinos.iterator();

        while (it.hasNext()) {
            Usuario vecino = it.next();

            for (int pelicula : vecino.getCalificaciones().keySet()) {
                if (!calificacionesUsuario.containsKey(pelicula)) {
                    candidatas.add(pelicula);
                }
            }
        }

        System.out.println("\nLos usuarios más similares a ti han visto un total de "
                + candidatas.size() + " películas que tú no has visto todavía.");

        Iterator<Integer> it2 = candidatas.iterator();
        int peli = it2.next();

        it = vecinos.iterator();

        System.out.println("Pelicula: " + peli);

        double numerador = 0.0;
        double denominador = 0.0;

        while (it.hasNext()) {
            Usuario vecino = it.next();
            HashMap<Integer, Integer> calificaciones = new HashMap<>(vecino.getCalificaciones());

            if (calificaciones.containsKey(peli)) {
                int calificacion = vecino.getCalificaciones().get(peli);
                double similitud = vecino.getSimilitud();

                if (similitud > 0.5) {
                    numerador += similitud * calificacion;
                } else {
                    numerador -= similitud * calificacion;
                }
                
                denominador += vecino.getSimilitud();
            }
        }

        double prediccion = numerador / denominador;

        System.out.println(prediccion);

        /*
        for (int pelicula : calificacionesUsuario.keySet()) {
            System.out.println(pelicula);
        }
         */
    }

}

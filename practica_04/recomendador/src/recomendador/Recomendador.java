package recomendador;

import Utils.Utils;
import data.Pelicula;
import data.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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

        TreeMap<Integer, Double> predicciones = new TreeMap<>();

        while (it2.hasNext()) {
            int pelicula = it2.next();

            it = vecinos.iterator();

            double numerador = 0.0;
            double denominador = 0.0;

            while (it.hasNext()) {
                Usuario vecino = it.next();
                HashMap<Integer, Integer> calificaciones = new HashMap<>(vecino.getCalificaciones());

                if (calificaciones.containsKey(pelicula)) {
                    int calificacion = vecino.getCalificaciones().get(pelicula);
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

            predicciones.put(pelicula, prediccion);
        }

        SortedMap prediccionesOrdenadas = new TreeMap(new Comparador(predicciones));

        prediccionesOrdenadas.putAll(predicciones);

        for (Iterator iter = prediccionesOrdenadas.keySet().iterator(); iter.hasNext();) {
            Integer key = (Integer) iter.next();
            System.out.println("Value/key:" + prediccionesOrdenadas.get(key) + "/" + key);
        }
    }

    private static class Comparador implements Comparator {

        private Map mapa = null;

        public Comparador(Map data) {
            super();
            this.mapa = data;
        }

        @Override
        public int compare(Object o1, Object o2) {
            Double e1 = (Double) mapa.get(o1);
            Double e2 = (Double) mapa.get(o2);
            return e2.compareTo(e1);
        }
    }

}

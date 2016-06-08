package recomendador;

import Utils.Utils;
import data.Pelicula;
import data.Usuario;
import java.io.IOException;
import static java.lang.Math.sqrt;
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

        Usuario usuario = usuarios.get(0);
        HashMap<Integer, Integer> calificacionesDatos = new HashMap<>(usuario.getCalificaciones());
        float media2 = usuario.getMediaValoraciones();

        int sumaValoraciones = 0;
        ArrayList<Integer> valores = new ArrayList<>(calificacionesUsuario.values());
        for (int valor : valores) {
            sumaValoraciones += valor;
        }
        int numValoraciones = calificacionesUsuario.size();
        float media1 = sumaValoraciones / numValoraciones;

        ArrayList<Integer> coincidencias = new ArrayList<>();

        for (int pelicula : calificacionesUsuario.keySet()) {
            if (calificacionesDatos.containsKey(pelicula)) {
                coincidencias.add(pelicula);
            }
        }

        float numerador = 0;
        float delta1 = 0;
        float delta2 = 0;
        float pearson = 0;

        if (coincidencias.size() > 0) {
            Iterator<Integer> it = coincidencias.iterator();
            while (it.hasNext()) {
                int indice = it.next();
                int u = calificacionesUsuario.get(indice);
                int v = calificacionesDatos.get(indice);
                numerador += ((u - media1) * (v - media2));
                delta1 += Math.pow((u - media1), 2);
                delta2 += Math.pow((v - media2), 2);
            }

            delta1 = (float) sqrt(delta1);
            delta2 = (float) sqrt(delta2);

            pearson = numerador / (delta1 * delta2);
        } else {
            pearson = 0;
        }

        System.out.println("Coeficiente de Pearson: " + pearson);

        //calcular metricas
        //seleccionar vecinos
        //calcular predicciones
    }
}

package recomendador;

import Utils.Utils;
import data.Pelicula;
import data.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

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
        float mediaValoracionesDatos = usuario.getMediaValoraciones();

        int sumaValoraciones = 0;
        ArrayList<Integer> valores = new ArrayList<>(calificacionesUsuario.values());
        for (int valor : valores) {
            sumaValoraciones += valor;
        }
        int numValoraciones = calificacionesUsuario.size();
        float mediaValoracionesUsuario = sumaValoraciones / numValoraciones;

        ArrayList<Integer> coincidencias = new ArrayList<>();

        for (int pelicula : calificacionesUsuario.keySet()) {
            System.out.print("Pelicula: " + pelicula);
            System.out.print(". Valoracion yo: " + calificacionesUsuario.get(pelicula));
            if (calificacionesDatos.containsKey(pelicula)) {
                System.out.println(". Valoracion otro: " + calificacionesDatos.get(pelicula));
                coincidencias.add(pelicula);
            } else {
                System.out.println(". Valoracion otro: -");
            }
        }

        float numerador = 0;

        if (coincidencias.size() > 0) {

            System.out.println("Coincidencias: ");

            Iterator<Integer> it = coincidencias.iterator();
            while (it.hasNext()) {
                int indice = it.next();
                int u = calificacionesUsuario.get(indice);
                int v = calificacionesDatos.get(indice);
                numerador += ((u - mediaValoracionesUsuario) * (v - mediaValoracionesDatos));
                System.out.println(indice + " Yo: " + u + " Tú: " + v);
            }
        }

        System.out.println("Mi media: " + mediaValoracionesUsuario + " Tu media: " + mediaValoracionesDatos);
        System.out.println("Valor numerador: " + numerador);

        //calcular metricas
        //seleccionar vecinos
        //calcular predicciones
    }
}

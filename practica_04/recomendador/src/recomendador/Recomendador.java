package recomendador;

import Utils.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import data.Pelicula;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Recomendador {

    private Utils utils = new Utils();

    public static void main(String[] args) throws IOException {
        Recomendador recomendador = new Recomendador();
        //HashMap<Integer, String> peliculas = new HashMap<>();
        //Multimap<Integer, int[]> valoraciones = HashMultimap.create();
        //HashMap<Integer, Integer> valoracionesUsuario = new HashMap<>();

        /*
        for (Map.Entry<Integer, String> entry : peliculas.entrySet()) {
        System.out.println(entry.getKey() + " : " + entry.getValue());
        }
         */
 /*
        is = Recomendador.class.getResourceAsStream("/resources/u.data");
        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        entrada = br.readLine();
        while (entrada != null) {
            String[] item = entrada.split("\t");

            int usuario = Integer.parseInt(item[0]);
            int pelicula = Integer.parseInt(item[1]);
            int calificacion = Integer.parseInt(item[2]);

            valoraciones.put(usuario, new int[]{pelicula, calificacion});

            entrada = br.readLine();
        }

        br.close();

        Collection<int[]> list = valoraciones.get(1);

        //pedir valoraciones usuario (primero fijas, luego variables)
        Random aleatorio = new Random();
        Scanner in = new Scanner(System.in);
        int num = -1;

        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> id = new ArrayList<>(peliculas.keySet());
            int randomKey = id.get(aleatorio.nextInt(id.size()));
            String nombre = peliculas.get(randomKey);

            boolean error = false;

            do {
                if (!error) {
                    System.out.println("\nIntroduzca valoración para \"" + nombre + "\" (0-5):");
                } else {
                    System.out.println("\nERROR: valoración no válido.");
                    System.out.println("Introduzca valoración para \"" + nombre + "\" (0-5):");
                }

                try {
                    num = Integer.parseInt(in.nextLine());
                } catch (NumberFormatException nfe) {
                    error = true;
                }

                if (num < 0 || num > 5) {
                    error = true;
                    num = -1;
                }

            } while (num == -1);
        }

        //calcular metricas
        //seleccionar vecinos
        //calcular predicciones*/
    }

    public Recomendador() throws IOException {
        ArrayList<Pelicula> peliculas = new ArrayList<>(utils.cargarPeliculas());
    }
}

package recomendador;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import data.Pelicula;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Recomendador {
    private ArrayList<Pelicula> peliculas;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        HashMap<Integer, String> peliculas = new HashMap<>();
        Multimap<Integer, int[]> valoraciones = HashMultimap.create();
        HashMap<Integer, Integer> valoracionesUsuario = new HashMap<>();

        InputStream is = Recomendador.class.getResourceAsStream("/resources/u.item");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String entrada = br.readLine();
        while (entrada != null) {
            String[] item = entrada.split("\\|");

            int id = Integer.parseInt(item[0]);
            String nombre = item[1];

            peliculas.put(id, nombre);

            entrada = br.readLine();
        }

        br.close();

        /*
        for (Map.Entry<Integer, String> entry : peliculas.entrySet()) {
        System.out.println(entry.getKey() + " : " + entry.getValue());
        }
         */
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
        //calcular predicciones
    }
}

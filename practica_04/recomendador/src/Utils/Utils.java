package Utils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import data.Pelicula;
import data.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import recomendador.Recomendador;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Utils {

    public ArrayList<Pelicula> cargarPeliculas() throws UnsupportedEncodingException, IOException {
        ArrayList<Pelicula> peliculas = new ArrayList();

        InputStream is = Recomendador.class.getResourceAsStream("/resources/u.item");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String entrada = br.readLine();
        while (entrada != null) {
            String[] item = entrada.split("\\|");

            int id = Integer.parseInt(item[0]);
            String nombre = item[1];

            peliculas.add(new Pelicula(id, nombre));

            entrada = br.readLine();
        }

        br.close();

        return peliculas;
    }

    public ArrayList<Usuario> cargarUsuarios() throws UnsupportedEncodingException, IOException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Multimap<Integer, int[]> valoracionesOrdenadas = HashMultimap.create();

        InputStream is = Recomendador.class.getResourceAsStream("/resources/u.data");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String entrada = br.readLine();
        while (entrada != null) {
            String[] item = entrada.split("\t");

            int usuario = Integer.parseInt(item[0]);
            int pelicula = Integer.parseInt(item[1]);
            int calificacion = Integer.parseInt(item[2]);

            valoracionesOrdenadas.put(usuario, new int[]{pelicula, calificacion});

            entrada = br.readLine();
        }

        br.close();

        Iterator<Integer> it = valoracionesOrdenadas.keySet().iterator();
        while (it.hasNext()) {
            int usuario = it.next();

            Collection<int[]> valores = valoracionesOrdenadas.get(usuario);
            Iterator<int[]> it2 = valores.iterator();

            HashMap<Integer, Integer> valoracionesUsuario = new HashMap<>();
            while (it2.hasNext()) {
                int par[] = it2.next();
                valoracionesUsuario.put(par[0], par[1]);
            }

            usuarios.add(new Usuario(usuario, valoracionesUsuario));
        }

        return usuarios;
    }

    public HashMap<Integer, Integer> introducirValoraciones(ArrayList<Pelicula> peliculas) {
        HashMap<Integer, Integer> valoraciones = new HashMap<>();

        Random aleatorio = new Random();
        Scanner in = new Scanner(System.in);
        int num = -1;

        for (int i = 0; i < 10; i++) {
            int index = aleatorio.nextInt(peliculas.size());
            peliculas.get(index);
            String nombre = peliculas.get(index).getTitulo();

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

            valoraciones.put(peliculas.get(index).getId(), num);
        }

        return valoraciones;
    }
}

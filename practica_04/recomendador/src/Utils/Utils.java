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
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
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

        for (int i = 0; i < 20; i++) {
            int index = aleatorio.nextInt(peliculas.size());
            peliculas.get(index);
            String nombre = peliculas.get(index).getTitulo();

            boolean error = false;
            do {
                if (!error) {
                    System.out.println("\nIntroduzca valoración para \"" + nombre + "\" (1-5):");
                } else {
                    System.out.println("\nERROR: valoración no válido.");
                    System.out.println("Introduzca valoración para \"" + nombre + "\" (1-5):");
                }

                try {
                    //num = Integer.parseInt(in.nextLine());
                    num = aleatorio.nextInt(5) + 1;
                } catch (NumberFormatException nfe) {
                    error = true;
                }

                if (num <= 0 || num > 5) {
                    error = true;
                    num = -1;
                }

            } while (num == -1);

            System.out.println(num);
            valoraciones.put(peliculas.get(index).getId(), num);
        }

        return valoraciones;
    }

    public void calculaSimilitudes(ArrayList<Usuario> usuarios, HashMap<Integer, Integer> calificacionesUsuario) {
        Iterator<Usuario> it = usuarios.iterator();

        while (it.hasNext()) {
            Usuario usuario = it.next();
            HashMap<Integer, Integer> calificacionesDatos = new HashMap<>(usuario.getCalificaciones());
            double media2 = usuario.getMediaValoraciones();

            int sumaValoraciones = 0;
            ArrayList<Integer> valores = new ArrayList<>(calificacionesUsuario.values());
            for (int valor : valores) {
                sumaValoraciones += valor;
            }
            int numValoraciones = calificacionesUsuario.size();
            double media1 = sumaValoraciones / numValoraciones;

            ArrayList<Integer> coincidencias = new ArrayList<>();

            for (int pelicula : calificacionesUsuario.keySet()) {
                if (calificacionesDatos.containsKey(pelicula)) {
                    coincidencias.add(pelicula);
                }
            }

            double numerador = 0.0;
            double delta1 = 0;
            double delta2 = 0;
            double similitud = 0.0;

            int n = coincidencias.size();

            if (n > 0) {

                Iterator<Integer> it2 = coincidencias.iterator();
                while (it2.hasNext()) {
                    int indice = it2.next();
                    int u = calificacionesUsuario.get(indice);
                    int v = calificacionesDatos.get(indice);

                    double aux1 = u - media1;
                    double aux2 = v - media2;

                    numerador += aux1 * aux2;

                    delta1 += aux1 * aux1;
                    delta2 += aux2 * aux2;
                }

                if (delta1 == 0.0 || delta2 == 0.0) {
                    similitud = -1.0;
                } else {
                    similitud = numerador / (Math.sqrt(delta1) * Math.sqrt(delta2));
                }
            } else {
                similitud = -1.0;
            }

            usuario.setSimilitud(similitud);
        }
    }

    public ArrayList<Usuario> seleccionarVecinos(ArrayList<Usuario> usuarios) {
        Collections.sort(usuarios);

        ArrayList<Usuario> vecinos = new ArrayList<>(usuarios.subList(0, 10));

        return vecinos;
    }

    public void seleccionarRecomendaciones(ArrayList<Usuario> vecinos, HashMap<Integer, Integer> calificacionesUsuario, ArrayList<Pelicula> peliculas) {
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

        HashMap<Integer, String> listaPeliculas = new HashMap<>();
        Iterator<Pelicula> it3 = peliculas.iterator();
        while (it3.hasNext()) {
            Pelicula pelicula = it3.next();
            listaPeliculas.put(pelicula.getId(), pelicula.getTitulo());
        }

        int aux = 1;

        System.out.println("\nTus recomendaciones son:");
        for (Iterator iter = prediccionesOrdenadas.keySet().iterator(); iter.hasNext();) {
            Integer key = (Integer) iter.next();

            System.out.println(aux + "º. " + listaPeliculas.get(key) + ": " + prediccionesOrdenadas.get(key));
            aux++;

            if (aux == 6) {
                break;
            }
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

package utils;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import recomendador.Recomendador;

/**
 * Clase con métodos de apoyo.
 *
 * @author Germán Martínez Maldonado
 */
public class Utils {

    /**
     * Carga todas las películas en el archivo de películas.
     *
     * @return Lista de películas contenidas en el archivo de películas
     */
    public ArrayList<Pelicula> cargaPeliculas() {
        ArrayList<Pelicula> peliculas = new ArrayList();

        InputStream is = null;
        BufferedReader br = null;

        try {
            // Lee el archivo "u.item" con las películas
            is = Recomendador.class.getResourceAsStream("/resources/u.item");
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String entrada = br.readLine();

            while (entrada != null) {
                // Lee y añade cada una de las películas
                String[] item = entrada.split("\\|");

                int id = Integer.parseInt(item[0]);
                String nombre = item[1];

                peliculas.add(new Pelicula(id, nombre));

                entrada = br.readLine();
            }

            br.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return peliculas;
    }

    /**
     * Carga todos los usuarios en el archivo de usuarios.
     *
     * @return Lista de usuarios de los que hay valoraciones en el archivo de
     * valoraciones
     */
    public ArrayList<Usuario> cargaUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        // Mapa de valor múltiple para agrupar todas las valoraciones de un mismo
        // usuario según su identificador
        Multimap<Integer, int[]> valoraciones = HashMultimap.create();

        InputStream is = null;
        BufferedReader br = null;

        try {
            // Lee el archivo "u.item" con todas las valoraciones dadas por usuarios
            is = Recomendador.class.getResourceAsStream("/resources/u.data");
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String entrada = br.readLine();

            while (entrada != null) {
                // Lee cada una de las valoraciones y las añade al mapa
                String[] item = entrada.split("\t");

                int usuario = Integer.parseInt(item[0]);
                int pelicula = Integer.parseInt(item[1]);
                int valoracion = Integer.parseInt(item[2]);

                valoraciones.put(usuario, new int[]{pelicula, valoracion});

                entrada = br.readLine();
            }

            br.close();

            // Recorre el mapa en función de las claves (identificador de usuario)
            Iterator<Integer> it = valoraciones.keySet().iterator();

            while (it.hasNext()) {
                int usuario = it.next();

                Collection<int[]> valores = valoraciones.get(usuario);
                Iterator<int[]> it2 = valores.iterator();

                // Añade en una mismo mapa todas las valoraciones del usuario
                // tomando ahora el identificador de la película como clave
                HashMap<Integer, Integer> valoracionesUsuario = new HashMap<>();
                while (it2.hasNext()) {
                    int par[] = it2.next();
                    valoracionesUsuario.put(par[0], par[1]);
                }

                // Crea el usuario con su identificador y todas sus valoraciones
                usuarios.add(new Usuario(usuario, valoracionesUsuario));
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return usuarios;
    }

    /**
     * Pide al usuario de la aplicación que califique 20 películas aleatorias
     *
     * @param peliculas Lista de películas
     * @return Valoraciones dadas
     */
    public HashMap<Integer, Integer> introduceValoraciones(ArrayList<Pelicula> peliculas) {
        HashMap<Integer, Integer> valoraciones = new HashMap<>();

        Random aleatorio = new Random();
        Scanner in = new Scanner(System.in);
        int num = -1;

        // Pide la valoración para 20 películas elegidas de forma aleatoria
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
                    num = Integer.parseInt(in.nextLine());
                } catch (NumberFormatException nfe) {
                    error = true;
                }

                // Solo se consideran valoraciones válidas aquellas entre 1-5
                if (num <= 0 || num > 5) {
                    error = true;
                    num = -1;
                }

            } while (num == -1);

            valoraciones.put(peliculas.get(index).getId(), num);
        }

        return valoraciones;
    }

    /**
     * Calcula la similitud de los usuarios en la base de datos con el usuario
     * actual.
     *
     * @param usuarios Lista de usuarios en la base de datos
     * @param valoracionesUsuario Valoraciones dadas por el usuario actual
     */
    public void calculaSimilitudes(ArrayList<Usuario> usuarios, HashMap<Integer, Integer> valoracionesUsuario) {
        // Calculamos el valor total de las valoraciones, el número de valoraciones
        // dadas y la valoración media de el usuario actual
        int sumaValoracionesUsuario = 0;

        ArrayList<Integer> valores = new ArrayList<>(valoracionesUsuario.values());

        for (int valor : valores) {
            sumaValoracionesUsuario += valor;
        }

        int numValoracionesUsuario = valoracionesUsuario.size();
        double mediaUsuario = sumaValoracionesUsuario / numValoracionesUsuario;

        Iterator<Usuario> it = usuarios.iterator();

        // Para cada usuario en la base de datos
        while (it.hasNext()) {
            Usuario usuario = it.next();
            HashMap<Integer, Integer> valoracionesDatos = new HashMap<>(usuario.getValoraciones());
            double media = usuario.getMediaValoraciones();

            ArrayList<Integer> coincidencias = new ArrayList<>();

            // Extraemos las películas que han valorado tanto el usuario en base
            // de datos como el usuario actual
            for (int pelicula : valoracionesUsuario.keySet()) {
                if (valoracionesDatos.containsKey(pelicula)) {
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

                // Calculamos las componentes del coeficiente de correlación 
                // de Pearson
                while (it2.hasNext()) {
                    int indice = it2.next();
                    int u = valoracionesUsuario.get(indice);
                    int v = valoracionesDatos.get(indice);

                    double aux1 = u - mediaUsuario;
                    double aux2 = v - media;

                    numerador += aux1 * aux2;

                    delta1 += aux1 * aux1;
                    delta2 += aux2 * aux2;
                }

                // Aplicamos la fórmula del coeficiente (siempre y cuando no haya
                // ningún 0 en el denonimador)
                if (delta1 == 0.0 || delta2 == 0.0) {
                    similitud = -1.0;
                } else {
                    similitud = numerador / (Math.sqrt(delta1) * Math.sqrt(delta2));
                }
                // Si ambos usuarios no han valorado ninguna misma película, no se
                // calcula el coeficiente de correlación
            } else {
                similitud = -1.0;
            }

            usuario.setSimilitud(similitud);
        }
    }

    /**
     * Selecciona los 10 usuarios más similares al usuario actual en función de
     * las valoraciones dadas a las mismas películas.
     *
     * @param usuarios Lista de usuarios en la base de datos
     * @return Lista con los 10 usuarios más similares al usuario actual
     */
    public ArrayList<Usuario> seleccionaVecinos(ArrayList<Usuario> usuarios) {
        // Ordenamos la lista de usuarios de mayor a menor según el valor de su
        // similitud con el usuario actual
        Collections.sort(usuarios);

        // Creamos una lista de usuarios con los 10 primeros de la lista
        ArrayList<Usuario> vecinos = new ArrayList<>(usuarios.subList(0, 10));

        return vecinos;
    }

    /**
     * Muestra las recomendaciones de películas para el usuario actual.
     *
     * @param vecinos Lista con los usuarios más similares al usuario actual
     * @param valoracionesUsuario Valoraciones dadas por el usuario actual
     * @param peliculas Lista de películas
     */
    public void muestraRecomendaciones(ArrayList<Usuario> vecinos, HashMap<Integer, Integer> valoracionesUsuario, ArrayList<Pelicula> peliculas) {
        HashSet<Integer> candidatas = new HashSet<>();

        Iterator<Usuario> it = vecinos.iterator();

        // Añade las películas vistas por los usuarios más similares que no haya
        // visto el usuario actual
        while (it.hasNext()) {
            Usuario vecino = it.next();

            for (int pelicula : vecino.getValoraciones().keySet()) {
                if (!valoracionesUsuario.containsKey(pelicula)) {
                    candidatas.add(pelicula);
                }
            }
        }

        Iterator<Integer> it2 = candidatas.iterator();

        TreeMap<Integer, Double> predicciones = new TreeMap<>();

        while (it2.hasNext()) {
            int pelicula = it2.next();

            it = vecinos.iterator();

            double numerador = 0.0;
            double denominador = 0.0;

            while (it.hasNext()) {
                Usuario vecino = it.next();
                HashMap<Integer, Integer> valoraciones = new HashMap<>(vecino.getValoraciones());

                // Añade el valor ponderado correspondiente a la calificación
                // que el usuario le dio a la película (si el usuario ha 
                // calificado dicha película)
                if (valoraciones.containsKey(pelicula)) {
                    int valoracion = vecino.getValoraciones().get(pelicula);
                    double similitud = vecino.getSimilitud();

                    // La ponderación consiste en que si el usuario tiene una 
                    // buena similitud con el usuario actual su valoración suma,
                    // en caso contrario resta
                    if (similitud > 0.5) {
                        numerador += similitud * valoracion;
                    } else {
                        numerador -= similitud * valoracion;
                    }

                    denominador += vecino.getSimilitud();
                }
            }

            // Calcula la predicción para una película en función de todos las 
            // valoraciones ponderadas dadas por los distintos usuarios
            double prediccion = numerador / denominador;

            predicciones.put(pelicula, prediccion);
        }

        // Ordena la lista de películas en función de su valor predicho de mayor
        // a menor
        SortedMap prediccionesOrdenadas = new TreeMap(new Comparador(predicciones));

        prediccionesOrdenadas.putAll(predicciones);

        // Obtiene los datos de cada unas de las películas consideradas para la
        // predicción
        HashMap<Integer, String> listaPeliculas = new HashMap<>();
        Iterator<Pelicula> it3 = peliculas.iterator();
        while (it3.hasNext()) {
            Pelicula pelicula = it3.next();
            listaPeliculas.put(pelicula.getId(), pelicula.getTitulo());
        }

        int aux = 1;

        // Se muestran las 5 primeras predicciones (siempre y cuando se valoración
        // predicho sea mayor que 4/5)
        System.out.println("\nPeliculas que te pueden interesar (valoracion predicho > 4):");
        for (Iterator iter = prediccionesOrdenadas.keySet().iterator(); iter.hasNext();) {
            Integer key = (Integer) iter.next();
            String titulo = listaPeliculas.get(key);
            Double prediccion = (Double) prediccionesOrdenadas.get(key);

            if (prediccion >= 4) {
                System.out.println(aux + "º. " + titulo + ": " + prediccion);
                aux++;

                if (aux == 6) {
                    break;
                }
            }
        }
    }

    /**
     * Comparador para ordenar las películas en función de su valor de
     * predicción
     */
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

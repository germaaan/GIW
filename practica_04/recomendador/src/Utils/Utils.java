package Utils;

import data.Pelicula;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
}

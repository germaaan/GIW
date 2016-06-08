package recomendador;

import Utils.Utils;
import data.Pelicula;
import data.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

        utils.calculaSimilitudes(usuarios, calificacionesUsuario);
        
        Collections.sort(usuarios);

        for (int i = 0; i < 10; i++) {
            Usuario usuario = usuarios.get(i);
            System.out.println("Usuario: " + usuario.getId() + " Coef: " + usuario.getCoefPearson());
        }

        ArrayList<Usuario> vecinos = new ArrayList<Usuario>(usuarios.subList(0, 10));
        System.out.println(vecinos.size());

        Iterator<Usuario> it = vecinos.iterator();

        while (it.hasNext()) {
            Usuario usuario = it.next();
            System.out.println("Usuario: " + usuario.getId() + " Coef: " + usuario.getCoefPearson());
        }
        
        //calcular predicciones
    }

}

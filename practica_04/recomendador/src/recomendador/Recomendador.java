package recomendador;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Recomendador {
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        HashMap<Integer, String> peliculas = new HashMap<>();
        Multimap<Integer, int[]> valoraciones = HashMultimap.create();
        
        InputStream is = Recomendador.class.getResourceAsStream("/resources/u.item");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        
        String entrada = br.readLine();
        while (entrada != null){
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
        while (entrada != null){
            String[] item = entrada.split("\t");
            
            int usuario = Integer.parseInt(item[0]);
            int pelicula = Integer.parseInt(item[1]);
            int calificacion = Integer.parseInt(item[2]);
            
            valoraciones.put(usuario, new int[]{pelicula, calificacion});
            
            entrada = br.readLine();
        }
        
        br.close();
        
        Collection<int[]> list = valoraciones.get(1);
        
        System.out.println(list.size());
        
        //pedir valoraciones usuario (primero fijas, luego variables)
        //calcular vecinos
        //calcular predicciones
    }
}

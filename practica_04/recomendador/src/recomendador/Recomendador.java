package recomendador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Recomendador {
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        HashMap<Integer, String> peliculas = new HashMap<>();
        
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
        
        for (Map.Entry<Integer, String> entry : peliculas.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        
        //Cargar valoraciones
        //pedir valoraciones usuario (primero fijas, luego variables)
        //calcular vecinos
        //calcular predicciones
    }
}

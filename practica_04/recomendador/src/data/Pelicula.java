package data;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Pelicula {
    private final int id;
    private final String titulo;
    
    public Pelicula(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getTitulo(){
        return this.titulo;
    }
}

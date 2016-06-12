package data;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Pelicula {

    // Código de identificación de la película
    private final int id;
    // Título de la película
    private final String titulo;

    /**
     * Crea una película nueva a partir de un código de identificación 
     * y un título.
     *
     * @param id Código de identificación de la película
     * @param titulo Título de la película
     */
    public Pelicula(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    /**
     * Obtiene el código de identificación de la película.
     * 
     * @return Código de identificación de la película
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtiene el título de la película.
     * 
     * @return Título de la película
     */
    public String getTitulo() {
        return this.titulo;
    }
}

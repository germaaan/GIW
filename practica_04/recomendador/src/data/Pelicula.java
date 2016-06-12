package data;

/**
 * Película existentes en la base de datos.
 *
 * @author Germán Martínez Maldonado
 */
public class Pelicula {

    // Identificador de la película
    private final int id;
    // Título de la película
    private final String titulo;

    /**
     * Crea una película nueva a partir de un identificador y un título.
     *
     * @param id Identificador de la película
     * @param titulo Título de la película
     */
    public Pelicula(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    /**
     * Obtiene el código de identificación de la película.
     *
     * @return Identificador de la película
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

package utils;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Noticia {

    private String titulo;
    private String texto;

    public Noticia(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Título:\n" + this.titulo + "\nTexto:\n" + this.texto + "\n";
    }
}

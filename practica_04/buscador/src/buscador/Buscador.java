package buscador;

import com.sun.javafx.util.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Buscador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String indice = "/home/germaaan/GIW/indice";
            String palabrasVacias = "/home/germaaan/GIW/palabras_vacias_utf8.txt";
            String busqueda = "president";

            ArrayList<String> resultados = new ArrayList<>();

            System.out.println("Realizando búsqueda.... ");

            DirectoryReader ireader = DirectoryReader.open(FSDirectory.open(new File(indice)));
            IndexSearcher isearcher = new IndexSearcher(ireader);

            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43, getPalabrasVacias(palabrasVacias));

            QueryParser parser = new QueryParser(Version.LUCENE_43, "texto", analyzer);
            Query query = parser.parse(busqueda);

            ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;

            if (hits.length == 0) {
                System.out.println("No se han encontrado resultados para la búsqueda \""
                        + busqueda + "\".");
            } else {
                System.out.println(hits.length + "resultados para la búsqueda \""
                        + busqueda + "\":");

                for (int i = 0; i < hits.length; i++) {
                    Document hitDoc = isearcher.doc(hits[i].doc);
                    String titulo = hitDoc.get("titulo");
                    System.out.println("\t" + titulo);
                    resultados.add(titulo);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static CharArraySet getPalabrasVacias(String ruta) {
        CharArraySet stopSet = new CharArraySet(Version.LUCENE_43,
                Arrays.asList(new String[]{""}), true);

        try {
            String[] words = StringUtils.split(
                    FileUtils.readFileToString(new File(ruta), "UTF-8"));
            ArrayList<String> stopWords = new ArrayList(Arrays.asList(words));

            stopSet = new CharArraySet(Version.LUCENE_43, stopWords, true);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stopSet;
    }

}

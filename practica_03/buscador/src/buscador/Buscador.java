package buscador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    public Map<String, String> buscar(String indice, String busqueda) {
        HashMap<String, String> resultados = new HashMap<>();

        CharArraySet stopSet = new CharArraySet(Version.LUCENE_43,
                Arrays.asList(new String[]{""}), true);

        try {
            DirectoryReader ireader = DirectoryReader.open(FSDirectory.open(new File(indice)));
            IndexSearcher isearcher = new IndexSearcher(ireader);

            File palabrasVacias = new File("../palabras_vacias_utf8.txt");

            String[] words = StringUtils.split(
                    FileUtils.readFileToString(palabrasVacias, "UTF-8"));
            ArrayList<String> stopWords = new ArrayList(Arrays.asList(words));

            stopSet = new CharArraySet(Version.LUCENE_43, stopWords, true);

            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43, stopSet);

            QueryParser parser = new QueryParser(Version.LUCENE_43, "texto", analyzer);
            Query query = parser.parse(busqueda);

            ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;

            if (hits.length > 0) {
                for (ScoreDoc hit : hits) {
                    Document hitDoc = isearcher.doc(hit.doc);
                    String titulo = hitDoc.get("titulo");
                    String texto = hitDoc.get("texto");
                    
                    resultados.put(titulo, texto);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultados;
    }
}

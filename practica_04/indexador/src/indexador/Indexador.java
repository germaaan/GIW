package indexador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import parser.Noticia;
import parser.Parser;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Indexador {

    private IndexWriter iwriter;
    private ArrayList<File> listaArchivos = new ArrayList();

    public Indexador(String rutaIndice) throws IOException {
        Analyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
        FSDirectory directory = FSDirectory.open(new File(rutaIndice));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);

        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        this.iwriter = new IndexWriter(directory, config);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String rutaIndice = System.getProperty("user.dir") + "/src/indice";
        Indexador indexador = null;

        Parser parser = new Parser();

        parser.SGML2XML();

        ArrayList<Noticia> listaNoticias = new ArrayList(parser.parseXML());
        Iterator<Noticia> iterador = listaNoticias.iterator();

        while (iterador.hasNext()) {
            System.out.print(iterador.next().toString());
        }
    }

}

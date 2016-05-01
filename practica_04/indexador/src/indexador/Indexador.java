package indexador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
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

        String ruta = "/home/germaaan/GIW/documentos/";

        Parser parser = new Parser();

        ArrayList<String> listaArchivos = new ArrayList(parser.getListaArchivos(ruta));
        ArrayList<String> listaXML = new ArrayList();
        ArrayList<Noticia> listaNoticias = new ArrayList();

        System.out.println("Convirtiendo a XML todos los archivos SGML...");

        Iterator<String> iterador = listaArchivos.iterator();
        while (iterador.hasNext()) {
            String archivo = iterador.next();

            parser.SGML2XML(ruta, archivo);
            listaXML.add((archivo.substring(0, archivo.lastIndexOf('.'))) + ".xml");
        }
        System.out.println("Recuperando toda la información de los archivos...");

        iterador = listaXML.iterator();
        
        while (iterador.hasNext()) {
            listaNoticias.addAll(parser.parseXML(ruta, iterador.next()));
        }
        
        System.out.println("Número de noticias recuperadas: " + listaNoticias.size());

        System.out.println("Borrando archivos temporales...");

        iterador = listaXML.iterator();
        while (iterador.hasNext()) {
            File aux = new File(ruta + iterador.next());
            aux.delete();
        }
    }
}

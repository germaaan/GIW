package indexador;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import parser.Noticia;
import parser.Parser;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Indexador {

    Parser parser = new Parser();

    public Indexador(String documentos, String palabrasVacias, String indice) {
        try {
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43,
                    parser.getPalabrasVacias(palabrasVacias));

            System.out.println("Número de palabras vacías reconocidas: " + analyzer.getStopwordSet().size());

            System.out.println("Convirtiendo a XML todos los archivos SGML...");
            ArrayList<String> archivos = new ArrayList(convertirDocumentos(documentos));

            System.out.println("Recuperando toda la información de los archivos...");
            ArrayList<Noticia> noticias = new ArrayList(procesarDocumentos(documentos, archivos));

            System.out.println("Número de noticias recuperadas: " + noticias.size());

            Directory directory = FSDirectory.open(new File(indice));

            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);
            IndexWriter iwriter = new IndexWriter(directory, config);

            Iterator<Noticia> iterador = noticias.iterator();
            while (iterador.hasNext()) {
                Noticia noticia = iterador.next();

                Document doc = new Document();

                doc.add(new Field("titulo", noticia.getTitulo(), Field.Store.YES, Field.Index.ANALYZED));
                //Añadir tokenstream filters
                doc.add(new TextField("texto", new StringReader(noticia.getTexto())));

                iwriter.addDocument(doc);
            }

            System.out.println("Número de documentos indexados: " + iwriter.numDocs());
            System.out.println("Borrando archivos temporales...");
            eliminarTemporales(documentos, archivos);
            System.out.println("Indexado finalizado.");
            iwriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<String> convertirDocumentos(String documentos) {
        ArrayList<String> archivos = new ArrayList(parser.getListaArchivos(documentos));
        ArrayList<String> xmls = new ArrayList();

        Iterator<String> iterador = archivos.iterator();
        while (iterador.hasNext()) {
            String archivo = iterador.next();

            parser.SGML2XML(documentos, archivo);
            xmls.add((archivo.substring(0, archivo.lastIndexOf('.'))) + ".xml");
        }

        return xmls;
    }

    private ArrayList<Noticia> procesarDocumentos(String documentos, ArrayList<String> archivos) {
        ArrayList<Noticia> noticias = new ArrayList();

        Iterator<String> iterador = archivos.iterator();
        while (iterador.hasNext()) {
            noticias.addAll(parser.parseXML(documentos, iterador.next()));
        }

        return noticias;
    }

    private void eliminarTemporales(String documentos, ArrayList<String> archivos) {
        Iterator<String> iterador = archivos.iterator();

        while (iterador.hasNext()) {
            File aux = new File(documentos + iterador.next());
            aux.delete();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String documentos = "/home/germaaan/GIW/documentos/";
        String palabrasVacias = "/home/germaaan/GIW/palabras_vacias_utf8.txt";
        String indice = "/home/germaaan/GIW/indice";

        Indexador indexador = new Indexador(documentos, palabrasVacias, indice);
    }
}

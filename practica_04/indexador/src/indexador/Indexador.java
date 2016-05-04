package indexador;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import static org.apache.lucene.index.DirectoryReader.indexExists;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import utils.Utils;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Indexador {

    private Utils utils = new Utils();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Introduzca la ruta del directorio donde se encuentra la "
                    + "colección documental a indexar: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String documentos = br.readLine();

            System.out.println("\nIntroduzca la ruta del archivo de palabras vacías a emplear "
                    + "por el analizador: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            String palabrasVacias = br.readLine();

            System.out.println("\nIntroduzca la ruta donde alojar los índices: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            String indice = br.readLine();

            File dirDocumentos = new File(documentos);
            File arcPalabrasVacias = new File(palabrasVacias);
            File dirIndices = new File(indice);

            if (dirDocumentos.exists() && dirDocumentos.isDirectory()) {
                if (arcPalabrasVacias.exists()) {
                    if (dirIndices.exists() && dirIndices.isDirectory()) {
                        if (documentos.charAt(documentos.length() - 1) != '/') {
                            documentos += "/";
                        }

                        if (indice.charAt(indice.length() - 1) != '/') {
                            indice += "/";
                        }

                        Indexador indexador = new Indexador(documentos, palabrasVacias,
                                indice);
                    } else {
                        System.err.println("\n\nEl directorio introducido para los índices no "
                                + "es válido.");
                    }
                } else {
                    System.err.println("\n\nEl archivo de palabras vacías introducido no es "
                            + "válido.");
                }
            } else {
                System.err.println("\n\nEl directorio introducido para la colección documental "
                        + "no es válido.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Indexador(String documentos, String palabrasVacias, String indice) {
        try {
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43,
                    utils.getPalabrasVacias(palabrasVacias));

            System.out.println("\n\nNúmero de palabras vacías reconocidas: " + analyzer.getStopwordSet().size());

            System.out.println("Convirtiendo a XML todos los archivos SGML...");
            ArrayList<String> archivos = new ArrayList(convertirDocumentos(documentos));

            System.out.println("Recuperando toda la información de los archivos...");
            HashMap<String, String> noticias = new HashMap<>(procesarDocumentos(documentos, archivos));

            System.out.println("Número de noticias recuperadas: " + noticias.size());

            Directory directory = FSDirectory.open(new File(indice));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);
            IndexWriter iwriter = new IndexWriter(directory, config);

            boolean existeIndice = indexExists(FSDirectory.open(new File(indice)));

            if (existeIndice) {
                System.out.println("Índice existente en \"" + indice + "\". Eliminando...");
                iwriter.deleteAll();
            }

            System.out.println("Creando nuevo índice...");
            Iterator<Map.Entry<String, String>> iterador = noticias.entrySet().iterator();
            while (iterador.hasNext()) {
                Map.Entry par = (Map.Entry) iterador.next();

                Document doc = new Document();

                String titulo = par.getKey().toString().trim().replaceAll("\n+", "\n").replaceAll("\n", ": ").replaceAll(" +", " ");
                String texto = par.getValue().toString().trim().replaceAll(" +", " ");

                doc.add(new StringField("titulo", titulo, Field.Store.YES));
                doc.add(new TextField("texto", texto, Field.Store.YES));

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
        ArrayList<String> archivos = new ArrayList(utils.getListaArchivos(documentos));
        ArrayList<String> xmls = new ArrayList();

        Iterator<String> iterador = archivos.iterator();
        while (iterador.hasNext()) {
            String archivo = iterador.next();

            utils.SGML2XML(documentos, archivo);
            xmls.add((archivo.substring(0, archivo.lastIndexOf('.'))) + ".xml");
        }

        return xmls;
    }

    private Map<String, String> procesarDocumentos(String documentos, ArrayList<String> archivos) {
        HashMap<String, String> noticias = new HashMap<>();

        Iterator<String> iterador = archivos.iterator();
        while (iterador.hasNext()) {
            noticias.putAll(utils.parseXML(documentos, iterador.next()));
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
}

package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Utils {

    public ArrayList<String> getListaArchivos(String ruta) {
        ArrayList<String> listaArchivos = new ArrayList<>();
        File[] archivos = new File(ruta).listFiles();

        for (File f : archivos) {
            if (f.isFile()) {
                String nombre = f.getName();
                String extension = nombre.substring(nombre.lastIndexOf('.') + 1, nombre.length());

                if (extension.equals("SGML") || extension.equals("sgml")) {
                    listaArchivos.add(nombre);
                }
            }
        }

        return listaArchivos;
    }

    public CharArraySet getPalabrasVacias(String ruta) {
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

    public void SGML2XML(String ruta, String archivo) {
        try {
            File archivoEntrada = new File(ruta + archivo);
            File archivoSalida = new File(ruta + (archivo.substring(0, archivo.lastIndexOf('.'))) + ".xml");

            String contenido = FileUtils.readFileToString(archivoEntrada, "ISO_8859_1");
            contenido = contenido.replace("&", "");
            contenido = contenido.replace(">< ", ">");
            contenido = contenido.replace("<\n</", "<");
            contenido = contenido.replace("<TEXT>\n</DOC>", "</TEXT>\n</DOC>");
            contenido = contenido.replace("<                       \n                                                                             ...\n</", "\n</");

            String cadena = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "\n<SGML>\n" + contenido + "\n</SGML>";

            FileUtils.writeStringToFile(archivoSalida, cadena, "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, String> parseXML(String ruta, String archivo) {
        File archivoEntrada = new File(ruta + archivo);
        HashMap<String, String> noticias = new HashMap<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoEntrada);
            doc.getDocumentElement().normalize();

            NodeList nodos = doc.getElementsByTagName("DOC");

            for (int temp = 0; temp < nodos.getLength(); temp++) {
                Node nodo = nodos.item(temp);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    String titulo = elemento.getElementsByTagName("TITLE").item(0).getTextContent();
                    String texto = elemento.getElementsByTagName("TEXT").item(0).getTextContent();

                    noticias.put(titulo, texto);
                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return noticias;
    }
}

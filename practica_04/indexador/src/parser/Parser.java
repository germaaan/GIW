package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Parser {

    public ArrayList<String> getListaArchivos(String ruta) {
        ArrayList<String> listaArchivos = new ArrayList<String>();
        File[] archivos = new File(ruta).listFiles();

        for (File f : archivos) {
            if (f.isFile()) {
                listaArchivos.add(f.getName());
            }
        }

        return listaArchivos;
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
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Noticia> parseXML(String ruta, String archivo) {
        File archivoEntrada = new File(ruta + archivo);
        ArrayList<Noticia> listaNoticias = new ArrayList();

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

                    listaNoticias.add(new Noticia(titulo, texto));
                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaNoticias;
    }
}

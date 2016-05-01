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

    public String leerArchivo(String archivo) {
        String contenido = "";
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(archivo), "UTF-8"));
            String linea;
            StringBuilder sb = new StringBuilder();
            while ((linea = br.readLine()) != null) {
                sb.append(linea);
                sb.append("\n");
            }

            contenido = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return contenido;
    }

    public void escribirArchivos(String archivo, String texto) {
        BufferedWriter out = null;

        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(archivo), "UTF-8"));

            out.write(texto);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void SGML2XML(String ruta, String archivo) {
        String archivoSalida = ruta + (archivo.substring(0, archivo.lastIndexOf('.'))) + ".xml";

        String cadena = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "\n<SGML>\n" + this.leerArchivo(ruta + archivo)
                + "\n</SGML>";

        this.escribirArchivos(archivoSalida, cadena);
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
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaNoticias;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
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

    String ruta = "/home/germaaan/GIW/documentos/";
    String nombreArchivo = "prueba";
    File entrada = new File(ruta + nombreArchivo + ".sgml");
    File salida = new File(ruta + nombreArchivo + ".xml");

    public void SGML2XML() {
        try (FileWriter writer = new FileWriter(salida, false)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.write(System.lineSeparator());
            writer.write("<SGML>");
            writer.write(System.lineSeparator());
            writer.write(new Scanner(entrada, "UTF-8").useDelimiter("\\A").next());
            writer.write(System.lineSeparator());
            writer.write("</SGML>");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Noticia> parseXML() {
        ArrayList<Noticia> listaNoticias = new ArrayList();

        try {
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(salida);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.File;
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
        try {
            FileWriter writer = new FileWriter(salida, true);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.write(System.lineSeparator());
            writer.write("<SGML>");
            writer.write(System.lineSeparator());
            writer.write(new Scanner(entrada, "UTF-8").useDelimiter("\\A").next());
            writer.write(System.lineSeparator());
            writer.write("</SGML>");
            writer.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void parseXML() {
        try {
            File inputFile = new File(ruta + nombreArchivo + ".xml");

            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());
            //NodeList nList = doc.getElementsByTagName("student");
            System.out.println("----------------------------");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

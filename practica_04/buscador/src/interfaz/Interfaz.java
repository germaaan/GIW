package interfaz;

import buscador.Buscador;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static org.apache.lucene.index.DirectoryReader.indexExists;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Germán Martínez Maldonado
 */
public class Interfaz extends javax.swing.JFrame {

    private String indice;
    private Buscador buscador = new Buscador();
    HashMap<String, String> resultados;

    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campoBusqueda = new javax.swing.JTextField();
        botonBuscar = new javax.swing.JButton();
        campoMensajeResultado = new javax.swing.JTextField();
        panelResultados = new javax.swing.JScrollPane();
        listaResultados = new javax.swing.JList<>();
        panelNoticias = new javax.swing.JScrollPane();
        areaNoticias = new javax.swing.JTextArea();
        barraMenu = new javax.swing.JMenuBar();
        menuAcciones = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        opcionSalir = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        opcionAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        campoBusqueda.setEditable(false);
        campoBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBuscar(evt);
            }
        });

        botonBuscar.setText("Buscar");
        botonBuscar.setEnabled(false);
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBuscar(evt);
            }
        });

        campoMensajeResultado.setEditable(false);
        campoMensajeResultado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoMensajeResultado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        campoMensajeResultado.setEnabled(false);
        campoMensajeResultado.setMinimumSize(new java.awt.Dimension(2, 19));
        campoMensajeResultado.setPreferredSize(new java.awt.Dimension(365, 23));

        listaResultados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                accionSeleccionarNoticia(evt);
            }
        });
        panelResultados.setViewportView(listaResultados);

        areaNoticias.setEditable(false);
        areaNoticias.setColumns(20);
        areaNoticias.setRows(5);
        panelNoticias.setViewportView(areaNoticias);

        menuAcciones.setText("Acciones");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem2.setText("Cargar índice");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionCargarIndice(evt);
            }
        });
        menuAcciones.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Guardar noticia");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionGuardarNoticia(evt);
            }
        });
        menuAcciones.add(jMenuItem1);

        opcionSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        opcionSalir.setText("Salir");
        opcionSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionSalir(evt);
            }
        });
        menuAcciones.add(opcionSalir);

        barraMenu.add(menuAcciones);

        menuAyuda.setText("Ayuda");

        opcionAcercaDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        opcionAcercaDe.setText("Acerca de");
        opcionAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionAcercaDe(evt);
            }
        });
        menuAyuda.add(opcionAcercaDe);

        barraMenu.add(menuAyuda);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelNoticias))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(campoBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(campoMensajeResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscar)
                    .addComponent(campoMensajeResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                    .addComponent(panelNoticias))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accionCargarIndice(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionCargarIndice
        try {
            String ruta;
            boolean existeIndice;

            JTextField campoRuta = new JTextField();

            Object[] contenido = {"Introduzca la ruta del índice: ", campoRuta};
            Object acciones[] = {"Aceptar", "Volver"};

            int accion = JOptionPane.showOptionDialog(null, contenido, "Cargar índice",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, acciones,
                    contenido);

            if (accion == JOptionPane.YES_OPTION) {
                ruta = campoRuta.getText();
                existeIndice = indexExists(FSDirectory.open(new File(ruta)));

                if (existeIndice) {
                    this.indice = ruta;
                    this.campoBusqueda.setEditable(true);
                    this.botonBuscar.setEnabled(true);
                    JOptionPane.showMessageDialog(this, "Índice cargado: " + this.indice,
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Índice cargado: " + this.indice);
                } else {
                    JOptionPane.showMessageDialog(this, "No existe ningún indice en :" + ruta,
                            "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("No existe ningún indice en : " + ruta);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_accionCargarIndice

    private void accionGuardarNoticia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionGuardarNoticia
        // TODO add your handling code here:
    }//GEN-LAST:event_accionGuardarNoticia

    private void accionSalir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionSalir
        // TODO add your handling code here:
    }//GEN-LAST:event_accionSalir

    private void accionAcercaDe(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionAcercaDe
        // TODO add your handling code here:
    }//GEN-LAST:event_accionAcercaDe

    private void accionBuscar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBuscar
        String busqueda = this.campoBusqueda.getText();
        DefaultListModel lista = new DefaultListModel();
        String mensajeResultado = "";

        if (!busqueda.equals("")) {
            System.out.println("Realizando búsqueda.... ");
            resultados = new HashMap<>(this.buscador.buscar(this.indice, busqueda));

            Iterator<Entry<String, String>> iterador = resultados.entrySet().iterator();
            while (iterador.hasNext()) {
                Map.Entry par = (Map.Entry) iterador.next();

                lista.addElement(par.getKey());
            }

            this.listaResultados.setModel(lista);
            mensajeResultado = resultados.size() + " resultados para la búsqueda.";

            this.campoMensajeResultado.setText(mensajeResultado);
        } else {
            mensajeResultado = "No ha introducido ningún texto en el campo de búsqueda.";

            JOptionPane.showMessageDialog(this, mensajeResultado, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        System.out.println("RESULTADO: " + mensajeResultado);
    }//GEN-LAST:event_accionBuscar

    private void accionSeleccionarNoticia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accionSeleccionarNoticia
        if (evt.getClickCount() == 2) {

            String seleccionado = this.listaResultados.getSelectedValue();

            System.out.println(seleccionado);

            String texto = (String) this.resultados.get(seleccionado);
            System.out.println(this.resultados.containsKey(seleccionado));
            System.out.println(texto);
        }
    }//GEN-LAST:event_accionSeleccionarNoticia

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaNoticias;
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JTextField campoBusqueda;
    private javax.swing.JTextField campoMensajeResultado;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JList<String> listaResultados;
    private javax.swing.JMenu menuAcciones;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenuItem opcionAcercaDe;
    private javax.swing.JMenuItem opcionSalir;
    private javax.swing.JScrollPane panelNoticias;
    private javax.swing.JScrollPane panelResultados;
    // End of variables declaration//GEN-END:variables
}

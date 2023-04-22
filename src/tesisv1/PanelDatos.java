/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author miguel
 */
public class PanelDatos extends JPanel {
    int distInterna = 18;
    int tamxPanel = 402;
    PanelReproduccion panelReproduccion;
    PanelEstadistica panelEstadistica;
    EscenarioVirtual escenarioVirtual;
    ManejadorEventos manejadorEvento;
    ControladorHilos controladorHilos;
    Pantalla pantalla;
    JLabel lblFNombre, lblNombreArchivo;
    FrameExportarDatos frameExportarDatos;
    
    JButton btGrabar, btCargar, btExportar;
    
    public PanelDatos(int xSize, int ySize, EscenarioVirtual escenarioIn, ControladorHilos controladorIn, Pantalla pantallaIn){
        
        this.controladorHilos = controladorIn;
        this.escenarioVirtual = escenarioIn;
        this.pantalla = pantallaIn;
        this.setLayout(null);
        this.setBounds(xSize - tamxPanel - 10, 532, tamxPanel, 317);
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Manejo de Archivos");
        this.setBorder(bordejpanel); 
       
        this.btGrabar = new JButton("Iniciar Grabación");   
        this.btGrabar.setBounds(10, 25,120,40);     
         
         
        this.btCargar = new JButton("Cargar Archivo");   
        this.btCargar.setBounds(tamxPanel/2 - 60, 25,120,40);     
        
         
        this.btExportar = new JButton("Exportar Datos");   
        this.btExportar.setBounds(tamxPanel-120-10, 25,120,40);   
        this.btExportar.setEnabled(false);
       
         
        this.lblFNombre = new JLabel("Archivo Cargado: ");
        this.lblFNombre.setFont(new Font("Serif", Font.BOLD, 15));
         
        this.lblFNombre.setBounds(12, 74, 145,20);
        this.add(this.lblFNombre);
                 
         
        this.lblNombreArchivo = new JLabel("Ninguno");
        this.lblNombreArchivo.setFont(new Font("Serif", Font.ITALIC, 15));
        this.lblNombreArchivo.setForeground(Color.red);
        this.lblNombreArchivo.setToolTipText("Nombre de arhivo cargado");
        this.lblNombreArchivo.setBounds(150, 74,150,20);
        this.add(this.lblNombreArchivo);
         
        this.panelReproduccion = new PanelReproduccion(xSize,ySize, this.escenarioVirtual, this.controladorHilos, this.pantalla,
                                    this.btGrabar, this.btCargar);
         
        this.panelEstadistica = new PanelEstadistica(xSize,ySize, this.controladorHilos);
         
        this.manejadorEvento = new ManejadorEventos(this.escenarioVirtual, this.controladorHilos, this.pantalla, 
                                                    this.panelReproduccion, this.panelEstadistica);
         
        this.btGrabar.addActionListener(manejadorEvento);
        this.add(this.btGrabar);
         
        this.btCargar.addActionListener(manejadorEvento);
        this.add(this.btCargar);
         
        this.btExportar.addActionListener(manejadorEvento);
        this.add(this.btExportar);
         
         
        this.add(this.panelReproduccion);
        this.add(this.panelEstadistica);
        
        this.setVisible(true);
    }
   
   private class ManejadorEventos implements ItemListener,ActionListener{
        EscenarioVirtual escenarioVirtual;
        //HiloGrabacion hiloGrabacion;
        ControladorHilos controladorHilos;
        Pantalla pantallaManejador;
        PanelReproduccion panelReproduccion;
        PanelEstadistica panelEstadistica;
        
        private ManejadorEventos(EscenarioVirtual escenarioIn, ControladorHilos controladorIn, Pantalla pantallaIn, 
                                PanelReproduccion panelReproduccionIn, PanelEstadistica panelEstadisticaIn) {
            this.escenarioVirtual = escenarioIn;
            this.controladorHilos = controladorIn;
            this.pantallaManejador = pantallaIn;
            this.panelReproduccion = panelReproduccionIn;
            this.panelEstadistica = panelEstadisticaIn;
         
        }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getTopLevelAncestor().requestFocus();
        try{
            System.gc();
            if(e.getSource() == btGrabar){      
                switch (btGrabar.getText()) {
                  
                    case "Iniciar Grabación":    
                       
                        IniciarGrabacion();
                        btGrabar.setText("Parar Grabación");
                        break;
                    case "Parar Grabación":
                        btGrabar.setText("Iniciar Grabación");
                        PararGrabacion();                       
                        break;
                }
            }    
            else if(e.getSource() == btCargar){      
                switch (btCargar.getText()) {
                    case "Cargar Archivo":
                     
                        CargarArchivo();
                        
                        break;
                    case "Quitar Archivo":
                        QuitarArchivo();                 
                        break;
                }
            } else if(e.getSource() == btExportar){
                ExportarArchivoExcel();
            }   
             
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

        private void IniciarGrabacion() {
          this.controladorHilos.hiloGrabacion = true;
          this.controladorHilos.ResetearVelocidad();
          
           btCargar.setEnabled(false); 
           this.panelReproduccion.btReproducir.setEnabled(false);
           this.panelEstadistica.btXvsTiempo.setEnabled(false);
           this.panelEstadistica.btYvsTiempo.setEnabled(false);
           this.panelEstadistica.btZvsTiempo.setEnabled(false);
           this.panelEstadistica.btRvsTiempo.setEnabled(false);
           this.panelReproduccion.btDobleVelocidad.setEnabled(false);
           this.panelReproduccion.btMitadVelocidad.setEnabled(false);
           this.panelReproduccion.lblVelocidad.setText("Velocidad x" + this.controladorHilos.velocidadReproduccion);
  
          HiloGrabacion hiloGrabacion = new HiloGrabacion(this.escenarioVirtual, this.controladorHilos, this.pantallaManejador);  
          hiloGrabacion.start();
        }

        private void PararGrabacion() {
         this.controladorHilos.hiloGrabacion = false;    
            btCargar.setEnabled(true);
        }

        private void CargarArchivo() {
            this.controladorHilos.ResetearVelocidad();
            //inicio hilo
            HiloLectura hiloLectura = new HiloLectura(this.escenarioVirtual, this.controladorHilos, 
                                        this.pantallaManejador, btGrabar, btCargar, this.panelReproduccion.btReproducir,
                                        this.panelReproduccion.btDobleVelocidad, this.panelReproduccion.btMitadVelocidad, 
                                        this.panelEstadistica.btXvsTiempo,this.panelEstadistica.btYvsTiempo,this.panelEstadistica.btZvsTiempo,
                                        this.panelEstadistica.btRvsTiempo,btExportar,this.panelReproduccion.btParar,lblNombreArchivo, this.panelReproduccion.lblTiempo);  
            hiloLectura.start();
            if(this.controladorHilos.estadoArchivoCargado){
                            btCargar.setText("Quitar Archivo");
                            this.panelReproduccion.btReproducir.setEnabled(true);
                            this.panelEstadistica.btXvsTiempo.setEnabled(true);
                            this.panelEstadistica.btYvsTiempo.setEnabled(true);
                            this.panelEstadistica.btZvsTiempo.setEnabled(true);
                            this.panelEstadistica.btRvsTiempo.setEnabled(true);
                            this.panelReproduccion.btDobleVelocidad.setEnabled(false);
                            this.panelReproduccion.btMitadVelocidad.setEnabled(false);
            }    
        }

        private void QuitarArchivo() {
            lblNombreArchivo.setText("Ninguno");
            lblNombreArchivo.setForeground(Color.red);
            this.panelReproduccion.ResetearVelocidad();
            this.panelReproduccion.lblTiempo.setText("");
            this.controladorHilos.estadoArchivoCargado = false; 
            btCargar.setText("Cargar Archivo");
            this.panelReproduccion.btDobleVelocidad.setEnabled(false);
            this.panelReproduccion.btMitadVelocidad.setEnabled(false);
            this.panelReproduccion.btReproducir.setEnabled(false);
            this.panelEstadistica.btXvsTiempo.setEnabled(false);
            this.panelEstadistica.btYvsTiempo.setEnabled(false);
            this.panelEstadistica.btZvsTiempo.setEnabled(false);
            this.panelEstadistica.btRvsTiempo.setEnabled(false);
            btExportar.setEnabled(false);
           
            btGrabar.setEnabled(true);
        }

        private void ExportarArchivoExcel() {
          if(!this.controladorHilos.frameExportarActivado){
              btCargar.setEnabled(false);
                  this.controladorHilos.frameExportarActivado = true;
                  frameExportarDatos = new FrameExportarDatos(this.controladorHilos,btCargar);
                                 
          }    
        }  

  }
}

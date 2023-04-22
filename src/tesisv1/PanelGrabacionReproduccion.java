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
public class PanelGrabacionReproduccion extends JPanel implements ItemListener{
    EscenarioVirtual escenarioVirtual;
    ManejadorEventos manejadorEvento;
    ControladorHilos controladorHilos;
    JButton btGrabacion, btCargado, btReproducir, btDobleVelocidad, btMitadVelocidad;
    Pantalla pantalla;
     JLabel lbltiempo, lblVelocidad, lblNombreArchivo;
    
    int tamxPanel = 200;
    
    public PanelGrabacionReproduccion(int xSize, int ySize, EscenarioVirtual escenarioIn, ControladorHilos controladorIn, Pantalla pantallaIn){
        
        this.controladorHilos = controladorIn;
         this.escenarioVirtual = escenarioIn;
         this.pantalla = pantallaIn;
        this.manejadorEvento = new ManejadorEventos(this.escenarioVirtual, this.controladorHilos, this.pantalla);
    
       
        int distFila = 60;
        int distBtt = 50;
        
        this.setLayout(null);
        this.setBounds(xSize -200 - tamxPanel -12, 600 -2, tamxPanel, 230);
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Guardado-Reproduccion");
        this.setBorder(bordejpanel); 
        
        this.btGrabacion = new JButton("GRABAR");   
         this.btGrabacion.setBounds(tamxPanel/2 -50, 20,100,50);     
         this.btGrabacion.addActionListener(manejadorEvento);
         this.add(this.btGrabacion);
         
         
         this.btCargado= new JButton("Cargar Archivo");   
         this.btCargado.setBounds(22, 90,150,30);     
         this.btCargado.addActionListener(manejadorEvento);
         this.add(this.btCargado);
         
         this.btReproducir= new JButton("Reproducir");   
         this.btReproducir.setBounds(tamxPanel/2 -50, 135,100,30);     
         this.btReproducir.addActionListener(manejadorEvento);
         this.btReproducir.setEnabled(false);
         this.add(this.btReproducir);
         
         this.btDobleVelocidad= new JButton("Velocidadx2");   
         this.btDobleVelocidad.setBounds(0, 170,100,40);     
         this.btDobleVelocidad.addActionListener(manejadorEvento);
         this.btDobleVelocidad.setEnabled(false);
         this.add(this.btDobleVelocidad);
         
          this.btMitadVelocidad= new JButton("Velocidad/2");   
         this.btMitadVelocidad.setBounds(tamxPanel/2-1, 170,100,40);     
         this.btMitadVelocidad.addActionListener(manejadorEvento);
         this.btMitadVelocidad.setEnabled(false);
         this.add(this.btMitadVelocidad);

         
         this.lbltiempo = new JLabel("Tiempo..");
         this.lbltiempo.setFont(new Font("Serif", Font.ITALIC, 15));
         this.lbltiempo.setToolTipText("tiempo");
         this.lbltiempo.setBounds(10,65, tamxPanel,30);
         this.add(this.lbltiempo);
         
         this.lblVelocidad = new JLabel("Velocidad x" + this.controladorHilos.velocidadReproduccion);
         this.lblVelocidad.setFont(new Font("Serif", Font.ITALIC, 15));
         this.lblVelocidad.setToolTipText("Velocidad Reproduccion");
         this.lblVelocidad.setBounds(tamxPanel/2-45,205, tamxPanel,20);
         this.add(this.lblVelocidad);
         
         this.lblNombreArchivo = new JLabel("");
         this.lblNombreArchivo.setFont(new Font("Serif", Font.ITALIC, 15));
         this.lblNombreArchivo.setToolTipText("Nombre de arhivo cargado");
         this.lblNombreArchivo.setBounds(42, 115,150,20);
         this.add(this.lblNombreArchivo);
         
         this.setVisible(true);    
    }
 
    @Override
    public void itemStateChanged(ItemEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    


private class ManejadorEventos implements ItemListener,ActionListener{
        EscenarioVirtual escenarioVirtual;
        //HiloGrabacion hiloGrabacion;
        ControladorHilos controladorHilos;
        Pantalla pantallaManejador;
        
        private ManejadorEventos(EscenarioVirtual escenarioIn, ControladorHilos controladorIn, Pantalla pantallaIn) {
            this.escenarioVirtual = escenarioIn;
            this.controladorHilos = controladorIn;
            this.pantallaManejador = pantallaIn;
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
            if(e.getSource() == btGrabacion){      
                switch (btGrabacion.getText()) {
                    case "GRABAR":                      
                        IniciarGrabacion();
                        btGrabacion.setText("PARAR");
                        break;
                    case "PARAR":
                        btGrabacion.setText("GRABAR");
                        PararGrabacion();                       
                        break;
                }
            }    
            if(e.getSource() == btCargado){      
                switch (btCargado.getText()) {
                    case "Cargar Archivo":
                        CargarArchivo();
                        lblNombreArchivo.setText(this.controladorHilos.nombreArchivoCargado);
                        break;
                    case "Quitar Archivo":
                        QuitarArchivo();
                         lblNombreArchivo.setText("");                 
                        break;
                }
            }   
             if(e.getSource() == btReproducir) {      
                switch (btReproducir.getText()) {
                    case "Reproducir":
                        if(this.controladorHilos.pausado)
                        {
                            this.controladorHilos.pausado = false;
                            btReproducir.setText("Pausar");
                        }else{
                            ReproducirArchivo(); 
                        }   
                        break;
                    case "Pausar":
                        PausarReproduccion();
                        break;
                }
            }   
             if(e.getSource() == btDobleVelocidad) {     
                    DoblarVelocidad();
             }
             if(e.getSource() == btMitadVelocidad) {     
                    RalentizarVelocidad();
             }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

        private void IniciarGrabacion() {
          this.controladorHilos.hiloGrabacion = true;
          this.controladorHilos.ResetearVelocidad();
          
            btReproducir.setEnabled(false);
            btCargado.setEnabled(false);
            btDobleVelocidad.setEnabled(false);
            btMitadVelocidad.setEnabled(false);
            lblVelocidad.setText("Velocidad x" + this.controladorHilos.velocidadReproduccion);
  
          HiloGrabacion hiloGrabacion = new HiloGrabacion(this.escenarioVirtual, this.controladorHilos, this.pantallaManejador);  
          hiloGrabacion.start();
        }

        private void PararGrabacion() {
         this.controladorHilos.hiloGrabacion = false;    
         // btReproducir.setEnabled(true);
            btCargado.setEnabled(true);
        }

        private void CargarArchivo() {
            //inicio hilo
           // HiloLectura hiloLectura = new HiloLectura(this.escenarioVirtual, this.controladorHilos, 
            //                            this.pantallaManejador, btGrabacion, btCargado, btReproducir, btDobleVelocidad, btMitadVelocidad, 
           //                             lblNombreArchivo, lbltiempo);  
           // hiloLectura.start();
            if(this.controladorHilos.estadoArchivoCargado){
                            btCargado.setText("Quitar Archivo");
                            btReproducir.setEnabled(true);
                            btDobleVelocidad.setEnabled(false);
                            btMitadVelocidad.setEnabled(false);
            }    
        }

        private void QuitarArchivo() {
           this.controladorHilos.estadoArchivoCargado = false; 
           btCargado.setText("Cargar Archivo");
           btReproducir.setEnabled(false);
           btDobleVelocidad.setEnabled(false);
           btMitadVelocidad.setEnabled(false);
           btGrabacion.setEnabled(true);
        }

        private void ReproducirArchivo() {
             this.controladorHilos.estadoEnReproduccion= true;
             btReproducir.setText("Pausar");
             btGrabacion.setEnabled(false);
             btCargado.setEnabled(false);
        }

        private void PausarReproduccion() {
            this.controladorHilos.pausado = true;
            btReproducir.setText("Reproducir");
        }

        private void DoblarVelocidad() {
               this.controladorHilos.DoblarVelocidad();
               lblVelocidad.setText( "Velocidad x" + this.controladorHilos.velocidadReproduccion);
        }

        private void RalentizarVelocidad() {
            this.controladorHilos.RalentizarVelocidad();
             lblVelocidad.setText( "Velocidad x" + this.controladorHilos.velocidadReproduccion);
        }

  }
}

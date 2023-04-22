/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

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
public class PanelReproduccion extends JPanel{
    int distInterna = 18;
    int tamxPanel = 192;
    EscenarioVirtual escenarioVirtual;
    ManejadorEventos manejadorEvento;
    ControladorHilos controladorHilos;
    Pantalla pantalla;
    JButton btReproducir, btDobleVelocidad, btMitadVelocidad, btParar;
    JLabel lblTiempo, lblVelocidad;
     
    public PanelReproduccion(int xSize, int ySize, EscenarioVirtual escenarioIn, ControladorHilos controladorIn, Pantalla pantallaIn, 
                               JButton btGrabacion, JButton btCargado){
        
         this.controladorHilos = controladorIn;
         this.escenarioVirtual = escenarioIn;
         this.pantalla = pantallaIn;
        this.manejadorEvento = new ManejadorEventos(this.escenarioVirtual, this.controladorHilos, this.pantalla, btGrabacion, btCargado);
        
        this.setLayout(null);
        this.setBounds(8, 100, tamxPanel, 210);
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Reproducción Archivo");
        this.setBorder(bordejpanel); 
        
        
        this.btReproducir= new JButton("Reproducir");   
         this.btReproducir.setBounds(tamxPanel/2 -50, 20,100,50);     
         this.btReproducir.addActionListener(manejadorEvento);
         this.btReproducir.setEnabled(false);
         this.add(this.btReproducir);
         
         this.btDobleVelocidad= new JButton("Velocidadx2");   
         this.btDobleVelocidad.setBounds(0, 135,95,40);     
         this.btDobleVelocidad.addActionListener(manejadorEvento);
         this.btDobleVelocidad.setEnabled(false);
         this.add(this.btDobleVelocidad);
         
          this.btMitadVelocidad= new JButton("Velocidad/2");   
         this.btMitadVelocidad.setBounds(tamxPanel/2-1, 135,95,40);     
         this.btMitadVelocidad.addActionListener(manejadorEvento);
         this.btMitadVelocidad.setEnabled(false);
         this.add(this.btMitadVelocidad);

         this.btParar = new JButton("Parar");
         this.btParar.setBounds(tamxPanel/2-30,100,60,35);
         this.btParar.addActionListener(manejadorEvento);
         this.btParar.setEnabled(false);
         this.add(this.btParar);
         
         this.lblTiempo = new JLabel("");
         this.lblTiempo.setFont(new Font("Serif", Font.BOLD, 20));  
         this.lblTiempo.setToolTipText("tiempo");
         this.lblTiempo.setBounds(0,75, tamxPanel,20);
         this.lblTiempo.setHorizontalAlignment( JLabel.CENTER );
         this.add(this.lblTiempo);
         
         this.lblVelocidad = new JLabel("Velocidad x" + this.controladorHilos.velocidadReproduccion);
         this.lblVelocidad.setFont(new Font("Serif", Font.ITALIC, 15));
         this.lblVelocidad.setToolTipText("Velocidad Reproduccion");
         this.lblVelocidad.setBounds(tamxPanel/2-45,173, tamxPanel,20);
         this.add(this.lblVelocidad);
         
         this.setVisible(true);
    }
    
     public void ResetearVelocidad() {
            this.controladorHilos.ResetearVelocidad();
            lblVelocidad.setText( "Velocidad x" + this.controladorHilos.velocidadReproduccion);
        }
    
    private class ManejadorEventos implements ItemListener,ActionListener{
        EscenarioVirtual escenarioVirtual;
        //HiloGrabacion hiloGrabacion;
        ControladorHilos controladorHilos;
        Pantalla pantallaManejador;
        JButton btGrabacion, btCargado;
        
        private ManejadorEventos(EscenarioVirtual escenarioIn, ControladorHilos controladorIn, Pantalla pantallaIn, 
                                    JButton btGrabacionIn, JButton btCargadoIn) {
            this.escenarioVirtual = escenarioIn;
            this.controladorHilos = controladorIn;
            this.pantallaManejador = pantallaIn;
            this.btCargado = btCargadoIn;
            this.btGrabacion = btGrabacionIn;
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
             if(e.getSource() == btParar) {
                 PararReproduccion();
             }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }        

        private void ReproducirArchivo() {
             this.controladorHilos.estadoEnReproduccion= true;
             btParar.setEnabled(true);
             btReproducir.setText("Pausar");
             this.btGrabacion.setEnabled(false);
             this.btCargado.setEnabled(false);
        }

        private void PausarReproduccion() {
            this.controladorHilos.pausado = true;
            btReproducir.setText("Reproducir");
        }
        private void PararReproduccion(){
           this.controladorHilos.pararActivado = true;
           this.controladorHilos.pausado = false;
           btParar.setEnabled(false);
        }

        private void DoblarVelocidad() {
            if(this.controladorHilos.velocidadReproduccion == 8){
                btDobleVelocidad.setEnabled(false);
            }
           
               this.controladorHilos.DoblarVelocidad();
               lblVelocidad.setText( "Velocidad x" + this.controladorHilos.velocidadReproduccion);    
        }

        private void RalentizarVelocidad() {
            if(this.controladorHilos.velocidadReproduccion == 16){
               btDobleVelocidad.setEnabled(true);
            }
            this.controladorHilos.RalentizarVelocidad();
             lblVelocidad.setText( "Velocidad x" + this.controladorHilos.velocidadReproduccion);
        }
        
       

  }
}
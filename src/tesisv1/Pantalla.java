/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.Enumeration;

/**
 *
 * @author miguel
 */
public class Pantalla extends JFrame implements KeyListener{
    EscenarioVirtual escenario;
    ControladorImagen controladorImagen;
    ControladorHilos controladorHilos;
    ControladorRotacionZoom controladorRotacionZoom;
    ListenerRotacionZoom listenerRotacionZoom;
    PanelVisual panelVisual;   
    PanelEscenario panelEscenario;
    PanelControlTeclado panelControlTeclado;
    PanelMuestraMedidas panelMuestraMedidas;
    //PanelGrabacionReproduccion panelGrabacionReproduccion;
    PanelDatos panelDatos;
    
    int alto;
    int ancho;
   
    int xSize, ySize;
    
    public Pantalla(EscenarioVirtual escenarioIn, ControladorHilos controladorIn)
    {              
        this.controladorRotacionZoom = new ControladorRotacionZoom();
        this.listenerRotacionZoom = new ListenerRotacionZoom(this.controladorRotacionZoom);
        this.setFocusable(true);
        
        this.requestFocus();
     
        this.addKeyListener(this.listenerRotacionZoom);
        
        this.xSize = 1450;
        this.ySize = 1000;
        this.escenario = escenarioIn;
        this.controladorHilos = controladorIn;
        
        
        this.controladorImagen = new ControladorImagen();
         
         
        this.setLayout(null);
        this.setTitle("Estudio del Desarrollo de la Técnica Básica Pianística");
        this.panelVisual = new PanelVisual(xSize,ySize, this.controladorImagen);
        this.panelEscenario = new PanelEscenario(xSize - 420,ySize, this.escenario,this.controladorImagen, this.controladorRotacionZoom);
        this.panelControlTeclado = new PanelControlTeclado(xSize,ySize, this.escenario);
        this.panelMuestraMedidas = new PanelMuestraMedidas(xSize,ySize, this.escenario);
        //this.panelGrabacionReproduccion = new PanelGrabacionReproduccion(xSize,ySize, this.escenario, this.controladorHilos, this);
        this.panelDatos = new PanelDatos(xSize,ySize, this.escenario, this.controladorHilos, this);
        
        
        this.add(this.panelVisual);
        this.setLocation(90,0);
        
        this.add(this.panelEscenario);
        this.setLocation(0,0);
        
        this.add(this.panelControlTeclado);
        this.setLocation(0,0);
        
        this.add(this.panelMuestraMedidas);
        this.setLocation(0,0);
        
        //this.add(this.panelGrabacionReproduccion);
       // this.setLocation(0,0);
        this.add(this.panelDatos);
        this.setLocation(0,0);
      
        this.setSize(xSize, ySize);
       this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener( new WindowAdapter() { 
                @Override
                public void windowClosing( WindowEvent evt ) {
                      ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/pregunta.png"));
                    Object [] opciones ={"Aceptar","Cancelar"};
                        int eleccion = JOptionPane.showOptionDialog(rootPane,
                                "Está seguro que quiere salir de la aplicación",
                                "Mensaje de Confirmación",
                        JOptionPane.YES_NO_OPTION,
                       JOptionPane.QUESTION_MESSAGE,Img,
                       opciones,"Aceptar");
                       
                        if (eleccion == JOptionPane.YES_OPTION)
                        {                         
                           System.exit(0);
                         
                        }else{
                            
                        }
                    } 
            } ); 

	this.setVisible(true);
         
    }

    @Override
    public void keyTyped(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
@Override
    public void keyPressed(KeyEvent e) {
     
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                
                rotarIzquierda();
		break;
	    case KeyEvent.VK_RIGHT:
                 
                rotarDerecha();
		break;
            case KeyEvent.VK_DOWN:
             
		rotarAbajo();
                break;
	    case KeyEvent.VK_UP:
		rotarArriba();
                break;
            case KeyEvent.VK_W:
                 if(this.controladorRotacionZoom.zoom < 13){
                       acercar();
                  }        
                break;
            case KeyEvent.VK_S:
                  if(this.controladorRotacionZoom.zoom > -13){
                       alejar();
                  }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    private void rotarIzquierda() {
       System.out.println("gira Izq");
       this.controladorRotacionZoom.delta = this.controladorRotacionZoom.delta - 0.03;
    }
    
    private void rotarDerecha(){
        this.controladorRotacionZoom.delta = this.controladorRotacionZoom.delta + 0.03;
    }

    private void rotarAbajo() {
        this.controladorRotacionZoom.dangulo = this.controladorRotacionZoom.dangulo- 0.03;
    }

    private void rotarArriba() {
       this.controladorRotacionZoom.dangulo = this.controladorRotacionZoom.dangulo + 0.03;
    }

    private void acercar() {
        this.controladorRotacionZoom.zoom = this.controladorRotacionZoom.zoom + 0.3;
    }

    private void alejar() {
        this.controladorRotacionZoom.zoom = this.controladorRotacionZoom.zoom - 0.3;
    }
}

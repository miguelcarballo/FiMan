/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author miguel
 */
public class ListenerRotacionZoom implements KeyListener{
    ControladorRotacionZoom controlador;
    
    public ListenerRotacionZoom(ControladorRotacionZoom controladorIn){
        this.controlador = controladorIn;
    }
    
@Override
    public void keyTyped(KeyEvent e) {
        
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
             
                 if(this.controlador.zoom > -13){
                       acercar();
                  }
                
                break;
            case KeyEvent.VK_S:
                  if(this.controlador.zoom < 13){
                  
                       alejar();
                  }
                break;
            case KeyEvent.VK_R:
                resetearPosicionCamara();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    private void rotarIzquierda() {

       this.controlador.delta = this.controlador.delta - 0.03;
    }
    
    private void rotarDerecha(){
        this.controlador.delta = this.controlador.delta + 0.03;
    }

    private void rotarAbajo() {
        this.controlador.dangulo = this.controlador.dangulo+ 0.03;
    }

    private void rotarArriba() {
       this.controlador.dangulo = this.controlador.dangulo - 0.03;
    }

    private void acercar() {
        this.controlador.zoom = this.controlador.zoom - 0.3;
    }

    private void alejar() {
        this.controlador.zoom = this.controlador.zoom + 0.3;
    }
    
    private void resetearPosicionCamara(){
        this.controlador.zoom = 0;
        this.controlador.delta = 0;
        this.controlador.dangulo = 0;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;
/**
 *
 * @author miguel
 */
public class HiloDibujo extends Thread{
    EscenarioVirtual escenario;
    Pantalla pantalla;
    ControladorHilos controladorHilos;
    
    public HiloDibujo(EscenarioVirtual escenarioIn, ControladorHilos controladorIn){
        this.escenario = escenarioIn;
        this.controladorHilos = controladorIn;
        this.pantalla = new Pantalla(escenarioIn, this.controladorHilos);
        
    }
    
    @Override
    public void run()
    {
       try {
           
                
            while(true){
                //System.gc();
                //this.pantalla.panelEscenario.borrar();
                //this.pantalla.panelEscenario.dibujar();
                this.pantalla.panelEscenario.repaint();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ex) {  
                    System.out.println("Error HiloDibujo sleep " + ex);
                }
          }
       } catch (Exception e){
           System.out.println("Error HiloDibujo " + e);
//           this.start();
       }
    
    }
    
    
}


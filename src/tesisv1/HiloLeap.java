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
 
public class HiloLeap extends Thread{
  
    Leap leap;
    EscenarioVirtual escenario;
    ControladorHilos controladorHilos;
        
    public HiloLeap(EscenarioVirtual escenarioIn, ControladorHilos controladorIn) {
        
         this.escenario = escenarioIn;
         this.controladorHilos = controladorIn;
         this.leap = new Leap(this.escenario, this.controladorHilos);
         //this.leap = new Leap(this);   
    }
    
    @Override
    public void run(){
       escucha();
    }
    
    public void escucha(){
        this.leap.correr();
       System.out.println("finCorrer");
    }
    

}

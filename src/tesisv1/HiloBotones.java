/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;
import javax.swing.JLabel;
/**
 *
 * @author miguel
 */
   
  public class HiloBotones extends Thread{
         double cantMov;
         int direccion;
         ControlBotonesPCTeclado controlBotones;
         EscenarioVirtual escenario;
         JLabel lblPos;
    
        public HiloBotones(ControlBotonesPCTeclado controlBotonesIn, EscenarioVirtual inEscenario, double cantidadIn, int direccionIn, JLabel lblPosIn){
            this.controlBotones = controlBotonesIn;
            this.escenario = inEscenario;
            this.cantMov = cantidadIn;
            this.direccion = direccionIn;
            this.lblPos = lblPosIn;
        }

        @Override
        public void run()
        {
            //while(true){
                 //System.out.println("inicio run "+ this.controlBotones.presionado);
               while(this.controlBotones.presionado){   
                try {
                    if(this.escenario.Piano != null){
                   // System.out.println("presionado while " + this.controlBotones.presionado);
                    this.escenario.Piano.moverTeclado(this.cantMov , this.direccion);
                    this.lblPos.setText(getStringPosTeclado());
                    }
                    Thread.sleep(80);
                } catch (Error e){
                } catch (InterruptedException ex) {
                    System.out.println("Error HiloBotones");
                    System.out.println(ex);
                }
            
            }
           
        }
        
         private String getStringPosTeclado() {
         String textPos;
         if(escenario.Piano == null){
             textPos = "";
         }
         else{
             double[] posDouble  = new double[3];
             posDouble = escenario.Piano.PosicionInicial.clone(); 
             int[] posInt = new int[3];
             posInt[0] = (int)posDouble[0];
             posInt[1] = (int)posDouble[1];
             posInt[2] = (int)posDouble[2];
             textPos = "(" + posInt[0]+ ","+posInt[1]+ ","+posInt[2]+ ")";
         }
         
       return textPos;
    }
    }

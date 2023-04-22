/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.util.Enumeration;

/**
 *
 * @author miguel
 */
public class HiloSonido extends Thread{
    EscenarioVirtual escenario;
    EstadoListener MusicPanel;
    ControladorHilos controladorHilos;

    
    public HiloSonido(EscenarioVirtual escenarioIn, ControladorHilos controladorIn){
        this.escenario = escenarioIn;
        this.MusicPanel = new MusicPanel();
        this.controladorHilos = controladorIn;
        this.escenario.Piano.addEstadoListener(MusicPanel);
    
    }
     @Override
    public void run(){
        while(true){
          verifPianoTocado();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void verifPianoTocado() {
        if(this.escenario.Piano != null){
            if(this.escenario.Piano.activado == false)
            {
                this.escenario.Piano.addEstadoListener(MusicPanel);
            }
            Piano piano = this.escenario.Piano;
        
            for (int i = 0; i<piano.numTeclas; i = i+1){
              verifTeclaTocada(piano.Teclado[i]);
            }
        }
    }

    private void verifTeclaTocada(Tecla tecla) {
       if (algoToca(tecla)){
           tecla.esPresionada();
       }else
       {
         tecla.esSoltada();
       }
    }

    private boolean algoToca(Tecla tecla) {
       boolean verif = false;
       if(this.escenario.Manos!= null){
        if(!this.escenario.Manos.isEmpty())
        {
          Enumeration<String> llaves = this.escenario.Manos.keys();       
          while (llaves.hasMoreElements()) {               
              Mano mano = this.escenario.Manos.get(llaves.nextElement());                  
              if (manoToca(mano,tecla)){
                  verif = true;
                  break;
              }
          }
        }
       }
       return verif;
    }

    private boolean manoToca(Mano manoIn, Tecla tecla) {
        boolean verif = false;
        if(!(manoIn == null)){
        if(!manoIn.Dedos.isEmpty())
        {
          Enumeration<String> llavesDedos = manoIn.Dedos.keys();
        
          while (llavesDedos.hasMoreElements()) {       
              Dedo dedoIn = manoIn.Dedos.get(llavesDedos.nextElement());
              double[] posPunta = dedoIn.Punta;    
              
              //if(tecla.estaEnTecla(posPunta)) {
                     //tecla.velocidadActual = dedoIn.Velocidad[1];
                 //    verif = true;
              //if(tecla.estaEnTecla(posPunta) && verifVelocidadDedo(dedoIn.Velocidad)){
                  
                 if(tecla._estado == Estado.SUELTO){
                      if(tecla.estaEnTecla(posPunta) && verifVelocidadDedo(dedoIn.Velocidad)){
                         verif = true;
                         //-----print
                         if(AuxPrint.datoTeclaTocada){
                             if(tecla._estado == Estado.SUELTO){
                              System.out.println("*****Nota = " + 
                              tecla.nota.nombre + " Dedo " +dedoIn.Tipo + 
                               " vel. = " + dedoIn.Velocidad[0] +" "+ dedoIn.Velocidad[1] + " "+dedoIn.Velocidad[2]);}
                          }
                         //-------
                          break;
                      }
                      else{
                          verif = false;
                      }
                  }
                  else{
                      if(tecla.estaEnTecla(posPunta)) {
                     //tecla.velocidadActual = dedoIn.Velocidad[1];
                      verif = true;
                      break;
                  }         
                 
              }
          }
        }  
        }
        return verif;
    }

    private boolean verifVelocidadDedo(double[] velocidadIn) {
        boolean verif;
        //OJO esto es para activar sensibilidad a velocidad
        verif = velocidadIn[1] <= 0 && velocidadIn[1] <= -60;
       return verif;
       
    }
}

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

import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import java.util.Enumeration;

class SampleListener extends Listener {
    
    EscenarioVirtual escenario;
    ControladorHilos controladorHilos;
   
    public SampleListener(ControladorHilos controladorHilosIn){
        this.controladorHilos = controladorHilosIn;
    }
            
    public void onInit(Controller controller) {
        System.out.println("Inicializado");
    }

    public void onConnect(Controller controller) {
        System.out.println("Conectado");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onDisconnect(Controller controller) {
        System.out.println("Desconectado");
    }

    public void onExit(Controller controller) {
        System.out.println("Salida exitosa");
    }
    

    @Override
    public void onFrame(Controller controller) {
        // Captura del frame + datos basicos
        Frame frame = controller.frame();
     
        //Captura de manos 
        if (AuxPrint.frames){
        System.out.println("N Frames = " + frame.currentFramesPerSecond());}
       if(this.controladorHilos.hiloLeap){
        cambiarEscenario(frame.hands());
       }
       
        for(Hand hand : frame.hands()) {
            String handType = hand.isLeft() ? "Left hand" : "Right hand";
            
            // Captura de brazo (arm bone)
            Arm arm = hand.arm();
            if (AuxPrint.leapArm){
                System.out.println("  Arm direction (brazo): " + arm.direction()
                             + ", wrist position (muneca): " + arm.wristPosition()
                             + ", elbow position (codo): " + arm.elbowPosition());}
               
          
        }
      //prueba de captura de movimientos (gestures)... 
       if(AuxPrint.divFrames)
           System.out.println("-------------------------------------------------------------------------");
    }
    
    
    public void cambiarEscenario(HandList listaManos) {  
        //verifica lista de manos DEL Escenario, elimina los q ya no existen en listaManos
        verificarManosEliminadas(listaManos);
        //verifica si existe mano en Escenario, si existe.... modifica, sino, agrega
        for ( int i = 0; i < listaManos.count(); i++){  
            Hand auxHand = listaManos.get(i);
            
            this.escenario.addModifMano(auxHand);   
        }
        
   //     this.escenario.mostrarManos();
    }
    
    public void verificarManosEliminadas(HandList listaManos) {
        if(!escenario.Manos.isEmpty())
        {
        Enumeration<String> llaves = escenario.Manos.keys();
        
          while (llaves.hasMoreElements()) { 
                String idManoEscenarioString = llaves.nextElement();
                int idManoEscenario = Integer.parseInt(idManoEscenarioString);
                boolean existe = false;
                for (int i = 0; i < listaManos.count(); i++){
                 int idManoLeap= listaManos.get(i).id();
                
                    if(idManoEscenario == idManoLeap) {
                    existe = true;
                       break;
                    }
                }
           //si no existe mano de escenario en leap, se elimina de escenario
             if (!existe){
                   escenario.eliminarMano(idManoEscenarioString);
             }         
           }
        }
    }
    
    
    public void setEscenario(EscenarioVirtual escenarioIn){
        this.escenario = escenarioIn;
    }
    
}

public class Leap {
    EscenarioVirtual escenario;
    ControladorHilos controladorHilos;
    public Leap( EscenarioVirtual escenarioI, ControladorHilos controladorHilosIn)
    {
        this.escenario = escenarioI;
        this.controladorHilos = controladorHilosIn;
    }
    
     public Leap( HiloLeap hilo)
    {
        this.escenario = hilo.escenario;
        this.controladorHilos = hilo.controladorHilos;
    }
     
     public void correr() {
        // Creacion del listener y controller
        SampleListener listener = new SampleListener(this.controladorHilos);
        listener.setEscenario(escenario);
        Controller controller = new Controller();

        // Para que Listener reciba eventos del controller
        controller.addListener(listener);

        // Mantener el proceso hasta ENTER
        System.out.println("Presione Enter para salir...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Eliminar el listener
        controller.removeListener(listener);
    }
}

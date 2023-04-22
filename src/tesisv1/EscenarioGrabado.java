/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author miguel
 */
public class EscenarioGrabado implements Serializable{
    Hashtable<String,Mano> Manos = new Hashtable<String,Mano>();
    int numero;

    EscenarioGrabado(EscenarioVirtual escenarioVirtual, int contador) {
        if(escenarioVirtual == null)
        {
            System.out.println("no hay escenario");
        }
        else{
        if(escenarioVirtual.Manos != null)
        {
             this.Manos = (Hashtable<String, Mano>) escenarioVirtual.Manos.clone();
        }  
        }
        this.numero = contador;    
        
    }
    
    public void MostrarManos(){
         System.out.println("num " + this.numero);
        
        if(!this.Manos.isEmpty())
        {
           //  System.out.println("Hay manos");
          Enumeration<String> llaves = this.Manos.keys();
          while (llaves.hasMoreElements()) {          
            
             String llave = llaves.nextElement();
              System.out.println("elemento " + llave);
             Mano mano = this.Manos.get(llave);
             mano.PrintMano();
             //  ManosIn.get(llaves.nextElement()).PrintMano(); 
            
           }
        }else{
            System.out.println("Manos vacias ");
       }
    }
 
}

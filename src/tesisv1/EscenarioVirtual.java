/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import com.leapmotion.leap.Hand;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author miguel
 */
public class EscenarioVirtual{
 
    Hashtable<String,Mano> Manos; // = new Hashtable<String,Mano>;
    Piano Piano;

    public EscenarioVirtual(){
       this.Manos = new Hashtable<String,Mano>();
       this.Piano = getPiano();
    // this.Piano.printTeclado();
    }
    void eliminarMano(String idManoEscenarioString) {
        this.Manos.remove(idManoEscenarioString);
        if (AuxPrint.elimMano)
            System.out.println("Se elimino mano: " + idManoEscenarioString);
    }

    void addModifMano(Hand auxHand) {
        Mano auxMano = new Mano(auxHand); //add cambios de dedos tambien
        String id = auxMano.ID + "";
        this.Manos.put(id, auxMano);
       
         if (AuxPrint.addModifMano)
             System.out.println("Se add-modif mano: " + id);
    }
    
    public void mostrarManos(){
        if(!this.Manos.isEmpty())
        {
          Enumeration<String> llaves = this.Manos.keys();
          while (llaves.hasMoreElements()) {               
              this.Manos.get(llaves.nextElement()).PrintMano();                 
           }
        }
    }

    public Piano getPiano() {
       Nota notaAux = new Nota("do",4);
      // double[] posicionInicial = {-180.0, 160.0 , -155.0};
       double[] posicionInicial = {-180.0, 108.0 , -145.0};
       Piano pianoAux = new Piano(notaAux,26, posicionInicial);     
       return pianoAux;
    }
   
}

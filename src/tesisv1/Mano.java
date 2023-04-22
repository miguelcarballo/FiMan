/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Finger;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author miguel
 */
public class Mano implements Serializable{
    int ID;
    float[] PosicionMuneca;
    Hashtable<String,Dedo> Dedos = new Hashtable<String,Dedo>();
    String Tipo;
    double[] VelocidadMuneca;

    Mano(Hand auxHand) {   
       this.ID = auxHand.id();
       this.PosicionMuneca = auxHand.wristPosition().toFloatArray();
       this.setHashDedos(auxHand);
       this.Tipo = convHand(auxHand.isRight());
       this.VelocidadMuneca = Operacion.convCoordFloatToDouble(auxHand.palmVelocity().toFloatArray());
    }
    
    public void PrintMano(){
        if (AuxPrint.mano){
        System.out.println("   MANO " + this.ID +"  muneca = " + PosicionMuneca[0] + 
                ", " +PosicionMuneca[1] + ", "+ PosicionMuneca[2]);
        
       // this.mostrarDedos();
        }
    }
    
    public double[] getDoublePosicionMuneca(){
        double[] dOut = new double[3];
        dOut[0] = (double)this.PosicionMuneca[0];
        dOut[1] = (double)this.PosicionMuneca[1];
        dOut[2] = (double)this.PosicionMuneca[2];
        return dOut;       
    }
    
    public void addModifDedo(Finger fingerIn){ 
        Dedo auxDedo = new Dedo(fingerIn);
        String id = auxDedo.ID + "";
        this.Dedos.put(id, auxDedo);    
    }
    
    public void eliminarDedo(){
        
    }
    
    public void setHashDedos(Hand HandIn){
        //Hashtable<String,Dedo> auxHash= new Hashtable<String,Dedo>();        
        for (Finger finger : HandIn.fingers()) {
           // System.out.println(finger);
            this.addModifDedo(finger);  
         }     
    }
    
    
    public void mostrarDedos(){
        if(!this.Dedos.isEmpty())
        {
          Enumeration<String> llaves = this.Dedos.keys();
        
          while (llaves.hasMoreElements()) {               
              this.Dedos.get(llaves.nextElement()).printDedo();
           }
        }
    }

    private String convHand(boolean esDerecha) {
       String mano;
       if(esDerecha)
       {
           mano = "RH";
       }
       else {
           mano = "LH";
       }
       return mano; 
    }
}

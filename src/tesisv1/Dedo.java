/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
/**
 *
 * @author miguel
 */
public class Dedo implements Serializable{
   int ID;
   int Tipo;
   Hashtable<String,Hueso> Huesos = new Hashtable<String,Hueso>();
   double[] Punta;
   double[] Velocidad;
   String Mano;
   float[] VectorDedo;
   
    //Hueso[] Huesos;
    
    public Dedo(Finger fingerIn){
       this.ID = fingerIn.id(); 
       this.Tipo  = convFingerTipo(fingerIn.type());
       this.setHashDedos(fingerIn);
       this.Punta = Operacion.convCoordFloatToDouble(fingerIn.tipPosition().toFloatArray());  
       this.Velocidad = Operacion.convCoordFloatToDouble(fingerIn.tipVelocity().toFloatArray());
       this.Mano = convHand(fingerIn.hand().isRight());
       this.VectorDedo = getVector(fingerIn);
    }
    public void printDedo(){
        System.out.println("    dedo ID"+ this.ID + "  tipo = " + this.Tipo);
        this.mostrarHuesos();
    }

    private void setHashDedos(Finger fingerIn) {
        for(Bone.Type boneType : Bone.Type.values()) {
                    Bone bone = fingerIn.bone(boneType);
                  /*  System.out.println("      " + bone.type()
                                     + " bone, start: " + bone.prevJoint()
                                     + ", end: " + bone.nextJoint()
                                     + ", direction: " + bone.direction());*/
             this.addModifHueso(bone); 
        }
    }

    private void addModifHueso(Bone boneIn) {
        Hueso auxHueso = new Hueso(boneIn);
        String id = auxHueso.Tipo + "";
        this.Huesos.put(id, auxHueso);
               
    }
    
    private void mostrarHuesos(){
        if(!this.Huesos.isEmpty())
        {
          Enumeration<String> llaves = this.Huesos.keys();
        
          while (llaves.hasMoreElements()) {               
              this.Huesos.get(llaves.nextElement()).printHueso();
           }
        }
    }

    private int convFingerTipo(Finger.Type type) {
        int tipo= 0;
            if (type == Finger.Type.TYPE_THUMB){
                tipo = 1;
            }
            else if (type == Finger.Type.TYPE_INDEX){
                tipo = 2;
            }
            else if (type == Finger.Type.TYPE_MIDDLE){
                tipo = 3;
            }
            else if (type == Finger.Type.TYPE_RING){
                tipo = 4;
            }
            else if (type == Finger.Type.TYPE_PINKY){
                tipo = 5;
            }
        
        return tipo;
    
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

    private float[] getVector(Finger fingerIn) {
        float[] aux = fingerIn.bone(Bone.Type.TYPE_DISTAL).direction().toFloatArray();
        return aux;
    }
}

package tesisv1;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Matrix;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author miguel
 */
public class Hueso implements Serializable{
    
  
    String Tipo;
    float[] PosicionI;
    float[] PosicionF;
    float Ancho;
    float Largo;
    float[] vBaseX;
    float[] vBaseY;
    float[] vBaseZ;

    Hueso(Bone boneIn) {
        this.Tipo = boneIn.type().toString();
        this.PosicionI = boneIn.prevJoint().toFloatArray();
        this.PosicionF = boneIn.nextJoint().toFloatArray();
        this.Ancho = boneIn.width();
        this.Largo = boneIn.length();
        
       // System.out.println("l " + this.Largo);
        this.vBaseX = boneIn.basis().getXBasis().toFloatArray();
        this.vBaseY = boneIn.basis().getYBasis().toFloatArray();
        this.vBaseZ = boneIn.basis().getZBasis().toFloatArray();

       // System.out.println("Base: "+ boneIn.basis().toString());
    }

    public void printHueso() {
        System.out.println("        hueso Tipo "+ this.Tipo);
    }
    
     public double[] getDoublePHIni(){
        double[] dOut = new double[3];
        dOut[0] = (double)this.PosicionI[0];
        dOut[1] = (double)this.PosicionI[1];
        dOut[2] = (double)this.PosicionI[2];
        return dOut;       
    }
     
     public double[] getDoublePHFin(){
        double[] dOut = new double[3];
        dOut[0] = (double)this.PosicionF[0];
        dOut[1] = (double)this.PosicionF[1];
        dOut[2] = (double)this.PosicionF[2];
        return dOut;       
    }
    
}

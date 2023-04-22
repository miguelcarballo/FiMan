/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import com.leapmotion.leap.Matrix;
import com.leapmotion.leap.Vector;
import java.awt.Color;

/**
 *
 * @author miguel
 */
public class DibujoHuesoV2 extends Malla{
    Hueso hueso;
    float ancho;
    Matrix matriz;
    int separacion = 6;
   
    
    public DibujoHuesoV2(Hueso huesoIn, boolean esPulgar, boolean corrector, Pgrafico buffer){
        this.hueso = huesoIn;
        ancho = this.hueso.Ancho*2/5;
        switch (huesoIn.Tipo) {
            case "TYPE_METACARPAL":
               this.separacion = 30;
              
                ancho = this.hueso.Ancho/2;
                break;
            case "TYPE_PROXIMAL":
                ancho = this.hueso.Ancho*0.45f;
                if(esPulgar){
                    this.separacion = 30;
                }  break;
            case "TYPE_INTERMEDIATE":
               
                ancho = this.hueso.Ancho*0.4f;
                if(esPulgar){
                    this.separacion = 6;
                }  break;
            case "TYPE_DISTAL":
                this.separacion = 3;
                ancho = this.hueso.Ancho*0.4f;
                break;
        }
        
             
      
       
        Vector origenInicio = new Vector(huesoIn.PosicionI[0],huesoIn.PosicionI[1],huesoIn.PosicionI[2]);
        Vector origenFin = new Vector(huesoIn.PosicionF[0],huesoIn.PosicionF[1],huesoIn.PosicionF[2]);
        
        Vector vBaseX = new Vector(huesoIn.vBaseX[0],huesoIn.vBaseX[1],huesoIn.vBaseX[2]);
        Vector vBaseY = new Vector(huesoIn.vBaseY[0],huesoIn.vBaseY[1],huesoIn.vBaseY[2]);
        Vector vBaseZ = new Vector(huesoIn.vBaseZ[0],huesoIn.vBaseZ[1],huesoIn.vBaseZ[2]);
        
        Vector vectorY = new Vector(vBaseY.normalized());
        Vector vectorZ = new Vector(vBaseZ.normalized()).times(-separacion);
        
        if (corrector){
            vBaseX.times(-1);
        }
        
        this.matriz = new Matrix(vBaseX, vBaseY, vBaseZ);
           
        int contCiclo = 0;
        float radioDedo = this.ancho;
        for(int i = 0; i <= huesoIn.Largo; i = i+separacion){ // avance en Hueso    Se crean vertices
            int anguloRotacion = 0;
            if(huesoIn.Tipo.equals("TYPE_DISTAL")){
               radioDedo = getRadio(huesoIn.Largo, i, this.ancho);
            }
            for (int j = 0; j <= 7; j++){
               
                this.matriz.setRotation(vBaseZ, (float) (anguloRotacion*Math.PI/180)); 
                Vector rotacion1 = new Vector(vectorY).times(radioDedo);
                this.matriz.setOrigin(origenInicio);
                Vector punto1inicio = new Vector(this.matriz.transformPoint(rotacion1));
                double[] p1Ini;
                p1Ini = Operacion.conversor(Operacion.convCoordFloatToDouble(punto1inicio.toFloatArray()).clone());
                this.agregarvertice(p1Ini[0],p1Ini[1] ,p1Ini[2]);
                anguloRotacion = anguloRotacion + 45;
            }
           origenInicio = new Vector(this.matriz.transformPoint(vectorZ));
           contCiclo = contCiclo+1;
        }
        //agregar posicion del final
        int anguloRotacion = 0;
        if(!huesoIn.Tipo.equals("TYPE_DISTAL")){
            for (int j = 0; j <= 7; j++){

                    this.matriz.setRotation(vBaseZ, (float) (anguloRotacion*Math.PI/180)); 
                    Vector rotacion1 = new Vector(vectorY).times(radioDedo);
                    this.matriz.setOrigin(origenInicio);
                   this.matriz.setOrigin(origenFin);
                    Vector punto1fin = new Vector(this.matriz.transformPoint(rotacion1));
                    double[] p1Fin = Operacion.conversor(Operacion.convCoordFloatToDouble(punto1fin.toFloatArray()).clone());
                    this.agregarvertice(p1Fin[0],p1Fin[1] ,p1Fin[2]);
                    anguloRotacion = anguloRotacion + 45;
             }
        contCiclo = contCiclo+1;
        }
        
         this.settbuffer(buffer);

        int contTotal = 0;
        for(int k = 0; k < contCiclo; k++){ // se crean aristas

            //----OJO
            //if(hueso.Tipo.equals("TYPE_DISTAL"))
           // {//
            for(int l = 0; l<=7; l++){
                if(l!=7){
                     this.agregararista(contTotal, contTotal + 1); //para cerrar octogono
                }else{
                    this.agregararista(contTotal, contTotal - 7);
                }        
                
                if(k>0){ // para ligar a los anteriores vertices a partir del 2do ciclo
                     this.agregararista(contTotal, contTotal - 8);
                }  
                contTotal = contTotal+1;
                //System.out.println("total: " + contTotal);
            }    
            //}
       }
       
        if(huesoIn.Tipo.equals("TYPE_DISTAL")){
             double[] p1Fin = Operacion.conversor(Operacion.convCoordFloatToDouble(origenFin.toFloatArray()).clone());
             this.agregarvertice(p1Fin[0],p1Fin[1] ,p1Fin[2]);
             for(int l = 1; l<=8; l++){
                
                   this.agregararista(contTotal, contTotal - l);
              }
            
             
        }
        
        
        this.color=Color.blue;
          setorigen();
          centrom();
    }

    private float getRadio(float largoDedo, int posActual, float radioInicial) {
        double potencia = 6;
        float radio;
        radio = (float) (radioInicial*(-Math.pow((double)posActual,potencia) / Math.pow((double)largoDedo,potencia) + 1));       
        return radio;
    }
}

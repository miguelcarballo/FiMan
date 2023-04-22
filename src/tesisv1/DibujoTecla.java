package tesisv1;

import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author miguel
 */
public class DibujoTecla extends Malla{
    double d = Operacion.distancia;
    double corrAltura = Operacion.correccionAltura;
    
    public DibujoTecla(double[][] coordenadas, boolean esBlanco,  Pgrafico buffer){
         
         int tamCoord = coordenadas.length;
         
         this.settbuffer(buffer);
         
         for (int i = 0; i < tamCoord; i++){
             double[] c = Operacion.conversor(coordenadas[i]);    
           
             this.agregarvertice(c[0], c[1] - corrAltura, c[2] + d);   
         }
         
          for (int i = 0; i < tamCoord - 1; i++){
               this.agregararista(i, i+1);
          }
          
          this.agregararista(tamCoord -1, 0);
          if (esBlanco) {
          this.color=Color.green;
          }
          else{this.color=Color.GRAY; }
          
          
          setorigen();
          centrom();
    }
    
}

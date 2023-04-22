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
public class Operacion {
    
    static double proporcionDis = 10.0;
    static double distancia = 0.0;
    static double correccionAltura = 1.4;
    static int intervaloFijoGrabado = 20;
    
    
     public static double[] conversor (double[] dinf) {
         double[] din = dinf.clone();
         
         double[] conv = {-1000, -1000, -1000};
         din[1] = din[1] - 150; 
         din[2] = - din[2] + 300;
         
         for (int i = 0; i <= 2; ++i )
         {
             conv[i] = din[i]/proporcionDis;
         }
         return conv;
    } 
     
     public static double convProporcion(double din){
         double dout = din/proporcionDis;  
         return dout;
     }
     
     
      public static double[] convCoordFloatToDouble(float[] PosicionI){
        double[] dOut = new double[3];
        dOut[0] = (double)PosicionI[0];
        dOut[1] = (double)PosicionI[1];
        dOut[2] = (double)PosicionI[2];
        return dOut;       
    }
      
       public static double[] Centro(){
           double[] centroReal = new double[]{0,100,0};
           double[] aux = conversor(centroReal);
           return aux;
       }
       
       public static int getMilisegundo(int numeroDeFrame) {
       int numMilis = (numeroDeFrame*Operacion.intervaloFijoGrabado)%1000;
       return numMilis;
    }

      public static int getSegundo(int numeroDeFrame) {      
        int miliReal = numeroDeFrame*Operacion.intervaloFijoGrabado;
        int numSec = ((miliReal-(miliReal%1000))/1000)%60;        
        return numSec;
    }

    public static int getMinuto(int numeroDeFrame) {
        int miliReal = numeroDeFrame*Operacion.intervaloFijoGrabado;
        int secReal = ((miliReal-(miliReal%1000))/1000);
        int minReal = (secReal-(secReal%60))/60;  
        return minReal;
    }
     
}

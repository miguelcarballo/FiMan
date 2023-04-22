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
public class Tecla extends TeclaMus{

    double PosicionAltura;
    double[][] Posicion;
    double[] PosicionFinal;
   
    
    
    
    
    public Tecla(Nota notaIn, double[] PosicionIniIn) {
        this.nota = notaIn;
        this.Posicion = OperacionMus.getPosTecla(notaIn, PosicionIniIn);
        this.PosicionAltura = PosicionIniIn[1];
        if (notaIn.esBlanca) {
            this.PosicionFinal = this.Posicion[7];
        } else{
            this.PosicionFinal = this.Posicion[3];
        }
    }  
    
    
     public void moverTecla(double cantidad, int direccion){
      // double[][] posicionAux = this.Posicion.clone();
       int cantVertices;
       if (this.nota.esBlanca)
       {cantVertices = 8;}
       else{cantVertices = 4;}
       
       if(direccion == 0){
           for(int i = 0; i < cantVertices; i++){
               this.Posicion[i][0] = this.Posicion[i][0] + cantidad;
           }
       }else if (direccion==1){
           for(int i = 0; i < cantVertices; i++){
               this.Posicion[i][1] = this.Posicion[i][1] + cantidad;
           }
       }
       else if(direccion==2){
           for(int i = 0; i < cantVertices; i++){
               this.Posicion[i][2] = this.Posicion[i][2] + cantidad;
           }
       }
       
       //this.Posicion = posicionAux;
       this.PosicionAltura = this.Posicion[0][1];
        if (this.nota.esBlanca) {
            this.PosicionFinal = this.Posicion[7];
        } else{
            this.PosicionFinal = this.Posicion[3];
        } 
    }
     
    public void printTecla(){
       System.out.println(this.nota.nombre);
       for (int i = 0; i < this.Posicion.length; i++){
            System.out.println("   x = " + this.Posicion[i][0] + " y = " + this.Posicion[i][1] + " z = " + this.Posicion[i][2]);
       }
       
       System.out.println("++++++++++++++++++++++");
    }
    
    public boolean estaEnTecla(double[] pos){
        boolean esta;
        if (this.nota.esBlanca){
           double[][] rectangulo1 = new double[4][3];
           double[][] rectangulo2 = new double[4][3];

           rectangulo1[0] = this.Posicion[0]; 
           rectangulo1[1] = this.Posicion[1]; 
           rectangulo1[2] = this.Posicion[6]; 
           rectangulo1[3] = this.Posicion[7]; 
           
           rectangulo2[0] = this.Posicion[2]; 
           rectangulo2[1] = this.Posicion[3]; 
           rectangulo2[2] = this.Posicion[4]; 
           rectangulo2[3] = this.Posicion[5]; 
           
           boolean esta1 = OperacionMus.coord1Area2(pos, rectangulo1);
           boolean esta2 = OperacionMus.coord1Area2(pos, rectangulo2);
           
           esta = esta1 || esta2;

        }else{
            esta = OperacionMus.coord1Area2(pos, this.Posicion);
        }
        
        return esta;
    }
}

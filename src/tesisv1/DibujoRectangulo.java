/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.Color;

/**
 *
 * @author miguel
 */
public class DibujoRectangulo extends Malla {
    double distancia = Operacion.distancia;
    double lado = 1;
    
    public DibujoRectangulo (double[] dinI, Pgrafico buffer) {
         double[] din = Operacion.conversor(dinI.clone());
                 
        this.settbuffer(buffer);
         this.xo= din[0];
        this.yo=din[1];
        this.zo=din[2];
         double d= distancia;
          //-----
        
        this.agregarvertice(-lado,-lado ,-lado+d);
        this.agregarvertice(lado, -lado, -lado+d);
        this.agregarvertice(lado, -lado, lado+d);
        this.agregarvertice(lado, lado, lado+d);
        this.agregarvertice(-lado, lado, lado+d);
        this.agregarvertice(-lado, lado, -lado+d);
        this.agregarvertice(-lado, -lado, lado+d);
        this.agregarvertice(lado, lado, -lado+d);
    
          this.agregarvertice(6, 0, 0+d);
            this.agregarvertice(0, 6, 0+d);
            this.agregarvertice(-6, 0, 0+d);
            this.agregarvertice(0, -6, 0+d);
            this.agregarvertice(0, 0, -6+d);
             this.agregarvertice(0, 0, 6+d);
         

        
         this.agregararista(0, 1);
         this.agregararista(1, 7);
         this.agregararista(7, 5);
         this.agregararista(5, 0);

         this.agregararista(1, 2);
         this.agregararista(2, 3);
         this.agregararista(3, 7);

         this.agregararista(3, 4);
         this.agregararista(4, 6);
         this.agregararista(6, 2);

         this.agregararista(4, 5);
         this.agregararista(6, 0);
        
       
        this.color=Color.red;
        setorigen();
        centrom();
    }

}

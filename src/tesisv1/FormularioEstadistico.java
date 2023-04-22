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
public class FormularioEstadistico {
    String nombre;
    String titulo;
    int[] tiempoInt;
    double[] dato;

    FormularioEstadistico(String nombreArchivoCargado, int cantidadDatos, int tipoDato) {
        this.nombre = nombreArchivoCargado;
        this.tiempoInt = new int[cantidadDatos];
        this.dato = new double[cantidadDatos]; 
        
        if(tipoDato == 0)
        {
            this.titulo = "Grafico X vs Tiempo";
        }else if(tipoDato == 1){
            this.titulo = "Grafico Y vs Tiempo";
        }
        else if(tipoDato == 2){
             this.titulo = "Grafico Z vs Tiempo";
        }
        else if(tipoDato == 3){
             this.titulo = "Grafico Rapidez vs Tiempo";      
        }
    }
}

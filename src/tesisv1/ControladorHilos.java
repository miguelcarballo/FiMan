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
public class ControladorHilos {
    public boolean hiloGrabacion;
    public boolean hiloLeap;
    public boolean estadoEnReproduccion;
    public boolean estadoArchivoCargado;
    public int intervaloReproduccion;
    public boolean pausado;
    public double velocidadReproduccion;
    public String nombreArchivoCargado;
    public String direccionArchivoCargado;
    public boolean frameExportarActivado;
    public boolean pararActivado;
    
    public ControladorHilos(){
        this.hiloGrabacion = true;
        this.hiloLeap = true;
        this.estadoEnReproduccion = false;
        this.estadoArchivoCargado = false;
        this.intervaloReproduccion = Operacion.intervaloFijoGrabado;
        this.pausado = false;
        this.velocidadReproduccion = 1;   
        this.nombreArchivoCargado = "";
        this.direccionArchivoCargado = "";
        this.frameExportarActivado = false;
        this.pararActivado = false;
    }
    
    public void DoblarVelocidad(){
        if(this.intervaloReproduccion==1){
            this.intervaloReproduccion=2;
        }
        this.velocidadReproduccion = this.velocidadReproduccion*2;
        this.intervaloReproduccion = this.intervaloReproduccion/2;
         if(this.intervaloReproduccion==0){
            this.intervaloReproduccion=1;
        }
    }
    
    public void RalentizarVelocidad(){
        this.velocidadReproduccion = this.velocidadReproduccion/2;
         this.intervaloReproduccion = this.intervaloReproduccion*2;
    }
    
    public void ResetearVelocidad(){
        this.velocidadReproduccion = 1;   
        this.intervaloReproduccion = Operacion.intervaloFijoGrabado;
    }
    
}

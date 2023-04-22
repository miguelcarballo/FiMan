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
public class ControlBotonesPCTeclado {
    boolean presionado;
    int direccion;
    int cantMov;
    
    public ControlBotonesPCTeclado(){
        this.presionado = false;
        this.direccion = 0;
        this.cantMov = 1;
    }
}

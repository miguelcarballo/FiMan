/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;
import java.util.EventObject;
/**
 *
 * @author miguel
 */
public class EstadoEvento extends EventObject {
    private Estado _estado;
    private Nota _nota;
    private double _velocidadY;
    
    public EstadoEvento(Object source, Estado estado, Nota nota, double velocidadY) {
        super(source);
        _estado = estado;
        _nota = nota;
        _velocidadY = velocidadY;
    }
    public Estado estado(){
        return _estado;
    }
    
    public Nota nota(){
        return _nota;
    }
    
    public double velocidadY(){
        return _velocidadY;
    }
}

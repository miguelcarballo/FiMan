/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author miguel
 */
public class TeclaMus {
    Nota nota;
   public Estado _estado = Estado.SUELTO;
    private List _listeners = new ArrayList();
    public double velocidadActual = 0;
    
    public synchronized void esPresionada(){
        if (_estado == Estado.SUELTO){
            _estado = Estado.PRESIONADO;
            _activarEstadoEvento();
        }
    }
    
    public synchronized void esSoltada(){
        if (_estado == Estado.PRESIONADO){
            _estado = Estado.SUELTO;
            _activarEstadoEvento();
        }
    }
    
    public synchronized void addEstadoListener(EstadoListener l){
        _listeners.add(l);
    }
    
    public synchronized void removeEstadoListener(EstadoListener l){
        _listeners.remove(l);
    }
    
    public synchronized void _activarEstadoEvento(){
        EstadoEvento estado = new EstadoEvento(this,_estado, nota, velocidadActual);
        Iterator listeners = _listeners.iterator();
        while(listeners.hasNext()){
            ((EstadoListener)listeners.next()).estadoRecibido(estado);
        }
    }   
}

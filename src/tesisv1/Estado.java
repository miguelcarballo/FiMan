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
public class Estado {
    public static final Estado PRESIONADO = new Estado("Presionado");
    public static final Estado SUELTO = new Estado("Suelto");
    
    private String _estado;
    
    public String toString(){
        return _estado;
    }
    private Estado(String estado) {
        _estado = estado;
    }   
}

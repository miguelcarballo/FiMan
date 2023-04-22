package tesisv1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author miguel
 */
public class Nota {
    String nombre;
    String nombreEnarmonico;
    int octava;
    int numNotaEscala;
    int numMIDI;
    boolean esBlanca;
    
    public Nota(String nombreIn, int octavaIn){
        int aux = OperacionMus.getNumEscala(nombreIn);
            this.nombre = nombreIn;
            this.octava = octavaIn;
            this.numNotaEscala = aux;
            this.numMIDI = OperacionMus.getNumMIDI(aux,octavaIn);   
            this.esBlanca = OperacionMus.getEsBlanca(nombreIn);
    }   
    
    public Nota(int numNotaEscalaIn, int octavaIn){
        String auxN = OperacionMus.getNombreInt(numNotaEscalaIn);
           this.nombre = auxN;
            this.octava = octavaIn;
            this.numNotaEscala = numNotaEscalaIn;
            this.numMIDI = OperacionMus.getNumMIDI(numNotaEscalaIn,octavaIn);   
            this.esBlanca = OperacionMus.getEsBlanca(auxN);
    }
}

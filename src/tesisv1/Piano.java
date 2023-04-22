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
public class Piano {
   
    Tecla[] Teclado;
    //String TipoSonido;
    double[] PosicionInicial;
    boolean activado;
    int numTeclas;
    
    
    Piano(Nota NotaIni, int nNotas, double[] posInicial) {
        
        this.Teclado = getTeclado(NotaIni, nNotas, posInicial);
        this.PosicionInicial = posInicial;
        this.numTeclas = nNotas;
        this.activado = false;
    }
    
    Piano(){
         Nota notaAux = new Nota("do",4);
      // double[] posicionInicial = {-180.0, 160.0 , -155.0};
       double[] posicionInicialF = {-180.0, 108.0 , -145.0};
         this.Teclado = getTeclado(notaAux,26, posicionInicialF);
        this.PosicionInicial = posicionInicialF;
        this.numTeclas = 26;
        this.activado = false;
    }
    
    public void moverTeclado(double cantidad, int direccion){
        for (int i = 0; i < this.Teclado.length; i++){
            this.Teclado[i].moverTecla(cantidad, direccion);
        }  
    }

    private Tecla[] getTeclado(Nota NotaIni, int nNotas, double[] posInicial) {
        
        Tecla[] tecladoAux = new Tecla[nNotas];
        Nota auxNota = NotaIni;
        double[] posAux = posInicial;
        int cont = 0;
        
        for (int i = 0; i < nNotas; i++){
           Tecla taux = new Tecla(auxNota,posAux);
           tecladoAux[cont] = taux;
           auxNota = OperacionMus.getNotaSuperior(auxNota);
           cont = cont +1;
           posAux= taux.PosicionFinal.clone();//FFFF
        }       
       return tecladoAux;
    }
    
    public void printTeclado(){
        for (int i = 0; i< this.Teclado.length; i++){
            this.Teclado[i].printTecla();
        }
    }
    
    /*public void verifTecladoTocado(double[] posIn){
        for (int i = 0; i< this.Teclado.length; i++){
            if (this.Teclado[i].estaEnTecla(posIn)){
                this.Teclado[i].esPresionada();
            } 
        }
    }*/
    
    void addEstadoListener(EstadoListener MusicPanel) {
        for (int i = 0; i< this.numTeclas; i++){
            this.Teclado[i].addEstadoListener(MusicPanel);
            this.activado = true;
        }          
    }
}

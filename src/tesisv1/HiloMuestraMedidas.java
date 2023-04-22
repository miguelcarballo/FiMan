/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import com.leapmotion.leap.Vector;
import java.util.Enumeration;
import javax.swing.JLabel;


/**
 *
 * @author miguel
 */
public class HiloMuestraMedidas extends Thread{
     EscenarioVirtual escenario;
     PanelMuestraMedidas panelMuestraMedidas;
     Mano manoDerecha;
     Mano manoIzquierda;
     
    
    public HiloMuestraMedidas(EscenarioVirtual escenarioIn, PanelMuestraMedidas panelIn){
        this.escenario = escenarioIn;
        this.panelMuestraMedidas = panelIn;
    }
    
    @Override
    public void run(){
        try {
       while(true){
                revisarAngulos();
                revisarVacios();
            
                Thread.sleep(200);
            
        }     
       } catch (InterruptedException ex) {
      }
    }

    private void revisarAngulos() {
        if (this.escenario.Manos!=null){
           Enumeration<String> llaves= this.escenario.Manos.keys();
           while(llaves.hasMoreElements()){   
              Mano manoAux = this.escenario.Manos.get(llaves.nextElement());
              if(manoAux.Tipo.equals("RH")){
                  this.manoDerecha = manoAux;
                  calculoAngulosDedos(this.manoDerecha);
              }
              else{
                  this.manoIzquierda = manoAux;
                  calculoAngulosDedos(this.manoIzquierda);
              } 
           }
        }
   //si hay manos tomar primera mano
        // si hay dedo, sacar vector, hacer calculos
        //Mostrar el resultado en la etiqueta correspondiente
    }

    private void calculoAngulosDedos(Mano mano) {
           if(mano!=null){
                Enumeration<String> llaves= mano.Dedos.keys();
                while(llaves.hasMoreElements()){  
                    Dedo dedoAux =  mano.Dedos.get(llaves.nextElement());
                    Vector vector = new Vector(dedoAux.VectorDedo[0],dedoAux.VectorDedo[1],dedoAux.VectorDedo[2]);
                    int auxGradoA = getGradoA(vector);
                    int auxGradoB = getGradoB(vector);
                    
                    mostrarEnPanelGradoA(mano.Tipo, dedoAux.Tipo, auxGradoA);
                    mostrarEnPanelGradoB(mano.Tipo, dedoAux.Tipo, auxGradoB);
                }
           }
    }

    private int getGradoA(Vector vector) {
       
        float anguloRadianes = vector.pitch();
        int grados = 180 - (int) ((180*anguloRadianes)/Math.PI);
        return grados;
    }
    
     private int getGradoB(Vector vector) {
      
         float anguloRadianes = vector.roll();
        int grados = 180+ (int) ((180*anguloRadianes)/Math.PI);
        return grados;
    }

    private void mostrarEnPanelGradoA(String TipoMano, int TipoDedo, int gradoIn) {
        if(TipoMano.equals("RH")){
            if(TipoDedo == 1){
                this.panelMuestraMedidas.lblRH1a.setText("" + gradoIn + "°");
            }
            else if(TipoDedo == 2){
                this.panelMuestraMedidas.lblRH2a.setText("" + gradoIn + "°");
            }
             else if(TipoDedo == 3){
                this.panelMuestraMedidas.lblRH3a.setText("" + gradoIn + "°");
            } else if(TipoDedo == 4){
                this.panelMuestraMedidas.lblRH4a.setText("" + gradoIn + "°");
            } else if(TipoDedo == 5){
                this.panelMuestraMedidas.lblRH5a.setText("" + gradoIn + "°");
            }
            
        }else{
            if(TipoDedo == 1){
                this.panelMuestraMedidas.lblLH1a.setText("" + gradoIn + "°");
            }
            else if(TipoDedo == 2){
                this.panelMuestraMedidas.lblLH2a.setText("" + gradoIn + "°");
            }
             else if(TipoDedo == 3){
                this.panelMuestraMedidas.lblLH3a.setText("" + gradoIn + "°");
            } else if(TipoDedo == 4){
                this.panelMuestraMedidas.lblLH4a.setText("" + gradoIn + "°");
            } else if(TipoDedo == 5){
                this.panelMuestraMedidas.lblLH5a.setText("" + gradoIn + "°");
            }
        }
        
        
    }
    
     private void mostrarEnPanelGradoB(String TipoMano, int TipoDedo, int gradoIn)  {
        if(TipoMano.equals("RH")){
            if(TipoDedo == 1){
                this.panelMuestraMedidas.lblRH1b.setText("" + gradoIn + "°");
            }
            else if(TipoDedo == 2){
                this.panelMuestraMedidas.lblRH2b.setText("" + gradoIn + "°");
            }
             else if(TipoDedo == 3){
                this.panelMuestraMedidas.lblRH3b.setText("" + gradoIn + "°");
            } else if(TipoDedo == 4){
                this.panelMuestraMedidas.lblRH4b.setText("" + gradoIn + "°");
            } else if(TipoDedo == 5){
                this.panelMuestraMedidas.lblRH5b.setText("" + gradoIn + "°");
            }
            
        }else{
            if(TipoDedo == 1){
                this.panelMuestraMedidas.lblLH1b.setText("" + gradoIn + "°");
            }
            else if(TipoDedo == 2){
                this.panelMuestraMedidas.lblLH2b.setText("" + gradoIn + "°");
            }
             else if(TipoDedo == 3){
                this.panelMuestraMedidas.lblLH3b.setText("" + gradoIn + "°");
            } else if(TipoDedo == 4){
                this.panelMuestraMedidas.lblLH4b.setText("" + gradoIn + "°");
            } else if(TipoDedo == 5){
                this.panelMuestraMedidas.lblLH5b.setText("" + gradoIn + "°");
            }
        }     
    }

    private void revisarVacios() {
       if (this.escenario.Manos!=null){
           int contRH = 0;
           int contLH = 0;
       
           Enumeration<String> llaves= this.escenario.Manos.keys();
           while(llaves.hasMoreElements()){   
              Mano manoAux = this.escenario.Manos.get(llaves.nextElement());
              if (manoAux.Tipo.equals("RH")){
                  contRH = contRH +1;
              }else{
                  contLH = contLH +1;
              }
           }
           
           if(contRH == 0){
                BorrarDatosRH();
           }
           if (contLH == 0){
                 BorrarDatosLH();
           }
       }
       else{
           BorrarDatosRH();
           BorrarDatosLH();
       }

    }

    private void BorrarDatosRH() {
        this.panelMuestraMedidas.lblRH1a.setText("____");
              this.panelMuestraMedidas.lblRH2a.setText("____");
              this.panelMuestraMedidas.lblRH3a.setText("____");
              this.panelMuestraMedidas.lblRH4a.setText("____");
              this.panelMuestraMedidas.lblRH5a.setText("____");
              
                this.panelMuestraMedidas.lblRH1b.setText("____");
              this.panelMuestraMedidas.lblRH2b.setText("____");
              this.panelMuestraMedidas.lblRH3b.setText("____");
              this.panelMuestraMedidas.lblRH4b.setText("____");
              this.panelMuestraMedidas.lblRH5b.setText("____");
    }

    private void BorrarDatosLH() {
         this.panelMuestraMedidas.lblLH1a.setText("____");
              this.panelMuestraMedidas.lblLH2a.setText("____");
              this.panelMuestraMedidas.lblLH3a.setText("____");
              this.panelMuestraMedidas.lblLH4a.setText("____");
              this.panelMuestraMedidas.lblLH5a.setText("____");
              
              this.panelMuestraMedidas.lblLH1b.setText("____");
              this.panelMuestraMedidas.lblLH2b.setText("____");
              this.panelMuestraMedidas.lblLH3b.setText("____");
              this.panelMuestraMedidas.lblLH4b.setText("____");
              this.panelMuestraMedidas.lblLH5b.setText("____");
    }

}

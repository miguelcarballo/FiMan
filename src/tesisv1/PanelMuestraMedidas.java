/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author miguel
 */
public class PanelMuestraMedidas extends JPanel {
    EscenarioVirtual escenario;
    
    int distInternaY = 18;
    int distInternaX = 60;
    int tamxPanel = 200;
     JLabel tituloRH,tituloLH, tituloDedoRH, tituloRHAnguloA, tituloRHAnguloB, lbldedos ;
    JLabel lblRH1a, lblRH2a, lblRH3a, lblRH4a, lblRH5a,lblLH1a, lblLH2a, lblLH3a, lblLH4a, lblLH5a;
     JLabel lblRH1b, lblRH2b, lblRH3b, lblRH4b, lblRH5b,lblLH1b, lblLH2b, lblLH3b, lblLH4b, lblLH5b;
     
    public PanelMuestraMedidas(int xSize, int ySize, EscenarioVirtual escenarioIn){
         this.escenario = escenarioIn;
         this.setLayout(null);
         this.setBounds(xSize - 2*tamxPanel -12, 0, tamxPanel, 535);
         Border bordespanel = new TitledBorder(new EtchedBorder(), "Angulos");
         this.setBorder(bordespanel); 
         InicializarEtiquetas();
         this.setVisible(true);
         
         comenzarHilo();   
        
    }

    private void InicializarEtiquetas() {
        this.tituloRH = new JLabel("R.H.");
        this.tituloRH.setFont(new Font("Serif", Font.BOLD, 15));
        this.tituloRH.setToolTipText("Medidas de Mano derecha");
        this.tituloRH.setBounds(15,distInternaY, tamxPanel,30);
        this.add(this.tituloRH);
        
        this.tituloDedoRH = new JLabel("Dedo");
        this.tituloDedoRH.setFont(new Font("Serif", Font.PLAIN, 13));
        this.tituloDedoRH.setToolTipText("Numeracion de dedos segun sistema pianístico");
        this.tituloDedoRH.setBounds(15,2*distInternaY+7, 60,30);
        this.add(this.tituloDedoRH);
        
        this.tituloRHAnguloA = new JLabel("°Contacto");
        this.tituloRHAnguloA.setFont(new Font("Serif", Font.PLAIN, 13));
        this.tituloRHAnguloA.setToolTipText("Angulo entre el contacto del dedo y el plano horizontal");
        this.tituloRHAnguloA.setBounds(distInternaX,2*distInternaY+7, 100,30);
        this.add(this.tituloRHAnguloA); 
        
        this.tituloRHAnguloB = new JLabel("°Inclinación");
        this.tituloRHAnguloB.setFont(new Font("Serif", Font.PLAIN, 13));
        this.tituloRHAnguloB.setToolTipText("Angulo de inclinación del dedo");
        this.tituloRHAnguloB.setBounds(2*distInternaX+7,2*distInternaY+7, 100,30);
        this.add(this.tituloRHAnguloB); 
        
        this.tituloLH = new JLabel("L.H.");
        this.tituloLH.setFont(new Font("Serif", Font.BOLD, 15));
        this.tituloLH.setToolTipText("Medidas de Mano Izquierda");
        this.tituloLH.setBounds(15,15*distInternaY, tamxPanel,30);
        this.add(this.tituloLH);
        
        this.tituloDedoRH = new JLabel("Dedo");
        this.tituloDedoRH.setFont(new Font("Serif", Font.PLAIN, 13));
        this.tituloDedoRH.setToolTipText("Numeracion de dedos segun sistema pianístico");
        this.tituloDedoRH.setBounds(15,16*distInternaY+7, 60,30);
        this.add(this.tituloDedoRH);
        
        this.tituloRHAnguloA = new JLabel("°Contacto");
        this.tituloRHAnguloA.setFont(new Font("Serif", Font.PLAIN, 13));
        this.tituloRHAnguloA.setToolTipText("Angulo entre el contacto del dedo y el plano horizontal");
        this.tituloRHAnguloA.setBounds(distInternaX,16*distInternaY+7, 100,30);
        this.add(this.tituloRHAnguloA); 
        
        this.tituloRHAnguloB = new JLabel("°Inclinación");
        this.tituloRHAnguloB.setFont(new Font("Serif", Font.PLAIN, 13));
        this.tituloRHAnguloB.setToolTipText("Angulo de inclinación del dedo");
        this.tituloRHAnguloB.setBounds(2*distInternaX+7,16*distInternaY+7, 100,30);
        this.add(this.tituloRHAnguloB); 
        
        this.lbldedos = new JLabel("1");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo pulgar derecho");
        this.lbldedos.setBounds(21,4*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        this.lbldedos = new JLabel("2");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo indice derecho");
        this.lbldedos.setBounds(21,6*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        this.lbldedos = new JLabel("3");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo medio derecho");
        this.lbldedos.setBounds(21,8*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        this.lbldedos = new JLabel("4");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo anular derecho");
        this.lbldedos.setBounds(21,10*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        this.lbldedos = new JLabel("5");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo meñique derecho");
        this.lbldedos.setBounds(21,12*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
          
        this.lbldedos = new JLabel("1");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo pulgar derecho");
        this.lbldedos.setBounds(21,18*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        this.lbldedos = new JLabel("2");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo indice derecho");
        this.lbldedos.setBounds(21,20*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        this.lbldedos = new JLabel("3");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo medio derecho");
        this.lbldedos.setBounds(21,22*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        this.lbldedos = new JLabel("4");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo anular derecho");
        this.lbldedos.setBounds(21,24*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        this.lbldedos = new JLabel("5");
        this.lbldedos.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lbldedos.setToolTipText("Dedo meñique derecho");
        this.lbldedos.setBounds(21,26*distInternaY, 100,30);
        this.add(this.lbldedos); 
        
        
        this.lblRH1a = new JLabel("____");
        this.lblRH1a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH1a.setToolTipText("Grados de contacto (dedo pulgar)");
        this.lblRH1a.setBounds(distInternaX+20,4*distInternaY, 100,30);
        this.add(this.lblRH1a); 
        
        this.lblRH2a = new JLabel("____");
        this.lblRH2a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH2a.setToolTipText("Grados de contacto (dedo indice)");
        this.lblRH2a.setBounds(distInternaX+20,6*distInternaY, 100,30);
        this.add(this.lblRH2a); 
        
        this.lblRH3a = new JLabel("____");
        this.lblRH3a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH3a.setToolTipText("Grados de contacto (dedo medio )");
        this.lblRH3a.setBounds(distInternaX+20,8*distInternaY, 100,30);
        this.add(this.lblRH3a); 
        
        this.lblRH4a = new JLabel("____");
        this.lblRH4a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH4a.setToolTipText("Grados de contacto (dedo anular)");
        this.lblRH4a.setBounds(distInternaX+20,10*distInternaY, 100,30);
        this.add(this.lblRH4a); 
        
        this.lblRH5a = new JLabel("____");
        this.lblRH5a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH5a.setToolTipText("Grados de contacto (dedo meñique)");
        this.lblRH5a.setBounds(distInternaX+20,12*distInternaY, 100,30);
        this.add(this.lblRH5a); 
        
        
          this.lblLH1a = new JLabel("____");
        this.lblLH1a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH1a.setToolTipText("Grados de contacto (dedo pulgar)");
        this.lblLH1a.setBounds(distInternaX+20,18*distInternaY, 100,30);
        this.add(this.lblLH1a); 
        
        this.lblLH2a = new JLabel("____");
        this.lblLH2a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH2a.setToolTipText("Grados de contacto (dedo indice)");
        this.lblLH2a.setBounds(distInternaX+20,20*distInternaY, 100,30);
        this.add(this.lblLH2a); 
        
        this.lblLH3a = new JLabel("____");
        this.lblLH3a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH3a.setToolTipText("Grados de contacto (dedo medio )");
        this.lblLH3a.setBounds(distInternaX+20,22*distInternaY, 100,30);
        this.add(this.lblLH3a); 
        
        this.lblLH4a = new JLabel("____");
        this.lblLH4a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH4a.setToolTipText("Grados de contacto (dedo anular)");
        this.lblLH4a.setBounds(distInternaX+20,24*distInternaY, 100,30);
        this.add(this.lblLH4a); 
        
        this.lblLH5a = new JLabel("____");
        this.lblLH5a.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH5a.setToolTipText("Grados de contacto (dedo meñique)");
        this.lblLH5a.setBounds(distInternaX+20,26*distInternaY, 100,30);
        this.add(this.lblLH5a); 
        
        
        this.lblRH1b = new JLabel("____");
        this.lblRH1b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH1b.setToolTipText("Grados de contacto (dedo pulgar)");
        this.lblRH1b.setBounds(2*distInternaX+25,4*distInternaY, 100,30);
        this.add(this.lblRH1b); 
        
        this.lblRH2b = new JLabel("____");
        this.lblRH2b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH2b.setToolTipText("Grados de contacto (dedo indice)");
        this.lblRH2b.setBounds(2*distInternaX+25,6*distInternaY, 100,30);
        this.add(this.lblRH2b); 
        
        this.lblRH3b = new JLabel("____");
        this.lblRH3b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH3b.setToolTipText("Grados de contacto (dedo medio )");
        this.lblRH3b.setBounds(2*distInternaX+25,8*distInternaY, 100,30);
        this.add(this.lblRH3b); 
        
        this.lblRH4b = new JLabel("____");
        this.lblRH4b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH4b.setToolTipText("Grados de contacto (dedo anular)");
        this.lblRH4b.setBounds(2*distInternaX+25,10*distInternaY, 100,30);
        this.add(this.lblRH4b); 
        
        this.lblRH5b = new JLabel("____");
        this.lblRH5b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblRH5b.setToolTipText("Grados de contacto (dedo meñique)");
        this.lblRH5b.setBounds(2*distInternaX+25,12*distInternaY, 100,30);
        this.add(this.lblRH5b); 
        
        
          this.lblLH1b = new JLabel("____");
        this.lblLH1b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH1b.setToolTipText("Grados de contacto (dedo pulgar)");
        this.lblLH1b.setBounds(2*distInternaX+25,18*distInternaY, 100,30);
        this.add(this.lblLH1b); 
        
        this.lblLH2b = new JLabel("____");
        this.lblLH2b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH2b.setToolTipText("Grados de contacto (dedo indice)");
        this.lblLH2b.setBounds(2*distInternaX+25,20*distInternaY, 100,30);
        this.add(this.lblLH2b); 
        
        this.lblLH3b = new JLabel("____");
        this.lblLH3b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH3b.setToolTipText("Grados de contacto (dedo medio )");
        this.lblLH3b.setBounds(2*distInternaX+25,22*distInternaY, 100,30);
        this.add(this.lblLH3b); 
        
        this.lblLH4b = new JLabel("____");
        this.lblLH4b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH4b.setToolTipText("Grados de contacto (dedo anular)");
        this.lblLH4b.setBounds(2*distInternaX+25,24*distInternaY, 100,30);
        this.add(this.lblLH4b); 
        
        this.lblLH5b = new JLabel("____");
        this.lblLH5b.setFont(new Font("Serif", Font.PLAIN, 15));
        this.lblLH5b.setToolTipText("Grados de contacto (dedo meñique)");
        this.lblLH5b.setBounds(2*distInternaX+25,26*distInternaY, 100,30);
        this.add(this.lblLH5b); 
        
    }

    private void comenzarHilo() {
        HiloMuestraMedidas hilo = new HiloMuestraMedidas(this.escenario,this);
         hilo.start();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author miguel
 */
public class PanelVisual extends JPanel implements ItemListener{
    ControladorImagen controlador; 
    int distInterna = 18;
    int tamxPanel = 200;
    JLabel RH,LH;
    JCheckBox cbTeclado, cbRH1, cbRH2,cbRH3, cbRH4,cbRH5, cbLH1, cbLH2,cbLH3, cbLH4,cbLH5, mRH, mLH;
    
    public PanelVisual(int xSize, int ySize, ControladorImagen controladorImagen){
        
        ManejadorEventos manejador = new ManejadorEventos();
        this.controlador = controladorImagen; 
        this.setLayout(null);
        this.setBounds(xSize - tamxPanel -10, 0, tamxPanel, 200);
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Control de Imagen");
        this.setBorder(bordejpanel); 
        
        this.RH = new JLabel("R.H.");
        this.RH.setFont(new Font("Serif", Font.ITALIC, 15));
        this.RH.setToolTipText("Mano derecha");
        this.RH.setBounds(25,2*distInterna, tamxPanel,30);
        this.add(this.RH);
        
        this.LH = new JLabel("L.H.");
        this.LH.setFont(new Font("Serif", Font.ITALIC, 15));
        this.LH.setToolTipText("Mano izquierda");
        this.LH.setBounds(tamxPanel/2 + 25,2*distInterna, tamxPanel,30);
        this.add(this.LH);
        
        //--------------------------------mano Derecha + teclado
        this.cbTeclado = new JCheckBox("Teclado");
        this.cbRH1 = new JCheckBox("1");
        this.cbRH2 = new JCheckBox("2");
        this.cbRH3 = new JCheckBox("3");
        this.cbRH4 = new JCheckBox("4");
        this.cbRH5 = new JCheckBox("5");
        this.mRH = new JCheckBox("Muñeca");
        
        this.cbTeclado.setBounds(0,distInterna*2/3, 100, 30);
        this.cbRH1.setBounds(0, 3*distInterna, 40, 30);
        this.cbRH2.setBounds(0, 4*distInterna, 40, 30);
        this.cbRH3.setBounds(0, 5*distInterna, 40, 30);
        this.cbRH4.setBounds(0, 6*distInterna, 40, 30);
        this.cbRH5.setBounds(0, 7*distInterna, 40, 30);
        this.mRH.setBounds(0, 8*distInterna, 80, 30);
        
        this.cbTeclado.setSelected(true);
        this.cbRH1.setSelected(true);
        this.cbRH2.setSelected(true);
        this.cbRH3.setSelected(true);
        this.cbRH4.setSelected(true);
        this.cbRH5.setSelected(true);
        this.mRH.setSelected(true);
        
        this.add(this.cbTeclado);
        this.add(this.cbRH1);
        this.add(this.cbRH2);
        this.add(this.cbRH3);
        this.add(this.cbRH4);
        this.add(this.cbRH5);
        this.add(this.mRH);
        //----------------mano Izquierda
        this.cbLH1 = new JCheckBox("1");
        this.cbLH2 = new JCheckBox("2");
        this.cbLH3 = new JCheckBox("3");
        this.cbLH4 = new JCheckBox("4");
        this.cbLH5 = new JCheckBox("5");
        this.mLH = new JCheckBox("Muñeca");
        
        this.cbLH1.setBounds(tamxPanel/2, 3*distInterna, 40, 30);
        this.cbLH2.setBounds(tamxPanel/2, 4*distInterna, 40, 30);
        this.cbLH3.setBounds(tamxPanel/2, 5*distInterna, 40, 30);
        this.cbLH4.setBounds(tamxPanel/2, 6*distInterna, 40, 30);
        this.cbLH5.setBounds(tamxPanel/2, 7*distInterna, 40, 30);
        this.mLH.setBounds(tamxPanel/2, 8*distInterna, 80, 30);
        
        this.cbLH1.setSelected(true);
        this.cbLH2.setSelected(true);
        this.cbLH3.setSelected(true);
        this.cbLH4.setSelected(true);
        this.cbLH5.setSelected(true);
        this.mLH.setSelected(true);
       
        
          this.cbTeclado.addItemListener(manejador);
          this.cbRH1.addItemListener(manejador);
          this.cbRH2.addItemListener(manejador);
          this.cbRH3.addItemListener(manejador);
          this.cbRH4.addItemListener(manejador);
          this.cbRH5.addItemListener(manejador);
          this.mRH.addItemListener(manejador);
          this.cbLH1.addItemListener(manejador);
          this.cbLH2.addItemListener(manejador);
          this.cbLH3.addItemListener(manejador);
          this.cbLH4.addItemListener(manejador);
          this.cbLH5.addItemListener(manejador);
          this.mLH.addItemListener(manejador);
        
         
        this.add(this.cbLH1);
        this.add(this.cbLH2);
        this.add(this.cbLH3);
        this.add(this.cbLH4);
        this.add(this.cbLH5);
        this.add(this.mLH);
        
        this.setVisible(true);
       
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    private class ManejadorEventos implements ItemListener {

      @Override
      public void itemStateChanged(ItemEvent e) {
       Object source = e.getItemSelectable();
       getTopLevelAncestor().requestFocus();
       if(source == cbTeclado){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.Teclado = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.Teclado = true;
           }
       }
       else if (source == cbRH1){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.RH1 = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.RH1 = true;
           }
       }
       else if (source == cbRH2){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.RH2 = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.RH2 = true;
           }
       }
       else if (source == cbRH3){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.RH3 = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.RH3 = true;
           }
       }
       else if (source == cbRH4){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.RH4 = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.RH4 = true;
           }
       }
       else if (source == cbRH5){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.RH5= false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.RH5 = true;
           }
       }
       else if (source == mRH){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.mRH = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.mRH = true;
           }
       }
       else if (source == cbLH1){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.LH1 = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.LH1 = true;
           }
       }
       else if (source == cbLH2){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.LH2 = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.LH2 = true;
           }
       }
       else if (source == cbLH3){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.LH3 = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.LH3 = true;
           }
       }
       else if (source == cbLH4){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.LH4 = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.LH4 = true;
           }
       }
       else if (source == cbLH5){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.LH5= false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.LH5 = true;
           }
       }
       else if (source == mLH){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               controlador.mLH = false;
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               controlador.mLH = true;
           }
       }

      }
    
    }
}

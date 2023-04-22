/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author miguel
 */
public class PanelEstadistica extends JPanel{
    int distInterna = 18;
    int tamxPanel = 192;
    JButton btXvsTiempo,btYvsTiempo,btZvsTiempo,btRvsTiempo;
    ManejadorEventos manejadorEvento;
    ControladorPanelEstadistica controladorPanel;
    ControladorHilos controladorHilos;
  
    
    
    public PanelEstadistica(int xSize, int ySize, ControladorHilos controladorHilosIn){
        this.setLayout(null);
        this.setBounds(202, 100, tamxPanel, 210);
        this.controladorPanel = new ControladorPanelEstadistica();
        this.controladorHilos = controladorHilosIn;
        this.manejadorEvento = new ManejadorEventos(this.controladorPanel, this.controladorHilos);
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Gráficos Estadísticos");
        this.setBorder(bordejpanel); 
        
        this.btXvsTiempo = new JButton("X vs Tiempo");
        this.btXvsTiempo.setBounds(tamxPanel/2 - 65, 25,130,40);     
        this.btXvsTiempo.addActionListener(manejadorEvento);
        this.btXvsTiempo.setEnabled(false);
        this.add(this.btXvsTiempo);
        
        this.btYvsTiempo = new JButton("Y vs Tiempo");
        this.btYvsTiempo.setBounds(tamxPanel/2 - 65, 70,130,40);     
        this.btYvsTiempo.addActionListener(manejadorEvento);
        this.btYvsTiempo.setEnabled(false);
        this.add(this.btYvsTiempo);
        
        this.btZvsTiempo = new JButton("Z vs Tiempo");
        this.btZvsTiempo.setBounds(tamxPanel/2 - 65, 115,130,40);     
        this.btZvsTiempo.addActionListener(manejadorEvento);
        this.btZvsTiempo.setEnabled(false);
        this.add(this.btZvsTiempo);
        
        this.btRvsTiempo = new JButton("Velocidad Y vs Tiempo");
        this.btRvsTiempo.setBounds(tamxPanel/2 - 77, 160,154,40);     
        this.btRvsTiempo.addActionListener(manejadorEvento);
        this.btRvsTiempo.setEnabled(false);
        this.add(this.btRvsTiempo);
    
        this.setVisible(true);
    }
    
     private class ManejadorEventos implements ItemListener,ActionListener{
        ControladorPanelEstadistica controladorPanel;
        ControladorHilos controladorHilos;
       
        
        private ManejadorEventos(ControladorPanelEstadistica coordinadorIn, ControladorHilos controladorHilosIn) {
                this.controladorPanel = coordinadorIn;
                this.controladorHilos = controladorHilosIn;
        }

    @Override
    public void itemStateChanged(ItemEvent e) {
           }

    @Override
    public void actionPerformed(ActionEvent e) {
        getTopLevelAncestor().requestFocus();
        try{
            System.gc();
        if(e.getSource() == btXvsTiempo){      
             abrirFrameEstadistico(0);
         }    else  if(e.getSource() == btYvsTiempo){      
             abrirFrameEstadistico(1);
         }    else  if(e.getSource() == btZvsTiempo){      
             abrirFrameEstadistico(2);
         }    else  if(e.getSource() == btRvsTiempo){      
             abrirFrameEstadistico(3);
         }    
    }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

        private void abrirFrameEstadistico(int tipoDato) {
            if(tipoDato == 0){
                if(!this.controladorPanel.cuadroXactivo){         
                FrameEstadistica nuevoFrame = new FrameEstadistica(this.controladorPanel, this.controladorHilos, tipoDato);
                this.controladorPanel.cuadroXactivo = true;
                }
           }else if(tipoDato == 1){
                if(!this.controladorPanel.cuadroYactivo){         
                FrameEstadistica nuevoFrame = new FrameEstadistica(this.controladorPanel, this.controladorHilos, tipoDato);
                this.controladorPanel.cuadroYactivo = true;
                }
            }else if(tipoDato == 2){
                if(!this.controladorPanel.cuadroZactivo){         
                FrameEstadistica nuevoFrame = new FrameEstadistica(this.controladorPanel, this.controladorHilos, tipoDato);
                this.controladorPanel.cuadroZactivo = true;
                }
            }else if(tipoDato == 3){
                if(!this.controladorPanel.cuadroRactivo){         
                FrameEstadistica nuevoFrame = new FrameEstadistica(this.controladorPanel, this.controladorHilos, tipoDato);
                this.controladorPanel.cuadroRactivo = true;
                }
            }
           
             
        }
   }
     
}


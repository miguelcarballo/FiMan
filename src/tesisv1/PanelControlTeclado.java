package tesisv1;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author miguel
 */
public class PanelControlTeclado extends JPanel implements ItemListener{
    EscenarioVirtual escenario;
    int distInterna = 18;
    int tamxPanel = 200;
    JCheckBox cbTeclado;
    JLabel lbImgLeap;
    ImgLeap imgLeap;
    JButton btXUp, btXDown, btYUp, btYDown, btZUp, btZDown;
    ControlBotonesPCTeclado controlBotones;
    JLabel lblPosIniTeclado;
    
    
    public PanelControlTeclado(int xSize, int ySize, EscenarioVirtual escenarioIn){
        this.escenario = escenarioIn;
        this.controlBotones = new ControlBotonesPCTeclado();
        
         this.lblPosIniTeclado = new JLabel(getStringPosTeclado());
         this.lblPosIniTeclado.setFont(new Font("Serif", Font.PLAIN, 13));
         this.lblPosIniTeclado.setHorizontalAlignment(SwingConstants.CENTER);
         this.lblPosIniTeclado.setBounds(0,150,tamxPanel,335);
         this.add(this.lblPosIniTeclado);
        ManejadorEventos manejador = new ManejadorEventos(this.escenario,this.controlBotones, this.lblPosIniTeclado);
        int distBt = 30;
        int locBt= 203;
        
        this.setLayout(null);
        this.setBounds(xSize - tamxPanel -10, 200, tamxPanel, 335);
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Control de Teclado");
        this.setBorder(bordejpanel); 
        
         this.cbTeclado = new JCheckBox("Teclado");
         this.cbTeclado.setBounds(0,distInterna*2/3, 100, 30);
         this.cbTeclado.setSelected(true);
          
         this.cbTeclado.addItemListener(manejador);
         this.cbTeclado.addItemListener(this);
         this.add(this.cbTeclado);
         
         this.imgLeap = new ImgLeap();
         this.imgLeap.setLocation(tamxPanel/4, 40);
         this.add(this.imgLeap);
         
        
         //--------------------
         this.btXUp = new JButton("X+");
         this.btXDown = new JButton("X-");
         this.btYUp = new JButton("Y+");
         this.btYDown = new JButton("Y-");
         this.btZUp = new JButton("Z+");
         this.btZDown = new JButton("Z-");
         
         
          this.btXUp.setBounds(tamxPanel/2+distBt/2,locBt,distBt,distBt);
        this.btXDown.setBounds(tamxPanel/2-distBt-distBt/2,locBt,distBt,distBt);
        this.btYUp.setBounds(tamxPanel/2-distBt/2,locBt-distBt,distBt,distBt);
        this.btYDown.setBounds(tamxPanel/2-distBt/2,locBt+distBt,distBt,distBt);
        this.btZUp.setBounds(tamxPanel/2-2*distBt-distBt/2,locBt+2*distBt, (int) (distBt*1.5),(int) (distBt*1.5));
         this.btZDown.setBounds(tamxPanel/2+2*distBt-distBt/2,locBt-2*distBt,distBt*2/3,distBt*2/3);
                
         this.btXUp.addActionListener(manejador);
         this.btXDown.addActionListener(manejador);
         this.btYUp.addActionListener(manejador);
         this.btYDown.addActionListener(manejador);
         this.btZUp.addActionListener(manejador);
         this.btZDown.addActionListener(manejador);
         
         this.btXUp.addMouseListener(manejador);
         this.btXDown.addMouseListener(manejador);
         this.btYUp.addMouseListener(manejador);
         this.btYDown.addMouseListener(manejador);
         this.btZUp.addMouseListener(manejador);
         this.btZDown.addMouseListener(manejador);
         
         this.add(this.btXUp);
         this.add(this.btXDown);
         this.add(this.btYUp);
         this.add(this.btYDown);
         this.add(this.btZUp);
         this.add(this.btZDown);
         
        this.setVisible(true);
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getStringPosTeclado() {
         String textPos;
         if(escenario.Piano == null){
             textPos = "";
         }
         else{
             double[] posDouble  = new double[3];
             posDouble = escenario.Piano.PosicionInicial.clone(); 
             int[] posInt = new int[3];
             posInt[0] = (int)posDouble[0];
             posInt[1] = (int)posDouble[1];
             posInt[2] = (int)posDouble[2];
             textPos = "(" + posInt[0]+ ","+posInt[1]+ ","+posInt[2]+ ")";
         }
         
       return textPos;
    }

    
    private class ManejadorEventos implements ItemListener,ActionListener,MouseListener {
        EscenarioVirtual escenario;
        ControlBotonesPCTeclado controlBotones;
         HiloBotones hilo;
         JLabel lblPos;
        public ManejadorEventos(EscenarioVirtual escenarioIn, ControlBotonesPCTeclado controlBotonesIn, JLabel lblPosIn){
            this.escenario = escenarioIn;
            this.controlBotones = controlBotonesIn;
            this.lblPos = lblPosIn;     
        }
    @Override
    public void itemStateChanged(ItemEvent e) {
       Object source = e.getItemSelectable();
       getTopLevelAncestor().requestFocus();
       if(source == cbTeclado){
           if(e.getStateChange() == ItemEvent.DESELECTED){
               
              this.escenario.Piano = null;
              this.lblPos.setText(getStringPosTeclado());
           } else if (e.getStateChange() == ItemEvent.SELECTED){
               this.escenario.Piano = new Piano();
               this.lblPos.setText(getStringPosTeclado());
           }
       }
    }
        @Override
        public void actionPerformed(ActionEvent e) {
             
        }

        @Override
        public void mouseClicked(MouseEvent e) {
          
        }

        @Override
        public void mousePressed(MouseEvent e) {
             getTopLevelAncestor().requestFocus();
             if(e.getSource() == btXUp){                
                        this.controlBotones.direccion = 0;
                        this.controlBotones.cantMov = 1;
                   } else if(e.getSource() == btXDown){        
                       this.controlBotones.direccion = 0;
                        this.controlBotones.cantMov = -1;
                   }else if(e.getSource() == btYUp){   
                       this.controlBotones.direccion = 1;
                        this.controlBotones.cantMov = 1;
                   }else if(e.getSource() == btYDown){
                       this.controlBotones.direccion = 1;
                        this.controlBotones.cantMov = -1;
                   }else if(e.getSource() == btZUp){
                       this.controlBotones.direccion = 2;
                        this.controlBotones.cantMov = 1;
                   }else if(e.getSource() == btZDown){
                       this.controlBotones.direccion = 2;
                        this.controlBotones.cantMov = -1;
                   }
         this.controlBotones.presionado = true;
         this.hilo = new HiloBotones(this.controlBotones,this.escenario,this.controlBotones.cantMov,this.controlBotones.direccion, this.lblPos); 
        this.hilo.start();

        }

        @Override
        public void mouseReleased(MouseEvent e) {
             getTopLevelAncestor().requestFocus();
          this.controlBotones.presionado = false; 
          // System.out.println("mouseReleased " + this.controlBotones.presionado);
           //this.hilo.interrupt();        
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
 
    
    private class ImgLeap extends JPanel {
        public ImgLeap (){
        this.setLayout(null);
        this.setBounds(0, 0, 150, 150);
        this.setVisible(true);
        }
        
        @Override
        @SuppressWarnings("null")
        public void paint(Graphics grafico) {

            ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/Leap_Axes.png"));
            
             if(Img!=null) {
                grafico.drawImage(Img.getImage(), 0, 0, 100, 100, null);
                setOpaque(false);
                super.paintComponent(grafico);
            }
                
        }
    }
}


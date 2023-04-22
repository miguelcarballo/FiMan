/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author miguel
 */
public class PanelEscenario extends JPanel{
    
     ControladorRotacionZoom controladorRotacionZoom;
    ControladorImagen controladorImagen; 
      EscenarioVirtual escenario;
      //Graphics g;
      //Pgrafico buffer;
      JTextField text;
      //BufferedImage image;
      Font letras;
      
       //PROPORCIONES
    int anchopers, largopers, anchoort, largoort;
    int xort, yfo, yso, ylo, xopers, yopers;
       
    int xSize, ySize;
    PanelEscenario(int xSizein, int ySizein, EscenarioVirtual escIn, 
            ControladorImagen controladorImagenIn, ControladorRotacionZoom controladorRotacionIn) {
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.controladorRotacionZoom = controladorRotacionIn;
        this.controladorImagen = controladorImagenIn;
        this.xSize = xSizein;
        this.ySize = ySizein;
        this.escenario = escIn;
        anchopers = 700;
        largopers = 720;
	anchoort = 240;
	largoort = 200;
	xort = 760;
	yfo = 53;
	yso = yfo + anchoort + 30;
	ylo = yso + anchoort + 30;
        xopers = 30;
	yopers = ylo + anchoort - largopers -25;
        this.setLayout(null);
        this.setBounds(0, 0, this.xSize,this.ySize);
      //  buffer = new Pgrafico();
        //image = new BufferedImage(xSize,ySize, 1);
	//buffer.pasarg(image.createGraphics());
        
        this.setVisible(true);
    }
     
    public void borrar(Graphics g) {
        Pgrafico buffer = new Pgrafico();
        buffer.pasarg(g);
	buffer.g.clearRect(0, 0, xSize, ySize);
    }
    
    public void dibujar(Graphics g){
       // this.escenario.Piano.printTeclado();
        dibujarMarcos(g);
       dibujarTeclado(g);
       dibujarMunecas(g); 
        //this.escenario.mostrarManos();    
    }
    
    public void dibujarMarcos(Graphics g){
        //CUADROS
        Pgrafico buffer = new Pgrafico();
        buffer.pasarg(g);
        letras = new Font(Font.DIALOG, Font.BOLD, 27);
	buffer.g.setFont(letras);
	buffer.g.setColor(Color.green);

	buffer.g.setColor(Color.white);
	buffer.g.drawString("Vista Perspectiva", xopers, yopers-13);
	buffer.Box(xopers, yopers, xopers + anchopers, yopers + largopers, false);
	buffer.Box(xort, yfo, xort + anchoort, yfo + anchoort, false);

	buffer.Box(xort, ylo, xort + anchoort, ylo + anchoort, false);
	buffer.Box(xort, yso, xort + anchoort, yso + anchoort, false);
        
        letras = new Font(Font.DIALOG, Font.BOLD, 25);
        buffer.g.setFont(letras);
        buffer.g.drawString("Vista Frontal", xort, yfo - 5);
        buffer.g.drawString("Vista Lateral", xort, ylo -5);
        buffer.g.drawString("Vista Superior", xort, yso -5);
	letras = new Font(Font.DIALOG, Font.BOLD, 20);
       
      
    }
    
    public void dibujarMunecas(Graphics g){
        Pgrafico buffer = new Pgrafico();
        buffer.pasarg(g);
        double[] centro = Operacion.Centro();
        if(!this.escenario.Manos.isEmpty())
        {
        Enumeration<String> llaves = this.escenario.Manos.keys();
        
          while (llaves.hasMoreElements()) {               
              Mano mano = this.escenario.Manos.get(llaves.nextElement());
            if (mano != null){
             if (permisoDibujoMuneca(mano.Tipo)){
               buffer.definirVentana(xopers, yopers, xopers + anchopers, yopers + largopers);
             
               DibujoRectangulo dibujoMuneca = new DibujoRectangulo(mano.getDoublePosicionMuneca(), buffer);
               
                dibujoMuneca.rotarext(Matriz.ejes[1], this.controladorRotacionZoom.delta,centro[0], centro[1],centro[2]);                          
                dibujoMuneca.rotarext(Matriz.ejes[0], this.controladorRotacionZoom.dangulo,centro[0], centro[1],centro[2]);
                dibujoMuneca.moverz(this.controladorRotacionZoom.zoom);
                
                dibujoMuneca.setdimp(largopers, anchopers);
                dibujoMuneca.dibujarpers(xopers, yopers);
              
                buffer.definirVentana(xort, ylo, xort + anchoort, ylo + anchoort);
                dibujoMuneca.setdiml(largoort, anchoort);
                dibujoMuneca.dibujarlat(xort, ylo);
            
                buffer.definirVentana(xort, yso, xort + anchoort, yso + anchoort);
                dibujoMuneca.setdims(largoort, anchoort);	
                dibujoMuneca.dibujarsup(xort, yso);
            
            
                buffer.definirVentana(xort, yfo, xort + anchoort, yfo + anchoort);
                dibujoMuneca.setdimf(largoort, anchoort);
                dibujoMuneca.dibujarfront(xort, yfo);
             // dibujoMuneca.setdims(largoort, anchoort);           
             }
            this.dibujarHuesos(mano, g);
             //agregar ciclo para dibujar dedos.
             
           }
        }
        }
        
    }
    
    private void dibujarHuesos(Mano manoIn, Graphics g) {
        Pgrafico buffer = new Pgrafico();
        buffer.pasarg(g);
        boolean auxCorrector = false; 
       
        double[] centro = Operacion.Centro();
        if(!manoIn.Dedos.isEmpty())
        {
        Enumeration<String> llavesDedos = manoIn.Dedos.keys();
        
          while (llavesDedos.hasMoreElements()) {       
              Dedo dedoIn = manoIn.Dedos.get(llavesDedos.nextElement());
           
             if(!dedoIn.Huesos.isEmpty() && permisoDibujoDedo(dedoIn))
             {
                  boolean esPulgar = false;
                 Enumeration<String> llavesHuesos = dedoIn.Huesos.keys();
        
                  while (llavesHuesos.hasMoreElements()) {       
                    Hueso hueso = dedoIn.Huesos.get(llavesHuesos.nextElement());
                    if (hueso.Largo != 0){
                        buffer.definirVentana(xopers, yopers, xopers + anchopers, yopers + largopers);
                        if(manoIn.Tipo.equals("LH")){
                            auxCorrector = true;
                        }
                        if(dedoIn.Tipo == 1){
                            esPulgar = true;
                        }
                       // DibujoHueso dibujoHueso = new DibujoHueso(hueso.PosicionI,hueso.PosicionF,15,
                            //    hueso.vBaseX, hueso.vBaseY, hueso.vBaseZ, auxCorrector, buffer);

                        DibujoHuesoV2 dibujoHueso = new DibujoHuesoV2(hueso,esPulgar, auxCorrector,buffer);
                        dibujoHueso.rotarext(Matriz.ejes[1], this.controladorRotacionZoom.delta,centro[0], centro[1],centro[2]);                          
                        dibujoHueso.rotarext(Matriz.ejes[0], this.controladorRotacionZoom.dangulo,centro[0], centro[1],centro[2]);
                        dibujoHueso.moverz(this.controladorRotacionZoom.zoom);

                        buffer.definirVentana(xopers, yopers, xopers + anchopers, yopers + largopers);
                        dibujoHueso.setdimp(largopers, anchopers);	
                        dibujoHueso.dibujarpers(xopers, yopers);

                        buffer.definirVentana(xort, ylo, xort + anchoort, ylo + anchoort);
                        dibujoHueso.setdiml(largoort, anchoort);
                        dibujoHueso.dibujarlat(xort, ylo);

                        buffer.definirVentana(xort, yso, xort + anchoort, yso + anchoort);
                        dibujoHueso.setdims(largoort, anchoort);
                        dibujoHueso.dibujarsup(xort, yso);                

                        buffer.definirVentana(xort, yfo, xort + anchoort, yfo + anchoort);
                        dibujoHueso.setdimf(largoort, anchoort);
                        dibujoHueso.dibujarfront(xort, yfo);
                    }
                  }
              }
             
           }
        }
        
    }

    private void dibujarTeclado(Graphics g) {
         Pgrafico buffer = new Pgrafico();
        buffer.pasarg(g);
        if(this.escenario.Piano != null)
        {
            if(this.controladorImagen.Teclado == true){
             Tecla[] teclado = this.escenario.Piano.Teclado;
   
              for (int i = 0; i < teclado.length; i++){
                  if (teclado[i].nota.esBlanca){
               
                      dibujarTecla(teclado[i], g);   
                 }   
              }   
              for (int i = 0; i < teclado.length; i++){
                   if (!teclado[i].nota.esBlanca){
                
                      dibujarTecla(teclado[i], g);   
                  }   
              }
            }
        }
    }
    
    
    private boolean permisoDibujoDedo(Dedo dedoIn) {
        boolean permiso = false;
        String mano = dedoIn.Mano;
        switch (mano) {
            case "RH":
                switch (dedoIn.Tipo){
                    case 1:
                        if(controladorImagen.RH1)
                            permiso = true;  
                        break;
                    case 2:
                        if(controladorImagen.RH2)
                            permiso = true;  
                         break;
                    case 3:
                        if(controladorImagen.RH3)
                            permiso = true;  
                         break;
                    case 4:
                        if(controladorImagen.RH4)
                            permiso = true;  
                         break;
                    case 5:
                        if(controladorImagen.RH5)
                            permiso = true;  
                        break;
                }
                break;
            case "LH":
                 switch (dedoIn.Tipo){
                    case 1:
                        if(controladorImagen.LH1)
                            permiso = true; 
                         break;
                    case 2:
                        if(controladorImagen.LH2)
                            permiso = true; 
                         break;
                    case 3:
                        if(controladorImagen.LH3)
                            permiso = true; 
                         break;
                    case 4:
                        if(controladorImagen.LH4)
                            permiso = true;
                         break;
                    case 5:
                        if(controladorImagen.LH5)
                            permiso = true; 
                         break;
                }
                break;
        }      
        return permiso;
    }
    
    private boolean permisoDibujoMuneca(String tipoMano) {
        boolean permiso = false;
        switch (tipoMano) {
            case "RH":
                if(controladorImagen.mRH)
                        permiso = true;                 
                break;
            case "LH":
               if(controladorImagen.mLH)
                        permiso = true;                 
                break;
        }
        return permiso;
    
    }
    
     @Override
    public void update(Graphics g) {
      Image offscreen = createImage(xSize,ySize);
       Graphics offgc = offscreen.getGraphics();
       // offgc.clearRect(0, 0, xSize, ySize);
         offgc.setColor(getBackground());
        offgc.fillRect(0, 0, xSize, ySize);
         offgc.setColor(getForeground());
         paint(offgc);
         g.drawImage(offscreen, 0, 0, this);
    }
/*
     Graphics offgc;
    Image offscreen = null;
    Dimension d = size();

    // create the offscreen buffer and associated Graphics
    offscreen = createImage(d.width, d.height);
    offgc = offscreen.getGraphics();
    // clear the exposed area
    offgc.setColor(getBackground());
    offgc.fillRect(0, 0, d.width, d.height);
    offgc.setColor(getForeground());
    // do normal redraw
    paint(offgc);
    // transfer offscreen to window
    g.drawImage(offscreen, 0, 0, this);
    
    ----
     dbbuffer=createImage(getWidth(),getHeight());

            dbg=dbbuffer.getGraphics();
           // dbg.setColor(Color.black);
            paintComponent(dbg);
aaaaaaaaaa
            g.drawImage(dbbuffer,0,0,this);
    */
    

    @Override
    public void paint(Graphics g) {          
	//this.g = g;
        dibujar(g);
	//g.drawImage(image, 0, 0, this);
    }

    private void dibujarTecla(Tecla tecla, Graphics g) {
         Pgrafico buffer = new Pgrafico();
        buffer.pasarg(g);
          double[] centro = Operacion.Centro();
        buffer.definirVentana(xopers, yopers, xopers + anchopers, yopers + largopers);
             
             DibujoTecla dibujoTecla = new DibujoTecla(tecla.Posicion, tecla.nota.esBlanca, buffer);
               dibujoTecla.rotarext(Matriz.ejes[1], this.controladorRotacionZoom.delta,centro[0], centro[1],centro[2]);                          
               dibujoTecla.rotarext(Matriz.ejes[0], this.controladorRotacionZoom.dangulo,centro[0], centro[1],centro[2]);
               dibujoTecla.moverz(this.controladorRotacionZoom.zoom);
               
               
             dibujoTecla.setdimp(largopers, anchopers);
             dibujoTecla.dibujarpers(xopers, yopers);
              
            buffer.definirVentana(xort, ylo, xort + anchoort, ylo + anchoort);
            dibujoTecla.setdiml(largoort, anchoort);
            dibujoTecla.dibujarlat(xort, ylo);
            
            buffer.definirVentana(xort, yso, xort + anchoort, yso + anchoort);
            dibujoTecla.setdims(largoort, anchoort);	
            dibujoTecla.dibujarsup(xort, yso);
            
            
            buffer.definirVentana(xort, yfo, xort + anchoort, yfo + anchoort);
             dibujoTecla.setdimf(largoort, anchoort);
             dibujoTecla.dibujarfront(xort, yfo);
    }

}

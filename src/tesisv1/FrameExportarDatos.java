/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jxl.*;
import jxl.write.*;
import jxl.write.Boolean;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

/**
 *
 * @author miguel
 */
public class FrameExportarDatos extends JFrame implements ChangeListener { 

    boolean hayError;
    ControladorHilos controladorHilos;
    JPanel panelIntervaloCaptura; 
    JPanel panelComponentes; //seleccion
    JPanel panelDatos; //seleccion
    JCheckBox cbRH1, cbRH2,cbRH3, cbRH4,cbRH5, cbLH1, cbLH2,cbLH3, cbLH4,cbLH5, cbRHm, cbLHm;
    JCheckBox cbX,cbY, cbZ, cbV;
    JButton btExportarDatos;
    JButton auxBtCargar;
    JSlider slideEspacioCaptura;
    ManejadorEventos manejadorEventos;
    protected static final String EXTENSION = ".xls";
    
    int seleccionEspacio;
    JLabel lblValorSeleccion;
    int xSize = 600; 
    int ySize = 350;
    static final int ESP_MIN = Operacion.intervaloFijoGrabado;//cambiar con operacion
    static final int ESP_MAX = 1000;
    static final int ESP_INIT = Operacion.intervaloFijoGrabado;
 
    public FrameExportarDatos(ControladorHilos controladorIn, JButton cargarArchivoIn) {
        this.hayError = false;
      this.controladorHilos = controladorIn;
      this.manejadorEventos = new ManejadorEventos(this,this.controladorHilos);
      this.auxBtCargar = cargarArchivoIn;
      this.setLayout(null);
     
       this.setAlwaysOnTop(true);
   
      // this.set
       this.setSize(xSize, ySize);
       this.setLocationRelativeTo(null); 
       this.setTitle("Exportar Datos a Excel: " + controladorIn.nombreArchivoCargado); // falta nombre de Archivo
          this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
          this.setResizable(false);
        this.addWindowListener( new WindowAdapter() { 
                @Override
                public void windowClosing( WindowEvent evt ) { 
                        controladorHilos.frameExportarActivado = false;
                        auxBtCargar.setEnabled(true);
                           evt.getWindow().dispose();                               
                } 
            } );
       crearPanelIntervaloCaptura();
       crearPanelComponentes();
       crearPanelDatos();
     //207
       this.btExportarDatos= new JButton("Exportar a Excel");   
       this.btExportarDatos.setBounds(xSize/4*3-70, ySize - 120,130,60);
       this.add(this.btExportarDatos);
         
       this.btExportarDatos.addActionListener(this.manejadorEventos);
       this.setVisible(true);
      
    }

    private void crearPanelIntervaloCaptura() {
        this.panelIntervaloCaptura = new JPanel();
        this.panelIntervaloCaptura.setBounds(0, 0, this.xSize, 130);
          this.panelIntervaloCaptura.setLayout(new BoxLayout(  this.panelIntervaloCaptura, BoxLayout.Y_AXIS));
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Captura de Datos");
        this.panelIntervaloCaptura.setBorder(bordejpanel); 
        
        JLabel sliderLabel = new JLabel("Milisegundos entre capturas");
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        this.slideEspacioCaptura = new JSlider(JSlider.HORIZONTAL,ESP_MIN, ESP_MAX, ESP_INIT);
        this.slideEspacioCaptura.setMajorTickSpacing(100);   
        this.seleccionEspacio = ESP_MIN;

        this.slideEspacioCaptura.setMinorTickSpacing(Operacion.intervaloFijoGrabado);
        this.slideEspacioCaptura.setPaintTicks(true);
        this.slideEspacioCaptura.setPaintLabels(true);
        this.slideEspacioCaptura .setSize(500, 340);
        this.slideEspacioCaptura.addChangeListener(this);
        Font font = new Font("Serif", Font.ITALIC, 11);
        this.slideEspacioCaptura.setFont(font);
        this.slideEspacioCaptura.setSnapToTicks(true);
        
        this.panelIntervaloCaptura.add(sliderLabel);
        this.panelIntervaloCaptura.add(this.slideEspacioCaptura );
        this.panelIntervaloCaptura.setVisible(true);
        
        this.lblValorSeleccion = new JLabel(""+this.slideEspacioCaptura.getValue() + " milisegundos");
         this.lblValorSeleccion.setFont(new Font("Serif", Font.BOLD, 15));
        this.lblValorSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panelIntervaloCaptura.add(lblValorSeleccion);
        this.add(this.panelIntervaloCaptura);
    }

    
    private void crearPanelComponentes(){
         int distInternaY = 22;
         int distInternaX = 40;
        this.panelComponentes = new JPanel();
        this.panelComponentes.setLayout(null);
        this.panelComponentes.setBounds(0, 127, xSize/2, 200);
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Componentes");
        this.panelComponentes.setBorder(bordejpanel); 
        
          //--------------------------------mano Derecha + teclado
         JLabel RH = new JLabel("R.H.");
        RH.setFont(new Font("Serif", Font.ITALIC, 15));
        RH.setToolTipText("Mano derecha");
        RH.setBounds(2*distInternaX-10, distInternaY, 80, 30);
        this.panelComponentes.add(RH);
        
        JLabel LH = new JLabel("L.H.");
        LH.setFont(new Font("Serif", Font.ITALIC, 15));
        LH.setToolTipText("Mano izquierda");
        LH.setBounds(5*distInternaX-10,distInternaY, 80, 30);
        this.panelComponentes.add(LH);
        
        this.cbRH1 = new JCheckBox("Dedo 1");
        this.cbRH2 = new JCheckBox("Dedo 2");
        this.cbRH3 = new JCheckBox("Dedo 3");
        this.cbRH4 = new JCheckBox("Dedo 4");
        this.cbRH5 = new JCheckBox("Dedo 5");
        this.cbRHm = new JCheckBox("Muñeca");
        
        this.cbRH1.setBounds(distInternaX, 2*distInternaY, 80, 30);
        this.cbRH2.setBounds(distInternaX, 3*distInternaY, 80, 30);
        this.cbRH3.setBounds(distInternaX, 4*distInternaY, 80, 30);
        this.cbRH4.setBounds(distInternaX, 5*distInternaY, 80, 30);
        this.cbRH5.setBounds(distInternaX, 6*distInternaY, 80, 30);
        this.cbRHm.setBounds(distInternaX, 7*distInternaY, 80, 30);
        
        this.cbRH1.setSelected(true);
        this.cbRH2.setSelected(true);
        this.cbRH3.setSelected(true);
        this.cbRH4.setSelected(true);
        this.cbRH5.setSelected(true);
        this.cbRHm.setSelected(true);
        
        this.panelComponentes.add(this.cbRH1);
        this.panelComponentes.add(this.cbRH2);
        this.panelComponentes.add(this.cbRH3);
        this.panelComponentes.add(this.cbRH4);
        this.panelComponentes.add(this.cbRH5);
        this.panelComponentes.add(this.cbRHm);
       //----------
        
        this.cbLH1 = new JCheckBox("Dedo 1");
        this.cbLH2 = new JCheckBox("Dedo 2");
        this.cbLH3 = new JCheckBox("Dedo 3");
        this.cbLH4 = new JCheckBox("Dedo 4");
        this.cbLH5 = new JCheckBox("Dedo 5");
        this.cbLHm = new JCheckBox("Muñeca");
        
        this.cbLH1.setBounds(4*distInternaX, 2*distInternaY, 80, 30);
        this.cbLH2.setBounds(4*distInternaX, 3*distInternaY, 80, 30);
        this.cbLH3.setBounds(4*distInternaX, 4*distInternaY, 80, 30);
        this.cbLH4.setBounds(4*distInternaX, 5*distInternaY, 80, 30);
        this.cbLH5.setBounds(4*distInternaX, 6*distInternaY, 80, 30);
        this.cbLHm.setBounds(4*distInternaX, 7*distInternaY, 80, 30);
        
        this.cbLH1.setSelected(true);
        this.cbLH2.setSelected(true);
        this.cbLH3.setSelected(true);
        this.cbLH4.setSelected(true);
        this.cbLH5.setSelected(true);
        this.cbLHm.setSelected(true);
        
        this.panelComponentes.add(this.cbLH1);
        this.panelComponentes.add(this.cbLH2);
        this.panelComponentes.add(this.cbLH3);
        this.panelComponentes.add(this.cbLH4);
        this.panelComponentes.add(this.cbLH5);
        this.panelComponentes.add(this.cbLHm);
        
        this.add(this.panelComponentes);
    }
    
    
    private void crearPanelDatos(){
        int distInternaY = 15;
         int distInternaX =50;
        this.panelDatos = new JPanel();
        this.panelDatos.setLayout(null);
        this.panelDatos.setBounds(xSize/2, 127, xSize/2, 80);
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Datos");
        this.panelDatos.setBorder(bordejpanel); 
        
        this.cbX = new JCheckBox("X");
        this.cbY = new JCheckBox("Y");
        this.cbZ = new JCheckBox("Z");
        this.cbV = new JCheckBox("Velocidad Y");
        
         this.cbX.setBounds(20, 2*distInternaY, 50, 30);
        this.cbY.setBounds(20+distInternaX, 2*distInternaY,50, 30);
        this.cbZ.setBounds(20+2*distInternaX, 2*distInternaY, 50, 30);
        this.cbV.setBounds(20+3*distInternaX, 2*distInternaY, 120, 30);
        
        this.cbX.setSelected(true);
        this.cbY.setSelected(true);
        this.cbZ.setSelected(true);
        this.cbV.setSelected(true);
        
        this.panelDatos.add(this.cbX);
        this.panelDatos.add(this.cbY);
        this.panelDatos.add(this.cbZ);
        this.panelDatos.add(this.cbV);
        
        this.add(this.panelDatos);
    }
    
     @Override
    public void stateChanged(ChangeEvent e) {
       if(e.getSource() == slideEspacioCaptura){
           this.seleccionEspacio = this.slideEspacioCaptura.getValue();
           this.lblValorSeleccion.setText(this.seleccionEspacio+ " milisegundos");
       }
    }
    
     private class ManejadorEventos implements ItemListener,ActionListener{
         JFrame frameExportar;
         ControladorHilos controladorHilos;
         int contadorLinea = 1;
         
         ManejadorEventos(JFrame frameExportarIn,ControladorHilos controladorIn){
             this.frameExportar = frameExportarIn;
             this.controladorHilos = controladorIn;
         }

       private void exportarDatosAExcel(){

         boolean aceptado = false;
        while(!aceptado){
            FileDialog winGuardar=new FileDialog(this.frameExportar,"Exportar datos a Excel ",FileDialog.SAVE);
            winGuardar.setAlwaysOnTop(true);
            FilenameFilter file = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if(name.endsWith(EXTENSION)){
                        //System.out.println(name);
                        return true;
                    }else{
                        return false;
                    }    
                }
            };
             winGuardar.setFilenameFilter(file);
             winGuardar.setFile("DatosExportados"+EXTENSION);
             winGuardar.setVisible(true);
              
             if(winGuardar.getDirectory()== null || winGuardar.getFile() == null){
                 aceptado = true;
             }else 
             {             
                 boolean extensionAceptada = evaluarExtension(winGuardar.getFile());
                 if(extensionAceptada)
                 {
                     String direccionSeleccionada = winGuardar.getDirectory()+winGuardar.getFile();
                     direccionSeleccionada = agregarExtension(direccionSeleccionada);
                     String auxNombrefile = agregarExtension(winGuardar.getFile());
                     boolean existeOtro = EvaluarExistencia(direccionSeleccionada);
                     if(existeOtro){    
                         boolean confirmacion = confirmarSobreescritura(winGuardar, auxNombrefile);
                         if(confirmacion){ 
                             try {
                             escribirArchivoExcel(direccionSeleccionada);
                             //Aceptar sobreescritura
                             ///SobreescribirArchivo(direccionSeleccionada);
                             if (!hayError){
                                 mmArchivoEscrito(auxNombrefile);
                             }
                             aceptado = true;
                             } catch (Exception ex) {
                                 System.out.println("Error exportando datos - ExportarDatos " + ex);
                             }
                         } 
                     } else{
                         try {
                             escribirArchivoExcel(direccionSeleccionada);
                             //SobreescribirArchivo(direccionSeleccionada);  //escritura normal
                             if(!hayError){
                                  mmArchivoEscrito(auxNombrefile);
                             }                          
                             aceptado = true;
                         } catch (Exception ex) {
                              System.out.println("Error exportando datos - ExportarDatos " + ex);
                         }
                     }
                 } else{   //no se acepta la extension
                        mmExtensionRechazada();
                }
             }
        }
        
    }
    
    private void escribirArchivoExcel(String direccionCompleta){
        extraerDatosArchivoCBR(this.controladorHilos.direccionArchivoCargado, direccionCompleta);
    }
    
      private void extraerDatosArchivoCBR(String direccionArchivoCargado, String direccionSeleccionada) {
          try {
            File exlFile = new File(direccionSeleccionada);
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);
            WritableSheet writableSheet = writableWorkbook.createSheet("Sheet1", 0);
              
            EscenarioGrabado object;
            ObjectInputStream objectIn;
            File selectedFile = new File(direccionArchivoCargado);
            
            objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(selectedFile.getAbsolutePath())));
            EscribirEtiquetas(writableSheet);
            while (objectIn!= null) {    
                object = (EscenarioGrabado)objectIn.readObject();            
                if(object.numero == -1){    
                    break;
                }
                //controlar seleccion del slide
                else if(object.numero == 0){
                     exportarDatosDeObjeto(object,writableSheet);
                }
                else if((Operacion.intervaloFijoGrabado*(object.numero))%seleccionEspacio == 0){
                       exportarDatosDeObjeto(object,writableSheet);
                }          
            }
            //this.escenarioVirtual.Manos = null;
            objectIn.close();   
            
            writableWorkbook.write();
            writableWorkbook.close(); 
        }catch(IOException | ClassNotFoundException | WriteException e){
            System.out.println("Error ExportarDatosExcel " + e);
            hayError = true;
            this.mmErrorLeyendoArchivo();
        }    
    }
      
      private void exportarDatosDeObjeto(EscenarioGrabado object, WritableSheet writableSheet) {
         if(!object.Manos.isEmpty())
        {
        Enumeration<String> llaves = object.Manos.keys();
        
          while (llaves.hasMoreElements()) {               
              Mano mano = object.Manos.get(llaves.nextElement());
               if(!mano.Dedos.isEmpty())
                {
                Enumeration<String> llavesDedos = mano.Dedos.keys();

                  while (llavesDedos.hasMoreElements()) {       
                      Dedo dedoIn = mano.Dedos.get(llavesDedos.nextElement());
                      
                      //evaluar si ese componente debe registrarse
                      if(dedoIn.Tipo == 1){
                          if(mano.Tipo.equals("RH")&&cbRH1.isSelected()){
                             //grabar datos en la fila
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }else if(mano.Tipo.equals("LH")&&cbLH1.isSelected()) {
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }    
                      }      
                      else if(dedoIn.Tipo == 2){
                          if(mano.Tipo.equals("RH")&&cbRH2.isSelected()){
                             //grabar datos en la fila
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }else if(mano.Tipo.equals("LH")&&cbLH2.isSelected()) {
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }    
                      }    
                     else if(dedoIn.Tipo == 3){
                          if(mano.Tipo.equals("RH")&&cbRH3.isSelected()){
                             //grabar datos en la fila
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }else if(mano.Tipo.equals("LH")&&cbLH3.isSelected()) {
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }    
                      }    
                     else if(dedoIn.Tipo == 4){
                          if(mano.Tipo.equals("RH")&&cbRH4.isSelected()){
                             //grabar datos en la fila
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }else if(mano.Tipo.equals("LH")&&cbLH4.isSelected()) {
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }    
                      }    
                     else if(dedoIn.Tipo == 5){
                          if(mano.Tipo.equals("RH")&&cbRH5.isSelected()){
                             //grabar datos en la fila
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }else if(mano.Tipo.equals("LH")&&cbLH5.isSelected()) {
                              EscribirDedoFila(dedoIn,object.numero,writableSheet);
                          }    
                      }        
                  }
                }
               
               if(mano.Tipo.equals("RH")&&cbRHm.isSelected()){ //grabar munecas
                    EscribirMunecaFila(mano,object.numero, writableSheet);
               }else if(mano.Tipo.equals("LH")&&cbLHm.isSelected()){
                   EscribirMunecaFila(mano,object.numero, writableSheet);
               }
          }
        }                  
    }
    private void EscribirEtiquetas(WritableSheet writableSheet){
         int contColumna = 5;
         
             try {
                  CellView cv0 = writableSheet.getColumnView(0);
                  CellView cv2 = writableSheet.getColumnView(2);
                  CellView cv3 = writableSheet.getColumnView(3);
                  CellView cv4 = writableSheet.getColumnView(4);
                  CellView cv5 = writableSheet.getColumnView(5);
                  
                //  cv.setAutosize(true);
                  cv0.setSize(7* 256 + 100); /* Every character is 256 units wide, so scale it. */
                  cv2.setSize(11* 256 + 100); 
                  cv3.setSize(5* 256 + 100); 
                  cv4.setSize(9* 256 + 100); 
                  cv5.setSize(15* 256 + 100); 
                  writableSheet.setColumnView(0, cv0);
                  writableSheet.setColumnView(1, cv0);
                  writableSheet.setColumnView(2, cv2);
                  writableSheet.setColumnView(3, cv3);
                  writableSheet.setColumnView(4, cv4);
                  writableSheet.setColumnView(5, cv5);
                  writableSheet.setColumnView(6, cv5);
                  writableSheet.setColumnView(7, cv5);
                  writableSheet.setColumnView(8, cv5);
            //---
             WritableFont cellFont = new WritableFont(WritableFont.TIMES, 10);
             cellFont.setColour(Colour.BLACK);   
             WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
             cellFormat.setAlignment(Alignment.CENTRE);
             //cellFormat.setBorder(jxl.write.Border.LEFT, BorderLineStyle.THICK);
             cellFormat.setBackground(Colour.GRAY_25);
                     
                Label minuto = new Label(0, 0, "Minuto",cellFormat);
                writableSheet.addCell(minuto);
                Label segundo = new Label(1, 0, "Segundo",cellFormat);
                writableSheet.addCell(segundo);
                Label mili = new Label(2, 0, "Milisegundo",cellFormat);
                writableSheet.addCell(mili);
                Label mano = new Label(3, 0, "Mano",cellFormat);
                writableSheet.addCell(mano);
                Label componente = new Label(4, 0, "Componente",cellFormat);
                writableSheet.addCell(componente);
                if(cbX.isSelected()){
                    Label x = new Label(contColumna, 0, "Posicion X [mm]",cellFormat);
                    writableSheet.addCell(x);
                    contColumna++;
                }
                if(cbY.isSelected()){
                    Label y = new Label(contColumna, 0, "Posicion Y [mm]",cellFormat);
                    writableSheet.addCell(y);
                    contColumna++;
                }
                if(cbZ.isSelected()){
                    Label z = new Label(contColumna, 0, "Posicion Z [mm]",cellFormat);
                    writableSheet.addCell(z);
                    contColumna++;
                }
                if(cbV.isSelected()){
                    Label v = new Label(contColumna, 0, "Velocidad Y [mm/s]",cellFormat);
                    writableSheet.addCell(v);
                    contColumna++;
                }                          
             } catch (WriteException ex) {
                     System.out.println("Error escribiendo una Etiqueta Excel" + ex);
             }
    }  
      
    private void EscribirDedoFila(Dedo dedoIn, int numeroFrame, WritableSheet writableSheet) {
        int contColumna = 5;
        int mili = Operacion.getMilisegundo(numeroFrame);
        int sec = Operacion.getSegundo(numeroFrame);
        int min = Operacion.getMinuto(numeroFrame);
       
        try {
             Number minuto = new Number(0, this.contadorLinea, min);
             writableSheet.addCell(minuto);
             Number segundo = new Number(1, this.contadorLinea, sec);
             writableSheet.addCell(segundo);
             Number milisegundo = new Number(2, this.contadorLinea, mili);
             writableSheet.addCell(milisegundo);
             Label mano = new Label(3, this.contadorLinea, dedoIn.Mano);
             writableSheet.addCell(mano);
             Label dedo = new Label(4, this.contadorLinea, "Dedo " + dedoIn.Tipo);
             writableSheet.addCell(dedo);
             if(cbX.isSelected()){
                 
                 Number x = new Number(contColumna, this.contadorLinea, (int)Math.round(dedoIn.Punta[0]));
                 writableSheet.addCell(x);
                 contColumna++;
             }
             if(cbY.isSelected()){
                 Number y = new Number(contColumna, this.contadorLinea, (int)Math.round(dedoIn.Punta[1]));
                 writableSheet.addCell(y);
                 contColumna++;
             }
             if(cbZ.isSelected()){
                 Number z= new Number(contColumna, this.contadorLinea, (int)Math.round(dedoIn.Punta[2]));
                 writableSheet.addCell(z);
                 contColumna++;
             }
             if(cbV.isSelected()){
                 Number v = new Number(contColumna, this.contadorLinea, (int)Math.round(dedoIn.Velocidad[1]));
                 writableSheet.addCell(v);
                 contColumna++;
             }            
       } catch (WriteException ex) {
          System.out.println("Error escribiendo una fila Excel" + ex);
       }    
        this.contadorLinea++;
    }

    private void EscribirMunecaFila(Mano manoIn, int numeroFrame, WritableSheet writableSheet) {
        int contColumna = 5;
        int mili = Operacion.getMilisegundo(numeroFrame);
        int sec = Operacion.getSegundo(numeroFrame);
        int min = Operacion.getMinuto(numeroFrame);
       
        try {
             Number minuto = new Number(0, this.contadorLinea, min);
             writableSheet.addCell(minuto);
             Number segundo = new Number(1, this.contadorLinea, sec);
             writableSheet.addCell(segundo);
             Number milisegundo = new Number(2, this.contadorLinea, mili);
             writableSheet.addCell(milisegundo);
             Label mano = new Label(3, this.contadorLinea, manoIn.Tipo);
             writableSheet.addCell(mano);
             Label muneca = new Label(4, this.contadorLinea, "Muneca");
             writableSheet.addCell(muneca);
             if(cbX.isSelected()){
                 Number x = new Number(contColumna, this.contadorLinea, (int)Math.round(manoIn.PosicionMuneca[0]));
                 writableSheet.addCell(x);
                 contColumna++;
             }
             if(cbY.isSelected()){
                 Number y = new Number(contColumna, this.contadorLinea, (int)Math.round(manoIn.PosicionMuneca[1]));
                 writableSheet.addCell(y);
               
                 contColumna++;
             }
             if(cbZ.isSelected()){
                 Number z= new Number(contColumna, this.contadorLinea, (int)Math.round(manoIn.PosicionMuneca[2]));
                 writableSheet.addCell(z);
                 contColumna++;
             }
             if(cbV.isSelected()){
                 Number v = new Number(contColumna, this.contadorLinea, (int)Math.round(manoIn.VelocidadMuneca[1]));
                 writableSheet.addCell(v);
                 contColumna++;
             }            
       } catch (WriteException ex) {
          System.out.println("Error escribiendo una fila Excel" + ex);
       }    
        this.contadorLinea++;
    }
    
    private boolean EvaluarExistencia(String direccionSeleccionada) {
        File fileEv = new File(direccionSeleccionada);
        return fileEv.exists();
    }

    private boolean evaluarExtension(String fileName) {
        if(fileName.endsWith(EXTENSION) || fileName.endsWith(EXTENSION.toUpperCase())){
            return true;
        }else{
             int index = fileName.lastIndexOf('.');
             return index == -1;
        }
    }

    private boolean confirmarSobreescritura(FileDialog winGuardar, String file) {
                Object[] options = {"Si","No"};
                ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/pregunta.png"));
                int result = JOptionPane.showOptionDialog(this.frameExportar,
                "¿Desea reemplazar "+ file + "?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                Img,//do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title
                
                switch(result){
                case 0:
                return true;
                case -1:
                return false;
                case 1:
                return false;
               
                }
        return false;
    }

    private String agregarExtension(String direccionSeleccionada) {
       if(direccionSeleccionada.endsWith(EXTENSION)||direccionSeleccionada.endsWith(EXTENSION.toUpperCase())){
            return direccionSeleccionada;
        } else{
            return direccionSeleccionada + EXTENSION;
        }
    }

    private void mmArchivoEscrito(String nombreFile) {   
      ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/bien.png"));
      JOptionPane.showMessageDialog(this.frameExportar,"Datos exportados a archivo Excel " + nombreFile, 
                "", JOptionPane.INFORMATION_MESSAGE, Img);
      this.controladorHilos.frameExportarActivado = false;
      auxBtCargar.setEnabled(true);
      this.frameExportar.dispose();
    }

    private void mmExtensionRechazada() {
          ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/error.png"));
        JOptionPane.showMessageDialog(this.frameExportar,"La extension de archivo debe ser " + EXTENSION, "", JOptionPane.INFORMATION_MESSAGE, Img);
    }

    private void mmErrorLeyendoArchivo() {
        ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/error.png"));
        JOptionPane.showMessageDialog(this.frameExportar,"ERROR leyendo Archivo, tipo de datos incompatible", "", JOptionPane.OK_OPTION, Img); 
        this.controladorHilos.frameExportarActivado = false;
        auxBtCargar.setEnabled(true);
        this.frameExportar.dispose();
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {    
       if(e.getSource() == btExportarDatos){ 
             exportarDatosAExcel();             
         }   
    }   
  }   
}

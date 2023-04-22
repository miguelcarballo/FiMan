/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Thread.sleep;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ExtensionFileFilter;

/**
 *
 * @author miguel
 */
public class FrameEstadistica extends JFrame implements ItemListener{

    private String title = "";
    private ChartPanel chartPanel;
    JFreeChart chart;
    int tipoDato;
    //ManejadorEventos manejador;
    JCheckBox cbSelect, cbRH1,cbRH2,cbRH3,cbRH4,cbRH5,cbRHmuneca, cbLH1,cbLH2,cbLH3,cbLH4,cbLH5,cbLHmuneca;
    ControladorPanelEstadistica controladorPanel;
    FormularioEstadistico formulario;
    ControladorHilos controladorHilos;
    TimeSeries serieRH1, serieRH2, serieRH3, serieRH4,serieRH5,serieRHmuneca
            ,serieLH1, serieLH2, serieLH3, serieLH4,serieLH5,serieLHmuneca;
   
    int dia, mes, annio;
    public FrameEstadistica( ControladorPanelEstadistica controladorPanelIn, ControladorHilos controladorHilosIn, int tipoDatoIn) {
        this.controladorPanel = controladorPanelIn;
        this.controladorHilos = controladorHilosIn;
        this.setSize(700, 400);
        
        Calendar fecha = new GregorianCalendar();
        this.annio= fecha.get(Calendar.YEAR); 
       // System.out.println("anio " + this.annio);
        this.mes = 1; //Desastre del Calendar
        // System.out.println("mes " + this.mes);
        this.dia = 1;
        // System.out.println("dia " + this.dia);
        
        this.tipoDato = tipoDatoIn;
        this.serieRH1 = new TimeSeries("RH 1");
        this.serieRH2 = new TimeSeries("RH 2");
        this.serieRH3 = new TimeSeries("RH 3");
        this.serieRH4 = new TimeSeries("RH 4");
        this.serieRH5 = new TimeSeries("RH 5");
        this.serieRHmuneca = new TimeSeries("RH muneca");
        this.serieLH1 = new TimeSeries("LH 1");
        this.serieLH2 = new TimeSeries("LH 2");
        this.serieLH3 = new TimeSeries("LH 3");
        this.serieLH4 = new TimeSeries("LH 4");
        this.serieLH5 = new TimeSeries("LH 5");
        this.serieLHmuneca = new TimeSeries("LH muneca");
        this.chartPanel = createChart();
       //  this.manejador = new ManejadorEventos();
         this.title = "Cuadro Estadistico "+convIntTipo(this.tipoDato)+ " vs Tiempo - " + this.controladorHilos.nombreArchivoCargado;
        this.setTitle(title);
    
       // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(0, 5));
        this.add(chartPanel, BorderLayout.CENTER);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setHorizontalAxisTrace(true);
        chartPanel.setVerticalAxisTrace(true);
       

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelderRH = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelderRH.setLayout(new BoxLayout(panelderRH, BoxLayout.Y_AXIS)); 
      
        JLabel lblPanelRH =  new JLabel("RH");
        JLabel lblPanelLH =  new JLabel("LH");
        
        panelderRH.add(lblPanelRH,BorderLayout.CENTER);
        panelderRH.add(crearCbRH1());
        panelderRH.add(crearCbRH2());
        panelderRH.add(crearCbRH3());
        panelderRH.add(crearCbRH4());
        panelderRH.add(crearCbRH5());
        panelderRH.add(crearCbRHmuneca());
        
        panelderRH.add(new JLabel(" "));
        panelderRH.add(lblPanelLH,BorderLayout.CENTER);
        panelderRH.add(crearCbLH1());
        panelderRH.add(crearCbLH2());
        panelderRH.add(crearCbLH3());
        panelderRH.add(crearCbLH4());
        panelderRH.add(crearCbLH5());
        panelderRH.add(crearCbLHmuneca());
        
        panel.add(crearbtGuardarImagen());
        panel.add(createZoom());
        this.add(panel, BorderLayout.SOUTH);
        this.add(panelderRH, BorderLayout.EAST);

      
        this.setLocationRelativeTo(null);
        
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener( new WindowAdapter() { 
                @Override
                public void windowClosing( WindowEvent evt ) {
                    if(tipoDato == 0){
                        controladorPanel.cuadroXactivo = false;
                           evt.getWindow().dispose();
                    } else if(tipoDato == 1){
                          controladorPanel.cuadroYactivo = false;
                           evt.getWindow().dispose();
                    }else if(tipoDato == 2){
                          controladorPanel.cuadroZactivo = false;
                           evt.getWindow().dispose();
                    }else if(tipoDato == 3){
                          controladorPanel.cuadroRactivo = false;
                           evt.getWindow().dispose();
                    }             
                } 
            } );        
    }


    private JButton createZoom() {
        final JButton auto = new JButton(new AbstractAction("Auto Zoom") {

            @Override
            public void actionPerformed(ActionEvent e) {
                chartPanel.restoreAutoBounds();
            }
        });
        return auto;
    }
    
    private JButton crearbtGuardarImagen() {
        final JButton auto = new JButton(new AbstractAction("Guardar Imagen") {

            @Override
            public void actionPerformed(ActionEvent e) {
             guardarJPG();
            }
        });
        return auto;
    }

    private ChartPanel createChart() {
       String etiquetaVertical = "Valor [mm]";
       if(this.tipoDato == 3)
       {
           etiquetaVertical = "Valor [mm/s]";
       }
        XYDataset roiData = createDataset();
        this.chart = ChartFactory.createTimeSeriesChart(
            title, "Tiempo [h:min:s.ms]", etiquetaVertical, roiData, true, true, false);
        XYPlot plot = this.chart.getXYPlot();
       XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
     
        //NumberFormat currency = NumberFormat.getCurrencyInstance();
        //currency.setMaximumFractionDigits(0);
     // NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
       // rangeAxis.setNumberFormatOverride(currency);
        return new ChartPanel(this.chart);
    }

    private XYDataset createDataset() {
         TimeSeriesCollection tsc= extraerDatosArchivo(this.controladorHilos.direccionArchivoCargado, this.tipoDato);

           tsc.addSeries(this.serieRH1);
           tsc.addSeries(this.serieRH2);
           tsc.addSeries(this.serieRH3);
           tsc.addSeries(this.serieRH4);
           tsc.addSeries(this.serieRH5);
           tsc.addSeries(this.serieRHmuneca);
           tsc.addSeries(this.serieLH1);
           tsc.addSeries(this.serieLH2);
           tsc.addSeries(this.serieLH3);
           tsc.addSeries(this.serieLH4);
           tsc.addSeries(this.serieLH5);
           tsc.addSeries(this.serieLHmuneca);
       
        return tsc;
    }
    
    private JCheckBox crearCbRH1(){
        this.cbRH1 = new JCheckBox("1");
          this.cbRH1.setSelected(true);
          this.cbRH1.addItemListener(this);
          return cbRH1;
    }
    
    private JCheckBox crearCbRH2(){
        this.cbRH2 = new JCheckBox("2");
          this.cbRH2.setSelected(true);
          this.cbRH2.addItemListener(this);
          return cbRH2;
    }
    
    private JCheckBox crearCbRH3(){
        this.cbRH3 = new JCheckBox("3");
          this.cbRH3.setSelected(true);
          this.cbRH3.addItemListener(this);
          return cbRH3;
    }
    
    private JCheckBox crearCbRH4(){
        this.cbRH4 = new JCheckBox("4");
          this.cbRH4.setSelected(true);
          this.cbRH4.addItemListener(this);
          return cbRH4;
    }
    
    private JCheckBox crearCbRH5(){
        this.cbRH5 = new JCheckBox("5");
          this.cbRH5.setSelected(true);
          this.cbRH5.addItemListener(this);
          return cbRH5;
    }
    
    private JCheckBox crearCbRHmuneca(){
        this.cbRHmuneca = new JCheckBox("Muñeca");
          this.cbRHmuneca.setSelected(true);
          this.cbRHmuneca.addItemListener(this);
          return cbRHmuneca;
    }
    
    private JCheckBox crearCbLH1(){
        this.cbLH1 = new JCheckBox("1");
          this.cbLH1.setSelected(true);
          this.cbLH1.addItemListener(this);
          return cbLH1;
    }
    
    private JCheckBox crearCbLH2(){
        this.cbLH2 = new JCheckBox("2");
          this.cbLH2.setSelected(true);
          this.cbLH2.addItemListener(this);
          return cbLH2;
    }
    
    private JCheckBox crearCbLH3(){
        this.cbLH3 = new JCheckBox("3");
          this.cbLH3.setSelected(true);
          this.cbLH3.addItemListener(this);
          return cbLH3;
    }
    
    private JCheckBox crearCbLH4(){
        this.cbLH4 = new JCheckBox("4");
          this.cbLH4.setSelected(true);
          this.cbLH4.addItemListener(this);
          return cbLH4;
    }
    
    private JCheckBox crearCbLH5(){
        this.cbLH5 = new JCheckBox("5");
          this.cbLH5.setSelected(true);
          this.cbLH5.addItemListener(this);
          return cbLH5;
    }
    
    private JCheckBox crearCbLHmuneca(){
        this.cbLHmuneca = new JCheckBox("Muñeca");
          this.cbLHmuneca.setSelected(true);
          this.cbLHmuneca.addItemListener(this);
          return cbLHmuneca;
    }

    @Override
      public void itemStateChanged(ItemEvent e) {
       Object source = e.getItemSelectable();
       XYPlot plot = this.chart.getXYPlot();
         AbstractRenderer renderer = (AbstractRenderer) plot.getRenderer();
       
       
           if(source == this.cbRH1){
               if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(0, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(0, true);
               }
           } else if(source == this.cbRH2){
              if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(1, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(1, true);
               }
           }else if(source == this.cbRH3){
              if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(2, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(2, true);
               }
           }else if(source == this.cbRH4){
               if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(3, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(3, true);
               }
           }else if(source == this.cbRH5){
               if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(4, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(4, true);
               }
           }else if(source == this.cbRHmuneca){
               if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(5, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(5, true);
               }       
           }
           if(source == this.cbLH1){
               if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(6, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(6, true);
               }
           } else if(source == this.cbLH2){
              if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(7, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(7, true);
               }
           }else if(source == this.cbLH3){
              if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(8, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(8, true);
               }
           }else if(source == this.cbLH4){
              if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(9, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(9, true);
               }
           }else if(source == this.cbLH5){
              if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(10, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(10, true);
               }
           }else if(source == this.cbLHmuneca){
               if(e.getStateChange() == ItemEvent.DESELECTED){
                   renderer.setSeriesVisible(11, false);                
               } else if (e.getStateChange() == ItemEvent.SELECTED){
                   renderer.setSeriesVisible(11, true);
               }
           }
      }


    private TimeSeriesCollection extraerDatosArchivo(String direccionArchivoCargado, int tipoDato) {
        TimeSeriesCollection tscAux = new TimeSeriesCollection();
       
       try {
            EscenarioGrabado object;
            ObjectInputStream objectIn;
            File selectedFile = new File(direccionArchivoCargado);
            
            objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(selectedFile.getAbsolutePath())));
           
            while (objectIn!= null) {    
                object = (EscenarioGrabado)objectIn.readObject();            
                if(object.numero == -1){    
                    break;
                }
                 extraerDatoObjeto(object,tipoDato);
            }
            //this.escenarioVirtual.Manos = null;
            objectIn.close();         
       
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error GenerandoEstadistica LeerArchivo " + e);
            this.mmErrorLeyendoArchivo();
            this.dispose();
            
        }  
       return tscAux;
    }

    private void extraerDatoObjeto(EscenarioGrabado object, int tipoDato) {
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

                      if(tipoDato == 3){ //quiere la velocidad
                          registrarComponente(dedoIn.Velocidad, dedoIn.Tipo, mano.Tipo, 1, object.numero);
                            //Ojo. Solo se mostrara la velocidad en Y, por esto se pasa el parametro 1
                      }else{
                          registrarComponente(dedoIn.Punta, dedoIn.Tipo, mano.Tipo,tipoDato, object.numero);
                      }        
                  }
                }
                if(tipoDato == 3){ 
                    registrarComponente(mano.VelocidadMuneca,6, mano.Tipo,1,object.numero);
                    // la muneca esta regitrado como tipo 6, los otros dedos estan del al 5;
                }else{
                    registrarComponente(mano.getDoublePosicionMuneca(),6, mano.Tipo,tipoDato,object.numero);
                }   
          }
        }                  
    }

    private String convIntTipo(int tipoDato) {
        String tipo = "";
        if(tipoDato == 0)
        return "X";  
        else if(tipoDato == 1)
             return "Y"; 
        else if(tipoDato == 2)
             return "Z"; 
        else 
            return "Velocidad Y";   
        
    }

    private void registrarComponente(double[] VectorComponente, int tipoComponente, String mano, int tipoDato, int numeroDeFrame) {
          //quiere decir que NO busca rapidez
            int mili = Operacion.getMilisegundo(numeroDeFrame);
            int sec = Operacion.getSegundo(numeroDeFrame);
            int min = Operacion.getMinuto(numeroDeFrame);
           
            if(tipoComponente == 1){  //pulgar
                switch (mano) {
                    case "RH":
                        this.serieRH1.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                        break;
                    case "LH":
                        this.serieLH1.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                        break;
                }
            } else if(tipoComponente == 2){
                switch (mano) {
                    case "RH":
                        this.serieRH2.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                        break;
                    case "LH":
                        this.serieLH2.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                        break;
                    }
                }else if(tipoComponente == 3){
                    switch (mano) {
                        case "RH":
                            this.serieRH3.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                            break;
                        case "LH":
                            this.serieLH3.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                            break;
                    }
               }else if(tipoComponente == 4){
                    switch (mano) {
                        case "RH":
                            this.serieRH4.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                            break;
                        case "LH":
                            this.serieLH4.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                            break;
                    }
               }else if(tipoComponente == 5){
                    switch (mano) {
                        case "RH":
                            this.serieRH5.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                            break;
                        case "LH":
                            this.serieLH5.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                            break;
                    }
               }else if(tipoComponente == 6){
                    switch (mano) {
                        case "RH":
                            this.serieRHmuneca.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                            break;
                        case "LH":
                            this.serieLHmuneca.addOrUpdate(new Millisecond(mili,sec,min,0,this.dia,this.mes,this.annio), VectorComponente[tipoDato]);
                            break;
                    }
               }  
        }
    

    
      private void mmErrorLeyendoArchivo() {
        ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/error.png"));
        JOptionPane.showMessageDialog(this,"ERROR leyendo Archivo, tipo de datos incompatible", "", JOptionPane.OK_OPTION, Img); 
                    if(tipoDato == 0){
                        controladorPanel.cuadroXactivo = false;
                        System.out.println("llega a dispose");
                           this.dispose();
                    } else if(tipoDato == 1){
                        System.out.println("llega a dispose");
                          controladorPanel.cuadroYactivo = false;
                           this.dispose();
                    }else if(tipoDato == 2){
                        System.out.println("llega a dispose");
                          controladorPanel.cuadroZactivo = false;
                         this.dispose();
                    }else if(tipoDato == 3){
                        System.out.println("llega a dispose");
                          controladorPanel.cuadroRactivo = false;
                           this.dispose();
                    }       
    }
    
    public void guardarJPG(){ 
    try 
    { 
        JFileChooser fileChooser = new JFileChooser(); 
        ExtensionFileFilter filter = new ExtensionFileFilter("JPEG", ".jpg"); 
        fileChooser.addChoosableFileFilter(filter); 

        int option = fileChooser.showSaveDialog(this); 

        if (option == JFileChooser.APPROVE_OPTION) 
        { 
            File file; 
            String fileName = fileChooser.getSelectedFile().getName(); 
            String filePath = fileChooser.getSelectedFile().getParent(); 
            int ext = fileName.indexOf(".jpg"); 

            if (ext <0) 
            { 
                 String test = filePath.substring(filePath.length()-1); 
            if (test!="/") 
            { 
                 fileName = filePath+"/"+fileName+".jpg"; 	
            } 
            else 
            { 
            fileName = filePath+fileName+".jpg"; 
            } 

            file = new File(fileName); 

        } 
        else 
        { 
            file = fileChooser.getSelectedFile();	
        }	
            ChartUtilities.saveChartAsJPEG(file, this.chart, chartPanel.getWidth(), chartPanel.getHeight()); 
            mmImagenGuardada();
      } 
    } 
        catch (IOException e) 
        { 
               System.out.println("Error Guardado Arhivo: "+e); 
                mmErrorImagen();
        } 
    }
    
    private void mmImagenGuardada() {
      ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/bien.png"));
      JOptionPane.showMessageDialog(this,"Imagen guardada", "", JOptionPane.INFORMATION_MESSAGE, Img);
    }
    
    private void mmErrorImagen(){
         ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/error.png"));
        JOptionPane.showMessageDialog(this,"Error guardando imagen", "", JOptionPane.INFORMATION_MESSAGE, Img);
    }
   
}

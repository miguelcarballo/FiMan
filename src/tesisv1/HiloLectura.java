/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.Color;
import java.awt.FileDialog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Thread.sleep;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author miguel
 */
public class HiloLectura extends Thread {
    EscenarioVirtual escenarioVirtual;
    ControladorHilos controladorHilos;
    protected static final String EXTENSION = ".ptf";
    protected static final String FORMAT_NAME = "ptf";
    public Pantalla pantalla;
    JButton btCargar, btGrabar, btReproducir, btDobleVelocidad, btMitadVelocidad, 
                btXvsTiempo, btYvsTiempo, btZvsTiempo, btRvsTiempo, btExportar, btParar;
    JLabel lbltiempo, lblVelocidad, lblNombreArchivo;
    Integer minutos, segundos , milesimas;
    
    
    public HiloLectura(EscenarioVirtual escenarioIn, ControladorHilos controlador, Pantalla pantallaIn, 
            JButton btGrabarIn, JButton btCargarIn, JButton btReproducirIn, JButton btDobleVelocidadIn,JButton btMitadVelocidadIn, 
            JButton btXvsTiempoIn,JButton btYvsTiempoIn,JButton btZvsTiempoIn, JButton btRvsTiempoIn, JButton btExportarIn, JButton btPararIn,
            JLabel lblArchivo, JLabel lblTiempoIn){
        this.escenarioVirtual = escenarioIn;
        this.controladorHilos = controlador;
        this.pantalla = pantallaIn;
        this.btCargar = btCargarIn;
        this.btGrabar = btGrabarIn;
        this.btReproducir = btReproducirIn;
        this.btXvsTiempo = btXvsTiempoIn;
        this.btYvsTiempo = btYvsTiempoIn;
        this.btZvsTiempo = btZvsTiempoIn;
        this.btRvsTiempo = btRvsTiempoIn;
        this.btDobleVelocidad = btDobleVelocidadIn;
        this.btMitadVelocidad = btMitadVelocidadIn;
        this.btExportar = btExportarIn;
        this.btParar = btPararIn;
        this.lblNombreArchivo = lblArchivo;
        this.lbltiempo = lblTiempoIn;
        
        this.minutos = 0;
        this.segundos = 0;
        this.milesimas = 0;
       
    }
    
    @Override
    public void run(){
       
       String direccionArchivo = CargarArchivo();
       if(direccionArchivo != null)
       {
           mmArchivoCargado();
          
           this.btReproducir.setEnabled(true);
           this.btXvsTiempo.setEnabled(true);
           this.btYvsTiempo.setEnabled(true);
           this.btZvsTiempo.setEnabled(true);
           this.btRvsTiempo.setEnabled(true);
           this.btDobleVelocidad.setEnabled(true);
           this.btMitadVelocidad.setEnabled(true);
           this.btExportar.setEnabled(true);
           this.btGrabar.setEnabled(false);
         
           this.btCargar.setText("Quitar Archivo");
           this.controladorHilos.estadoArchivoCargado = true;
           while(this.controladorHilos.estadoArchivoCargado){
               try {
                   //espera
                   sleep(40);
                } catch (InterruptedException ex) {
                    System.out.println("EsperaInterrumpida HiloLectura");
                }
                   if(this.controladorHilos.estadoEnReproduccion){
                    LeerArchivo(direccionArchivo);
                    this.controladorHilos.estadoEnReproduccion = false;
                    this.btCargar.setEnabled(true); //ojo
                  //  this.btGrabar.setEnabled(true);
                    this.btReproducir.setText("Reproducir");
                    this.btParar.setEnabled(false);
                   }  
          }
       }  
    }

    private String CargarArchivo() {
         boolean permiso = false;
       while(!permiso){
           FileDialog winCargar=new FileDialog(this.pantalla,"Cargar archivo .ptf",FileDialog.LOAD);
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
             winCargar.setFilenameFilter(file);
             winCargar.setVisible(true);
             
             if(winCargar.getDirectory()== null || winCargar.getFile() == null){
                 permiso = true;
             }else 
             {
                 boolean extensionAceptada = evaluarExtension(winCargar.getFile());
                 if(extensionAceptada)
                 {
                     String direccionSeleccionada = winCargar.getDirectory()+winCargar.getFile();
                     this.controladorHilos.direccionArchivoCargado = direccionSeleccionada;
                     this.controladorHilos.nombreArchivoCargado = winCargar.getFile();
                     this.lblNombreArchivo.setText(this.controladorHilos.nombreArchivoCargado);
                     this.lblNombreArchivo.setForeground(Color.BLUE);
                     this.resetearCronometro();
                     return direccionSeleccionada;                     
                 }else{
                     mmExtensionRechazada();
                 }
             }
       }
       return null;
    }

     private boolean evaluarExtension(String fileName) {
        if(fileName.endsWith(EXTENSION) || fileName.endsWith(EXTENSION.toUpperCase())){
            return true;
        }else{
             int index = fileName.lastIndexOf('.');
             return index == -1;
        }
    }
     
     private void mmExtensionRechazada() {
        ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/error.png"));
        JOptionPane.showMessageDialog(null,"La extension de archivo cargado debe ser " + EXTENSION, "", JOptionPane.INFORMATION_MESSAGE, Img);
    }
     
    private void mmArchivoCargado() {
      ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/bien.png"));
      JOptionPane.showMessageDialog(null,"Archivo cargado", "", JOptionPane.OK_OPTION, Img);
   
    }
     
    private void mmReproduccionTerminada() {
      ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/bien.png"));
      JOptionPane.showMessageDialog(this.pantalla,"Reproduccion finalizada", "", JOptionPane.OK_OPTION, Img); 
    }
      
    private void mmErrorLeyendoArchivo() {
        ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/error.png"));
        String arch = this.lblNombreArchivo.getText();
        JOptionPane.showMessageDialog(this.pantalla,"ERROR reproduciendo " + arch + 
                                    ", tipo de datos incompatible", "", JOptionPane.OK_OPTION, Img); 
    }
    
    private void LeerArchivo(String direccionArchivo) {
        this.controladorHilos.hiloLeap = false;
           try {
            EscenarioGrabado object;
            ObjectInputStream objectIn;
            File selectedFile = new File(direccionArchivo);
            objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(selectedFile.getAbsolutePath())));
           
            while (objectIn!= null) {     
                sleep(this.controladorHilos.intervaloReproduccion);
              
                object = (EscenarioGrabado)objectIn.readObject();
                while(this.controladorHilos.pausado){
                    sleep(40);
                }       
                if(this.controladorHilos.pararActivado){
                    break;
                }            
                if(object.numero == -1){          
                    break;
                }
                 ReemplazarEscenario(object);
            }
            if(this.controladorHilos.pararActivado){
                objectIn.close();
                resetearCronometro();
                this.controladorHilos.pararActivado = false;
            } else{
                 objectIn.close(); 
                 
                 mmReproduccionTerminada();
                 resetearCronometro();
            }
        } catch(IOException | ClassNotFoundException | InterruptedException e){
            System.out.println("Error HiloLectura LeerArchivo " + e);
          
            mmErrorLeyendoArchivo();
        }
       
        this.controladorHilos.hiloLeap = true;
    }

    private void ReemplazarEscenario(EscenarioGrabado escenarioGrabadoIn) {
        this.escenarioVirtual.Manos = (Hashtable<String, Mano>) escenarioGrabadoIn.Manos.clone();
        actualizarCronometro(escenarioGrabadoIn.numero);
    }

  

    private void resetearCronometro() {
        this.minutos = 0;
        this.segundos = 0;
        this.milesimas = 0;
        
         this.lbltiempo.setText( "00:00:000" );
      }

    private void actualizarCronometro(int numGrabado) {
      String min="", seg="", mil="";
      
      this.milesimas += Operacion.intervaloFijoGrabado;
       if( this.milesimas >= 1000 )
                {
                    int resto = this.milesimas - 1000;
                    this.milesimas = resto;
                    this.segundos += 1;
                    //Si los segundos llegan a 60 entonces aumenta 1 los minutos
                    //y los segundos vuelven a 0
                    if( this.segundos >= 60 )
                    {
                        this.segundos = 0;
                        this.minutos++;
                    }
                }
                //Esto solamente es estetica para que siempre este en formato
                //00:00:000
                if( this.minutos < 10 ) min = "0" + this.minutos;
                else min = this.minutos.toString();
                if( this.segundos < 10 ) seg = "0" + this.segundos;
                else seg = segundos.toString();

                if( this.milesimas < 10 ) mil = "00" + this.milesimas;
                else if( this.milesimas < 100 ) mil = "0" + this.milesimas;
                else mil = this.milesimas.toString();
                
                this.lbltiempo.setText( min + ":" + seg + ":" + mil );
    }
 
}

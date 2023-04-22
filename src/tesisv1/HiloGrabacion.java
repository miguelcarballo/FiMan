/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author miguel
 */
public class HiloGrabacion extends Thread{
    EscenarioVirtual escenarioVirtual;
    ControladorHilos controladorHilos;
    Pantalla pantalla;
   

    
     protected static final String EXTENSION = ".ptf";
  
    public HiloGrabacion(EscenarioVirtual escenarioIn, ControladorHilos controlador, Pantalla pantallaIn){
        this.escenarioVirtual = escenarioIn;    
        this.controladorHilos = controlador;
        this.pantalla = pantallaIn;
    
    }
    
    @Override
    public void run(){
        try {
            guardadoTemporal();
            guardadoDefinido();
  
            //System.out.println("Fin hilo grabacion");
        } catch (InterruptedException ex) {
          
            System.out.println("error hilo Grabacion Interrupted");
        }
        
    }

    private void guardadoTemporal() throws InterruptedException {
      ObjectOutputStream out;
      int contador = 0;
      try{ 
        
          File archivo = new File("aux.temp");
         // System.out.println(archivo.exists());
          if(archivo.exists())
          {
            
              archivo.delete();
              archivo = new File("aux.temp");
          }
             out = new ObjectOutputStream(new FileOutputStream(archivo));
        
            while(this.controladorHilos.hiloGrabacion){
                
                sleep(Operacion.intervaloFijoGrabado); //aqui se define la resolucion de guardado
               // EscenarioVirtual nuevoE = this.escenarioVirtual
                EscenarioGrabado escenarioGrabado = new EscenarioGrabado(this.escenarioVirtual, contador); // generar dato
                
                out.writeObject(escenarioGrabado);
               // escenarioGrabado.MostrarManos();
                contador = contador+1;
                 //System.out.println("grab final " + this.controladorHilos.hiloGrabacion);
                 
            }
                EscenarioGrabado escenarioGrabado = new EscenarioGrabado(this.escenarioVirtual, -1); //ultimo 
                out.writeObject(escenarioGrabado);
              
                
             out.flush();
             out.close();
            // System.out.println("se escribio");
          }
          catch(IOException ex){
            System.out.println(ex);
      }  
    }

    private void guardadoDefinido() {      
        boolean aceptado = false;
        while(!aceptado){
            FileDialog winGuardar=new FileDialog(this.pantalla,"Guardar archivo " + EXTENSION,FileDialog.SAVE);
            //winGuardar.setAlwaysOnTop(true);
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
             winGuardar.setFile("captura"+EXTENSION);
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
                             //Aceptar sobreescritura
                             SobreescribirArchivo(direccionSeleccionada);
                             mmArchivoEscrito(auxNombrefile);
                             aceptado = true;
                             } catch (IOException ex) {
                                 System.out.println("Error copiando archivos - HiloGuardado");
                             }
                         } 
                     } else{
                         try {
                             SobreescribirArchivo(direccionSeleccionada);  //escritura normal
                             mmArchivoEscrito(auxNombrefile);
                             aceptado = true;
                         } catch (IOException ex) {
                              System.out.println("Error copiando archivos 2- HiloGuardado");
                         }
                     }
                 } else{   //no se acepta la extension
                        mmExtensionRechazada();
                }
             }
        }
        
    }

    private boolean EvaluarExistencia(String direccionSeleccionada) {
        File fileEv = new File(direccionSeleccionada);
        return fileEv.exists();
    }

    private boolean evaluarExtension(String fileName) {
        if(fileName.endsWith(EXTENSION) || fileName.endsWith(EXTENSION.toUpperCase())){
            //System.out.println("return true");
            return true;
        }else{
             int index = fileName.lastIndexOf('.');
             return index == -1;
        }
    }

    private boolean confirmarSobreescritura(FileDialog winGuardar, String file) {
                Object[] options = {"Si","No"};
                ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/pregunta.png"));
                int result = JOptionPane.showOptionDialog(null,
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

    private void SobreescribirArchivo(String direccionIn) throws IOException {
          File selectedFile = new File(direccionIn);
          selectedFile.delete();
          selectedFile = new File(direccionIn);
          File source = new File("aux.temp");
          
          if(source.exists())
          {
           Files.copy(source.toPath(), selectedFile.toPath()); 
          }
    }

    private String agregarExtension(String direccionSeleccionada) {
     
        if(direccionSeleccionada.endsWith(EXTENSION)||direccionSeleccionada.endsWith(EXTENSION.toUpperCase())){
            return direccionSeleccionada;
        } else{
            return direccionSeleccionada + EXTENSION;
        }
    }

    private void mmArchivoEscrito(String nombreArchivo) {
         ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/bien.png"));
      JOptionPane.showMessageDialog(null,"Operación realizada correctamente: " + nombreArchivo + " guardado"
              , "", JOptionPane.INFORMATION_MESSAGE, Img);
    
    }

    private void mmExtensionRechazada() {
         ImageIcon Img = new ImageIcon(getClass().getResource("/tesisv1/img/error.png"));
        JOptionPane.showMessageDialog(null,"La extension de archivo debe ser " + EXTENSION, "", JOptionPane.INFORMATION_MESSAGE, Img);
    }
}

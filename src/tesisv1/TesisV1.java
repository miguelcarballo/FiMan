/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class TesisV1 {
static ControladorHilos controladorHilos;
static EscenarioVirtual escenario;
static HiloLeap hleap;
static HiloDibujo hdibujo;
 static HiloSonido hsonido;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    try {
        // System.setProperty("Djava.library.path", "/Users/miguel/Desktop/LeapSDK/lib/");
        //addLibraryPath("/Users/miguel/Desktop/LeapSDK/lib/");
        //setLibraryPath("/Users/miguel/NetBeansProjects/TesisV1/dist/lib/");
        addLibraryPath("/Users/miguel/Desktop/LeapSDKf/");
    } catch (Exception ex) {
        Logger.getLogger(TesisV1.class.getName()).log(Level.SEVERE, null, ex);
    }
        // TODO code application logic here
        controladorHilos = new ControladorHilos();
        escenario = new EscenarioVirtual();
      
       hleap = new HiloLeap(escenario, controladorHilos); 
       hdibujo = new HiloDibujo(escenario, controladorHilos);
       hsonido = new HiloSonido(escenario, controladorHilos);
       hdibujo.start();
       hleap.start();
       hsonido.start();
       
      
    }
    
    public static void addLibraryPath(String pathToAdd) throws Exception{
    final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
    usrPathsField.setAccessible(true);

    //get array of paths
    final String[] paths = (String[])usrPathsField.get(null);

    //check if the path to add is already present
    for(String path : paths) {
        if(path.equals(pathToAdd)) {
            return;
        }
    }

    //add the new path
    final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
    newPaths[newPaths.length-1] = pathToAdd;
    usrPathsField.set(null, newPaths);
}
    
    public static void setLibraryPath(String path) throws Exception {
    System.setProperty("java.library.path", path);

    //set sys_paths to null so that java.library.path will be reevalueted next time it is needed
    final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
    sysPathsField.setAccessible(true);
    sysPathsField.set(null, null);
}
}

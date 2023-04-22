/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;
import com.leapmotion.leap.Matrix;
import com.leapmotion.leap.Vector;
import java.awt.Color;

/**
 *
 * @author miguel
 */
public class DibujoHueso extends Malla {
    double d = Operacion.distancia;
    double[] cIni;
    double[] cFin;
    double[] p1Ini;
    double[] p2Ini;
    double[] p3Ini;
    double[] p4Ini;
    
    double[] p1Fin;
    double[] p2Fin;
    double[] p3Fin;
    double[] p4Fin;
    
   
    Matrix matriz;
    
    
    public DibujoHueso(float[] cIniIn, float[] cFinIn, float anchoIn, 
                        float[] vBaseXin,float[] vBaseYin, float[] vBaseZin, boolean corrector, Pgrafico buffer){
        
        float ancho = anchoIn/2;
        Vector origenInicio = new Vector(cIniIn[0],cIniIn[1],cIniIn[2]);
        Vector origenFin = new Vector(cFinIn[0],cFinIn[1],cFinIn[2]);
        
        Vector vBaseX = new Vector(vBaseXin[0],vBaseXin[1],vBaseXin[2]);
        Vector vBaseY = new Vector(vBaseYin[0],vBaseYin[1],vBaseYin[2]);
        Vector vBaseZ = new Vector(vBaseZin[0],vBaseZin[1],vBaseZin[2]);
        
        Vector vectorY = new Vector(vBaseY.normalized());
        if (corrector){
            vBaseX.times(-1);
        }
        this.matriz = new Matrix(vBaseX, vBaseY, vBaseZ);
        
        this.matriz.setRotation(vBaseZ, (float) (45*Math.PI/180)); // primero 45 grados
        Vector rotacion1 = new Vector(vectorY).times(ancho);
        this.matriz.setOrigin(origenInicio);
        Vector punto1inicio = new Vector(this.matriz.transformPoint(rotacion1));
        this.matriz.setOrigin(origenFin);
        Vector punto1fin = new Vector(this.matriz.transformPoint(rotacion1));
        
        this.matriz.setRotation(vBaseZ, (float) (135*Math.PI/180)); // segundo 90 grados
        Vector rotacion2 = new Vector(vectorY).times(ancho);
        this.matriz.setOrigin(origenInicio);
        Vector punto2inicio = new Vector(this.matriz.transformPoint(rotacion2));
        this.matriz.setOrigin(origenFin);
        Vector punto2fin = new Vector(this.matriz.transformPoint(rotacion2));
        
        this.matriz.setRotation(vBaseZ, (float) (225*Math.PI/180)); // segundo 90 grados
        Vector rotacion3 = new Vector(vectorY).times(ancho);
        this.matriz.setOrigin(origenInicio);
        Vector punto3inicio = new Vector(this.matriz.transformPoint(rotacion3));
        this.matriz.setOrigin(origenFin);
        Vector punto3fin = new Vector(this.matriz.transformPoint(rotacion3));
        
        this.matriz.setRotation(vBaseZ, (float) (315*Math.PI/180)); // segundo 90 grados
        Vector rotacion4 = new Vector(vectorY).times(ancho);
        this.matriz.setOrigin(origenInicio);
        Vector punto4inicio = new Vector(this.matriz.transformPoint(rotacion4));
        this.matriz.setOrigin(origenFin);
        Vector punto4fin = new Vector(this.matriz.transformPoint(rotacion4));
        
        this.p1Ini = Operacion.conversor(Operacion.convCoordFloatToDouble(punto1inicio.toFloatArray()).clone());
        this.p2Ini = Operacion.conversor(Operacion.convCoordFloatToDouble(punto2inicio.toFloatArray()).clone());
        this.p3Ini = Operacion.conversor(Operacion.convCoordFloatToDouble(punto3inicio.toFloatArray()).clone());
        this.p4Ini = Operacion.conversor(Operacion.convCoordFloatToDouble(punto4inicio.toFloatArray()).clone());
        
         this.p1Fin = Operacion.conversor(Operacion.convCoordFloatToDouble(punto1fin.toFloatArray()).clone());
        this.p2Fin = Operacion.conversor(Operacion.convCoordFloatToDouble(punto2fin.toFloatArray()).clone());
        this.p3Fin = Operacion.conversor(Operacion.convCoordFloatToDouble(punto3fin.toFloatArray()).clone());
        this.p4Fin = Operacion.conversor(Operacion.convCoordFloatToDouble(punto4fin.toFloatArray()).clone());
    
         this.settbuffer(buffer);
         this.cIni = Operacion.conversor(Operacion.convCoordFloatToDouble(cIniIn.clone()));
        this.cFin = Operacion.conversor(Operacion.convCoordFloatToDouble(cIniIn.clone()));
        // this.ancho = Operacion.convProporcion(anchoIn);
         //double a = ancho/3; //uso 1/3 del grosor
         
         /* this.agregarvertice(cIni[0]-a,cIni[1]+a ,cIni[2]+d);
          this.agregarvertice(cIni[0]-a,cIni[1]-a ,cIni[2]+d);
          this.agregarvertice(cIni[0]+a,cIni[1]-a ,cIni[2]+d);
          this.agregarvertice(cIni[0]+a,cIni[1]+a ,cIni[2]+d);
          
          this.agregarvertice(cFin[0]-a,cFin[1]+a ,cFin[2]+d);
          this.agregarvertice(cFin[0]-a,cFin[1]-a ,cFin[2]+d);
          this.agregarvertice(cFin[0]+a,cFin[1]-a ,cFin[2]+d);
          this.agregarvertice(cFin[0]+a,cFin[1]+a ,cFin[2]+d);
          */
         this.agregarvertice(p1Ini[0],p1Ini[1] ,p1Ini[2]);
         this.agregarvertice(p2Ini[0],p2Ini[1] ,p2Ini[2]);
         this.agregarvertice(p3Ini[0],p3Ini[1] ,p3Ini[2]);
         this.agregarvertice(p4Ini[0],p4Ini[1] ,p4Ini[2]);
         
         this.agregarvertice(p1Fin[0],p1Fin[1] ,p1Fin[2]);
         this.agregarvertice(p2Fin[0],p2Fin[1] ,p2Fin[2]);
         this.agregarvertice(p3Fin[0],p3Fin[1] ,p3Fin[2]);
         this.agregarvertice(p4Fin[0],p4Fin[1] ,p4Fin[2]);
         
         this.agregarvertice(cIni[0], cIni[1],cIni[2]);
         this.agregarvertice(cFin[0], cFin[1],cFin[2]);
           
          this.agregararista(0, 1);
          this.agregararista(1, 2);
          this.agregararista(2, 3);
          this.agregararista(3, 0);
          
          this.agregararista(4, 5);
          this.agregararista(5, 6);
          this.agregararista(6, 7);
          this.agregararista(7, 4);
          
          this.agregararista(0, 4);
          this.agregararista(1, 5);
          this.agregararista(2, 6);
          this.agregararista(3, 7);
          
          //para saber....
          this.agregararista(0, 8);
          this.agregararista(1, 8);
          this.agregararista(2, 8);
          this.agregararista(3, 8);
          
          this.color=Color.blue;
          setorigen();
          centrom();
      
    }
}

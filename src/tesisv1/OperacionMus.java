package tesisv1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author miguel
 */
public class OperacionMus {
    
    
    public static double tamOctava = 16.5 * 10;
    public static double anTeclaSup = (16.5/12) * 10 ;
    public static double anTeclaInf = (16.5/7)* 10;
    public static double lTeclaNg = 9.5 * 10;
    public static double lTeclaBl = 14.7 * 10;
    
    public static double tolerancia = 50;
    
 
     public static int getNumMIDI(int nNota, int nOctava){
        int nmidi = 60+(nOctava - 4)*12 + (nNota - 1);     
        return nmidi;
    }
    
     public static int getNumEscala(String nombreIn){
        int vnota;
        switch(nombreIn.toLowerCase()) {    
            case "do": vnota = 1;
                break;
            case "do#": vnota = 2;
                break;
            case "re": vnota = 3;
                break;
            case "re#": vnota = 4;
                break;
            case "mi": vnota = 5;
                break; 
            case "fa": vnota = 6;
                break;
            case "fa#": vnota = 7;
                break;
            case "sol": vnota = 8;
                break;
            case "sol#": vnota = 9;
                break;
            case "la": vnota = 10;
                break;
            case "la#": vnota = 11;
                break;
            case "si": vnota = 12;
                break;
            default: vnota = 0;
                break;
        }  
        return vnota;
    }

    public static boolean getEsBlanca(String nombreIn) {    
      boolean es;
         switch(nombreIn.toLowerCase()) {    
            case "do#": case "re#": case "fa#":case "sol#":case "la#": es = false;
                break;
            default: es = true;
         }
         return es;          
    }
    
    public static String getNombreInt(int valor){
        String aux;
        switch(valor){
            case 1: aux = "do";
                break;
            case 2: aux = "do#";
                break;
            case 3: aux = "re";
                break;
            case 4: aux = "re#";
                break;
            case 5: aux = "mi";
                break;             
            case 6: aux = "fa";
                break;
            case 7: aux = "fa#";
                break;
            case 8: aux = "sol";
                break;
            case 9: aux = "sol#";
                break;
            case 10: aux = "la";
                break;               
            case 11: aux = "la#";
                break;           
            case 12: aux = "si";
                break;
            default: aux = "error";
                break;       
        }
        return aux;   
    }
    
    
    public static double[][] getPosTecla(Nota notaIn, double[] PosIn){
        double[][] pos;
        double x = PosIn[0];
        double al = PosIn[1];
        double y = PosIn[2];
        
         
        if (!notaIn.esBlanca){ 
            pos = new double[4][3];
            pos[0] = PosIn;
            pos[1][0] = x;
            pos[1][2] = y + lTeclaNg;
            pos[2][0] = x + anTeclaSup;
            pos[2][2] = y+ lTeclaNg;
            pos[3][0] = x + anTeclaSup;
            pos[3][2] = y;
            
            pos[1][1] = al;
            pos[2][1] = al;
            pos[3][1] = al;         
        }
        else{
            pos = new double[8][3];
            pos[0] = PosIn;
            
            pos[1][2] = y + lTeclaNg;
            pos[2][2] = y + lTeclaNg;
            pos[3][2] = y + lTeclaBl;
            pos[4][2] = y + lTeclaBl;
            pos[5][2] = y + lTeclaNg;
            pos[6][2] = y + lTeclaNg;
            pos[7][2] = y;
            //solo para la altura
            for (int f = 1; f < 8; f++){
                pos[f][1] = al;
            }
     
            if(notaIn.numNotaEscala == 1 || notaIn.numNotaEscala == 6){ //do o fa
                pos[1][0] = x;               
                pos[2][0] = x;               
                pos[3][0] = x;              
                pos[4][0] = x + anTeclaInf;             
                pos[5][0] = x + anTeclaInf;            
                pos[6][0] = x + anTeclaSup;            
                pos[7][0] = x + anTeclaSup;          
            }          
            else if (notaIn.numNotaEscala == 3) //re
            {
                pos[1][0] = x;        
                pos[2][0] = x-(2*anTeclaSup-anTeclaInf);          
                pos[3][0] = x-(2*anTeclaSup-anTeclaInf);           
                pos[4][0] = x-(2*anTeclaSup-anTeclaInf) + anTeclaInf;      
                pos[5][0] = x-(2*anTeclaSup-anTeclaInf) + anTeclaInf;          
                pos[6][0] = x + anTeclaSup;              
                pos[7][0] = x + anTeclaSup;    
            }
            else if (notaIn.numNotaEscala == 5) //mi
            {
                pos[1][0] = x;           
                pos[2][0] = x-(4*anTeclaSup-2*anTeclaInf);           
                pos[3][0] = x-(4*anTeclaSup-2*anTeclaInf);             
                pos[4][0] = x+anTeclaSup;           
                pos[5][0] = x+anTeclaSup;          
                pos[6][0] = x+anTeclaSup;        
                pos[7][0] = x+anTeclaSup;       
            }
            /*else if(notaIn.numNotaEscala == 8){ //sol
                pos[1][0] = x;        
                pos[2][0] = x-(7*anTeclaSup-4*anTeclaInf);          
                pos[3][0] = x-(7*anTeclaSup-4*anTeclaInf);           
                pos[4][0] = x-(7*anTeclaSup-4*anTeclaInf) + anTeclaInf;      
                pos[5][0] = x-(7*anTeclaSup-4*anTeclaInf) + anTeclaInf;          
                pos[6][0] = x + anTeclaSup;              
                pos[7][0] = x + anTeclaSup;         
            }     */  
             else if(notaIn.numNotaEscala == 8){ //sol
                pos[1][0] = x;        
                pos[2][0] = x-(2*anTeclaSup-anTeclaInf);          
                pos[3][0] = x-(2*anTeclaSup-anTeclaInf);           
                pos[4][0] = x-(2*anTeclaSup-anTeclaInf) + anTeclaInf;      
                pos[5][0] = x-(2*anTeclaSup-anTeclaInf) + anTeclaInf;          
                pos[6][0] = x + anTeclaSup;              
                pos[7][0] = x + anTeclaSup;         
            }   /*       
            else if(notaIn.numNotaEscala == 10){ //la
                pos[1][0] = x;        
                pos[2][0] = x-(9*anTeclaSup-5*anTeclaInf);          
                pos[3][0] = x-(9*anTeclaSup-5*anTeclaInf);           
                pos[4][0] = x-(9*anTeclaSup-5*anTeclaInf) + anTeclaInf;      
                pos[5][0] = x-(9*anTeclaSup-5*anTeclaInf) + anTeclaInf;          
                pos[6][0] = x + anTeclaSup;              
                pos[7][0] = x + anTeclaSup;         
            }   */   
             else if(notaIn.numNotaEscala == 10){ //la
                pos[1][0] = x;        
                pos[2][0] = x-(4*anTeclaSup-2*anTeclaInf);          
                pos[3][0] = x-(4*anTeclaSup-2*anTeclaInf);           
                pos[4][0] = x-(4*anTeclaSup-2*anTeclaInf) + anTeclaInf;      
                pos[5][0] = x-(4*anTeclaSup-2*anTeclaInf) + anTeclaInf;          
                pos[6][0] = x + anTeclaSup;              
                pos[7][0] = x + anTeclaSup;         
            }     
             /*
            else if (notaIn.numNotaEscala == 12) //si
            {
                pos[1][0] = x;           
                pos[2][0] = x-(11*anTeclaSup-6*anTeclaInf);           
                pos[3][0] = x-(11*anTeclaSup-6*anTeclaInf);             
                pos[4][0] = x+anTeclaSup;           
                pos[5][0] = x+anTeclaSup;          
                pos[6][0] = x+anTeclaSup;        
                pos[7][0] = x+anTeclaSup;       
            }    */
           else if(notaIn.numNotaEscala == 12){ //si
                pos[1][0] = x;        
                pos[2][0] = x-(6*anTeclaSup-3*anTeclaInf);          
                pos[3][0] = x-(6*anTeclaSup-3*anTeclaInf);           
                pos[4][0] = x+ anTeclaSup;      
                pos[5][0] = x+ anTeclaSup;          
                pos[6][0] = x + anTeclaSup;              
                pos[7][0] = x + anTeclaSup;         
            }    
        }    
        return pos;       
    }

    public static Nota getNotaSuperior(Nota auxNotaIn) {
        int octava = auxNotaIn.octava;
        int valor = auxNotaIn.numNotaEscala;
        
        if (auxNotaIn.numNotaEscala != 12)
        {
            valor = valor +1;
        }else{
            valor = 1;
            octava = octava +1;
        }        
        Nota auxNota = new Nota(valor,octava); 
        return auxNota;      
    }
    
    public static boolean coord1Area2(double[] coord, double[][] puntosArea){
        double[] A = puntosArea[1];
        double[] B = puntosArea[2];
       // double[] C = puntosArea[3];
        double[] D = puntosArea[0];
        
        double[] P = coord;
        boolean esta = false;
        
        if(P[1] > (A[1] - tolerancia) && (P[1] < A[1])){ //eje y
            if ((P[0] > A[0]) && P[0]<B[0]){    //eje x
                if((P[2])>D[2] && (P[2]< A[2])) {    //eje z
                    esta = true;
                }
            }
        }
        return esta;
    }
   
}

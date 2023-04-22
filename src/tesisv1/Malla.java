/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisv1;

import java.awt.Color;

/**
 *
 * @author Owner
 */
public abstract class Malla {

    private Vlist vertices;
    private Alist aristas;
   // private Plist poligonos;
    private Pgrafico buffer;
    private int anchop, largop;
    private int anchol, largol;
    private int anchof, largof;
    private int anchos, largos;
     protected double xc, yc, zc;
     protected double xo=0,yo=0,zo=0;
    private double[] eje;
    public Color color = Color.BLUE;

    public Malla() {
        this.vertices = new Vlist();
        this.aristas = new Alist();
    }
public void setorigen(){
this.vertices.setorigen(xo, yo, zo);}

    public void settbuffer(Pgrafico buffer) {
        this.buffer = buffer;
    }

    public void ejerot() {
        double norma = Math.sqrt(xc * xc + yc * yc + zc * zc);
        this.eje = new double[4];
        this.eje[0] = xc / norma;
        this.eje[1] = yc / norma;
        this.eje[2] = zc / norma;
        this.eje[3] = 0;
    }

    public double getxc(){return this.xc;}
    public double getyc(){return this.yc;}
    public double getzc(){return this.zc;}

    public void moverx(double dx) {
        this.vertices.transformacion(Matriz.trans(dx, 0, 0));
        this.movercentro(dx, 0, 0);
    }

    public void movery(double dy) {
        this.vertices.transformacion(Matriz.trans(0, dy, 0));
        this.movercentro(0, dy, 0);
    }

    public void moverz(double dz) {
        this.vertices.transformacion(Matriz.trans(0, 0, dz));
        this.movercentro(0, 0, dz);
    }

    public void rotar(double[] eje, double tita) {
        Matriz total = Matriz.trans(-this.xc, -this.yc, -this.zc).mxm(Matriz.rot(eje, tita)).mxm(Matriz.trans(this.xc, this.yc, this.zc));
        this.vertices.transformacion(total);
        
    }

    public void rotarext(double[] eje, double tita,double xc,double yc, double zc) {
        Matriz total = Matriz.trans(-xc, -yc, -zc).mxm(Matriz.rot(eje, tita)).mxm(Matriz.trans(xc, yc,zc));
        this.vertices.transformacion(total);

    }

    public void escala(double[] eje, double k) {
        Matriz total = Matriz.trans(-this.xc, -this.yc, -this.zc).mxm(Matriz.sca(eje, k)).mxm(Matriz.trans(this.xc, this.yc, this.zc));
        this.vertices.transformacion(total);
    }

    public void dibujarfront(int x, int y) {
        buffer.g.setColor(color);
        this.vertices.proyectar(1);
      
      
        for (int i = 0; i < aristas.size; i++) {
            Vertice v1 = aristas.getarista(i).getv1();
            Vertice v2 = aristas.getarista(i).getv2();
            int x0 = (int) (v1.getxf() * largof) + x;
            int x1 = (int) (v2.getxf() * largof) + x;
            int y0 = (int) ((1 - v1.getyf()) * anchof) + y;
            int y1 = (int) ((1 - v2.getyf()) * anchof) + y;
            buffer.Line(x0, y0, x1, y1);

        }
    }

    public void dibujarlat(int x, int y) {
         buffer.g.setColor(color);
        this.vertices.proyectar(2);
      
       // buffer.g.drawString("Vista Lateral", x, anchol + y);
        for (int i = 0; i < aristas.size; i++) {
            Vertice v1 = aristas.getarista(i).getv1();
            Vertice v2 = aristas.getarista(i).getv2();
            int x0 = (int) (v1.getxl() * largol) + x;
            int x1 = (int) (v2.getxl() * largol) + x;
            int y0 = (int) ((1 - v1.getyl()) * anchol) + y;
            int y1 = (int) ((1 - v2.getyl()) * anchol) + y;
            buffer.Line(x0, y0, x1, y1);
        }
    }

    public void dibujarsup(int x, int y) {
         buffer.g.setColor(color);
        this.vertices.proyectar(3);
    
       // buffer.g.drawString("Vista Superior", x, anchos + y);
        for (int i = 0; i < aristas.size; i++) {
            Vertice v1 = aristas.getarista(i).getv1();
            Vertice v2 = aristas.getarista(i).getv2();
            int x0 = (int) (v1.getxs() * largos) + x;
            int x1 = (int) (v2.getxs() * largos) + x;
            int y0 = (int) ((1 - v1.getys()) * anchos) + y;
            int y1 = (int) ((1 - v2.getys()) * anchos) + y;
            buffer.Line(x0, y0, x1, y1);
          
        }

    }

    public void dibujarpers(int x, int y) {
         buffer.g.setColor(color);
        this.vertices.proyectar(4);
        
        for (int i = 0; i < aristas.size; i++) {
            Vertice v1 = aristas.getarista(i).getv1();
            Vertice v2 = aristas.getarista(i).getv2();
            int x0 = (int) (v1.getxp() * largop) + x;
            int x1 = (int) (v2.getxp() * largop) + x;
            int y0 = (int) ((1 - v1.getyp()) * anchop) + y;
            int y1 = (int) ((1 - v2.getyp()) * anchop) + y;
            buffer.Line(x0, y0, x1, y1);
            //    System.out.println(i + " " + x0 + " " + x1 + " " + y0 + " " + y1);

        }
    }

    public void setdimp(int largo, int ancho) {

        this.largop = largo;
        this.anchop = ancho;
    }

    public void setdiml(int largo, int ancho) {

        this.largol = largo;
        this.anchol = ancho;
    }

    public void setdims(int largo, int ancho) {

        this.largos = largo;
        this.anchos = ancho;
    }

    public void setdimf(int largo, int ancho) {

        this.largof = largo;
        this.anchof = ancho;
    }

    public void centrom() {

        this.xc = this.vertices.getxc();
        this.yc = this.vertices.getyc();
        this.zc = this.vertices.getzc();
        this.ejerot();
    }

    public void centrom(double x, double y, double z) {
        this.xc = x+xo;
        this.yc = y+yo;
        this.zc = z+zo;
    }

    ;

    public void movercentro(double xc, double yc, double zc) {
        this.xc = this.xc + xc;
        this.yc = this.yc + yc;
        this.zc = this.zc + zc;
    }

    public void agregarvertice(double x, double y, double z) {
        this.vertices.addv(new Vertice(x, y, z));
    }

    public void agregararista(int a, int b) {

        this.aristas.adda(this.vertices.getvert(a), this.vertices.getvert(b));
    }
}



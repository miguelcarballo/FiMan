/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisv1;

/**
 *
 * @author Owner
 */
public class Vertice {

    private double[] vertice = new double[4];
    double xp, yp;
    double xf, yf;
    double xl, yl;
    double xs, ys;
    int ancho, largo;

    public Vertice() {
    }

    public Vertice(double x, double y, double z) {
        this.vertice[0] = x;
        this.vertice[1] = y;
        this.vertice[2] = z;
        this.vertice[3] = 1;
    }

    public void origen(double xo,double yo,double zo){
    this.vertice[0]=this.vertice[0]+xo;
     this.vertice[1]=this.vertice[1]+yo;
     this.vertice[2]=this.vertice[2]+zo;
    }

   

    public double geti(int i) {
        return this.vertice[i];
    }

    public void list() {
        String s = new String();
        for (int i = 0; i < vertice.length; i++) {
            s += vertice[i] + " ";
        }
        s += "xps=" + xs + " yps=" + ys;
        System.out.println(s);
    }

    public double[] getvert() {
        return this.vertice;
    }

    public void setvert(double[] vert) {
        for (int i = 0; i < vert.length; i++) {
            this.vertice[i] = vert[i];
        }
    }

    public void vxm(Matriz m) {
        double[] vxm = new double[vertice.length];
        double res = 0;

        for (int j = 0; j < vxm.length; j++) {
            for (int i = 0; i < vxm.length; i++) {
                res = res + vertice[i] * m.getvalue(i, j);
            }
            vxm[j] = res;
            res = 0;
        }

        vertice = vxm;

    }

    public void convertir() {
        if (vertice[3] != 1 && vertice[3] != 0) {
            for (int i = 0; i < vertice.length; i++) {
                vertice[i] = vertice[i] / Math.abs(vertice[3]);
            }
        }
    }

    public double[] convertir(double[] v) {
        if (v[3] != 1 && v[3] != 0) {
            for (int i = 0; i < v.length; i++) {
                v[i] = v[i] / Math.abs(v[3]);
            }
        }
        return v;
    }

    public Vertice proyeccion(Matriz proy) {

        Vertice vxm = new Vertice();
        double res = 0;

        for (int j = 0; j < vxm.vertice.length; j++) {
            for (int i = 0; i < vxm.vertice.length; i++) {
                res = res + this.vertice[i] * proy.getvalue(i, j);
            }
            vxm.vertice[j] = res;
            res = 0;
        }
        vxm.convertir();
        return vxm;
    }

    public void frontal() {
        Vertice proyectado = this.proyeccion(Matriz.po(Matriz.ejes[2]));
        //proyectado.vxm(Matriz.w2v(-15, -10,15 , 20, 0, 0, 1, 1));
         proyectado.vxm(Matriz.w2v(-15, -15, 15,15, 0, 0, 1, 1));
        this.xf = proyectado.geti(0);
        this.yf = proyectado.geti(1);
    }

    public void lateral() {
        Vertice proyectado = this.proyeccion(Matriz.po(Matriz.ejes[0]));
        proyectado.vertice[0] = proyectado.vertice[2];
        //proyectado.vxm(Matriz.w2v(-5, -10, 25, 20, 0, 0, 1, 1));
         proyectado.vxm(Matriz.w2v(10, -10, 50,30, 0, 0, 1, 1));
        this.xl = proyectado.geti(0);
        this.yl = proyectado.geti(1);

    }

      public void superior() {
        Vertice proyectado = this.proyeccion(Matriz.po(Matriz.ejes[1]));
        proyectado.vertice[1] = proyectado.vertice[2];
        //proyectado.vxm(Matriz.w2v(-15, -3, 15, 27, 0, 0, 1, 1));
         proyectado.vxm(Matriz.w2v(-18, 20, 13,50, 0, 0, 1, 1));
        this.xs = proyectado.geti(0);
        this.ys = proyectado.geti(1);
    }

    public void perspectiva() {
        Vertice proyectado = this.proyeccion(Matriz.pp(45));
        //proyectado.vxm(Matriz.w2v(-20, -20, 20,20, 0, 0, 1, 1));
        proyectado.vxm(Matriz.w2v(-30, -30, 30,30, 0, 0, 1, 1));
        this.xp = proyectado.geti(0);
        this.yp = proyectado.geti(1);

    }

    public void settdimension(int ancho, int largo) {
        this.ancho = ancho;
        this.largo = largo;
    }

  

    public double getxf() {
        return this.xf;
    }

    public double getxl() {
        return this.xl;
    }

    public double getxs() {
        return this.xs;
    }

    public double getxp() {
        return this.xp;
    }

    public double getyp() {
        return this.yp;
    }

    public double getyf() {
        return this.yf;
    }

    public double getyl() {
        return this.yl;
    }

    public double getys() {
        return this.ys;
    }

    public void random() {
        for (int i = 0; i < this.vertice.length; i++) {
            this.vertice[i] = Math.random();
        }
        this.vertice[3] = 1;
    }
}

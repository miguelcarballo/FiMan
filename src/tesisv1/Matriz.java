/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisv1;

/**
 *
 * @author Owner
 */
public class Matriz {

    private int dim;
    private double[][] matriz;
    final static double[][] ejes = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}};

    public Matriz(int dimension) {
        this.dim = dimension;
        matriz = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == j) {
                    this.setvalue(i, j, 1);
                } else {
                    this.setvalue(i, j, 0);
                }
            }
        }
    }

    public void setvalue(int i, int j, double val) {
        this.matriz[i][j] = val;
    }

    public double getvalue(int i, int j) {
        return this.matriz[i][j];
    }

    public int getdim() {
        return this.dim;
    }

    static Matriz mxm(Matriz m1, Matriz m2) {

        Matriz m = new Matriz(m1.getdim());
        double res = 0;

        for (int i = 0; i < m.getdim(); i++) {
            for (int j = 0; j < m.getdim(); j++) {
                for (int i1 = 0; i1 < m1.getdim(); i1++) {
                    res = res + m1.getvalue(i, i1) * m2.getvalue(i1, j);
                }
                m.setvalue(i, j, res);
                res = 0;
            }
        }
        return m;
    }

    public Matriz mxm(Matriz m2) {

        Matriz m = new Matriz(dim);
        double res = 0;

        for (int i = 0; i < m.getdim(); i++) {
            for (int j = 0; j < m.getdim(); j++) {
                for (int i1 = 0; i1 < dim; i1++) {
                    res = res + this.getvalue(i, i1) * m2.getvalue(i1, j);
                }
                m.setvalue(i, j, res);
                res = 0;
            }
        }
        return m;
    }

    /*static Matriz wtv(int xmin,int ymin, int xmax, int ymax){
    }*/
    static Matriz w2v(double xmin, double ymin, double xmax, double ymax, double umin, double vmin, double umax, double vmax) {
        Matriz res = new Matriz(4);

        res.setvalue(0, 0, (umax - umin) / (xmax - xmin));
        res.setvalue(1, 1, (vmax - vmin) / (ymax - ymin));

        return Matriz.trans(-xmin, -ymin, 0).mxm(res).mxm(Matriz.trans(umin, vmin, 0));
    }

    static Matriz trans(double dx, double dy, double dz) {
        Matriz m = new Matriz(4);

        m.setvalue(3, 0, dx);
        m.setvalue(3, 1, dy);
        m.setvalue(3, 2, dz);
        return m;
    }

    static Matriz rot(double[] n, double tita) {
        Matriz m = new Matriz(n.length);
        double cos = Math.cos(tita);
        double sen = Math.sin(tita);
        m.setvalue(0, 0, n[0] * n[0] * (1 - cos) + cos);
        m.setvalue(1, 1, n[1] * n[1] * (1 - cos) + cos);
        m.setvalue(1, 0, n[0] * n[1] * (1 - cos) - n[2] * sen);
        m.setvalue(0, 1, n[0] * n[1] * (1 - cos) + n[2] * sen);

        if (m.getdim() == 4) {
            m.setvalue(2, 2, n[2] * n[2] * (1 - cos) + cos);
            m.setvalue(0, 2, n[0] * n[2] * (1 - cos) - n[1] * sen);
            m.setvalue(2, 0, n[0] * n[2] * (1 - cos) + n[1] * sen);
            m.setvalue(2, 1, n[1] * n[2] * (1 - cos) - n[0] * sen);
            m.setvalue(1, 2, n[2] * n[1] * (1 - cos) + n[0] * sen);
        }

        return m;

    }

    static Matriz sca(double[] n, double k) {
        Matriz m = new Matriz(n.length);
        m.setvalue(0, 0, 1 + (k - 1) * n[0] * n[0]);
        m.setvalue(1, 1, 1 + (k - 1) * n[1] * n[1]);
        m.setvalue(1, 0, (k - 1) * n[0] * n[1]);
        m.setvalue(0, 1, (k - 1) * n[0] * n[1]);

        if (m.getdim() == 4) {
            m.setvalue(2, 2, 1 + (k - 1) * n[2] * n[2]);
            m.setvalue(2, 0, (k - 1) * n[0] * n[2]);
            m.setvalue(0, 2, (k - 1) * n[0] * n[2]);
            m.setvalue(2, 1, (k - 1) * n[1] * n[2]);
            m.setvalue(1, 2, (k - 1) * n[1] * n[2]);
        }
        return m;
    }

    static Matriz po(double[] n) {
        return sca(n, 0);
    }

    static Matriz pp(double d) {
        Matriz m = new Matriz(4);
        m.matriz[3][3] = 0;
        m.matriz[2][3] = 1 / d;

        return m;
    }

    static Matriz ref(double[] n) {
        return sca(n, -1);
    }

    public void list() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print(this.matriz[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void random() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                this.setvalue(i, j, (int) (Math.random() * 100));
            }
        }
    }
}

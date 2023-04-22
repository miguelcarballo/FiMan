/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisv1;

/**
 *
 * @author Owner
 */
public class Vlist {

    private vnodo head;
    private vnodo tail;
    private int size;

    public Vlist() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void setorigen(double xo, double yo, double zo){
    if(!vacio()){
    vnodo temp=this.head;
    for(int i=0;i<this.size;i++)
    {
    temp.v.origen(xo, yo, zo);
    temp=temp.next;
    }
    }

    }

    public void setdimension(int ancho, int largo) {
        if (!vacio()) {
            vnodo temp = this.head;
            for (int i = 0; i < this.size; i++) {
                temp.v.settdimension(ancho, largo);
                temp = temp.next;
            }
        }
    }

    public boolean vacio() {
        return (this.head == null);
    }

    public void addv(Vertice v) {
        if (vacio()) {
            vnodo nuevo = new vnodo(v);
            this.head = nuevo;
            this.tail = nuevo;
            nuevo = null;

        } else {
            vnodo nuevo = new vnodo(v);
            this.tail.next = nuevo;
            this.tail = nuevo;
            nuevo = null;
        }
        this.size++;
    }

    public Vertice getvert(int n) {
        if (!vacio()) {
            vnodo temp = this.head;
            for (int i = 0; i < this.size; i++) {
                if (i == n) {
                    return temp.v;
                }
                temp = temp.next;
            }

        }
        return null;
    }

    public void list() {
        if (!vacio()) {
            vnodo temp = this.head;
            for (int i = 0; i < this.size; i++) {
                temp.v.list();
                temp = temp.next;
            }
        }

    }

    public double getxc() {
        double res = 0;
        if (!vacio()) {
            vnodo temp = this.head;

            for (int i = 0; i < this.size; i++) {
                res = res + temp.v.geti(0);
                temp = temp.next;
            }
        }
        return res / this.size;
    }

    public double getyc() {
        double res = 0;
        if (!vacio()) {
            vnodo temp = this.head;

            for (int i = 0; i < this.size; i++) {
                res = res + temp.v.geti(1);
                temp = temp.next;
            }
        }
        return res / this.size;
    }

    public double getzc() {
        double res = 0;
        if (!vacio()) {
            vnodo temp = this.head;

            for (int i = 0; i < this.size; i++) {
                res = res + temp.v.geti(2);
                temp = temp.next;
            }
        }
        return res / this.size;
    }

 
    public void transformacion(Matriz m) {

        if (!vacio()) {
            vnodo temp = this.head;
            for (int i = 0; i < this.size; i++) {
                temp.v.vxm(m);
                temp = temp.next;
            }

        }
    }

    public void proyectar(int sel) {
        if (!vacio()) {
            vnodo temp = this.head;
         
                if(sel==1){
                 for (int i = 0; i < this.size; i++) {
                      temp.v.frontal();
                      temp = temp.next;
                 }
                }
                else if(sel==2){
                   for (int i = 0; i < this.size; i++) {
                       temp.v.lateral();
                       temp = temp.next;
                   }
                }
                else if(sel==3){
                     for (int i = 0; i < this.size; i++) {
                      temp.v.superior();
                      temp = temp.next;
                   }
                }
                else if(sel==4){
                    for (int i = 0; i < this.size; i++) {
                    temp.v.perspectiva();
                    temp = temp.next;
                   }
                }
        }
    }
}

class vnodo {

    vnodo next;
    Vertice v;

    public vnodo(Vertice v) {
        this.v = v;
        this.next = null;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisv1;

/**
 *
 * @author Owner
 */
public class Arista {

    private Vertice v1;
    private Vertice v2;

    public Arista(Vertice v1, Vertice v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public void list() {
        System.out.println("V1");
        this.v1.list();
        System.out.println("V2");
        this.v2.list();
    }

    public Vertice getv1() {
        return this.v1;
    }

    public Vertice getv2() {
        return this.v2;
    }
}

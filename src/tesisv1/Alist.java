/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tesisv1;

/**
 *
 * @author Owner
 */
public class Alist {
    private anodo head;
   private anodo tail;
   public int size;

   public Alist(){
   this.head=null;
   this.tail=null;
   this.size=0;}

   public boolean vacio(){
   return(this.head==null);}

   public Arista getarista(int n){
       if(!vacio()){
   anodo temp=this.head;
   for(int i=0; i<this.size;i++)
   {   if(i==n)return temp.a;
       temp=temp.next;
   }

       }
       return null;
   }

   public void adda(Vertice v1, Vertice v2){
       if(vacio()){
       anodo nuevo= new anodo(v1,v2);
       this.head=nuevo;
       this.tail=nuevo;
       nuevo=null;
       }
       else{
       anodo nuevo=new anodo(v1,v2);
       this.tail.next=nuevo;
       this.tail=nuevo;
       nuevo=null;
       }
       this.size++;
   }





  public void list(){
       if(!vacio()){
           anodo temp=this.head;
   for (int i=0;i<this.size;i++){
       temp.a.list();
           temp=temp.next;}
   }

   }




}

class anodo {
    anodo next;
    Arista a;

    public anodo(Vertice v1,Vertice v2){
        this.a=new Arista(v1,v2);
        this.next=null;
    }

}


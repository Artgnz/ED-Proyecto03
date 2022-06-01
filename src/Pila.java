package src.edd;
import java.util.Iterator;

public class Pila<T> extends PushPop<T>{
    
    
    /**
     * Metodo que agrega un elemento al inicio
     * @param elemento
     */
    public void push(T elemento){
        if(elemento == null){
            throw new IllegalArgumentException("");
        }
        Nodo aux = new Nodo(elemento);
        if(isEmpty()){
            this.cabeza=ultimo=aux;
            longi++;
            return ;
        }
        aux.siguiente = cabeza;
        cabeza = aux;
        longi ++;

    }

    /**
     * Regresa un clon de la estructura.
     * 
     * @return un clon de la estructura.
     */
    public Pila<T> clone(){
        Pila<T> nueva = new Pila<T>();
        Pila<T> auxiliar = new Pila<T>();
        if (this.isEmpty()) {
            return nueva;
        }
        auxiliar.push(this.cabeza.elemento);
        Nodo n = this.cabeza;
        while (n.siguiente != null) {
           auxiliar.push(n.siguiente.elemento);
           n = n.siguiente;
        }
        nueva.push(auxiliar.cabeza.elemento);
        n = auxiliar.cabeza;
        while (n.siguiente != null) {
           nueva.push(n.siguiente.elemento);
           n = n.siguiente;
        }
        return nueva;

    }

    /**
     * Metodo para imprimir una pila
     * @return String
     */
    public String toString(){
        if (this.isEmpty()) {
            return "";
        }
        Lista<T> lista = new Lista<T>();
        lista.add(this.cabeza.elemento);
        Nodo n=this.cabeza;
        while (n.siguiente != null) {
            lista.agregaFinal(n.siguiente.elemento);
            n = n.siguiente;
        } 
        lista.reverse();
        return lista.toString();
    }

}




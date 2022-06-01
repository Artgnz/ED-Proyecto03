package src.edd;

public class Cola<T> extends PushPop<T> {
    // Agregar al final.
    public void push(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("");
        }
        Nodo aux = new Nodo(elemento);
        if (isEmpty()) {
            this.cabeza = ultimo = aux;
            longi++;
            return;
        }
        ultimo.siguiente = aux;
        ultimo = aux;
        longi++;

    }

    /**
     * Regresa un clon de la estructura.
     * 
     * @return un clon de la estructura.
     */
    public Cola<T> clone() {
        Cola<T> nueva = new Cola<T>();
        if (this.isEmpty()) {
            return nueva;
        }
        nueva.push(this.cabeza.elemento);
        Nodo n = this.cabeza;
        while (n.siguiente != null) {
            nueva.push(n.siguiente.elemento);
            n = n.siguiente;
        }
        return nueva;

    }

    /**
     * Metodo para imprimir una cola
     * @return
     */
    public String toString() {
        if (this.isEmpty()) {
            return "";
        }
        String regreso = this.cabeza.elemento.toString();
        Nodo n = this.cabeza;
        while (n.siguiente != null) {
            regreso += ", " + n.siguiente.elemento.toString();
            n = n.siguiente;
        }
        return regreso;
    }

}

package src.edd;
import src.edd.ArbolBinarioBusqueda;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 * <li>Todos los vértices son NEGROS o ROJOS.</li>
 * <li>La raíz es NEGRA.</li>
 * <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 * <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 * <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 * mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {
    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
    */
    protected class VerticeRojinegro extends Vertice {
        public Color color;
    
        public VerticeRojinegro(T elemento){
            super(elemento);
            color = Color.ROJO;
        }
        
        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * 
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            if (this == null)
                return "";
            if (this.color == Color.ROJO)
                return "\u001B[31m" + this.elemento.toString() + "\u001B[0m";
            return "\u001B[32m" + this.elemento.toString() + "\u001B[0m";
        }
        
        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * 
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro) o;
            return color == vertice.color && super.equals(o);
        }

    }

    VerticeRojinegro ultimoAgregado;

    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  rojinegro.
     */
    public ArbolRojinegro(Collection<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    protected VerticeRojinegro convertirRojiNegro(VerticeArbolBinario<T> vertice) {
        if(vertice==null){
            return null;
        }
        return (VerticeRojinegro) vertice;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * 
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *                            VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro aux = (VerticeRojinegro) vertice;
        return aux.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void add(T elemento) {
        if (elemento != null) {
            super.add(elemento);

            // ToDo ... Conseguir el ultimoAgregado
            VerticeRojinegro v = convertirRojiNegro(ultimoAgregado);
            v.color = Color.ROJO;
            rebalancea(v);
        }

    }

    /**
     * Método que inserta un elemento en el árbol AVL
     * 
     * @param elemento
     */
    public void insertar(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("No se puede insertar un elemento vacío");
        }
        // si el arbol no tiene elementos, el elemento a insertar será la raiz.
        if (isEmpty()) {
            raiz = new VerticeRojinegro(elemento);
            convertirRojiNegro(raiz).color=Color.NEGRO;
            // aumentamos el contador de los elementos
            elementos++;
        } else {
            insertarAux(convertirRojiNegro(raiz), elemento);
        }
    }

    /**
     * Método auxiliar de insertar
     * 
     * @param vertice
     * @param elemento
     * @return
     */
    private void insertarAux(VerticeRojinegro vertice, T elemento) {
        if (!elemento.equals(vertice.elemento)) {
            if (elemento.compareTo(vertice.elemento) < 0) {
                if (vertice.hayIzquierdo()) {
                    insertarAux(convertirRojiNegro(vertice.izquierdo), elemento);
                }else {
                    // hacemos un nuevo vértice AVL con el elemento
                    VerticeRojinegro nuevo = new VerticeRojinegro(elemento);
                    // el hijo izquierdo del vértice será el nuevo vértice
                    vertice.izquierdo = nuevo;
                    // el padre del vértice insertado será el vértice original
                    nuevo.padre = vertice;
                    // aumentamos el contador de los elementos
                    elementos++;
                    rebalancea(nuevo);
                }
            }else {
                // si el vértice tiene hijo derecho
                if (vertice.hayDerecho()) {
                    insertarAux(convertirRojiNegro(vertice.derecho), elemento);
                } else {
                    VerticeRojinegro nuevo = new VerticeRojinegro(elemento);
                    vertice.derecho = nuevo;
                    nuevo.padre = vertice;
                    elementos++;
                    rebalancea(nuevo);
                }
            }
            //System.out.println(ultimoAgregado);
            
            return;
        }
        return;
    }

    private void rebalancea(VerticeRojinegro v){
        if(v!=null){
            //Caso 1
            if(!v.hayPadre()){
                v.color=Color.NEGRO;
                System.out.println(v.color);
            }else if(convertirRojiNegro(v.padre).color==Color.NEGRO){
                return;
            }else if(convertirRojiNegro(v.padre).color==Color.ROJO){
                if(hayTio(v)){
                    if(getTio(v).color==Color.ROJO){
                        getPadre(v).color=Color.NEGRO;
                        getTio(v).color=Color.NEGRO;
                        getAbuelo(v).color=Color.ROJO;
                        rebalancea(getAbuelo(v));
                    }else if(getTio(v).color==Color.NEGRO){
                        if(cruzadosDer(v)){
                           VerticeRojinegro p= getPadre(v);
                           rotar(p, false);
                           rebalancea(p);
                           
                        }else if(cruzadosIzq(v)){
                            VerticeRojinegro p= getPadre(v);
                            rotar(p, true);
                            rebalancea(p);
                        }else{
                            getPadre(v).color=Color.NEGRO;
                            getAbuelo(v).color=Color.ROJO;
                            if(isIzquierdo(v)){
                                rotar(getAbuelo(v), false);
                            }else{
                                rotar(getAbuelo(v), true);
                            }
                            return;
                        }
                    }
                }else{
                    if(cruzadosDer(v)){
                        VerticeRojinegro p= getPadre(v);
                        rotar(p, false);
                        rebalancea(p);
                        
                     }else if(cruzadosIzq(v)){
                         VerticeRojinegro p= getPadre(v);
                         rotar(p, true);
                         rebalancea(p);
                     }else{
                         getPadre(v).color=Color.NEGRO;
                         getAbuelo(v).color=Color.ROJO;
                         if(isIzquierdo(v)){
                             System.out.println("vertice"+v);
                             System.out.println("Padre "+getPadre(v));
                             System.out.println("rotando"+getAbuelo(v));
                             rotar(getAbuelo(v), false);
                         }else{
                             rotar(getAbuelo(v), true);
                         }
                         return;
                     }
                }
            }
        }
        return;
    }

    //Saber si un vertice tiene 2 hijos
    private boolean hay2hijos(VerticeRojinegro v){
        if(v!=null){
            if(v.hayDerecho()&&v.hayIzquierdo()){
                return true;
            }
        }
        return false;
    }


    private boolean hayAbuelo(VerticeRojinegro v){
        if(v!=null){
            if(v.hayPadre()&&v.padre.hayPadre()){
                return true;
            }
        }
        return false;
    }

    private VerticeRojinegro getAbuelo(VerticeRojinegro v) {
        if(v!=null){
            if(hayAbuelo(v)){
                return convertirRojiNegro(v.padre.padre);
            }
        }
        return null;
    }

    private VerticeRojinegro getPadre(VerticeRojinegro v) {
        if(v!=null){
            if(v.hayPadre()){
                return convertirRojiNegro(v.padre);
            }
        }
        return null;
    }

    private boolean hayTio(VerticeRojinegro v) {
        if(v!=null){
            if(hayAbuelo(v)){
                if(isDerecho(convertirRojiNegro(v.padre))){
                    return v.padre.padre.hayIzquierdo();
                }else{
                    return v.padre.padre.hayDerecho();
                }
            }
        }
        return false;
    }

    private VerticeRojinegro getTio(VerticeRojinegro v) {
        if(v!=null){
            if(hayTio(v)){
                if(isDerecho(convertirRojiNegro(v.padre))){
                    return convertirRojiNegro(v.padre.padre.izquierdo);
                }else{
                    return convertirRojiNegro(v.padre.padre.derecho);
                }
            }
        }
        return null;
    }

    private boolean isIzquierdo(VerticeRojinegro v) {
        if(v!=null){
            if(v.hayPadre()){
                if(v.padre.hayIzquierdo()&&v.padre.izquierdo.equals(v)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDerecho(VerticeRojinegro v) {
        if(v!=null){
            if(v.hayPadre()){
                if(v.padre.hayDerecho()&&v.padre.derecho.equals(v)){
                    return true;
                }
            }
        }
        return false;
    }

    

    private boolean hayHermano(VerticeRojinegro v) {
        if(v!=null){
            if(v.hayPadre()){
                if(isDerecho(v)){
                    return v.padre.hayIzquierdo();
                }else{
                    return v.padre.hayDerecho();
                }
            }
        }
        return false;
    }

    // Si es derecho -> devuelve el izq
    // Si es Izq -> devuelve el der
    private VerticeRojinegro getHermano(VerticeRojinegro v) {
        if(v!=null){
            if(hayHermano(v)){
                if(isDerecho(v)){
                    return convertirRojiNegro(v.padre.izquierdo);
                }else{
                    return convertirRojiNegro(v.padre.derecho);
                }
            }
        }
        return null;
    }

    //v.padre es izq y v es derecho.
    private boolean cruzadosIzq(VerticeRojinegro v){
        if(v!=null){
            if(v.hayPadre()){
                if(isIzquierdo(convertirRojiNegro(v.padre))&&isDerecho(v)){
                    return true;
                }
            }
        }
        return false;
    }

    // v.padre es der y v es izq.
    private boolean cruzadosDer(VerticeRojinegro v) {
        if(v!=null){
            if(hayAbuelo(v)){
                if(isDerecho(convertirRojiNegro(v.padre))&&isIzquierdo(v)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isRojo(VerticeRojinegro v){
        if(v==null){
            return false;
        }
        if(v.color==Color.ROJO){
            return true;
        }
        return false;
    }

    private boolean isNegro(VerticeRojinegro v){
        if(v==null){
            return true;
        }
        if(v.color==Color.NEGRO){
            return true;
        }
        return false;
    }



    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    public boolean delete(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("No se puede eliminar un elemento vacio");
        } else if(isEmpty()){
            throw new IllegalCallerException("No se puede eliminar un elemento de un arbol vacio");
        }else{
            VerticeRojinegro aux=convertirRojiNegro(search2(raiz, elemento));//el vertice a eliminar
            if(aux==null){
                throw new IllegalCallerException("No se puede eliminar un elemento que no está en el árbol");
            }else{
                if(aux.color==Color.ROJO){
                    super.delete(raiz,elemento);
                }else if(!aux.hayDerecho()&&!aux.hayIzquierdo()){
                    if(elemento.equals(raiz.elemento)){
                        raiz=null;
                        elementos=0;
                    }else{
                        rebalancea2(aux);
                        super.delete(raiz, elemento);
                    }
                }else if(!aux.hayDerecho()&&convertirRojiNegro(aux.izquierdo).color==Color.ROJO){
                        convertirRojiNegro(aux.izquierdo).color=Color.NEGRO;
                        super.delete(raiz,elemento);
                }else if(!aux.hayIzquierdo()&&convertirRojiNegro(aux.derecho).color==Color.ROJO){
                    convertirRojiNegro(aux.derecho).color=Color.NEGRO;
                    super.delete(raiz,elemento);
                }else if(!aux.hayDerecho()){
                    VerticeRojinegro h=convertirRojiNegro(aux.izquierdo);
                    super.delete(raiz, elemento);
                    rebalancea2(h);
                }else if(!aux.hayIzquierdo()){
                    VerticeRojinegro h=convertirRojiNegro(aux.derecho);
                    super.delete(raiz, elemento);
                    rebalancea2(h);
                }else{
                    Vertice aux2 = aux.derecho; // aux2 es el hijo derecho del nodo a eliminar
                    while (aux2.hayIzquierdo()) {
                        aux2 = aux2.izquierdo; // bajamos lo mas que se pueda hacia la izquierda hasta que no tenga hijo izquierdo
                    }
                    T elem = aux2.elemento; // guardamos el elemento del hijo derecho del nodo a aliminar
                    delete(aux2, elem); // aplicamos la función deleteAux para eliminar el elemento que se convertirá en la raiz luego de eliminar a aux
                    aux.elemento = elem; // hacemos el intercambio de referencias
                }
            }
        }
        return true;
    }

    public void rebalancea2(VerticeRojinegro v){
        //Caso 1
        if(!v.hayPadre()){
            return;
        }
        VerticeRojinegro p=getPadre(v);
        VerticeRojinegro w=getHermano(v);
        //Caso 2
        if(isRojo(w)){
            p.color=Color.ROJO;
            if(isIzquierdo(v)){
                rotar(p, true);
            }else{
                rotar(p, false);
            }
            p=getPadre(v);
            w=getHermano(v);
        }else{
            //CAso 3
            if(isNegro(p)&&isNegro(convertirRojiNegro(izquierdo(w)))&&isNegro(convertirRojiNegro(derecho(w)))){
                if(w!=null){
                    w.color=Color.ROJO;
                }
                rebalancea2(p);
            }
        }

        //Caso 4
        if(p.color==Color.ROJO){
            p.color=Color.NEGRO;
            if(w!=null){
                w.color=Color.ROJO;
            }
            return;
        }

        //Caso 5
        if(isDerecho(v)&&isRojo(convertirRojiNegro(derecho(w)))&&isNegro(convertirRojiNegro(izquierdo(w)))){
            p.color=Color.ROJO;
            if(w!=null){
                w.color=Color.NEGRO;
            }
            rotar(w,true);
        }else if(isIzquierdo(v)&&isRojo(convertirRojiNegro(izquierdo(w)))&&isNegro(convertirRojiNegro(derecho(w)))){
            p.color=Color.ROJO;
            w.color=Color.NEGRO;
            rotar(w,false);
            p=getPadre(v);
            w=getHermano(v);
        }

        //Caso 6
        if(isDerecho(v)&&isRojo(convertirRojiNegro(w.izquierdo))){
            Color c=p.color;
            p.color=Color.NEGRO;
            if(izquierdo(getTio(v))!=null){
                izquierdo(getTio(v)).color=Color.NEGRO;
            }
            w.color=c;
            rotar(p,false);
            return;
        }else if(isIzquierdo(v)&&isRojo(convertirRojiNegro(derecho(w)))){
            Color c=p.color;
            p.color=Color.NEGRO;
            if(izquierdo(getTio(v))!=null){
                izquierdo(getTio(v)).color=Color.NEGRO;
            }
            //if(w!=null){
                w.color=c;
            //}
            rotar(p,true);
            return;
        }
    }


     /**
     * Método que rota el vértice para rebalancear el árbol
     * 
     * @param vertice
     * @param izquierda
     * @return
     */
    public void rotar(VerticeRojinegro vertice, boolean izquierda) {
        Vertice r=null;
        if(vertice.equals(raiz)){
            r=vertice;
        }
        // creamos un nuevo verticeRojinegro auxiliar
        VerticeRojinegro aux;
        // si el vértice es distinto de null
        if (vertice != null) {
            // y la variable izquierda se inicializa en true
            if (izquierda) {
                // creamos un nuevo vértice padre distinto de null
                VerticeRojinegro padre = null;
                // si el vértice tiene padre
                if (vertice.hayPadre()) {
                    padre = convertirRojiNegro(vertice.padre);
                }
                // hacemos que aux sea el hijo derecho del vértice
                aux = derecho(vertice);
                // hacemos que el hijo derecho del vértice sea el hijo izquierdo de aux
                vertice.derecho = izquierdo(aux);
                // si el hijo derecho del vértice es distinto de null
                if (vertice.derecho != null) {
                    // hacemos que el padre del hijo derecho del vértice sea el vértice
                    vertice.derecho.padre = vertice;
                }
                // si aux es distinto de null
                if (aux != null) {
                    // hacemos que el hijo izquierdo de aux sea el vértice
                    aux.izquierdo = vertice;
                    // hacemos que el padre del vértice sea aux
                    vertice.padre = aux;
                }
                // si el vértice aux es distinto de null
                if (aux != null) {
                    // hacemos que el padre de aux sea el padre del vértice
                    aux.padre = padre;
                }
                // si el vértice es distinto de null
                if (padre != null) {
                    // si el padre tiene hijo derecho y es igual que el vértice
                    if (padre.hayDerecho() && padre.derecho.elemento.equals(vertice.elemento)) {
                        // el hijo derecho del padre será aux
                        padre.derecho = aux;
                    } else {
                        // el hijo izquierdo del padre será aux
                        padre.izquierdo = aux;
                    }
                }

            }else {
                // creamos un nuevo verticeRojinegro padre que inicializa en null
                VerticeRojinegro padre = null;
                // si el vértice tiene padre
                if (vertice.hayPadre()) {
                    // creamos un nuveo vértice que sea padre del vértice original
                    padre = convertirRojiNegro(vertice.padre);
                }
                // hacemos que aux sea el hijo izquierdo del vértice
                aux = izquierdo(vertice);
                // hacemos que el hijo izquierdo del vértice sea el hijo derecho de aux
                vertice.izquierdo = derecho(aux);
                // si el hijo izquierdo del vértice es distinto de null
                if (vertice.izquierdo != null) {
                    // hacemos que el padre del hijo izquierdo del vértice sea el vértice
                    vertice.izquierdo.padre = vertice;
                }
                // si aux es distinto de null
                if (aux != null) {
                    // hacemos que el hijo derecho de aux sea el vértice
                    aux.derecho = vertice;
                    // hacemos que el padre del vértice sea aux
                    vertice.padre = aux;
                }
                // si el vértice aux es distinto de null
                if (aux != null) {
                    // hacemos que el padre de aux sea el padre del vértice
                    aux.padre = padre;
                }
                // si el vértice padre es distinto de null
                if (padre != null) {
                    // si el padre tiene hijo derecho y es igual que el vértice
                    if (padre.hayDerecho() && padre.derecho.elemento.equals(vertice.elemento)) {
                        // el hijo derecho del padre será aux
                        padre.derecho = aux;
                    } else {
                        // el hijo izquierdo del padre será aux
                        padre.izquierdo = aux;
                    }
                }

            }
            if(r!=null){
                //aux.color=Color.NEGRO;
                this.raiz=aux;
            }
            return;
        }
        return;

    }

    /**
     * Método que genera un nodo de tipo VerticeRojinegro para devolver el hijo derecho de
     * v
     * 
     * @param v
     * @return
     */
    public VerticeRojinegro derecho(VerticeRojinegro v) {
        if (v != null) {
            return convertirRojiNegro(v.derecho);
        } else {
            return null;
        }
    }

    /**
     * Método que genera un nodo de tipo VerticeRojinegro para devolver el hijo izquierdo
     * de v
     * 
     * @param v
     * @return
     */
    public VerticeRojinegro izquierdo(VerticeRojinegro v) {
        if (v != null) {
            return convertirRojiNegro(v.izquierdo);
        } else {
            return null;
        }
    }
    
}
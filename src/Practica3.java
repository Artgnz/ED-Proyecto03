package src.edd;
import java.util.Iterator;
import java.util.Comparator;

import src.edd.Cola;
import src.edd.Pila;
public class Practica3 {
    
    private static int s=0;
    //################################################################################################################################################################
    /**
     * Método que dada una lista de enteros y un entero N, deberá encontrar la
     * pareja de números en la
     * lista tal que la suma de estos, sea la más cercana a N
     * 
     * @param lista
     * @param n
     */
    public static void sumaCercana(Lista<Integer> lista, int n) {

        System.out.println("La lista antes de ordenar " + lista);
        // ordenamos la lista de menor a mayor
        Lista<Integer> listaOrdenada = lista.mergeSort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        // pasamos la lista a un arreglo
        System.out.println("La lista ordenada " + listaOrdenada);
        int[] arreglo = new int[listaOrdenada.size()];
        while (!listaOrdenada.isEmpty()) {
            arreglo[listaOrdenada.size() - 1] = listaOrdenada.pop();
        }
        // inicializamos pointers
        int variableIzq = 0;
        int variableDer = arreglo.length - 1;
        int diferencia = Integer.MAX_VALUE;
        Integer num1 = 0;
        Integer num2 = 0;
        while (variableIzq < variableDer) {
            int suma = arreglo[variableIzq] + arreglo[variableDer];
            int queTanCercano = Math.abs(suma - n);
            if (diferencia > queTanCercano) {
                diferencia = queTanCercano;
                num1 = arreglo[variableIzq];
                num2 = arreglo[variableDer];
            }
            if (suma > n) {
                variableDer--;
            }
            if (suma < n) {
                variableIzq++;
            }
            if (suma == n) {
                num1 = arreglo[variableIzq];
                num2 = arreglo[variableDer];
                break;
            }
        }
        System.out.println("La pareja de números cuya suma es más cercana a " + n + " es: " + num1 + " y " + num2);

    }

    //#####################################################################################################################################################################################################################################
    /**
     * Metodo para obtener todas las posibles combinaciones de los caracteres de una cadena
     * @param cadena
     */
    public static void permutaCadena(String cadena){
        Pila<Character> pila=new Pila<Character>();
        Cola<Character> cola=new Cola<Character>();
        for(int i=0; i<cadena.length();i++){
            cola.push(cadena.charAt(i));
        }
        permuta(0,pila,cadena.length(),cola);
    }
    
    /**
     * Metodo auxiliar del método permutaCadena
     * @param numRecursion nivel en el arbol
     * @param pila 
     * @param numCaracteres todo lo que podemos bajar en el arbol hasta encontrar una solucion posible
     * @param cola
     */
    private static void permuta(int numRecursion, Pila<Character> pila, int numCaracteres, Cola<Character> cola){
        if(numRecursion==numCaracteres){//cuando ya estamos en el ultimo nivel del arbol
            System.out.println(pila);//mostramos la solución generada
        }else{
            //hacia los lados
            for(int i=numRecursion; i<numCaracteres;i++){//desde el nivel en que estamos hasta el ultimo nivel al que podemos llegar en el arbol porque el numero de permutaciones al bajar en el arbol va disminuyendo
                pila.push(cola.pop());

                permuta(numRecursion+1,pila, numCaracteres,cola);//hacemos llamada recursiva, bajando un nivel en el arbol

                cola.push(pila.pop()); //regresamos los caracteres a la cola cuando regresamos un nivel en el arbol
            }

        }
    }

    //###################################################################################################################################################################################################################################
    /**
     * Método para encontrar todos los posibles tableros en los que se colocan n reinas sin que se ataquen
     * @param n el numero de reinas
     */
    public static void N_reinas(int n){
        //Tablero donde colocaremos a las reinas
        String[][] tablero=new String[n][n];
        //Arreglo que representa las columnas para guardar la posición de cada reina en las filas
        int[] posFilas=new int[n];
        int[] copia=new int[n];
        //Llenamos los arreglos
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                tablero[i][j]="- ";
            }
            posFilas[i]=-1;
        }
        recursiva(0,posFilas, tablero);
    }

    /**
     * Metodo auxiliar del método N_Reinas
     * @param nivel
     * @param posFilas
     * @param tablero
     */
    private static void recursiva(int nivel, int[] posFilas, String[][] tablero){
        int numReinas=posFilas.length;
        //System.out.println(numReinas);
        //System.out.println(nivel);
        if(nivel==numReinas){//Caso base: encontramos una solución cuando el nivel del arbol es el ultimo, cuando ya pudimos poner a todas las reinas
            //System.out.println("base");
            s++;
            System.out.println(s+" ############################");
            for(int i=0;i<numReinas;i++){
                tablero[i][posFilas[i]]="R ";
            }
            for(int i=0; i<numReinas;i++){
                for(int j=0;j<numReinas;j++){
                    System.out.print(tablero[i][j]);
                }
                System.out.println();
            }
            
            for(int i=0;i<numReinas;i++){
                tablero[i][posFilas[i]]="- ";
            }
        
        }else{
            for(posFilas[nivel]=0;posFilas[nivel]<numReinas;posFilas[nivel]++){//nos movemos a través de los nodos en el mismo nivel del arbol
                if(validaMovimiento(posFilas,nivel)){//validamos que el movimiento se pueda realizar
                    //System.out.println("Recursiva");
                    recursiva(nivel+1, posFilas, tablero);//con cada recursión aumentamos un nodo hacia abajo en el arbol
                }
            }
        }
    } 
    /**
     * Metodo auxiliar del metodo recursiva para el método N_reinas
     * @param posFilas arreglo para la posición de las filas de cada reina
     * @param nivel el nivel del arbol
     * @return
     */
    private static boolean validaMovimiento(int[] posFilas, int nivel){
        for(int i=0;i<nivel;i++){
            //Si las reinas están en la misma fila o en la misma diagonal
            if(posFilas[i]==posFilas[nivel]||Math.abs(nivel-i)==Math.abs(posFilas[nivel]-posFilas[i])){
                return false;
            }
        }
        return true;
    }

    //###############################################################################################################################################################################################################################################################3

    /**
     * Metodo privado auxiliar de PrimosQueSuman
     * @param formando lista con las soluciones parciales
     * @param listaPrimos arreglo de los numeros primos
     * @param sumaActual la suma actual inicial
     * @param sumacte la suma que queremos formar
     * @param k numero de sumandos requeridos
     * @param nivel el nivel en el que estamos del arbol
     */
    private static void suma(Lista<Integer> formando, Integer[] listaPrimos, int sumaActual, int sumacte, int k, int nivel){
        if (formando.size() == k) {//si estamos en el ultimo nivel del arbol
            if (sumaActual == sumacte) {//comparamos la suma que llevamos con la suma requerida
                //System.out.println("Caso base");
                System.out.println(formando);
            }
            return;
        }else{
            for (int i = nivel; i < listaPrimos.length; i++) {//reecorremos los nodos del mismo nivel(en cada nivel)
                if (sumaActual + listaPrimos[i] > sumacte){//si la suma actual es mayor, ya no tiene caso recorrer todo lo que falta
                    break; 
                }
                sumaActual += listaPrimos[i];//sumamos el elemento de la posicion i
                //System.out.println("Suma actual: "+sumaActual);
                formando.add(listaPrimos[i]);//añadimos el elemento a la lista que estamos formando de soluciones parciales
                suma(formando, listaPrimos,sumaActual, sumacte,  k, i+1);//recursion
                sumaActual -= listaPrimos[i];//restamos lo que habiamos sumado
                formando.pop();//quitamos de la lista lo que le habíamos agregado
            }
        }
    }

    /**
     * Metodo para obtener n numeros primos mayores que primo que suman suma
     * @param suma
     * @param primo
     * @param n
     */
    public static void primosQueSuman(int suma, int primo, int n) {
        //Sacamos los numeros desde el 2 hasta suma
        Lista <Integer> primos=cribaEratostenes(suma);
        //quitamos los primos menores que primo
        for(int i=0;i<primos.size();i++){
            if(primos.elementoEnPos(0)<=primo){
                primos.eliminaEnPos(0);
            }
        }
        //System.out.println(primos);
        //Pasamos la lista de primos a un arreglo
        Integer[] arrPrimos=new Integer[primos.size()];
        Iterator<Integer> it=primos.iterator(); 
        for(int i=0;i<arrPrimos.length;i++){
            arrPrimos[i]=it.next();
        }
        //Lista en la que iremos formando las soluciones parciales
        Lista<Integer> formando=new Lista<Integer>();
        int sumaActual=0;
        suma(formando, arrPrimos, sumaActual, suma,  n,0);
    }
    
    /**
     * Metodo auxiliar para obtemner los numeros primos desde 2 hasta un número dado
     * @param n el numero limite
     * @return
     */
    public static Lista<Integer> cribaEratostenes(int n) {
        //Lista donde pondremos los numeros primos de 0 a n
        Lista<Integer> primos=new Lista<Integer>();
        //Ciclo para verificar cada numero de 2 a n
        for (int numero = 2; numero < n; numero++) {
            boolean esPrimo = true;
            // Ciclo for para tomar a los posibles divisores
            for (int divisor = 2; divisor <= numero / 2; divisor++) {
                // Condicional para ver si el número tiene divisores
                if (numero % divisor == 0) {
                    esPrimo = false; // Si el número tiene algún divisor, esPrimo cambia a false
                }
            }
            // Condicional que verifica si esPrimo se mantiene en true
            if (esPrimo) {
                primos.add(numero); // Si esPrimo se mantiene en true, imprimir el número
            }
        }
        return primos;
    }

    //#############################################################################################################################################################################################################################################################
    /**
     * Metodo para encontrar la raíz cuadrada de un numero usando búsqueda binaria
     * Margen de error: 1e − 5
     * @param n numero del que queremos saber su raíz
     */
    public static void sqrtBusqBin(double n){
        //Tomamos un intervalo de 0 a n
        double inicioRango=0;
        double finRango= n;
        //sacamos la mitad del intervalo
        double mitad=(inicioRango+finRango)/2;
        //Margen de error
        double e=0.00001;
        while(Math.abs((mitad*mitad)-n)>=e){//Con el margen de error definido
            //si la mitad al cuadrado es menor a n
            if(mitad*mitad<n){
                //actualizamos el inicio del rango a la mitad
                inicioRango=mitad;
            }else{//si la mitad al cuadrado es mayor a n
                //actualizamos el fin del intervalo para que se evalúe hasta la mitad
                finRango=mitad; 
            }
            //sacamos la mitad del nuevo intervalo
            mitad=(inicioRango+finRango)/2;
        }
        System.out.println(mitad);
    }

    //###################################################################################################################################################################################################################################
    public static void main(String[] args){
        System.out.println("********************************************************************");
        System.out.println("Prueba del metodo sumaCercana");
        Lista<Integer> prueba2 = new Lista<Integer>();
        prueba2.add(10);
        prueba2.add(1);
        prueba2.add(9);
        prueba2.add(7);
        prueba2.add(18);
        sumaCercana(prueba2, 13);
        System.out.println("********************************************************************");
        System.out.println("Prueba del método permutaCadena");
        System.out.println("Permutaciones de la cadena \"ABC\"");
        permutaCadena("ABC");
        System.out.println("********************************************************************");
        System.out.println("Prueba del metodo N_reinas con 4 reinas");
        N_reinas(4);
        System.out.println("********************************************************************");
        System.out.println("Prueba del metodo primosQueSuman con suma=28, primo=7 y n=2");
        primosQueSuman(28, 7, 2);
        System.out.println("********************************************************************");
        System.out.println("Prueba del metodo sqrtBusqBin con n=2");
        sqrtBusqBin(2);
        
        
    }
}

package src.edd;
import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.io.Serializable;

/**
 * Clase que moldea el comportamiento de los competidores que participarán en el torneo
 * @author Arturo González Peñaloza
 * @author Emilio Arsenio Raudry Rico
 * @author Fernanda Ameyalli Osorio Morales
 */
public class Competidor {
    private String nombre;
    private Lista<Integer> historial;
    private double probabilidad, cuota, monto;
    private boolean ganador;

    /**
     *Constructor del competidor.
     *@param nombre Nombre del competidor.
     */
    public Competidor(String nombre){
	this.nombre = nombre;
	this.historial = new Lista<>();
	this.probabilidad = 0.0;
	this.cuota = 0.0;
	this.monto = 0.0;

    }
    /**
     *Calcula la probabilidad de ganar del competidor.
     *@param n Numero de otros competidores a concursar.
     *@param s Cantidad de carreras en las que ha competido.
     */
    public void calcularProbabilidad(int n, int s){
	this.probabilidad = (s * (n+1) - sumatoria(this.historial)) / (s * (n * (n+1))/2);
    }

    /**
     *Calcula la cuota del competidor.
     */
    public void calcularCuota(){
	this.cuota = 1/this.probabilidad;
    }

    /**
     *Calcula el monto a recibir en caso de que el competidor gane.
     *double r Cantidad apostada.
     */
    public void calcularMonto(double r){
	this.monto = r * this.cuota;
    }

    /**
     *Metodo auxiliar que simula una sumatoria.
     *@param historial Lista con el historial de posicionamiento de los competidores.
     *@return int Suma de los elementos de la lista.
     */
    public static int sumatoria(Lista<Integer> historial){
	int suma = 0;
	Iterator<Integer> iterador = historial.iterator();
	for(int lugar:historial){
	    suma += lugar;
	}
	return suma;
    }
    /**
     *Agrega la posicion mas reciente en la cabeza del historial.
     *@param i Posicion actual.
     */
    public void actualizarHistorial(int i){
        this.historial.agregaInicio(i);
    }
    /**
     *Devuelve el nombre del competidor.
     *@return String nombre del competidor.
     */
    public String getNombre(){
	return this.nombre;
    }

    /**
     *Devuelve la probabilidad de ganar del competidor.
     *@return double Probabilidad de ganar del competidor.
     */
    public double getProbabilidad(){
	return this.probabilidad;
    }

   /**
     *Devuelve la Cuota del competidor.
     *@return double Cuota del competidor.
     */
    public double getCuota(){
	return this.cuota;
    }

    /**
     *Devuelve el monto del competidor en caso de ganar.
     *@return double Monto del competidor.
     */
    public double getMonto(){
	return this.monto;
    }

   /**
     *Devuelve el historial del competidor.
     *@return Lista<Integer> Historial del competidor.
     */
    public Lista<Integer> getHistorial(){
	return this.historial;
    }

    /**
     *Devuelve un String con las posiciones de las ultimas cinco carreras del competidor.
     *@return String Las posiciones de las ultimas cinco carreras.
     */
    public String getUltimas5Carreras(){
	Iterator<Integer> iterador = this.historial.iterator();
	String ultimasCinco = "Historial: ";
	int i = 1;
	for(int lugar : this.historial){
	    if(i>5)
		break;
	    else{
		ultimasCinco = ultimasCinco + iterador.next() + ", ";
		i++;
	    }
	}
	return ultimasCinco;
    }

    @Override
    public boolean equals(Object o){
	if (!(o instanceof Competidor))
            return false;
	Competidor competidor2 = (Competidor) o;
	
	if(!(this.nombre.equals(competidor2.getNombre())))
	    return false;

	if(!(this.historial.equals(competidor2.getHistorial())))
	    return false;

	if(this.probabilidad != competidor2.getProbabilidad())
	    return false;

	if(this.cuota != competidor2.getCuota())
	    return false;

	if(this.monto != competidor2.getMonto())
	    return false;

	return true;
	
    }
}

package src.edd;
import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.io.Serializable;

/**
 * Clase que moldea el comportamiento de los competidores que participarán en el torneo
 */
public class Competidor {
    private String nombre;
    private Lista<Integer> historial;
    private double probabilidad, cuota, monto;
    private boolean ganador;
    /*
     * Método constructor de los competidores que tendrán sus posiciones anteriores y su cuota p
     * por apostar
     */

    public Competidor(String nombre){
	this.nombre = nombre;
	this.historial = new Lista<>();
	this.probabilidad = 0.0;
	this.cuota = 0.0;
	this.monto = 0.0;
	this.ganador = false;
    }

    public void calcularProbabilidad(int n, int s){
	this.probabilidad = (s * (n+1) - sumatoria(this.historial)) / (s * (n * (n+1))/2);
    }

    public void calcularCuota(){
	this.cuota = 1/this.probabilidad;
    }

    public void calcularMonto(double r){
	this.monto = r * this.cuota;
    }

    public boolean esGanador(){
	return this.ganador;
    }

    /***/
    public static int sumatoria(Lista<Integer> historial){
	int suma = 0;
	Iterator<Integer> iterador = historial.iterator();
	for(int lugar:historial){
	    suma += lugar;
	}
	return suma;
    }

    public void actualizarHistorial(int i){
        this.historial.agregaInicio(i);
    }
    
    public String getNombre(){
	return this.nombre;
    }
    
    public double getProbabilidad(){
	return this.probabilidad;
    }

    public double getCuota(){
	return this.cuota;
    }
    public double getMonto(){
	return this.monto;
    }
    public Lista<Integer> getHistorial(){
	return this.historial;
    }

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

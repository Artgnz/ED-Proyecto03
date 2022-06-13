package src.edd;

import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;

/**
 * Clase que moldea a los candidatos que participarán en el Torneo
 */
public class Candidato implements Serializable {

    int minimaHabilidad;
    int maximaHabilidad;
    String nombre;

    double habilidadDecimal;
    int habilidad;

    /**
     * Método contructor de candidatos
     * 
     * @param nombre
     */
    public Candidato(String nombre) {
        this.nombre = nombre;
        this.habilidadDecimal = Math.random() * (400 - 50);
        this.habilidad = (int) habilidadDecimal;

    }

    /**
     * Método que devuelve el nombre del candidato
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método que devuelve la habilidad del candidato
     */
    public double getHabilidad() {
        return this.habilidad;
    }

    /**
     * Método que modifica la habilidad del candidato.
     */
    public void setHabilidad(int habilidad) {
        this.habilidad = habilidad;
    }

}

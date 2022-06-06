package src.edd;

import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;

/**
 * Clase que moldea a los candidatos que participarán en el Torneo
 */
public class Candidato {
    
    /**
     * Método contructor de candidatos
     */
    public Candidato() {
        int minimaHabilidad = 50;
        int maximaHabilidad = 400;
        double habilidad = Math.random()*(maximaHabilidad-minimaHabilidad);
    }


    public double getHabilidad() {
        return this.habilidad;
    }

    public void setHabilidad(double habilidad) {
        this.habilidad = habilidad;
    }
    
    }


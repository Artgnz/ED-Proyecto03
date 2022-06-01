package src.edd;

import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;

public class Usuario implements Comparable, Serializable {
    private Cuenta cuenta;
    private String nombreUsuario;
    private String contrasena;

    //constructor
    public Usuario(String nombreUsuario, String contrasena) {
        cuenta = new Cuenta();
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    //getters
    public Cuenta getCuenta() {
        return cuenta;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String toString(){
        return nombreUsuario ;
    }

    public boolean equals(Object o){
        if(!o.getClass().equals(this.getClass())){
            return false;
        }else{
            Usuario m=(Usuario) o;
            if(m.nombreUsuario.equals(this.nombreUsuario)){
                return true;
            }else{
                return false;
            }
        }
    }

    public int compareTo(Object o) {
        if(!o.getClass().equals(this.getClass())){
            throw new IllegalArgumentException("Los objetos no pertenecen a la misma clase");
        }else{
            Usuario j=(Usuario)o;
            if(nombreUsuario.equals(j.nombreUsuario)){
                return 0;
            }
            if(nombreUsuario.compareTo(j.nombreUsuario)<0){
                return -1;
            }else{
                return 1;
            }
        }
    }

}

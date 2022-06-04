package src.edd;

import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;

public class Main {
    
    public int Bienvenida(){
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        System.out.println("Bienvenido al sistema de gestion de cuentas");
        System.out.println("1. Iniciar sesion");
        System.out.println("2. Crear cuenta");
        System.out.println("3. Comenzar a apostar");
        System.out.println("3. Salir");
        System.out.println("Ingrese una opcion: ");
        try{
            opcion = sc.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Opcion invalida");
            opcion = Bienvenida();
        }
        return opcion;
    }
}

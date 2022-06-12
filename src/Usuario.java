package src.edd;

import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;

/**
 * Clase que moldea el comportamiento de los usuarios que podrán apostar en nuestro sistema de apuestas
 */
public class Usuario implements Comparable, Serializable {
    private Cuenta cuenta;
    private String nombreUsuario;

    //constructor
    public Usuario(String nombreUsuario) {
        this.cuenta = new Cuenta();
        this.nombreUsuario = nombreUsuario;
    }

    //getters
    public Cuenta getCuenta() {
        return cuenta;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String toString(){
        return nombreUsuario ;
    }

    /**
     * Método que compara dos usuarios
     * @param o el otro usuario a comparar
     */
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

    /**
     * Método que compara dos usuarios
     * @param o el otro usuario a comparar
     */
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

    /**
     * Método para que el Usuario pueda actualizar o mover cosas de su cuenta
     */
    public void ajustesCuenta(){
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{
            System.out.println("Bienvenidx a ajustes de cuenta" +"\n" + "¿Qué deseas realizar?" );
            System.out.println("\n1. Depositar dinero a la cuenta");
            System.out.println("\n2. Retirar dinero de la cuenta");
            System.out.println("\n3. Visualizar saldo de la cuenta");
            System.out.println("\n4. Salir");
            System.out.print("\nOpcion: ");
            try{
                opcion = sc.nextInt();
            }catch(InputMismatchException e){
                System.out.println("\nError: Introduzca un numero entero.");
                opcion = -1;
                sc.nextLine();
            }
            switch(opcion){
                case 1:
                    System.out.print("\nIntroduzca el dinero a añadir: ");
                    try{
                        cuenta.depositar(sc.nextInt());
                    }catch(InputMismatchException e){
                        System.out.println("\nError: Introduzca un numero entero.");
                        sc.nextLine();
                    }
                    break;
                case 2:
                    System.out.print("\nIntroduzca el dinero a retirar: ");
                    try{
                        cuenta.retirar(sc.nextInt());
                    }catch(InputMismatchException e){
                        System.out.println("\nError: Introduzca un numero entero.");
                        sc.nextLine();
                    }
                    break;
                case 3:
                    System.out.print("\nEl saldo actual de su cuenta es: ");
                    System.out.printf("%.2f%n", cuenta.getSaldo());
                    break;
                default:
                    System.out.println("\nVuelva pronto");
            }
        }while(opcion!=4);
    }

}




package src.edd;

import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;

/**
 * Clase Main que moldea el funcionamiento del sistema de apuestas
 */
public class Main {
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{ System.out.println("BIENVENIDX AL SISTEMA DE APUESTAS" + "\n" + "¿Qué desea hacer?");
            System.out.println("1. Deseo ver el torneo");
            System.out.println("2. Deseo ver las carreras");
            System.out.println("3. Salir");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextInt();
            try {
                switch(opcion){
                    case 1:
                        System.out.println("Antes de poder comenzar a ver el torneo, deberá registrarse, en caso de que ya tenga una cuenta, ingrese su nombre de usuario: ");
                        //cuando el usuario ya se encontraba en el sistema
                        //cuando el usuario aún no se encontraba en el sistema
                        String nombreUsuario = sc.next();
                        //al momento de generar el nuevo usuario también se genera su cuenta como uno de sus atributos
                        Usuario usuario = new Usuario(nombreUsuario);
                        Cuenta usuarioCuenta = usuario.getCuenta();
                        System.out.println("Recuerde que antes de poder empezar a apostar deberá contar con saldo disponible en su cuenta. Puede acceder a los ajustes de ella a continuación");
                        usuario.ajustesCuenta();
                        System.out.println("\nBienvenidx " + usuario.getNombreUsuario() + " al sistema de apuestas del Torneo.");
                        Torneo torneo = new Torneo();
                while (torneo.getCandidatos().size() > 1) {
                    //se notifica al usuario los candidatos que competirán en la partida y el sus respectivos montos de apuesta
                    torneo.nuevaPartida(usuario, usuarioCuenta);
                    //se dan los segundos para que el usuario decida y haga la apuesta
                   //se notifica al usuario el resultado de la partida
                }
                        torneo.nombrarGanador();
                        break;
                    case 2:
                        System.out.println("Antes de poder comenzar a ver las carreras, deberá registrarse, en caso de que ya tenga una cuenta, ingrese su nombre de usuario: ");
                    
                        break;
                    case 3:
                        System.out.println("Vuelva pronto (:");
                        break;
                    default:
                        System.out.println("Opcion inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error. Opcion inválida");
            }
            
        }while(opcion!=3);
    }
    
    
}

package src.edd;

import java.lang.Comparable;
import java.util.InputMismatchException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.Serializable;
import java.lang.Thread;
import java.util.Scanner;

import src.edd.Usuarios;

import java.io.InputStreamReader;
/**
 * Clase Main que moldea el funcionamiento del sistema de apuestas
 */
public class Main {
    private class Serializador {
        static boolean serializar(String nombreDeArchivo, Object objeto) {
            try {   
                FileOutputStream archivo = new FileOutputStream(nombreDeArchivo);
                ObjectOutputStream salida = new ObjectOutputStream(archivo);
              
                salida.writeObject(objeto);
                salida.writeObject(objeto);
              
                salida.close();
                archivo.close();
                return true;
            }
            catch(Exception ex) {
                return false;
            }   
        }

        static Object deSerializar(String nombreDeArchivo) {
            try {   
                FileInputStream archivo = new FileInputStream(nombreDeArchivo);
                ObjectInputStream entrada = new ObjectInputStream(archivo);

                Object object = entrada.readObject();
              
                archivo.close();
                entrada.close();
                return object;
            } catch(Exception ex){
                return null;
            }
        }
    }

    private static Usuarios usuarios;
    private static Torneo torneo;

    private static void restaurarEstado() {
        usuarios = (Usuarios)Serializador.deSerializar("usuarios.ser");
        if (usuarios == null) {
            System.out.println("fafa");

            usuarios = new Usuarios();
        }
        torneo = (Torneo)Serializador.deSerializar("torneo.ser");
        if (torneo == null) {
            torneo = new Torneo();
        }
    }

    public static void main(String[] args) {
        restaurarEstado();
        int opcion = -1;
        String error = "Ingrese una opción válida\n";
        do {
            String mensaje = "BIENVENIDX AL SISTEMA DE APUESTAS\n Para acceder necesita ingresar sesión.\n";
            mensaje += "En caso de contar con cuenta, ingrese \"si\", si no, escriba \"no\"";

            String nombreDeUsuario;
            if (Entrada.getString(mensaje, error, new String[]{"si", "no"}).equals("si")) {
                while (true) {
                    nombreDeUsuario = Entrada.getString("Ingrese su nombre de usuario.", "Ingrese una opción válida.");
                    if (usuarios.contieneUsuario(nombreDeUsuario)) {
                        break;
                    } else {
                        System.out.println("No se encontró el usuario.");
                    }
                }
            } else {
                while (true) {
                    nombreDeUsuario = Entrada.getString("Ingrese su nombre de usuario.", "Ingrese una opción válida.");
                    if (usuarios.contieneUsuario(nombreDeUsuario)) {
                        System.out.println("Ya existe un usuario registrado con ese nombre. Favor de Ingresar otro nombre de usuario.");
                    } else {
                        usuarios.agregaUsuario(new Usuario(nombreDeUsuario));
                        break;
                    }
                }
            }
            Usuario usuario = usuarios.getUsuario(nombreDeUsuario);
            Cuenta usuarioCuenta = usuario.getCuenta();
                
            mensaje = "BIENVENIDX AL SISTEMA DE APUESTAS" + "\n" + "¿Qué desea hacer?\n";
            mensaje += "1. Deseo ver el torneo\n";
            mensaje += "2. Deseo ver las carreras\n";
            mensaje += "3. Salir\n";
            mensaje += "Ingrese una opcion: \n";        
            opcion = Entrada.getInt(mensaje, error, 1, 3);
            Entrada.ignoreLine();
            switch(opcion){
            case 1:
                System.out.println("Recuerde que antes de poder empezar a apostar deberá contar con saldo disponible en su cuenta. Puede acceder a los ajustes de ella a continuación");
                usuario.ajustesCuenta();
                System.out.println("\nBienvenidx " + usuario.getNombreUsuario() + " al sistema de apuestas del Torneo.");

                while (torneo.getCandidatos().size() > 1) {
                    System.out.println("##############################");

                    torneo.prepararPartida();
                    Thread thread2 = new Thread(() -> {
                            try {
                                for (int segundos = 20; segundos > 0; segundos--) {
                                    System.out.println("Faltan " + segundos + " segundos para que inicie la partida.");
                                    Thread.sleep(1000);
                                }
                            }catch(Exception e){
                            }
                    });
                    thread2.start();
                    Scanner scan = new Scanner(System.in);
                    int respuesta = scan.nextInt();
                    if (!thread2.isAlive()) {
                        System.out.println("Como introdujo su respuesta cuando terminó el tiempo, no será considerado en esta partida.");
                        respuesta = -1;
                    }
                    if (respuesta == -1) {
                        thread2.interrupt();
                        opcion = 3;
                        System.out.println("Vuelva pronto :)");
                        break;
                    }
                    while (thread2.isAlive()) {
                        
                    }
                    torneo.nuevaPartida(usuario, usuarioCuenta, respuesta);
                }
                torneo.nombrarGanador();
                break;
            case 2:
                System.out.println("Recuerde que antes de poder empezar a apostar deberá contar con saldo disponible en su cuenta. Puede acceder a los ajustes de ella a continuación");
                usuario.ajustesCuenta();
                System.out.println("\nBienvenidx " + usuario.getNombreUsuario() + " al sistema de apuestas de carreras Torneo.");
                break;
            case 3:
                System.out.println("Vuelva pronto (:");
                break;
            default:
                System.out.println("Opcion inválida");
            }
        } while(opcion!=3);
        Serializador.serializar("usuarios.ser", usuarios);
        Serializador.serializar("torneo.ser", torneo);
    }
}

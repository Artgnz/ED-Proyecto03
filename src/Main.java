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
import java.util.Timer;
import java.util.TimerTask;

import src.edd.Carrera;
import src.edd.Usuarios;

import java.io.InputStreamReader;
/**
 * Clase Main que moldea el funcionamiento del sistema de apuestas
 */
public class Main {


    private static Usuarios usuarios;
    private static Torneo torneo;
    private static Carrera carrera;

    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";

    private static void restaurarEstado() {
        usuarios = (Usuarios)Serializador.deSerializar("usuarios.ser");
        if (usuarios == null) {
            usuarios = new Usuarios();
        }
        torneo = (Torneo)Serializador.deSerializar("torneo.ser");
        if (torneo == null) {
            torneo = new Torneo();
        }
        carrera = (Carrera)Serializador.deSerializar("carrera.ser");
        if (carrera == null) {
            carrera = new Carrera();
        }
    }

    public static void main(String[] args) {
        restaurarEstado();
        int opcion = -1;
        String error = "Ingrese una opción válida\n";
        do {
            String mensaje = ANSI_PURPLE_BACKGROUND + "BIENVENIDX AL SISTEMA DE APUESTAS\n Para acceder necesita ingresar sesión.\n";
            mensaje += ANSI_PURPLE_BACKGROUND + "En caso de contar con cuenta, ingrese \"si\", si no, escriba \"no\"" + ANSI_RESET;

            String nombreDeUsuario;
            if (Entrada.getString(mensaje, error, new String[]{"si", "no"}).equals("si")) {
                while (true) {
                    nombreDeUsuario = Entrada.getString(ANSI_GREEN + "Ingrese su nombre de usuario." + ANSI_RESET, "Ingrese una opción válida.");
                    if (usuarios.contieneUsuario(nombreDeUsuario)) {
                        break;
                    } else {
                        System.out.println(ANSI_GREEN + "No se encontró el usuario." + ANSI_RESET);
                    }
                }
            } else {
                while (true) {
                    nombreDeUsuario = Entrada.getString(ANSI_GREEN + "Ingrese su nombre de usuario." + ANSI_RESET, "Ingrese una opción válida.");
                    if (usuarios.contieneUsuario(nombreDeUsuario)) {
                        System.out.println(ANSI_PURPLE_BACKGROUND + "Ya existe un usuario registrado con ese nombre. Favor de Ingresar otro nombre de usuario." + ANSI_RESET);
                    } else {
                        usuarios.agregaUsuario(new Usuario(nombreDeUsuario));
                        break;
                    }
                }
            }
            Usuario usuario = usuarios.getUsuario(nombreDeUsuario);
            Cuenta usuarioCuenta = usuario.getCuenta();

            mensaje = ANSI_PURPLE_BACKGROUND + "BIENVENIDX AL SISTEMA DE APUESTAS" + "\n" + "¿Qué desea hacer?\n";
            mensaje += "1. Deseo ver el torneo\n";
            mensaje += "2. Deseo ver las carreras\n";
            mensaje += "3. Salir\n";
            mensaje += "Ingrese una opcion: \n" + ANSI_RESET;;
            opcion = Entrada.getInt(mensaje, error, 1, 3);
            Entrada.ignoreLine();
            switch(opcion){
            case 1:
                System.out.println(ANSI_GREEN + "Recuerde que antes de poder empezar a apostar deberá contar con saldo disponible en su cuenta. Puede acceder a los ajustes de ella a continuación" + ANSI_RESET);
                usuario.ajustesCuenta();
                Serializador.serializar("usuarios.ser", usuarios);
                System.out.println("\nBienvenidx " + usuario.getNombreUsuario() + " al sistema de apuestas del Torneo.");
                boolean bandera = true;
                while (bandera) {
                    while (torneo.getCandidatos().size() > 1) {
                        System.out.println("##############################");
                        Serializador.serializar("torneo.ser", torneo);
                        torneo.prepararPartida();
                        Thread thread2 = new Thread(() -> {
                                try {
                                    for (int segundos = 15; segundos > 0; segundos--) {
                                        System.out.println("Faltan " + segundos + " segundos para que inicie la partida.");
                                        Thread.sleep(1000);
                                    }
                                }catch(Exception e){
                                }
                        });
                        Timer timer = new Timer();
                        TimerTask temporizador = new TimerTask() {
                                @Override
                                public void run() {
                                    System.out.println(ANSI_RED + "La partida ha comenzado, presione algun número." + ANSI_RESET);
                                }
                            };
                        timer.schedule(temporizador, 1000 * 15);
                        thread2.start();
                        Scanner scan = new Scanner(System.in);
                        int respuesta = scan.nextInt();
                        timer.cancel();
                        if (respuesta == -2) {
                            thread2.interrupt();
                            opcion = 3;
                            System.out.println(ANSI_CYAN + "Vuelva pronto :)" + ANSI_RESET);
                            bandera = false;
                            break;
                        }
                        if (!thread2.isAlive()) {
                            System.out.println(ANSI_RED + "Como introdujo su respuesta cuando terminó el tiempo, no será considerado en esta partida." + ANSI_RESET);
                            respuesta = (respuesta == -2 ? -2 : -1);
                        }
                        // while (thread2.isAlive()) {

                        // }
                        torneo.nuevaPartida(usuario, usuarioCuenta, respuesta, thread2);
                    }
                    if (bandera) {
                        System.out.println("El torneo ha finalizado.");
                        torneo.nombrarGanador();
                        torneo = new Torneo();
                        Serializador.serializar("torneo.ser", torneo);
                        System.out.println("Iniciando nuevo torneo...");
                    }
                }
                break;
            case 2:
                System.out.println("Recuerde que antes de poder empezar a apostar deberá contar con saldo disponible en su cuenta. Puede acceder a los ajustes de ella a continuación");
                usuario.ajustesCuenta();
                Serializador.serializar("usuarios.ser", usuarios);

                System.out.println(ANSI_GREEN + "\nBienvenidx " + usuario.getNombreUsuario() + " al sistema de apuestas de carreras." + ANSI_RESET);
                while (true) {
                    System.out.println("##############################");
                    Serializador.serializar("carrera.ser", carrera);
                    carrera.prepararCarrera();
                    System.out.println(ANSI_CYAN + "Cuotas:" + ANSI_RESET);
                    carrera.imprimirCuotas();
                    System.out.println(ANSI_CYAN + "Si desea apostar presiona 1 si no, presione cualquier otro número.\nSi desea dejar de ver las carreras, presione -2." +  ANSI_RESET);
                    Thread thread2 = new Thread(() -> {
                            try {
                                for (int segundos = 15; segundos > 0; segundos--) {
                                    System.out.println("Faltan " + segundos + " segundos para que inicie la carrera.");
                                    Thread.sleep(1000);
                                }
                            }catch(Exception e){
                            }
                    });
                    Timer timer = new Timer();
                    TimerTask temporizador = new TimerTask() {
                            @Override
                            public void run() {
                                System.out.println(ANSI_RED + "La partida ha comenzado, presione algun número." + ANSI_RESET);
                            }
                        };
                    timer.schedule(temporizador, 1000 * 15);
                    thread2.start();
                    Scanner scan = new Scanner(System.in);
                    int respuesta = scan.nextInt();
                    timer.cancel();
                    if (!thread2.isAlive()) {
                        System.out.println(ANSI_RED + "Como introdujo su respuesta cuando terminó el tiempo, no será considerado en esta carrera." + ANSI_RESET);
                        respuesta = -1;
                    }
                    if (respuesta == -2) {
                        thread2.interrupt();
                        opcion = 3;
                        System.out.println("Vuelva pronto" + usuario.getNombreUsuario() + " :)");
                        break;
                    }
                    carrera.ejecutarCarrera(usuario, usuarioCuenta, respuesta, thread2);
                }
                break;
            case 3:
                System.out.println("Vuelva pronto (:");
                break;
            default:
                System.out.println(ANSI_RED + "Opcion inválida" + ANSI_RESET);
            }
        } while(opcion!=3);
        Serializador.serializar("usuarios.ser", usuarios);
    }
}

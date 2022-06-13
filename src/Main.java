package src.edd;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Clase Main que moldea el funcionamiento del sistema de apuestas
 */
public class Main {

    /**
     * Usuarios registrados.
     */
    private static Usuarios usuarios;
    /**
     * Torneo en ejecución.
     */
    private static Torneo torneo;
    /**
     * Carrera en ejecución.
     */
    private static Carrera carrera;

    /**
     * Restaura el estado de los atributos de {@code this}.
     */
    private static void restaurarEstado() {
        try {
            usuarios = (Usuarios)Serializador.deSerializar("usuarios.ser");
        } catch (ClassCastException e) {
            System.out.println("El archivo usuarios.ser está corrupto.");
        }
        if (usuarios == null) {
            usuarios = new Usuarios();
        }
        try {
            torneo = (Torneo)Serializador.deSerializar("torneo.ser");
        } catch (ClassCastException e) {
            System.out.println("El archivo torneo.ser está corrupto.");            
        }
        if (torneo == null) {
            torneo = new Torneo();
        }
        try {
            carrera = (Carrera)Serializador.deSerializar("carrera.ser");
        } catch (ClassCastException e){
            System.out.println("El archivo usuarios.ser está corrupto.");
        }
        if (carrera == null) {
            carrera = new Carrera();
        }
    }

    public static void main(String[] args) {
        restaurarEstado();
        int opcion = -1;
        String error = "Ingrese una opción válida\n";
        do {
            Usuario usuario;
            String mensaje = Colors.ANSI_PURPLE_BACKGROUND + "BIENVENIDX AL SISTEMA DE APUESTAS\n Para acceder necesita ingresar sesión.\n";
            mensaje += Colors.ANSI_PURPLE_BACKGROUND + "En caso de contar con cuenta, ingrese \"si\", si no, escriba \"no\"" + Colors.ANSI_RESET;

            String nombreDeUsuario;
            if (Entrada.getString(mensaje, error, new String[]{"si", "no"}).equals("si")) {
                while (true) {
                    nombreDeUsuario = Entrada.getString(Colors.ANSI_GREEN + "Ingrese su nombre de usuario." + Colors.ANSI_RESET, "Ingrese una opción válida.");
                    if (usuarios.contieneUsuario(nombreDeUsuario)) {
                        usuario = usuarios.getUsuario(nombreDeUsuario);
                        break;
                    } else {
                        System.out.println(Colors.ANSI_GREEN + "No se encontró el usuario." + Colors.ANSI_RESET);
                    }
                }
            } else {
                while (true) {
                    nombreDeUsuario = Entrada.getString(Colors.ANSI_GREEN + "Ingrese su nombre de usuario." + Colors.ANSI_RESET, "Ingrese una opción válida.");
                    if (usuarios.contieneUsuario(nombreDeUsuario)) {
                        System.out.println(Colors.ANSI_PURPLE_BACKGROUND + "Ya existe un usuario registrado con ese nombre. Favor de Ingresar otro nombre de usuario." + Colors.ANSI_RESET);
                    } else {
                        usuario = new Usuario(nombreDeUsuario);
                        System.out.println("Se agregó a " + usuario + " al sistema.");
                        usuarios.agregaUsuario(new Usuario(nombreDeUsuario));
                        Serializador.serializar("usuarios.ser", usuarios);
                        break;
                    }
                }
            }
            Cuenta usuarioCuenta = usuario.getCuenta();

            mensaje = Colors.ANSI_PURPLE_BACKGROUND + "BIENVENIDX AL SISTEMA DE APUESTAS" + "\n" + "¿Qué desea hacer?\n";
            mensaje += "1. Deseo ver el torneo\n";
            mensaje += "2. Deseo ver las carreras\n";
            mensaje += "3. Salir\n";
            mensaje += "Ingrese una opcion: \n" + Colors.ANSI_RESET;;
            opcion = Entrada.getInt(mensaje, error, 1, 3);
            Entrada.ignoreLine();
            switch(opcion){
            case 1:
                System.out.println(Colors.ANSI_GREEN + "Recuerde que antes de poder empezar a apostar deberá contar con saldo disponible en su cuenta. Puede acceder a los ajustes de ella a continuación" + Colors.ANSI_RESET);
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
                                    System.out.println(Colors.ANSI_RED + "La partida ha comenzado, presione algun número." + Colors.ANSI_RESET);
                                }
                            };
                        timer.schedule(temporizador, 1000 * 15);
                        thread2.start();
                        Scanner scan = new Scanner(System.in);
                        int respuesta = -1;
                        try {
                            respuesta = scan.nextInt();
                        } catch (Exception e) {
                        }
                        timer.cancel();
                        if (respuesta == -2) {
                            thread2.interrupt();
                            opcion = 3;
                            System.out.println("Vuelva pronto, " + usuario.getNombreUsuario() + " :)");
                            bandera = false;
                            break;
                        }
                        if (!thread2.isAlive()) {
                            System.out.println(Colors.ANSI_RED + "Como introdujo su respuesta cuando terminó el tiempo, no será considerado en esta partida." + Colors.ANSI_RESET);
                            respuesta = (respuesta == -2 ? -2 : -1);
                        }
                        torneo.nuevaPartida(usuario, usuarioCuenta, respuesta, thread2);
                        Serializador.serializar("usuarios.ser", usuario);
                    }
                    Serializador.serializar("usuarios.ser", usuario);
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

                System.out.println(Colors.ANSI_GREEN + "\nBienvenidx " + usuario.getNombreUsuario() + " al sistema de apuestas de carreras." + Colors.ANSI_RESET);
                while (true) {
                    System.out.println("##############################");
                    Serializador.serializar("carrera.ser", carrera);
                    carrera.prepararCarrera();
                    System.out.println(Colors.ANSI_CYAN + "Cuotas:" + Colors.ANSI_RESET);
                    carrera.imprimirCuotas();
                    System.out.println(Colors.ANSI_CYAN + "Si desea apostar presiona 1 si no, presione cualquier otro número.\nSi desea dejar de ver las carreras, presione -2." +  Colors.ANSI_RESET);
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
                                System.out.println(Colors.ANSI_RED + "La partida ha comenzado, presione algun número." + Colors.ANSI_RESET);
                            }
                        };
                    timer.schedule(temporizador, 1000 * 15);
                    thread2.start();
                    Scanner scan = new Scanner(System.in);
                    int respuesta = -1;
                    try {
                        respuesta = scan.nextInt();
                    } catch (Exception e) {
                    }
                    timer.cancel();
                    if (respuesta == -2) {
                        thread2.interrupt();
                        opcion = 3;
                        System.out.println("Vuelva pronto, " + usuario.getNombreUsuario() + " :)");
                        break;
                    }
                    if (!thread2.isAlive()) {
                        System.out.println(Colors.ANSI_RED + "Como introdujo su respuesta cuando terminó el tiempo, no será considerado en esta carrera." + Colors.ANSI_RESET);
                        respuesta = -1;
                    }
                    carrera.ejecutarCarrera(usuario, usuarioCuenta, respuesta, thread2);
                    Serializador.serializar("usuarios.ser", usuario);
                }
                break;
            case 3:
                System.out.println("Vuelva pronto (:");
                break;
            default:
                System.out.println(Colors.ANSI_RED + "Opcion inválida" + Colors.ANSI_RESET);
            }
        } while(opcion!=3);
        Serializador.serializar("usuarios.ser", usuarios);
    }
}



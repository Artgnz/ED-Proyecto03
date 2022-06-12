package src.edd;

import java.util.Scanner;


public class Entrada {
    private static Scanner scn = new Scanner(System.in);

    /**
     * Método que pide al usuario un número entero.
     * @param mensaje Un mensaje que le indica al usuario que opciones puede ingresar.
     * @param error Mensaje que indica que se introdujo una opción inválida.
     * @param min El valor mínimo que se puede introducir.
     * @param max El valor máximo que se puede introducir.
     * @return int El número que el usuario introduzca.
     */
    public static int getInt(String mensaje, String error, int min, int max) {
        int val;

        while (true) {
            System.out.println(mensaje);
            if (scn.hasNextInt()) {
                val = scn.nextInt();
                if (val < min || max < val) {
                    System.out.println(error);
                } else {
                    return val;
                }
            } else {
                scn.next();
                System.out.println(error);
            }
        }
    }

    /**
     * Metodo que pide al usuario un String.
     * @param mensaje Un mensaje que le indica al usuario que opciones puede ingresar.
     * @param error Mensaje que indica que se introdujo una opción inválida.
     * @param opciones Strings validos que puede insertar el usuario..
     */
    public static String getString(String mensaje, String error, String[] opciones) {
        String entrada;

        while (true) {
            System.out.println(mensaje);
            if (scn.hasNextLine()) {
                entrada = scn.nextLine();
                for (int i = 0; i < opciones.length; i++) {
                    if (entrada.equals(opciones[i])) {
                        return entrada;
                    }
                }
                System.out.println(error);
            } else {
                scn.nextLine();
                System.out.println(error);
            }
        }
    }
    /**
     * Metodo que pide al usuario un String.
     * @param mensaje Un mensaje para el usuario.
     * @param error Mensaje que indica si se introdujo una entrada inválida.
     */
    public static String getString(String mensaje, String error) {
        String entrada;

        while (true) {
            System.out.println(mensaje);
            if (scn.hasNextLine()) {
                entrada = scn.nextLine();
                return entrada;
            } else {
                scn.nextLine();
                System.out.println(error);
            }
        }
    }

    /**
     * Metodo que pide al usuario un String.
     * @param mensaje Un mensaje que le indica al usuario que opciones puede ingresar.
     * @param error Mensaje que indica que se introdujo una opción inválida.
     * @param opciones Strings validos que puede insertar el usuario..
     */
    public static String getString(String mensaje, String error, Lista<String> opciones) {
        String entrada;
        while (true) {
            System.out.println(mensaje);
            if (scn.hasNextLine()) {
                entrada = scn.nextLine();
                if (opciones.contains(entrada)) {
                        return entrada;
                }
                System.out.println(error);
            } else {
                scn.nextLine();
                System.out.println(error);
            }
        }
    }

    /**
     * Hace que el scanner pase a la siguiente linea.
     */
    public static void ignoreLine() {
        scn.nextLine();
    }

}

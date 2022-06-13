package src.edd;

import java.io.Serializable;

/**
 * Clase que moldea las cuentas de los usuarios que participarán en el torneo
 */
public class Cuenta implements Serializable {

    // Atributos de la cuenta
    private double saldoDisponible;
    Lista<String> historialCuenta = new Lista<String>();
    Lista<String> historialApuestasHechas = new Lista<String>();

    // Constructor
    public Cuenta() {
        this.saldoDisponible = 0;// al comienzo no se cuenta con ningún saldo
        historialCuenta = new Lista<String>();
        historialApuestasHechas = new Lista<String>();
    }

    /**
     * Método para depositar dinero en la cuenta
     * 
     * @param cantidad
     */
    public void depositar(int cantidad) {
        if (cantidad < 0) {
            System.out.println("Lo sentimos. No se puede depositar una cantidad negativa");
        }
        this.saldoDisponible += cantidad;
        historialCuenta.add("Se realizó un depósito por: " + cantidad);
    }

    /**
     * Método que muestra el saldo disponible en la cuenta
     */
    public double getSaldo() {
        historialCuenta.add("Se revisó el saldo.");
        return saldoDisponible;
    }

    /**
     * Método que modifica el saldo disponible en la cuenta
     * 
     * @param saldo
     */
    public double setSaldo(double saldo) {
        historialCuenta.add("Se modificó el saldo");
        return saldoDisponible += saldo;
    }

    /**
     * Método para retirar dinero de la cuenta
     * 
     * @param cantidad Cantidad a retirar
     */
    public void retirar(int cantidad) {
        if (cantidad < 0) {
            System.out.println("Lo sentimos. No se puede retirar una cantidad negativa");
        }
        if (cantidad > this.saldoDisponible) {
            System.out.println("No se puede retirar dinero porque no hay saldo suficiente");
        } else {
            this.saldoDisponible -= cantidad;
            historialCuenta.add("Se realizó un retiro por: " + cantidad);
        }
    }

    /**
     * Método que retira el dinero que se pierde al realizar una apuesta y lo guarda
     * en un historial
     * 
     * @param cantidad a retirar
     */
    public void retirarApuesta(double cantidad) {
        this.saldoDisponible -= cantidad;
    }

    /**
     * Booleano que verifica que haya suficientes fondos en la cuenta para realizar
     * una apuesta
     * 
     * @param cantidadAApostar
     */
    public boolean validarApuesta(double cantidadAAPostar) {
        if (this.saldoDisponible < cantidadAAPostar) {
            System.out.println("Lo sentimos. No hay suficiente fondos en su cuenta para realizar la apuesta, debes realizar un depósito para continuar jugando");
            return false;
        } else {
            return true;
        }

    }

    /**
     * Método que deposita la cantidad que se gana si la apuesta resulta favorable
     * 
     * @param cantidad que se ganó
     */
    public void depositarPremio(double cantidad) {
        historialApuestasHechas.add("Se ganó una apuesta por " + cantidad + "\n");
        this.saldoDisponible += cantidad;
    }

    /**
     * Método para consultar el saldo de la cuenta
     */
    public String consultarSaldo() {
        historialCuenta.add("Se consultó el saldo disponible\n");
        return "Usted cuenta con " + this.saldoDisponible;
    }

    /**
     * Método para consultar el historial de la cuenta
     */
    public Lista<String> consultarHistorial() {
        return historialCuenta;
    }

    /**
     * Método para apostar con el dinero de la cuenta
     * 
     * @param cantidad a apostar
     */

    public void apostar(int cantidad) {
        if (cantidad < 0) {
            System.out.println("Lo sentimos. No se puede apostar una cantidad negativa");
        }
        if (cantidad > this.saldoDisponible) {
            System.out.println("No se puede apostar esa cantidad de dinero porque no tiene saldo suficiente");
        } else {
            this.saldoDisponible -= cantidad;
            historialCuenta.add("Se realizó una apuesta por: " + cantidad + "\n");
            historialApuestasHechas.add("Se realizó una apuesta por: " + cantidad + "\n");
        }
    }

    /**
     * Método para consultar el historial de apuestas
     */
    public Lista<String> consultarHistorialApuestas() {
        return historialApuestasHechas;
    }

}

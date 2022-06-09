package src.edd;

import java.io.Serializable;


public class Cuenta implements Serializable{

    //Atributos de la cuenta
    private double saldoDisponible;
    Lista<String> historialCuenta = new Lista<String>();
    Lista<String> historialApuestasHechas = new Lista<String>();

    // Constructor
    public Cuenta() {
        this.saldoDisponible = 0;//al comienzo no se cuenta con ningún saldo
        historialCuenta = new Lista<String>();
        historialApuestasHechas = new Lista<String>();
    }

    /**
     * Método para depositar dinero en la cuenta
     * @param cantidad
     */
    public void depositar(int cantidad) {
        if(cantidad < 0){
            System.out.println("Lo sentimos. No se puede depositar una cantidad negativa");
        }
        this.saldoDisponible += cantidad;
        historialCuenta.add("Se realizó un depósito por: "+ cantidad);  
}

    public double getSaldo() {
        return saldoDisponible;
    }

    public double setSaldo(double saldo) {
        return saldoDisponible += saldo;
    }

    /**
     * Método para retirar dinero de la cuenta
     * @param cantidad Cantidad a retirar
     */
    public void retirar(int cantidad) {
        if(cantidad < 0){
            System.out.println("Lo sentimos. No se puede retirar una cantidad negativa");
        }
        if (cantidad > this.saldoDisponible) {
            System.out.println("No se puede retirar dinero porque no hay saldo suficiente");
        } else {
            this.saldoDisponible -= cantidad;
            historialCuenta.add("Se realizó un retiro por: "+ cantidad);
        }
    }

    public void retirarApuesta(double cantidad) {
        historialApuestasHechas.add("Se hizo una apuesta");
        this.saldoDisponible -= cantidad;
    }

    public void depositarPremio(double cantidad) {
        historialApuestasHechas.add("Se ganó una apuesta");
        this.saldoDisponible += cantidad;
    }


    /**
     * Método para consultar el saldo de la cuenta
     */
    public String consultarSaldo() {
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
     * @param cantidad a apostar
     */

     public void apostar(int cantidad) {
        if(cantidad < 0){
            System.out.println("Lo sentimos. No se puede apostar una cantidad negativa");
        }
        if (cantidad > this.saldoDisponible) {
            System.out.println("No se puede apostar esa cantidad de dinero porque no tiene saldo suficiente");
        } else {
            this.saldoDisponible -= cantidad;
            historialCuenta.add("Se realizó una apuesta por: "+ cantidad);
            historialApuestasHechas.add("Se realizó una apuesta por: "+ cantidad);
        }
    }

    /**
     * Método para consultar el historial de apuestas
     */
    public Lista<String> consultarHistorialApuestas() {
        return historialApuestasHechas;
    }

    /**
     * Método para abonar al dinero ganado luego de que la apuesta resultara favorable
     * @param cantidad a abonar
     */
    public void depositoApuestaGanada(int cantidad){
        this.saldoDisponible += cantidad;
        historialCuenta.add("Depósito por apuesta ganada por: "+ cantidad);
    }

    /**
     * Método para retirar el dinero perdido luego de que la apuesta no resultara favorable
     */
    public void retiroApuestaPerdida(int cantidad){
        this.saldoDisponible -= cantidad;
        historialCuenta.add("Retiro por apuesta perdida por: "+ cantidad);
    }
}
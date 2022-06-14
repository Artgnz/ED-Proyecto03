package src.edd;

import java.util.Iterator;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.Thread;
import java.io.Serializable;

/**
 * Clase que moldea la forma en la que irán llevándose a cabo los torneos
 * @author Osorio Morales Fernanda Ameyalli
 * @author Raudry Rico Emilio Arsenio
 * @author González Peñaloza Arturo
 */
public class Torneo implements Serializable{

    //atributos
    public Lista<Candidato> candidatos = new Lista<>();
    private Candidato[] siguientesCandidatos = new Candidato[2];
    Iterator<Candidato> iterador = candidatos.iterator();

    public Usuario usuario;
    public Cuenta cuenta;

    private Candidato candidato1;
    private Candidato candidato2;

    //constructor de un torneo
    public Torneo(){
        for(int i = 1; i< 17; i++){
            Candidato candidato = new Candidato("Candidato " + i);
            candidatos.add(candidato);
            //verificamos que no haya empates en las habilidades de los candidatos
            while(iterador.hasNext()){
                Candidato candidatoAux = iterador.next();
                if(candidato.getHabilidad() == candidatoAux.getHabilidad()){
                    candidato.setHabilidad((int) Math.random()*(400-50));
                    break;
                }
            }
        }
    }
    
    /**
     * Método que devuelve la lista donde se están guardando los candidatos
     */
    public Lista getCandidatos(){
        return candidatos;
    }

    public void prepararPartida() {
        candidato1 = candidatos.pop();
        candidato2 = candidatos.pop();
        candidatos.shuffle();
        System.out.println(Colors.ANSI_CYAN + "En la siguiente partida competirán " + candidato1.getNombre() + " VS " + candidato2.getNombre());
        double probabilidadCandidato1 = candidato1.getHabilidad() / (candidato1.getHabilidad()+candidato2.getHabilidad());
        double probabilidadCandidato2 = candidato2.getHabilidad() / (candidato1.getHabilidad()+candidato2.getHabilidad());

        //Mostramos al usuario las cuotas por apostar de cada uno de los candidatos
        double cuotaCandidato1 = 1/probabilidadCandidato1;
        double cuotaCandidato2 = 1/probabilidadCandidato2;
        System.out.println("Si desea apostar por el " + candidato1.getNombre()+ " pulse 1, la cuota de apuesta es de: " );
        System.out.printf("%.3f%n", cuotaCandidato1);
        System.out.println("Si desea apostar por el "+ candidato2.getNombre() + " pulse 2, la cuota de apuesta es de: " );
        System.out.printf("%.3f%n", cuotaCandidato2);
        System.out.println("Si desea dejar de ver el torneo, presione -2");
        System.out.println("Si desea no apostar, presione cualquier otro número." + Colors.ANSI_RESET);
    }

    /**
     * Método para generar las partidas en las que se enfrentarán los candidatos
     * @param usuario
     * @param cuenta
     */
    public void nuevaPartida(Usuario usuario, Cuenta cuenta, int opcion, Thread thread){
        this.usuario = usuario;
        this.cuenta = cuenta;

        //calculamos la probabilidad de que gane cada uno de los candidatos
        double probabilidadCandidato1 = candidato1.getHabilidad() / (candidato1.getHabilidad()+candidato2.getHabilidad());
        double probabilidadCandidato2 = candidato2.getHabilidad() / (candidato1.getHabilidad()+candidato2.getHabilidad());

        //Mostramos al usuario las cuotas por apostar de cada uno de los candidatos
        double cuotaCandidato1 = 1/probabilidadCandidato1;
        double cuotaCandidato2 = 1/probabilidadCandidato2;
        double cuotaApostada = 0;
        double cuotaApostada2 = 0;
        Scanner scanner = new Scanner(System.in);

        //se descuenta la apuesta de la cuenta del usuario
        try {
            if(opcion == 1){
                System.out.println(Colors.ANSI_CYAN + "Ingrese la cuota que desea apostar");
                cuotaApostada = scanner.nextDouble();
                if(cuenta.validarApuesta(cuotaApostada) == true){
                    System.out.println("Apuesta realizada.");
                    cuenta.retirarApuesta(cuotaApostada);
                    System.out.println(cuenta.consultarSaldo());
                } else{
                    usuario.ajustesCuenta();
                }
            }
            else if(opcion == 2){
                System.out.println("Ingrese la cuota que desea apostar"+ Colors.ANSI_RESET);
                cuotaApostada2 = scanner.nextDouble();
                if(cuenta.validarApuesta(cuotaApostada2) == true){
                    cuenta.retirarApuesta(cuotaApostada2);
                    System.out.println(cuenta.consultarSaldo());
                } else{
                    usuario.ajustesCuenta();
                }
            }
            while (thread.isAlive()) {
            }
            //vemos quien gana la partida
            double x = Math.random();
            boolean ganoC1 = false;
            boolean ganoC2 = false;
            if(x < probabilidadCandidato1){
                System.out.println(Colors.ANSI_CYAN + "El candidato 1 gana la partida");
                ganoC1 = true;
                ganoC2 = false;
                candidatos.add(candidato1);
            } else{
                System.out.println("El candidato 2 gana la partida" + Colors.ANSI_RESET);
                ganoC1 = false;
                ganoC2 = true;
                candidatos.add(candidato2);
            }
            //hacemos los pagos correspondientes
            if(opcion == 1){
                if(ganoC1){
                    //calculamos la cuota que ganará el usuario por haber apostado por el candidato 1
                    double premioGanador = cuotaCandidato1*cuotaApostada;
                    cuenta.depositarPremio(premioGanador);
                    System.out.println(Colors.ANSI_CYAN + "Felicidades, has ganado la apuesta, se abonará a tu cuenta ");
                    System.out.printf("%.3f%s\n", premioGanador, Colors.ANSI_RESET);
                    System.out.println(cuenta.consultarSaldo());
                } else{
                    System.out.println(Colors.ANSI_CYAN + "Lo siento, has perdido la apuesta" + Colors.ANSI_RESET);
                    System.out.println(cuenta.consultarSaldo());
                }
            } 
            else if (opcion == 2){
                if(ganoC2){
                    //calculamos la cuota que ganará el usuario por haber apostado por el candidato 2
                    double premioGanador = cuotaCandidato2*cuotaApostada2;
                    cuenta.depositarPremio(premioGanador);
                    System.out.println(Colors.ANSI_CYAN + "Felicidades, has ganado la apuesta, se abonará a tu cuenta: " );
                    System.out.printf("%.3f%s\n", premioGanador, Colors.ANSI_RESET);
                    cuenta.consultarSaldo();
                } else{
                    System.out.println(Colors.ANSI_CYAN + "Lo siento, has perdido la apuesta" + Colors.ANSI_RESET);
                    cuenta.consultarSaldo();
                }
            }

        } catch (InputMismatchException e) {
            System.out.println(Colors.ANSI_RED + "Error, opción invalida" + Colors.ANSI_RESET);
        }

    }
            
             

    

       
    /**
     * Método para ver quien es el ganador del torneo
     */

    public void nombrarGanador(){
        if(candidatos.size() == 1){
            System.out.println(Colors.ANSI_CYAN + "El ganador del torneo es: " + candidatos.pop().getNombre() + Colors.ANSI_RESET);
        } 
    }
        
        


}

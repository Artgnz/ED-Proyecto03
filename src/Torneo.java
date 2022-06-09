package src.edd;

import java.util.Iterator;
import java.util.Scanner;


/**
 * Clase que moldea la forma en la que irán llevándose a cabo los torneos
 */
public class Torneo {

    public Lista<Candidato> candidatos = new Lista<>();
    Iterator<Candidato> iterador = candidatos.iterator();

    public Usuario usuario;
    public Cuenta cuenta;

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
    
    public Lista getCandidatos(){
        return candidatos;
    }


    /**
     * Método para generar las partidas en las que se enfrentarán los candidatos
     */

    public void nuevaPartida(Usuario usuario, Cuenta cuenta){

        Candidato candidato1 = candidatos.pop();
        candidatos.shuffle();
        Candidato candidato2 = candidatos.pop();

        this.usuario = usuario;
        this.cuenta = cuenta;

        //calculamos la probabilidad de que gane cada uno de los candidatos
        double probabilidadCandidato1 = candidato1.getHabilidad()/(candidato1.getHabilidad()+candidato2.getHabilidad());
        double probabilidadCandidato2 = candidato2.getHabilidad()/(candidato1.getHabilidad()+candidato2.getHabilidad());

        //Mostramos al usuario las cuotas por apostar de cada uno de los candidatos
        double cuotaCandidato1 = 1/probabilidadCandidato1;
        double cuotaCandidato2 = 1/probabilidadCandidato2;
        System.out.println("Es hora de apostar, bienvenidx a la partida de " + candidato1.getNombre()+  " VS " + candidato2.getNombre());
        System.out.println("Si desea apostar por el " + candidato1.getNombre()+ " pulse 1, la cuota de apuesta es de " + cuotaCandidato1 + " pesos mexicanos");
        System.out.println("Si desea apostar por el "+ candidato2.getNombre() + " pulse 2, la cuota de apuesta es de " + cuotaCandidato2 + " pesos mexicanos");
        Scanner scanner = new Scanner(System.in);
             int opcion = scanner.nextInt();//por quien quizo apostar el usuario

             //se descuenta la apuesta de la cuenta del usuario
             if(opcion == 1){
            cuenta.retirarApuesta(cuotaCandidato1);
            System.out.println(cuenta.consultarSaldo());
             }
             else if(opcion == 2){
            cuenta.retirarApuesta(cuotaCandidato2);
            System.out.println(cuenta.consultarSaldo());
             }
            else{
            System.out.println("Opcion invalida");
             }

//vemos quien gana la partida
             double x = Math.random();
             boolean ganoC1 = false;
             boolean ganoC2 = false;
             if(x < probabilidadCandidato1){
                 System.out.println("El candidato 1 gana la partida");
                 ganoC1 = true;
                 ganoC2 = false;
                 candidatos.add(candidato1);
             } else{
                 System.out.println("El candidato 2 gana la partida");
                 ganoC1 = false;
                 ganoC2 = true;
                 candidatos.add(candidato2);
             }
             //hacemos los pagos correspondientes
             if(opcion == 1){
                if(ganoC1){
                    //calculamos la cuota que ganará el usuario por haber apostado por el candidato 1
                    double premioGanador = cuotaCandidato1*cuotaCandidato1;
                    cuenta.depositarPremio(premioGanador);
                    System.out.println("Felicidades, has ganado la apuesta, se abonará a tu cuenta " + premioGanador + " pesos mexicanos");
                    System.out.println(cuenta.consultarSaldo());
                } else{
                    System.out.println("Lo siento, has perdido la apuesta");
                    System.out.println(cuenta.consultarSaldo());
                }
            } 
                    else if (opcion == 2){
                if(ganoC2){
                    //calculamos la cuota que ganará el usuario por haber apostado por el candidato 2
                    double premioGanador = cuotaCandidato2*cuotaCandidato2;
                    cuenta.depositarPremio(premioGanador);
                    System.out.println("Felicidades, has ganado la apuesta, se abonará a tu cuenta " + premioGanador + " pesos mexicanos");
                    cuenta.consultarSaldo();
                } else{
                    System.out.println("Lo siento, has perdido la apuesta");
                    cuenta.consultarSaldo();
                        }
                  }
    }

       
    /**
     * Método para ver quien es el ganador del torneo
     */

    public void nombrarGanador(){
        if(candidatos.size() == 1){
            System.out.println("El ganador del torneo es: " + candidatos.pop().getNombre());
        } 
    }
        
        


}

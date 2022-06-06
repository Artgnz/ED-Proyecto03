package src.edd;

import java.util.Scanner;


/**
 * Clase que moldea la forma en la que irán llevándose a cabo los torneos
 */
public class Torneo {
    
    /*
     * Método que se encarga de iniciar el torneo
     */
    public void iniciarTorneo(){
        Pila<Candidato> candidatos = new Pila<Candidato>();
        //generamos a los candidatos que participarán en el torneo
        for(int i = 0; i< 16; i++){
            Candidato candidato = new Candidato();
            //verificamos que no haya empates en las habilidades de los candidatos
            if(candidato.getHabilidad() == this.getHabilidad()){
               this.setHabilidad(Math.random()*(400-50));
            }
            candidatos.push(candidato);
        }

}

    /**
     * Método para generar las partidas en las que se enfrentarán los candidatos
     */

    public void nuevaPartida(){
        //dos candidatos se están enfrentando
        Candidato candidato1 = candidatos.pop(candidato);
        Candidato candidato2 = candidatos.pop(candidato);

        //calculamos la probabilidad de que gane cada uno de los candidatos
        double probabilidadCandidato1 = candidato1.getHabilidad()/(candidato1.getHabilidad()+candidato2.getHabilidad());
        double probabilidadCandidato2 = candidato2.getHabilidad()/(candidato1.getHabilidad()+candidato2.getHabilidad());

        //Mostramos al usuario las cuotas por apostar de cada uno de los candidatos
        double cuotaCandidato1 = 1/probabilidadCandidato1;
        double cuotaCandidato2 = 1/probabilidadCandidato2;
        System.out.println("Es hora de apostar, bienvenidx a la partida de candidato 1 y candidato 2");
        System.out.println("La probabilidad de que gane el candidato 1 es de: " + probabilidadCandidato1);
        System.out.println("La probabilidad de que gane el candidato 2 es de: " + probabilidadCandidato2);
        System.out.println("Si desea apostar por el candidato 1 pulse 1, la cuota de apuesta es de " + cuotaCandidato1 + "pesos mexicanos");
        System.out.println("Si desea apostar por el candidato 2 pulse 2, la cuota de apuesta es de " + cuotaCandidato2 + "pesos mexicanos");

        //se dan los segundos para que el usuario decida y haga la apuesta
        //inicia la partida
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();//por quien quizo apostar el usuario

        //se descuenta la apuesta de la cuenta del usuario
        if(opcion == 1){
            usuario.cuenta.retirarApuesta(cuotaCandidato1);
        }
        else if(opcion == 2){
            usuario.cuenta.retirarApuesta(cuotaCandidato2);
        }
        else{
            System.out.println("Opcion invalida");
        }

        //vemos quien ganará la partida
        double x = random();
        boolean ganoC1 = false;
        boolean ganoC2 = false;
        if(x < probabilidadCandidato1){
            System.out.println("El candidato 1 gana la partida");
            ganoC1 = true;
            ganoC2 = false;
        } else{
            System.out.println("El candidato 2 gana la partida");
            ganoC1 = false;
            ganoC2 = true;
        }

        //se ve si el usuario perdió o ganó la apuesta
        if(opcion == 1){
            if(ganoC1){
                //calculamos la cuota que ganará el usuario por haber apostado por el candidato 1
                double premioGanador = cuotaCandidato1*cuotaCandidato1;
                usuario.cuenta.depositarPremio(premioGanador);
                System.out.println("Felicidades, has ganado la apuesta, se abonará a tu cuenta " + premioGanador + "pesos mexicanos");
            } else{
                System.out.println("Lo siento, has perdido la apuesta");
            }
        } 
        else if (opcion == 2){
            if(ganoC2){
                //calculamos la cuota que ganará el usuario por haber apostado por el candidato 2
                double premioGanador = cuotaCandidato2*cuotaCandidato2;
                usuario.cuenta.depositarPremio(premioGanador);
                System.out.println("Felicidades, has ganado la apuesta");
            } else{
                System.out.println("Lo siento, has perdido la apuesta");
            }
        }
    }
        
        


}

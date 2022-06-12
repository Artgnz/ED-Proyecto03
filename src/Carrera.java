package src.edd;
import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;
import java.util.Random;
/**
 *Clase que representa a una carrera del sistema de apuestas.
 * @author Arturo González Peñaloza
 * @author Emilio Arsenio Raudry Rico
 * @author Fernanda Ameyalli Osorio Morales
*/
public class Carrera{
    private Competidor[] competidores;
    private Competidor[] ranking;
    private int idCarrera;
    private Usuario usuario;
    private Cuenta cuenta;
    private static Scanner sc;
    /**
     *Constructor sin parametros.
     */
    public Carrera(){
	//Crear los competidores
	competidores = new Competidor[10];
        ranking = new Competidor[10];

	for(int i=0; i<10;i++ ){
	    Competidor competidor = new Competidor("Caballo " + i+1);
	    competidores[i] = competidor;
	}

	for(int i=0; i<10;i++ ){
	    ranking[i] = null;
	}

	this.idCarrera = 0;
    }
    /**
     *Permite la ejecucion de una carrera.
     *@param usuario Usuario.
     *@param cuenta Cuenta del usuario.
     */
    public void ejecutarCarrera(Usuario usuario, Cuenta cuenta){

        this.usuario = usuario;
        this.cuenta = cuenta;

	if(this.idCarrera==0)
	    this.crearHistorial();

	this.calcularCompetidores();

	System.out.println("Momento de apostar, apueste al caballo que crea que va a ganar.");
	
	this.imprimirCuotas();

	sc = new Scanner(System.in);

	int opcion = getInt("Introduzca el numero del caballo (1-10): ", "Ingerese una opcion valida.",1,10);

	sc.nextLine();
	
	System.out.println("Introduzca la cantidad a apostar: ");
	
	double entrada = sc.nextDouble();

	//Las excepciones, no me acuerdo ahhh
	
	Competidor apostado = competidores[opcion-1];

	if(cuenta.validarApuesta(entrada) == true){
	    cuenta.retirarApuesta(entrada);
	    System.out.println(cuenta.consultarSaldo());
        }

	else{
	    usuario.ajustesCuenta();
        }

	System.out.println("Comienza carrera!");
	
	this.rankeo();

	Competidor ganador = ranking[0];

	this.imprimirResultados();

	System.out.println("Ganador : " + ganador.getNombre());

	if(apostado.equals(ganador)){
	    System.out.println("Felicidades, has ganando la apuesta!");
	    apostado.calcularMonto(entrada);
	    cuenta.depositarPremio(apostado.getMonto());
	}
	
	for(int i=0; i<10; i++){
	    competidores[i].actualizarHistorial(i+1);
	}

	this.incrementarId();
	
    }
    /**
       Crea un historial con las ultimas cinco posiciones de cada competidor.
     */
    public void crearHistorial(){

	Random aleatorio = new Random();

	for(int i = 0; i<5; i++){
	    Lista<Integer> lista = new Lista<>();
	    for(int j=0; j<10; j++){

		int n;
		
		do{
		     n = 1 + aleatorio.nextInt(9);

		}while(lista.contains(n));
		
		competidores[j].actualizarHistorial(n);
		lista.add(n);
	    }   
	}	
    }
    /**
     *Posiciona a los competidores en sus respectivos lugares una vez iniciada la carrera.
     */
    public void rankeo(){
	Competidor mayor = this.obtenerMayor();
	Random aleatorio = new Random();
	Lista<Integer> lista = new Lista<>();

	for(int i=0;i<10; i++){
	    if(competidores[i].equals(mayor))
		ranking[0]=competidores[i];
	    int n;

	    do{
		 n = 1 + aleatorio.nextInt(8);
	    }while(lista.contains(n));

	    ranking[n]=competidores[i];
	    lista.add(n);
	}
    }

    /**
     *Devuelve al primer lugar, aquel competidor con mayores probabilidades de ganar.
     *@return Competidor Competidor ganador.
     */
    public Competidor obtenerMayor(){
	Competidor mayor=null;
	double mayorProba=0;

	for(int i=0; i<10; i++){
	    if(competidores[i].getProbabilidad()>mayorProba){
		mayorProba = competidores[i].getProbabilidad();
		mayor = competidores[i];
	    }
	}
	return mayor;
    }
    /**
     *Incrementa en 1 al Id de la carrera.
     */
    public void incrementarId(){
	this.idCarrera = this.idCarrera + 1;
    }

    /**
     *Calcula las probabilidades y cuotas de cada competidor.
     */
    public void calcularCompetidores(){
	for(int i = 0; i<10; i++){
	    competidores[i].calcularProbabilidad(10, competidores[i].getHistorial().size());
	    competidores[i].calcularCuota();
	}
    }
    /**
     *Imprime las cuotas de los jugadores.
     */
    public void imprimirCuotas(){
	System.out.println("Cuotas de los competidores: ");
	for(int i=0; i<10; i++){
	    System.out.println(competidores[i].getNombre() + ": " + competidores[i].getCuota());
	}
    }
    /**
     *Imprime los resultados de la carrera.
     */
    public void imprimirResultados(){
	System.out.println("Resultados de la carrera: ");
	for(int i = 0; i<10;i++){
	    System.out.println("Posicion " + i + ": " + ranking[i].getNombre());
	}
    }

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
            if (sc.hasNextInt()) {
                val = sc.nextInt();
                if (val < min || max < val) {
                    System.out.println(error);
                } else {
                    return val;
                }
            } else {
                sc.next();
                System.out.println(error);
            }
        }
    }
 
}

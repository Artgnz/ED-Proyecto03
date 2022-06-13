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

    public void prepararCarrera(){
	System.out.println("1");

	if(this.idCarrera==0)
	    this.crearHistorial();
	System.out.println("2");

	this.calcularCompetidores();
	System.out.println("3");
    }
    /**
     *Permite la ejecucion de una carrera.
     *@param usuario Usuario.
     *@param cuenta Cuenta del usuario.
     */
    public void ejecutarCarrera(Usuario usuario, Cuenta cuenta, int respuesta, Thread thread) {

	
        this.usuario = usuario;
        this.cuenta = cuenta;
	if (respuesta == 1) {
	    System.out.println(Colors.ANSI_GREEN + "Momento de apostar, apueste al caballo que crea que va a ganar." + Colors.ANSI_RESET);
	    sc = new Scanner(System.in);

	    int opcion = Entrada.getInt(Colors.ANSI_GREEN + "Introduzca el numero del caballo (1-10): " + Colors.ANSI_RESET, "Ingrese una opcion valida.",1,10);

	    sc.nextLine();
	
	    System.out.println(Colors.ANSI_GREEN + "Introduzca la cantidad a apostar: " + Colors.ANSI_RESET);
	
	    double entrada = sc.nextDouble();
	
	    Competidor apostado = competidores[opcion-1];

	    if(cuenta.validarApuesta(entrada) == true){
		cuenta.retirarApuesta(entrada);
		System.out.println(cuenta.consultarSaldo());
	    }

	    else{
		usuario.ajustesCuenta();
	    }
	    
	    while (thread.isAlive()) {}
	    System.out.println(Colors.ANSI_CYAN + "Comienza carrera!" + Colors.ANSI_RESET);
	
	    this.rankeo();

	    Competidor ganador = ranking[0];

	    this.imprimirResultados();

	    System.out.println(Colors.ANSI_CYAN + "Ganador : " + ganador.getNombre() + Colors.ANSI_RESET);

	    if(apostado.equals(ganador)){
		System.out.println(Colors.ANSI_GREEN + "Felicidades, has ganando la apuesta!"+ Colors.ANSI_RESET);
		apostado.calcularMonto(entrada);
		cuenta.depositarPremio(apostado.getMonto());
	    } else {
		System.out.println(Colors.ANSI_RED + "Has perdido." + Colors.ANSI_RESET);
	    }
	
	    for(int i=0; i<10; i++){
		competidores[i].actualizarHistorial(i+1);
	    }

	    this.incrementarId();
	} else {
	    System.out.println(Colors.ANSI_CYAN + "Comienza carrera!" + Colors.ANSI_RESET);
	
	    this.rankeo();

	    System.out.println(Colors.ANSI_RED + "acaba rankeo" + Colors.ANSI_RESET);

	    Competidor ganador = ranking[0];

	    this.imprimirResultados();

	    System.out.println(Colors.ANSI_CYAN + "Ganador : " + ganador.getNombre() + Colors.ANSI_RESET);

	    for(int i=0; i<10; i++){
		competidores[i].actualizarHistorial(i+1);
	    }	    
	}

	

	
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
		    n = 1 + aleatorio.nextInt(10);
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
	    if(competidores[i].equals(mayor)) {
		ranking[0]=competidores[i];
		continue;
	    }
	    int n;

	    do{
		 n = 1 + aleatorio.nextInt(9);
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
	System.out.println(Colors.ANSI_CYAN + "Cuotas de los competidores: " + Colors.ANSI_RESET);
	for(int i=0; i<10; i++){
	    System.out.println(competidores[i].getNombre() + ": " + competidores[i].getCuota());
	}
    }
    /**
     *Imprime los resultados de la carrera.
     */
    public void imprimirResultados(){
	System.out.println(Colors.ANSI_GREEN + "Resultados de la carrera: " + Colors.ANSI_RESET);
	for(int i = 0; i<10;i++){
	    System.out.println("Posicion " + i + ": " + ranking[i].getNombre());
	}
    }
 
}

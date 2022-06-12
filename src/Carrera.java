package src.edd;
import java.lang.Comparable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;
import java.util.Random;
import java.text.DecimalFormat;
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
	int n;
	for(int i=0; i<10;i++ ){
	    n= i+1;
	    Competidor competidor = new Competidor("Caballo " + n);
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
    public void ejecutarCarrera(Usuario usuario, Cuenta cuenta, int respuesta){

	
        this.usuario = usuario;
        this.cuenta = cuenta;
	if (respuesta == 1) {
	    System.out.println("Momento de apostar, apueste al caballo que crea que va a ganar.");
	    sc = new Scanner(System.in);

	    int opcion = getInt("Introduzca el numero del caballo (1-10): ", "Ingerese una opcion valida.",1,10);

	    sc.nextLine();
	
	    System.out.println("Introduzca la cantidad a apostar: ");
	
	    double entrada = sc.nextDouble();
	
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
	    } else {
		System.out.println("Has perdido.");
	    }
	
	    for(int i=0; i<10; i++){
		competidores[i].actualizarHistorial(i+1);
	    }

	    this.incrementarId();
	} else {
	    System.out.println("Comienza carrera!");
	
	    this.rankeo();

	    System.out.println("acaba rankeo");

	    Competidor ganador = ranking[0];

	    this.imprimirResultados();

	    System.out.println("Ganador : " + ganador.getNombre());

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
        Competidor[] aux = this.competidores;
	double acumulador = 0;
	double aleatorio = Math.random();
	Competidor mayor = null;
	bubbleSort(aux);
	for(int i=0; i<10;i++){
	    if(( aleatorio < aux[i].getProbabilidad() + acumulador) || (i==9)){
		mayor = aux[i];
		break;
	    }
	acumulador = acumulador + aux[i].getProbabilidad();
	} 
	return mayor;
    }

   /**
     Metodo auxiliar que permite ordenar un arreglo de Competidores por Bubblesort con base en sus probabilides de ganar.
     @param array Arreglo con los numeros a ordenar.
     */
    public static void bubbleSort(Competidor[] array){
	for(int i=0; i < array.length; i++){ //Iterador que permite recorrer todo el arreglo.
	    for(int j = 0; j < array.length - 1; j++){ //Iterador que permite recorrer a todo el arreglo, hasta poner el mayor al final de este
		if(array[j].getProbabilidad() < array[j+1].getProbabilidad()){ //Si el elemento actual del arreglo es mayor que su sucesor, estos se intercambian
		    Competidor aux = array[j];
		    array[j] = array[j+1];
		    array[j+1] = aux;
		}
	    }
	}
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
	DecimalFormat formato = new DecimalFormat("#.##");
	for(int i=0; i<10; i++){
	    System.out.println(competidores[i].getNombre() + ": " + formato.format(competidores[i].getCuota()));
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

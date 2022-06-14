# Proyecto3

Sistema de apuestas

Integrantes:
González Peñaloza Arturo 319091193
Osorio Morales Fernanda Ameyalli
Emilio Arsenio

Para compilar el programa:

Para ejecutar el programa:
Estructura del proyecto:
# Clases:
1. Colors:
   Esta clase contiene cadenas de texto para dar formato a las cadenas de texto en la terminal. 
   Todos sus atributos son constantes y estáticos.
2. Serializador:
   Esta clase permite la serialización y deserialización de objetos. Contiene dos métodos estáticos.
   Uno para serializar y otro para deserializar.
3. Usuarios:
   Esta clase almacena a los usuarios que se han registrado en el sistema.
   Contiene métodos para saber si existe un usuario, conseguir un usuario y agregar a un usuario.
4. Entrada:
   Permite la entrada de datos de manera que se introduzcan datos válidos.
   Contiene métodos para ingresar cadenas y números enteros.
5. Torneo:
   Moldea el funcionamiento del torneo de acuerdo a lo que estarán realizando los candidatos y los movimientos que decida hacer el usuario.
6. Carrera:
   Moldea el funcionamiento de las carreras de acuerdo a lo que estarán realizando los competidores y los movimientos que decida hace el usuario.
7. Cuenta: 
   Moldea los movimientos que se pueden llevar a cabo con las cuentas de los usuarios, acciones como depositar, generar apuestas y cobrar premios son algunas de ellas .

# Inconvenientes
Para la realización del proyecto surgieron inconvenientes para el uso de los hilos y la entrada de datos. Nos resultó complicado combinar el uso de hilos con la lectura de datos, esto surgió 
por lo siguiente:
1. Los métodos de lectura de Scanner bloquean los hilos y no es posible salirse de ese estado interrumpiendo con el método interrupt de java.lang.Thread. 
2. Similarmente, se intentó algo similar con BufferedReader, donde se invocaba el método readLine de bufferedReader únicamente si había entrada en la terminal. Este método no era 
   consistente en la lectura de datos, es decir, se detenía el hilo satisfactoriamente, sin embargo, al momento de jugar la siguiente partida/ronda, si el usuario introducía un dígito, 
   este no siempre lo leía el bufferedReader. 
3. Otra alternativa que tomamos consistió en usar las clases "Future", "ExecutorService" y      "Callable" de java.util.concurrent. Con esta opción, usabamos el método submit de ExecutorService con 
   "task" que pedía la entrada de datos. Si después de cierto tiempo no se introducían datos, este hilo era interrumpido. Similarmente que con la segunda opción que tomamos, 
   la ejecución del programa continuaba, sin embargo, al momento de que el usuario introdujera un número después de no haberlo introducido en una ronda, el número no lo leía el BufferedReader.
4. Finalmente, optamos por lo siguiente:
   a. Crear un hilo que tenga la cuenta regresiva para que inicie la ronda.
   b. Crear un temporizador que después de 15 segundos le diga al usuario que la partida comenzó y necesita ingresar un dígito para ver los resultados.
   
   De esta forma, logramos que el usuario únicamente pudiera aceptar la opción de apostar durante el tiempo de espera entre partida y partida. Además, con esta opción
   no surgen problemas al momento de ver más partidas/rondas.
5. En cuanto al funcionamiento del Torneo, lo que supuso un tanto de conflicto fue la forma de 
   entender las instrucciones respecto a las apuestas y los montos, puesto que en ocasiones el PDF no era suficientemente claro respecto a ciertas condiciones, por ejemplo, la cantidad a apostar que el Usuario decidiría, puesto que primero se había entendido como que únicamente podría apostar la cuota de cada candidato y luego llegamos a la conclusión de que podía apostar cualquier otra cantidad que el deseara y la cuota serviría para ver cuanto dinero ganaría en caso de que la apuesta resultara favorable.
6. El resto de la lógica del funcionamiento del Torneo fue dividido por clases: Candidato,   Torneo, así como requerimos el uso de Usuario y Cuenta para involucrar los movimientos de "dinero" que se estarían llevando a cabo. 
7. En cuanto a lo respectivo a Carreras, nos costó un poco de trabajo idear una manera que nos permitiera crear el historial de los competidores sin que repetiera lugares. De igual manera el cálculo para la obtención de un ganador nos costó un poco de tiempo, ya que no habíamos tenido experiencia con el manejo de números aleatorios. 

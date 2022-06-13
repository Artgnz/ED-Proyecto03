package src.edd;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serializa y deserializa objetos.
 * 
 * @author Arturo González Peñaloza
 * @author Emilio Arsenio Raudry Rico
 * @author Fernanda Ameyalli Osorio Morales
 */
public class Serializador {

    /**
     * Dado un nombre de archivo y un objeto, serializa el objeto creando un archivo
     * con el nombre de archivo dado.
     * 
     * @param nombreDeArchivo nombre del archivo.
     * @param objeto          objeto que se serializa.
     * @return {@code true} si fue posible serializar el objeto.
     */
    static boolean serializar(String nombreDeArchivo, Object objeto) {
        try (FileOutputStream archivo = new FileOutputStream(nombreDeArchivo);
                ObjectOutputStream salida = new ObjectOutputStream(archivo)) {
            salida.writeObject(objeto);
            salida.writeObject(objeto);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Dado el nombre de archivo, recrea ese objeto.
     * 
     * @param nombreDeArchivo nombre del archivo.
     * @return {@code Object} objeto que se recreó a partir del archivo. Si no fue
     *         posible recrearlo, regresa null.
     */
    static Object deSerializar(String nombreDeArchivo) {
        try (FileInputStream archivo = new FileInputStream(nombreDeArchivo);
                ObjectInputStream entrada = new ObjectInputStream(archivo)) {
            Object object = entrada.readObject();
            return object;
        } catch (Exception ex) {
            return null;
        }
    }
}

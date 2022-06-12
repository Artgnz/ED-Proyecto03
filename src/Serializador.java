package src.edd;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Serializador {
    static boolean serializar(String nombreDeArchivo, Object objeto) {
        try {   
            FileOutputStream archivo = new FileOutputStream(nombreDeArchivo);
            ObjectOutputStream salida = new ObjectOutputStream(archivo);
              
            salida.writeObject(objeto);
            salida.writeObject(objeto);
              
            salida.close();
            archivo.close();
            return true;
        }
        catch(Exception ex) {
            return false;
        }   
    }

    static Object deSerializar(String nombreDeArchivo) {
        try {   
            FileInputStream archivo = new FileInputStream(nombreDeArchivo);
            ObjectInputStream entrada = new ObjectInputStream(archivo);

            Object object = entrada.readObject();
              
            archivo.close();
            entrada.close();
            return object;
        } catch(Exception ex){
            return null;
        }
    }
}

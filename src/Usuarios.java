package src.edd;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Almacena a usuarios.
 * 
 * @author Arturo González Peñaloza
 * @author Emilio Arsenio Raudry Rico
 * @author Fernanda Ameyalli Osorio Morales
 */
public class Usuarios implements Serializable {
    /**
     * Lista de usuarios almacenados.
     */
    private Lista<Usuario> usuarios;

    /**
     * Constructor.
     */
    public Usuarios() {
        usuarios = new Lista<>();
    }

    /**
     * Busca a un usuario en la lista.
     * 
     * @param nombreUsuario nombre del usuario.
     * @return <tt>true</tt> si el usuario está en la lista.
     */
    public boolean contieneUsuario(String nombreUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dado el nombre de un usuario, lo regresa. ®param nombre nombre del usuario
     * 
     * @return {@code Usuario} usuario que se encontró.
     * @throws NoSuchElementException si no se encuentra el nombre en la colección.
     */
    public Usuario getUsuario(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombre)) {
                return usuario;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Agrega a un usuario a la colección.
     * 
     * @param Usuario usuario a agregar.
     */
    public void agregaUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

}

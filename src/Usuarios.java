package src.edd;

import java.io.Serializable;
import java.util.NoSuchElementException;


import src.edd.Usuario;

public class Usuarios implements Serializable {
    private Lista<Usuario> usuarios;

    public Usuarios() {
        usuarios = new Lista<>();
    }

    public boolean contieneUsuario(String nombreUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }

    public Usuario getUsuario(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombre)) {
                return usuario;
            }
        }
        throw new NoSuchElementException();
    }
    public void agregaUsuario(Usuario usuario) {
        System.out.println("Se agregó a " + usuario);

        usuarios.add(usuario);
    }


}

package br.infnet.edu.at_java.Service;


import br.infnet.edu.at_java.DTO.UsuarioDTOInput;
import br.infnet.edu.at_java.Model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsuarioService {

    private List<Usuario> listaUsuarios = new ArrayList<>();

    public List<Usuario> listar() {
        return new ArrayList<>(listaUsuarios);
    }

    public void inserir(UsuarioDTOInput usuarioDTOInput) {
        Usuario u = new Usuario();
        u.setId(listaUsuarios.size() + 1);
        u.setNome(usuarioDTOInput.getNome());
        u.setSenha(usuarioDTOInput.getSenha());
        listaUsuarios.add(u);
    }


    public void alterar(UsuarioDTOInput usuario) {
        listaUsuarios.stream()
                .filter(u -> Objects.equals(u.getNome(), usuario.getNome()))
                .filter(u -> Objects.equals(u.getSenha(), usuario.getSenha()))
                .findFirst()
                .ifPresent(u -> {
                    u.setNome(usuario.getNome());
                    u.setSenha(usuario.getSenha());
                });
    }


    public Usuario buscar(int id) {
        return listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void excluir(int id) {
        listaUsuarios.removeIf(u -> u.getId() == id);
    }
}

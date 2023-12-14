package br.infnet.edu.at_java.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private int id;
    private String nome;
    private String senha;
    private String email;
}
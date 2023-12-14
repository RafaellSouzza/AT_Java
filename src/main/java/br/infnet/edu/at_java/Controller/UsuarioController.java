package br.infnet.edu.at_java.Controller;

import br.infnet.edu.at_java.DTO.UsuarioDTOInput;
import br.infnet.edu.at_java.DTO.UsuarioDTOOutput;
import br.infnet.edu.at_java.Service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static spark.Spark.*;


@RestController
public class UsuarioController {


    UsuarioService usuarioService =  new UsuarioService();
    ObjectMapper objectMapper = new ObjectMapper();
    ModelMapper modelMapper = new ModelMapper();

    private void respostasRequisicoes() {
        get("/usuarios/:id", (request, response) -> {
            response.type("application/json");

            try {
                int id = Integer.parseInt(request.params(":id"));
                UsuarioDTOOutput usuarioDTOOutput = modelMapper.map(usuarioService.buscar(id),UsuarioDTOOutput.class);

                if (usuarioDTOOutput != null) {
                    response.status(200);
                    return objectMapper.writeValueAsString(usuarioDTOOutput);
                } else {
                    response.status(404);
                    return "{}";
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return "{\"message\":\"ID inválido.\"}";
            } catch (Exception e) {
                response.status(500);
                return "{\"message\":\"Erro interno no servidor.\"}";
            }
        });
        delete("/usuarios/:id", (request, response) -> {
            try {
                int id = Integer.parseInt(request.params(":id"));
                usuarioService.excluir(id);
                response.status(204);
                return "";
            } catch (NumberFormatException e) {
                response.status(400);
                return "{\"message\":\"ID inválido.\"}";
            } catch (NoSuchElementException  e ) {
                response.status(404);
                return "{\"message\":\"Usuário não encontrado.\"}";
            } catch (Exception e) {
                response.status(500);
                return "{\"message\":\"Erro interno no servidor.\"}";
            }
        });
        post("/usuarios", (request, response) -> {
            response.type("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                UsuarioDTOInput usuarioInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
                usuarioService.inserir(usuarioInput);
                response.status(201);

                return objectMapper.writeValueAsString(usuarioInput);
            } catch (JsonProcessingException e) {
                response.status(400);
                return "{\"message\":\"Erro ao processar JSON.\"}";
            } catch (Exception e) {
                response.status(500);
                return "{\"message\":\"Erro interno no servidor.\"}";
            }
        });
        put("/usuarios", (request, response) -> {
            response.type("application/json");
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                UsuarioDTOInput usuarioInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
                usuarioService.alterar(usuarioInput);
                response.status(200);

                return objectMapper.writeValueAsString(usuarioInput);
            } catch (JsonProcessingException e) {
                response.status(400);
                return "{\"message\":\"Erro ao processar JSON.\"}";
            } catch (Exception e) {
                response.status(500);
                return "{\"message\":\"Erro interno no servidor.\"}";
            }
        });
    }

    public static class ErrorMessage {
        private String message;
        public ErrorMessage(String message) {
            this.message = message;
        }
    }

}
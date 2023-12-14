package br.infnet.edu.at_java.Service;

import br.infnet.edu.at_java.DTO.UsuarioDTOInput;
import br.infnet.edu.at_java.Model.UserResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {


    @Test
    public void validaInsercaoUsuario() throws Exception {
        URL urlRandomUser = new URL("https://randomuser.me/api/");
        HttpURLConnection conRandomUser = (HttpURLConnection) urlRandomUser.openConnection();
        conRandomUser.setRequestMethod("GET");

        UserResponse userResponse;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            userResponse = objectMapper.readValue(conRandomUser.getInputStream(), UserResponse.class);
        } finally {
            conRandomUser.disconnect();
        }
        URL urlMinhaApi = new URL("http://localhost:8080/usuarios");
        HttpURLConnection conMinhaApi = (HttpURLConnection) urlMinhaApi.openConnection();
        conMinhaApi.setRequestMethod("POST");
        conMinhaApi.setDoOutput(true);
        conMinhaApi.setRequestProperty("Content-Type", "application/json");

        UsuarioDTOInput u = new UsuarioDTOInput();
            UserResponse.User user = userResponse.getResults().get(0);

        u.setNome(user.getName().getFirst()+" "+user.getName().getLast());
        u.setSenha("1234");

        try {
            String jsonInput = new ObjectMapper().writeValueAsString(u);
            try (var os = conMinhaApi.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Valida se o código de resposta é 201 (Created)
            int responseCode = conMinhaApi.getResponseCode();
            assertEquals(201, responseCode, "A inserção do usuário falhou.");
        } finally {
            conMinhaApi.disconnect();
        }
    }
}

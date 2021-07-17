package kitou.user;

import kitou.business.UserService;
import kitou.data.entities.User;
import kitou.data.repositories.UserRepository;
import kitou.util.CRest;
import kitou.util.Role;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /** Genera un token corriendo la aplicación y luego reemplazas la variable.
     * Usar el usuario testkitou@gmail.com para generar el token.
     * Constraseña: q1-w2-e3-r4
     * Ejemplo: "Bearer ya29.a0Raw... */
    public final String bearerToken = "Bearer ya29.a0ARrdaM8dQrO9K6JUbvUvoSOV3LDTr1Ynxwu1V6mLYe8jqHfzn7BPjB2C2hRFpYFn_HaSje0RsrFWK7cKJ5Vulda0X5MG7YPZpACNDaxfrPezxVJy7WUS3xntkX6pqlKktLMek3ybj8CRzkXS8Rx2Rq0HqDukUw";

    @Test
    @Order(1)
    void initializeAdmin() throws Exception{
        var initialUser = new User();
        initialUser.setEmail(CRest.ADMIN_EMAIL);
        initialUser.setRole(Role.ADMIN.value);
        userRepository.save(initialUser);

        assertNotNull(userRepository.findUserByEmail(CRest.ADMIN_EMAIL));
    }

    @Test
    @Order(2)
    void register() throws Exception{
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\"" +
                        ", \"targetEmail\": \"test.user@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(true,"Usuario creado con éxito.")));
    }

    @Test
    @Order(3)
    void badDoubleRegister() throws Exception{
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\"" +
                        ", \"targetEmail\": \"test.user@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Usuario ya existente.")));
    }

    @Test
    @Order(4)
    void badPermission() throws Exception{
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@gmail.com\"" +
                        ", \"targetEmail\": \"test.user2@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"No se tiene los suficientes privilegios.")));
    }

    @Test
    @Order(4)
    void login() throws Exception{
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(true,"Sesión iniciada.","\"role\": 2")));
    }

    @Test
    @Order(4)
    void badTokenLogin() throws Exception{
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\"}")
                .header("Authorization","Bearer badtoken"))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(4)
    void badEmailLogin() throws Exception{
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(5)
    void demote() throws Exception{
        mvc.perform(post("/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\", " +
                        "\"targetEmail\": \"test.user@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(true,"Democión realizada.")));
    }

    @Test
    @Order(6)
    void badOverDemote() throws Exception{
        mvc.perform(post("/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\", " +
                        "\"targetEmail\": \"test.user@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"No existe un rol inferior.")));
    }

    @Test
    @Order(6)
    void badTargetDemote() throws Exception{
        mvc.perform(post("/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\", " +
                        "\"targetEmail\": \"test.baduser@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(7)
    void promote() throws Exception{
        mvc.perform(post("/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\", " +
                        "\"targetEmail\": \"test.user@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(true,"Promoción realizada.")));
    }

    @Test
    @Order(8)
    void badOverPromote() throws Exception{
        mvc.perform(post("/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\", " +
                        "\"targetEmail\": \""+ CRest.ADMIN_EMAIL+"\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"No existe un rol superior.")));
    }

    @Test
    @Order(8)
    void badTargetPromote() throws Exception{
        mvc.perform(post("/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ CRest.ADMIN_EMAIL+"\", " +
                        "\"targetEmail\": \"test.baduser@gmail.com\"}")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }
}
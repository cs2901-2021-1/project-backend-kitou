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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public final String bearerToken = "Bearer ya29.a0ARrdaM-KhBqhe0s-_QRwgyfCkRaTowLjniibvAKpzDoSFhIlOv-lnSwl4gePTSvQGa41oUTO9Myvbg-98N_BuVgy-0w9N6ko8beoVKgp1ZO_zF8VaBAXxQARkwaXA0XE10XTp-AlsjmLHauQF76-AyvE5IPbSA";

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
                .content("{\"email\": \"test.user@gmail.com\"}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(true,"Usuario creado con éxito.")));
    }

    @Test
    @Order(3)
    void getUsers() throws Exception{
        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string("[{\"role\":2,\"id\":1,\"email\":\"testkitou@gmail.com\"},{\"role\":1,\"id\":2,\"email\":\"test.user@gmail.com\"}]"));
    }

    @Test
    @Order(3)
    void badCredentialsGetUsers() throws Exception{
        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization","Bearer badtoken"))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(3)
    void badDoubleRegister() throws Exception{
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@gmail.com\"}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Usuario ya existente.")));
    }

    @Test
    @Order(4)
    void badPermissionRegister() throws Exception{
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.baduser@gmail.com\"}")
                .header("UserEmail", "test.user@gmail.com")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"No se tiene los suficientes privilegios.")));
    }

    @Test
    @Order(4)
    void login() throws Exception{
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(true,"Sesión iniciada.","\"role\": 2")));
    }

    @Test
    @Order(4)
    void badTokenLogin() throws Exception{
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization","Bearer badtoken"))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(4)
    void badEmailLogin() throws Exception{
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", "test.user@gmail.com")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(5)
    void demote() throws Exception{
        mvc.perform(post("/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@gmail.com\"}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(true,"Democión realizada.")));
    }

    @Test
    @Order(6)
    void badOverDemote() throws Exception{
        mvc.perform(post("/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@gmail.com\"}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"No existe un rol inferior.")));
    }

    @Test
    @Order(6)
    void badTargetDemote() throws Exception{
        mvc.perform(post("/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.baduser@gmail.com\"}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(7)
    void promote() throws Exception{
        mvc.perform(post("/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@gmail.com\"}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(true,"Promoción realizada.")));
    }

    @Test
    @Order(8)
    void badOverPromote() throws Exception{
        mvc.perform(post("/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+CRest.ADMIN_EMAIL+"\"}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"No existe un rol superior.")));
    }

    @Test
    @Order(8)
    void badTargetPromote() throws Exception{
        mvc.perform(post("/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.baduser@gmail.com\"}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(9)
    void condition() throws Exception{
        mvc.perform(get("/condition")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string("es algo"));
    }

    @Test
    @Order(10)
    void badPermissionCondition() throws Exception{
        mvc.perform(get("/condition")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", "test.user@gmail.com")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(10)
    void badTokenCondition() throws Exception{
        mvc.perform(get("/condition")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization","Bearer badtoken"))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(10)
    void badUserCondition() throws Exception{
        mvc.perform(get("/condition")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", "baduser")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(11)
    void report() throws Exception{
        mvc.perform(get("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string("es algo"));
    }

    @Test
    @Order(11)
    void reportCondition() throws Exception{
        mvc.perform(post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ciclo\": 1, \"carrera\": \"CS\", \"malla\": 203}")
                .header("UserEmail", CRest.ADMIN_EMAIL)
                .header("Authorization",bearerToken))
                .andExpect(content().string("salio bien"));
    }

    @Test
    @Order(12)
    void badPermissionReport() throws Exception{
        mvc.perform(get("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .header("UserEmail", "test.user@gmail.com")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }

    @Test
    @Order(12)
    void badPermissionReportCondition() throws Exception{
        mvc.perform(post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ciclo\": 1, \"carrera\": \"CS\", \"malla\": 203}")
                .header("UserEmail", "test.user@gmail.com")
                .header("Authorization",bearerToken))
                .andExpect(content().string(CRest.responseMessage(false,"Credenciales inválidas.")));
    }
}
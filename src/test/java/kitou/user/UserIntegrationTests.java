package kitou.user;

import kitou.business.UserService;
import kitou.config.ConstConfig;
import org.junit.jupiter.api.Test;
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
class UserIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Test
    void register_login() throws Exception{

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_TRUE));

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_TRUE));

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_FALSE));

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.BADuser@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_FALSE));
    }

    @Test
    void promote_demote() throws Exception{

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \""+ConstConfig.ADMIN_EMAIL+"\"}"));

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test.user@utec.edu.pe\"}"));

        assertNotNull(userService.validateUser("test.user@utec.edu.pe"));
        assertThrows(IllegalArgumentException.class,()->userService.validateUser("test.BADuser@utec.edu.pe"));

        mvc.perform(post("/mod/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adminEmail\": \""+ConstConfig.ADMIN_EMAIL+"\", " +
                        "\n\"userEmail\": \"test.user@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_TRUE));

        mvc.perform(post("/mod/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adminEmail\": \""+ConstConfig.ADMIN_EMAIL+"\", " +
                        "\n\"userEmail\": \"test.user@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_FALSE));

        mvc.perform(post("/mod/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adminEmail\": \""+ConstConfig.ADMIN_EMAIL+"\", " +
                        "\n\"userEmail\": \"test.user@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_TRUE));

        mvc.perform(post("/mod/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adminEmail\": \""+ConstConfig.ADMIN_EMAIL+"\", " +
                        "\n\"userEmail\": \""+ConstConfig.ADMIN_EMAIL+"\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_FALSE));

        mvc.perform(post("/mod/promote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adminEmail\": \"test.user@utec.edu.pe\", " +
                        "\n\"userEmail\": \"test.user@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_FALSE));

        mvc.perform(post("/mod/demote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adminEmail\": \"test.user@utec.edu.pe\", " +
                        "\n\"userEmail\": \"test.user@utec.edu.pe\"}"))
                .andExpect(content().string(ConstConfig.SUCCESS_FALSE));
    }
}
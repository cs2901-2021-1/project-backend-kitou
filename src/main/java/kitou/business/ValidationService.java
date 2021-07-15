package kitou.business;

import kitou.data.dtos.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.logging.Logger;

@Service
public class ValidationService {

    static final Logger logger = Logger.getLogger(ValidationService.class.getName());

    public void validateToken(String accessToken, String email){
        if(!email
                .equals(Objects
                .requireNonNull(new RestTemplate()
                .getForEntity("https://www.googleapis.com/oauth2/v3/tokeninfo?access_token="+accessToken
                        , UserDTO.class).getBody()).getEmail())){
            throw new UsernameNotFoundException("Correo inválido.");
        }
    }
}

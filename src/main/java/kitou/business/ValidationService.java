package kitou.business;

import javassist.NotFoundException;
import kitou.data.dtos.UserDTO;
import kitou.data.repositories.UserRepository;
import kitou.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.logging.Logger;

@Service
public class ValidationService {

    @Autowired
    public UserRepository userRepository;

    static final Logger logger = Logger.getLogger(ValidationService.class.getName());

    public ValidationService validateTokenAndRoleUserless(String accessToken, String email, Role role) throws NotFoundException {
        var user = userRepository.findUserByEmail(email);
        validateToken(accessToken, email);
        validateRole(user.getRole(), role);
        return this;
    }

    public ValidationService validateToken(String accessToken, String email){
        UserDTO request;
        try{
            request = Objects.requireNonNull(new RestTemplate()
                    .getForEntity("https://www.googleapis.com/oauth2/v3/tokeninfo?access_token="+accessToken
                            , UserDTO.class).getBody());
        }catch (Exception e){
            throw new BadCredentialsException("Credenciales inválidas.");
        }
        if(!email.equals(request.getEmail())){
            throw new BadCredentialsException("Credenciales inválidas.");
        }
        return this;
    }

    public ValidationService validateRole(Integer userRole, Role role){
        if(userRole < role.value){
            throw new AuthorizationServiceException("No se tiene los suficientes privilegios.");
        }
        return this;
    }

    public ValidationService validateUniqueness(String email){
        if(userRepository.findUserByEmail(email) != null)
            throw new IllegalArgumentException("Usuario ya existente.");
        return this;
    }
}

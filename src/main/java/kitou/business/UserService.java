package kitou.business;

import kitou.util.CRest;
import kitou.data.dtos.UserDTO;
import kitou.data.entities.User;
import kitou.data.repositories.UserRepository;
import kitou.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService{

    static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ValidationService validationService;

    public User findUser(String email){
        var user = userRepository.findUserByEmail(email);
        if(user != null){
            return user;
        }
        throw new BadCredentialsException("Credenciales inválidas.");
    }

    public String login(String accessToken, String email){
        try{
            var user = findUser(email);
            validationService.validateToken(accessToken, email);
            return CRest.responseMessage(true,"Sesión iniciada.","\"role\": "+user.getRole());
        }catch (Exception e){
            return CRest.responseMessage(false,e.getMessage());
        }
    }

    public String createUser(String accessToken, String email, UserDTO userDTO){
        try {
            var user = findUser(email);
            validationService.validateUniqueness(userDTO.getEmail())
                    .validateRole(user.getRole(),Role.ADMIN)
                    .validateToken(accessToken, user.getEmail());

            var newUser = new User();
            newUser.setEmail(userDTO.getEmail());
            newUser.setRole(Role.STANDARD.value);
            userRepository.save(newUser);
            return CRest.responseMessage(true,"Usuario creado con éxito.");
        }catch (Exception e){
            return CRest.responseMessage(false,e.getMessage());
        }
    }

    public String promoteUser(String accessToken, String email, UserDTO userDTO){
        try {
            var user = findUser(email);
            var targetUser = findUser(userDTO.getEmail());
            validationService.validateRole(user.getRole(),Role.ADMIN)
                    .validateToken(accessToken, user.getEmail());

            if(targetUser.getRole() >= Role.ADMIN.value){
                return CRest.responseMessage(false,"No existe un rol superior.");
            }
            targetUser.promote();
            userRepository.save(targetUser);
            return CRest.responseMessage(true,"Promoción realizada.");
        }catch (Exception e){
            return CRest.responseMessage(false,e.getMessage());
        }
    }

    public String demoteUser(String accessToken, String email, UserDTO userDTO){
        try {
            var user = findUser(email);
            var targetUser = findUser(userDTO.getEmail());
            validationService.validateRole(user.getRole(),Role.ADMIN)
                    .validateToken(accessToken, user.getEmail());

            if(targetUser.getRole() <= Role.REVOKED.value){
                return CRest.responseMessage(false,"No existe un rol inferior.");
            }
            targetUser.demote();
            userRepository.save(targetUser);
            return CRest.responseMessage(true,"Democión realizada.");
        }catch (Exception e){
            return CRest.responseMessage(false,e.getMessage());
        }
    }
}
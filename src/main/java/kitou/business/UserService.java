package kitou.business;

import kitou.util.ConstantUtil;
import kitou.data.dtos.UserDTO;
import kitou.data.entities.User;
import kitou.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService{
    static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ValidationService validationService;

    public User validateUser(String email) throws UsernameNotFoundException {
        var user = userRepository.findUserByEmail(email);
        if(user != null){
            return user;
        }
        throw new UsernameNotFoundException("Usuario no encontrado.");
    }

    public void validateRole(Integer userRole, Integer role){
        if(userRole < role){
            throw new AuthorizationServiceException("No se tiene los suficientes privilegios");
        }
    }

    public void validateUniquity(String email){
        if(userRepository.findUserByEmail(email) != null)
            throw new IllegalArgumentException("Usuario ya existente");
    }

    public String login(String accessToken, UserDTO userDTO){
        try{
            validationService.validateToken(accessToken, userDTO.getEmail());
            validateUser(userDTO.getEmail());
            return ConstantUtil.responseMessage(true,"Sesión iniciada.");
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }

    public String createUser(String accessToken, UserDTO userDTO){
        try {
            validationService.validateToken(accessToken, userDTO.getEmail());
            var user = validateUser(userDTO.getEmail());
            validateUniquity(userDTO.getTargetEmail());
            validateRole(user.getRole(),2);

            var new_user = new User();
            new_user.setEmail(userDTO.getTargetEmail());
            new_user.setRole(1);
            if(new_user.getEmail().equals(ConstantUtil.ADMIN_EMAIL))
                new_user.setRole(2);
            userRepository.save(new_user);
            return ConstantUtil.responseMessage(true,"Usuario creado con éxito.");
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }

    public String promoteUser(String accessToken, UserDTO userDTO){
        try {
            validationService.validateToken(accessToken, userDTO.getEmail());
            var user = validateUser(userDTO.getEmail());
            validateUniquity(userDTO.getTargetEmail());
            validateRole(user.getRole(),2);

            if(user.getRole() > 1){
                return ConstantUtil.responseMessage(false,"No existe un rol superior");
            }
            user.promote();
            userRepository.save(user);
            return ConstantUtil.responseMessage(true,"Promoción realizada");
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }

    public String demoteUser(String accessToken, UserDTO userDTO){
        try {
            validationService.validateToken(accessToken, userDTO.getEmail());
            var user = validateUser(userDTO.getEmail());
            validateUniquity(userDTO.getTargetEmail());
            validateRole(user.getRole(),2);

            if(user.getRole() < 1){
                return ConstantUtil.responseMessage(false,"No existe un rol inferior");
            }
            user.demote();
            userRepository.save(user);
            return ConstantUtil.responseMessage(true,"Democión realizada");
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }
}
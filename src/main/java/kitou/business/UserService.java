package kitou.business;

import kitou.util.ConstantUtil;
import kitou.data.dtos.UserDTO;
import kitou.data.entities.User;
import kitou.data.repositories.UserRepository;
import kitou.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User findUser(String email) throws UsernameNotFoundException {
        var user = userRepository.findUserByEmail(email);
        if(user != null){
            return user;
        }
        throw new UsernameNotFoundException("Usuario no encontrado.");
    }

    public String login(String accessToken, UserDTO userDTO){
        try{
            var user = findUser(userDTO.getEmail());
            validationService.validateToken(accessToken, userDTO.getEmail());
            return ConstantUtil.responseMessage(true,"Sesión iniciada.","\"role\": "+user.getRole());
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }

    public String createUser(String accessToken, UserDTO userDTO){
        try {
            var user = findUser(userDTO.getEmail());
            validationService.validateUniqueness(userDTO.getTargetEmail())
                    .validateRole(user.getRole(),Role.ADMIN)
                    .validateToken(accessToken, user.getEmail());

            var newUser = new User();
            newUser.setEmail(userDTO.getTargetEmail());
            newUser.setRole(Role.STANDARD.value);
            userRepository.save(newUser);
            return ConstantUtil.responseMessage(true,"Usuario creado con éxito.");
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }

    public String promoteUser(String accessToken, UserDTO userDTO){
        try {
            var user = findUser(userDTO.getEmail());
            var targetUser = findUser(userDTO.getTargetEmail());
            validationService.validateRole(user.getRole(),Role.ADMIN)
                    .validateToken(accessToken, user.getEmail());

            if(targetUser.getRole() >= Role.ADMIN.value){
                return ConstantUtil.responseMessage(false,"No existe un rol superior.");
            }
            targetUser.promote();
            userRepository.save(targetUser);
            return ConstantUtil.responseMessage(true,"Promoción realizada.");
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }

    public String demoteUser(String accessToken, UserDTO userDTO){
        try {
            var user = findUser(userDTO.getEmail());
            var targetUser = findUser(userDTO.getTargetEmail());
            validationService.validateRole(user.getRole(),Role.ADMIN)
                    .validateToken(accessToken, user.getEmail());

            if(targetUser.getRole() <= Role.REVOKED.value){
                return ConstantUtil.responseMessage(false,"No existe un rol inferior.");
            }
            targetUser.demote();
            userRepository.save(targetUser);
            return ConstantUtil.responseMessage(true,"Democión realizada.");
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }
}
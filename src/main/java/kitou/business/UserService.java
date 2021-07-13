package kitou.business;

import kitou.config.ConstConfig;
import kitou.data.dtos.UserDTO;
import kitou.data.dtos.RoleChangeDTO;
import kitou.data.entities.User;
import kitou.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService{
    static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserRepository userRepository;

    public User validateUser(String email) throws IllegalArgumentException{
        var user = userRepository.findUserByEmail(email);
        if(user != null){
            return user;
        }
        throw new IllegalArgumentException("Usuario no encontrado.");
    }

    public String login(UserDTO userDTO){
        try{
            validateUser(userDTO.getEmail());
            return ConstConfig.SUCCESS_TRUE;
        }catch (IllegalArgumentException e){
            return ConstConfig.SUCCESS_FALSE;
        }
    }

    public String createUser(UserDTO userDTO){
        if(userRepository.findUserByEmail(userDTO.getEmail()) == null){
            var user = new User();
            user.setEmail(userDTO.getEmail());
            user.setRole(1);
            if(user.getEmail().equals(ConstConfig.ADMIN_EMAIL))
                user.setRole(2);
            userRepository.save(user);
            logger.info("Usuario creado con éxito.");
            return ConstConfig.SUCCESS_TRUE;
        }
        logger.info("Ya existe un usuario con ese correo.");
        return ConstConfig.SUCCESS_FALSE;
    }

    public String promoteUser(RoleChangeDTO roleChangeDTO){
        var admin = userRepository.findUserByEmail(roleChangeDTO.getAdminEmail());
        if(admin.getRole()==2){
            var user = validateUser(roleChangeDTO.getUserEmail());
            if(user.getRole() > 1){
                return ConstConfig.SUCCESS_FALSE;
            }
            user.promote();
            userRepository.save(user);
            logger.info("Promoción realizada");
            return ConstConfig.SUCCESS_TRUE;
        }
        logger.info("Faltan permisos para ejecutar esta acción.");
        return ConstConfig.SUCCESS_FALSE;
    }

    public String demoteUser(RoleChangeDTO roleChangeDTO){
        var admin = userRepository.findUserByEmail(roleChangeDTO.getAdminEmail());
        if(admin.getRole()==2){
            var user = validateUser(roleChangeDTO.getUserEmail());
            if(user.getRole() < 1){
                return ConstConfig.SUCCESS_FALSE;
            }
            user.demote();
            userRepository.save(user);
            logger.info("Democión realizada");
            return ConstConfig.SUCCESS_TRUE;
        }
        logger.info("Faltan permisos para ejecutar esta acción.");
        return ConstConfig.SUCCESS_FALSE;
    }
}
package kitou.business;

import kitou.data.dtos.UserDTO;
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

    public User validateUser(String username) throws IllegalArgumentException{
        var user = userRepository.findUserByUsername(username);
        if(user != null){
            return user;
        }else{
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
    }

    public User auth(UserDTO userDTO){
        var user = validateUser(userDTO.getUsername());
        if(user.getPassword().equals(userDTO.getPassword())){
            logger.info(user.toString());
            return user;
        }else{
            logger.info("Contraseña incorrecta.");
            return null;
        }
    }

    public User createUser(UserDTO userDTO){
        if(userRepository.findUserByUsername(userDTO.getUsername()) == null){
            var user = new User(userDTO.getUsername(),userDTO.getPassword());
            userRepository.save(user);
            logger.info(user.toString());
            return user;
        }else{
            logger.info("Ya existe un usuario con ese nombre.");
            return null;
        }
    }

    public void promoteUser(String username, UserDTO adminDTO){
        var admin = validateUser(adminDTO.getUsername());
        if(admin.getPassword().equals(adminDTO.getPassword()) & (admin.getRole()==2)){
            var user = validateUser(username);
            user.promote();
            userRepository.save(user);
            logger.info("Promoción realizada");
        }else{
            logger.info("Faltan permisos para ejecutar esta acción.");
        }
    }

    public void demoteUser(String username, UserDTO adminDTO){
        var admin = validateUser(adminDTO.getUsername());
        if(admin.getPassword().equals(adminDTO.getPassword()) & (admin.getRole()==2)){
            var user = validateUser(username);
            user.demote();
            userRepository.save(user);
            logger.info("Democión realizada");
        }else{
            logger.info("Faltan permisos para ejecutar esta acción.");
        }
    }
}
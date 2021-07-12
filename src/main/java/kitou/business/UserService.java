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

    public User validateUser(String email) throws IllegalArgumentException{
        var user = userRepository.findUserByEmail(email);
        if(user != null){
            return user;
        }else{
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
    }

    public String login(UserDTO userDTO){
        try{
            var user = validateUser(userDTO.getEmail());
            var rpta = "{\"success\": true, \"email\":\""+user.getEmail()+"\", \"role\":\"" + user.getRole()+"\"}";
            logger.info(rpta);
            return rpta;
        }catch (IllegalArgumentException e){
            return "{\"success\": false, \"email\": null}";
        }
    }

    public String createUser(UserDTO userDTO){
        if(userRepository.findUserByEmail(userDTO.getEmail()) == null){
            var user = new User(userDTO.getEmail());
            userRepository.save(user);
            logger.info("Usuario creado con éxito.");
            return "{\"success\": true}";
        }else{
            logger.info("Ya existe un usuario con ese correo.");
            return "{\"success\": false}";
        }
    }

    public String promoteUser(String email, UserDTO adminDTO){
        var admin = userRepository.findUserByEmail(adminDTO.getEmail());
        if(admin.getRole()==2){
            var user = validateUser(email);
            user.promote();
            userRepository.save(user);
            logger.info("Promoción realizada");
            return "{\"success\": true}";
        }else{
            logger.info("Faltan permisos para ejecutar esta acción.");
            return "{\"success\": false}";
        }
    }

    public String demoteUser(String email, UserDTO adminDTO){
        var admin = userRepository.findUserByEmail(adminDTO.getEmail());
        if(admin.getRole()==2){
            var user = validateUser(email);
            user.demote();
            userRepository.save(user);
            logger.info("Democión realizada");
            return "{\"success\": true}";
        }else{
            logger.info("Faltan permisos para ejecutar esta acción.");
            return "{\"success\": false}";
        }
    }
}
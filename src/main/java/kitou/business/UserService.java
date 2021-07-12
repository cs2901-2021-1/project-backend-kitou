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

    public User auth(UserDTO userDTO) throws IllegalArgumentException {
        var user = validateUser(userDTO.getUsername());
        if (user != null) {
            return user;
        }
        else{
            throw new IllegalArgumentException("Usuario Denegado");
        }
    }



    public String login(UserDTO userDTO){
        try{
            var user = auth(userDTO);
            return "{\"success\": true, \"username\":\""+user.getUsername()+"\"}";
        }catch (IllegalArgumentException e){
            return "{\"success\": false, \"username\": null}";
        }
    }

    public String createUser(UserDTO userDTO){
        if(userRepository.findUserByUsername(userDTO.getUsername()) == null){
            var user = new User(userDTO.getUsername(),userDTO.getPassword());
            userRepository.save(user);
            logger.info("Usuario creado con éxito.");
            return "{\"success\": true}";
        }else{
            logger.info("Ya existe un usuario con ese nombre.");
            return "{\"success\": false}";
        }
    }

    public String promoteUser(String username, UserDTO adminDTO){
        var admin = auth(adminDTO);
        if(admin.getRole()==2){
            var user = validateUser(username);
            user.promote();
            userRepository.save(user);
            logger.info("Promoción realizada");
            return "{\"success\": true}";
        }else{
            logger.info("Faltan permisos para ejecutar esta acción.");
            return "{\"success\": false}";
        }
    }

    public String demoteUser(String username, UserDTO adminDTO){
        var admin = auth(adminDTO);
        if(admin.getRole()==2){
            var user = validateUser(username);
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
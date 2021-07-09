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

    public User auth(UserDTO userDTO){
        var user = userRepository.findUserByUsername(userDTO.getUsername());
        if(user.getPassword().equals(userDTO.getPassword())){
            return user;
        }
        return null;
    }

    public User createUser(UserDTO userDTO){
        var user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

    public void changeRole(UserDTO userDTO){

    }
}
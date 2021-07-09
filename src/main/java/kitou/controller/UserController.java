package kitou.controller;

import kitou.business.UserService;
import kitou.data.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {

    static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    public void auth(UserDTO userDTO){
        userService.auth(userDTO);
    }

    public void createUser(UserDTO userDTO){
        userService.createUser(userDTO);
    }

    public void changeRole(UserDTO userDTO){
        userService.changeRole(userDTO);
    }
}
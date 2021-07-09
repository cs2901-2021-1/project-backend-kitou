package kitou.controller;

import kitou.business.UserService;
import kitou.data.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@RestController
public class UserController {

    static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public void auth(@RequestBody UserDTO userDTO){
        userService.auth(userDTO);
    }

    @PostMapping("/register")
    public void createUser(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
    }

    @PostMapping("/mod")
    public void changeRole(UserDTO userDTO){
        userService.changeRole(userDTO);
    }
}
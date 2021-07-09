package kitou.controller;

import kitou.business.UserService;
import kitou.data.dtos.UserDTO;
import kitou.data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    public User auth(@RequestBody UserDTO userDTO){
        return userService.auth(userDTO);
    }

    @PostMapping("/register")
    public User createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @PostMapping("/mod/promote/{username}")
    public void promote(@PathVariable String username, @RequestBody UserDTO adminDTO){
        userService.promoteUser(username, adminDTO);
    }

    @PostMapping("/mod/demote/{username}")
    public void demote(@PathVariable String username, @RequestBody UserDTO adminDTO){
        userService.demoteUser(username, adminDTO);
    }
}
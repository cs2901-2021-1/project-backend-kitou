package kitou.controller;

import kitou.business.UserService;
import kitou.data.dtos.UserDTO;
import kitou.data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
public class UserController {

    static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;


    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody UserDTO userDTO){
        return userService.login(userDTO);
    }

    @PostMapping("/register")
    @ResponseBody
    public String createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @PostMapping("/mod/promote/{username}")
    @ResponseBody
    public String promote(@PathVariable String username, @RequestBody UserDTO adminDTO){
        return userService.promoteUser(username, adminDTO);
    }

    @PostMapping("/mod/demote/{username}")
    @ResponseBody
    public String demote(@PathVariable String username, @RequestBody UserDTO adminDTO){
        return userService.demoteUser(username, adminDTO);
    }
}
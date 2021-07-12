package kitou.controller;

import kitou.business.UserService;
import kitou.data.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin("*")
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

    @PostMapping("/mod/promote/{email}")
    @ResponseBody
    public String promote(@PathVariable String email, @RequestBody UserDTO adminDTO){
        return userService.promoteUser(email, adminDTO);
    }

    @PostMapping("/mod/demote/{email}")
    @ResponseBody
    public String demote(@PathVariable String email, @RequestBody UserDTO adminDTO){
        return userService.demoteUser(email, adminDTO);
    }
}
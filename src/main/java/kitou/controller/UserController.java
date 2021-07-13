package kitou.controller;

import kitou.business.UserService;
import kitou.data.dtos.UserDTO;
import kitou.data.dtos.RoleChangeDTO;
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

    @PostMapping("/mod/promote")
    @ResponseBody
    public String promote(@RequestBody RoleChangeDTO roleChangeDTO){
        return userService.promoteUser(roleChangeDTO);
    }

    @PostMapping("/mod/demote")
    @ResponseBody
    public String demote(@RequestBody RoleChangeDTO roleChangeDTO){
        return userService.demoteUser(roleChangeDTO);
    }
}
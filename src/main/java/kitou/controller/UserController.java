package kitou.controller;

import kitou.business.UserService;
import kitou.util.CRest;
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

    @GetMapping("/user")
    @ResponseBody
    public String getUsers(@RequestHeader(name = "Authorization") String accessToken
            , @RequestHeader(name = "UserEmail") String email){
        return userService.getUsers(accessToken.substring(7), email);
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestHeader(name = "Authorization") String accessToken
            , @RequestHeader(name = "UserEmail") String email){
        return userService.login(accessToken.substring(7), email);
    }

    @PostMapping("/register")
    @ResponseBody
    public String createUser(@RequestHeader(name = "Authorization") String accessToken
            , @RequestHeader(name = "UserEmail") String email
            , @RequestBody UserDTO userDTO){
        return userService.createUser(accessToken.substring(7), email, userDTO);
    }

    @PostMapping("/promote")
    @ResponseBody
    public String promote(@RequestHeader(name = "Authorization") String accessToken
            , @RequestHeader(name = "UserEmail") String email
            , @RequestBody UserDTO userDTO){
        return userService.promoteUser(accessToken.substring(7), email, userDTO);
    }

    @PostMapping("/demote")
    @ResponseBody
    public String demote(@RequestHeader(name = "Authorization") String accessToken
            , @RequestHeader(name = "UserEmail") String email
            , @RequestBody UserDTO userDTO){
        return userService.demoteUser(accessToken.substring(7), email, userDTO);
    }
}
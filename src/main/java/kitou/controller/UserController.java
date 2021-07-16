package kitou.controller;

import kitou.business.UserService;
import kitou.util.CRest;
import kitou.data.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin(origins = CRest.FRONT_URI)
@RestController
public class UserController {

    static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestHeader(name = "Authorization") String accessToken
            , @RequestBody UserDTO userDTO){
        return userService.login(accessToken.substring(7), userDTO);
    }

    @PostMapping("/register")
    @ResponseBody
    public String createUser(@RequestHeader(name = "Authorization") String accessToken
            , @RequestBody UserDTO userDTO){
        return userService.createUser(accessToken.substring(7), userDTO);
    }

    @PostMapping("/promote")
    @ResponseBody
    public String promote(@RequestHeader(name = "Authorization") String accessToken
            , @RequestBody UserDTO userDTO){
        return userService.promoteUser(accessToken.substring(7), userDTO);
    }

    @PostMapping("/demote")
    @ResponseBody
    public String demote(@RequestHeader(name = "Authorization") String accessToken
            , @RequestBody UserDTO userDTO){
        return userService.demoteUser(accessToken.substring(7), userDTO);
    }
}
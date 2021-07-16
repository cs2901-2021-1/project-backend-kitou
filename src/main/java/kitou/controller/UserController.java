package kitou.controller;

import kitou.business.UserService;
import kitou.util.ConstantUtil;
import kitou.data.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin(origins = ConstantUtil.FRONT_URI)
@RestController
public class UserController {

    static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestHeader(name = "accessToken") String accessToken
            , @RequestBody UserDTO userDTO){
        return userService.login(accessToken, userDTO);
    }

    @PostMapping("/register")
    @ResponseBody
    public String createUser(@RequestHeader(name = "accessToken") String accessToken
            , @RequestBody UserDTO userDTO){
        return userService.createUser(accessToken, userDTO);
    }

    @PostMapping("/promote")
    @ResponseBody
    public String promote(@RequestHeader(name = "accessToken") String accessToken
            , @RequestBody UserDTO userDTO){
        return userService.promoteUser(accessToken, userDTO);
    }

    @PostMapping("/demote")
    @ResponseBody
    public String demote(@RequestHeader(name = "accessToken") String accessToken
            , @RequestBody UserDTO userDTO){
        return userService.demoteUser(accessToken, userDTO);
    }
}
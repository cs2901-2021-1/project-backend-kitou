package kitou.controller;

import kitou.business.ConditionService;
import kitou.data.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class ConditionController {

    static final Logger logger = Logger.getLogger(ConditionController.class.getName());

    @Autowired
    ConditionService conditionService;

    @GetMapping("/condition")
    @ResponseBody
    public String fetchCondition(@RequestHeader(name = "accessToken") String accessToken
            , @RequestBody UserDTO userDTO){
        return conditionService.fetchCondition(accessToken, userDTO);
    }
}
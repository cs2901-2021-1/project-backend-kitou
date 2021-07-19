package kitou.controller;

import kitou.business.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
@CrossOrigin("*")
public class ConditionController {

    static final Logger logger = Logger.getLogger(ConditionController.class.getName());

    @Autowired
    ConditionService conditionService;

    @GetMapping("/condition")
    @ResponseBody
    public String fetchCondition(@RequestHeader(name = "Authorization") String accessToken
            , @RequestHeader(name = "UserEmail") String email){
        return conditionService.fetchCondition(accessToken.substring(7), email);
    }
}
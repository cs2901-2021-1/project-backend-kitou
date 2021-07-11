package kitou.controller;

import kitou.business.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class ConditionController {

    static final Logger logger = Logger.getLogger(ConditionController.class.getName());

    @Autowired
    ConditionService conditionService;

    @GetMapping("/condition")
    @ResponseBody
    public String fetchCondition(){
        return conditionService.fetchCondition();
    }
}
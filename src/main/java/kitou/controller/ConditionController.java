package kitou.controller;

import kitou.business.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/condition")
public class ConditionController {

    static final Logger logger = Logger.getLogger(ConditionController.class.getName());

    @Autowired
    ConditionService conditionService;

    public void fetchCondition(){
        conditionService.fetchCondition();
    }
}
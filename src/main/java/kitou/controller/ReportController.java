package kitou.controller;

import kitou.business.ReportService;
import kitou.data.dtos.ConditionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class ReportController {

    static final Logger logger = Logger.getLogger(ReportController.class.getName());

    @Autowired
    ReportService reportService;

    @GetMapping("/report")
    public String generateReport(@RequestHeader(name = "Authorization") String accessToken
            , @RequestHeader(name = "UserEmail") String email){
        return reportService.generateReport(accessToken.substring(7), email);
    }

    @PostMapping("/report")
    public String generateReport(@RequestHeader(name = "Authorization") String accessToken
            , @RequestHeader(name = "UserEmail") String email
            , @RequestBody ConditionDTO conditionDTO){
        return reportService.generateReport(accessToken.substring(7), email, conditionDTO);
    }
}
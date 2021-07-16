package kitou.controller;

import kitou.business.ReportService;
import kitou.data.dtos.ConditionDTO;
import kitou.data.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/report")
public class ReportController {

    static final Logger logger = Logger.getLogger(ReportController.class.getName());

    @Autowired
    ReportService reportService;

    @PostMapping("/report")
    public String generateReport(@RequestHeader(name = "Authorization") String accessToken
            , @RequestBody UserDTO userDTO){
        return reportService.generateReport(accessToken.substring(7), userDTO);
    }

    @PostMapping("/reportC")
    public String generateReport(@RequestHeader(name = "Authorization") String accessToken
            , @RequestBody UserDTO userDTO
            , @RequestBody ConditionDTO conditionDTO){
        return reportService.generateReport(accessToken.substring(7), userDTO, conditionDTO);
    }
}
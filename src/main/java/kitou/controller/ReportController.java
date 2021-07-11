package kitou.controller;

import kitou.business.ReportService;
import kitou.data.dtos.ConditionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/report")
public class ReportController {

    static final Logger logger = Logger.getLogger(ReportController.class.getName());

    @Autowired
    ReportService reportService;

    @GetMapping("/report")
    public String generateReport(){
        return reportService.generateReport();
    }

    @PostMapping("/report")
    public String generateReport(@RequestBody ConditionDTO conditionDTO){
        return reportService.generateReport(conditionDTO);
    }
}
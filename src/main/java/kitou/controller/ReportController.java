package kitou.controller;

import kitou.business.ReportService;
import kitou.data.dtos.ConditionDTO;
import kitou.util.CRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
@CrossOrigin("*")
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

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getpdf(){
        return new RestTemplate().getForEntity(CRest.PREDICTION_URI+"/pdf", byte[].class);
    }
}
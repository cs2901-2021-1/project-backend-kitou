package kitou.controller;

import kitou.business.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/report")
public class ReportController {

    static final Logger logger = Logger.getLogger(ReportController.class.getName());

    @Autowired
    ReportService reportService;

    public void generateReport(){
        reportService.generateReport();
    }

    public void generateReport(String malla, String carrera, String ciclo){
        reportService.generateReport(malla, carrera, ciclo);
    }
}
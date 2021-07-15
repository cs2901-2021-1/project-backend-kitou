package kitou.business;

import kitou.util.ConstantUtil;
import kitou.data.dtos.ConditionDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class ReportService {

    static final Logger logger = Logger.getLogger(ReportService.class.getName());

    public String generateReport(){
        return new RestTemplate().getForObject(ConstantUtil.PREDICTION_URI,String.class);
    }

    public String generateReport(ConditionDTO conditionDTO){
        return new RestTemplate().postForObject(ConstantUtil.PREDICTION_URI,conditionDTO,String.class);
    }
}
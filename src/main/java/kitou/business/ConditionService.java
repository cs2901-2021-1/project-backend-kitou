package kitou.business;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import kitou.config.UriConfig;

import java.util.logging.Logger;

@Service
public class ConditionService{

    static final Logger logger = Logger.getLogger(ConditionService.class.getName());

    public String fetchCondition(){
        return new RestTemplate().getForObject(UriConfig.PREDICTION_URI,String.class);
    }
}
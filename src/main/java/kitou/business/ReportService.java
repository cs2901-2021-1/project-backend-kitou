package kitou.business;

import kitou.data.dtos.UserDTO;
import kitou.util.CRest;
import kitou.data.dtos.ConditionDTO;
import kitou.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class ReportService {

    static final Logger logger = Logger.getLogger(ReportService.class.getName());

    @Autowired
    public ValidationService validationService;

    public String generateReport(String accessToken, UserDTO userDTO){
        try{
            validationService.validateTokenAndRoleUserless(accessToken, userDTO.getEmail(), Role.STANDARD);
            return new RestTemplate().getForObject(CRest.PREDICTION_URI,String.class);
        }catch (Exception e){
            return CRest.responseMessage(false,e.getMessage());
        }
    }

    public String generateReport(String accessToken, UserDTO userDTO, ConditionDTO conditionDTO){
        try{
            validationService.validateTokenAndRoleUserless(accessToken, userDTO.getEmail(), Role.STANDARD);
            return new RestTemplate().postForObject(CRest.PREDICTION_URI,conditionDTO,String.class);
        }catch (Exception e){
            return CRest.responseMessage(false,e.getMessage());
        }
    }
}
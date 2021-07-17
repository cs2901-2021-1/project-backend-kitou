package kitou.business;

import kitou.util.CRest;
import kitou.data.dtos.ConditionDTO;
import kitou.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class ReportService {

    static final Logger logger = Logger.getLogger(ReportService.class.getName());

    @Autowired
    public ValidationService validationService;

    public String generateReport(String accessToken, String email){
        try{
            validationService.validateTokenAndRoleUserless(accessToken, email, Role.STANDARD);
            return new RestTemplate().getForObject(CRest.PREDICTION_URI+"/report",String.class);
        }catch (BadCredentialsException b){
            return CRest.responseMessage(false,b.getMessage());
        }catch (Exception u){
            return CRest.responseMessage(false,"Error al momento de tratar de conseguir la data.");
        }
    }

    public String generateReport(String accessToken, String email, ConditionDTO conditionDTO){
        try{
            validationService.validateTokenAndRoleUserless(accessToken, email, Role.STANDARD);
            return new RestTemplate().postForObject(CRest.PREDICTION_URI+"/report",conditionDTO,String.class);
        }catch (BadCredentialsException b){
            return CRest.responseMessage(false,b.getMessage());
        }catch (Exception u){
            return CRest.responseMessage(false,"Error al momento de tratar de conseguir la data.");
        }
    }
}
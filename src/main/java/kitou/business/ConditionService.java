package kitou.business;

import kitou.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import kitou.util.CRest;

import java.util.logging.Logger;

@Service
public class ConditionService{

    static final Logger logger = Logger.getLogger(ConditionService.class.getName());

    @Autowired
    public ValidationService validationService;

    public String fetchCondition(String accessToken, String email){
        try{
            validationService.validateTokenAndRoleUserless(accessToken, email, Role.STANDARD);
            return new RestTemplate().getForObject(CRest.PREDICTION_URI+"/route",String.class);
        }catch (BadCredentialsException b){
            return CRest.responseMessage(false,b.getMessage());
        }catch (Exception u){
            return CRest.responseMessage(false,"Error al momento de tratar de conseguir la data.");
        }
    }
}
package kitou.business;

import kitou.data.dtos.UserDTO;
import kitou.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import kitou.util.ConstantUtil;

import java.util.logging.Logger;

@Service
public class ConditionService{

    static final Logger logger = Logger.getLogger(ConditionService.class.getName());

    @Autowired
    public ValidationService validationService;

    public String fetchCondition(String accessToken, UserDTO userDTO){
        try{
            validationService.validateTokenAndRoleUserless(accessToken, userDTO.getEmail(), Role.STANDARD);
            return new RestTemplate().getForObject(ConstantUtil.PREDICTION_URI,String.class);
        }catch (Exception e){
            return ConstantUtil.responseMessage(false,e.getMessage());
        }
    }
}
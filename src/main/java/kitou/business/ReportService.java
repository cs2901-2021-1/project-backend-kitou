package kitou.business;

import kitou.util.CRest;
import kitou.data.dtos.ConditionDTO;
import kitou.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class ReportService {

    static final Logger logger = Logger.getLogger(ReportService.class.getName());

    @Autowired
    public ValidationService validationService;

    public ResponseEntity<byte[]> generateReport(String accessToken, String email){
        try{
            validationService.validateTokenAndRoleUserless(accessToken, email, Role.STANDARD);
            return new RestTemplate().getForEntity(CRest.PREDICTION_URI+"/pdf", byte[].class);
        }catch (BadCredentialsException b){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new byte[0]);
        }catch (Exception u){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new byte[0]);
        }
    }

    public ResponseEntity<byte[]> generateReport(String accessToken, String email, ConditionDTO conditionDTO){
        try{
            validationService.validateTokenAndRoleUserless(accessToken, email, Role.STANDARD);
            return new RestTemplate().getForEntity(CRest.PREDICTION_URI+"/pdf", byte[].class);
        }catch (BadCredentialsException b){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new byte[0]);
        }catch (Exception u){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new byte[0]);
        }
    }
}
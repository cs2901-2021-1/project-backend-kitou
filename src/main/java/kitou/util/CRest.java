package kitou.util;

public class CRest {
    private CRest() { }
    public static final String PREDICTION_URI = "http://localhost:8000";
    /** Reemplazar por: https://cs.mrg.com.pe/app-sec01-group05/*/
    public static final String FRONT_URI = "http://localhost:3000";
    public static final String ADMIN_EMAIL = "testkitou@gmail.com";

    public static String responseMessage(Boolean success, String message){
        return "{" +
                "\"success\": "+success+
                ", \"message\": \""+message+'\"'+
                "}";
    }

    public static String responseMessage(Boolean success, String message, String custom){
        return "{" +
                "\"success\": "+success+
                ", \"message\": \""+message+'\"'+
                ", "+custom+
                "}";
    }
}

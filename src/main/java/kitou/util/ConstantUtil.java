package kitou.util;

public class ConstantUtil {
    private ConstantUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static final String PREDICTION_URI = "https://v2.jokeapi.dev/joke/Any";
    public static final String FRONT_URI = "http://localhost:3000"; //"https://cs.mrg.com.pe/app-sec01-group05/";
    public static final String ADMIN_EMAIL = "andres.lostaunau@utec.edu.pe";
    public static final String SUCCESS_TRUE = "{\"success\": true}";
    public static final String SUCCESS_FALSE = "{\"success\": false}";

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

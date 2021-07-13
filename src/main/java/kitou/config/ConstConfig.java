package kitou.config;

public class ConstConfig {
    private ConstConfig()
    {
        throw new IllegalStateException("Utility class");
    }
    public static final String APP_URI = "http://localhost:";
    public static final String PREDICTION_URI = "https://v2.jokeapi.dev/joke/Any";
    public static final String FRONT_URI = "http://locahost:3000";
    public static final String ADMIN_EMAIL = "andres.lostaunau@utec.edu.pe";
    public static final String SUCCESS_TRUE = "{\"success\": true}";
    public static final String SUCCESS_FALSE = "{\"success\": false}";
}

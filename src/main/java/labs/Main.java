package labs;

import com.fastcgi.FCGIInterface;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Properties;

public class Main {
    private static final String HTTP_RESPONSE = """
            Content-Type: application/json
                    
            {"code":"%d","result":"%s","x":"%d","y":"%.3f","r":"%.1f","time":"%s","scriptTime":"%.3f"}
            """;
    private static final String HTTP_ERROR = """
            Content-Type: application/json
                    
            {"code":"%d","result":"%s","time":"%s","scriptTime":"%.3f"}
            """;

    public static void main(String[] args) {

        FCGIInterface fcgi = new FCGIInterface();

        while (fcgi.FCGIaccept() >= 0) {
            if (FCGIInterface.request.params.getProperty("REQUEST_METHOD").equals("GET")) {
                long time = System.nanoTime();
                Properties prop = System.getProperties();
                String QUERY_STRING = prop.getProperty("QUERY_STRING");
                if (!QUERY_STRING.isBlank()) {

                    Validator validator = new Validator();
                    try {
                        LinkedHashMap<String, Float> params = parse(QUERY_STRING);
                        boolean isValidated = validator.validateParams(params);
                        boolean isHit = validator.isHit(params);

                        if (isValidated) {
                            System.out.printf((HTTP_RESPONSE) + "%n", 200, isHit, params.get("x").intValue(), params.get("y"), params.get("r"),
                                    String.valueOf(LocalTime.now()).split("\\.")[0],
                                    (double) (System.nanoTime() - time) / 1000000); //
                        } else {
                            System.out.printf((HTTP_ERROR) + "%n", 400, "Invalid values", String.valueOf(LocalTime.now()).split("\\.")[0],
                                    (double) (System.nanoTime() - time) / 1000000);
                        }
                    } catch (NumberFormatException e) {
                        System.out.printf((HTTP_ERROR) + "%n", 400, "Invalid values", String.valueOf(LocalTime.now()).split("\\.")[0],
                                (double) (System.nanoTime() - time) / 1000000);
                    } catch (Exception e) {
                        System.out.printf((HTTP_ERROR) + "%n", 400, e.getMessage(), String.valueOf(LocalTime.now()).split("\\.")[0],
                                (double) (System.nanoTime() - time) / 1000000);
                    }
                } else {
                    System.out.printf((HTTP_ERROR) + "%n", 400, "Fill values", String.valueOf(LocalTime.now()).split("\\.")[0],
                            (double) (System.nanoTime() - time) / 10000000);
                }
            }
        }
    }

    public static LinkedHashMap<String, Float> parse(String queryString) throws IndexOutOfBoundsException {
        LinkedHashMap<String, Float> parameters = new LinkedHashMap<>();
        String[] reses = queryString.split("&");
        float x = Float.parseFloat(reses[0].substring(2));
        float y = Float.parseFloat(reses[1].substring(2));
        float r = Float.parseFloat(reses[2].substring(2));
        parameters.put("x", x);
        parameters.put("y", y);
        parameters.put("r", r);
        return parameters;
    }
}
package labs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Validator {

    public boolean isHit(LinkedHashMap<String, Float> params) {
        float x = params.get("x");
        float y = params.get("y");
        float r = params.get("r");

        return ((x >= -r / 2) && (x <= 0) && (y >= 0) && (y <= r) ||
                (x <= 0) && (y >= -2 * x - r) && (y <= 0) ||
                (x * x + y * y <= r * r) && (x >= 0) && (y <= 0)
        );
    }

    public boolean validateParams(LinkedHashMap<String, Float> params) {
        return validateX(params.get("x")) && validateY(params.get("y")) && validateR(params.get("r"));
    }

    private boolean validateX(float x) {
        List<Float> xValues = new ArrayList<>(9);
        xValues.add(-3f);
        xValues.add(-2f);
        xValues.add(-1f);
        xValues.add(0f);
        xValues.add(1f);
        xValues.add(2f);
        xValues.add(3f);
        return xValues.contains(x);
    }

    private boolean validateY(float y) {
        return (y < 3 && y > -5);
    }

    private boolean validateR(float r) {
        List<Float> rValues = new ArrayList<>(5);
        rValues.add(1f);
        rValues.add(2f);
        rValues.add(3f);
        rValues.add(4f);
        rValues.add(5f);
        return rValues.contains(r);
    }


}
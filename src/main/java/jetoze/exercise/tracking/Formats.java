package jetoze.exercise.tracking;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class Formats {

    private static final NumberFormat INT_FORMAT = new DecimalFormat("#,###");
    
    private static final NumberFormat DOUBLE_FORMAT = new DecimalFormat("0.0");
    
    public static Integer stringToInt(String s) {
        try {
            return INT_FORMAT.parse(s).intValue();
        } catch (ParseException e) {
            throw new IllegalArgumentException(s);
        }
    }
    
    public static String format(Number value) {
        if (value instanceof Double) {
            return DOUBLE_FORMAT.format(value.doubleValue());
        } else {
            return INT_FORMAT.format(value.intValue());
        }
    }
    
    private Formats() {/**/}

}

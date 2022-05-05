package jetoze.exercise.tracking;

import java.util.function.Predicate;

public interface Condition extends Predicate<Number> {

    String getLabel();
    
    
    static Condition ge(Number value) {
        return new Condition() {

            @Override
            public boolean test(Number t) {
                return t.doubleValue() >= value.doubleValue();
            }

            @Override
            public String getLabel() {
                return ">= " + Formats.format(value);
            }
            
        };
    }
    
}

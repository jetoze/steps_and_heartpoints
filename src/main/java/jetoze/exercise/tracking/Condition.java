package jetoze.exercise.tracking;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

public interface Condition extends Predicate<Number> {

    String getLabel();
    
    
    static Condition ge(Number value) {
        requireNonNull(value);
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
    
    static Condition lt(Number value) {
        requireNonNull(value);
        return new Condition() {

            @Override
            public boolean test(Number t) {
                return t.doubleValue() < value.doubleValue();
            }

            @Override
            public String getLabel() {
                return "< " + Formats.format(value);
            }
            
        };
    }
    
}

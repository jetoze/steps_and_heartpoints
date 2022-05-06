package jetoze.exercise.tracking;

import static java.util.Objects.requireNonNull;

import java.util.List;

public class NumberOfDaysReport extends Report<Integer> {

    private final String label;
    
    public NumberOfDaysReport(String label, Stat stat, int numberOfDays) {
        super(stat, List.of(numberOfDays));
        this.label = requireNonNull(label);
    }

    @Override
    protected String getHeader() {
        return "Number of Days with " + label + " " + getStatLabel();
    }

}

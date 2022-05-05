package jetoze.exercise.tracking;

import static java.util.Objects.*;

import java.util.List;
import java.util.stream.Collectors;

public class LongestStreakReport extends Report<ConsecutiveDailyValues> {
    
    private final String label;
    
    public LongestStreakReport(String label, Stat stat, List<ConsecutiveDailyValues> values) {
        super(stat, values);
        this.label = requireNonNull(label);
    }

    @Override
    protected String getHeader() {
        return "Longest streak of " + label + " " + getStatLabel();
    }

    @Override
    protected String valueToString(ConsecutiveDailyValues c) {
        String caption = String.format("%s to %s (%d days)", 
                c.firstDate(),
                c.lastDate(),
                c.numberOfDays());
        String separator = "\n  ";
        String values = c.values()
                .map(Formats::format)
                .collect(Collectors.joining(separator));
        return caption + separator + values;
        
    }
}

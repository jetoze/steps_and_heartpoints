package jetoze.exercise.tracking;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.List;
import java.util.stream.Stream;

public final class TopConsecutiveDaysReport extends Report<ConsecutiveDailyValues> {

    private final int numberOfDays;
    
    public TopConsecutiveDaysReport(Stat stat, Stream<ConsecutiveDailyValues> values) {
        this(stat, values.collect(toImmutableList()));
    }
    
    public TopConsecutiveDaysReport(Stat stat, List<ConsecutiveDailyValues> values) {
        super(stat, values);
        this.numberOfDays = values.get(0).numberOfDays();
        checkArgument(values.stream().mapToInt(ConsecutiveDailyValues::numberOfDays)
            .allMatch(v -> v == numberOfDays), "Inconsistent date range");
    }

    @Override
    protected String getHeader() {
        return String.format("Top %d Consecutive Days of %s", numberOfDays, getStatLabel());
    }

}

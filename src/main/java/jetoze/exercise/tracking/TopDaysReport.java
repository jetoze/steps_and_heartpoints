package jetoze.exercise.tracking;

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.List;
import java.util.stream.Stream;

public final class TopDaysReport extends Report<DailyValue> {
    
    public TopDaysReport(Stat stat, Stream<DailyValue> values) {
        super(stat, values.collect(toImmutableList()));
    }
    
    public TopDaysReport(Stat stat, List<DailyValue> values) {
        super(stat, values);
    }

    @Override
    protected String getHeader() {
        return "Top " + size() + " " + getStatLabel();
    }
    
}

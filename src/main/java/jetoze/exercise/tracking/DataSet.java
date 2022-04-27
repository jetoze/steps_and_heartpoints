package jetoze.exercise.tracking;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

public class DataSet {

    private final ImmutableList<DailyStats> dailyStats;
    
    public DataSet(List<DailyStats> dailyStats) {
        this.dailyStats = ImmutableList.copyOf(dailyStats);
    }

    public ImmutableList<DailyStats> getDays() {
        return dailyStats;
    }
    
    public Stream<DailyValue> sortedBy(Stat stat, int top) {
        requireNonNull(stat);
        checkArgument(top > 0, "top must be > 0 (was %s)", top);
        return dailyStats.stream()
                .map(ds -> ds.toDailyValue(stat))
                .sorted(Comparator.reverseOrder())
                .limit(top);
    }
    
    public Stream<ConsecutiveDailyValues> topConsecutiveDays(int days, Stat stat, int top) {
        checkArgument(days > 1, "top must be > 1 (was %s)", days);
        checkArgument(days < dailyStats.size(), "top must be < %s (was %s)", dailyStats.size(), days);
        requireNonNull(stat);
        checkArgument(top > 0, "top must be > 0 (was %s)", top);
        List<ConsecutiveDailyValues> list = new ArrayList<>();
        for (int n = 0; n < dailyStats.size() - days; ++n) {
            List<DailyValue> values = dailyStats.subList(n, n + days).stream()
                    .map(ds -> ds.toDailyValue(stat))
                    .collect(toImmutableList());
            list.add(new ConsecutiveDailyValues(stat, values));
        }
        return list.stream()
                .sorted(Comparator.reverseOrder())
                .limit(top);
    }

}

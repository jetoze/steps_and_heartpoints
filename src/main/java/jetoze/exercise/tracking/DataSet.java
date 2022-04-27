package jetoze.exercise.tracking;

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
        return dailyStats.stream()
                .sorted(stat.reversed())
                .limit(top)
                .map(ds -> ds.toDailyValue(stat));
    }
    

}

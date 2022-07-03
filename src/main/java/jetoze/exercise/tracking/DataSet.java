package jetoze.exercise.tracking;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
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
    
    public ImmutableList<DailyValue> getDailyValues(Stat stat) {
        requireNonNull(stat);
        return dailyStats.stream()
                .map(ds -> ds.toDailyValue(stat))
                .collect(toImmutableList());
    }
    
    public TopDaysReport topDays(Stat stat, int top) {
        requireNonNull(stat);
        checkArgument(top > 0, "top must be > 0 (was %s)", top);
        return new TopDaysReport(stat, sortedBy(stat, top));
    }
    
    private Stream<DailyValue> sortedBy(Stat stat, int top) {
        return dailyStats.stream()
                .map(ds -> ds.toDailyValue(stat))
                .sorted(Comparator.reverseOrder())
                .limit(top);
    }
    
    public TopConsecutiveDaysReport topConsecutiveDays(int days, Stat stat, int top) {
        checkArgument(days > 1, "top must be > 1 (was %s)", days);
        checkArgument(days < dailyStats.size(), "top must be < %s (was %s)", dailyStats.size(), days);
        requireNonNull(stat);
        checkArgument(top > 0, "top must be > 0 (was %s)", top);
        return new TopConsecutiveDaysReport(stat, sortConsecutiveDays(days, stat, top));
    }
    
    public LongestStreakReport longestStreak(Stat stat, Condition condition) {
        return new LongestStreakReport(condition.getLabel(), stat, calculateLongestStreak(stat, condition));
    }
    
    public LongestStreakReport longestIncreasingTrend(Stat stat) {
        return new LongestStreakReport("increasing", stat, longestTrend(stat, 
                (p, n) -> Double.compare(p.doubleValue(), n.doubleValue()) <= 0.0));
    }
    
    public LongestStreakReport longestDecreasingTrend(Stat stat) {
        return new LongestStreakReport("decreasing", stat, longestTrend(stat, 
                (p, n) -> Double.compare(p.doubleValue(), n.doubleValue()) >= 0.0));
    }
    
    public NumberOfDaysReport numberOfDays(Stat stat, Condition condition) {
        int numberOfDays = (int) dailyStats.stream()
            .map(ds -> ds.toDailyValue(stat))
            .map(DailyValue::getValue)
            .filter(condition)
            .count();
        return new NumberOfDaysReport(condition.getLabel(), stat, numberOfDays);
    }
    
    public List<DailyValue> dailyAverageProgression(Stat stat) {
        List<DailyValue> averages = new ArrayList<>();
        double sum = 0;
        int days = 0;
        double average = 0.0;
        for (DailyValue v : getDailyValues(stat)) {
            sum += v.getValue().doubleValue();
            days++;
            average = sum / days;
            averages.add(new DailyValue(v.getDate(), average));
        }
        return averages;
    }
    
    private Stream<ConsecutiveDailyValues> sortConsecutiveDays(int days, Stat stat, int top) {
        List<ConsecutiveDailyValues> list = new ArrayList<>();
        for (int n = 0; n <= dailyStats.size() - days; ++n) {
            List<DailyValue> values = dailyStats.subList(n, n + days).stream()
                    .map(ds -> ds.toDailyValue(stat))
                    .collect(toImmutableList());
            list.add(new ConsecutiveDailyValues(stat, values));
        }
        return list.stream()
                .sorted(Comparator.reverseOrder())
                .limit(top);
    }
    
    private List<ConsecutiveDailyValues> calculateLongestStreak (Stat stat, Predicate<Number> condition) {
        return longestTrend(stat, (previousValue, newValue) -> condition.test(newValue));
    }
    
    private List<ConsecutiveDailyValues> longestTrend(Stat stat, BiFunction<Number, Number, Boolean> criteria) {
        List<ConsecutiveDailyValues> result = new ArrayList<>();
        int maxLength = 2;
        List<DailyValue> current = new ArrayList<>();
        Number previousValue = 0;
        for (DailyStats d : dailyStats) {
            if (criteria.apply(previousValue, d.get(stat))) {
                current.add(d.toDailyValue(stat));
            } else if (!current.isEmpty()) {
                if (current.size() >= maxLength) {
                    if (current.size() > maxLength) {
                        result = new ArrayList<>();
                        maxLength = current.size();
                    }
                    result.add(new ConsecutiveDailyValues(stat, current));
                }
                current = new ArrayList<>();
            }
            previousValue = d.get(stat);
        }
        if (current.size() >= maxLength) {
            if (current.size() > maxLength) {
                result = new ArrayList<>();
            }
            result.add(new ConsecutiveDailyValues(stat, current));
        }
        return result;
    }

}

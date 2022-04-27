package jetoze.exercise.tracking;

import static java.util.Objects.requireNonNull;
import static jetoze.exercise.tracking.Formats.stringToInt;

import java.time.LocalDate;
import java.util.EnumMap;

public final class DailyStats {
    
    private final LocalDate date;
    private final EnumMap<Stat, Number> stats = new EnumMap<>(Stat.class);
    
    public DailyStats(String[] parts) {
        date = LocalDate.parse(parts[0]);
        stats.put(Stat.OURA_STEPS, stringToInt(parts[1]));
        stats.put(Stat.OURA_BURN, stringToInt(parts[2]));
        stats.put(Stat.OURA_DISTANCE, Double.valueOf(parts[3]));
        stats.put(Stat.GFIT_HEARTPOINTS, stringToInt(parts[4]));
        stats.put(Stat.GFIT_STEPS, stringToInt(parts[5]));
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public Number get(Stat stat) {
        return stats.get(requireNonNull(stat));
    }

    public DailyValue toDailyValue(Stat stat) {
        return new DailyValue(date, get(stat));
    }
    
}

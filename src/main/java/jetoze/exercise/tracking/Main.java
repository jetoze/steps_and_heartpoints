package jetoze.exercise.tracking;

public class Main {

    public static void main(String[] args) {
        DataSet dataSet = Parser.parseResource("Steps & Heartpoints - 2022.tsv");
        for (Stat stat : Stat.values()) {
            top(dataSet, stat, 5);
            System.out.println();
        }
        for (Stat stat : Stat.values()) {
            topConsecutiveDays(dataSet, stat, 5, 5);
            System.out.println();
        }
        longestStreak(dataSet, Stat.OURA_STEPS, Condition.ge(10_000));
        longestStreak(dataSet, Stat.GFIT_HEARTPOINTS, Condition.ge(50));
    }
    
    static void top(DataSet dataSet, Stat stat, int top) {
        Report<DailyValue> report = dataSet.topDays(stat, top);
        report.output(System.out::println);
    }

    static void topConsecutiveDays(DataSet dataSet, Stat stat, int days, int top) {
        TopConsecutiveDaysReport report = dataSet.topConsecutiveDays(days, stat, top);
        report.output(System.out::println);
    }
    
    static void longestStreak(DataSet dataSet, Stat stat, Condition condition) {
        LongestStreakReport report = dataSet.longestStreak(stat, condition);
        report.output(System.out::println);
    }
    
}

package jetoze.exercise.tracking;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        DataSet dataSet = Parser.parseResource("Steps & Heartpoints - 2022.tsv");

//        for (Stat stat : Stat.values()) {
//            top(dataSet, stat, 10);
//            System.out.println();
//        }
//        for (Stat stat : Stat.values()) {
//            topConsecutiveDays(dataSet, stat, 2, 5);
//            System.out.println();
//        }
//        for (Stat stat : Stat.values()) {
//            longestIncreasingTrend(dataSet, stat);
//            System.out.println();
//        }
//        longestStreak(dataSet, Stat.OURA_BURN, Condition.ge(1000));
//        longestStreak(dataSet, Stat.GFIT_HEARTPOINTS, Condition.ge(100));
//        top(dataSet, Stat.OURA_DISTANCE, 10);
        numberOfDays(dataSet, Stat.GFIT_STEPS, Condition.ge(20_000));
//        longestStreak(dataSet, Stat.OURA_STEPS, Condition.ge(11_000));
//        longestIncreasingTrend(dataSet, Stat.OURA_BURN);
//        longestDecreasingTrend(dataSet, Stat.OURA_STEPS);
//        plotAverageProgression(dataSet, Stat.OURA_STEPS);
//        printAverageProgression(dataSet, Stat.GFIT_HEARTPOINTS, " ");
        
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
    
    static void numberOfDays(DataSet dataSet, Stat stat, Condition condition) {
        NumberOfDaysReport report = dataSet.numberOfDays(stat, condition);
        report.output(System.out::println);
    }
    
    static void longestIncreasingTrend(DataSet dataSet, Stat stat) {
        LongestStreakReport report = dataSet.longestIncreasingTrend(stat);
        report.output(System.out::println);
    }
    
    static void longestDecreasingTrend(DataSet dataSet, Stat stat) {
        LongestStreakReport report = dataSet.longestDecreasingTrend(stat);
        report.output(System.out::println);
    }
    
    static void printAverageProgression(DataSet dataSet, Stat stat, String separator) {
        String s = dataSet.dailyAverageProgression(stat).stream()
            .map(DailyValue::getValue)
            .mapToDouble(Number::doubleValue)
            .mapToObj(Formats::format)
            .collect(Collectors.joining(separator));
        System.out.println(s);
    }
    
    static void plotAverageProgression(DataSet dataSet, Stat stat) {
        List<Number> averages = dataSet.dailyAverageProgression(stat).stream()
                .map(DailyValue::getValue)
                .collect(Collectors.toList());
        XYGraphPlotter plotter = new XYGraphPlotter(averages, 300);
        plotter.plot();
    }
    
}

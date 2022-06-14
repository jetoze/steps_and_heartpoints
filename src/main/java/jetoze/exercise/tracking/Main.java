package jetoze.exercise.tracking;

import java.util.ArrayList;
import java.util.List;

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
//        longestStreak(dataSet, Stat.OURA_STEPS, Condition.ge(10_000));
//        longestStreak(dataSet, Stat.GFIT_HEARTPOINTS, Condition.ge(100));
//        top(dataSet, Stat.OURA_DISTANCE, 10);
//        numberOfDays(dataSet, Stat.OURA_DISTANCE, Condition.ge(13.0));
//        longestStreak(dataSet, Stat.OURA_STEPS, Condition.ge(11_000));
//        averageProgression(dataSet);
//        averageProgression(dataSet);
        plotAverageProgression(dataSet);
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
    
    static void averageProgression(DataSet dataSet) {
        List<DailyValue> dailyValues = dataSet.getDailyValues(Stat.GFIT_STEPS);
        int sum = 0;
        int days = 0;
        for (DailyValue v : dailyValues) {
            sum += v.getValue().intValue();
            days++;
            int average = sum / days;
            //System.out.println(Formats.format(average));
            System.out.print(average + " ");
        }
    }
    
    static void plotAverageProgression(DataSet dataSet) {
        List<DailyValue> dailyValues = dataSet.getDailyValues(Stat.OURA_STEPS);
        List<Number> averages = new ArrayList<>();
        double sum = 0;
        int days = 0;
        for (DailyValue v : dailyValues) {
            sum += v.getValue().doubleValue();
            days++;
            double average = sum / days;
            averages.add(average);
        }
        XYGraphPlotter plotter = new XYGraphPlotter(averages, 300);
        plotter.plot();
    }
    
}

package jetoze.exercise.tracking;

public class Main {

    public static void main(String[] args) {
        DataSet dataSet = Parser.parseResource("Steps & Heartpoints - 2022.tsv");
//        for (Stat stat : Stat.values()) {
//            top(dataSet, stat, 10);
//            System.out.println();
//        }
        for (Stat stat : Stat.values()) {
            topConsecutiveDays(dataSet, stat, 5, 3);
            System.out.println();
        }
    }
    
    static void top(DataSet dataSet, Stat stat, int top) {
        String header = "Top " + top + " " + stat.getLabel();
        System.out.println(header);
        System.out.println("-".repeat(header.length()));
        dataSet.sortedBy(stat, top)
            .forEach(System.out::println);
    }

    static void topConsecutiveDays(DataSet dataSet, Stat stat, int days, int top) {
        String header = String.format("Top %d Consecutive Days of %s", days, stat.getLabel());
        System.out.println(header);
        System.out.println("-".repeat(header.length()));
        dataSet.topConsecutiveDays(days, stat, top)
            .forEach(System.out::println);
    }
    
}

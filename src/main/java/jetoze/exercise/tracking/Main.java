package jetoze.exercise.tracking;

public class Main {

    public static void main(String[] args) {
        DataSet dataSet = Parser.parseResource("Steps & Heartpoints - 2022.tsv");
        for (Stat stat : Stat.values()) {
            top(dataSet, stat, 10);
            System.out.println();
        }
        
    }
    
    private static void top(DataSet dataSet, Stat stat, int top) {
        String header = "Top " + top + " " + stat.getLabel();
        System.out.println(header);
        System.out.println("-".repeat(header.length()));
        dataSet.sortedBy(stat, top)
            .forEach(System.out::println);
    }

}

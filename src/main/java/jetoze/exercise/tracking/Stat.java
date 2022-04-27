package jetoze.exercise.tracking;

import java.util.Comparator;

public enum Stat implements Comparator<DailyStats> {

    OURA_STEPS("Oura Steps"),
    OURA_BURN("Oura Burn"),
    OURA_DISTANCE("Oura Distance"),
    GFIT_HEARTPOINTS("GFit HeartPoints"),
    GFIT_STEPS("GFit Steps");

    private final String label;
    
    private Stat(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public int compare(DailyStats o1, DailyStats o2) {
        return Double.compare(o1.get(this).doubleValue(), o2.get(this).doubleValue());
    }
    
    
}

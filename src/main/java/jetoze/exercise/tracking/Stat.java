package jetoze.exercise.tracking;

public enum Stat {

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
    
}

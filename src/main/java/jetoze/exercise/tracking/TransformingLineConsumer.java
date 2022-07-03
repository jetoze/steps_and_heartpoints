package jetoze.exercise.tracking;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class TransformingLineConsumer implements Consumer<String> {

    private final Consumer<String> delegatee;
    private final Function<String, String> transform;
    
    public TransformingLineConsumer(Consumer<String> delegatee, Function<String, String> transform) {
        this.delegatee = requireNonNull(delegatee);
        this.transform = requireNonNull(transform);
    }

    @Override
    public void accept(String t) {
        delegatee.accept(transform.apply(t));
    }

}

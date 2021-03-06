package jetoze.exercise.tracking;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;

public abstract class Report<T> {

    private final Stat stat;
    private final ImmutableList<T> values;
    
    protected Report(Stat stat, List<T> values) {
        this.stat = requireNonNull(stat);
        this.values = ImmutableList.copyOf(values);
    }

    public final void output(Consumer<String> lineConsumer) {
        String header = getHeader();
        lineConsumer.accept(header);
        lineConsumer.accept("-".repeat(header.length()));
        AtomicInteger place = new AtomicInteger();
        Consumer<String> wrappingLineConsumer = new TransformingLineConsumer(lineConsumer, s -> place.incrementAndGet() + ") " + s);
        values.stream()
            .map(this::valueToString)
            .forEach(wrappingLineConsumer);

    }
    
    protected final String getStatLabel() {
        return stat.getLabel();
    }
    
    protected final int size() {
        return values.size();
    }
    
    protected abstract String getHeader();
    
    protected String valueToString(T value) {
        return value.toString();
    }
        
}

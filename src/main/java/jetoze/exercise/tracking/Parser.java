package jetoze.exercise.tracking;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

public class Parser {

    private final InputStream is;
    
    private Parser(InputStream is) {
        this.is = requireNonNull(is);
    }

    public DataSet parse() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                Stream<String> lines = reader.lines()) {
            ImmutableList<DailyStats> stats = lines.filter(this::startsWithDate)
                .map(line -> line.split("\t"))
                .map(DailyStats::new)
                .collect(ImmutableList.toImmutableList());
            return new DataSet(stats);
        }
    }
    
    private boolean startsWithDate(String line) {
        return line.startsWith("202");
    }
    
    public static DataSet parseResource(String resource) {
        try (InputStream is = Parser.class.getClassLoader().getResourceAsStream(resource)) {
            Parser parser = new Parser(is);
            return parser.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
}

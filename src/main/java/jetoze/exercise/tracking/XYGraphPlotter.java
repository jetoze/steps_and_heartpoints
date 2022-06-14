package jetoze.exercise.tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class XYGraphPlotter {

    private final int scale;
    private final ImmutableList<Point> sortedPoints;
    
    public XYGraphPlotter(List<Number> values, int scale) {
        this.scale = scale;
        double max = values.stream()
                .mapToDouble(Number::doubleValue)
                .max()
                .getAsDouble();
        List<Point> points = new ArrayList<>();
        int x = 1;
        for (Number v : values) {
            points.add(new Point(x, (int) (scale * (v.doubleValue() / max))));
            ++x;
        }
        Collections.sort(points, Comparator.comparingDouble(Point::getY)
                .reversed()
                .thenComparingInt(Point::getX));
        this.sortedPoints = ImmutableList.copyOf(points);
    }
    
    public void plot() {
        Iterator<Point> it = sortedPoints.iterator();
        Point p = it.next();
        for (int n = scale; n >= 0; --n) {
            while (n > p.y) {
                System.out.println();
                --n;
            }
            Line line = new Line();
            line.plot(p);
            while (it.hasNext()) {
                p = it.next();
                if (p.y != n) {
                    break;
                }
                line.plot(p);
            }
            System.out.println(line);
            if (!it.hasNext()) {
                break;
            }
        }
        System.out.println(" ".repeat(sortedPoints.size() + 5));
    }
    
    
    private static final class Line {
        
        private final StringBuilder sb = new StringBuilder();
        private int lastX;
        
        public void plot(Point p) {
            sb.append(" ".repeat(p.x - lastX));
            if (sb.length() != p.x) {
                throw new RuntimeException(String.format("%s plotted at %d", p, sb.length()));
            }
            sb.append("o");
            lastX = p.x + 1;
        }
        
        @Override
        public String toString() {
            return sb.toString();
        }
    }
    
    
    private static final class Point {
        public final int x;
        public final int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        int getX() {
            return x;
        }
        
        int getY() {
            return y;
        }
        
        @Override
        public String toString() {
            return String.format("[%d, %d]", x, y);
        }
    }
    
}

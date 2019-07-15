import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {

    private final Point[] pts;
    private ArrayList<LineSegment> segs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        // corner cases
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            int j = i + 1;
            while (j < points.length) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                j++;
            }
        }

        // parameter initialization
        pts = points;
        segs = new ArrayList<>();
        
        // find line segments
        Arrays.sort(pts);
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                for (int m =  j + 1; m < pts.length; m++) {
                    for (int n = m + 1; n < pts.length; n++) {
                        if (isCollinear(pts[i], pts[j], pts[m], pts[n])) {
                            segs.add(new LineSegment(pts[i], pts[n]));
                        }
                    }
                }
            }
        }
        
    }

    // check if four points are collinear
    private boolean isCollinear(Point a, Point b, Point c, Point d) {
        double slope1 = a.slopeTo(b);
        double slope2 = a.slopeTo(c);
        double slope3 = a.slopeTo(d);
        return (slope1 == slope2) && (slope1 == slope3);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segs.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] temp = new LineSegment[numberOfSegments()];
        return segs.toArray(temp);
    }

    public static void main(String[] args) {
         // read the n points from a file
         In in = new In(args[0]);
         int n = in.readInt();
         Point[] points = new Point[n];
         for (int i = 0; i < n; i++) {
             int x = in.readInt();
             int y = in.readInt();
             points[i] = new Point(x, y);
         }
 
         // draw the points
         StdDraw.enableDoubleBuffering();
         StdDraw.setXscale(0, 32768);
         StdDraw.setYscale(0, 32768);
         for (Point p : points) {
             p.draw();
         }
         StdDraw.show();
 
         // print and draw the line segments
         BruteCollinearPoints collinear = new BruteCollinearPoints(points);
         for (LineSegment segment : collinear.segments()) {
             StdOut.println(segment);
             segment.draw();
         }
         StdDraw.show();
    }

}
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {

    private final Point[] pts;
    private ArrayList<LineSegment> segs;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        // corner cases
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i ++) {
            if (points[i] == null) throw new IllegalArgumentException();
            int j = i + 1;
            while (j < points.length) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                j ++;
            }
        }

        // parameter initialization
        pts = points;
        segs = new ArrayList<>();

        // find segments
        Point[] aux = Arrays.copyOf(pts, pts.length);
        for (int i = 0; i < pts.length; i ++) {
            Point curr = pts[i];
            Arrays.sort(aux, curr.slopeOrder());

            int start = 0;                            // start of the line
            boolean active = false;                   // check if the point before is in line
            for (int j = 0; j < aux.length; j ++) {
                if (j < aux.length - 1 && curr.slopeTo(aux[j]) == curr.slopeTo(aux[j + 1])) {   // add j < aux.length - 1 to handle the last point 
                    if (!active) {
                        start = j;
                        active = true;
                    }
                }
                else {
                    if (active) {
                        if (j - start + 1 >= 3) {
                            ArrayList<Point> line_pts = new ArrayList<>();
                            for (int m = start; m < j + 1; m ++) {
                                line_pts.add(aux[m]);
                            }
                            line_pts.add(aux[0]);                           // add the first item which is the point itself to the point sets that belong to the line
                            Collections.sort(line_pts);
                            if (aux[0].compareTo(line_pts.get(0)) == 0) {
                                segs.add(new LineSegment(line_pts.get(0), line_pts.get(j - start + 1)));
                            }
                        }
                        active = false;
                    }
                }
            }
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }
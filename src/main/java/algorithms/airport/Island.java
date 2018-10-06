package algorithms.airport;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;

import static algorithms.airport.Utility.doLinesCross;
import static algorithms.airport.Utility.getIntersection;

public class Island extends Polygon {

    /**
     * Constructs an island with the given vertices
     *
     * @param xpoints The x co-ordinates
     * @param ypoints The y co-ordinates
     * @param npoints The number of points
     */
    public Island(final int[] xpoints, final int[] ypoints, final int npoints) {
        super(xpoints, ypoints, npoints);
        if (npoints < 3) {
            throw new IllegalArgumentException("Island must have at least 3 points.");
        }
    }

    /**
     * The total number of points in the polygon
     */
    public int size() {
        return npoints;
    }

    private Line2D getLineFromIndices(final int a, final int b) {
        if (a == b || a > npoints || b > npoints || a < 0 || b < 0)
            throw new IllegalArgumentException("Invalid line segment");

        final Point2D.Double ptA = new Point2D.Double(xpoints[a], ypoints[a]);
        final Point2D.Double ptB = new Point2D.Double(xpoints[b], ypoints[b]);

        return new Line2D.Double(ptA, ptB);
    }

    /**
     * Tests where a line between two vertices is fully contained by the island
     *
     * @param a The index of the first vertex
     * @param b The index of the second vertex
     * @return True if the line is inside the polygon
     */
    public boolean containsLine(final int a, final int b) {
        if (a == b || a > npoints || b > npoints || a < 0 || b < 0) {
            throw new IllegalArgumentException("Invalid line segment");
        }

        // check if the vertices are in direct sequence
        if (Math.abs(a - b) == 1 || Math.abs(a - b) == npoints - 1) {
            return true;
        }

        final Point2D pA = new Point2D.Double(xpoints[a], ypoints[a]);
        final Point2D pB = new Point2D.Double(xpoints[b], ypoints[b]);

        // If the line segment crosses ANY edge it is invalid.
        for (int i = 0; i < npoints - 1; i++) {
            if (a == i || a == a + 1 || b == i || b == i + 1)
                continue; // dont check if the line segment crosses itself

            if (doLinesCross(getLineFromIndices(a, b), getLineFromIndices(i, i + 1)))
                return false;
        }

        if (doLinesCross(getLineFromIndices(a, b), getLineFromIndices(npoints - 1, 0)))
            return false;

        final LinkedList<Point2D> intersections = getIntersections(a, b);

        final Comparator<Point2D> comp = (o1, o2) -> Double.compare(pA.distance(o2), pA.distance(o1));
        // sort the intersections by distance from a and check whether
        // each midpoint between the intersections is inside the polygon
        // if any midpoint is not inside the polygon, then the line is invalid
        intersections.sort(comp);
        intersections.addFirst(pA);
        intersections.addLast(pB);

        final Iterator<Point2D> it = intersections.iterator();
        Point2D left = it.next();

        for (int i = 0; i < intersections.size() - 1; i++) {
            Point2D right = it.next();
            if (left.equals(right))
                continue;

            final double x = (left.getX() + right.getX()) / 2;
            final double y = (left.getY() + right.getY()) / 2;

            if (!contains(x, y)) {
                return false;
            }
            left = right;
        }

        return true;
    }

    /**
     * Finds all intersections between the given line segment and the edges of the polygon
     *
     * @param a The index of the first vertex in the line segment
     * @param b The index of the second vertex in the line segment
     * @return A list of points where the lines intersect
     */
    private LinkedList<Point2D> getIntersections(final int a, final int b) {
        return getIntersections(new Point2D.Double(xpoints[a], ypoints[a]), new Point2D.Double(xpoints[b], ypoints[b]));
    }

    /**
     * Finds all intersections between the given line segment and the edges of the polygon
     *
     * @param a The first point in the line segment
     * @param b The second point in the line segment
     * @return A list of points where the lines intersect
     */
    public LinkedList<Point2D> getIntersections(final Point2D a, final Point2D b) {
        final LinkedList<Point2D> list = new LinkedList<>();

        for (int i = 0; i < size() - 1; i++) {
            if (xpoints[i] == a.getX() && ypoints[i] == a.getY() || xpoints[i + 1] == a.getX() && ypoints[i + 1] == a.getY()
                    || xpoints[i] == b.getX() && ypoints[i] == b.getY() || xpoints[i + 1] == b.getX() && ypoints[i + 1] == b.getY())
                continue;

            final Line2D edge = getLineFromIndices(i, i + 1);
            final Point2D intersectionPt = getIntersection(new Line2D.Double(a, b), edge);

            if (intersectionPt != null)
                list.add(intersectionPt);
        }

        if (xpoints[0] == a.getX() && ypoints[0] == a.getY() || xpoints[npoints - 1] == a.getX() && ypoints[npoints - 1] == a.getY()
                || xpoints[0] == b.getX() && ypoints[0] == b.getY() || xpoints[npoints - 1] == b.getX() && ypoints[npoints - 1] == b.getY())
            return list;

        final Line2D.Double edge = new Line2D.Double(xpoints[npoints - 1], ypoints[npoints - 1], xpoints[0], ypoints[0]);
        final Point2D intersectionPt = getIntersection(new Line2D.Double(a, b), edge);

        if (intersectionPt != null)
            list.add(intersectionPt);

        return list;
    }
}
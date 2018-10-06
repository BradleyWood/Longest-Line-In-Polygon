package algorithms.airport;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Utility {

    /**
     * Calculates the angle from the src point to the destination point in degrees
     *
     * @param src  src point
     * @param dest dest point
     * @return The angle in degrees
     */
    public static double angleBetween(final Point2D src, final Point2D dest) {
        return angleBetween(src.getX(), src.getY(), dest.getX(), dest.getY());
    }

    /**
     * Calculates the angle from the src point to the destination point in degrees
     *
     * @param srcX  src point x value
     * @param srcY  src point y value
     * @param destX dest point x value
     * @param destY dest point y value
     * @return The angle in degrees
     */
    public static double angleBetween(final double srcX, final double srcY, final double destX, final double destY) {
        double dx = destX - srcX;
        double dy = -(destY - srcY);
        double inRads = Math.atan2(dy, dx);
        if (inRads < 0)
            inRads = Math.abs(inRads);
        else
            inRads = 2 * Math.PI - inRads;
        return Math.toDegrees(inRads);
    }

    public static boolean isPointOnLine(final Line2D line, final Point2D pt) {
        final double lineLength = line.getP1().distance(line.getP2());
        final double sumOfPtDistance = line.getP1().distance(pt) + line.getP2().distance(pt);

        final double epsilon = 0.00000000001;

        return sumOfPtDistance + epsilon > lineLength && sumOfPtDistance - epsilon < lineLength;
    }

    /**
     * Calculates the intersection point of two line segments
     *
     * @param a The first line
     * @param b The second line
     * @return The intersection point or NULL if the lines do not intersect
     */
    public static Point2D getIntersection(final Line2D a, final Line2D b) {
        double x = ((a.getX2() - a.getX1()) * (b.getX1() * b.getY2() - b.getX2() * b.getY1()) -
                (b.getX2() - b.getX1()) * (a.getX1() * a.getY2() - a.getX2() * a.getY1()))
                / ((a.getX1() - a.getX2()) * (b.getY1() - b.getY2()) - (a.getY1() - a.getY2()) * (b.getX1() - b.getX2()));

        double y = ((b.getY1() - b.getY2()) * (a.getX1() * a.getY2() - a.getX2() * a.getY1()) -
                (a.getY1() - a.getY2()) * (b.getX1() * b.getY2() - b.getX2() * b.getY1()))
                / ((a.getX1() - a.getX2()) * (b.getY1() - b.getY2()) - (a.getY1() - a.getY2()) * (b.getX1() - b.getX2()));

        double minXa = Math.min(a.getX1(), a.getX2());
        double minXb = Math.min(b.getX1(), b.getX2());
        double maxXa = Math.max(a.getX1(), a.getX2());
        double maxXb = Math.max(b.getX1(), b.getX2());
        double minYa = Math.min(a.getY1(), a.getY2());
        double minYb = Math.min(b.getY1(), b.getY2());
        double maxYa = Math.max(a.getY1(), a.getY2());
        double maxYb = Math.max(b.getY1(), b.getY2());

        // check that the intersection is within the domain and range of each line segment
        if (x >= minXa && x >= minXb && x <= maxXa && x <= maxXb && y >= minYa && y >= minYb && y <= maxYa && y <= maxYb) {
            return new Point2D.Double(x, y);
        }

        return null;
    }

    /**
     * Checks if two line segments cross each other
     *
     * @param lineA The first line segment
     * @param lineB The second line segment
     * @return True if one line crosses the other
     */
    public static boolean doLinesCross(final Line2D lineA, final Line2D lineB) {
        if (!lineA.intersectsLine(lineB))
            return false;

        return !isPointOnLine(lineA, lineB.getP1()) && !isPointOnLine(lineA, lineB.getP2()) &&
                !isPointOnLine(lineB, lineA.getP1()) && !isPointOnLine(lineB, lineA.getP2());
    }
}

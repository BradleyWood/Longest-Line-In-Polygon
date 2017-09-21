package algorithms.airport;

import java.awt.geom.Point2D;

public class Utility {


    /**
     * Calculates the angle from the src point to the destination point in degrees
     *
     * @param src src point
     * @param dest dest point
     * @return The angle in degrees
     */
    public static double angleBetween(final Point2D src, final Point2D dest) {
        return angleBetween(src.getX(), src.getY(), dest.getX(), dest.getY());
    }


    /**
     * Calculates the angle from the src point to the destination point in degrees
     *
     * @param srcX src point x value
     * @param srcY src point y value
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


    /**
     * The 2d distance between two points
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double distanceBetween(final double x1, final double y1, final double x2, final double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Tests whether a point is on a line. This is used to help check whether a line
     * ends on another line
     *
     * @param px1 The x co-ordinate of the point to test
     * @param py1 The y co-ordinate of the point to test
     * @param x2  first x line co-ordinate
     * @param y2  first y line co-ordinate
     * @param x3  second x line co-ordinate
     * @param y3  second y line co-ordinate
     * @return
     */
    public static boolean onLine(final double px1, final double py1, final double x2, final double y2, final double x3, final double y3) {
        double d23 = distanceBetween(x2, y2, x3, y3);
        double d1 = distanceBetween(px1, py1, x3, y3) + distanceBetween(px1, py1, x2, y2);
        double eps = 0.00000000001;
        return d1 + eps > d23 && d1 - eps < d23;
    }
}

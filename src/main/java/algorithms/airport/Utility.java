package algorithms.airport;

import java.awt.geom.Line2D;
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

    public static boolean isPointOnLine(final Line2D line, final Point2D pt) {
        final double lineLength = line.getP1().distance(line.getP2());
        final double sumOfPtDistance = line.getP1().distance(pt) + line.getP2().distance(pt);

        final double epsilon = 0.00000000001;

        return sumOfPtDistance + epsilon > lineLength && sumOfPtDistance - epsilon < lineLength;
    }
}

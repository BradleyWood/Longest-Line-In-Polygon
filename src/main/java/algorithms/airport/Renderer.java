package algorithms.airport;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Renderer {

    private Island poly;
    private Line2D runway;
    private int width;
    private int height;

    public Renderer(Island poly, Line2D runway) {
        this.poly = poly;
        this.runway = runway;
    }

    /**
     * Renders an image for the Island and Runway. <br>
     * If the dimensions used do not maintain the same aspect ratio as
     * the polygon then the image will be scaled and may look squashed
     * or stretched.
     * <br>
     * <br>
     * The length of the runway, number of vertices and the start/end locations
     * of the runway will also be displayed at the bottom of the image.
     *
     * @param width  The width The width of the polygon in the image
     * @param height The height The height of the polygon in the image
     * @return The rendered image
     */
    public BufferedImage toImage(int width, int height) {
        this.width = width;
        this.height = height;

        BufferedImage image = new BufferedImage(width + 1, height + 20, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.clearRect(0, 0, width, height + 20);

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(rh);

        g2d.setColor(Color.GREEN);
        String line = "(" + formatDouble(runway.getX1()) + "," + formatDouble(runway.getY1()) + ") to (" +
                formatDouble(runway.getX2()) + "," + formatDouble(runway.getY2()) + ")";
        g2d.drawString("n = " + poly.npoints + " Length = " + formatDouble(runway.getP1().distance(runway.getP2())) + " " + line, 5, height + 14);
        // draw the text before the transformation occurs so we dont flip it

        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -height);
        // we must flip the image along the y axis because the screen co-ordinates
        // are opposite to the graphing standard

        g2d.transform(tx);
        g2d.setStroke(new BasicStroke(height / 125 == 0 ? 1 : height / 125));
        // the line thickness will scale with the image size


        g2d.setColor(Color.RED);
        for (int i = 0; i < poly.npoints - 1; i++) {
            g2d.drawLine(calculateX(poly.xpoints[i]), calculateY(poly.ypoints[i]), calculateX(poly.xpoints[i + 1]), calculateY(poly.ypoints[i + 1]));
        }
        // connect last vertex to the first
        g2d.drawLine(calculateX(poly.xpoints[poly.size() - 1]), calculateY(poly.ypoints[poly.size() - 1]),
                calculateX(poly.xpoints[0]), calculateY(poly.ypoints[0]));
        g2d.setColor(Color.BLACK);

        g2d.setColor(Color.GREEN);
        g2d.drawLine(calculateX(runway.getX1()), calculateY(runway.getY1()),
                calculateX(runway.getX2()), calculateY(runway.getY2()));

        return image;
    }

    /**
     * Calculates the X co-ordinate in the image for the given X co-ordinate in the polygon.
     *
     * @param x The X co-ordinate in the polygon
     * @return The X co-ordinate of the vertex in the image
     */
    private int calculateX(double x) {
        Rectangle2D bounds = poly.getBounds();
        return (int) (((x - bounds.getX()) / bounds.getWidth()) * (width));
    }

    /**
     * Calculates the Y co-ordinate in the image for the given Y co-ordinate in the polygon.
     *
     * @param y The Y co-ordinate in the polygon
     * @return The Y co-ordinate of the vertex in the image
     */
    private int calculateY(double y) {
        Rectangle2D bounds = poly.getBounds();
        return (int) (((y - bounds.getY()) / bounds.getHeight()) * (height));
    }

    /**
     * Formats a number to 6 decimal places so it can be easily displayed in the image
     *
     * @param d The number
     * @return The number with a maximum of 6 decimal places
     */
    private static String formatDouble(double d) {
        return "" + (Math.round(d * 1000000d) / 1000000d);
    }
}

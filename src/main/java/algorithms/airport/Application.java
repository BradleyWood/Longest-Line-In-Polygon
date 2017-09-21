package algorithms.airport;


import javax.imageio.ImageIO;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    private static String output = "output.png";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: You must provide an input polygon file.");
            return;
        }
        String path = args[0];
        if (args.length >= 2) {
            output = args[1];
        }
        try {
            Island poly = loadPoly(path);
            if (poly != null) {

                int width = 400;
                int height = (int) (poly.getBounds().getHeight() / poly.getBounds().getWidth() * width);

                long startTime = System.currentTimeMillis();
                Line2D runway = new AirportCalculator(poly).calculate();
                System.out.println("Calculated in " + (System.currentTimeMillis() - startTime) + " ms.");

                if (runway != null) {
                    System.out.println("Runway length: " + Utility.distanceBetween(runway.getX1(), runway.getY1(), runway.getX2(), runway.getY2()));
                    System.out.println("(" + runway.getX1() + "," + runway.getY1() + ") to (" + runway.getX2() + "," + runway.getY2() + ")");

                    Renderer renderer = new Renderer(poly, runway);
                    BufferedImage img = renderer.toImage(width, height);
                    ImageIO.write(img, "png", new FileOutputStream(output));
                } else {
                    System.out.println("The polygon is invalid: No two vertices can form a valid runway");
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file. " + path);
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.out.println("Invalid file format.");
        }
    }

    private static Island loadPoly(final String file) throws IOException {
        Scanner sc = new Scanner(new FileInputStream(file));

        int numVertices = sc.nextInt();

        if (numVertices < 3) {
            System.out.println("Polygon needs 3 or more points!");
            return null;
        }

        int[] xpoints = new int[numVertices];
        int[] ypoints = new int[numVertices];
        int i = 0;

        while (sc.hasNext()) {
            if (i % 2 == 0) {
                xpoints[i / 2] = sc.nextInt();
            } else {
                ypoints[i / 2] = sc.nextInt();
            }
            i++;
        }

        if (numVertices != i / 2) {
            System.out.println("Expected " + numVertices + " vertices but found " + i / 2);
            return null;
        }
        return new Island(xpoints, ypoints, numVertices);
    }
}

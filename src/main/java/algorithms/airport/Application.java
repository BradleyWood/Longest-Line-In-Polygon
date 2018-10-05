package algorithms.airport;

import javax.imageio.ImageIO;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Application {

    private static String output = "output.png";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: You must provide an input polygon file.");
            return;
        }

        final String path = args[0];

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
                    System.out.println("Runway length: " + runway.getP1().distance(runway.getP2()));
                    System.out.println("(" + runway.getX1() + "," + runway.getY1() + ") to (" + runway.getX2() + "," + runway.getY2() + ")");

                    Renderer renderer = new Renderer(poly, runway);
                    BufferedImage img = renderer.toImage(width, height);
                    ImageIO.write(img, "png", new FileOutputStream(output));
                } else {
                    System.out.println("The polygon is invalid: No two vertices can form a valid runway");
                }
            } else {
                System.out.println("Invalid file format.");
            }
        } catch (IOException e) {
            System.out.println("Failed to read file. " + path);
            e.printStackTrace();
        }
    }

    private static Island loadPoly(final String file) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        final List<Integer> xPoints = new ArrayList<>();
        final List<Integer> yPoints = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty())
                continue;

            final String[] numbers = line.split(" ");

            if (numbers.length != 2)
                return null;

            try {
                xPoints.add(Integer.parseInt(numbers[0]));
                yPoints.add(Integer.parseInt(numbers[1]));
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return new Island(xPoints.stream().mapToInt(n -> n).toArray(),
                yPoints.stream().mapToInt(n -> n).toArray(), xPoints.size());
    }
}

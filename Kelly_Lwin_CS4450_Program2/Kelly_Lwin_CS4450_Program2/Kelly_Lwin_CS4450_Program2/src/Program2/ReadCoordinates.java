package Program2;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCoordinates {

    public static List<Polygon> readPolygons(String filename) {
        List<Polygon> polygons = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Polygon polygon = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("P")) {
                    if (polygon != null) {
                        polygons.add(polygon);
                    }

                    String[] parts = line.split(" ");
                    float r = Float.parseFloat(parts[1]);
                    float g = Float.parseFloat(parts[2]);
                    float b = Float.parseFloat(parts[3]);

                    polygon = new Polygon(r, g, b);
                } 
                else if (line.startsWith("T")) {
                    // Transformation section starts
                } 
                else if (line.startsWith("r") || line.startsWith("s") || line.startsWith("t")) {
                    if (polygon != null) {
                        polygon.addTransformation(line);
                    }
                } 
                else {
                    String[] parts = line.split(" ");
                    if (polygon != null) {
                        polygon.addVertex(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]));
                    }
                }
            }

            if (polygon != null) {
                polygons.add(polygon);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return polygons;
    }
}

package Program2;

import java.util.*;
import java.io.*;

public class ReadCoordinates {
    public static List<Polygon> readPolygons(String filename) {
        List<Polygon> polygons = new ArrayList<>();
        Polygon currentPoly = null;
        boolean inTransformations = false;
        
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = br.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty())
                    continue;
                
                if(line.startsWith("P")) {
                    // If a polygon is already being read, add it to the list.
                    if(currentPoly != null) {
                        polygons.add(currentPoly);
                    }
                    currentPoly = new Polygon();
                    inTransformations = false;
                    // Parse the fill color from the "P" line.
                    String[] parts = line.split("\\s+");
                    if(parts.length >= 4) {
                        float r = Float.parseFloat(parts[1]);
                        float g = Float.parseFloat(parts[2]);
                        float b = Float.parseFloat(parts[3]);
                        currentPoly.setColor(r, g, b);
                    }
                } else if(line.startsWith("T")) {
                    inTransformations = true;
                } else if(inTransformations && (line.startsWith("t") || line.startsWith("r") || line.startsWith("s"))) {
                    // Record transformation commands in the order encountered.
                    currentPoly.addTransformation(line);
                } else {
                    // Otherwise assume the line contains vertex coordinates "x y".
                    String[] parts = line.split("\\s+");
                    if(parts.length >= 2) {
                        float x = Float.parseFloat(parts[0]);
                        float y = Float.parseFloat(parts[1]);
                        currentPoly.addVertex(x, y);
                    }
                }
            }
            if(currentPoly != null) {
                polygons.add(currentPoly);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return polygons;
    }
}

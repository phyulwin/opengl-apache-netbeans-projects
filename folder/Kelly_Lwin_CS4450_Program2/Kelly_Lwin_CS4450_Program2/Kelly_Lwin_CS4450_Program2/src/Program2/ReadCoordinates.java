/***************************************************************
* file: ReadCoordinates.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: Program 2
* date last modified: 3/3/2025
* purpose: This program reads a file that contains
* polygon color, vertices, and transformation descriptions, 
* parses and draws them. 
* note: The code contains content assisted by AI.
****************************************************************/
package Program2;

import java.util.*;
import java.io.*;

public class ReadCoordinates {
    // method: readPolygons
    // purpose: Reads file line by line, determines characteristics of a new polygon, then return all polygons.
    public static List<Polygon> readPolygons(String filename) {
        List<Polygon> polygons = new ArrayList<>();
        Polygon newPolygon = null;
        boolean inTransformations = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                if (line.startsWith("P")) {
                    // If a polygon is already being read, add it to the list
                    if (newPolygon != null) {
                        polygons.add(newPolygon);
                    }
                    newPolygon = createPolygon(line);
                    inTransformations = false;
                } 
                else if (line.startsWith("T")) {
                    inTransformations = true;
                } 
                else if (inTransformations && isTransformation(line)) {
                    newPolygon.addTransformation(line);
                } 
                else {
                    addVertex(newPolygon, line);
                }
            }
            
            // Add the last polygon read if any
            if (newPolygon != null) {
                polygons.add(newPolygon);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return polygons;
    }

    // method: createPolygon
    // purpose: Creates a new Polygon from a "P" line and sets fill color.
    private static Polygon createPolygon(String line) {
        Polygon newPolygon = new Polygon();
        String[] parts = line.split("\\s+");
        
        if (parts.length >= 4) {
            float r = Float.parseFloat(parts[1]);
            float g = Float.parseFloat(parts[2]);
            float b = Float.parseFloat(parts[3]);
            newPolygon.setColor(r, g, b);
        }
        return newPolygon;
    }
    
    // method: isTransformation
    // purpose: Checks if a line represents a transformation command.
    private static boolean isTransformation(String line) {
        return line.startsWith("t") || line.startsWith("r") || line.startsWith("s");
    }

    // method: addVertex
    // purpose: Parses a line with vertex coordinates and adds the vertex to the current polygon.
    private static void addVertex(Polygon polygon, String line) {
        String[] parts = line.split("\\s+");
        
        if (parts.length >= 2) {
            float x = Float.parseFloat(parts[0]);
            float y = Float.parseFloat(parts[1]);
            polygon.addVertex(x, y);
        }
    }
}


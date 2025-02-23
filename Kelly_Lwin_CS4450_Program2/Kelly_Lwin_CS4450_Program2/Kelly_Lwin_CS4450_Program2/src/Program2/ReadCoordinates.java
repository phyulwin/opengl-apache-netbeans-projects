/***************************************************************
* file: ReadCoordinates.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: program 1
* date last modified: 2/10/2025
*
* purpose: This program reads a file that contains 
* shape descriptions, parses their coordinates, and draws them.
* note: The code contains content assisted by AI.
****************************************************************/

package Program2;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class ReadCoordinates {

    public static void readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Polygon polygon = null;  // Using the separate Polygon class

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("P")) {
                    if (polygon != null) {
                        polygon.print();  // Print the previously read polygon
                    }

                    // Read RGB values
                    String[] parts = line.split(" ");
                    float r = Float.parseFloat(parts[1]);
                    float g = Float.parseFloat(parts[2]);
                    float b = Float.parseFloat(parts[3]);

                    // Create a new Polygon object
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
                    // Read vertices
                    String[] parts = line.split(" ");
                    if (polygon != null) {
                        polygon.addVertex(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]));
                    }
                }
            }

            // Print last polygon if any
            if (polygon != null) {
                polygon.print();
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

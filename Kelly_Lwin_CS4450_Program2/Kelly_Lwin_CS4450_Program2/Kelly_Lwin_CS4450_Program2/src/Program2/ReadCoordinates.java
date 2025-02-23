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

    // method: readFile
    // purpose: Reads the file line by line and processes each line.
    public static void readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            List<float[]> polygonVertices = new ArrayList<>();
            float[] color = new float[3]; // RGB Color
            List<String[]> transformations = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("P")) {
                    if (!polygonVertices.isEmpty()) {
                        // Apply transformations and draw previous polygon
                        drawPolygon(polygonVertices, color, transformations);
                        polygonVertices.clear();
                        transformations.clear();
                    }

                    // Read RGB values
                    String[] parts = line.split(" ");
                    color[0] = Float.parseFloat(parts[1]); // Red
                    color[1] = Float.parseFloat(parts[2]); // Green
                    color[2] = Float.parseFloat(parts[3]); // Blue
                } 
                else if (line.startsWith("T")) {
                    // Transformation section starts, do nothing
                } 
                else if (line.startsWith("r") || line.startsWith("s") || line.startsWith("t")) {
                    transformations.add(line.split(" "));
                } 
                else {
                    // Read vertices
                    String[] parts = line.split(" ");
                    polygonVertices.add(new float[]{Float.parseFloat(parts[0]), Float.parseFloat(parts[1])});
                }
            }

            // Draw last polygon if any
            if (!polygonVertices.isEmpty()) {
                drawPolygon(polygonVertices, color, transformations);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    private static void drawPolygon(List<float[]> vertices, float[] color, List<String[]> transformations) {
        // Apply transformations
        List<float[]> transformedVertices = applyTransformations(vertices, transformations);

        // Draw the polygon using scanline fill
        glColor3f(color[0], color[1], color[2]); // Set fill color
        glBegin(GL_POLYGON);
        for (float[] vertex : transformedVertices) {
            glVertex2f(vertex[0], vertex[1]);
        }
        glEnd();
    }

    private static List<float[]> applyTransformations(List<float[]> vertices, List<String[]> transformations) {
        List<float[]> transformedVertices = new ArrayList<>(vertices);

        for (String[] transformation : transformations) {
            String type = transformation[0];

            switch (type) {
                case "r": // Rotation
                    float angle = (float) Math.toRadians(Float.parseFloat(transformation[1]));
                    float cosA = (float) Math.cos(angle);
                    float sinA = (float) Math.sin(angle);

                    for (float[] vertex : transformedVertices) {
                        float x = vertex[0];
                        float y = vertex[1];
                        vertex[0] = x * cosA - y * sinA;
                        vertex[1] = x * sinA + y * cosA;
                    }
                    break;

                case "s": // Scaling
                    float scaleX = Float.parseFloat(transformation[1]);
                    float scaleY = Float.parseFloat(transformation[2]);

                    for (float[] vertex : transformedVertices) {
                        vertex[0] *= scaleX;
                        vertex[1] *= scaleY;
                    }
                    break;

                case "t": // Translation
                    float tx = Float.parseFloat(transformation[1]);
                    float ty = Float.parseFloat(transformation[2]);

                    for (float[] vertex : transformedVertices) {
                        vertex[0] += tx;
                        vertex[1] += ty;
                    }
                    break;
            }
        }
        return transformedVertices;
    }
}
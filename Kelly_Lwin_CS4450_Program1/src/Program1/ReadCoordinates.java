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

package Program1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCoordinates {

    // method: readFile
    // purpose: Reads the file line by line and processes each line.
    public static void readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseLineFromFile(line);
            }
        } catch (IOException e) {
            // Safely exits the program is file reading error occurs.
            System.out.println("Error: Failed to read file - " + e.getMessage());
            System.exit(1);
        }
    }

    // method: parseLineFromFile
    // purpose: Parses a line from the file to determine the shape type and extract its properties.
    private static void parseLineFromFile(String line) {
        if (line.isEmpty()) {
            return; // Ignore empty lines
        }
        char shapeType = line.charAt(0);
        String[] parts = line.substring(2).split(" ");
        
        // Checks the first character which indicates shape type
        switch (shapeType) {
            case 'c':
                parseCircle(parts);
                break;
            case 'e':
                parseEllipse(parts);
                break;
            case 'l':
                parseLine(parts);
                break;
            default:
                // Safely exits the program to avoid crashing
                System.out.println("Unknown Shape");
                System.exit(1);  
                break;
        }
    }

    // method: parseLine
    // purpose: Parses line data and calls the drawing method.
    private static void parseLine(String[] parts) {
        try {
            String[] start = parts[0].split(",");
            int x1 = Integer.parseInt(start[0]);
            int y1 = Integer.parseInt(start[1]);

            String[] end = parts[1].split(",");
            int x2 = Integer.parseInt(end[0]);
            int y2 = Integer.parseInt(end[1]);

            Line line = new Line(x1, y1, x2, y2);
            line.draw();
        } catch (Exception e) {
            System.out.println("Error: Invalid line format - " + e.getMessage());
            System.exit(1);
        }
    }

    // method: parseCircle
    // purpose: Parses circle data and calls the drawing method.
    private static void parseCircle(String[] parts) {
        try {
            String[] center = parts[0].split(",");
            int x = Integer.parseInt(center[0]);
            int y = Integer.parseInt(center[1]);

            int radius = Integer.parseInt(parts[1]);

            Circle circle = new Circle(x, y, radius);
            circle.draw();
        } catch (Exception e) {
            System.out.println("Error: Invalid circle format - " + e.getMessage());
            System.exit(1);
        }
    }

    // method: parseEllipse
    // purpose: Parses ellipse data and calls the drawing method.
    private static void parseEllipse(String[] parts) {
        try {
            // Extract and parse the center coordinates (x, y)
            String[] center = parts[0].split(",");
            int x = Integer.parseInt(center[0]);
            int y = Integer.parseInt(center[1]);
            
            // Extract and parse the radii (rx, ry)
            String[] radii = parts[1].split(",");
            int rx = Integer.parseInt(radii[0]);
            int ry = Integer.parseInt(radii[1]);
            
            // Create an Ellipse object with parsed values
            Ellipse ellipse = new Ellipse(x, y, rx, ry);
            ellipse.draw();
        } catch (Exception e) {
            System.out.println("Error: Invalid ellipse format - " + e.getMessage());
            System.exit(1);
        }
    }
}
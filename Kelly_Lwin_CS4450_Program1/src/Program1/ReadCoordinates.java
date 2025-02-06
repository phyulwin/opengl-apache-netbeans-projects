package Program1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Program1.DrawPrimitives;

public class ReadCoordinates {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java ShapeParser <filename>");
            return;
        }

        String filename = args[0];
        readFile(filename);
    }

    public static void readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void parseLine(String line) {
        if (line.isEmpty()) return;
        char shapeType = line.charAt(0);
        String[] parts = line.substring(2).split(" ");
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
                System.out.println("Unknown shape type: " + shapeType);
                break;
        }
    }

    private static void parseCircle(String[] parts) {
        String[] center = parts[0].split(",");
        int x = Integer.parseInt(center[0]);
        int y = Integer.parseInt(center[1]);
        int radius = Integer.parseInt(parts[1]);
        DrawPrimitives.drawCircle(x, y, radius);
    }

    private static void parseEllipse(String[] parts) {
        String[] center = parts[0].split(",");
        int x = Integer.parseInt(center[0]);
        int y = Integer.parseInt(center[1]);
        String[] radii = parts[1].split(",");
        int rx = Integer.parseInt(radii[0]);
        int ry = Integer.parseInt(radii[1]);
        DrawPrimitives.drawEllipse(x, y, rx, ry);
    }

    private static void parseLine(String[] parts) {
        String[] start = parts[0].split(",");
        int x1 = Integer.parseInt(start[0]);
        int y1 = Integer.parseInt(start[1]);
        String[] end = parts[1].split(",");
        int x2 = Integer.parseInt(end[0]);
        int y2 = Integer.parseInt(end[1]);
        DrawPrimitives.drawLine(x1, y1, x2, y2);
    }
    
}

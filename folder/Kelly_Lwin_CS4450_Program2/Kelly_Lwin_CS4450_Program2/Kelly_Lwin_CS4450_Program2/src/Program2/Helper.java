/***************************************************************
* file: Helper.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: Program 2
* date last modified: 3/3/2025
* purpose: This is a helper class (static methods).
* note: The code contains content assisted by AI.
* references used: https://open.gl/drawing; https://open.gl/transformations 
****************************************************************/
package Program2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helper {
    // method: getYBounds
    // purpose: Compute the minimum and maximum y-values from a list of vertices
    public static float[] getYBounds(List<Point> points) {
        // Initialize yMin with maximum possible value
        float yMin = Float.MAX_VALUE;
        // Initialize yMax with minimum possible value
        float yMax = -Float.MAX_VALUE;
        // Loop through all points to find the bounds
        for (Point p : points) {
            if (p.y < yMin) yMin = p.y;
            if (p.y > yMax) yMax = p.y;
        }
        // Return yMin and yMax as an array
        return new float[] {yMin, yMax};
    }
    
    // method: getScanlineIntersections
    // purpose: For a given y-value (scanline), determine all x-intersections with the polygon edges
    public static List<Float> getScanlineIntersections(List<Point> points, int y) {
        List<Float> xIntersections = new ArrayList<>();

        // Loop through each edge of the polygon
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            // Check if the scanline intersects the edge between p1 and p2
            if ((p1.y <= y && p2.y > y) || (p2.y <= y && p1.y > y)) {
                // Compute the x-coordinate of the intersection
                float x = p1.x + (y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
                // Add the intersection to the list
                xIntersections.add(x);
            }
        }
        // Sort intersections from left to right
        Collections.sort(xIntersections);
        // Return the list of intersections
        return xIntersections;
    }
    
    // method: multiplyMatrix
    // purpose: Multiply two 3x3 matrices
    public static double[][] multiplyMatrix(double[][] A, double[][] B) {
        double[][] C = new double[3][3];
        
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                C[i][j] = 0;
                for (int k = 0; k < 3; k++){
                    // Accumulate products for each element
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        // Return the multiplied matrix
        return C;
    }
    
    // method: applyMatrix
    // purpose: Apply a 3x3 matrix M to a point p (as homogeneous coordinate)
    public static Point applyMatrix(double[][] M, Point p) {
        // Compute transformed x
        double x = M[0][0] * p.x + M[0][1] * p.y + M[0][2];
        // Compute transformed y
        double y = M[1][0] * p.x + M[1][1] * p.y + M[1][2];
        // Return new transformed point
        return new Point((float)x, (float)y);
    }
    
    // method: computeCompositeMatrix
    // purpose: Compute the composite transformation matrix from all commands
    private static double[][] computeCompositeMatrix(List<String> transformations) {
        // Start with an identity matrix
        double[][] composite = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        
        // Process each transformation command in order
        for(String cmd : transformations) {
            String[] parts = cmd.split("\\s+");
            if(parts.length == 0) {
                continue;
            }
            char type = parts[0].charAt(0);
            double[][] M = null;
            
            switch(type) {
                // Translation: "t dx dy"
                case 't': 
                    if(parts.length >= 3) {
                        double dx = Double.parseDouble(parts[1]);
                        double dy = Double.parseDouble(parts[2]);
                        // Build translation matrix
                        M = new double[][] {
                            {1, 0, dx},
                            {0, 1, dy},
                            {0, 0, 1}
                        };
                    }
                    break;
                // Rotation: "r angle pivotX pivotY"
                case 'r': 
                    if(parts.length >= 4) {
                        double angle = Math.toRadians(Double.parseDouble(parts[1]));
                        double px = Double.parseDouble(parts[2]);
                        double py = Double.parseDouble(parts[3]);
                        // Build the composite for rotation about a pivot
                        // Translate pivot to origin
                        double[][] T1 = {
                            {1, 0, -px},
                            {0, 1, -py},
                            {0, 0, 1}
                        };
                        // Rotation matrix
                        double[][] R = {
                            {Math.cos(angle), -Math.sin(angle), 0},
                            {Math.sin(angle),  Math.cos(angle), 0},
                            {0, 0, 1}
                        };
                        // Translate back to original position
                        double[][] T2 = {
                            {1, 0, px},
                            {0, 1, py},
                            {0, 0, 1}
                        };
                        // Composite rotation matrix
                        M = Helper.multiplyMatrix(T2, Helper.multiplyMatrix(R, T1));
                    }
                    break;
                // Scaling: "s sx sy"
                case 's': 
                    if(parts.length >= 3) {
                        double sx = Double.parseDouble(parts[1]);
                        double sy = Double.parseDouble(parts[2]);
                        // Build scaling matrix
                        M = new double[][] {
                            {sx, 0, 0},
                            {0, sy, 0},
                            {0, 0, 1}
                        };
                    }
                    break;
            }
            if(M != null) {
                // Pre-multiply to maintain the file order
                composite = Helper.multiplyMatrix(M, composite);
            }
        }
        // Return the final composite transformation matrix
        return composite;
    }
    
    // method: getTransformedVertices
    // purpose: Get the list of vertices after applying the composite transformation
    public static List<Point> getTransformedVertices(List<Point> vertices, List<String> transformations) {
        // Compute composite matrix
        double[][] composite = computeCompositeMatrix(transformations);
        List<Point> transformed = new ArrayList<>();
        
        // Transform each vertex using the composite matrix
        for (Point p : vertices) {
            transformed.add(Helper.applyMatrix(composite, p));
        }
        // Return the transformed vertices
        return transformed;
    }
}

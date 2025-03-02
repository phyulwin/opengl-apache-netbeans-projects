package Program2;

import static org.lwjgl.opengl.GL11.*;
import java.util.*;
import java.lang.Math;

public class Polygon {
    // Nested class for a 2D point.
    public static class Point {
        public float x, y;
        public Point(float x, float y) { this.x = x; this.y = y; }
    }
    
    // List of vertices (in model space).
    private List<Point> vertices;
    // Fill color components.
    private float r, g, b, a;
    // Transformation commands stored in the order they appear in the file.
    private List<String> transformationCommands;
    
    public Polygon() {
        vertices = new ArrayList<>();
        transformationCommands = new ArrayList<>();
        // Default to white and fully opaque.
        r = g = b = 1.0f;
        a = 1.0f;
    }
    
    public void addVertex(float x, float y) {
        vertices.add(new Point(x, y));
    }
    
    public void setColor(float r, float g, float b, float a) {
        this.r = r; this.g = g; this.b = b; this.a = a;
    }
    
    // Overloaded setColor without alpha (defaults to opaque).
    public void setColor(float r, float g, float b) {
        setColor(r, g, b, 1.0f);
    }
    
    public void addTransformation(String command) {
        transformationCommands.add(command);
    }
    
    // Helper: Multiply two 3x3 matrices.
    private double[][] multiplyMatrix(double[][] A, double[][] B) {
        double[][] C = new double[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                C[i][j] = 0;
                for (int k = 0; k < 3; k++){
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
    
    // Helper: Apply a 3x3 matrix M to a point p (as homogeneous coordinate).
    private Point applyMatrix(double[][] M, Point p) {
        double x = M[0][0] * p.x + M[0][1] * p.y + M[0][2];
        double y = M[1][0] * p.x + M[1][1] * p.y + M[1][2];
        return new Point((float)x, (float)y);
    }
    
    // Computes the composite transformation matrix from the stored transformation commands.
    private double[][] computeCompositeMatrix() {
        double[][] composite = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        for(String cmd : transformationCommands) {
            String[] parts = cmd.split("\\s+");
            if(parts.length == 0) continue;
            char type = parts[0].charAt(0);
            double[][] M = null;
            switch(type) {
                case 't': // Translation: "t dx dy"
                    if(parts.length >= 3) {
                        double dx = Double.parseDouble(parts[1]);
                        double dy = Double.parseDouble(parts[2]);
                        M = new double[][] {
                            {1, 0, dx},
                            {0, 1, dy},
                            {0, 0, 1}
                        };
                    }
                    break;
                case 'r': // Rotation: "r angle pivotX pivotY"
                    if(parts.length >= 4) {
                        double angle = Math.toRadians(Double.parseDouble(parts[1]));
                        double px = Double.parseDouble(parts[2]);
                        double py = Double.parseDouble(parts[3]);
                        // Build the composite for rotation about a pivot.
                        double[][] T1 = {
                            {1, 0, -px},
                            {0, 1, -py},
                            {0, 0, 1}
                        };
                        double[][] R = {
                            {Math.cos(angle), -Math.sin(angle), 0},
                            {Math.sin(angle),  Math.cos(angle), 0},
                            {0, 0, 1}
                        };
                        double[][] T2 = {
                            {1, 0, px},
                            {0, 1, py},
                            {0, 0, 1}
                        };
                        M = multiplyMatrix(T2, multiplyMatrix(R, T1));
                    }
                    break;
                case 's': // Scaling: "s sx sy"
                    if(parts.length >= 3) {
                        double sx = Double.parseDouble(parts[1]);
                        double sy = Double.parseDouble(parts[2]);
                        M = new double[][] {
                            {sx, 0, 0},
                            {0, sy, 0},
                            {0, 0, 1}
                        };
                    }
                    break;
            }
            if(M != null) {
                // Pre-multiply to maintain the file order.
                composite = multiplyMatrix(M, composite);
            }
        }
        return composite;
    }
    
    // Draws the polygon by applying the composite transformation and using the scanline fill algorithm.
    public void draw() {
        // Compute composite transformation matrix.
        double[][] composite = computeCompositeMatrix();
        // Transform vertices.
        List<Point> transformed = new ArrayList<>();
        for(Point p : vertices) {
            transformed.add(applyMatrix(composite, p));
        }
        int n = transformed.size();
        if(n < 3) return; // Not a valid polygon.
        
        // Determine y bounds.
        float yMin = Float.MAX_VALUE;
        float yMax = -Float.MAX_VALUE;
        for(Point p : transformed) {
            if(p.y < yMin) yMin = p.y;
            if(p.y > yMax) yMax = p.y;
        }
        
        // Set the fill color.
        glColor4f(r, g, b, a);
        
        // For each scanline, compute intersections with polygon edges and draw horizontal lines.
        for (int y = (int)Math.ceil(yMin); y <= (int)Math.floor(yMax); y++) {
            List<Float> xIntersections = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Point p1 = transformed.get(i);
                Point p2 = transformed.get((i + 1) % n);
                if ((p1.y <= y && p2.y > y) || (p2.y <= y && p1.y > y)) {
                    float x = p1.x + (y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
                    xIntersections.add(x);
                }
            }
            Collections.sort(xIntersections);
            glBegin(GL_LINES);
            for (int i = 0; i < xIntersections.size(); i += 2) {
                if (i + 1 < xIntersections.size()) {
                    glVertex2f(xIntersections.get(i), y);
                    glVertex2f(xIntersections.get(i + 1), y);
                }
            }
            glEnd();
        }
    }
}

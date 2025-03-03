/***************************************************************
* file: Polygon.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: Program 2
* date last modified: 3/3/2025
* purpose: This class represents a polygon.
* note: The code contains content assisted by AI.
****************************************************************/
package Program2;

import static org.lwjgl.opengl.GL11.*;
import java.util.*;
import java.lang.Math;

public class Polygon {
    // Instance variables
    private List<Point> vertices;
    private float r, g, b;
    private List<String> transformations;
    
    // method: Polygon
    // purpose: Default Constructor
    public Polygon() {
        vertices = new ArrayList<>();
        transformations = new ArrayList<>();
        r = g = b = 1.0f;
    }
    
    // method: addVertex
    // purpose: Add a vertex to the polygon
    public void addVertex(float x, float y) {
        vertices.add(new Point(x, y));
    }
    
    // method: setColor
    // purpose: Set the color of the polygon
    public void setColor(float r, float g, float b) {
        this.r = r; this.g = g; this.b = b;
    }
    
    // method: addTransformation
    // purpose: Add a transformation command (translation, rotation, scaling)
    public void addTransformation(String command) {
        transformations.add(command);
    }
    
    // method: draw
    // purpose: Draw the polygon using scanline fill after applying transformations
    public void draw() {
        // Get transformed vertices using composite transformation
        List<Point> transformed = Helper.getTransformedVertices(vertices, transformations);
        
        // Exit if the polygon has less than 3 vertices
        if (transformed.size() < 3) {
            return;
        }
        
        // Get vertical bounds (yMin and yMax) from the transformed vertices
        float[] bounds = Helper.getYBounds(transformed);
        float yMin = bounds[0];
        float yMax = bounds[1];
        
        // Set fill color for the polygon
        glColor4f(r, g, b, 1.0f);
        
        // Loop over each horizontal scanline between yMin and yMax
        for (int y = (int)Math.ceil(yMin); y <= (int)Math.floor(yMax); y++) {
            // Get x-intersections for the current scanline
            List<Float> xIntersections = Helper.getScanlineIntersections(transformed, y);
            glBegin(GL_LINES);
            // Draw line segments between pairs of intersections
            for (int i = 0; i < xIntersections.size(); i += 2) {
                if (i + 1 < xIntersections.size()) {
                    // Set starting point of the segment
                    glVertex2f(xIntersections.get(i), y);
                    // Set ending point of the segment
                    glVertex2f(xIntersections.get(i + 1), y);
                }
            }
            glEnd();
        }
    }
}

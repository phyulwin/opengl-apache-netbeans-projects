/***************************************************************
* file: Polygon.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: program 1
* date last modified: 2/10/2025
*
* purpose: This class represents a polygon object with color, 
* vertices, and transformations. It provides methods to 
* print and draw the polygon.
****************************************************************/

package Program2;

// import required modules
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;

import java.util.List;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Polygon {
    private float[] color;
    private List<float[]> vertices;
    private List<String> transformations;

    public Polygon(float r, float g, float b) {
        this.color = new float[]{r, g, b};
        this.vertices = new ArrayList<>();
        this.transformations = new ArrayList<>();
    }

    public void addVertex(float x, float y) {
        vertices.add(new float[]{x, y});
    }

    public void addTransformation(String transformation) {
        transformations.add(transformation);
    }

    public void draw() {
        glPushMatrix();  // Save current transformation state
        
        // Apply transformations before drawing
        for (String transformation : transformations) {
            String[] parts = transformation.split(" ");
            switch (parts[0]) {
                case "r": // Rotation
                    float angle = Float.parseFloat(parts[1]);
                    float pivotX = Float.parseFloat(parts[2]);
                    float pivotY = Float.parseFloat(parts[3]);
                    glTranslatef(pivotX, pivotY, 0);
                    glRotatef(angle, 0, 0, 1);
                    glTranslatef(-pivotX, -pivotY, 0);
                    break;

                case "s": // Scaling
                    float scaleX = Float.parseFloat(parts[1]);
                    float scaleY = Float.parseFloat(parts[2]);
                    float scalePivotX = Float.parseFloat(parts[3]);
                    glTranslatef(scalePivotX, scalePivotX, 0);
                    glScalef(scaleX, scaleY, 1);
                    glTranslatef(-scalePivotX, -scalePivotX, 0);
                    break;

                case "t": // Translation
                    float translateX = Float.parseFloat(parts[1]);
                    float translateY = Float.parseFloat(parts[2]);
                    glTranslatef(translateX, translateY, 0);
                    break;
            }
        }

        // Set color
        glColor3f(color[0], color[1], color[2]);

        // Draw the polygon
        glBegin(GL_POLYGON);
        for (float[] vertex : vertices) {
            glVertex2f(vertex[0], vertex[1]);
        }
        glEnd();

        glPopMatrix();  // Restore previous transformation state
    }

    public void print() {
        System.out.println("Polygon:");
        System.out.printf("Color: R=%.2f, G=%.2f, B=%.2f\n", color[0], color[1], color[2]);

        System.out.println("Vertices:");
        for (float[] vertex : vertices) {
            System.out.printf("(%.2f, %.2f)\n", vertex[0], vertex[1]);
        }

        System.out.println("Transformations:");
        for (String transformation : transformations) {
            System.out.println(transformation);
        }

        System.out.println("--------------------------------------------------");
    }
}

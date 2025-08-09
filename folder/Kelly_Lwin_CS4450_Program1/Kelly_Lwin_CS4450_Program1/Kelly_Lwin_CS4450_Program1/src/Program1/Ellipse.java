/***************************************************************
* file: Ellipse.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: program 1
* date last modified: 2/10/2025
*
* purpose: This class represents a ellipse.
* note: The code contains content assisted by AI.
****************************************************************/

package Program1;

import static org.lwjgl.opengl.GL11.*;

public class Ellipse {

    // Instance variables
    float centerX;
    float centerY;
    float radiusX;
    float radiusY;
    float[] color;

    // method: Ellipse
    // purpose: Default constructor to set default values.
    public Ellipse() {
        this.centerX = 0.0f;
        this.centerY = 0.0f;
        this.radiusX = 1.0f;
        this.radiusY = 1.0f;
        this.color = new float[]{0.0f, 1.0f, 0.0f}; // Default to green
    }

    // method: Ellipse (constructor)
    // purpose: Initializes an ellipse with specified center and radii and sets default color (red).
    public Ellipse(float centerX, float centerY, float radiusX, float radiusY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.color = new float[]{0.0f, 1.0f, 0.0f}; // Default to green
    }

    // method: draw
    // purpose: Draws the ellipse using OpenGL.
    public void draw() {
        glColor3f(color[0], color[1], color[2]);

        int numSegments = 100; // Higher number gives a smoother ellipse
        float theta;

        glBegin(GL_LINE_LOOP); // Use GL_LINE_LOOP to create the outline of an ellipse
        for (int i = 0; i < numSegments; i++) {
            theta = (float) (2.0f * Math.PI * i / numSegments); // Compute angle
            float x = (float) (radiusX * Math.cos(theta)); // Compute x coordinate
            float y = (float) (radiusY * Math.sin(theta)); // Compute y coordinate
            glVertex2f(centerX + x, centerY + y); // Offset by center coordinates
        }
        glEnd();
    }
}
/***************************************************************
* file: Circle.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: program 1
* date last modified: 2/10/2025
*
* purpose: This class represents a circle.
* note: The code contains content assisted by AI.
****************************************************************/

package Program1;

import static org.lwjgl.opengl.GL11.*;

public class Circle {

    // Instance variables
    float radius;
    float centerX;
    float centerY;
    float[] color;

    // method: Circle 
    // purpose: Default constructor to set default values.
    public Circle() {
        this.centerX = 0.0f;
        this.centerY = 0.0f;
        this.radius = 1.0f;
        this.color = new float[]{0.0f, 0.0f, 1.0f}; // Default to blue
    }

    // method: Circle (constructor)
    // purpose: Initializes a circle with specified center and radius and sets default color (red).
    public Circle(float centerX, float centerY, float radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.color = new float[]{0.0f, 0.0f, 1.0f}; // Default to blue
    }

    // method: draw
    // purpose: Draws the circle using OpenGL.
    public void draw() {
        glColor3f(color[0], color[1], color[2]);

        int numSegments = 100; // Higher number gives a smoother circle
        float theta;

        glBegin(GL_LINE_LOOP); // Use GL_LINE_LOOP to create the outline of a circle
        for (int i = 0; i < numSegments; i++) {
            theta = (float) (2.0f * Math.PI * i / numSegments); // Compute angle
            float x = (float) (radius * Math.cos(theta)); // Compute x coordinate
            float y = (float) (radius * Math.sin(theta)); // Compute y coordinate
            glVertex2f(centerX + x, centerY + y); // Offset by center coordinates
        }
        glEnd();
    }
}
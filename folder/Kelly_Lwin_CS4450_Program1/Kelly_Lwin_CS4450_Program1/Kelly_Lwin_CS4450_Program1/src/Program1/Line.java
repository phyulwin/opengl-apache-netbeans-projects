/***************************************************************
* file: Line.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: program 1
* date last modified: 2/10/2025
*
* purpose: This class represents a line.
* note: The code contains content assisted by AI.
****************************************************************/

package Program1;

import static org.lwjgl.opengl.GL11.*;

public class Line {

    // Instance variables
    float startX;
    float startY;
    float endX;
    float endY;
    float[] color;

    // method: Line 
    // purpose: Default constructor to set default values
    public Line() {
        this.startX = 0.0f;
        this.startY = 0.0f;
        this.endX = 1.0f;
        this.endY = 1.0f;
        this.color = new float[]{1.0f, 0.0f, 0.0f}; // Default to red
    }
    
    // method: Line (constructor)
    // purpose: Initializes a line with specified start and end points and sets default color (red).
    public Line(float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = new float[]{1.0f, 0.0f, 0.0f}; // Default to red
    }

    // method: draw
    // purpose: Draws the line using OpenGL with the specified color.
    public void draw() {
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_LINES); // Begin drawing a line
        glVertex2f(startX, startY); // Specify the starting vertex
        glVertex2f(endX, endY); // Specify the ending vertex
        glEnd(); 
    }
}
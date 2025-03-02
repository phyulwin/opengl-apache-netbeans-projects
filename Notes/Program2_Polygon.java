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
 ***************************************************************/

package Program2;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class Polygon {
    // Vertex data fields
    private float[] vertices;
    private int vertexCount;
    private ByteBuffer vertexBuffer;

    // Transformation properties
    private float translationX = 0.0f;
    private float translationY = 0.0f;
    private float rotation = 0.0f; // in degrees
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    
    // Color property (RGBA)
    private float[] color = {1.0f, 1.0f, 1.0f, 1.0f}; // default white

    /**
     * Constructs a polygon using the given vertex array.
     * The vertices should be in the format: x, y, z for each vertex.
     */
    public Polygon(float[] vertices) {
        this.vertices = vertices;
        this.vertexCount = vertices.length / 3;
        prepareVertexBuffer();
    }
    
    /**
     * Sets the color for the polygon.
     * @param r red component (0 to 1)
     * @param g green component (0 to 1)
     * @param b blue component (0 to 1)
     * @param a alpha component (0 to 1)
     */
    public void setColor(float r, float g, float b, float a) {
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
        this.color[3] = a;
    }
    
    /**
     * Prepares the ByteBuffer for vertex data.
     */
    private void prepareVertexBuffer() {
        // Each float is 4 bytes.
        vertexBuffer = BufferUtils.createByteBuffer(vertices.length * 4);
        FloatBuffer floatView = vertexBuffer.asFloatBuffer();
        floatView.put(vertices);
        floatView.flip(); // Prepare the buffer for reading
    }
    
    /**
     * Sets the translation (position) for the polygon.
     */
    public void setTranslation(float x, float y) {
        this.translationX = x;
        this.translationY = y;
    }
    
    /**
     * Sets the rotation angle (in degrees) for the polygon.
     */
    public void setRotation(float angle) {
        this.rotation = angle;
    }
    
    /**
     * Increments the rotation by the specified angle (in degrees).
     */
    public void incrementRotation(float deltaAngle) {
        this.rotation += deltaAngle;
        if (this.rotation >= 360f) {
            this.rotation -= 360f;
        }
    }
    
    /**
     * Sets the scale for the polygon.
     */
    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
    
    /**
     * Prints the polygon's properties to the console.
     */
    public void print() {
        System.out.println("Polygon Properties:");
        System.out.println("Translation: (" + translationX + ", " + translationY + ")");
        System.out.println("Rotation: " + rotation + " degrees");
        System.out.println("Scale: (" + scaleX + ", " + scaleY + ")");
        System.out.print("Color: (");
        for (int i = 0; i < color.length; i++) {
            System.out.print(color[i] + (i < color.length - 1 ? ", " : ""));
        }
        System.out.println(")");
        System.out.println("Vertices:");
        for (int i = 0; i < vertexCount; i++) {
            System.out.println("Vertex " + i + ": (" + vertices[i * 3] + ", " 
                    + vertices[i * 3 + 1] + ", " + vertices[i * 3 + 2] + ")");
        }
    }
    
    /**
     * Draws the polygon. The method applies the current transformation 
     * (translation, rotation, and scaling) and renders the polygon.
     */
    public void draw() {
        glPushMatrix();
        // Apply transformations in the order: translate, rotate, scale
        glTranslatef(translationX, translationY, 0);
        glRotatef(rotation, 0, 0, 1);
        glScalef(scaleX, scaleY, 1);
        
        // Set the polygon color
        glColor4f(color[0], color[1], color[2], color[3]);
        
        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
        glDrawArrays(GL_POLYGON, 0, vertexCount);
        glDisableClientState(GL_VERTEX_ARRAY);
        
        glPopMatrix();
    }
}

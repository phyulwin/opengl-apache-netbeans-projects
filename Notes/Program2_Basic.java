/***************************************************************
 * file: Basic.java
 * author: Kelly L.
 * class: CS 4450.01 (S25-Regular) Computer Graphics
 *
 * assignment: program 1
 * date last modified: 2/10/2025
 *
 * purpose: This class sets up the LWJGL display, initializes 
 * OpenGL, creates a Polygon object, and continuously draws it.
 ***************************************************************/

package Program2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Basic {
    private static final int SCREEN_WIDTH = 640;
    private static final int SCREEN_HEIGHT = 480;
    private static final int FRAME_RATE = 60;
    private static final String WINDOW_TITLE = "Polygon Class Demo";

    // Define a polygon with 5 vertices (x, y, z) in window coordinates
    private float[] polygonVertices = {
        20, 100, 0,
        100, 300, 0,
        500, 50, 0,
        320, 10, 0,
        40, 40, 0
    };

    private Polygon polygon;

    public void start() {
        try {
            createWindow();
            initGL();
            // Create the polygon using the defined vertices
            polygon = new Polygon(polygonVertices);
            // Set initial transformation: translate to center, with no rotation or scaling change
            polygon.setTranslation(SCREEN_WIDTH / 2.0f, SCREEN_HEIGHT / 2.0f);
            polygon.setRotation(0.0f);
            polygon.setScale(1.0f, 1.0f);
            // Set a custom color (for example, a semi-transparent blue)
            polygon.setColor(0.0f, 0.0f, 1.0f, 0.8f);

            // Print polygon properties to the console
            polygon.print();

            renderLoop();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void createWindow() throws LWJGLException {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
        Display.setTitle(WINDOW_TITLE);
        Display.create();
    }

    private void initGL() {
        // Set the viewport to cover the entire window
        glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Set up the orthographic projection matrix using window coordinates
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 0, 1);

        // Set up the modelview matrix
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        // Set polygon mode to fill and clear color to dark gray
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
    }

    private void renderLoop() {
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Optionally, animate the polygon by incrementing its rotation
            polygon.incrementRotation(1.0f);

            // Draw the polygon
            polygon.draw();

            Display.update();
            Display.sync(FRAME_RATE);
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Basic().start();
    }
}

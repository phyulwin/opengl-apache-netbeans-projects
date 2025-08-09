package cs4450_demo;
// Defines the package for this class. Organizes code modules.

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
// Imports necessary LWJGL classes and static members from GL11 for OpenGL graphics.

public class Basic {
// Declares the class named Basic.

    public void start() {
    // Public method that serves as the entry point for initializing and starting the rendering process.
        try {
            createWindow();
            // Calls the createWindow method to set up the display.
            initGL();
            // Calls the initGL method to initialize OpenGL settings.
            render();
            // Calls the render method to start the rendering loop.
        } catch (Exception e) {
            e.printStackTrace();
            // Catches and prints any exceptions that occur during initialization or rendering.
        }
    }

    private void createWindow() throws Exception {
    // Private method to configure and create the program window.
        Display.setFullscreen(false);
        // Sets the display mode to windowed.
        Display.setDisplayMode(new DisplayMode(640, 480));
        // Sets the display mode dimensions to 640x480 pixels.
        Display.setTitle("Program 1");
        // Sets the title of the window.
        Display.create();
        // Creates the display with the previously set parameters.
    }

    private void initGL() {
    // Private method to initialize various OpenGL settings.
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Sets the clear color for the window (black in this case).
        glMatrixMode(GL_PROJECTION);
        // Sets the matrix mode to projection to deal with how objects are projected in our scene.
        glLoadIdentity();
        // Resets the projection matrix.
        glOrtho(0, 640, 0, 480, 1, -1);
        // Sets up a 2D orthographic projection matrix.
        glMatrixMode(GL_MODELVIEW);
        // Switches the matrix mode to model view to deal with object transformations.
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        // Sets the quality of color, texture coordinate, and other perspective calculations. The best quality possible.
    }

    private void render() {
    // Private method to handle the rendering loop.
        while (!Display.isCloseRequested()) {
        // Loop that continues until the display window is requested to close.
            try {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                // Clears the screen and depth buffer.
                glLoadIdentity();
                // Reset the model view matrix.
                glColor3f(1.0f, 1.0f, 0.0f);
                // Sets the current color to yellow.
                glPointSize(10);
                // Sets the size of points to be drawn.
                glBegin(GL_POINTS);
                // Begins drawing points.
                glVertex2f(350.0f, 150.0f);
                glVertex2f(50.0f, 50.0f);
                // Specifies two points to draw.
                glEnd();
                // Ends drawing.

                Display.update();
                // Updates the display with the newly drawn graphics.
                Display.sync(60);
                // Synchronizes the display to run at 60 frames per second.
            } catch (Exception e) {
                e.printStackTrace();
                // Prints any rendering exceptions.
            }
        }
        Display.destroy();
        // Destroys the display and cleans up resources once the loop exits.
    }

    public static void main(String[] args) {
        Basic basic = new Basic();
        // Creates an instance of Basic.
        basic.start();
        // Calls the start method to initialize and begin the rendering loop.
    }
}

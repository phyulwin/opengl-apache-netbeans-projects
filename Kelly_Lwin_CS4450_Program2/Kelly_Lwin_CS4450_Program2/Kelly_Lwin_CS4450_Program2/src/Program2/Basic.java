/***************************************************************
* file: Basic.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: Program 2
* date last modified: 3/3/2025
*
* purpose: This program creates an OpenGL window and 
* renders graphical primitives based on coordinate input.
* note: The code contains content assisted by AI.
****************************************************************/
package Program2;

import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Basic {
    // instance variables
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 480;
    
    // Calculate centered coordinate system
    private static final int POS_WINDOW_WIDTH_X = (WINDOW_WIDTH / 2);
    private static final int NEG_WINDOW_WIDTH_X = -1 * POS_WINDOW_WIDTH_X;
    
    private static final int POS_WINDOW_WIDTH_Y = (WINDOW_HEIGHT / 2);
    private static final int NEG_WINDOW_WIDTH_Y = -1 * POS_WINDOW_WIDTH_Y;
                
    private static final int FRAME_RATE = 60;
    private static final String WINDOW_TITLE = "Kelly Lwin - Program 2";

    private List<Polygon> polygons;

    // method: start
    // purpose: Sets up the graphics and begins rendering.
    public void start() {
        try {
            createWindow();
            initGL();
            // Read polygons from the file
            polygons = ReadCoordinates.readPolygons("src/coordinates.txt");
            renderLoop();
        } catch(LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    // method: createWindow
    // purpose: Creates and initializes an OpenGL window.
    private void createWindow() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
        Display.setTitle(WINDOW_TITLE);
        Display.create();
    }

    // method: initGL
    // purpose: Initializes OpenGL settings and sets the background color.
    private void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(NEG_WINDOW_WIDTH_X, POS_WINDOW_WIDTH_X, 
                NEG_WINDOW_WIDTH_Y, POS_WINDOW_WIDTH_Y, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    // method: render
    // purpose: Main rendering loop that draws graphics.
    private void renderLoop() {
        while(!Display.isCloseRequested()){
            glClear(GL_COLOR_BUFFER_BIT);
            
            // Draw each polygon
            if(polygons != null) {
                for(Polygon polygon : polygons) {
                    polygon.draw();
                }
            }
            
            Display.update();
            Display.sync(FRAME_RATE);
        }
        Display.destroy();
    }
    
    // method: main
    // purpose: Creates an instance of Basic and starts program execution.
    public static void main(String[] args) {
        new Basic().start();
    }
}

package Program2;

//import java.util.List;
//
//import org.lwjgl.*;
//
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.DisplayMode;
//import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Basic {

    /*private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 480;
    private static final int FRAME_RATE = 60;
    private static final String WINDOW_TITLE = "Kelly Lwin - Program 2";

    private List<Polygon> polygons;

    public void start() {
        try {
            createWindow();
            initGL();
            loadPolygons();
            render();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
        Display.setTitle(WINDOW_TITLE);
        Display.create();
    }

    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Set viewport to the window dimensions
        glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        // Use an orthographic projection matching the window dimensions
        glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, 0, 1);
//        glOrtho(-320, 320, -240, 240, 1, -1);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    private void render() {
        while (!Display.isCloseRequested()) {
            try {
                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    System.out.println("Exiting Program...");
                    break;
                }

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                for (Polygon polygon : polygons) {
//                    polygon.print();
                    polygon.draw();
                }

                Display.update();
                Display.sync(FRAME_RATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        Basic basic = new Basic();
        basic.start();
    } */
    private static final int SCREEN_WIDTH = 640;
    private static final int SCREEN_HEIGHT = 480;
    private static final int FRAME_RATE = 60;
    private static final String WINDOW_TITLE = "Hello World";

    // The polygon vertices (x, y, z)
    private float[] polygonVertices = {
        20, 100, 0,
        100, 300, 0,
        500, 50, 0,
        320, 10, 0,
        40, 40, 0
    };

    private ByteBuffer vertexByteBuffer;

    public void start() {
        try {
            createWindow();
            initGL();
            prepareVertexBuffer();
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
        // Set the viewport to the entire window
        glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Set up projection matrix
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 0, 1);

        // Set up modelview matrix
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        // Set polygon drawing mode to fill
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    private void prepareVertexBuffer() {
        // Each float is 4 bytes, so allocate a ByteBuffer big enough for all vertices
        vertexByteBuffer = BufferUtils.createByteBuffer(polygonVertices.length * 4);

        // Convert it to a FloatBuffer view, put the data, and flip
        FloatBuffer floatView = vertexByteBuffer.asFloatBuffer();
        floatView.put(polygonVertices);
        floatView.flip();  // prepares the float view for reading
    }

    private void renderLoop() {
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Enable vertex arrays and specify pointer to the vertex data
            glEnableClientState(GL_VERTEX_ARRAY);
            glVertexPointer(3, GL_FLOAT, 0, vertexByteBuffer); // Note: pass the ByteBuffer now
            glDrawArrays(GL_POLYGON, 0, 5);
            glDisableClientState(GL_VERTEX_ARRAY);

            Display.update();
            Display.sync(FRAME_RATE);
        }
        Display.destroy();
    }
    
    public static void main(String[] args) {
        Basic basic = new Basic();
        basic.start();
    }
}

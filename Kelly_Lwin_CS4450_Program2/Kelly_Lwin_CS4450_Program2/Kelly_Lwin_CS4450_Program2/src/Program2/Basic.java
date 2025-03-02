package Program2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import java.util.List;

public class Basic {
    private static final int SCREEN_WIDTH = 640;
    private static final int SCREEN_HEIGHT = 480;
    private static final int FRAME_RATE = 60;
    private static final String WINDOW_TITLE = "Scanline Polygon Fill Demo";

    private List<Polygon> polygons;

    public void start() {
        try {
            createWindow();
            initGL();
            // Read polygons once from the file.
            polygons = ReadCoordinates.readPolygons("src/coordinates.txt");
            renderLoop();
        } catch(LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void createWindow() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
        Display.setTitle(WINDOW_TITLE);
        Display.create();
    }

    private void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        // Set up a centered coordinate system: x from -320 to 320 and y from -240 to 240.
        glOrtho(-320, 320, -240, 240, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glClearColor(0, 0, 0, 1); // Black background.
    }

    private void renderLoop() {
        while(!Display.isCloseRequested()){
            glClear(GL_COLOR_BUFFER_BIT);
            
            // Draw each polygon.
            if(polygons != null) {
                for(Polygon poly : polygons) {
                    poly.draw();
                }
            }
            
            Display.update();
            Display.sync(FRAME_RATE);
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Basic().start();
    }
}

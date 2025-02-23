package Program2;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;

public class Basic {

    private static final int WINDOW_WIDTH = 640;
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
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-320, 320, -240, 240, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    private void loadPolygons() {
        polygons = ReadCoordinates.readPolygons("src/coordinates.txt");
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
                    polygon.print();
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
    }
}

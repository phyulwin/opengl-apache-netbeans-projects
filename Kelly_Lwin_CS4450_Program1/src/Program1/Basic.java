package Program1;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

import Program1.ReadCoordinates;

public class Basic {

    public void start() {
        try {
            createWindow();
            initGL();
            render();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        // draw a window of 640x480 in the center of the screen
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Kelly Lwin - Program 1");
        Display.create();
    }

    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    private void render() {
        while (!Display.isCloseRequested()) {
            try {
                // quit the application if the escape key is pressed
                if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    System.out.println("Exiting Program...");
                    break;
                }
                
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                glColor3f(1.0f, 1.0f, 0.0f);
                glPointSize(10);
                
                // read in coordinates from a file titled coordinates.txt
                ReadCoordinates.readFile("src\\Program1\\coordinates.txt");

                Display.update();
                Display.sync(60);
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

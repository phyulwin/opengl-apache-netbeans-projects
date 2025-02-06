package cs4450_demo;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Basic {

    public void start() {
        try {
            createWindow();
            initGL();
            render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 1");
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
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                glColor3f(1.0f, 1.0f, 0.0f);
                glPointSize(10);
                glBegin(GL_POINTS);
                glVertex2f(350.0f, 150.0f);
                glVertex2f(50.0f, 50.0f);
                glEnd();

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

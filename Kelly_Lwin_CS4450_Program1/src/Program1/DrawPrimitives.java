package Program1;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class DrawPrimitives {
    
    public static void drawLine(float startX, float startY, float endX, float endY) {
        glColor3f(1.0f, 0.0f, 0.0f);
        glBegin(GL_LINES);
        glVertex2f(startX, startY);
        glVertex2f(endX, endY);
        glEnd();
    }
    
    public static void drawCircle(float centerX, float centerY, float radius) {
        int numSegments = 100; // Higher number gives smoother circle
        float theta;
        
        glColor3f(0.0f, 0.0f, 1.0f);
        glBegin(GL_LINE_LOOP); // Use GL_LINE_LOOP to create the outline of a circle
        for (int i = 0; i < numSegments; i++) {
            theta = (float) (2.0f * Math.PI * i / numSegments);
            float x = (float) (radius * Math.cos(theta));
            float y = (float) (radius * Math.sin(theta));
            glVertex2f(centerX + x, centerY + y); // Offset by center coordinates
        }
        glEnd();
    }
    
    public static void drawEllipse(float centerX, float centerY, float radiusX, float radiusY) {
        int numSegments = 100; // Higher number gives smoother ellipse
        float theta;
        
        glColor3f(0.0f, 1.0f, 0.0f);
        glBegin(GL_LINE_LOOP); // Use GL_LINE_LOOP to create the outline of an ellipse
        for (int i = 0; i < numSegments; i++) {
            theta = (float) (2.0f * Math.PI * i / numSegments);
            float x = (float) (radiusX * Math.cos(theta));
            float y = (float) (radiusY * Math.sin(theta));
            glVertex2f(centerX + x, centerY + y); // Offset by center coordinates
        }
        glEnd();
    }
    
}

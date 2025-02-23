/***************************************************************
* file: Polygon.java
* author: Kelly L.
* class: CS 4450.01 (S25-Regular) Computer Graphics
*
* assignment: program 1
* date last modified: 2/10/2025
*
* purpose: This class represents a polygon object with color, 
* vertices, and transformations. It provides a method to 
* print polygon properties.
****************************************************************/

package Program2;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private float[] color;
    private List<float[]> vertices;
    private List<String> transformations;

    public Polygon(float r, float g, float b) {
        this.color = new float[]{r, g, b};
        this.vertices = new ArrayList<>();
        this.transformations = new ArrayList<>();
    }

    public void addVertex(float x, float y) {
        vertices.add(new float[]{x, y});
    }

    public void addTransformation(String transformation) {
        transformations.add(transformation);
    }

    public void print() {
        System.out.println("Polygon:");
        System.out.printf("Color: R=%.2f, G=%.2f, B=%.2f\n", color[0], color[1], color[2]);

        System.out.println("Vertices:");
        for (float[] vertex : vertices) {
            System.out.printf("(%.2f, %.2f)\n", vertex[0], vertex[1]);
        }

        System.out.println("Transformations:");
        for (String transformation : transformations) {
            System.out.println(transformation);
        }

        System.out.println("--------------------------------------------------");
    }
}

package entity;

import java.awt.*;

/**
 * The Drawable interface represents objects that can be drawn on a graphical context using the Graphics2D class.
 * Classes that implement this interface must provide an implementation for the draw method, which takes a Graphics2D
 * object as a parameter and defines how the object should be drawn.
 *
 * By using this interface, we establish a common contract for any class that wants to be drawable, enabling a
 * consistent way to render various types of objects on a graphical surface. This abstraction allows for flexibility
 * in the types of objects that can be drawn, promoting code reusability and modularity.
 */
public interface Drawable {
    /**
     * Draws the object on a graphical context using the provided Graphics2D object.
     *
     * @param g2 The Graphics2D object on which the object should be drawn.
     */
    void draw(Graphics2D g2);
}
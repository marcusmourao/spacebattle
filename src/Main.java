/**
 * Created by lucas on 02/01/17.
 */
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {
    public Main() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();

            while(!Display.isCloseRequested()) {
                Display.update();
            }

            Display.destroy();
        } catch(LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
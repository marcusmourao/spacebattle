/**
 * Created by lucas on 02/01/17.
 */
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.event.KeyEvent;


public class Main {
    public Main() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();

            while(!Display.isCloseRequested()) {
                Display.update();
                while (Keyboard.next()) {
                    // get event key here
                    if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                        System.out.println("Espaço");
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                        System.out.println("Esquerda");
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                        System.out.println("Direita");
                    }
                }
            }

            Display.destroy();
        } catch(LWJGLException e) {
            e.printStackTrace();
        }
    }

    public boolean isKeyPressed(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_SPACE:
                keyCode = Keyboard.KEY_SPACE;
                break;
            case KeyEvent.VK_LEFT:
                keyCode = Keyboard.KEY_LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                keyCode = Keyboard.KEY_RIGHT;
                break;
        }

        return Keyboard.isKeyDown(keyCode);
    }


    public static void main(String[] args) {
        new Main();
    }
}
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by lucas on 04/01/17.
 */
public class Menu extends BasicGameState {


    // ID we return to class 'Application'
    public static final int ID = 0;

    private Input input;

    // init-method for initializing all resources
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    // render-method for all the things happening on-screen
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
//        g.fillOval(75,100,100,100);
        g.drawString("click para jogar!",200,150);

    }

    // update-method with all the magic happening in it
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
        input = gc.getInput();
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();

        if(input.isMouseButtonDown(0)){
            sbg.enterState(1); //mudança de estado
        }
    }

    // Returning 'ID' from class 'MainMenu'
    @Override
    public int getID() {
        return Menu.ID;
    }

}

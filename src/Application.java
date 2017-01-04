import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by lucas on 04/01/17.
 */
public class Application extends StateBasedGame {

    // Game state identifiers
    public static final int MAINMENU     = 0;
    public static final int GAME         = 1;

    // Application Properties
    public static final int WIDTH   = 800;
    public static final int HEIGHT  = 600;
    public static final int FPS     = 60;

    // Class Constructor
    public Application(String appName) {
        super(appName);
    }

    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) throws SlickException {
        // The first state added will be the one that is loaded first, when the application is launched
        this.addState(new Menu());
        this.addState(new Game());
    }

    // Main Method
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Application("Space Invader"));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.start();

        } catch(SlickException e) {
            e.printStackTrace();
        }
    }
}

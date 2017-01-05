import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by lucas on 04/01/17.
 */
public class Menu extends BasicGameState {


    // ID we return to class 'Application'
    public static final int ID = 0;

    private Input input;

    private Image background;
    private Image playNow;
    private Image rules;
    private Image history;
    private Image next;
    private int step;

    private Music music;

    // init-method for initializing all resources
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("img/space.png");
        playNow = new Image("img/play.png");
        history = new Image("img/rules.png");
        rules = new Image("img/rules2.png");
        next = new Image("img/next.png");
        step = 2;

        music = new Music("music/03-stage-1.ogg");
        music.setVolume(0.2f);
        music.loop();
    }

    // render-method for all the things happening on-screen
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        background.draw(0,0);

       if(step == 2){
           playNow.draw(250,250);
       }else if(step == 1) {
           history.draw(0,0);
           next.draw(250,450);
       }else if(step == 0){
           rules.draw(0,0);
           next.draw(250,450);
       }

    }

    // update-method with all the magic happening in it
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
        input = gc.getInput();
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();

        if(input.isMousePressed(0)){
            if(step == 0){
                sbg.enterState(1); //mudança de estado
            }
            step--;
        }
    }

    // Returning 'ID' from class 'MainMenu'
    @Override
    public int getID() {
        return Menu.ID;
    }

}

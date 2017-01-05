import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by lucas on 04/01/17.
 */
public class Game extends BasicGameState {

    // ID we return to class 'Application'
    public static final int ID = 1;


    private int time=0;
    private Vector<Bala> municao = new Vector<>();
    private Vector<Asteroide> asteroide = new Vector<>();
    private Nave nave;

    private Image textureNave;
    private Image textureAsteroide;
    private Image textureBala;

    private Input input;

    // init-method for initializing all resources
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        textureNave = new Image("img/nave.png");
        textureAsteroide = new Image("img/asteroid.png");
        textureBala = new Image("img/bala.jpg");

        nave = new Nave(400 - (textureNave.getWidth()/2), 600 - 50 - textureNave.getHeight(), 500, 500, 500);
        asteroide.add(new Asteroide(400 - (textureAsteroide.getWidth()/2), 0, 50));


    }

    // render-method for all the things happening on-screen
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        textureNave.draw(nave.getX(),nave.getY());

        if(!asteroide.isEmpty()){
            for(int i=0; i<asteroide.size(); i++)
                textureAsteroide.draw(asteroide.get(i).getX(),asteroide.get(i).getY());
        }

        if(!municao.isEmpty()){
            for(int i=0; i<municao.size();i++)
                textureBala.draw(municao.get(i).getX(),municao.get(i).getY());
        }

    }

    // update-method with all the magic happening in it
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
        input = gc.getInput();

        //verifica se um tiro foi disparado
        if (input.isKeyDown(Keyboard.KEY_SPACE)) {
            System.out.println("Tiro");
            municao.add(new Bala( nave.getX(), 600 - textureNave.getHeight(), 50));
        }

        //muda a posicao da nave para a esquerda
        if (input.isKeyDown(Keyboard.KEY_LEFT)) {
            if (nave.getX() >= 5)
                nave.setX(nave.getX() - 5);
            //System.out.println("Esquerda");
        }
        //muda a posicao da nave para a direita
        if (input.isKeyDown(Keyboard.KEY_RIGHT)) {
            if (nave.getX() <= 795 - textureNave.getWidth())
                nave.setX(nave.getX() + 5);
            //System.out.println("Direita");
        }

        if(time % 5 ==0)
        {
            if(!asteroide.isEmpty()){
                for(int i=0; i< asteroide.size(); i++)
                    asteroide.get(i).setY(asteroide.get(i).getY()+1);
            }
            for(int i=0; i<municao.size();i++)
                municao.get(i).setY(municao.get(i).getY() - 10);
        }

        if(time % 200 == 0) {
            int randomNum = ThreadLocalRandom.current().nextInt(textureAsteroide.getWidth()/2, 800 + 1);
            asteroide.add(new Asteroide(randomNum - (textureAsteroide.getWidth()/2), 0, 50));
        }

        time ++;
    }

    // Returning 'ID' from class 'MainMenu'
    @Override
    public int getID() {
        return Game.ID;
    }


}

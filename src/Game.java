import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by lucas on 04/01/17.
 */
public class Game extends BasicGameState {

    // ID we return to class 'Application'
    public static final int ID = 1;


    private int time=0;
    private ArrayList<Bala> municao = new ArrayList<>();
    private ArrayList<Asteroide> asteroide = new ArrayList<>();
    private Nave nave;
    private int pontuacao;

    private Image textureNave;
    private Image textureAsteroide;
    private Image textureBala;

    private Input input;

    // init-method for initializing all resources
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        pontuacao = 0;

        textureNave = new Image("img/nave.png");
        textureAsteroide = new Image("img/asteroid.png");
        textureBala = new Image("img/bala.png");

        nave = new Nave(400 - (textureNave.getWidth()/2), 600 - 15 - textureNave.getHeight(), textureNave.getWidth(), textureNave.getHeight(), 5, 500, 500);
        asteroide.add(new Asteroide(400 - (textureAsteroide.getWidth()/2), 0, textureAsteroide.getWidth(), textureAsteroide.getHeight(), 50));
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

        g.drawString("Pontuacao: "+pontuacao,10,35);
        String vida = "Vida: ";
        for (int i = 0; i < nave.getVida(); i++)
            vida += "*";
        g.drawString(vida, 10, 60);

        // Verifica se o jogador morreu
        if (nave.getVida() <= 0) {
            g.drawString("Game Over", 350, 290);
        }
    }

    // update-method with all the magic happening in it
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
        input = gc.getInput();

        // Se a nave nao possuir mais vida, bloqueia os movimentos do usuario
        if (nave.getVida() > 0) {
            //verifica se um tiro foi disparado
            if (input.isKeyPressed(Keyboard.KEY_SPACE)) {
                //System.out.println("Tiro");
                municao.add(new Bala(nave.getX() + (nave.getLargura() / 5), 600 - nave.getAltura() - 15, textureBala.getWidth(), textureBala.getHeight()));
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
        }

        // Definicao da velocidade de criacao dos asteroides
        int velocidadeAsteroide;
        if (pontuacao <= 20)
            velocidadeAsteroide = 1;
        else if (pontuacao <= 50)
            velocidadeAsteroide = 2;
        else if (pontuacao <= 90)
            velocidadeAsteroide = 3;
        else
            velocidadeAsteroide = 5;

        if(time % 5 == 0)
        {
            // Atualiza  posicao dos asteroides
            if(!asteroide.isEmpty()){
                for(int i=0; i< asteroide.size(); i++)
                    asteroide.get(i).setY(asteroide.get(i).getY()+velocidadeAsteroide);
            }

            // Verifica se alguma bala colidiu com asteroide
            for(int i=0; i<municao.size();i++) {
                municao.get(i).setY(municao.get(i).getY() - 40);
                for (int j = 0; j < asteroide.size(); j++)
                    if (municao.get(i).colideCom(asteroide.get(j))) {
                        asteroide.remove(j);
                        //System.out.println("Abateu");
                        municao.remove(i);
                        pontuacao++;
                        break;
                    }
            }

            // Verifica se algum asteroide colidiu com a nave
            for (int i = 0; i < asteroide.size(); i++) {
                if (asteroide.get(i).colideCom(nave)) {
                    nave.diminuiVida();
                    asteroide.remove(i);
                    break;
                }
            }
        }

        // Aumenta a velocidade da criacao de novos asteroides
        // Quanto menor o valor, maior a velocidade
        int velocidadeCriacaoAsteroides;
        if (pontuacao <= 20)
            velocidadeCriacaoAsteroides = 150;
        else if (pontuacao <= 50)
            velocidadeCriacaoAsteroides = 100;
        else if (pontuacao <= 90)
            velocidadeCriacaoAsteroides = 60;
        else
            velocidadeCriacaoAsteroides = 40;

        // Criacao de novos asteroides
        if(time % velocidadeCriacaoAsteroides == 0) {
            int randomNum = ThreadLocalRandom.current().nextInt(textureAsteroide.getWidth()/2, 800 + 1);
            asteroide.add(new Asteroide(randomNum - (textureAsteroide.getWidth()/2), 0, textureAsteroide.getWidth(), textureAsteroide.getHeight(), 50));
        }

        time ++;
    }

    // Returning 'ID' from class 'MainMenu'
    @Override
    public int getID() {
        return Game.ID;
    }


}

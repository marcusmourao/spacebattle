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
    private Image background;

    private Music music;

    private Input input;
    private boolean collision_nave;
    private int timeout_nave;
    private boolean collision_asteroid;

    private Sound shoot;
    private Sound explode;
    private Sound explode_nave;

    // init-method for initializing all resources
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        pontuacao = 0;

        shoot = new Sound("music/shoot.wav");
        explode = new Sound("music/explosion.wav");
        explode_nave = new Sound("music/invaderkilled.wav");

        textureNave = new Image("img/nave.png");
        textureAsteroide = new Image("img/asteroid.png");
        textureBala = new Image("img/bala.png");
        background = new Image("img/space.png");

        nave = new Nave(400 - (textureNave.getWidth()/2), 600 - 15 - textureNave.getHeight(), textureNave.getWidth(), textureNave.getHeight(), 5, 500, 500);
        asteroide.add(new Asteroide(400 - (textureAsteroide.getWidth()/2), 0, textureAsteroide.getWidth(), textureAsteroide.getHeight(), 50));
    }

    // render-method for all the things happening on-screen
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        background.draw(0,0);

        if(!municao.isEmpty()){
            for(int i=0; i<municao.size();i++)
                textureBala.draw(municao.get(i).getX(),municao.get(i).getY());
        }

        g.drawString("Pontuacao: "+pontuacao,10,10);
        String vida = "Vida: ";
        for (int i = 0; i < nave.getVida(); i++)
            vida += "*";
        g.drawString(vida, 10, 35);

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
                municao.add(new Bala(nave.getX() + (nave.getLargura() / 5), 600 - nave.getAltura() - 15, textureBala.getWidth(), textureBala.getHeight()));
                shoot.play();
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
        int velocidadeAsteroide = 7;
        if (pontuacao <= 20)
            velocidadeAsteroide += 2 ;
        else if (pontuacao <= 50)
            velocidadeAsteroide += 2 ;
        else if (pontuacao <= 90)
            velocidadeAsteroide += 2 ;
        else
            velocidadeAsteroide += 2 ;

        if(time % 5 == 0)
        {
            // Atualiza  posicao dos asteroides
            if(!asteroide.isEmpty()){
                for(int i=0; i< asteroide.size(); i++)
                    asteroide.get(i).setY(asteroide.get(i).getY()+velocidadeAsteroide);
            }

            // Verifica se alguma bala colidiu com asteroide
            for(int i=0; i<municao.size();i++) {
                //atualiza a posição da bala
                municao.get(i).setY(municao.get(i).getY() - 40);
                collision_asteroid = false;
                for (int j = 0; j < asteroide.size(); j++)
                    if (municao.get(i).colideCom(asteroide.get(j))) {
                        asteroide.remove(j);
                        municao.remove(i);
                        pontuacao++;

                        collision_asteroid = true;
                        explode.play();
                        break;
                    }

                    //remove a munição quando ela sair na tela
                    if(!collision_asteroid && municao.get(i).getY() < 0){
                        municao.remove(i);
                }
            }

            // Verifica se algum asteroide colidiu com a nave
            for (int i = 0; i < asteroide.size(); i++) {
                if (asteroide.get(i).colideCom(nave)) {
                    nave.diminuiVida();
                    asteroide.remove(i);
                    explode_nave.play();
                    //verificação necessário para o caso da nave colidir duas vezes seguidas
                    if(!collision_nave) {
                        collision_nave = true;
                        timeout_nave = 60;
                    }
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

        //efeito para piscar a nave quando colide com um asteroid
        if(timeout_nave > 0){
            timeout_nave--;
        }

        if(timeout_nave % 10 == 0){
            if(timeout_nave == 0){
                collision_nave = false;
            }else
                collision_nave = !collision_nave;
        }

        time ++;
    }

    // Returning 'ID' from class 'MainMenu'
    @Override
    public int getID() {
        return Game.ID;
    }


}

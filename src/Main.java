/**
 * Created by lucas on 02/01/17.
 */
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.input.Keyboard;


public class Main {

    // Game state identifiers
    public static final int SPLASHSCREEN = 0;
    public static final int MAINMENU     = 1;
    public static final int GAME         = 2;

    // Application Properties
    public static final int WIDTH   = 800;
    public static final int HEIGHT  = 600;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;


    int time=0;
    Vector<Bala> municao = new Vector<>();
    Vector<Asteroide> asteroide = new Vector<>();
    Nave nave;

    Texture textureNave;
    Texture textureAsteroide;
    Texture textureBala;
    Texture textureBackground;


    public void render(Texture texture, int positionX, int positionY) throws IOException {

        Color.white.bind();
        texture.bind(); // or GL11.glBind(texture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0,0);
        //GL11.glVertex2f(100,100);
        GL11.glVertex2f(positionX,positionY);
        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(positionX+texture.getTextureWidth(),positionY);
        //GL11.glVertex2f(100+texture.getTextureWidth(),100);
        GL11.glTexCoord2f(1,1);
        //GL11.glVertex2f(100+texture.getTextureWidth(),100+texture.getTextureHeight());
        GL11.glVertex2f(positionX+texture.getTextureWidth(),positionY+texture.getTextureHeight());
        GL11.glTexCoord2f(0,1);
        //GL11.glVertex2f(100,100+texture.getTextureHeight());
        GL11.glVertex2f(positionX,positionY+texture.getTextureHeight());
        GL11.glEnd();
    }


    public Main() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();

            GL11.glEnable(GL11.GL_TEXTURE_2D);

            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

            // enable alpha blending
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glViewport(0,0,800,600);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, 800, 600, 0, 1, -1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            try {
                textureBackground = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("img/space.png"));
                textureNave = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("img/nave.png"));
                textureAsteroide = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("img/asteroid.png"));
                textureBala = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("img/bala.jpg"));

            } catch (IOException e) {
                System.err.println(e.getStackTrace());
            }

            nave = new Nave(400 - (textureNave.getImageWidth()/2), 600 - 50 - textureNave.getImageHeight(), 500, 500, 500);
            asteroide.add(new Asteroide(400 - (textureAsteroide.getImageWidth()/2), 0, 50));


            while(!Display.isCloseRequested()) {
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                render(textureBackground,0,0);
               // render(textureNave, nave.getX(), nave.getY());
//
//                if(!asteroide.isEmpty()){
//                    for(int i=0; i<asteroide.size(); i++)
//                           render(textureAsteroide, asteroide.get(i).getX(), asteroide.get(i).getY());
//
//                }
//
//                if(!municao.isEmpty()){
//                    for(int i=0; i<municao.size();i++)
//                        render(textureBala, municao.get(i).getX(), municao.get(i).getY());
//                }

                Display.update();
                while (Keyboard.next()){
                    // get event key here
                    if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                        System.out.println("PEW");
                        municao.add(new Bala( nave.getX(), 600 -textureNave.getImageHeight(), 50));
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                        if (nave.getX() >= 5)
                            nave.setX(nave.getX() - 5);
                        //System.out.println("Esquerda");
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                        if (nave.getX() <= 795 - textureNave.getImageWidth())
                            nave.setX(nave.getX() + 5);
                        //System.out.println("Direita");
                    }
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
                        int randomNum = ThreadLocalRandom.current().nextInt(textureAsteroide.getImageWidth()/2, 800 + 1);
                        asteroide.add(new Asteroide(randomNum - (textureAsteroide.getImageWidth()/2), 0, 50));
                }

                time ++;
            }

            Display.destroy();
        } catch(LWJGLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
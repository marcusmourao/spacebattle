import java.awt.*;

/**
 * Created by marcus on 02/01/17.
 */
public class Objeto {
    private int x;
    private int y;
    private int largura;
    private int altura;

    public Objeto(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.altura = altura;
        this.largura = largura;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public int[] getPosition(){
        int[] position = new int[2];
        position[0] = getX();
        position[1] = getY();
        return position;

    }

    public void moveCima(){
        this.y = this.y -1;

    }

    public void moveBaixo(){
        this.y = this.y +1;

    }

    public void moveDireita(){
        this.y = this.x +1;

    }

    public void moveEsquerda(){
        this.y = this.x -1;

    }

    public boolean colideCom(Objeto o) {
        Rectangle atual, outro;
        atual = new Rectangle(x, y, largura, altura);
        outro = new Rectangle(o.getX(), o.getY(), o.getLargura(), o.getAltura());

        return atual.intersects(outro);
    }
}

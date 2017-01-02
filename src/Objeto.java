/**
 * Created by marcus on 02/01/17.
 */
public class Objeto {
    private int x;
    private int y;

    public Objeto(int x, int y) {
        this.x = x;
        this.y = y;
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
}

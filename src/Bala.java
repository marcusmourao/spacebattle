/**
 * Created by marcus on 03/01/17.
 */
public class Bala extends Objeto {
    int vida;

    public Bala(int x, int y, int vida) {
        super(x, y);
        this.vida = vida;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
}

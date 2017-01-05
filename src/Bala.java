/**
 * Created by marcus on 03/01/17.
 */
public class Bala extends Objeto {
    int vida;

    public Bala(int x, int y, int largura, int altura, int vida) {
        super(x, y, largura, altura);
        this.vida = vida;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
}

/**
 * Created by marcus on 02/01/17.
 */
public class Asteroide extends Objeto {
    private int vida;

    public Asteroide(int x, int y, int largura, int altura, int vida) {
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

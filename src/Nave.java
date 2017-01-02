/**
 * Created by marcus on 02/01/17.
 */
public class Nave extends Objeto {

    private int vida;
    private int municao;
    private int combustivel;

    public Nave(int x, int y, int vida, int municao, int combustivel) {
        super(x, y);
        this.vida = vida;
        this.municao = municao;
        this.combustivel = combustivel;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getMunicao() {
        return municao;
    }

    public void setMunicao(int municao) {
        this.municao = municao;
    }

    public int getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }
}

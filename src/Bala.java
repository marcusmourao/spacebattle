import java.awt.*;

/**
 * Created by marcus on 03/01/17.
 */
public class Bala extends Objeto {
    public Bala(int x, int y, int largura, int altura) {
        super(x, y, largura, altura);
    }

    public boolean colideCom(Objeto o) {
        Rectangle atual, outro;
        // Como a bala tem lados transparentes, colide apenas se a parte mais central da mesma
        // estiver em contato com o outro objeto
        atual = new Rectangle(this.getX()+10, this.getY()+10, this.getLargura()-10, this.getAltura()-10);
        outro = new Rectangle(o.getX(), o.getY(), o.getLargura(), o.getAltura());

        return atual.intersects(outro);
    }
}

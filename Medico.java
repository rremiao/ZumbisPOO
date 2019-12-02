import java.util.ArrayList;
import java.util.List;

public class Medico extends Personagem{
    public int hp;
    public int movimento;
    public int dano;
    public int cura;
    public int range;

    public Medico(String imagemInicial,int linInicial,int colInicial){
        super(imagemInicial, linInicial, colInicial);
        this.hp = 4;
        this.movimento = 4;
        this.dano = 3;
        this.cura = 4;
        this.range = 3;
    }

    public void ataca(Zumbi alvo){
        alvo.hp = alvo.hp - this.dano;
    }

    

    public void recebeAtaque(int danoP){
        this.hp = this.hp - danoP;
    }


    public void testaAtaque(List<Personagem> zumbis){
        List<Personagem> alvo = (List)zumbis 
                                .stream()
                                .filter(p -> p instanceof Zumbi ||  p instanceof ZumbiNinja || p instanceof ZumbiT800)
                                .filter(p -> this.verificaRange((Zumbi)p));
        alvo.forEach(p -> p.recebeAtaque(this.dano));
                                
    }


    public int getHp(){
        testaCura();
        return this.hp;
    }

    public int getRange(){
        return this.range;
    }

    public void cura(){
        desinfecta();
        this.cura -= 1;
        this.hp += 4;
    }

    public void testaCura(){
        if(this.hp < 2){
            this.cura();
        }
    }

       
    @Override
    public void atualizaPosicao() {
        getHp();
        testaAtaque(Jogo.getInstance().getPersonagens());
        int dirLin = Jogo.getInstance().aleatorio(movimento)-1;
        int dirCol = Jogo.getInstance().aleatorio(movimento)-1;
        int oldLin = this.getCelula().getLinha();
        int oldCol = this.getCelula().getColuna();
        int lin = oldLin + dirLin;
        int col = oldCol + dirCol;
        if (lin < 0) lin = 0;
        if (lin >= Jogo.NLIN) lin = Jogo.NLIN-1;
        if (col < 0) col = 0;
        if (col >= Jogo.NCOL) col = Jogo.NCOL-1;
        if (Jogo.getInstance().getCelula(lin, col).getPersonagem() != null){
            return;
        }else{
            // Limpa celula atual
            Jogo.getInstance().getCelula(oldLin, oldCol).setPersonagem(null);
            // Coloca personagem na nova posição
            Jogo.getInstance().getCelula(lin, col).setPersonagem(this);
        }
    }

    @Override
    public void influenciaVizinhos() {

    }

    @Override
    public boolean verificaEstado() {
        return true;
    }

    public boolean verificaRange(Personagem alvo){
        if(alvo.getCelula().getLinha() > this.getCelula().getLinha()-range && alvo.getCelula().getLinha() < this.getCelula().getLinha()+range)
            if(alvo.getCelula().getColuna() > this.getCelula().getColuna()-range && alvo.getCelula().getColuna() < this.getCelula().getColuna()+range)
                return true;
        return false;
    }

}
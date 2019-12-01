public class Nomade extends Personagem{
    private int hp;
    private int movimento;
    private int dano;
    private int cura;
    private int range;

    public Nomade(int energiaInicial, String imagemInicial,int linInicial,int colInicial){
        super(energiaInicial, imagemInicial, linInicial, colInicial);
        this.hp = 5;
        this.movimento = 5;
        this.dano = 4;
        this.cura = 3;
        this.range = 4;
    }

    public void ataca(Zumbi alvo){
        alvo.hp = alvo.hp - this.dano;
    }

    public void testaAtaque(List<Personagem> zumbis){
        if(p-> p instanceof Zumbi : zumbis){
            if(this.getCelula() <= p.getCelula()){
            this.ataca();
        }    
                       
    }

    public void recebeAtaque(Zumbi atacante){
        this.hp = this.hp - atacante.dano;
    }

    public int getHp(){
        return this.hp;
    }

    public int getRange(){
        return this.range;
    }

    public void cura(){
        desinfecta();
        this.cura -= 1;
        this.hp += 2;
    }

      
    @Override
    public void atualizaPosicao() {
        testaAtaque();
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

}
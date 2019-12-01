public class ZumbiNinja extends Zumbi {
    private Personagem alvo;
    private int hp;
    private int movimento;
    private int dano;
    private int range;


    public ZumbiNinja(int linInicial,int colInicial){
        super(10,"Ninja",linInicial,colInicial);
        alvo = null;
        this.hp = 3;
        this.movimento = 4;
        this.dano = 3;
        this.range = 1;
    }

    private Personagem defineAlvo(){
        System.out.println("Procurando alvo");
        for(int l=0;l<Jogo.NLIN;l++){
            for(int c=0;c<Jogo.NCOL;c++){
                Personagem p = Jogo.getInstance().getCelula(l, c).getPersonagem();
                if (p != null && p instanceof Personagem && !p.infectado()){
                    alvo = p;
                    System.out.println("Alvo definido: "+alvo.getImage());
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public void atualizaPosicao() {
        if (alvo == null || alvo.infectado()){
            alvo = defineAlvo();
            return;
        }

        // Pega posicao atual do ZumbiEsperto
        int oldLin = this.getCelula().getLinha();
        int oldCol = this.getCelula().getColuna();

        // Pega a posicao do alvo
        int linAlvo = alvo.getCelula().getLinha();
        int colAlvo = alvo.getCelula().getColuna();

        // Calcula o deslocamento
        int lin = oldLin;
        int col = oldCol;
        if (lin < linAlvo) lin++;
        if (lin > linAlvo) lin--;
        if (col < colAlvo) col++;
        if (col > colAlvo) col--;

        // Verifica se não saiu dos limites do tabuleiro
        if (lin < 0) lin = 0;
        if (lin >= Jogo.NLIN) lin = Jogo.NLIN-1;
        if (col < 0) col = 0;
        if (col >= Jogo.NCOL) col = Jogo.NCOL-1;

        // Verifica se não quer ir para uma celula ocupada
        if (Jogo.getInstance().getCelula(lin, col).getPersonagem() != null){
            return;
        }else{
            // Limpa celula atual
            Jogo.getInstance().getCelula(oldLin, oldCol).setPersonagem(null);
            // Coloca personagem na nova posição
            Jogo.getInstance().getCelula(lin, col).setPersonagem(this);
        }
        testaAtaque(Jogo.getInstance().getPersonagem());
    }

    public void testaAtaque(List<Personagem> param){
        List<Personagem> alvo = param
                                .stream()
                                .filter(p-> p instanceof Medico || p-> p instanceof Caipira || p-> p instanceof Engenheiro || p-> p instanceof Nomade)
                                .filter(p-> (p.getCelula() - this.getCelula()) <= 0)
                                .map(this.ataca(p))
                                .collect(Collectors.toList());  
    }

    public void ataca(Personagem alvo){
        alvo.hp = alvo.hp - this.dano;
    }

     public void recebeAtaque(Personagem param){
        this.hp = this.hp - param.dano;
    }

     public int getHp(){
        return this.hp;
    }


    @Override
    public void influenciaVizinhos() {
        int lin = this.getCelula().getLinha();
        int col = this.getCelula().getColuna();
        for(int l=lin-1;l<=lin+1;l++){
            for(int c=col-1;c<=col+1;c++){
                // Se a posição é dentro do tabuleiro
                if (l>=0 && l<Jogo.NLIN && c>=0 && c<Jogo.NCOL){
                    // Se não é a propria celula
                    if (!( lin == l && col == c)){
                        // Recupera o personagem da célula vizinha
                        Personagem p = Jogo.getInstance().getCelula(l,c).getPersonagem();
                        // Se não for nulo, infecta
                        if (p != null){
                            p.infecta();
                        }
                    }
                }
            }
        }
        
    }

    @Override
    public void verificaEstado() {
        // Como não sofre influencia de ninguém, o estado nunca muda
    }
}
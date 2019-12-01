public class ZumbiUnderground extends Zumbi {
    private Personagem alvo;
    private int hp;
    private int movimento;
    private int dano;
    private int range;


    public ZumbiUnderground(int linInicial,int colInicial){
        super(10,"Underground",linInicial,colInicial);
        alvo = null;
        this.hp = 1;
        this.movimento = 0;
        this.dano = 1;
        this.range = 0;
    }

    //Alterei o metodo de atacar pois ele só pode atacar quando suas posições se sobrepoem
    //Apaguei os metodos restantes pois esse zumbi apenas ataca, mas nao se move ou recebe ataques;
    public void testaAtaque(List<Personagem> zumbis){
        if(p-> p instanceof Personagem : zumbis){
            if(this.getCelula() <= p.getCelula()){
            this.ataca();
        }    
                       
    }

    public void ataca(Personagem alvo){
        alvo.hp = alvo.hp - this.dano;
    }


    @Override
    public void verificaEstado() {
        // Como não sofre influencia de ninguém, o estado nunca muda
    }
    @Override
    public void atualizaPosicao() {
        testaAtaque();
    
    }
}
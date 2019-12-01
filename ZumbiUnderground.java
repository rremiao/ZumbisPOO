public class ZumbiUnderground extends Zumbi {
    public int hp;
    public int movimento;
    public Personagem alvo;
    public int dano;
    public int cura;
    public int range;


    public ZumbiUnderground(String imagemInicial, int linInicial,int colInicial){
        super("Underground",linInicial,colInicial);
        alvo = null;
        this.hp = 1;
        this.movimento = 0;
        this.dano = 1;
        this.range = 0;
    }

    //Alterei o metodo de atacar pois ele só pode atacar quando suas posições se sobrepoem
    //Apaguei os metodos restantes pois esse zumbi apenas ataca, mas nao se move ou recebe ataques;
    public void testaAtaque(List<Personagem> param){
        List<Personagem> alvo = param
                                .stream()
                                .filter(p-> p instanceof Medico || p instanceof Caipira || p instanceof Engenheiro || p instanceof Nomade)
                                .filter(p-> (p.getCelula() - this.getCelula()) <= 0);
        alvo.forEach(p->this.ataca(p));

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
        testaAtaque(Jogo.getInstance().getPersonagem());
    
    }
}
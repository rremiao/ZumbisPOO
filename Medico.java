public class Medico extends Personagem{
    private int hp;
    private int movimento;
    private int dano;
    //3 curas

    public Medico(int energiaInicial, String imagemInicial,int linInicial,int colInicial){
        super(energiaInicial, imagemInicial, linInicial, colInicial)
        this.hp = 4;
        this.movimento = 4;
        this.dano = 0;//bisturi
    }

    public int ataca(){
        return this.dano;
    }

    public void recebeAtaque(int danoRecebido){
        this.hp = this.hp - danoRecebido;
    }

    public void equipaArma(int arma){
        this.dano = arma;
    }

    public void equipaArmadura(int armadura){
        this.hp = this.hp + armadura;
    }

    @override
    public void cura(int cura){
        super.infectado = false;
        this.hp = this.hp + cura;
    }

    @override
    public abstract int atualizaPosicao(){
        return movimento;
    }

}
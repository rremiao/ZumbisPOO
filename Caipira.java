public class Caipira extends Personagem{
    private int hp;
    private int movimento;
    private int dano;
    private int cura;
    private int range;

    public Caipira(int energiaInicial, String imagemInicial,int linInicial,int colInicial){
        super(energiaInicial, imagemInicial, linInicial, colInicial)
        this.hp = 4;
        this.movimento = 3;
        this.dano = 5;
        this.cura = 2;
        this.range = 5;
    }

    public int ataca(){
        return this.dano;
    }

    public void recebeAtaque(int danoRecebido){
        this.hp = this.hp - danoRecebido;
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

    @override
    public int atualizaPosicao(){
        return movimento;
    }

}
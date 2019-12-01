public class Bobao extends Personagem {
    public Bobao(int linInicial, int colInicial) {
        super(10, "Normal", linInicial, colInicial);
    }

    @Override
    public void infecta(){
        if (this.infectado()){
            return;
        }
        super.infecta();
        this.setImage("Infectado");
        this.getCelula().setImageFromPersonagem();   
    }

    public void cura(){
        this.setImage("Normal");
        this.getCelula().setImageFromPersonagem();   
    }

    @Override
    public void atualizaPosicao() {
        // Não se mexe
    }

    @Override
    public void influenciaVizinhos() {
        // Não influencia ninguém
    }

}
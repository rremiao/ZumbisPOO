
//Felipe  

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

    @Override
    public void cura(){
        super.cura();
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

    @Override
    public void verificaEstado() {
        // Se esta morto retorna
        if (!this.estaVivo()){
            return;
        }
        // Se esta infectado perde energia a cada passo
        if (this.infectado()) {
            diminuiEnergia(2);
            // Se não tem mais energia morre
            if (this.getEnergia() == 0) {
                this.setImage("Morto");
                this.getCelula().setImageFromPersonagem();
            }
        }
    }
}
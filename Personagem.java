public abstract class Personagem {
    private String imagem; // Identificador da imagem
    private boolean infectado;
    private Celula celula;
    public int hp;
    public int movimento;
    public int dano;
    public int cura;
    public int range;

    public Personagem( String imagemInicial,int linInicial,int colInicial){
        this.imagem = imagemInicial;
        Jogo.getInstance().getCelula(linInicial, colInicial).setPersonagem(this);
        this.infectado = false;
    }

    public boolean infectado(){
        return infectado;
    }

    public void desinfecta(){
        infectado = false;
    }

    public void infecta(){
        infectado = true;
    }

    public String getImage(){
        return imagem;
    }

    public void setImage(String imagem){
        this.imagem = imagem;
    }

    public Celula getCelula(){
        return celula;
    }

    public void setCelula(Celula celula){
        this.celula = celula;
    }

    // Define próximo movimento
    public abstract void atualizaPosicao();

    // Verifica possiveis atualizações de estado a cada passo
    public abstract boolean verificaEstado();

    public abstract void recebeAtaque(int danoP);

    // Define como o personagem influencia os vizinhos
    // Toda vez que chega em uma célula analisa os vizinhos
    // e influencia os mesmos
    public abstract void influenciaVizinhos();
}

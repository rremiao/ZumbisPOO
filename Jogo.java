
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Jogo extends Application {
    public static final int CELL_WIDTH = 20;
    public static final int CELL_HEIGHT = 20;
    public static final int NLIN = 10;
    public static final int NCOL = 10;
    public static final int QTDADEBOBOES = 4;
    public static final int QTDADEZUMBIS = 2;

    public static Jogo jogo = null;

    private Random random;
    private Map<String, Image> imagens;
    private List<Celula> celulas;
    private List<Personagem> personagens;

    public static Jogo getInstance(){
        return jogo;
    }

    public Jogo(){
        jogo = this;
        random = new Random();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Retorna um número aleatorio a partir do gerador unico
    public int aleatorio(int limite){
        return random.nextInt(limite);
    }

    // Retorna a celula de uma certa linha,coluna
    public Celula getCelula(int nLin,int nCol){
        int pos = (nLin*NCOL)+nCol;
        return celulas.get(pos);
    }

    public List<Personagem> getPersonagens(){
        return personagens;
    }

    private void loadImagens() {
        imagens = new HashMap<>();

        // Armazena as imagens dos personagens
        Image aux = new Image("file:Imagens\\nomade.jpg");
        imagens.put("Nomade", aux);
        aux = new Image("file:Imagens\\engenheiro.jpg");
        imagens.put("Engenheiro", aux);
        aux = new Image("file:Imagens\\medico.jpg");
        imagens.put("Medico", aux);
        aux = new Image("file:Imagens\\caipira.jpg");
        imagens.put("Caipira", aux);
        aux = new Image("file:Imagens\\infectado.png");
        imagens.put("Infectado", aux);
        aux = new Image("file:Imagens\\homerzombie.png");
        imagens.put("Zumbi", aux);
        aux = new Image("file:Imagens\\morto.jpg");
        imagens.put("Morto", aux);
        aux = new Image("file:Imagens\\ninjazombie.png");
        imagens.put("Ninja", aux);
        aux = new Image("file:Imagens\\back.jpg");
        imagens.put("Vazio", aux);
        aux = new Image("file:Imagens\\t800zombie.png");
        imagens.put("T800", aux);
        aux = new Image("file:Imagens\\undergroundzombie.png");
        imagens.put("Underground", aux);

        // Armazena a imagem da celula ula
        imagens.put("Null", null);
    }

    public Image getImage(String id){
        return imagens.get(id);
    }

    @Override
    public void start(Stage primaryStage) {
        // Carrega imagens
        loadImagens();

        // Configura a interface com o usuario
        primaryStage.setTitle("Simulador de Zumbis");
        GridPane tab = new GridPane();
        tab.setAlignment(Pos.CENTER);
        tab.setHgap(10);
        tab.setVgap(10);
        tab.setPadding(new Insets(25, 25, 25, 25));

        // Monta o "tabuleiro"
        celulas = new ArrayList<>(NLIN*NCOL);
        for (int lin = 0; lin < NLIN; lin++) {
            for (int col = 0; col < NCOL; col++) {
                Celula cel = new Celula(lin,col);
                //cel.setOnAction(e->cliqueNaCelula(e));
                celulas.add(cel);
                tab.add(cel, col, lin);
            }
        }

        // Cria a lista de personagens
        personagens = new ArrayList<>(NLIN*NCOL);
        personagens.add(new ZumbiNinja(6,6));
        personagens.add(new Medico("Medico",3,2));
        personagens.add(new Bobao(4,0));
        personagens.add(new Engenheiro("Engenheiro",5,6));
        personagens.add(new Nomade("Nomade",4,4));
        personagens.add(new Caipira("Caipira",2,5));
        personagens.add(new ZumbiNinja(1,1));
        personagens.add(new ZumbiT800(5,5));
        
        
        // Cria 10 boboes aleatorios
        for(int i=0;i<QTDADEBOBOES;i++){
            // Lembrte: quando um personagem é criado ele se vincula
            // automaticamente na célula indicada nos parametros
            // linha e coluna (ver o construtor de Personagem)
            boolean posOk = false;
            while(!posOk){
                int lin = random.nextInt(NLIN);
                int col = random.nextInt(NCOL);
                if (this.getCelula(lin, col).getPersonagem() == null){
                    personagens.add(new Bobao(lin,col));
                    posOk = true;
                }
            }
        }
        
        // Cria 5 Zumbis aleatórios
        for(int i=0;i<QTDADEZUMBIS;i++){
            boolean posOk = false;
            while(!posOk){
                int lin = random.nextInt(NLIN);
                int col = random.nextInt(NCOL);
                if (this.getCelula(lin, col).getPersonagem() == null){
                    personagens.add(new Zumbi(lin,col));
                    posOk = true;
                }
            }
        }

        // Define o botao que avança a simulação
        Button avanca = new Button("Próximo turno");
        avanca.setOnAction(e->avancaSimulacao());
        // Define outros botoes
        
        // Monta a cena e exibe
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(25, 25, 25, 25));
        hb.getChildren().add(tab);
        hb.getChildren().add(avanca);      
        
        Scene scene = new Scene(hb);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void avancaSimulacao(){
        // Avança um passo em todos os personagens
        personagens.forEach(p->{
            p.atualizaPosicao();
            p.verificaEstado();
            p.influenciaVizinhos();
        });
        // Verifica se o jogo acabou
        long vivos = personagens
                    .stream()
                    .filter(p->!(p instanceof Zumbi))
                    .filter(p->!(p instanceof ZumbiNinja))
                    .filter(p->!(p instanceof ZumbiT800))
                    .filter(p->!(p instanceof ZumbiUnderground))
                    .filter(p->p.verificaEstado())
                    .count();
        if (vivos == 0){
            Alert msgBox = new Alert(AlertType.INFORMATION);
            msgBox.setHeaderText("Fim de Jogo");
            msgBox.setContentText("Todos os personagens morreram!");
            msgBox.showAndWait();
            System.exit(0);
        }
    }


    /*
    public void cliqueNaCelula(ActionEvent e){
        Celula c = (Celula)e.getSource();
        System.out.println("Celula: l="+c.getLinha()+" c="+c.getColuna());
        Personagem p = c.getPersonagem();
        if (p.infectado()){
            p.cura();
        }
    }
    */
}

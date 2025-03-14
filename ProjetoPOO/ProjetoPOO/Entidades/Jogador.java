package Entidades;
import Itens.Arma;
import Itens.Atadura;
import Itens.TacoBasebol;

public class Jogador extends Entidades {
    // atributos
    private int vida;
    private int percepcao; // definida na dificuldade do jogo, ver como pegar quando a dificuldade for escolhida
    private int x;
    private int y;
    Arma arma;
    Atadura atadura;
    TacoBasebol taco;
    
    public Jogador  () {
        this.vida = 20; // ver sobre isso
        this.x = 0;
        this.y = 0;
    }


    // get e set
    public int getPercepcao() {
        return percepcao;
    }

    public int getVida() {
        return vida;
    }

    public void setPercepcao(int percepcao) {
        this.percepcao = percepcao;
    }
    
    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getJogador_X() {
        return x;
    }

    public int getJogador_Y() {
        return y;
    }

    public void setJogador_X(int x) {
        this.x = x;
    }

    public void setJogador_Y(int y) {
        this.y = y;
    }
}
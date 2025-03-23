package Entidades;

public class Jogador extends Entidades {
    // atributos
    private int vida;
    private int percepcao; // definida na dificuldade do jogo, ver como pegar quando a dificuldade for escolhida
    private int x;
    private int y;
    private int dano;
    private int municao;
    private boolean tacoBasebol;
    private boolean arma;
    private boolean atadura;
    
    public Jogador  () {
        this.x = 0;
        this.y = 0;
        this.vida = 10;
        this.dano = 1;
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
    
    
    public int getMunicao() {
        return municao;
    }

    public boolean getTacoBasebol() {
        return tacoBasebol;
    }

    public boolean getArma() {
        return arma;
    }

    public boolean getAtadura () {
        return atadura;
    }

    public void setMunicao (int municao) {
        this.municao += municao;
    }

    public void setTacoBasebol () {
        this.tacoBasebol = true;
    }

    public void setArma () {
        this.arma = true;
    }

    public void setAtadura () {
        this.atadura = true;
    }


    public int getDano() {
        return dano;
    }

    public int getDanoTacoBasebol() {
        return dano = 2;
    }

    public int getDanoArma() {
        return dano = 3;
    }


    public void regeneraVida () {
        if (getAtadura() == true) {
            setVida(vida + 5);
        }
    }

}
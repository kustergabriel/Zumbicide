package Entidades;

public abstract class Zumbi extends Entidades {
    private int vidaMaxima;
    private int vidaAtual;
    private int dano;

   
    public Zumbi(int vida, int dano) {
        this.vidaMaxima = vida;
        this.vidaAtual = vida; 
        this.dano = dano;
    }

    
    public int getVidaMaxima() {
        return vidaMaxima;
    }

    
    public int getVidaAtual() {
        return vidaAtual;
    }

 
    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = Math.max(0, Math.min(vidaAtual, vidaMaxima)); // Garante que a vida fique entre 0 e vida máxima
    }

    // Getter para dano
    public int getDano() {
        return dano;
    }

    // Setter para dano
    public void setDano(int dano) {
        this.dano = dano;
    }

    
}

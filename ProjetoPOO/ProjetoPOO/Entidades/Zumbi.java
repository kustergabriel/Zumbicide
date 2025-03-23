package Entidades;

public abstract class Zumbi extends Entidades {
    private int vida;
    private int dano;

   
    public Zumbi(int vida, int dano) {
        this.vida = vida;
        this.dano = dano;
    }

    
    public int getVida() {
        return vida;
    }
    
    public void setVida(int vida) {
        this.vida = vida;
    }

    // Getter para dano
    public int getDano() {
        return dano;
    }

    // Setter para dano
    public void setDano(int dano) {
        this.dano = dano;
    }


    @Override
    public String toString() {
        return super.toString();
    }

}

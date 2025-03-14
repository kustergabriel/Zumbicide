package Itens;

public class Arma extends Itens {
    private int municao; 

    
    public Arma() {
        super(2);
        this.municao = 4; 
    }

    
    public void setMunicao(int municao) {
        this.municao = municao;
    }

    
    public int getMunicao() {
        return this.municao;
    }
}


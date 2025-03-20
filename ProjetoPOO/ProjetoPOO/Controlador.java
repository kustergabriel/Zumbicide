import java.util.Random;

public class Controlador {
    //ATRIBUTOS
    private Mapa tabuleiro;
    private InterfaceJogo interfaceJogo;
    Random gerador = new Random();

    //CONSTRUTOR
    public Controlador(Mapa tabuleiro, InterfaceJogo interfaceJogo) {
        tabuleiro = this.tabuleiro;
        interfaceJogo = this.interfaceJogo;
    }

    //MÉTODOS
    // Coisas do jogador
    public void jogarDadoEsquiva (int percepcao) {
            if ((gerador.nextInt(3) + 1) == percepcao) { // + 1 para ele pular o zero na contagem
                System.out.println("Esquiva!!!!");
            } else {
                System.out.println("Nao esquiva!!!!");
            }
    }

    public void jogarDadoAtaqueCritico () {
        if ((gerador.nextInt(5) + 1) == 6) { // + 1 para ele pular o zero na contagem
            System.out.println("Ataque Critico!!!!");
        } else {
            System.out.println("Nao Critico!!!!");
        }
    }
}

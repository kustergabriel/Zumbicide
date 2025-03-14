import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MapaAntigo extends JFrame {
    //ATRIBUTOS
    private final int tamanho = 10;
    private JButton[][] botoes = new JButton[tamanho][tamanho];

    //Posição do jogador
    private int posicaoX = 0;  //linha
    private int posicaoY = 0;  //coluna


    private static final String[][] MAPA = {
        {"P", "W", "", "B", "", "", "", "Z", "", ""},
        {"", "W", "", "W", "ZR", "", "", "B", "", ""},
        {"", "W", "", "W", "", "", "W", "W", "", "Z"},
        {"", "W", "", "W", "", "", "W", "W", "", ""},
        {"", "", "B", "W", "", "ZC", "W", "W", "", ""},
        {"", "W", "W", "W", "", "", "W", "W", "", ""},
        {"Z", "W", "", "", "", "Z", "W", "W", "ZC", ""},
        {"", "W", "", "ZR", "", "", "B", "", "", ""},
        {"", "W", "W", "W", "W", "W", "", "", "W", ""},
        {"B", "", "", "Z", "", "", "", "", "W", "ZG"}
    };
    /*
    W = Parede
    ZR = Zumbi Rastejante
    ZC = Zumbi Corredor
    Z = Zumbi Comum
    ZG = Zumbi Gigante
    B = Baú
    */

    //CONSTRUTOR
    public MapaAntigo() {
        setTitle("ZUMBICIDIO MAPA 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(tamanho, tamanho));

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                botoes[i][j] = new JButton(MAPA[i][j]);
                if(MAPA[i][j] == "W"){
                    botoes[i][j].setEnabled(false); //Desabilitar paredes
                }
                //Borda rosa
                botoes[i][j].setBorder(BorderFactory.createLineBorder(Color.PINK));

                //Deixar não transparente
                definirCor(botoes[i][j], MAPA[i][j]);

                //Adiciona um ActionListener para cada botão
                final int x = i;
                final int y = j;
                botoes[i][j].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    moverParaPosicao(x, y);
                    }
                });

                add(botoes[i][j]);
            }
        }
    }

    //MÉTODOS
    private void definirCor(JButton botao, String tipo) {
        switch (tipo) {
            case "W": botao.setBackground(Color.GRAY); break;
            case "P": botao.setBackground(Color.BLUE); break;
            case "B": botao.setBackground(Color.YELLOW); break;
            case "ZR": botao.setBackground(Color.ORANGE); break;
            case "ZC": botao.setBackground(Color.RED); break;
            case "Z": botao.setBackground(Color.GREEN); break;
            case "ZG": botao.setBackground(Color.MAGENTA); break;
            default: botao.setBackground(Color.WHITE); break;
        }
        botao.setOpaque(true);
    }

    private void moverParaPosicao(int novaX, int novaY) {
        //Mover uma casa por vez
        if ((Math.abs(novaX - posicaoX) <= 1 && Math.abs(novaY - posicaoY) == 0) ||
            (Math.abs(novaX - posicaoX) == 0 && Math.abs(novaY - posicaoY) <= 1)) {
            //Verifica se a posição é uma parede/posição atual
            if (!MAPA[novaX][novaY].equals("W") && (novaX != posicaoX || novaY != posicaoY)) {
                //Remove o player da posição anterior
                botoes[posicaoX][posicaoY].setText("");  //Remove o texto do botão (Deixa vazio)
                botoes[posicaoX][posicaoY].setBackground(Color.WHITE);  //Muda a cor da casa antiga para branca
                MAPA[posicaoX][posicaoY] = "";  //Atualiza a varivael MAPA

                // Coloca o "P" na nova posição
                botoes[novaX][novaY].setText("P");  //Define o texto do botão para "P"
                botoes[novaX][novaY].setBackground(Color.BLUE);  //Muda a cor da nova casa para azul
                MAPA[novaX][novaY] = "P";  // Atualiza a varivael MAPA

                posicaoX = novaX;
                posicaoY = novaY;
            }
        }
    }
}
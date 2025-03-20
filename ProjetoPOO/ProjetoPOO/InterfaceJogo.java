import javax.swing.*;
import Entidades.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceJogo extends JFrame {
    //ATRIBUTOS
    private int tamanho = 10;
    private JButton[][] botoes = new JButton[tamanho][tamanho];
    private Mapa mapa1;  // Agora não cria diretamente em linha, apenas declara
    private Entidades[][] mapa2;

    //CONSTRUTOR
    InterfaceJogo() {
        int x_local = 0;
        int y_local = 0; 
        mapa1 = new Mapa(this); // Passando 'this' (a instância de InterfaceJogo) para o construtor de Mapa
        mapa2 = mapa1.getMapa();

        setTitle("ZUMBIS ODEIAM JAVA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
        setLocationRelativeTo(null);

        GridLayout grid = new GridLayout(10,10);
        setLayout(grid);

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                botoes[i][j] = new JButton();
                
                if (mapa2[i][j] instanceof Jogador) {
                    botoes[i][j].setText("P");
                    x_local = i;
                    y_local = j;
                } else if (mapa2[i][j] instanceof Parede) {
                    botoes[i][j].setText("W");
                } else if (mapa2[i][j] instanceof Bau) {
                    botoes[i][j].setText("B");
                } else if (mapa2[i][j] instanceof ZumbiNormal) {
                    botoes[i][j].setText("Z");
                } else if (mapa2[i][j] instanceof ZumbiRastejante) {
                    botoes[i][j].setText("ZR");
                } else if (mapa2[i][j] instanceof ZumbiGigante) {
                    botoes[i][j].setText("ZG");
                } else if (mapa2[i][j] instanceof ZumbiCorredor) {
                    botoes[i][j].setText("ZC");
                } else {
                    botoes[i][j].setText(" ");;
                }

                if(botoes[i][j].getText().equals("W")){
                        botoes[i][j].setEnabled(false); //Desabilitar paredes
                    }
                    
                //CUSTOMIZAR BOTOES
                botoes[i][j].setBorder(BorderFactory.createLineBorder(Color.PINK));   
                definirCor(botoes[i][j], botoes[i][j].getText()); //Deixar nao transparente

                final int x = i;
                final int y = j;

                botoes[i][j].addActionListener(new ActionListener() {
                @Override
                    public void actionPerformed(ActionEvent e) {
                        if (mapa1.moverJogador(x, y)) {
                            atualizarTabuleiro(mapa1.getMapa());

                        } else {
                            System.out.println("Movimento inválido!");
                        }
                        }
                    });
                if (!Mapa.debug) { 
                    botoes[i][j].setVisible(false);
                }
                add(botoes[i][j]);

            }
        }
        //VISUALIZAR APENAS A MESMA LINHA E COLUNA ATE A PAREDE
        //AQUI TEM QUE COLOCAR O BOTAO DE DEBUG
        //Revelar linha 
        for (int j = y_local; j < tamanho; j++) { //direita
            if (mapa2[x_local][j] instanceof Parede) break;
            botoes[x_local][j].setVisible(true);
        }
        for (int j = y_local; j >= 0; j--) { //esquerda
            if (mapa2[x_local][j] instanceof Parede) break;
            botoes[x_local][j].setVisible(true);
        }

        //Revelar coluna
        for (int i = x_local; i < tamanho; i++) { //baixo
            if (mapa2[i][y_local] instanceof Parede) break;
            botoes[i][y_local].setVisible(true);
        }
        for (int i = x_local; i >= 0; i--) { //cima
            if (mapa2[i][y_local] instanceof Parede) break;
            botoes[i][y_local].setVisible(true);
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

    public void atualizarTabuleiro(Entidades[][] tabuleiro) {
        int x_local = 0;
        int y_local = 0;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] instanceof Jogador) {
                    botoes[i][j].setText("P");
                    definirCor(botoes[i][j], "P");
                    x_local = i;
                    y_local = j;
                } else if (tabuleiro[i][j] instanceof Parede) {
                    botoes[i][j].setText("W");
                    definirCor(botoes[i][j], "W");
                } else if (tabuleiro[i][j] instanceof Bau) {
                    botoes[i][j].setText("B");
                    definirCor(botoes[i][j], "B");
                } else if (tabuleiro[i][j] instanceof ZumbiNormal) {
                    botoes[i][j].setText("Z");
                    definirCor(botoes[i][j], "Z");
                } else if (tabuleiro[i][j] instanceof ZumbiRastejante) {
                    botoes[i][j].setText("ZR");
                    definirCor(botoes[i][j], "ZR");
                } else if (tabuleiro[i][j] instanceof ZumbiGigante) {
                    botoes[i][j].setText("ZG");
                    definirCor(botoes[i][j], "ZG");
                } else if (tabuleiro[i][j] instanceof ZumbiCorredor) {
                    botoes[i][j].setText("ZC");
                    definirCor(botoes[i][j], "ZC");
                } else {
                    botoes[i][j].setText(" ");
                    definirCor(botoes[i][j], " "); // Espaço vazio
                }

                if (!Mapa.debug) {
                    botoes[i][j].setVisible(false);
                }
            }
        }
        if (!Mapa.debug) {
            //AQUI TEM QUE COLOCAR O BOTAO DE DEBUG
            //Revelar linha 
            for (int j = y_local; j < tamanho; j++) { //direita
                if (tabuleiro[x_local][j] instanceof Parede) break;
                botoes[x_local][j].setVisible(true);
            }
            for (int j = y_local; j >= 0; j--) { //esquerda
                if (tabuleiro[x_local][j] instanceof Parede) break;
                botoes[x_local][j].setVisible(true);
            }

            //Revelar coluna
            for (int i = x_local; i < tamanho; i++) { //baixo
                if (tabuleiro[i][y_local] instanceof Parede) break;
                botoes[i][y_local].setVisible(true);
            }
            for (int i = x_local; i >= 0; i--) { //cima
                if (tabuleiro[i][y_local] instanceof Parede) break;
                botoes[i][y_local].setVisible(true);
            }
        }
    }
}

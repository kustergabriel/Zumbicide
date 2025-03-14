import javax.swing.*;

import Entidades.*;

import java.awt.*;
import java.awt.event.*;

public class InterfaceJogo extends JFrame {
    //ATRIBUTOS
    private int tamanho = 10;
    private JButton[][] botoes = new JButton[tamanho][tamanho];
    private Mapa mapa1 = new Mapa(); 
    private String[][] mapa2;
    //Controlador controlador = new Controlador(mapa1, null);

    //CONSTRUTOR
    InterfaceJogo() {
        mapa2 = mapa1.getMapa();

        setTitle("ZUMBICIDE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
        setLocationRelativeTo(null);

        GridLayout grid = new GridLayout(10,10);
        setLayout(grid);

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                botoes[i][j] = new JButton();


                if(mapa2[i][j].equals("W")){
                        botoes[i][j].setEnabled(false); //Desabilitar paredes
                    }
                    
                //Borda rosa
                botoes[i][j].setBorder(BorderFactory.createLineBorder(Color.PINK));

                //Deixar não transparente
                definirCor(botoes[i][j], mapa2[i][j]);
                
                final int x = i;
                final int y = j;

                botoes[i][j].addActionListener(new ActionListener() {
                @Override
                    public void actionPerformed(ActionEvent e) {
                        if (mapa1.moverJogador(x, y)) {
                            atualizarTabuleiro(mapa1.getTabuleiro());
                        } else {
                            System.out.println("Movimento inválido!");
                        }
                        //controlador.moverJogador(x, y);
                        //Jogador.getJogador_X(), Jogador.getJogador_Y()
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

    public void atualizarTabuleiro(Entidades[][] tabuleiro) {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] instanceof Jogador) {
                    botoes[i][j].setText("P");
                    definirCor(botoes[i][j], "P");
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
            }
        }
    }
}

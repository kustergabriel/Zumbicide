import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Entidades.*;

public class Mapa extends Entidades {
    //ATRIBUTOS
    private String opcao = "Mao";
    private final  int tamanho = 10;
    public  Entidades[][] tabuleiro = new Entidades[tamanho][tamanho];
    private String mapa;
    private Jogador player = new Jogador();
    //Criar a lista com os itens do bau
    private ArrayList<String> itens = new ArrayList<>(Arrays.asList("Taco", "Atadura", "Revolver", "Revolver"));

    //CONSTRUTOR
    Mapa() {
        mapa = EscolherMapa();
        mapa = "ProjetoPOO/Mapas/Mapa_1.txt";

        //Parte para criar os baus
        //Aleatorizar, pra depois fazer um contador e fica 10/10
        Collections.shuffle(itens);
        //Como o array tem 4 itens, e sempre vai ter 4 baus, eu vou colocar um em cada
        int contador_bau = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(mapa))) {
            String linha;
            int i = 0;
        
            while ((linha = br.readLine()) != null && i < tamanho) {
                String[] valores = linha.split(" "); //Separa os elementos por espaço
                for (int j = 0; j < valores.length && j < tamanho; j++) {
                    char simbolo = valores[j].charAt(0); //Pega o primeiro caractere
                    
                    switch (simbolo) {
                        case 'P': 
                            tabuleiro[i][j] = player; 
                            player.setJogador_X(i); 
                            player.setJogador_Y(i); 
                            break;
                        case 'W': 
                            tabuleiro[i][j] = new Parede(); 
                            break;
                        case 'B': 
                            tabuleiro[i][j] = new Bau(itens.get(contador_bau));

                            //AQUI TAVA DANDO BO PQ O ARQUIVO DE EXEMPLO TEM 5 BAUS
                            //ENTÃO ADICIONEI O RESET PRA SE ALGUEM COLOCAR 5 OU MAIS BAUS NAO QUEBRAR
                            contador_bau = (contador_bau + 1) % itens.size();
                            break;
                        case 'Z': 
                            tabuleiro[i][j] = new ZumbiNormal(); 
                            break;
                        case 'R': 
                            tabuleiro[i][j] = new ZumbiRastejante(); 
                            break;
                        case 'G': 
                            tabuleiro[i][j] = new ZumbiGigante(); 
                            break;
                        case 'C': 
                            tabuleiro[i][j] = new ZumbiCorredor(); 
                            break;
                        default: 
                            tabuleiro[i][j] = new Vazio(); // Espaço vazio
                    }
                }
                i++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    //MÉTODOS
    private String EscolherMapa() {
        int num_aleatorio = (int)(Math.random() * 3) + 1; // Gera um valor aleatório 1 a 3
        if(num_aleatorio == 1) {
            mapa = "ProjetoPOO/Mapas/Mapa_1.txt";
        } else if (num_aleatorio == 2) {
            mapa = "ProjetoPOO/Mapas/Mapa_2.txt";
        } else {
            mapa = "ProjetoPOO/Mapas/Mapa_3.txt";
        }
        return mapa;
    }

    public boolean moverJogador(int novaX, int novaY) {
        //Mover uma casa por vez
        if ((Math.abs(novaX - player.getJogador_X()) <= 1 && Math.abs(novaY - player.getJogador_Y()) == 0) ||
            (Math.abs(novaX - player.getJogador_X()) == 0 && Math.abs(novaY - player.getJogador_Y()) <= 1)) {
                if(tabuleiro[novaX][novaY] instanceof Vazio) {
                    //Coloca vazio na posição anterior
                    tabuleiro[player.getJogador_X()][player.getJogador_Y()] = new Vazio();
                    //Atualiza x,y do jogador
                    player.setJogador_X(novaX);
                    player.setJogador_Y(novaY);

                    tabuleiro[novaX][novaY] = player;
                    return true;
                } else if (tabuleiro[novaX][novaY] instanceof Bau) {
                    Bau bau = (Bau) tabuleiro[novaX][novaY]; //Faz o casting para Bau PORCARIA DE JAVA 3H PRA ISSO
                    String item = bau.getItem();

                    //JUNIOR(EU) CONTINUAR AQUI
                    //FAZER A LOGICA DO BAU
                    //ELES QUE SE FODAM NA BATALHA LA
                    
                } else {
                    interfaceBatalha(player, tabuleiro[novaX][novaY] );
                    return true;
                }
            }
            return false;
        }

    public void exibirMapa() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] instanceof Jogador) {
                    System.out.print("P ");
                } else if (tabuleiro[i][j] instanceof Parede) {
                    System.out.print("W ");
                } else if (tabuleiro[i][j] instanceof Bau) {
                    System.out.print("B ");
                } else if (tabuleiro[i][j] instanceof ZumbiNormal) {
                    System.out.print("Z ");
                } else if (tabuleiro[i][j] instanceof ZumbiRastejante) {
                    System.out.print("ZR ");
                } else if (tabuleiro[i][j] instanceof ZumbiGigante) {
                    System.out.print("ZG ");
                } else if (tabuleiro[i][j] instanceof ZumbiCorredor) {
                    System.out.print("ZC ");
                } else if (tabuleiro[i][j] instanceof Vazio){
                    System.out.print("X");
                } else {
                    System.out.print(" "); // Espaço vazio
                }
            }
            System.out.println(); // Nova linha após cada linha do tabuleiro
        }
    }

    public Entidades[][] getMapa() {
        return tabuleiro;
    }

    public Entidades getObjetoXY(int x, int y) {
        return tabuleiro[x][y];
    }

    public Jogador getJogador() {
    return player;
    }



    // ------------METODOS DA BATALHA (SOCORRO)---------
    public void interfaceBatalha(Jogador player, Entidades zumbi) {
        JFrame janela = new JFrame();
        
        JButton botaoFugir = new JButton("--FUGIR--");
        botaoFugir.setBounds(600,500,200,30);
        
        botaoFugir.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        }});
        
        JLabel msg = new JLabel("Escolha como atacar?"); 
        msg.setBounds(350, 50, 200, 30); 
        
        JButton botao = new JButton("--REVOLVER--");
        botao.setBounds(300, 410, 200, 30);

        JButton botao2 = new JButton("--SOCO--");
        botao2.setBounds(520, 410, 200, 30);
        botao2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Scanner teclado = new Scanner(System.in);

                if (opcao == "Mao") {
                    danoComAMao(zumbi);
                }
                
            }
        });
        janela.add(botaoFugir);
        janela.add(msg);
        janela.setLayout(null);
        janela.add(botao);
        janela.add(botao2);
        janela.setBounds(225, 150, 900, 500);
        janela.setDefaultCloseOperation(3);
        janela.setVisible(true);
    }

    
    public int danoComAMao (Entidades zumbi) {
        Zumbi zumbiCast = (Zumbi) zumbi;
        int dado = (int) (Math.random() * 6 + 1);
        if(zumbi instanceof ZumbiGigante){
            int vidaperdida = zumbiCast.getVida();
            vidaperdida -= 0;
            zumbiCast.setVida(vidaperdida);
        }
        
        if (dado == 6){
            int vidaperdida = zumbiCast.getVida();
            vidaperdida -= 2;
            zumbiCast.setVida(vidaperdida);
            System.out.println (zumbiCast.getVida());
        }
        else {
            int vidaperdida = zumbiCast.getVida();
            vidaperdida -= 1;
            zumbiCast.setVida(vidaperdida);
            System.out.println (zumbiCast.getVida());
        }
        
        if (player.getTacoBasebol() == true) {
            int vidaperdida = zumbiCast.getVida();
            vidaperdida -= 1.5;
            zumbiCast.setVida(vidaperdida);

        } 
        
        //System.out.println("dado = " + dado);
            //if (dado == 6) {
                //System.out.println("dano critico");
            //}
        return dado;
    }

    public void bau() {

    }
    

public String[][] getMapaReserva() {
    String[][] mapa_tabuleiro = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tabuleiro[i][j] instanceof Jogador) {
                    mapa_tabuleiro[i][j] = "P";
                } else if (tabuleiro[i][j] instanceof Parede) {
                    mapa_tabuleiro[i][j] = "W";
                } else if (tabuleiro[i][j] instanceof Bau) {
                    mapa_tabuleiro[i][j] = "B ";
                } else if (tabuleiro[i][j] instanceof ZumbiNormal) {
                    mapa_tabuleiro[i][j] = "Z ";
                } else if (tabuleiro[i][j] instanceof ZumbiRastejante) {
                    mapa_tabuleiro[i][j] = "ZR ";
                } else if (tabuleiro[i][j] instanceof ZumbiGigante) {
                    mapa_tabuleiro[i][j] = "ZG ";
                } else if (tabuleiro[i][j] instanceof ZumbiCorredor) {
                    mapa_tabuleiro[i][j] = "ZC ";
                } else {
                    mapa_tabuleiro[i][j] = " ";
                }
            }
        }
    return mapa_tabuleiro;
    }


/* 
   public void Dano_ao_zumb(Zumbi zumbi) {
        if (ZumbiRastejante && arma) {
            ZumbiRastejante.vidaAtual -= 0;

            return;
        } else if (ZumbiGigante) {
            if (Arma) {
                zumbi.vidaAtual -= 2;
            }

            return;
        } else {
            if (Arma) {
                zumbi.vidaAtual -= 2;
            } else if (TacoBasebol) {
                zumbi.vidaAtual -= 1.5;
            } else {
                zumbi.vidaAtual -= 1;
            }
        }*/








}


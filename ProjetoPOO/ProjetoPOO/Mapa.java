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
    public static boolean debug = true;
    private String opcao = "Mao";
    private final  int tamanho = 10;
    public  Entidades[][] tabuleiro = new Entidades[tamanho][tamanho];
    private InterfaceJogo interfaceJogo; // Para armazenar a referência da InterfaceJogo
    private String mapa;
    private Jogador player = new Jogador();
    //Criar a lista com os itens do bau
    private ArrayList<String> itens = new ArrayList<String>();


    //CONSTRUTOR
    Mapa(InterfaceJogo interfaceJogo) {
        this.interfaceJogo = interfaceJogo; // Inicializa a variável
        mapa = EscolherMapa();
        mapa = "ProjetoPOO/Mapas/Mapa_1.txt";

        //----------------------------------------------------BAU----------------------------------------------------------
        //Aleatorizar, pra depois fazer um contador e fica 10/10
        Collections.shuffle(itens);
        //Como o array tem 4 itens, e sempre vai ter 4 baus, eu vou colocar um em cada
        int contador_bau = 0;
        itens.add("Taco");
        itens.add("Atadura");
        itens.add("Revolver");
        itens.add("Revolver");
        //----------------------------------------------------BAU----------------------------------------------------------
        
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
        Zumbi zumbiCast = (Zumbi) zumbi;
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

        // Quando clica no botao, chamamos a funcao que o jogador da dano!
        public void actionPerformed(ActionEvent e) {

                if (opcao == "Mao") {
                    // Dando problema depois se clica 2x no soco
                    if (zumbiCast.getVida() == 0) {
                        janela.setVisible(false);
                    }
                    jogadorDaDano(zumbi);
                }
                
            }
        });

        // Adicoes de Botoes
        janela.add(botaoFugir);
        janela.add(msg);
        janela.setLayout(null);
        janela.add(botao);
        janela.add(botao2);
        janela.setBounds(225, 150, 900, 500);
        janela.setDefaultCloseOperation(3);
        janela.setVisible(true);
    }

    public void jogadorDaDano (Entidades zumbi) {
        Zumbi zumbiCast = (Zumbi) zumbi;
        if (zumbiCast.getVida() != 0) {
        int vidaperdida;
        int turnos = 0;
        
        do { // Jogador e Zumbi podem dar dano...
        int dado = (int) (Math.random() * 6 + 1);
        turnos++;
        if(zumbi instanceof ZumbiGigante){
            int vida = zumbiCast.getVida();
            vida -= 0;
            zumbiCast.setVida(vida);
        }
        
        if (dado == 6){
            int vida = zumbiCast.getVida();
            vidaperdida = vida - 2;
            zumbiCast.setVida(vidaperdida);
            System.out.println ("Vida do Zumbi: " + vida + ", Vida depois do soco forte: " + vidaperdida);
        }
            else {
                int vida = zumbiCast.getVida();
                vidaperdida = vida - 1;
                zumbiCast.setVida(vidaperdida);
                System.out.println ("Vida do Zumbi: " + vida + ", Vida depois do soco xoxinho: " + vidaperdida);
            }
        
        if (player.getTacoBasebol() == true) {
            int vida = zumbiCast.getVida();
            vida -= 1.5;
            zumbiCast.setVida(vida);

        } 
    } while (vidaperdida != 0 && player.getVida() != 0);

    if (vidaperdida == 0) {
        System.out.println("Turnos Jogados: " + turnos);
        System.out.println("Zumbi Morto!");

        // Atualizando o tabuleiro para remover o zumbi
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == zumbiCast) {
                    tabuleiro[i][j] = new Vazio();  // Zumbi morreu, substitui por Vazio
                }
            }
        }
        
        // Atualizando a interface gráfica
        interfaceJogo.atualizarTabuleiro(tabuleiro);
    }
    }
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


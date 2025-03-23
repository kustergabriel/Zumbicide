import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

import Entidades.*;

public class Mapa extends Entidades {
    //ATRIBUTOS
    public static boolean debug = true;
    public static boolean fugirDaBatalha = true;
    private final  int tamanho = 10;
    public  Entidades[][] tabuleiro = new Entidades[tamanho][tamanho];
    private InterfaceJogo interfaceJogo; // Para armazenar a referência da InterfaceJogo
    private String mapa;
    private Jogador player = new Jogador();
    private int zumbiVidaPerdida;
    //Criar a lista com os itens do bau
    private ArrayList<String> itens = new ArrayList<String>();

    Mapa(InterfaceJogo interfaceJogo) {
        this.interfaceJogo = interfaceJogo; // Inicializa a variável
        mapa = EscolherMapa();
        mapa = "ProjetoPOO/Mapas/Mapa_1.txt";

        //----------------------------------------------------BAU----------------------------------------------------------
        //Como o array tem 4 itens, e sempre vai ter 4 baus, eu vou colocar um em cada
        int contador_bau = 0;
        itens.add("Taco");
        itens.add("Atadura");
        itens.add("Revolver");
        itens.add("Revolver");
        //Aleatorizar, pra depois fazer um contador e fica 10/10
        Collections.shuffle(itens);

        //----------------------------BAU----------------------------------------------------------
        
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
                   
                //TESTE PARA VER A POSIÇÃO É VAZIA
                if(tabuleiro[novaX][novaY] instanceof Vazio) {
                    //Coloca vazio na posição anterior
                    tabuleiro[player.getJogador_X()][player.getJogador_Y()] = new Vazio();
                    //Atualiza x,y do jogador
                    player.setJogador_X(novaX);
                    player.setJogador_Y(novaY);

                    tabuleiro[novaX][novaY] = player;
                    return true;

                //TESTE PARA VER A POSIÇÃO É UM BAU E PEGAR OS ITENS
                } else if (tabuleiro[novaX][novaY] instanceof Bau) {
                    Bau bau = (Bau) tabuleiro[novaX][novaY];
                    String item = bau.getItem();
                    


                    //Downcasting para conseguir fazer o jogador receber o item do bau
                    Jogador odeio_java = (Jogador) tabuleiro[player.getJogador_X()][player.getJogador_Y()];   //Downcasting, odeio Java
                    odeio_java.setTacoBasebol();
                    //Confia em mim, isso é um jogador compilador do crl

                    if (item.equals("Taco")) {
                        odeio_java.setTacoBasebol();
                        JOptionPane.showMessageDialog(null, "O jogador Recebeu um taco de basebol neste bau, seu dano agora: " + player.getDanoTacoBasebol());
                    } else if (item.equals("Atadura")) {
                        odeio_java.setAtadura();
                        JOptionPane.showMessageDialog(null, "O jogador Recebeu uma atadura neste bau.");
                    } else if (item.equals("Revolver")) {
                        odeio_java.setArma();
                        odeio_java.setMunicao(2);
                        JOptionPane.showMessageDialog(null, "O jogador Recebeu uma arma com duas municoes neste bau.");
                    }
                    
                    //Coloca vazio na posição anterior
                    tabuleiro[player.getJogador_X()][player.getJogador_Y()] = new Vazio();
                    //Atualiza x,y do jogador
                    player.setJogador_X(novaX);
                    player.setJogador_Y(novaY);

                    tabuleiro[novaX][novaY] = player;
                    return true;

                //SE NÃO FOR VAZIO OU BAU, É ZUMBI, POIS AS PAREDES ESTÃO DESABILITADAS
                } else {
                    interfaceBatalha(player, tabuleiro[novaX][novaY] );

                    if(!fugirDaBatalha) {
                        //Coloca vazio na posição anterior
                        tabuleiro[player.getJogador_X()][player.getJogador_Y()] = new Vazio();
                        //Atualiza x,y do jogador
                        player.setJogador_X(novaX);
                        player.setJogador_Y(novaY);

                        tabuleiro[novaX][novaY] = player;

                        //Resetar a variavel
                        fugirDaBatalha = false;
                    }

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

    // ------------METODOS DA BATALHA (SOCORRO)---------
    public void interfaceBatalha(Jogador player, Entidades zumbi) {
        JFrame janela = new JFrame();
        
        JLabel msgEscolhacomoatacar = new JLabel("Escolha como atacar?"); 
        msgEscolhacomoatacar.setBounds(550, 50, 200, 30); 
        
        JButton botaoRevolver = new JButton("--REVOLVER--");
        botaoRevolver.setBounds(300, 410, 200, 30);
        botaoRevolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jogadorDaDanoArma (zumbi,janela);
            }
            });

        JButton botaoSoco = new JButton("--SOCO--");
        botaoSoco.setBounds(520, 410, 200, 30);
        botaoSoco.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            jogadorDaDanoMao(zumbi, janela); // Passamos a janela para conseguir fechar ela com uma funcao
        }
        });

        JButton botaoAtadura = new JButton("--ATADURA--");
        botaoAtadura.setBounds(520, 410, 200, 30);
        botaoAtadura.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            player.regeneraVida();
            JOptionPane.showMessageDialog(null, "Vida do jogador agora: " + player.getVida());
        }
        });

        // Adicoes de Botoes
        janela.add(msgEscolhacomoatacar);
        janela.add(botaoAtadura);
        janela.setLayout(null);
        janela.add(botaoRevolver);
        janela.add(botaoSoco);
        janela.setBounds(225, 150, 900, 500);
        janela.setDefaultCloseOperation(3);
        janela.setVisible(true);
    }
    
    public void jogadorDaDanoMao (Entidades zumbi, JFrame janela) {
        Zumbi zumbiCast = (Zumbi) zumbi;
        JOptionPane.showMessageDialog(null, "Batalha contra um " + zumbi);
        
        do { // Jogador e Zumbi podem dar dano...
        int dado = (int) (Math.random() * 6 + 1);

        if(zumbi instanceof ZumbiGigante){
            // Fazer a logica para o zumbi gigante depois
        }

        if (dado == 6){
            // Aqui o jogador da um dano critico!
            int vidaZumbi = zumbiCast.getVida(); // Pegamos a vida do zumbi e tiramos dois, independente se for com a mao ou taco
            zumbiVidaPerdida = vidaZumbi - 3;
            zumbiCast.setVida(Math.max(zumbiVidaPerdida, 0)); // Math.max para nunca passar o valor de zero
            JOptionPane.showMessageDialog(null, "Soco crítico! O zumbi perdeu 3 de vida.");

        } else if (player.getTacoBasebol() == true) { // Verificar isso, por que na teoria no jogador eu ja verifico se eu tenho um taco
                int vidaZumbi = zumbiCast.getVida();
                zumbiVidaPerdida = vidaZumbi - player.getDanoTacoBasebol();
                zumbiCast.setVida(Math.max(zumbiVidaPerdida, 0));
                JOptionPane.showMessageDialog(null, "Ataque com taco! Vida do zumbi agora: " + zumbiCast.getVida());
            } 
                else {
                    int vidaZumbi = zumbiCast.getVida();
                    zumbiVidaPerdida = vidaZumbi - player.getDano();
                    zumbiCast.setVida(Math.max(zumbiVidaPerdida, 0));
                    JOptionPane.showMessageDialog(null, "Soco no zumbi! Vida agora: " + zumbiCast.getVida());
            }

            if (zumbiCast.getVida() <= 0) {
                JOptionPane.showMessageDialog(null, "Zumbi morto!");
                janela.dispose(); // Fechar a janela da batalha
                //interfaceJogo.matarZumbi();
                break;
            }

            int danoZumbi = zumbiCast.getDano();
            player.setVida(player.getVida() - danoZumbi);
            JOptionPane.showMessageDialog(null, "O zumbi atacou, o jogador perdeu " +zumbiCast.getDano() + " de vida. Vida do jogador: " + player.getVida());
    
            if (player.getVida() <= 0) {
                JOptionPane.showMessageDialog(null, "Cabo o jogo padrinho, tu morreu");
                janela.dispose(); // Fechar a janela da batalha
                break;
            }
            
         } while (zumbiCast.getVida() > 0 && player.getVida() > 0);

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

    public void jogadorDaDanoArma (Entidades zumbi, JFrame janela) {
        Zumbi zumbiCast = (Zumbi) zumbi;
        JOptionPane.showMessageDialog(null, "Batalha contra um " + zumbi);

        do {
            if (player.getArma() == true && player.getMunicao() > 0) {
                int vidaZumbi = zumbiCast.getVida();
                zumbiVidaPerdida = vidaZumbi - player.getDanoArma();
                zumbiCast.setVida(Math.max(zumbiVidaPerdida, 0)); // Math.max para nunca passar o valor de zero
                JOptionPane.showMessageDialog(null, "Tu deu um tiro no zumbi e ele perdeu " + player.getDanoArma() + " de vida!");
                player.setMunicao(player.getMunicao() - 1);
                
                
            } else if (player.getArma() == false) {
                JOptionPane.showMessageDialog(null, "Tu nem tem uma arma vacilao!!!");
                janela.dispose(); // Fechar a janela da batalha
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Acabaram suas municoes meu amigo, se ferrou!");
                janela.dispose(); // Fechar a janela da batalha
                break;
            }

            if (zumbiCast.getVida() <= 0) {
                JOptionPane.showMessageDialog(null, "Zumbi morto!");
                janela.dispose(); // Fechar a janela da batalha
                //interfaceJogo.matarZumbi();
                break;
            }

            int danoZumbi = zumbiCast.getDano();
            player.setVida(player.getVida() - danoZumbi);
            JOptionPane.showMessageDialog(null, "O zumbi atacou, o jogador perdeu " +zumbiCast.getDano() + " de vida. Vida do jogador: " + player.getVida());
    
            if (player.getVida() <= 0) {
                JOptionPane.showMessageDialog(null, "Cabo o jogo padrinho, tu morreu");
                janela.dispose(); // Fechar a janela da batalha
                break;
            }
        
        } while (zumbiCast.getVida() > 0 && player.getVida() > 0);  
        
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


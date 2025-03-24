import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

import Entidades.*;

public class Mapa extends Entidades {
    //ATRIBUTOS
    public static boolean debug = false;
    public static boolean fugirDaBatalha = true;
    private final  int tamanho = 10;
    public  Entidades[][] tabuleiro = new Entidades[tamanho][tamanho];
    private InterfaceJogo interfaceJogo;
    private String mapa;
    private Jogador player = new Jogador();
    //Criar a lista com os itens do bau
    private ArrayList<String> itens = new ArrayList<String>();
    private int contadorZumbis = 10;


    Mapa(InterfaceJogo interfaceJogo) {
        this.interfaceJogo = interfaceJogo; 
        mapa = EscolherMapa();

        //Pegar a percepção (Dificuldade)
        player.setPercepcao(MenuInicial.setarPercepcao);

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
                            tabuleiro[i][j] = new Vazio();
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
        int num_aleatorio = (int)(Math.random() * 3) + 1;
        try{
            if(num_aleatorio == 1) {
                mapa = "ProjetoPOO/Mapas/Mapa_1.txt";
            } else if (num_aleatorio == 2) {
                mapa = "ProjetoPOO/Mapas/Mapa_2.txt";
            } else {
                mapa = "ProjetoPOO/Mapas/Mapa_3.txt";
            }

            return mapa;
        } catch(Exception e) {
            e.printStackTrace();
            return "ERRO AO LER O MAPA";
        }
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
                        int dadoBau = (int) (Math.random() * 3 + 1);
                        // Aqui a gente encontra um zumbi rastejante no bau
                        JOptionPane.showMessageDialog(null, "Você abriu o bau mas havia um zumbi rastejante escondido ");
                        
                        if (dadoBau <= player.getPercepcao()){
                            JOptionPane.showMessageDialog(null, "Parabens, desviou e logo em seguida matou o zumbi!");                        
                        }
                        else if (player.getTacoBasebol() == true) { 
                            player.setVida(player.getVida() - 1);
                            JOptionPane.showMessageDialog(null, "Tu matou o zumbi com um ataque, muito forte!");
                            interfaceJogo.atualizarVidaPanel(player.getVida());                       
                        } else {
                            JOptionPane.showMessageDialog(null, "Perdeu 2 de vida mas pelo menos matou o zumbi!");                        
                            player.setVida(player.getVida() - 2); 
                            interfaceJogo.atualizarVidaPanel(player.getVida()); 
                        }
                        if (player.getVida() <= 0) {
                            JOptionPane.showMessageDialog(null, "Morreuu!");
                            telaDeReinicio();                        
                        }
                    }
                    //Coloca vazio na posição anterior
                    tabuleiro[player.getJogador_X()][player.getJogador_Y()] = new Vazio();
                    //Atualiza x,y do jogador
                    player.setJogador_X(novaX);
                    player.setJogador_Y(novaY);

                    tabuleiro[novaX][novaY] = player;
                    return true;

                //SE NÃO FOR VAZIO OU BAU, É ZUMBI, POIS AS PAREDES ESTÃO DESABILITADAS
                } else if (!(tabuleiro[novaX][novaY] instanceof Jogador)){
                    batalhaEmTurnos(player, tabuleiro[novaX][novaY]);

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
        
        public void MoverZumbis(){
            for (int i = 0; i < tamanho; i++){
                  for (int j = 0;j < tamanho;j++){
                    
                    // Verifica se tem um zumbi na posiçao atual
                    if(tabuleiro[i][j] instanceof Zumbi){
                        Zumbi zumbi = (Zumbi) tabuleiro[i][j];

                         if (zumbi instanceof ZumbiGigante) {//se for o zumbi giga pula para ao proximo zumbi
                            continue; 
                }
                            // pega a posiçao do jogador
                     int jogadorX = player.getJogador_X();
                     int jogadorY = player.getJogador_Y();
                        
                        // movimento do zumbi em direçao ao jogador
                     int dx = Integer.compare(jogadorX, i);// Retorna -1(cima), 0(n movimenta) ou 1(baixo) dependendo da posição do jogador
                     int dy = Integer.compare(jogadorY, j);
                    //Se  esta acima do zumbi (jogadorX < i), então dx sera -1 (movimento para cima).
                    // Determina a nova posição do zumbi
                     int novoX = i + dx;
                     int novoY = j + dy;

                        // VERIFICA SE A POSIÇAO ESTA DENTRO DOS LIMITES DO TABULEIRO E SE N TEM  OUTRO ZUMBI NA POSIÇAO OU PAREDE OU JOGADOR 
                     if(novoX >= 0 && novoX < tamanho && novoY >=0 && novoY < tamanho && tabuleiro[novoX][novoY] instanceof Vazio){
                     tabuleiro[i][j] = new Vazio();
                     tabuleiro[novoX][novoY]= zumbi;
                        
                        }
                    }                    
                  }  

            }

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
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public Entidades[][] getMapa() {
        return tabuleiro;
    }

    public Entidades getObjetoXY(int x, int y) {
        return tabuleiro[x][y];
    }

    // ------------METODOS DA BATALHA (SOCORRO)---------
    public void batalhaEmTurnos(Jogador player, Entidades zumbi) {
        JFrame janela = new JFrame("Batalha");
        Zumbi zumbiCast = (Zumbi) zumbi;
        JOptionPane.showMessageDialog(null, "Batalha contra um: " + zumbi);
    
        while (player.getVida() > 0 && zumbiCast.getVida() > 0) {
            int escolha;
            if(player.getTacoBasebol() == false) {
                escolha = JOptionPane.showOptionDialog(
                    null, "Escolha sua ação:", "Batalha",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new Object[]{"Soco", "Revolver", "Atadura", "Fugir"}, "Soco"); 
            } else {
                escolha = JOptionPane.showOptionDialog(
                null, "Escolha sua ação:", "Batalha",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new Object[]{"Taco", "Revolver", "Atadura", "Fugir"}, "Taco"); 
            }
            
            // Escolha do botao
            switch (escolha) {
                case 0: // mao
                    atacarComMao(player, zumbiCast);
                    break;
                case 1: // revorvi
                    atacarComArma(player, zumbiCast);
                    break;
                case 2: // cura
                    if (player.getAtadura() == true){
                    JOptionPane.showMessageDialog(null, "Vida Regenerada!");
                    player.regeneraVida();
                    interfaceJogo.atualizarVidaPanel(player.getVida());
                    break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Tu nao tem atadura safadinho!!!");
                    }
                    break;
                case 3: // fuga
                    JOptionPane.showMessageDialog(null, "Você fugiu da batalha!");
                    janela.dispose(); // sair da interface
                    return; 
            }
            
            // Se o zumbi morreu, encerrar batalha
            if (zumbiCast.getVida() <= 0) {
                JOptionPane.showMessageDialog(null, "Zumbi morto!");
                janela.dispose();
                contadorZumbis--;
                interfaceJogo.atualizarContadorZumbis(getContadorZumbis());

                if (zumbiCast instanceof ZumbiGigante && getContadorZumbis() == 0) {
                    JOptionPane.showMessageDialog(null, "Você venceu o jogo! Todos os zumbis foram derrotados.");
                    telaDeReinicio();  
                }

                if (getContadorZumbis() == 0) {
                    JOptionPane.showMessageDialog(null, "Ba, tu nao matasse o zumbi gigante por ultimo, mas mesmo assim ganhasse, Parabens");
                    telaDeReinicio();  
                }
                for (int i = 0; i < tamanho; i++) {
                    for (int j = 0; j < tamanho; j++) {
                        if (tabuleiro[i][j] == zumbiCast) {
                            tabuleiro[i][j] = new Vazio();  // Zumbi morreu, substitui por Vazio
                        }
                    }
                }
                interfaceJogo.atualizarTabuleiro(tabuleiro);
                break;
            }
            
            // Turno do zumbi
            ataqueDoZumbi(player, zumbiCast);
            
            // Se o jogador morreu, encerrar batalha
            if (player.getVida() <= 0) {
                JOptionPane.showMessageDialog(null, "Jogo encerrado, voce morreu");
                telaDeReinicio();
                janela.dispose();
                break;
            }
        }
    }

    // Aqui se for zumbi gigante nao pode dar dano na mao
    private void atacarComMao(Jogador player, Zumbi zumbi) {

        if (zumbi instanceof ZumbiGigante) {
            JOptionPane.showMessageDialog(null, "Esquece, tu nunca vai matar um zumbi gigante com um taquinhozinho!");
            return;
        }

        int dado = (int) (Math.random() * 6 + 1);
        if (dado == 6) {
            zumbi.setVida(Math.max(zumbi.getVida() - 3, 0));
            JOptionPane.showMessageDialog(null, "Ataque crítico! O zumbi perdeu 3 de vida! Vida do zumbi: " + zumbi.getVida());
        } else if (player.getTacoBasebol()) {
            zumbi.setVida(Math.max(zumbi.getVida() - player.getDanoTacoBasebol(), 0));
            JOptionPane.showMessageDialog(null, "Ataque com taco! Vida do zumbi: " + zumbi.getVida());
        } else {
            zumbi.setVida(Math.max(zumbi.getVida() - player.getDano(), 0));
            JOptionPane.showMessageDialog(null, "Soco no zumbi! Vida do zumbi: " + zumbi.getVida());
        }
    }
    // Aqui se for zumbi corredor nao pode dar dano na arma
    private void atacarComArma(Jogador player, Zumbi zumbi) {
        if (zumbi instanceof ZumbiCorredor) {
            JOptionPane.showMessageDialog(null, "Esquece, tu nunca vai matar um zumbi corredor com uma arma!");
            return;
        }
        if (player.getArma() == true && player.getMunicao() > 0) {
            zumbi.setVida(Math.max(zumbi.getVida() - player.getDanoArma(), 0));
            player.setMunicao(-1);
            JOptionPane.showMessageDialog(null, "Tiro na cabesss do zumbi! Vida do Zumbi: " + zumbi.getVida() + " | Municao: " + player.getMunicao());
        } else if (player.getArma() == true) {
            JOptionPane.showMessageDialog(null, "Acabaram as tuas municoes padrinho, se ferrou!");
        } else {
            JOptionPane.showMessageDialog(null, "Tu nem tem uma arma, meu!");
        }
    }
    private void ataqueDoZumbi(Jogador player, Zumbi zumbi) {
        int dadoPercepcao = (int) (Math.random() * 3 + 1);
        if (dadoPercepcao < player.getPercepcao()) {
            JOptionPane.showMessageDialog(null, "Você desviou do ataque do zumbi!");
        } else {
            player.setVida(Math.max(player.getVida() - zumbi.getDano(), 0));
            JOptionPane.showMessageDialog(null, "O zumbi atacou! Dano Do Zumbi " + zumbi.getDano());
            interfaceJogo.atualizarVidaPanel(player.getVida());
        }

    }

    private void telaDeReinicio() {
        int escolha;
            escolha = JOptionPane.showOptionDialog(
            null, "Escolha sua opcao:", "Tela de Morte",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, new Object[]{"Reiniciar", "Novo Jogo", "Sair"}, "Sair"); 
                
            // Escolha do botao
            switch (escolha) {
                case 0: // Reiniciar Jogo
                jogadorReiniciarJogo();
                    break;
                case 1: // Novo Jogo
                jogadorNovoJogo();
                    break;
                case 2: // Atadura
                    System.exit(0); // Fechar todo o programa
            }
    }
    
    private void jogadorReiniciarJogo() {
        player.setVida(10);
        interfaceJogo.atualizarVidaPanel(player.getVida());
    }
    
    private void jogadorNovoJogo () {
        InterfaceJogo interfaceJogo = new InterfaceJogo();
        interfaceJogo.setVisible(true);
    }


    public int getContadorZumbis() {
        return contadorZumbis;
    }

}


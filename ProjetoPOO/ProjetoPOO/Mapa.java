import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Entidades.*;

public class Mapa extends Entidades {
    //ATRIBUTOS
    //private char opcao = 's';
    private final  int tamanho = 10;
    public  Entidades[][] tabuleiro = new Entidades[tamanho][tamanho];
    private String mapa;
    private Jogador player = new Jogador();

    //CONSTRUTOR
    Mapa() {
        mapa = EscolherMapa();
        mapa = "ProjetoPOO/Mapas/Mapa_1.txt";
        
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
                            tabuleiro[i][j] = new Bau(); 
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

    public Entidades[][] getTabuleiro() {
        return tabuleiro;
    }

  // ------------METODOS DA BATALHA (SOCORRO)---------

/*
   public void Dano_ao_zumb(Zumbi zumbi) {
        
        if (ZumbiRastejante && Arma) {
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
        }
    }
*/


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




















}


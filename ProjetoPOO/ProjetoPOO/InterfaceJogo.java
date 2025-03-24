import javax.swing.*;
import Entidades.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceJogo extends JFrame {
    //ATRIBUTOS
    private int tamanho = 10;
    private JButton[][] botoes = new JButton[tamanho][tamanho];
    private Mapa mapa1;  
    private Entidades[][] mapa2;
    private JLabel lblVida;
    private JLabel lblPercepcao;
    private JLabel lblContazumbis;

    ImageIcon jogadorIcon = carregarImagem ("C:/Users/Gabriel Azevedo/Documents/GitHub/Zumbicide/ProjetoPOO/ProjetoPOO/Imagens/imagemJogador.jpg");
    ImageIcon bauIcon = carregarImagem ("C:/Users/Gabriel Azevedo/Documents/GitHub/Zumbicide/ProjetoPOO/ProjetoPOO/Imagens/imagemBau.jpg");
    ImageIcon zumbiNormalIcon = carregarImagem("C:/Users/Gabriel Azevedo/Documents/GitHub/Zumbicide/ProjetoPOO/ProjetoPOO/Imagens/zumbiNormal.jpg");
    ImageIcon zumbiRastejanteIcon = carregarImagem("C:/Users/Gabriel Azevedo/Documents/GitHub/Zumbicide/ProjetoPOO/ProjetoPOO/Imagens/zumbiRastajante.jpg");
    ImageIcon zumbiGiganteIcon = carregarImagem("C:/Users/Gabriel Azevedo/Documents/GitHub/Zumbicide/ProjetoPOO/ProjetoPOO/Imagens/zumbiBoss.jpg");
    ImageIcon zumbiCorredorIcon = carregarImagem("C:/Users/Gabriel Azevedo/Documents/GitHub/Zumbicide/ProjetoPOO/ProjetoPOO/Imagens/zumbiCorredor.jpg");

    //CONSTRUTOR
    InterfaceJogo() {
        int x_local = 0;
        int y_local = 0;
        mapa1 = new Mapa(this);
        mapa2 = mapa1.getMapa();

        setTitle("ZUMBIS ODEIAM JAVA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
        setLocationRelativeTo(null);

        // Criar o painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // Painel para os status do jogador
        JPanel painelStatus = new JPanel();
        lblVida = new JLabel("Vida: " + 10); // 10 é a vida padrão
        lblPercepcao = new JLabel("Percepção: " + MenuInicial.setarPercepcao);
        lblContazumbis = new JLabel("Zumbis: " + mapa1.getContadorZumbis());
        painelStatus.add(lblVida);
        painelStatus.add(lblContazumbis);
        painelStatus.add(lblPercepcao);
        painelPrincipal.add(painelStatus, BorderLayout.NORTH); // Aparece status no topo

        //Painel para a matriz de botões
        JPanel painelJogo = new JPanel(new GridLayout(10, 10));
        botoes = new JButton[10][10];
        

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                botoes[i][j] = new JButton();
                
                if (mapa2[i][j] instanceof Jogador) {
                    botoes[i][j].setText("P");
                    botoes[i][j].setIcon(jogadorIcon);
                    x_local = i;
                    y_local = j;
                } else if (mapa2[i][j] instanceof Parede) {
                    botoes[i][j].setText("W");
                    
                } else if (mapa2[i][j] instanceof Bau) {
                    botoes[i][j].setText("B");
                    botoes[i][j].setIcon(bauIcon);
                } else if (mapa2[i][j] instanceof ZumbiNormal) {
                    botoes[i][j].setText("Z");
                    botoes[i][j].setIcon(zumbiNormalIcon);
                } else if (mapa2[i][j] instanceof ZumbiRastejante) {
                    //botoes[i][j].setText("ZR");
                    botoes[i][j].setIcon(zumbiRastejanteIcon);
                    if (!Mapa.debug) {  //Rastejante só aparece no debug
                        botoes[i][j].setIcon(null);
                    }
                } else if (mapa2[i][j] instanceof ZumbiGigante) {
                    botoes[i][j].setText("ZG");
                    botoes[i][j].setIcon(zumbiGiganteIcon);
                } else if (mapa2[i][j] instanceof ZumbiCorredor) {
                    botoes[i][j].setText("ZC");
                    botoes[i][j].setIcon(zumbiCorredorIcon);
                } else {
                    botoes[i][j].setText(" ");
                }

                if(botoes[i][j].getText().equals("W")){
                        botoes[i][j].setEnabled(false); //Desabilitar paredes
                    }
                    
                //CUSTOMIZAR BOTOES
                botoes[i][j].setBorder(BorderFactory.createLineBorder(Color.PINK));   

                final int x = i;
                final int y = j;

                botoes[i][j].addActionListener(new ActionListener() {
                @Override
                    public void actionPerformed(ActionEvent e) {
                        if (mapa1.moverJogador(x, y)) {
                            mapa1.MoverZumbis();
                            atualizarTabuleiro(mapa1.getMapa());
                          
                         
                        } else {
                            System.out.println("Movimento inválido!");
                        }
                        }
                    });

                if (!Mapa.debug) { 
                    botoes[i][j].setVisible(false);
                }
            
            //Add os botoes no painel
            painelJogo.add(botoes[i][j]);
            }
        }

    //VISUALIZAR APENAS A MESMA LINHA E COLUNA ATE A PAREDE
    if (!Mapa.debug) { 
        setPlayerVision(botoes, x_local, y_local);
    }
    
    //Seta no centro os botoes
    painelPrincipal.add(painelJogo, BorderLayout.CENTER);

    //Add o painel de botoes ao painel principal 
    add(painelPrincipal);
    }
    //METODOS
    private ImageIcon carregarImagem(String caminho) {
        ImageIcon icon = new ImageIcon(caminho);
        Image img = icon.getImage().getScaledInstance(80, 70, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void atualizarTabuleiro(Entidades[][] tabuleiro) {
        int x_local = 0;
        int y_local = 0;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
               
                botoes[i][j].setText(" ");
                botoes[i][j].setIcon(null); 
               
                if (tabuleiro[i][j] instanceof Jogador) {
                    botoes[i][j].setText("P");
                    botoes[i][j].setIcon(jogadorIcon);
                    x_local = i;
                    y_local = j;
                    
                } else if (tabuleiro[i][j] instanceof Parede) {
                    botoes[i][j].setText("W");
                    
                } else if (tabuleiro[i][j] instanceof Bau) {
                    botoes[i][j].setText("B");
                    botoes[i][j].setIcon(bauIcon);
                } else if (tabuleiro[i][j] instanceof ZumbiNormal) {
                    botoes[i][j].setText("Z");
                    botoes[i][j].setIcon(zumbiNormalIcon);
                } else if (tabuleiro[i][j] instanceof ZumbiRastejante) {
                    //botoes[i][j].setText("ZR");
                   if (!Mapa.debug){
                      botoes[i][j].setIcon(null);} 
                    
                } else if (tabuleiro[i][j] instanceof ZumbiGigante) {
                    botoes[i][j].setText("ZG");
                    botoes[i][j].setIcon(zumbiGiganteIcon);
                } else if (tabuleiro[i][j] instanceof ZumbiCorredor) {
                    botoes[i][j].setText("ZC");
                    botoes[i][j].setIcon(zumbiCorredorIcon);
                } else {
                    botoes[i][j].setText(" ");
                    botoes[i][j].setIcon(null);
                }  

                if (!Mapa.debug) {
                    botoes[i][j].setVisible(false);
                }    
                      
            }
        }
        if (!Mapa.debug) {
            setPlayerVision(botoes, x_local, y_local);
        }
    }

    public void setPlayerVision(JButton[][] botoes, int x_local, int y_local) {
            for (int j = y_local; j < tamanho; j++) { //direita
                if (mapa2[x_local][j] instanceof Vazio || mapa2[x_local][j] instanceof ZumbiRastejante || mapa2[x_local][j] instanceof Jogador) {
                    botoes[x_local][j].setVisible(true);
                } else {
                    botoes[x_local][j].setVisible(true);
                    break;
                }
            }
            for (int j = y_local; j >= 0; j--) { //esquerda
                if (mapa2[x_local][j] instanceof Vazio || mapa2[x_local][j] instanceof ZumbiRastejante || mapa2[x_local][j] instanceof Jogador) {
                    botoes[x_local][j].setVisible(true);
                } else {
                    botoes[x_local][j].setVisible(true);
                    break;
                }
            }

            //Revelar coluna
            for (int i = x_local; i < tamanho; i++) { //baixo
                if (mapa2[i][y_local] instanceof Vazio || mapa2[i][y_local] instanceof ZumbiRastejante || mapa2[i][y_local] instanceof Jogador) {
                    botoes[i][y_local].setVisible(true);
                } else {
                    botoes[i][y_local].setVisible(true);
                    break;
                }
            }

            for (int i = x_local; i >= 0; i--) { //cima
                if (mapa2[i][y_local] instanceof Vazio || mapa2[i][y_local] instanceof ZumbiRastejante || mapa2[i][y_local] instanceof Jogador) {
                    botoes[i][y_local].setVisible(true);
                } else {    
                    botoes[i][y_local].setVisible(true);
                    break;
                }
            }
    }

    public void atualizarVidaPanel(int novaVida) {
        lblVida.setText("Vida: " + novaVida);
    }

    public void atualizarContadorZumbis(int quantZumbis) {
        lblContazumbis.setText("Zumbis: " + quantZumbis);
    }


}

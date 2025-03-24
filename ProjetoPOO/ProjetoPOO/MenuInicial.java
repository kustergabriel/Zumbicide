import javax.swing.*;
import Entidades.Jogador;
import java.awt.*;
import java.awt.event.*;

public class MenuInicial extends JFrame implements ActionListener {

    public static int setarPercepcao;
    Jogador jogador;
    JButton botaoIniciar;
    JButton botaoDebug;
    JButton botaoSair;
    JButton botaoDificuldade1;
    JButton botaoDificuldade2;
    JButton botaoDificuldade3;

    public MenuInicial() {
        setTitle("ZUMBIS ODEIAM JAVA");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        String caminhoImagem = "C:/Users/Gabriel Azevedo/Documents/GitHub/Zumbicide/ProjetoPOO/ProjetoPOO/Imagens/imagemLogo.jpg";
        ImageIcon imagemIcon = new ImageIcon(caminhoImagem);

        Image imagem = imagemIcon.getImage();
        Image novaImagem = imagem.getScaledInstance(700, 700, Image.SCALE_SMOOTH);
        imagemIcon = new ImageIcon(novaImagem);

        // Criando um JLabel para mostrar a imagem
        JLabel labelImagem = new JLabel(imagemIcon);
        labelImagem.setHorizontalAlignment(JLabel.CENTER);

        // Adicionando a imagem ao container
        container.add(labelImagem, BorderLayout.CENTER);

        // Painel para os botao
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(Color.LIGHT_GRAY);

        botaoSair = new JButton("Sair do Jogo");
        botaoDebug = new JButton("DEBUG");
        botaoDificuldade1 = new JButton("Fácil");
        botaoDificuldade2 = new JButton("Médio");
        botaoDificuldade3 = new JButton("Difícil");

        // Adicionando os botao no painel
        painelBotoes.add(botaoDificuldade1);
        painelBotoes.add(botaoDificuldade2);
        painelBotoes.add(botaoDificuldade3);
        painelBotoes.add(botaoDebug);
        painelBotoes.add(botaoSair);

        // Adicionando o painel de botao em baixo
        container.add(painelBotoes, BorderLayout.SOUTH);

        // Adicionando acao aos botao
        botaoSair.addActionListener(this);
        botaoDebug.addActionListener(this);
        botaoDificuldade1.addActionListener(this);
        botaoDificuldade2.addActionListener(this);
        botaoDificuldade3.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == botaoSair) {
            System.exit(0);
        }
        if (evento.getSource() == botaoDebug) {
            Mapa.debug = !Mapa.debug;
        }
        if (evento.getSource() == botaoDificuldade1) {
            setarPercepcao = 3;
            InterfaceJogo interfaceJogo = new InterfaceJogo();
            interfaceJogo.setVisible(true);
            setVisible(false);
        }
        if (evento.getSource() == botaoDificuldade2) {
            setarPercepcao = 2;
            InterfaceJogo interfaceJogo = new InterfaceJogo();
            interfaceJogo.setVisible(true);
            setVisible(false);
        }
        if (evento.getSource() == botaoDificuldade3) {
            setarPercepcao = 1;
            InterfaceJogo interfaceJogo = new InterfaceJogo();
            interfaceJogo.setVisible(true);
            setVisible(false);
        }
    }
}

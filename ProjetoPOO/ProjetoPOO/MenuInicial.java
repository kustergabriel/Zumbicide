import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuInicial extends JFrame implements ActionListener {

    JButton botaoIniciar;
    JButton botaoDebug;
    JButton botaoSair;

    public MenuInicial() {
        setTitle("MATRIX OF ZUMBYS");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Container principal
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Carregando a imagem
        String caminhoImagem = "C:/Users/Gabriel Azevedo/Desktop/ProjetoPOO/Imagens/imagemlogo.png";
		ImageIcon imagemIcon = new ImageIcon(caminhoImagem);

		if (imagemIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
    	System.out.println("Imagem carregada com sucesso!");
			} else {
    	System.out.println("Erro ao carregar a imagem. Verifique o caminho: " + caminhoImagem);
			}
        // Redimensionando a imagem (opcional)
        Image imagem = imagemIcon.getImage();
        Image novaImagem = imagem.getScaledInstance(500, 500, Image.SCALE_SMOOTH); // Ajuste o tamanho conforme necessário
        imagemIcon = new ImageIcon(novaImagem);

        // Criando um JLabel para exibir a imagem
        JLabel labelImagem = new JLabel(imagemIcon);
        labelImagem.setHorizontalAlignment(JLabel.CENTER); // Centraliza a imagem no JLabel

        // Adicionando a imagem ao container
        container.add(labelImagem, BorderLayout.CENTER);

        // Painel para os botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(Color.LIGHT_GRAY);

        // Criando os botões
        botaoIniciar = new JButton("Iniciar Jogo");
        botaoSair = new JButton("Sair do Jogo");
        botaoDebug = new JButton("DEBUG");

        // Adicionando os botões ao painel
        painelBotoes.add(botaoIniciar);
        painelBotoes.add(botaoDebug);
        painelBotoes.add(botaoSair);

        // Adicionando o painel de botões ao rodapé (SOUTH) do BorderLayout
        container.add(painelBotoes, BorderLayout.SOUTH);

        // Adicionando listeners aos botões
        botaoSair.addActionListener(this);
        botaoIniciar.addActionListener(this);
        botaoDebug.addActionListener(this);

        // Tornando a janela visível
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == botaoIniciar) {
            setVisible(false);
            //SwingUtilities.invokeLater(() -> {
            //    new Mapa().setVisible(true);
            //});
        }
        if (evento.getSource() == botaoSair) {
            System.exit(0);
        }
        if (evento.getSource() == botaoDebug) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MenuInicial();
    }
}
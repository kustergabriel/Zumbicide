import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Batalha extends JFrame {
        char opcao = 's';

        Batalha() {
        JFrame janela = new JFrame();
        
        JLabel msg = new JLabel("Escolha como atacar?"); 
        msg.setBounds(350, 50, 200, 30); 
        
        JButton botao = new JButton("--REVOLVER--");
        botao.setBounds(200, 410, 200, 30);

        JButton botao2 = new JButton("--soco--");
        botao2.setBounds(500, 410, 200, 30);
        botao2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Scanner teclado = new Scanner(System.in);

                while (opcao == 's') {

                    System.out.println("Lancando o dado... ");
                    int dado = (int) (Math.random() * 6 + 1);
                    System.out.println("dado = " + dado);

                    if (dado == 6) {
                        System.out.println("dano critico");
                    }
                    break;
                }
            }
        });
        janela.add(msg);
        janela.setLayout(null);
        janela.add(botao);
        janela.add(botao2);
        janela.setBounds(225, 150, 900, 500);
        janela.setDefaultCloseOperation(3);
        janela.setVisible(true);
    }
}


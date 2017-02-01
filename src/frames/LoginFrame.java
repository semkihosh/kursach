package frames;

import colors.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JPanel panel = new JPanel();
    private JButton anonymButton = new JButton("Войти анонимно");
    private JButton adminButton = new JButton("Войти как администратор");
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    public LoginFrame() {
        initFrame();

        panel.setBackground(Colors.darkBlueColor());
        panel.setBounds(0, 0, getWidth(), getHeight());

        int padding = 5;
        int anonymButtonHeight = 50;
        int adminButtonHeight = 50;
        int anonymButtonWidth = 250;
        int adminButtonWidth = 250;
        int codeButtonHeight = 100;
        int codeButtonWight = 100;

        int adminButtonX = ((panel.getWidth() - adminButtonWidth) / 2);
        int adminButtonY = ((panel.getHeight() - adminButtonHeight) / 2);
        int anonymButtonX = ((panel.getWidth() - anonymButtonWidth) / 2);
        int anonymButtonY = ((panel.getHeight() - anonymButtonHeight) / 2) + adminButtonHeight + padding;
        int codeButtonX = ((panel.getWidth()- codeButtonWight)/ 2);
        int codeButtonY = ((panel.getHeight() - codeButtonHeight) / 2) + anonymButtonHeight + padding;

        anonymButton.setBounds(anonymButtonX, anonymButtonY, anonymButtonWidth, anonymButtonHeight);
        adminButton.setBounds(adminButtonX, adminButtonY, adminButtonWidth, adminButtonHeight);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLoginFrame();
            }
        });
        anonymButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudyFrame(false);
            }
        });


        adminButton.setForeground(Color.white);
        adminButton.setBackground(Colors.redColor());
        adminButton.setOpaque(true);
        adminButton.setBorder(null);

        anonymButton.setForeground(Color.black);
        anonymButton.setBackground(Colors.blueWhiteColor());
        anonymButton.setOpaque(true);
        anonymButton.setBorder(null);



        panel.setLayout(null);
        panel.add(anonymButton);
        panel.add(adminButton);


        add(panel);

        repaint();
    }

    public void initFrame() {
        setSize(600, 600);
        setResizable(false);
        setTitle("Логин");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);

        setLocation(x, y);
    }
    public void inFrame() {
        setSize(600, 600);
        setResizable(false);
        setTitle("code");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);

        setLocation(x, y);
    }

}

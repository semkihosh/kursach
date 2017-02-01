package frames;

import colors.Colors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class SecretLoginFrame extends Frame {
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panel = new JPanel();
    private JButton codeButton = new JButton("Войти");
    private JTextField codeField = new JTextField("enter code");
    private JLabel incorrectInput = new JLabel("Неправильно введенные данные!");

    private String codeLogin = "";

    public SecretLoginFrame(boolean b) {
        inFrame();
        try {

            InputStream is = getClass().getResourceAsStream("/code.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            java.util.List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            codeLogin = lines.get(0);
            // loginField.setText(String.valueOf(f));
        } catch (IOException e) {
            codeField.setText(e.toString());
            e.printStackTrace();
        }
        panel.setBackground(Colors.darkBlueColor());
        panel.setBounds(0, 0, getWidth(), getHeight());

        int padding = 10;
        int codeButtonHeight = 50;
        int codeButtonWidth = 250;
        int fieldCodeHeight = 50;
        int fieldCodeWidth = 250;
        int incorrectDataHeight = 20;
        int incorrectDataWidth = 250;

        int incorrectDataX = ((panel.getWidth() - incorrectDataWidth) / 2);
        int incorrectDataY = ((panel.getHeight() - incorrectDataHeight) / 2) - codeButtonHeight + padding;


        int loginFieldX = ((panel.getWidth() - fieldCodeWidth) / 2);
        int loginFieldY = (((panel.getHeight() - fieldCodeHeight) / 2)
                - codeButtonHeight) - incorrectDataHeight - padding;
        int loginButtonX = ((panel.getWidth() - codeButtonWidth) / 2);
        int loginButtonY = ((panel.getHeight() - codeButtonHeight) / 2);

        incorrectInput.setForeground(Colors.redColor());
        incorrectInput.setVisible(false);
        incorrectInput.setBounds(incorrectDataX, incorrectDataY, incorrectDataWidth, incorrectDataHeight);
        codeField.setBounds(loginFieldX, loginFieldY, fieldCodeWidth, fieldCodeHeight);
        codeButton.setBounds(loginButtonX, loginButtonY, codeButtonWidth, codeButtonHeight);
        codeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isInputDataCorrect = isDataCorrect(codeField.getText(), codeField.getText());
                if (isInputDataCorrect) {
                    new StudyFrame(true);
                    incorrectInput.setVisible(false);
                } else {
                    incorrectInput.setVisible(true);
                }
            }
        });

        codeField.setForeground(Color.white);
        codeField.setBackground(Colors.lightBlue());
        codeField.setOpaque(true);
        codeField.setBorder(getBorder());

        codeButton.setForeground(Color.white);
        codeButton.setBackground(Colors.redColor());
        codeButton.setOpaque(true);
        codeButton.setBorder(null);

        panel.setLayout(null);
        panel.add(codeField);
        panel.add(codeButton);
        panel.add(incorrectInput);

        add(panel);
        setVisible(true);
    }
    public void inFrame() {
        setSize(1200, 1200);
        setResizable(false);
        setTitle("code");
        setVisible(true);

        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);

        setLocation(x, y);
    }


    public CompoundBorder getBorder() {
        int textFieldTextPadding = 10;

        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border empty = new EmptyBorder(0, textFieldTextPadding, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);

        return border;
    }

    public boolean isDataCorrect(String text, String login) {
        boolean isInputEqualsToAdminData = login.equals(codeLogin);
        return isInputEqualsToAdminData;
    }
}




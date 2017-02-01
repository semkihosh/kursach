package frames;

        import colors.Colors;

        import javax.swing.*;
        import javax.swing.border.Border;
        import javax.swing.border.CompoundBorder;
        import javax.swing.border.EmptyBorder;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.io.*;
        import java.lang.reflect.Array;
        import java.net.URISyntaxException;
        import java.net.URL;
        import java.nio.charset.Charset;
        import java.nio.charset.StandardCharsets;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class AdminLoginFrame extends JFrame {
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panel = new JPanel();
    private JButton loginButton = new JButton("Войти");
    private JTextField codeField = new JTextField("Введите код");
    private JTextField loginField = new JTextField("Введите логин");
    private JTextField passwordField = new JTextField("Введите пароль");
    private JLabel incorrectInput = new JLabel("Неправильно введенные данные!");

    private List<String> adminLogin = new ArrayList<>();
    private List<String> adminPassword = new ArrayList<>();
    private List<String> adminCode= new ArrayList<>();

    public ArrayList<String> getFileLines(String path)
    {
        ArrayList<String> lines = new ArrayList<>();
        try {
        InputStream is = getClass().getResourceAsStream(path);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        } catch (IOException e) {
            loginField.setText(e.toString());
            e.printStackTrace();
        }
        return lines;
    }
    public AdminLoginFrame() {
        initFrame();


            List<String> admin = getFileLines("/admin.txt");
            List<String> code = getFileLines("/code.txt");

            adminLogin = new ArrayList<>(Arrays.asList(admin.get(0).split(",")));
            adminPassword = new ArrayList<>(Arrays.asList(admin.get(1).split(",")));
            adminCode = new ArrayList<>(Arrays.asList(code.get(0).split(",")));
            // loginField.setText(String.valueOf(f));

        System.out.println("Login = "+adminLogin);
        System.out.println("Pass = "+adminPassword);


        panel.setBackground(Colors.darkBlueColor());
        panel.setBounds(0, 0, getWidth(), getHeight());

        int padding = 10;
        int loginButtonHeight = 50;
        int loginButtonWidth = 250;
        int fieldPasswordHeight = 50;
        int fieldPasswordWidth = 250;
        int fieldLoginHeight = 50;
        int fieldLoginWidth = 250;
        int incorrectDataHeight = 20;
        int incorrectDataWidth = 250;

        int incorrectDataX = ((panel.getWidth() - incorrectDataWidth) / 2);
        int incorrectDataY = ((panel.getHeight() - incorrectDataHeight) / 2) - loginButtonHeight + padding;
        int passwordFieldX = ((panel.getWidth() - fieldPasswordWidth) / 2);
        int passwordFieldY = ((panel.getHeight() - fieldPasswordHeight) / 2) - loginButtonHeight -
                incorrectDataHeight - padding;
        int loginFieldX = ((panel.getWidth() - fieldLoginWidth) / 2);
        int loginFieldY = (((panel.getHeight() - fieldLoginHeight) / 2)
                - loginButtonHeight) - fieldPasswordHeight - incorrectDataHeight - padding;
        int codeFieldX = ((panel.getWidth() - fieldLoginWidth) / 2);
        int codeFieldY = (((panel.getHeight() - fieldLoginHeight) / 2)
                - loginButtonHeight) - fieldPasswordHeight - incorrectDataHeight - padding - fieldLoginHeight;
        int loginButtonX = ((panel.getWidth() - loginButtonWidth) / 2);
        int loginButtonY = ((panel.getHeight() - loginButtonHeight) / 2);

        incorrectInput.setForeground(Colors.redColor());
        incorrectInput.setVisible(false);
        codeField.setBounds(codeFieldX, codeFieldY, fieldLoginWidth, fieldLoginHeight);
        incorrectInput.setBounds(incorrectDataX, incorrectDataY, incorrectDataWidth, incorrectDataHeight);
        passwordField.setBounds(passwordFieldX, passwordFieldY, fieldPasswordWidth, fieldPasswordHeight);
        loginField.setBounds(loginFieldX, loginFieldY, fieldLoginWidth, fieldLoginHeight);
        loginButton.setBounds(loginButtonX, loginButtonY, loginButtonWidth, loginButtonHeight);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isInputDataCorrect = isDataCorrect(loginField.getText(), passwordField.getText(), codeField.getText());
                if (isInputDataCorrect) {
                    new StudyFrame(true);
                    incorrectInput.setVisible(false);
                } else {
                    incorrectInput.setVisible(true);
                }
            }
        });

        loginField.setForeground(Color.white);
        loginField.setBackground(Colors.lightBlue());
        loginField.setOpaque(true);
        loginField.setBorder(getBorder());

        passwordField.setForeground(Color.white);
        passwordField.setBackground(Colors.lightBlue());
        passwordField.setOpaque(true);
        passwordField.setBorder(getBorder());

        loginButton.setForeground(Color.white);
        loginButton.setBackground(Colors.redColor());
        loginButton.setOpaque(true);
        loginButton.setBorder(null);

        panel.setLayout(null);
        panel.add(codeField);
        panel.add(passwordField);
        panel.add(loginField);
        panel.add(loginButton);
        panel.add(incorrectInput);

        add(panel);
        setVisible(true);
    }

    public void initFrame() {
        setSize(600, 600);
        setResizable(false);
        setTitle("Администратор");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);

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

    public boolean isDataCorrect(String login, String password, String code) {
        System.out.println(adminLogin);
        System.out.println(adminPassword);
        System.out.println(adminCode);
        System.out.println(login);
        System.out.println(password);
        System.out.println(code);
        boolean isInputEqualsToAdminData = (adminLogin.contains(login) && adminPassword.contains(password))
                || adminCode.contains(code);
        return isInputEqualsToAdminData;
    }
}

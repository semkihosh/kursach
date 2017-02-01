package frames;

import colors.Colors;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import utils.TreeNode;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class StudyFrame extends JFrame {
    private JPanel panel = new JPanel();
    private JTree jTree;
    private JLabel pathLabel = new JLabel("Курсы", JLabel.CENTER);
    private JScrollPane textPanel;
    private JTextPane fileTextLabel = new JTextPane();
    private JComboBox artistsComboBox;
    private JButton addFolderButton = new JButton("Добавить папку");
    private JButton addFileButton = new JButton("Добавить файл");
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private String selectedPath = "Курсы";

    public StudyFrame(boolean isAdmin) {
        initFrame();

        if (!isAdmin) {
            pathLabel.setVisible(false);
            addFolderButton.setVisible(false);
            addFileButton.setVisible(false);
        }

        panel.setBackground(Colors.darkBlueColor());
        panel.setBounds(0, 0, getWidth(), getHeight());


        File[] files = null;

        files = new File("src/Курсы").listFiles();
        String[] artistNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            artistNames[i] = files[i].getName();
        }
        artistsComboBox = new JComboBox(artistNames);


        textPanel = new JScrollPane(fileTextLabel);

        artistsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String artistName = (String) cb.getSelectedItem();

                DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
                MutableTreeNode root = (MutableTreeNode) model.getRoot();
                root.removeAllChildren();
                model.reload(root);

                fillTree("src/Курсы/" + artistName, root);

                model.reload(root);
            }
        });

        MutableTreeNode themesNode = new MutableTreeNode(new TreeNode("src/Курсы", "Курсы", true));
        fillTree("src/Курсы/" + artistsComboBox.getSelectedItem().toString(), themesNode);

        jTree = new JTree(themesNode);

        int padding = 5;
        int artistComboBoxHeight = 40;
        int pathLabelWidth = 200;
        int pathLabelHeight = 35;
        int treeWidth = 225;
        int addFolderButtonHeight = 25;
        int addFileButtonHeight = 25;
        int addFolderButtonWidth = 200;
        int addFileButtonWidth = 200;

        int pathLabelX = treeWidth + padding;
        int addFolderButtonX = pathLabelX + pathLabelWidth;
        int addFileButtonX = addFolderButtonX + addFolderButtonWidth + (padding);
        int textPaneY = addFolderButtonHeight + (padding * 3) + 1;
        int textPaneX = treeWidth + padding;

        pathLabel.setForeground(Color.white);
        pathLabel.setBounds(pathLabelX, padding, pathLabelWidth, pathLabelHeight);

        artistsComboBox.setBounds(0, 0, treeWidth, artistComboBoxHeight);

        textPanel.setBackground(Color.white);
        textPanel.setBounds(textPaneX, textPaneY, getWidth() - textPaneX - padding
                , getHeight() - textPaneY);

        fileTextLabel.setBounds(0, 0, textPanel.getWidth(), textPanel.getHeight());
        fileTextLabel.revalidate();
        jTree.setBounds(0, artistComboBoxHeight, treeWidth, panel.getHeight());
        jTree.setBorder(getBorder());
        jTree.setCellRenderer(new CellRenderer());

//        File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
//        System.out.println("Path = "+jarFile.getPath());

        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                MutableTreeNode node = (MutableTreeNode) jTree.getLastSelectedPathComponent();
                if (node == null) return;
                if (node.node.isDirectory()) {
                    selectedPath = node.toString();
                    setSelectedPath();
                } else showContent(node.node.getFilePath());
            }
        });

        addFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputData = JOptionPane.showInputDialog("Введите название папки:");
                if (inputData != null) {
                    DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
                    MutableTreeNode root = (MutableTreeNode) model.getRoot();
                    insertNodeToTree(null, inputData, root, true);
                    model.reload();
                }
            }
        });

        addFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.setFileFilter(new FileNameExtensionFilter(".doc", "doc", "text"));
                fileOpen.setFileFilter(new FileNameExtensionFilter(".docx", "docx", "text"));

                int ret = fileOpen.showDialog(null, "Выберите файл файл");
                if (ret == 0) {
                    File file = fileOpen.getSelectedFile();
                    DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
                    MutableTreeNode root = (MutableTreeNode) model.getRoot();
                    insertNodeToTree(file, file.getName(), root, false);
                    model.reload();
                }

                System.out.println(ret);
            }
        });

        addFolderButton.setBounds(addFolderButtonX, padding * 2, addFolderButtonWidth, addFolderButtonHeight);
        addFileButton.setBounds(addFileButtonX, padding * 2, addFileButtonWidth, addFileButtonHeight);
        addFolderButton.setForeground(Color.white);
        addFolderButton.setBackground(Colors.redColor());
        addFolderButton.setOpaque(true);
        addFolderButton.setBorder(null);

        addFileButton.setForeground(Color.black);
        addFileButton.setBackground(Colors.blueWhiteColor());
        addFileButton.setOpaque(true);
        addFileButton.setBorder(null);

        panel.setLayout(null);
        panel.add(jTree);
        panel.add(artistsComboBox);
        panel.add(addFileButton);
        panel.add(addFolderButton);
        panel.add(pathLabel);
        panel.add(textPanel);

        add(panel);

        repaint();
    }

    public void setSelectedPath() {
        pathLabel.setText(selectedPath);
    }

    public void initFrame() {
        setSize(1000, 700);
        setResizable(false);
        setTitle("Курсы");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);

        setLocation(x, y);
    }

    public void showContent(String selectedPath) {
        System.out.println("Sel path = " + selectedPath);
        File file = new File(selectedPath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file.getAbsolutePath());
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        String resultContent = "";
        if (file.getAbsolutePath().contains(".docx")) {
            XWPFDocument document = null;
            try {
                document = new XWPFDocument(fis);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            XWPFWordExtractor wordex = new XWPFWordExtractor(document);
            resultContent += wordex.getText();

        } else if (file.getAbsolutePath().contains(".doc")) {
            HWPFDocument doc = null;
            try {
                doc = new HWPFDocument(fis);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            WordExtractor extractor = new WordExtractor(doc);
            String[] fileData = extractor.getParagraphText();

            for (String paragraph : fileData) {
                resultContent += paragraph;
            }
        }
        fileTextLabel.setText(resultContent);
    }

    public void insertNodeToTree(File file, String newPath, MutableTreeNode parentNode, boolean isFolder) {
        Enumeration children = parentNode.children();
        if (parentNode.node.getName().equals(selectedPath)) {
            String path = (selectedPath.equals("Курсы") ? "/" +
                    artistsComboBox.getSelectedItem().toString() : "/");
            String filePath = parentNode.node.getFilePath() + path + "/" + newPath;
            MutableTreeNode mutableTreeNode = new MutableTreeNode(new TreeNode(filePath, newPath, isFolder));
            parentNode.add(mutableTreeNode);

            System.out.println("FILE = " + filePath);
            if (isFolder) new File(filePath).mkdir();
            else {
                File newFile = new File(filePath);
                writeFile(file, newFile);
            }
            return;
        }
        while (children.hasMoreElements()) {
            MutableTreeNode node = (MutableTreeNode) children.nextElement();
            if (node.node.getName().equals(selectedPath)) {
                System.out.println("PATH = " + selectedPath);
                String filePath = node.node.getFilePath() + "/" + newPath;
                MutableTreeNode mutableTreeNode = new MutableTreeNode(new TreeNode(filePath, newPath, isFolder));
                node.add(mutableTreeNode);

                if (isFolder) new File(filePath).mkdir();
                else {
                    File newFile = new File(filePath);
                    writeFile(file, newFile);
                }
                return;

            } else if (node.children().hasMoreElements()) insertNodeToTree(file, newPath, node, isFolder);
        }
    }

    public void writeFile(File from, File to) {
        InputStream inStream = null;
        OutputStream outStream = null;

        try {
            inStream = new FileInputStream(from);
            outStream = new FileOutputStream(to);

            byte[] buffer = new byte[1024];

            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            inStream.close();
            outStream.close();

            System.out.println("File is copied successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillTree(String path, MutableTreeNode parentNode) {
        File[] files = new File(path).listFiles();

        for (File file : files) {
            boolean isDirectory = file.isDirectory();
            MutableTreeNode childNode = new MutableTreeNode(new TreeNode(file.getPath(), file.getName(), isDirectory));
            parentNode.add(childNode);
            System.out.println("Path = " + path);
            if (isDirectory) fillTree(file.getPath(), childNode);
        }
    }

    public CompoundBorder getBorder() {
        int textFieldTextPadding = 10;

        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border empty = new EmptyBorder(textFieldTextPadding, textFieldTextPadding, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);

        return border;
    }

    private static class CellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean
                sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if (value instanceof DefaultMutableTreeNode) {
                MutableTreeNode node = (MutableTreeNode) value;
                utils.TreeNode treeNode = node.node;
                if (treeNode.isDirectory()) {
                    setIcon(UIManager.getIcon("FileView.directoryIcon"));
                } else {
                    setIcon(UIManager.getIcon("FileView.fileIcon"));
                }
            }
            return this;
        }
    }

    class MutableTreeNode extends DefaultMutableTreeNode {
        utils.TreeNode node;

        public MutableTreeNode(utils.TreeNode node) {
            this.node = node;
        }

        public String toString() {
            return node.getName();
        }
    }

}



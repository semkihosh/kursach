package utils;

public class TreeNode {
    private String filePath;
    private String name;
    private boolean isDirectory;

    public TreeNode(String name, boolean isDirect) {
        this.name = name;
        this.isDirectory = isDirect;
    }

    public TreeNode(String filePath, String name, boolean isDirect) {
        this.filePath = filePath;
        this.name = name;
        this.isDirectory = isDirect;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirect(boolean direct) {
        isDirectory = direct;
    }
}

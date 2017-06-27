package Test;

//JTree	指定路徑開始

import java.io.File;  

import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTree;  
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeModel;  
import Ftp.ListAllFiles;

public class AllFile extends JFrame {  
    private static final long serialVersionUID = -1877527354792619586L;  
      
    static JTree tree;  
    static DefaultTreeModel newModel;  
    static DefaultMutableTreeNode Node;  
    static DefaultMutableTreeNode temp;  
    static String path="D:/An";//需要遍历的目录  
    public AllFile() {  
        Node=traverseFolder(path);  
        newModel=new DefaultTreeModel(Node);  
        tree=new JTree(newModel);  
          
        this.setSize(400, 300);  
        this.add(new JScrollPane(tree));  
        this.setResizable(false);  
        this.setLocationRelativeTo(null);  
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  
        this.setVisible(true);  
    }  
    public static void main(String[] args) {  
        new AllFile();  
    }  
      
    public static DefaultMutableTreeNode traverseFolder(String path) {  
        DefaultMutableTreeNode fujiedian = new DefaultMutableTreeNode(new File(path).getName());  
        File file = new File(path);  
         
        if (file.exists()) {  
            File[] files = file.listFiles();  
            if (files.length == 0) {  
                if(file.isDirectory()) {//如果是空文件夹  
                    DefaultMutableTreeNode dn=new DefaultMutableTreeNode(file.getName(), false);  
                    return dn;  
                }  
            }else{  
                for (File file2 : files) {  
                    if (file2.isDirectory()) {  
                        //是目录的话，生成节点，并添加里面的节点  
                        fujiedian.add(traverseFolder(file2.getAbsolutePath()));  
                    }else{  
                        //是文件的话直接生成节点，并把该节点加到对应父节点上  
                        temp=new DefaultMutableTreeNode(file2.getName());  
                        fujiedian.add(temp);  
                    }  
                }  
            }  
        } else {//文件不存在  
            return null;  
        }  
        return fujiedian;  
    }  
}  
package Test;

//JTree	ָ��·���_ʼ

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
    static String path="D:/An";//��Ҫ������Ŀ¼  
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
                if(file.isDirectory()) {//����ǿ��ļ���  
                    DefaultMutableTreeNode dn=new DefaultMutableTreeNode(file.getName(), false);  
                    return dn;  
                }  
            }else{  
                for (File file2 : files) {  
                    if (file2.isDirectory()) {  
                        //��Ŀ¼�Ļ������ɽڵ㣬���������Ľڵ�  
                        fujiedian.add(traverseFolder(file2.getAbsolutePath()));  
                    }else{  
                        //���ļ��Ļ�ֱ�����ɽڵ㣬���Ѹýڵ�ӵ���Ӧ���ڵ���  
                        temp=new DefaultMutableTreeNode(file2.getName());  
                        fujiedian.add(temp);  
                    }  
                }  
            }  
        } else {//�ļ�������  
            return null;  
        }  
        return fujiedian;  
    }  
}  
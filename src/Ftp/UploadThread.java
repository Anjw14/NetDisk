package Ftp;

//下载大文件时不能改变JTree的选择，否则会使任务终止。因 FtpClient.storeFile需要始终保持在一个目录下，而本程序中未显示当前目录在JTable，需要随之切换目录。
//解决方法：新建个FtpClient进行本线程的运行。

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFileChooser;

import UI.FileTree;
import UI.Login;
import UI.TreeFlushThread;

public class UploadThread implements Runnable{
	
	private Login login;
	private FileTree tree;
	
	public UploadThread(Login login, FileTree tree){
		this.login = login;
		this.tree = tree;
	}

	@Override
	public void run() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("选择上传文件");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
			int returnVal = chooser.showOpenDialog(chooser);
			if(returnVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("取消");
				return;
			}
			this.upload(chooser.getSelectedFile());
			new Thread(new TreeFlushThread(tree, true)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void upload(File file) throws Exception{  
        if(file.isDirectory()){           
            login.ftpClient.makeDirectory(file.getName());                
            System.out.println("..."+login.ftpClient.changeWorkingDirectory(file.getName()));      
            String[] files = file.list();             
            for (int i = 0; i < files.length; i++) {      
                File file1 = new File(file.getPath()+"\\"+files[i] );      
                if(file1.isDirectory()){      
                    upload(file1);      
                    login.ftpClient.changeToParentDirectory();      
                }else{                    
                    FileInputStream input = new FileInputStream(file1);      
                    login.ftpClient.storeFile(file1.getName(), input);      
                    input.close();                            
                }                 
            }      
        }else{      
            FileInputStream input = new FileInputStream(file);      
            login.ftpClient.storeFile(file.getName(), input);      
            input.close();        
        }      
    } 
	
	
}

package Ftp;

//下载大文件时不能改变JTree的选择，否则会使任务终止。因 FtpClient.storeFile需要始终保持在一个目录下，而本程序中未显示当前目录在JTable，需要随之切换目录。
//解决方法：新建个FtpClient进行本线程的运行。

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import Sql.MySql;
import UI.FileTree;
import UI.Login;
import UI.MainInterface;
import UI.TreeFlushThread;

public class UploadThread implements Runnable{
	
	private Login login;
	private FileTree tree;
	private MySql mySql;
	private JTextArea historyTextArea;
	private MainInterface mainInterface;
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
	
	public UploadThread(Login login, FileTree tree, MySql mySql, JTextArea historyTextArea){
		this.login = login;
		this.tree = tree;
		this.mySql = mySql;
		this.historyTextArea = historyTextArea;
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
			String historyText = (login.name+" 于  "+df.format(new Date())+" 上传了文件 【"+chooser.getSelectedFile().getName()+"】 。");
			mySql.addExistFileNote("history", historyText+"\n");
			historyTextArea.setText(mySql.getNote("history"));
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
        	login.ftpClient.changeWorkingDirectory(new String(tree.selectedDirPath.getBytes("GBK"), "iso-8859-1"));
            FileInputStream input = new FileInputStream(file);      
            login.ftpClient.storeFile(file.getName(), input);      
            input.close();        
        }      
    } 
	
	
}

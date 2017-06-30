package Ftp;

//���ش��ļ�ʱ���ܸı�JTree��ѡ�񣬷����ʹ������ֹ���� FtpClient.storeFile��Ҫʼ�ձ�����һ��Ŀ¼�£�����������δ��ʾ��ǰĿ¼��JTable����Ҫ��֮�л�Ŀ¼��
//����������½���FtpClient���б��̵߳����С�

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
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
	
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
			chooser.setDialogTitle("ѡ���ϴ��ļ�");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
			int returnVal = chooser.showOpenDialog(chooser);
			if(returnVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("ȡ��");
				return;
			}
			this.upload(chooser.getSelectedFile());
			new Thread(new TreeFlushThread(tree, true)).start();
			String historyText = (login.name+" ��  "+df.format(new Date())+" �ϴ����ļ� ��"+chooser.getSelectedFile().getName()+"�� ��");
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

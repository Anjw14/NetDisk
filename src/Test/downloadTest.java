package Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class downloadTest {

	public static void main(String [] args){
		
	}
	public static boolean downFile(  
            String url, //FTP������hostname  
            int port,//FTP�������˿�  
            String username, //FTP��¼�˺�  
            String password, //FTP��¼����  
            String remotePath,//FTP�������ϵ����·��   
            String fileName,//Ҫ���ص��ļ���  
            String localPath//���غ󱣴浽���ص�·�� 
            ) 
	{    
        boolean success = false;    
        FTPClient ftp = new FTPClient();    
        try {    
            int reply;    
            ftp.connect(url, port);    
            //�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������     
            ftp.login(username, password);//��¼     
            reply = ftp.getReplyCode();    
            if (!FTPReply.isPositiveCompletion(reply)) {    
                ftp.disconnect();    
                return success;    
            }   
            System.out.println("aaa");
            ftp.changeWorkingDirectory(remotePath);//ת�Ƶ�FTP������Ŀ¼     
            FTPFile[] fs = ftp.listFiles();  
            
            for(FTPFile ff:fs){ 
             System.out.println("bb" + fs);
             
                if(ff.getName().equals(fileName)){  
                 System.out.println("dd");
                    File localFile = new File(localPath+"/"+ff.getName());    
                    OutputStream is = new FileOutputStream(localFile);     
                    ftp.retrieveFile(ff.getName(), is);  
                    System.out.println("ccc" +ff.getName()+fileName);
                    is.close();    
                }    
            }    
            ftp.logout();    
            success = true;    
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            if (ftp.isConnected()) {    
                try {    
                    ftp.disconnect();    
                } catch (IOException ioe) {    
                }    
            }    
        }    
        return success;    
    }
}

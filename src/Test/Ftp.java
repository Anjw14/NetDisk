package Test;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.*;

public class Ftp {

	public static void main(String[] args) throws InterruptedException {
		
		try {  
			FTPClient ftpClient = new FTPClient();
            ftpClient.connect("192.168.1.101", 2121);// ����FTP������  
            ftpClient.login("FtpUser", "Anjianwei");// ��½FTP������  
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
                System.out.println("δ���ӵ�FTP���û������������");
                ftpClient.disconnect();  
            } else {  
                System.out.println("FTP���ӳɹ���");  
//                Thread.sleep(60*1000);
            }  
        } catch (SocketException e) {  
            e.printStackTrace();  
            System.out.println("FTP��IP��ַ���ܴ�������ȷ���á�");  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("FTP�Ķ˿ڴ���,����ȷ���á�");  
        }  
	}
	
}

package Test;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.*;

public class Ftp {

	public static void main(String[] args) throws InterruptedException {
		
		try {  
			FTPClient ftpClient = new FTPClient();
            ftpClient.connect("192.168.1.101", 2121);// 连接FTP服务器  
            ftpClient.login("FtpUser", "Anjianwei");// 登陆FTP服务器  
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
                System.out.println("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();  
            } else {  
                System.out.println("FTP连接成功。");  
//                Thread.sleep(60*1000);
            }  
        } catch (SocketException e) {  
            e.printStackTrace();  
            System.out.println("FTP的IP地址可能错误，请正确配置。");  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("FTP的端口错误,请正确配置。");  
        }  
	}
	
}

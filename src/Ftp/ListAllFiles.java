package Ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

/** 
 * 列出FTP服务器上指定目录下面的所有文件 
 */  

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;  
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.PrintCommandListener;  
import org.apache.commons.net.ftp.FTPClient;  
import org.apache.commons.net.ftp.FTPFile;  
import org.apache.commons.net.ftp.FTPReply;  
import org.apache.log4j.Logger;  
public class ListAllFiles {  
    private static Logger logger = Logger.getLogger(ListAllFiles.class);  
    public FTPClient ftp;  
    public FTPFile[] ftpFiles;
    public ArrayList<String> arFiles;  
    InetAddress ip = InetAddress.getLocalHost();
      
    /** 
     * 重载构造函数 
     * @param isPrintCommmand 是否打印与FTPServer的交互命令 
     * @throws IOException 
     */  
    public ListAllFiles(boolean isPrintCommmand) throws IOException{  
        ftp = new FTPClient();
        arFiles = new ArrayList<String>();  
        if(isPrintCommmand){  
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));  
        }  
        if(this.login(ip.getHostAddress(), 21, "FtpUser", "Anjianwei"))  
        	this.List("/");
        this.disConnection(); 
//        System.out.println(arFiles.toString());
//        InputStream iStream = ftp.retrieveFileStream("chap01.pdf");
//        File file = File.createTempFile("mmp", null);
//    	FileUtils.copyInputStreamToFile(iStream, file);

//    	InputStreamReader nr = new InputStreamReader(new FileInputStream(file), "gbk");
//		BufferedReader read = new BufferedReader(nr);
//		String lineTxt = null;
//		while ((lineTxt = read.readLine()) != null) {
//			System.out.println(lineTxt);
//		}
//		read.close();
    	
//    	System.out.println(file.getName()+"**********");
//    	System.out.println(iStream.toString()+"\n");
    }  
      
    /** 
     * 登陆FTP服务器 
     * @param host FTPServer IP地址 
     * @param port FTPServer 端口 
     * @param username FTPServer 登陆用户名 
     * @param password FTPServer 登陆密码 
     * @return 是否登录成功 
     * @throws IOException 
     */  
    public boolean login(String host,int port,String username,String password) throws IOException{  
        this.ftp.connect(host,port);  
        if(FTPReply.isPositiveCompletion(this.ftp.getReplyCode())){  
            if(this.ftp.login(username, password)){  
                this.ftp.setControlEncoding("GBK");  
                return true;  
            }  
        }  
        if(this.ftp.isConnected()){  
            this.ftp.disconnect();  
        }  
        return false;  
    }  
      
    /** 
     * 关闭数据链接 
     * @throws IOException 
     */  
    public void disConnection() throws IOException{  
        if(this.ftp.isConnected()){  
            this.ftp.disconnect();  
        }  
    }  
      
    /** 
     * 递归遍历出目录下面所有文件 
     * @param pathName 需要遍历的目录，必须以"/"开始和结束 
     * @throws IOException 
     */  
    public void List(String pathName) throws IOException{  
        if(pathName.startsWith("/")&&pathName.endsWith("/")){  
            String directory = pathName;  
            //更换目录到当前目录  
            this.ftp.changeWorkingDirectory(directory);  
            ftpFiles = this.ftp.listFiles();  
            for(FTPFile file:ftpFiles){  
                if(file.isFile()){  
                    arFiles.add(directory+file.getName());  
                }else if(file.isDirectory()){  
                    List(directory+file.getName()+"/");  
                }  
            }  
        }
        this.ftp.changeWorkingDirectory(pathName);  
        ftpFiles = this.ftp.listFiles();  
    }  
      
    /** 
     * 递归遍历目录下面指定的文件名 
     * @param pathName 需要遍历的目录，必须以"/"开始和结束 
     * @param ext 文件的扩展名 
     * @throws IOException  
     */  
    public void List(String pathName,String ext) throws IOException{  
        if(pathName.startsWith("/")&&pathName.endsWith("/")){  
            String directory = pathName;  
            //更换目录到当前目录  
            this.ftp.changeWorkingDirectory(directory);  
            ftpFiles = this.ftp.listFiles();  
            for(FTPFile file:ftpFiles){  
                if(file.isFile()){  
                    if(file.getName().endsWith(ext)){  
                        arFiles.add(directory+file.getName());  
                    }  
                }else if(file.isDirectory()){  
                    List(directory+file.getName()+"/",ext);  
                }  
            }  
        } 
    }  
    public static void main(String[] args) throws IOException {  
        ListAllFiles f = new ListAllFiles(false);  
//        if(f.login("192.168.1.101", 21, "FtpUser", "Anjianwei"))  
//        	f.List("");  
//        f.disConnection();  
//        Iterator<String> it = f.arFiles.iterator();  
//        while(it.hasNext()){  
//            logger.info(it.next());  
//        }  
    }
}  


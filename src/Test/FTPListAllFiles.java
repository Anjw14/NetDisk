package Test;

/** 
 * �г�FTP��������ָ��Ŀ¼����������ļ� 
 */  

import java.io.IOException;  
import java.io.PrintWriter;  
import java.util.ArrayList;  
import java.util.Iterator;  
import org.apache.commons.net.PrintCommandListener;  
import org.apache.commons.net.ftp.FTPClient;  
import org.apache.commons.net.ftp.FTPFile;  
import org.apache.commons.net.ftp.FTPReply;  
import org.apache.log4j.Logger;  
public class FTPListAllFiles {  
    private static Logger logger = Logger.getLogger(FTPListAllFiles.class);  
    public FTPClient ftp;  
    public ArrayList<String> arFiles;  
      
    /** 
     * ���ع��캯�� 
     * @param isPrintCommmand �Ƿ��ӡ��FTPServer�Ľ������� 
     */  
    public FTPListAllFiles(boolean isPrintCommmand){  
        ftp = new FTPClient();  
        arFiles = new ArrayList<String>();  
        if(isPrintCommmand){  
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));  
        }  
    }  
      
    /** 
     * ��½FTP������ 
     * @param host FTPServer IP��ַ 
     * @param port FTPServer �˿� 
     * @param username FTPServer ��½�û��� 
     * @param password FTPServer ��½���� 
     * @return �Ƿ��¼�ɹ� 
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
     * �ر��������� 
     * @throws IOException 
     */  
    public void disConnection() throws IOException{  
        if(this.ftp.isConnected()){  
            this.ftp.disconnect();  
        }  
    }  
      
    /** 
     * �ݹ������Ŀ¼���������ļ� 
     * @param pathName ��Ҫ������Ŀ¼��������"/"��ʼ�ͽ��� 
     * @throws IOException 
     */  
    public void List(String pathName) throws IOException{  
        if(pathName.startsWith("/")&&pathName.endsWith("/")){  
            String directory = pathName;  
            //����Ŀ¼����ǰĿ¼  
            this.ftp.changeWorkingDirectory(directory);  
            FTPFile[] files = this.ftp.listFiles();  
            for(FTPFile file:files){  
                if(file.isFile()){  
                    arFiles.add(directory+file.getName());  
                }else if(file.isDirectory()){  
                    List(directory+file.getName()+"/");  
                }  
            }  
        }  
    }  
      
    /** 
     * �ݹ����Ŀ¼����ָ�����ļ��� 
     * @param pathName ��Ҫ������Ŀ¼��������"/"��ʼ�ͽ��� 
     * @param ext �ļ�����չ�� 
     * @throws IOException  
     */  
    public void List(String pathName,String ext) throws IOException{  
        if(pathName.startsWith("/")&&pathName.endsWith("/")){  
            String directory = pathName;  
            //����Ŀ¼����ǰĿ¼  
            this.ftp.changeWorkingDirectory(directory);  
            FTPFile[] files = this.ftp.listFiles();  
            for(FTPFile file:files){  
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
        FTPListAllFiles f = new FTPListAllFiles(true);  
        if(f.login("192.168.1.101", 21, "FtpUser", "Anjianwei")){  
            f.List("");  
        }  
        f.disConnection();  
        Iterator<String> it = f.arFiles.iterator();  
        while(it.hasNext()){  
            logger.info(it.next());  
        }  
          
    }  
}  

package Ftp;

/** 
 * �г�FTP��������ָ��Ŀ¼����������ļ� 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import UI.Login;


public class ListAllFiles {
	private static Logger logger = Logger.getLogger(ListAllFiles.class);
	public FTPClient ftp;
	public FTPFile[] ftpFiles;
	public ArrayList<String> arFiles;
	InetAddress ip = InetAddress.getLocalHost();

	/**
	 * ���ع��캯��
	 * 
	 * @param isPrintCommmand
	 *            �Ƿ��ӡ��FTPServer�Ľ�������
	 * @throws IOException
	 */
	public ListAllFiles(boolean isPrintCommmand, Login login) throws IOException {
//		ftp = new FTPClient();
		ftp = login.ftpClient;
		arFiles = new ArrayList<String>();
		if (isPrintCommmand) {
			ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		}
//		if (this.login(login.ipAddress, login.portNum, login.User, login.password))
			this.List("/");
//		this.disConnection();
	}

//	/**
//	 * ��½FTP������
//	 * 
//	 * @param host
//	 *            FTPServer IP��ַ
//	 * @param port
//	 *            FTPServer �˿�
//	 * @param username
//	 *            FTPServer ��½�û���
//	 * @param password
//	 *            FTPServer ��½����
//	 * @return �Ƿ��¼�ɹ�
//	 * @throws IOException
//	 */
//	public boolean login(String host, int port, String username, String password) throws IOException {
//		this.ftp.connect(host, port);
//		if (FTPReply.isPositiveCompletion(this.ftp.getReplyCode())) {
//			if (this.ftp.login(username, password)) {
//				this.ftp.setControlEncoding("GBK");
//				return true;
//			}
//		}
//		if (this.ftp.isConnected()) {
//			this.ftp.disconnect();
//		}
//		return false;
//	}
//
//	/**
//	 * �ر���������
//	 * 
//	 * @throws IOException
//	 */
//	public void disConnection() throws IOException {
//		if (this.ftp.isConnected()) {
//			this.ftp.disconnect();
//		}
//	}

	/**
	 * �ݹ������Ŀ¼���������ļ�
	 * 
	 * @param pathName
	 *            ��Ҫ������Ŀ¼��������"/"��ʼ�ͽ���
	 * @throws IOException
	 */
	public void List(String pathName) throws IOException {
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			String directory = pathName;
			// ����Ŀ¼����ǰĿ¼
			this.ftp.changeWorkingDirectory(directory);
			ftpFiles = this.ftp.listFiles();
			for (FTPFile file : ftpFiles) {
				if (file.isFile()) {
					arFiles.add(directory + file.getName());
				} else if (file.isDirectory()) {
					List(directory + file.getName() + "/");
				}
			}
		}
		this.ftp.changeWorkingDirectory(pathName);
		ftpFiles = this.ftp.listFiles();
	}

	/**
	 * �ݹ����Ŀ¼����ָ�����ļ���
	 * 
	 * @param pathName
	 *            ��Ҫ������Ŀ¼��������"/"��ʼ�ͽ���
	 * @param ext
	 *            �ļ�����չ��
	 * @throws IOException
	 */
	public void List(String pathName, String ext) throws IOException {
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			String directory = pathName;
			// ����Ŀ¼����ǰĿ¼
			this.ftp.changeWorkingDirectory(directory);
			ftpFiles = this.ftp.listFiles();
			for (FTPFile file : ftpFiles) {
				if (file.isFile()) {
					if (file.getName().endsWith(ext)) {
						arFiles.add(directory + file.getName());
					}
				} else if (file.isDirectory()) {
					List(directory + file.getName() + "/", ext);
				}
			}
		}
	}

//	public static void main(String[] args) throws IOException {
//		ListAllFiles f = new ListAllFiles(false);
		// if(f.login("192.168.1.101", 21, "FtpUser", "Anjianwei"))
		// f.List("");
		// f.disConnection();
		// Iterator<String> it = f.arFiles.iterator();
		// while(it.hasNext()){
		// logger.info(it.next());
		// }
//	}
}

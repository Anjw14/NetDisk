package Ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

/** 
 * �г�FTP��������ָ��Ŀ¼����������ļ� 
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

import UI.Login;

public class ListPointedFile {
	private static Logger logger = Logger.getLogger(ListPointedFile.class);
	public FTPClient ftp;
	public FTPFile[] ftpFiles;
	public ArrayList<String> arFiles;
	public FTPFile[] ftpCurrentFile;
	public FTPFile[] ftpFinalFile;
	public String selectPath;
	public String selectDirPath;
	InetAddress ip = InetAddress.getLocalHost();

	/**
	 * ���ع��캯��
	 * 
	 * @param isPrintCommmand
	 *            �Ƿ��ӡ��FTPServer�Ľ�������
	 * @throws IOException
	 */
	public ListPointedFile(boolean isPrintCommmand, String str, Login login) throws IOException {
//		ftp = new FTPClient();
		ftp = login.ftpClient;
		arFiles = new ArrayList<String>();
		if (isPrintCommmand) {
			ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		}
//		this.login(login.ipAddress, login.portNum, login.User, login.password);

	}

	/**
	 * ��½FTP������
	 * 
	 * @param host
	 *            FTPServer IP��ַ
	 * @param port
	 *            FTPServer �˿�
	 * @param username
	 *            FTPServer ��½�û���
	 * @param password
	 *            FTPServer ��½����
	 * @return �Ƿ��¼�ɹ�
	 * @throws IOException
	 */
//	public boolean login(String host, int port, String username, String password) throws IOException {
//		this.ftp.connect(host, port);
//		if (FTPReply.isPositiveCompletion(this.ftp.getReplyCode())) {
//			if (this.ftp.login(username, password)) {
//				this.ftp.setControlEncoding("gbk");
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
//
//	/**
//	 * �ݹ������Ŀ¼���������ļ�
//	 * 
//	 * @param pathName
//	 *            ��Ҫ������Ŀ¼��������"/"��ʼ�ͽ���
//	 * @throws IOException
//	 */
	public void List(String pathName) throws IOException {
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			String directory = pathName.substring(0, pathName.length() - 1);
			// ����Ŀ¼����ǰĿ¼
			this.ftp.changeWorkingDirectory(directory);
			ftpFiles = this.ftp.listFiles();
			for (FTPFile file : ftpFiles) {
				if (file.isFile()) {
					arFiles.add(directory + file.getName());
				} else if (file.isDirectory()) {
					List(directory + "/" + file.getName() + "/");
				}
			}
		}
		this.ftp.changeWorkingDirectory(new String(pathName.getBytes("GBK"), "iso-8859-1"));
		ftpFiles = this.ftp.listFiles();
		ftpCurrentFile = this.ftp.listFiles();
	}

	/**
	 * �õ�ָ���ļ����µ�FTPFile[]�� ����ftpFinalFile
	 */
	public FTPFile[] ListSubFile(String pathName, Login login) throws IOException {
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			if (pathName.contains(";")) {
				String directory = (pathName.split(";")[0] + pathName.split(";")[1]).substring(0,
						pathName.length() - 1);
				// ����Ŀ¼����ǰĿ¼
				if (this.ftp.changeWorkingDirectory(new String(directory.getBytes("GBK"), "iso-8859-1"))) {
					ListPointedFile tempList = new ListPointedFile(false, directory, login);
					tempList.List(directory);
					selectPath = directory;
					selectDirPath = pathName.split(";")[0];
					ftpFinalFile = tempList.ftpFiles;
					return ftpFinalFile;
				} else {
					String oldDirectory = pathName.split(";")[0];
					this.List(oldDirectory);
					for (FTPFile file : ftpCurrentFile) {
						if (file.isDirectory()) {
							ListSubFile(
									"/" + pathName.split(";")[0] + "/" + file.getName() + ";" + pathName.split(";")[1], login);
						}
					}
				}
			} else {
				String directory = pathName.substring(0, pathName.length() - 1);
				// ����Ŀ¼����ǰĿ¼
				if (this.ftp.changeWorkingDirectory(new String(directory.getBytes("GBK"), "iso-8859-1"))) {
					ListPointedFile tempList = new ListPointedFile(false, directory, login);
					selectPath = directory;
					selectDirPath = pathName.split(";")[0];
					tempList.List(directory);
					ftpFinalFile = tempList.ftpFiles;
					return ftpFinalFile;
				} else {
					ftpCurrentFile = this.ftp.listFiles();
					for (FTPFile file : ftpCurrentFile) {
						if (file.isDirectory())
							ListSubFile("/" + file.getName() + ";" + pathName, login);
					}
				}
			}

		}
		return null;
	}

//	public static void main(String[] args) throws IOException {
//		ListPointedFile f = new ListPointedFile(false, "");
//		f.List("");
//		f.ListSubFile("/test/");
//		f.disConnection();
//	}
}
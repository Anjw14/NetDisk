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
	 * 重载构造函数
	 * 
	 * @param isPrintCommmand
	 *            是否打印与FTPServer的交互命令
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
	 * 登陆FTP服务器
	 * 
	 * @param host
	 *            FTPServer IP地址
	 * @param port
	 *            FTPServer 端口
	 * @param username
	 *            FTPServer 登陆用户名
	 * @param password
	 *            FTPServer 登陆密码
	 * @return 是否登录成功
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
//	 * 关闭数据链接
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
//	 * 递归遍历出目录下面所有文件
//	 * 
//	 * @param pathName
//	 *            需要遍历的目录，必须以"/"开始和结束
//	 * @throws IOException
//	 */
	public void List(String pathName) throws IOException {
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			String directory = pathName.substring(0, pathName.length() - 1);
			// 更换目录到当前目录
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
	 * 得到指定文件夹下的FTPFile[]， 返回ftpFinalFile
	 */
	public FTPFile[] ListSubFile(String pathName, Login login) throws IOException {
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			if (pathName.contains(";")) {
				String directory = (pathName.split(";")[0] + pathName.split(";")[1]).substring(0,
						pathName.length() - 1);
				// 更换目录到当前目录
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
				// 更换目录到当前目录
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
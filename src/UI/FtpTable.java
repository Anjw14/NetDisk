package UI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import Utils.FtpClient;
import Utils.FtpFile;
import sun.net.TelnetInputStream;


public class FtpTable {

	public JTable table = new JTable();
	public FtpClient ftpClient;
	public FTPFile[] ftpFiles;
	
	public FtpTable(FileTree fileTree) throws IOException{
		table.setShowGrid(false);
		table.setModel(	
				new DefaultTableModel( 
						new Object[][] {},	new String[] {"文件名", "大小", "最后修改日期", "作者", "版本"}	)	);
	}

	/**
	 * @param fileTree
	 */
	public void tableFlush(FileTree fileTree) {
		ftpFiles = fileTree.tableFtpFiles;
		DefaultTableModel model = ( DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for(FTPFile file : ftpFiles){
			model.addRow(new Object[] { file.getName(), file.getSize(), file.getTimestamp().getTime() }); 
		}
		
	}
	
	public synchronized void fileList(final TelnetInputStream list){
		
		final DefaultTableModel model = ( DefaultTableModel) table.getModel();
		model.setRowCount(0);
		Runnable runnable = new Runnable() {
			public synchronized void run() {
				table.clearSelection();
				try {
//					FtpClient ftpClient = new FTPClient();
//					ftpClient.connect("192.168.1.101", 21);// 连接FTP服务器  
//					ftpClient.login("FtpUser", "Anjianwei");// 登陆FTP服务器  
					
					String pwd = getPwd(); // 获取FTP服务器的当前文件夹
					
					byte[]names=new byte[2048];
					int bufsize=0;
					bufsize=list.read(names, 0, names.length);
					int i=0,j=0;
					while(i<bufsize){
						//字符模式为10，二进制模式为13
//						if (names[i]==10) {
						if (names[i]==13) {
							//获取字符串 -rwx------ 1 user group          57344 Apr 18 05:32 腾讯电商2013实习生招聘TST推荐模板.xls
							//文件名在数据中开始做坐标为j,i-j为文件名的长度，文件名在数据中的结束下标为i-1
							String fileMessage = new String(names,j,i-j);
							if(fileMessage.length() == 0){
								System.out.println("fileMessage.length() == 0");
								break;
							}
							//按照空格将fileMessage截为数组后获取相关信息
							// 正则表达式  \s表示空格，｛1，｝表示1一个以上 
							if(!fileMessage.split("\\s+")[8].equals(".") && !fileMessage.split("\\s+")[8].equals("..")){
								/**文件大小*/
								String sizeOrDir="";
								if (fileMessage.startsWith("d")) {//如果是目录
									sizeOrDir="<DIR>";
								}else if (fileMessage.startsWith("-")) {//如果是文件
									sizeOrDir=fileMessage.split("\\s+")[4];
								}
								/**文件名*/
								String fileName=fileMessage.split("\\s+")[8];
								/**文件日期*/
								String dateStr =fileMessage.split("\\s+")[5] +" "+fileMessage.split("\\s+")[6]+" " +fileMessage.split("\\s+")[7];
//								System.out.println("sizeOrDir="+sizeOrDir);
//								System.out.println("fileName="+fileName); 
//								System.out.println("dateStr="+dateStr);
								
								FtpFile ftpFile = new FtpFile();
								// 将FTP目录信息初始化到FTP文件对象中
								ftpFile.setLastDate(dateStr);
								ftpFile.setSize(sizeOrDir);
								ftpFile.setName(fileName);
								ftpFile.setPath(pwd);
								// 将文件信息添加到表格中
								model.addRow(new Object[] { ftpFile, ftpFile.getSize(),
										dateStr }); 
							}
							
//							j=i+1;//上一次位置为字符模式
							j=i+2;//上一次位置为二进制模式
						}
						i=i+1;
					}
					list.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		};
		if (SwingUtilities.isEventDispatchThread()) // 启动线程对象
			runnable.run();
		else
			SwingUtilities.invokeLater(runnable);
	}
	
	public String getPwd() {
		String pwd = null;
		try {
			pwd = ftpClient.pwd();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pwd;
	}
	public void refreshCurrentFolder() {
		try {
			 // 获取服务器文件列表
			TelnetInputStream list = ftpClient.list();
			fileList(list); // 调用解析方法
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
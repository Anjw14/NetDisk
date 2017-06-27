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
						new Object[][] {},	new String[] {"�ļ���", "��С", "����޸�����", "����", "�汾"}	)	);
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
//					ftpClient.connect("192.168.1.101", 21);// ����FTP������  
//					ftpClient.login("FtpUser", "Anjianwei");// ��½FTP������  
					
					String pwd = getPwd(); // ��ȡFTP�������ĵ�ǰ�ļ���
					
					byte[]names=new byte[2048];
					int bufsize=0;
					bufsize=list.read(names, 0, names.length);
					int i=0,j=0;
					while(i<bufsize){
						//�ַ�ģʽΪ10��������ģʽΪ13
//						if (names[i]==10) {
						if (names[i]==13) {
							//��ȡ�ַ��� -rwx------ 1 user group          57344 Apr 18 05:32 ��Ѷ����2013ʵϰ����ƸTST�Ƽ�ģ��.xls
							//�ļ����������п�ʼ������Ϊj,i-jΪ�ļ����ĳ��ȣ��ļ����������еĽ����±�Ϊi-1
							String fileMessage = new String(names,j,i-j);
							if(fileMessage.length() == 0){
								System.out.println("fileMessage.length() == 0");
								break;
							}
							//���տո�fileMessage��Ϊ������ȡ�����Ϣ
							// ������ʽ  \s��ʾ�ո񣬣�1������ʾ1һ������ 
							if(!fileMessage.split("\\s+")[8].equals(".") && !fileMessage.split("\\s+")[8].equals("..")){
								/**�ļ���С*/
								String sizeOrDir="";
								if (fileMessage.startsWith("d")) {//�����Ŀ¼
									sizeOrDir="<DIR>";
								}else if (fileMessage.startsWith("-")) {//������ļ�
									sizeOrDir=fileMessage.split("\\s+")[4];
								}
								/**�ļ���*/
								String fileName=fileMessage.split("\\s+")[8];
								/**�ļ�����*/
								String dateStr =fileMessage.split("\\s+")[5] +" "+fileMessage.split("\\s+")[6]+" " +fileMessage.split("\\s+")[7];
//								System.out.println("sizeOrDir="+sizeOrDir);
//								System.out.println("fileName="+fileName); 
//								System.out.println("dateStr="+dateStr);
								
								FtpFile ftpFile = new FtpFile();
								// ��FTPĿ¼��Ϣ��ʼ����FTP�ļ�������
								ftpFile.setLastDate(dateStr);
								ftpFile.setSize(sizeOrDir);
								ftpFile.setName(fileName);
								ftpFile.setPath(pwd);
								// ���ļ���Ϣ��ӵ������
								model.addRow(new Object[] { ftpFile, ftpFile.getSize(),
										dateStr }); 
							}
							
//							j=i+1;//��һ��λ��Ϊ�ַ�ģʽ
							j=i+2;//��һ��λ��Ϊ������ģʽ
						}
						i=i+1;
					}
					list.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		};
		if (SwingUtilities.isEventDispatchThread()) // �����̶߳���
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
			 // ��ȡ�������ļ��б�
			TelnetInputStream list = ftpClient.list();
			fileList(list); // ���ý�������
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
package UI;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;

import org.apache.commons.net.ftp.FTPFile;


/**
 *  FTP��Դ����������Ⱦ��
 */
public class FtpTableCellRanderer extends DefaultTableCellRenderer {
	private final ImageIcon folderIcon = new ImageIcon(getClass().getResource(
			"/OtherComponent/folderIcon.JPG")); // �ļ���ͼ��
	private final ImageIcon fileIcon = new ImageIcon(getClass().getResource(
			"/OtherComponent/fileIcon.JPG")); // �ļ�ͼ��
	private static FtpTableCellRanderer instance = null; // ��Ⱦ����ʵ������

	/**
	 * ����յĹ��췽��
	 */
	private FtpTableCellRanderer() {
	}

	/**
	 * ��ȡ��Ⱦ��ʵ������ķ���
	 * 
	 * @return ��Ⱦ����ʵ������
	 */
	public static FtpTableCellRanderer getCellRanderer() {
		if (instance == null)
			instance = new FtpTableCellRanderer();
		return instance;
	}

	/**
	 * ��д���ñ�����ݵķ���
	 */
	@Override
	protected void setValue(Object value) {
		FTPFile ftpFile = (FTPFile) value;
		try {
			File tempFile = File.createTempFile("temp", ftpFile.getName().toString());
        	Icon icon;
        	icon = FileSystemView.getFileSystemView().getSystemIcon(tempFile);
			// ��ȡ��ʱ�ļ���ͼ��
        	if(ftpFile.isDirectory()){
        		icon = new ImageIcon(getClass().getResource("/OtherComponent/folderIcon.JPG")); // �ļ���ͼ��
        	}
			tempFile.delete(); // ɾ����ʱ�ļ�
			setIcon(icon); // ���ñ��Ԫͼ��
			setText(ftpFile.getName());// �����ı�����
		} catch (IOException e) {
			e.printStackTrace();
			setIcon(fileIcon);
			setText(ftpFile.getName());
		}
	}
}

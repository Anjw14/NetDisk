package UI;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;

import org.apache.commons.net.ftp.FTPFile;


/**
 *  FTP资源表格组件的渲染器
 */
public class FtpTableCellRanderer extends DefaultTableCellRenderer {
	private final ImageIcon folderIcon = new ImageIcon(getClass().getResource(
			"/OtherComponent/folderIcon.JPG")); // 文件夹图标
	private final ImageIcon fileIcon = new ImageIcon(getClass().getResource(
			"/OtherComponent/fileIcon.JPG")); // 文件图标
	private static FtpTableCellRanderer instance = null; // 渲染器的实例对象

	/**
	 * 被封闭的构造方法
	 */
	private FtpTableCellRanderer() {
	}

	/**
	 * 获取渲染器实例对象的方法
	 * 
	 * @return 渲染器的实例对象
	 */
	public static FtpTableCellRanderer getCellRanderer() {
		if (instance == null)
			instance = new FtpTableCellRanderer();
		return instance;
	}

	/**
	 * 重写设置表格数据的方法
	 */
	@Override
	protected void setValue(Object value) {
		FTPFile ftpFile = (FTPFile) value;
		try {
			File tempFile = File.createTempFile("temp", ftpFile.getName().toString());
        	Icon icon;
        	icon = FileSystemView.getFileSystemView().getSystemIcon(tempFile);
			// 获取临时文件的图标
        	if(ftpFile.isDirectory()){
        		icon = new ImageIcon(getClass().getResource("/OtherComponent/folderIcon.JPG")); // 文件夹图标
        	}
			tempFile.delete(); // 删除临时文件
			setIcon(icon); // 设置表格单元图标
			setText(ftpFile.getName());// 设置文本内容
		} catch (IOException e) {
			e.printStackTrace();
			setIcon(fileIcon);
			setText(ftpFile.getName());
		}
	}
}

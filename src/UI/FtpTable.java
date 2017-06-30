package UI;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.net.ftp.FTPFile;

import Sql.MySql;


public class FtpTable {

	public JTable table = new JTable();
	public FTPFile[] ftpFiles;
	public FTPFile selectedFile;
	public MainInterface mainInterface;
	public MySql mySql = new MySql();
	
	private String size; // �ļ���С
	private long longSize; // �ļ���С�ĳ���������
	private final int GB = (int) Math.pow(1024, 3); // GB��λ��ֵ
	private final int MB = (int) Math.pow(1024, 2); // MB��λ��ֵ
	private final int KB = 1024; // KB��λ��ֵ
	
	public FtpTable(FileTree fileTree, final MainInterface mainInterface) throws IOException, SQLException{
		this.mainInterface = mainInterface;
		table.setShowGrid(false);
		table.setModel(	
				new DefaultTableModel( 
						new Object[][] {},	new String[] {"�ļ���", "��С", "����޸�����", "����", "�汾"}	)	);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(4).setPreferredWidth(5);
		table.getColumnModel().getColumn(0).setCellRenderer(FtpTableCellRanderer.getCellRanderer());
		
		if(! mySql.isNoted("history"))
			mySql.addFileNote("history", "history", "");
		mainInterface.historyTextArea.setText(mySql.getNote("history"));
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				mainInterface.noteTextArea.setEditable(false);
				if(table.getSelectedRow() != -1){
					selectedFile = (FTPFile) table.getModel().getValueAt(table.getSelectedRow(), 0);
					if(selectedFile.isDirectory())
						mainInterface.downloadButton.setEnabled(false);
					else{
						mainInterface.downloadButton.setEnabled(true);
						mainInterface.propertiesTextArea.setText("\n    �ļ�����"+selectedFile.getName()+"\n\n    �ļ��汾��v1.0\n\n ");
						try {
							if(mySql.isNoted(mainInterface.pathTextField.getText()))
								mainInterface.noteTextArea.setText(mySql.getNote(mainInterface.pathTextField.getText()));
							else
								mainInterface.noteTextArea.setText("");
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
	}

	
	
	
	/**
	 * @param fileTree
	 * @throws IOException 
	 */
	public void tableFlush(FileTree fileTree) throws IOException {
		ftpFiles = fileTree.tableFtpFiles;
		DefaultTableModel model = ( DefaultTableModel) table.getModel();
		model.setRowCount(0);
		DecimalFormat decimalFormatter = new DecimalFormat("0.0");
		SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(FTPFile file : ftpFiles){
			if(file.isDirectory())
				size = "<DIR>";
			else{
				longSize = file.getSize();
				if (longSize > GB) 
					size = decimalFormatter.format((float)longSize / GB) + " GB ";
				else if (longSize > MB) 
					size = decimalFormatter.format((float)longSize / MB) + " MB ";
				else if (longSize > KB) 
					size = decimalFormatter.format((float)longSize / KB) + " KB ";
				else
					size = (float)longSize + " B ";
			}
			model.addRow(new Object[] { file, size, dataFormatter.format(file.getTimestamp().getTime()) }); 
		}
	}
}


/**
 *  FTP��Դ����������Ⱦ��
 */
class FtpTableCellRanderer extends DefaultTableCellRenderer {
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

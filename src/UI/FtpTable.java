package UI;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.net.ftp.FTPFile;


public class FtpTable {

	public JTable table = new JTable();
	public FTPFile[] ftpFiles;
	public FTPFile selectedFile;
	public MainInterface mainInterface;
	
	private String size; // 文件大小
	private long longSize; // 文件大小的长整型类型
	private final int GB = (int) Math.pow(1024, 3); // GB单位数值
	private final int MB = (int) Math.pow(1024, 2); // MB单位数值
	private final int KB = 1024; // KB单位数值
	
	public FtpTable(FileTree fileTree, final MainInterface mainInterface) throws IOException{
		this.mainInterface = mainInterface;
		table.setShowGrid(false);
		table.setModel(	
				new DefaultTableModel( 
						new Object[][] {},	new String[] {"文件名", "大小", "最后修改日期", "作者", "版本"}	)	);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(4).setPreferredWidth(5);
		table.getColumnModel().getColumn(0).setCellRenderer(FtpTableCellRanderer.getCellRanderer());
		
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				if(table.getSelectedRow() != -1){
					selectedFile = (FTPFile) table.getModel().getValueAt(table.getSelectedRow(), 0);
					if(selectedFile.isDirectory())
						mainInterface.downloadButton.setEnabled(false);
					else
						mainInterface.downloadButton.setEnabled(true);
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
		SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
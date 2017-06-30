package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.net.ftp.FTPFile;

import Ftp.ListAllFiles;
import Ftp.ListPointedFile;

public class FileTree extends JTree {
	public MainInterface mainInterface;
	public TreePath mouseInPath;
	public String selectedPath;
	public String selectedDirPath;
	public FileTreeModel model;
	protected FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	// public ListAllFiles f = new ListAllFiles(false);
	// public ListPointedFile listFile = new ListPointedFile(false, "");
	public FTPFile[] ftpCurrentFile;
	public FTPFile[] tableFtpFiles;
	
	public FileTree(final MainInterface mainInterface) throws IOException {
		this.mainInterface = mainInterface;
		final Login login = this.mainInterface.login;
		final ListPointedFile listFile = new ListPointedFile(false, "", login);
		final ListAllFiles f = new ListAllFiles(false, this.mainInterface.login);
		model = new FileTreeModel(new DefaultMutableTreeNode(new FileNode("Ftp", null, null, true)), this);
		this.setModel(model);
		this.setCellRenderer(new FileTreeRenderer());

		setRootVisible(false);

		// 实现JTree效果
		addTreeWillExpandListener(new TreeWillExpandListener() {
			@Override
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				DefaultMutableTreeNode lastTreeNode = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
				FileNode fileNode = (FileNode) lastTreeNode.getUserObject();
				try {
					if (!fileNode.isInit) {
						FTPFile[] files;
						if (fileNode.isDummyRoot) {
							files = f.ftpFiles;
						} else {
							String name = fileNode.name;
							listFile.List("");
							listFile.ListSubFile("/" + name + "/", login);
							files = listFile.ftpFinalFile;
						}
						for (int i = 0; i < files.length; i++) {

							File tempFile = File.createTempFile("temp", files[i].getName().toString());
							Icon icon;
							icon = FileSystemView.getFileSystemView().getSystemIcon(tempFile);
							// 获取临时文件的图标
							if (files[i].isDirectory())
								icon = new ImageIcon(getClass().getResource("/OtherComponent/folderIcon.JPG")); // 文件夹图标
							tempFile.delete(); // 删除临时文件

							FileNode childFileNode = new FileNode(files[i].getName(), icon, files[i], false);
							DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(childFileNode);
							lastTreeNode.add(childTreeNode);
						}
						// 通知模型节点发生变化
						DefaultTreeModel treeModel1 = (DefaultTreeModel) getModel();
						treeModel1.nodeStructureChanged(lastTreeNode);
					}
				} catch (IOException e) {

					e.printStackTrace();
				}
				// 更改标识，避免重复加载
				fileNode.isInit = true;
			}

			@Override
			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

			}
		});

		// 获得鼠标选中的文件路径
		addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				
				mainInterface.noteTextArea.setText("");
				
				DefaultMutableTreeNode lastTreeNode = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				FileNode selectNode = (FileNode) lastTreeNode.getUserObject();

				String name = selectNode.name;
				try {
					listFile.List("");
					listFile.ListSubFile("/" + name + "/", login);
					String path = selectNode.file.isDirectory() ? listFile.selectPath + "/" : listFile.selectPath;
					selectedPath = path;
					selectedDirPath = listFile.selectDirPath;
					mainInterface.pathTextField.setText("Ftp:/" + path);
					tableFtpFiles = listFile.ftpFinalFile;
					mainInterface.ftpTable.tableFlush(mainInterface.tree);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 获得鼠标所在的TreePath
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				TreePath path = getPathForLocation(e.getX(), e.getY());

				// 计算repaint的区域并repaint JTree
				if (path != null) {
					if (mouseInPath != null) {
						Rectangle oldRect = getPathBounds(mouseInPath);
						mouseInPath = path;
						repaint(getPathBounds(path).union(oldRect));
					} else {
						mouseInPath = path;
						Rectangle bounds = getPathBounds(mouseInPath);
						repaint(bounds);
					}
				} else if (mouseInPath != null) {
					Rectangle oldRect = getPathBounds(mouseInPath);
					mouseInPath = null;
					repaint(oldRect);
				}
			}
		});

		new Thread(new TreeFlushThread(this, false)).start();
	}
}

class FileNode {
	public FileNode(String name, Icon icon, FTPFile file, boolean isDummyRoot) {
		this.name = name;
		this.icon = icon;
		this.file = file;
		this.isDummyRoot = isDummyRoot;
	}

	public boolean isInit;
	public boolean isDummyRoot;
	public String name;
	public Icon icon;
	public FTPFile file;
}

class FileTreeRenderer extends DefaultTreeCellRenderer {
	public FileTreeRenderer() {
	}

	@Override
	public Component getTreeCellRendererComponent(javax.swing.JTree tree, java.lang.Object value, boolean sel,
			boolean expanded, boolean leaf, int row, boolean hasFocus) {

		FileTree fileTree = (FileTree) tree;
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		FileNode fileNode = (FileNode) node.getUserObject();
		label.setText(fileNode.name);
		label.setIcon(fileNode.icon);

		label.setOpaque(false);
		// 如果当前渲染的节点就是鼠标滑过的节点，改变背景色
		if (fileTree.mouseInPath != null && fileTree.mouseInPath.getLastPathComponent().equals(value)) {
			label.setOpaque(true);
			label.setBackground(new Color(0, 250, 154, 90));
		}
		return label;
	}
}

class FileTreeModel extends DefaultTreeModel {

	public FTPFile[] files;
	
	public FileTreeModel(TreeNode root, FileTree fileTree) throws IOException {
		super(root);
		FileSystemView fileSystemView = FileSystemView.getFileSystemView();

		// File[] files=fileSystemView.getRoots();

		// File[] files = (new File("F:/Share")).listFiles();
		// for (int i = 0; i < files.length; i++) {
		// FileNode childFileNode = new
		// FileNode(fileSystemView.getSystemDisplayName(files[i]),
		// fileSystemView.getSystemIcon(files[i]), files[i], false);
		// DefaultMutableTreeNode childTreeNode = new
		// DefaultMutableTreeNode(childFileNode);
		// ((DefaultMutableTreeNode)root).add(childTreeNode);
		// }

		ListAllFiles f = new ListAllFiles(false, fileTree.mainInterface.login);
		files = f.ftpFiles;
		for (int i = 0; i < files.length; i++) {

			// System.out.println(files[i].getName());
			// InputStream iStream=f.ftp.retrieveFileStream("chap01.pdf");
			// File file = File.createTempFile("tmp", null);
			// FileUtils.copyInputStreamToFile(iStream, file);

			if (files[i].isDirectory()) {
				Icon icon = new ImageIcon(getClass().getResource("/OtherComponent/folderIcon.JPG")); // 文件夹图标
				FileNode childFileNode = new FileNode(files[i].getName().toString(), icon, files[i], false);
				DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(childFileNode);
				((DefaultMutableTreeNode) root).add(childTreeNode);
			} else {
				File tempFile = File.createTempFile("temp", files[i].getName().toString());
				// 获取临时文件的图标
				Icon icon = FileSystemView.getFileSystemView().getSystemIcon(tempFile);
				FileNode childFileNode = new FileNode(files[i].getName().toString(), icon, files[i], false);
				DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(childFileNode);
				((DefaultMutableTreeNode) root).add(childTreeNode);
				tempFile.delete(); // 删除临时文件
			}

		}
	}

	@Override
	public boolean isLeaf(Object node) {
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
		FileNode fileNode = (FileNode) treeNode.getUserObject();
		if (fileNode.isDummyRoot)
			return false;
		return fileNode.file.isFile();
	}
}


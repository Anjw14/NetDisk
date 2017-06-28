package UI;

import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import Ftp.ListAllFiles;

public class TreeFlushThread implements Runnable {
	
	private FileTree tree;
	private Boolean isInstant;
	
	public TreeFlushThread(FileTree tree, Boolean isInstant) {
		this.tree = tree;
		this.isInstant = isInstant;
	}

	@Override
	public void run() {
		
		try {
			if(isInstant){
				System.out.println("3333333333333333");
				this.tree.model = new FileTreeModel(new DefaultMutableTreeNode(new FileNode("Ftp", null, null, true)), tree);
				this.tree.setModel(this.tree.model);
			}else{
				while(true){
					Thread.sleep(10*1000);
					ListAllFiles f = new ListAllFiles(false, this.tree.mainInterface.login);
					if(tree.model.files.length==f.ftpFiles.length){
						System.out.println("111111111111111");
					}else{
						System.out.println("222222222222222");
						this.tree.model = new FileTreeModel(new DefaultMutableTreeNode(new FileNode("Ftp", null, null, true)), tree);
						this.tree.setModel(this.tree.model);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

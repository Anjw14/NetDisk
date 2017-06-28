package UI;

import java.io.IOException;

public class FlushThread implements Runnable {
	
	private MainInterface MainInterface;
	
	public FlushThread(MainInterface mainInterface){
		this.MainInterface = mainInterface;
	}

	@Override
	public void run() {
		try {
			while(true){
				Thread.sleep(10*1000);
				this.MainInterface.tree = new FileTree(this.MainInterface);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

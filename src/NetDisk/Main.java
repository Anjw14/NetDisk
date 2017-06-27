package NetDisk;

import java.awt.EventQueue;
import java.net.UnknownHostException;
import javax.swing.UIManager;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import UI.Login;

public class Main {

	public static void main(String[] args) throws UnknownHostException {
		Login login = new Login();
		login.loginRun();
	}
}

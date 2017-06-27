package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.sun.java.swing.plaf.nimbus.*;

import java.awt.SystemColor;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JScrollBar;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

public class MainInterface extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField userDisplayTextField;
	private JTextField ftpDisplayTextField;
	
	public JTextField pathTextField;
	public FtpTable ftpTable;
	public FileTree tree;
	public Login login;
	public JButton downloadButton;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		MainInterface mainUI = new MainInterface();
//		mainUI.mainInterfaceRun();
//	}

	/**
	 * 
	 */
	public void mainInterfaceRun() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
//					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					UIManager.setLookAndFeel(new NimbusLookAndFeel());//设置一个非常漂亮的外观
					MainInterface frame = new MainInterface(login);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainInterface(Login login) throws IOException {
		
		this.login = login; 
		
		setTitle("NetDisk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 914, 541);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem findFolder = new JMenuItem("Find Folder");
		fileMenu.add(findFolder);
		
		JMenuItem findDocument = new JMenuItem("Find Document");
		fileMenu.add(findDocument);
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{874, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GridBagConstraints gbc_splitPane_5 = new GridBagConstraints();
		gbc_splitPane_5.fill = GridBagConstraints.BOTH;
		gbc_splitPane_5.gridx = 0;
		gbc_splitPane_5.gridy = 0;
		contentPane.add(splitPane_5, gbc_splitPane_5);
		
		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setBackground(SystemColor.menu);
		splitPane_5.setLeftComponent(splitPane_4);
		splitPane_4.setDividerSize(0);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setEnabled(false);
		menuBar_1.setBackground(SystemColor.menu);
		splitPane_4.setRightComponent(menuBar_1);
		
		JMenu mnNewMenu = new JMenu("\u5907\u6CE8");
		mnNewMenu.setBackground(Color.WHITE);
		menuBar_1.add(mnNewMenu);
		
		JMenuItem menuItem_1 = new JMenuItem("\u4FEE\u6539\u5907\u6CE8");
		mnNewMenu.add(menuItem_1);
		
		JMenuItem menuItem = new JMenuItem("\u65B0\u5EFA\u5907\u6CE8");
		mnNewMenu.add(menuItem);
		
		JMenu menu = new JMenu("\u67E5\u627E");
		menu.setBackground(Color.WHITE);
		menuBar_1.add(menu);
		
		JMenuItem menuItem_2 = new JMenuItem("\u67E5\u627E\u6587\u4EF6\u5939");
		menu.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("\u67E5\u627E\u6587\u4EF6");
		menu.add(menuItem_3);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		splitPane_4.setLeftComponent(toolBar);
		
		JButton button = new JButton("\u4E0A\u4F20");
		toolBar.add(button);
		
		downloadButton = new JButton("\u4E0B\u8F7D");
		downloadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("666");
				
				
				
			}
		});
		downloadButton.setEnabled(false);
		toolBar.add(downloadButton);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane_5.setRightComponent(splitPane);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{53, 74, 85, 0, 0};
		gbl_panel.rowHeights = new int[] {1, 1};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel label = new JLabel("\u767B\u5F55\u540D");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		
		userDisplayTextField = new JTextField();
		userDisplayTextField.setHorizontalAlignment(SwingConstants.LEFT);
		userDisplayTextField.setEditable(false); 
		userDisplayTextField.setText(login.name);
		GridBagConstraints gbc_userDisplayTextField = new GridBagConstraints();
		gbc_userDisplayTextField.anchor = GridBagConstraints.EAST;
		gbc_userDisplayTextField.insets = new Insets(0, 0, 0, 5);
		gbc_userDisplayTextField.gridx = 1;
		gbc_userDisplayTextField.gridy = 0;
		panel.add(userDisplayTextField, gbc_userDisplayTextField);
		userDisplayTextField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u4E3B\u7AD9\u5730\u5740");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 0;
		panel.add(label_1, gbc_label_1);
		
		ftpDisplayTextField = new JTextField();
		ftpDisplayTextField.setHorizontalAlignment(SwingConstants.LEFT);
		ftpDisplayTextField.setEditable(false);
		ftpDisplayTextField.setText(login.ipAddress);
		GridBagConstraints gbc_ftpDisplayTextField = new GridBagConstraints();
		gbc_ftpDisplayTextField.anchor = GridBagConstraints.EAST;
		gbc_ftpDisplayTextField.gridx = 3;
		gbc_ftpDisplayTextField.gridy = 0;
		panel.add(ftpDisplayTextField, gbc_ftpDisplayTextField);
		ftpDisplayTextField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{859, 0};
		gbl_panel_1.rowHeights = new int[]{409, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.4);
		splitPane_1.setDividerLocation(0.5);
		splitPane_1.setOneTouchExpandable(true);
		splitPane_1.setDividerLocation(0.5);
		GridBagConstraints gbc_splitPane_1 = new GridBagConstraints();
		gbc_splitPane_1.weighty = 1.0;
		gbc_splitPane_1.weightx = 1.0;
		gbc_splitPane_1.fill = GridBagConstraints.BOTH;
		gbc_splitPane_1.gridx = 0;
		gbc_splitPane_1.gridy = 0;
		panel_1.add(splitPane_1, gbc_splitPane_1);
		
		JPanel panel_3 = new JPanel();
		splitPane_1.setRightComponent(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_3.add(splitPane_2);
		
		JPanel panel_4 = new JPanel();
		splitPane_2.setLeftComponent(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		pathTextField = new JTextField();
		panel_4.add(pathTextField);
		pathTextField.setColumns(10);
		pathTextField.setEditable(false);
		
		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setResizeWeight(0.7);
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setRightComponent(splitPane_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_3.setLeftComponent(scrollPane_1);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane_3.setRightComponent(tabbedPane);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("Properties", null, scrollPane_2, null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		tabbedPane.addTab("Notes", null, scrollPane_3, null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		tabbedPane.addTab("History", null, scrollPane_4, null);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		tabbedPane.addTab("Preview", null, scrollPane_5, null);
		
		JPanel panel_2 = new JPanel();
		splitPane_1.setLeftComponent(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{108, 0};
		gbl_panel_2.rowHeights = new int[]{15, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("\u6587\u4EF6");
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewJgoodiesTitle = new GridBagConstraints();
		gbc_lblNewJgoodiesTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewJgoodiesTitle.gridx = 0;
		gbc_lblNewJgoodiesTitle.gridy = 0;
		panel_2.add(lblNewJgoodiesTitle, gbc_lblNewJgoodiesTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_2.add(scrollPane, gbc_scrollPane);

		/*******************JTree********************/
		
		tree = new FileTree(this);
		scrollPane.setViewportView(tree);

		/*******************JTable*******************/
		
		ftpTable = new FtpTable(tree,this);
		table = ftpTable.table;
		scrollPane_1.setViewportView(table);
		
	}
}




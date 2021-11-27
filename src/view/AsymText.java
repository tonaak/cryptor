package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JButton;
import javax.crypto.Cipher;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class AsymText extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int xx, yy;
	private JTextField filePath;
	private JTextField modetxt;
	private int stateMode = 1;
	private String encMode = "Encrypt";
	private String decMode = "Decrypt";
	private String mode = encMode;
	
	private static Base64.Encoder encoder = Base64.getEncoder();
	private static Base64.Decoder decoder = Base64.getDecoder();

	public static String encrypt(String text, PublicKey publicKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] plainText = text.getBytes("UTF-8");
		byte[] cipherText = cipher.doFinal(plainText);
		
		cipherText = encoder.encode(cipherText);
		return new String(cipherText);
	}
	
	public static String decrypt(String text, PrivateKey key) throws Exception{
		byte[] cipherText = decoder.decode(text);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(cipherText);
		String result = new String(plainText, "UTF-8");
		return result;
	}
	
	public static PrivateKey readPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		String privateString = new String(bytes, StandardCharsets.UTF_8);
		bytes = decoder.decode(privateString);
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(ks);
	}
	
	public static PublicKey readPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		String publicString = new String(bytes, StandardCharsets.UTF_8);
		bytes = decoder.decode(publicString);
		X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(ks);
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AsymText frame = new AsymText();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AsymText() {
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 128, 0), 1, true));
		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				yy = e.getY();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evt) {
				setLocation(evt.getXOnScreen() - xx, evt.getYOnScreen() - yy);
			}
		});
		panel.setBackground(new Color(0, 128, 0));
		panel.setBounds(0, 0, 800, 162);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnExit = new JButton("X");
		btnExit.setMargin(new Insets(0, 0, 0, 0));
		btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFocusPainted(false);
		btnExit.setBorderPainted(false);
		btnExit.setBackground(new Color(0, 128, 0));
		btnExit.setForeground(Color.WHITE);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 20));

		btnExit.setBounds(764, 0, 36, 40);
		panel.add(btnExit);

		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(AsymText.class.getResource("/image/encrypted-data.png")));
		logo.setBounds(101, 11, 143, 140);
		panel.add(logo);

		JLabel title = new JLabel("ASYMMETRIC");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 29));
		title.setBounds(246, 39, 325, 91);
		panel.add(title);

		JButton btnReturn = new JButton("");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Asym sym = new Asym();
				sym.setVisible(true);
				// delay dispose
				Timer timer = new Timer( 30, new ActionListener(){
					public void actionPerformed( ActionEvent e ){
						dispose();
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
		btnReturn.setIcon(new ImageIcon(AsymText.class.getResource("/image/return.png")));
		btnReturn.setMargin(new Insets(0, 0, 0, 0));
		btnReturn.setForeground(Color.WHITE);
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnReturn.setFocusPainted(false);
		btnReturn.setBorderPainted(false);
		btnReturn.setBackground(new Color(0, 128, 0));
		btnReturn.setBounds(0, 0, 46, 40);
		panel.add(btnReturn);

		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(Color.WHITE);
		panelMenu.setBorder(new LineBorder(new Color(0, 100, 0)));
		panelMenu.setBounds(0, 162, 800, 438);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);

		JTextArea plainInput = new JTextArea();
		plainInput.setMargin(new Insets(0, 4, 0, 4));
		plainInput.setLineWrap(true);
		plainInput.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JScrollPane sp = new JScrollPane(plainInput);
		sp.setBackground(Color.WHITE);
		sp.setBorder(new TitledBorder(new LineBorder(new Color(0, 100, 0)), "Plain Text", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 100, 0)));
		sp.setBounds(37, 210, 317, 176);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelMenu.add(sp);

		JScrollPane sp_1 = new JScrollPane((Component) null);
		sp_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 100, 0)), "Cipher Text", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 100, 0)));
		sp_1.setBackground(Color.WHITE);
		sp_1.setBounds(447, 210, 317, 176);
		panelMenu.add(sp_1);

		JTextArea cipherInput = new JTextArea();
		cipherInput.setEditable(false);
		cipherInput.setMargin(new Insets(0, 4, 0, 4));
		cipherInput.setLineWrap(true);
		cipherInput.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sp_1.setViewportView(cipherInput);

		JComboBox<Object> algoSelect = new JComboBox<Object>();
		algoSelect.setModel(new DefaultComboBoxModel<Object>(new String[] {"RSA"}));
		algoSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		algoSelect.setBorder(null);
		algoSelect.setBackground(Color.WHITE);
		algoSelect.setBounds(39, 34, 313, 29);
		panelMenu.add(algoSelect);

		JLabel algo = new JLabel("Algorithms:");
		algo.setForeground(new Color(0, 100, 0));
		algo.setFont(new Font("Tahoma", Font.BOLD, 12));
		algo.setBackground(Color.WHITE);
		algo.setBounds(39, 11, 88, 22);
		panelMenu.add(algo);

		filePath = new JTextField();
		filePath.setText("");
		filePath.setEditable(false);
		filePath.setColumns(10);
		filePath.setBackground(Color.WHITE);
		filePath.setBounds(39, 93, 613, 29);
		panelMenu.add(filePath);

		JLabel keyFile = new JLabel("Key File ( Public key ) :");
		keyFile.setForeground(new Color(0, 100, 0));
		keyFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		keyFile.setBackground(Color.WHITE);
		keyFile.setBounds(39, 68, 164, 22);
		panelMenu.add(keyFile);

		JButton browse = new JButton("Browse");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int option = chooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION){
					File f = chooser.getSelectedFile();
					String filename = f.getAbsolutePath();
					filePath.setText(filename);
				}else{
					filePath.setText("No file selected");
				}
			}
		});
		browse.setForeground(Color.WHITE);
		browse.setFont(new Font("Tahoma", Font.BOLD, 12));
		browse.setFocusPainted(false);
		browse.setBackground(new Color(0, 128, 0));
		browse.setBounds(662, 92, 100, 29);
		panelMenu.add(browse);

		modetxt = new JTextField();
		modetxt.setMargin(new Insets(2, 5, 2, 2));
		modetxt.setForeground(new Color(128, 0, 0));
		modetxt.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
		modetxt.setText("Encrypt");
		modetxt.setBorder(null);
		modetxt.setEditable(false);
		modetxt.setColumns(10);
		modetxt.setBackground(Color.WHITE);
		modetxt.setBounds(449, 34, 109, 29);
		panelMenu.add(modetxt);

		JLabel lbMode = new JLabel("Current Action:");
		lbMode.setForeground(new Color(0, 100, 0));
		lbMode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbMode.setBackground(Color.WHITE);
		lbMode.setBounds(449, 9, 100, 22);
		panelMenu.add(lbMode);

		JButton changeBtn = new JButton("");
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plainInput.setText("");
				cipherInput.setText("");
				stateMode = 1 - stateMode;
				if(stateMode == 1) {
					mode = encMode;
					plainInput.setEditable(true);
					cipherInput.setEditable(false);
					keyFile.setText("Key File ( Public key ) :");
				} else {
					mode = decMode;
					cipherInput.setEditable(true);
					plainInput.setEditable(false);
					keyFile.setText("Key File ( Private key ) :");
				}
				modetxt.setText(mode);
			}
		});

		changeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				changeBtn.setBackground(new Color(152, 251, 152));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				changeBtn.setBackground(SystemColor.menu);
			}
		});
		changeBtn.setFocusPainted(false);
		changeBtn.setBorder(null);
		changeBtn.setBackground(SystemColor.menu);
		changeBtn.setMargin(new Insets(2, 0, 2, 0));
		changeBtn.setIcon(new ImageIcon(AsymText.class.getResource("/image/swap.png")));
		changeBtn.setBounds(600, 11, 52, 52);
		panelMenu.add(changeBtn);

		String[] modeList = new String[] {"ECB"};
		String[] paddingList = new String[] {"PKCS1Padding"};

		JComboBox<Object> modeSelect = new JComboBox<Object>();
		modeSelect.setModel(new DefaultComboBoxModel<Object>(modeList));
		modeSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modeSelect.setBorder(null);
		modeSelect.setBackground(Color.WHITE);
		modeSelect.setBounds(39, 156, 313, 29);
		panelMenu.add(modeSelect);

		JLabel modelb = new JLabel("Mode:");
		modelb.setForeground(new Color(0, 100, 0));
		modelb.setFont(new Font("Tahoma", Font.BOLD, 12));
		modelb.setBackground(Color.WHITE);
		modelb.setBounds(39, 133, 88, 22);
		panelMenu.add(modelb);

		JComboBox<Object> paddingSelect = new JComboBox<Object>();
		paddingSelect.setModel(new DefaultComboBoxModel<Object>(paddingList));
		paddingSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		paddingSelect.setBorder(null);
		paddingSelect.setBackground(Color.WHITE);
		paddingSelect.setBounds(449, 156, 313, 29);
		panelMenu.add(paddingSelect);

		JLabel paddinglb = new JLabel("Padding:");
		paddinglb.setForeground(new Color(0, 100, 0));
		paddinglb.setFont(new Font("Tahoma", Font.BOLD, 12));
		paddinglb.setBackground(Color.WHITE);
		paddinglb.setBounds(449, 133, 88, 22);
		panelMenu.add(paddinglb);

		algoSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		modeSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		JButton startBtn = new JButton("");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyFilePath = filePath.getText();

				if(keyFilePath.equalsIgnoreCase("") || keyFilePath.equalsIgnoreCase("No file selected")) {
					JOptionPane.showMessageDialog(contentPane, "Please select a key file!");
				} else {
					if (mode == encMode) {
						String text = plainInput.getText();
						try {
							PublicKey publicKey = readPublicKey(keyFilePath);
							String cipherText = encrypt(text, publicKey);
							cipherInput.setText(cipherText);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(contentPane, "Process failed. Check your key file, your input and the encrypt mode");
						}
					} else {
						String text = cipherInput.getText();
						try {
							PrivateKey privateKey = readPrivateKey(keyFilePath);
							String plainText = decrypt(text, privateKey);
							plainInput.setText(plainText);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(contentPane, "Process failed. Check your key file, your input and the decrypt mode");
						}
					}
				}
			}
		});
		startBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startBtn.setBackground(new Color(152, 251, 152));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				startBtn.setBackground(Color.WHITE);
			}
		});
		startBtn.setFocusPainted(false);
		startBtn.setIcon(new ImageIcon(AsymText.class.getResource("/image/start.png")));
		startBtn.setBorder(null);
		startBtn.setActionCommand("");
		startBtn.setBackground(Color.WHITE);
		startBtn.setBounds(364, 216, 73, 52);
		panelMenu.add(startBtn);
	}
}

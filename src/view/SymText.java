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
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class SymText extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int xx, yy;
	private JTextField filePath;
	private JTextField modetxt;
	private int stateMode = 1;
	private String encMode = "Encrypt";
	private String decMode = "Decrypt";
	private String mode = encMode;

	private static Clipboard getSystemClipboard()
    {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        return defaultToolkit.getSystemClipboard();
    }
	public static void copy(String text)
    {
        Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new StringSelection(text), null);
    }
	
	public static String encrypt(String text, String algorithm, String keyFile, String mode, String padding) throws Exception {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(keyFile));
		byte[] originalBytes = Base64.getDecoder().decode(fileContent);
		SecretKey key = new SecretKeySpec(originalBytes, 0, originalBytes.length, algorithm);

		Cipher cipher = Cipher.getInstance(algorithm + mode + padding);

		if(mode.equalsIgnoreCase("/ECB") || algorithm.equalsIgnoreCase("RC4")) {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} else if(mode.equalsIgnoreCase("")) {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} else {
			if(algorithm.equalsIgnoreCase("AES") ||
					algorithm.equalsIgnoreCase("RC6") ||
					algorithm.equalsIgnoreCase("CAST6") ||
					algorithm.equalsIgnoreCase("Serpent") ||
					algorithm.equalsIgnoreCase("Twofish")) {
				cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));
			}
		}

		byte[] plainText = text.getBytes("UTF-8");
		byte[] cipherText = cipher.doFinal(plainText);

		cipherText = Base64.getEncoder().encode(cipherText);
		return new String(cipherText);
	}

	public static String decrypt(String text, String algorithm, String keyFile, String mode, String padding) throws Exception {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(keyFile));
		byte[] originalBytes = Base64.getDecoder().decode(fileContent);
		SecretKey key = new SecretKeySpec(originalBytes, 0, originalBytes.length, algorithm);

		byte[] textByte = Base64.getDecoder().decode(text.getBytes());
		
		Cipher cipher = Cipher.getInstance(algorithm + mode + padding);

		if(mode.equalsIgnoreCase("/ECB") || algorithm.equalsIgnoreCase("RC4")) {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} else if(mode.equalsIgnoreCase("")) {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} else {
			if(algorithm.equalsIgnoreCase("AES")||
					algorithm.equalsIgnoreCase("RC6") ||
					algorithm.equalsIgnoreCase("CAST6") ||
					algorithm.equalsIgnoreCase("Serpent") ||
					algorithm.equalsIgnoreCase("Twofish")) {
				cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
			} else {
				cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));
			}
		}

		byte[] plainText = cipher.doFinal(textByte);
		return new String(plainText, "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SymText frame = new SymText();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SymText() {
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
		logo.setIcon(new ImageIcon(SymText.class.getResource("/image/encrypted-data.png")));
		logo.setBounds(101, 11, 143, 140);
		panel.add(logo);

		JLabel title = new JLabel("SYMMETRIC");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 29));
		title.setBounds(246, 39, 325, 91);
		panel.add(title);

		JButton btnReturn = new JButton("");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sym sym = new Sym();
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
		btnReturn.setIcon(new ImageIcon(SymText.class.getResource("/image/return.png")));
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
		algoSelect.setModel(new DefaultComboBoxModel<Object>(new String[] {"AES", "DES", "DESede", "Blowfish", "RC2", "RC4", "RC5", "RC6",
				"CAST5", "CAST6", "IDEA", "Serpent", "Skipjack", "Twofish"}));
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

		JLabel keyFile = new JLabel("Key File:");
		keyFile.setForeground(new Color(0, 100, 0));
		keyFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		keyFile.setBackground(Color.WHITE);
		keyFile.setBounds(39, 68, 88, 22);
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
				} else {
					mode = decMode;
					cipherInput.setEditable(true);
					plainInput.setEditable(false);
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
		changeBtn.setIcon(new ImageIcon(SymText.class.getResource("/image/swap.png")));
		changeBtn.setBounds(600, 11, 52, 52);
		panelMenu.add(changeBtn);

		String[] emptyMode = new String[] {""};
		String[] noPadding = new String[] {"NoPadding"};
		String[] emptyPadding = new String[] {""};
		String[] modeList = new String[] {"NONE", "ECB", "CBC", "PCBC", "CTR", "CFB", "CFB8", "CFB64", "OFB", "OFB8", "OFB64"};
		String[] paddingList = new String[] {"PKCS5Padding", "ISO10126Padding"};
		String[] bouncymodeList = new String[] {"NONE", "ECB", "CBC", "CTR", "CFB", "CFB8", "CFB64", "OFB", "OFB8", "OFB64"};

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
		paddingSelect.setModel(new DefaultComboBoxModel<Object>(emptyPadding));
		paddingSelect.setEnabled(false);
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
				String[] values = {"RC5","RC6","CAST5","CAST6","Serpent","Twofish","Skipjack","IDEA"};
				boolean contains = Arrays.stream(values).anyMatch(algoSelect.getSelectedItem()::equals);

				if(algoSelect.getSelectedItem().equals("RC4")) {
					modeSelect.setModel(new DefaultComboBoxModel<Object>(emptyMode));
					modeSelect.setEnabled(false);
					paddingSelect.setModel(new DefaultComboBoxModel<Object>(emptyPadding));
					paddingSelect.setEnabled(false);
				} else if(contains){ 
					modeSelect.setModel(new DefaultComboBoxModel<Object>(bouncymodeList));
					modeSelect.setEnabled(true);
					paddingSelect.setModel(new DefaultComboBoxModel<Object>(emptyPadding));
					paddingSelect.setEnabled(false);
				} else {
					modeSelect.setModel(new DefaultComboBoxModel<Object>(modeList));
					modeSelect.setEnabled(true);
					paddingSelect.setModel(new DefaultComboBoxModel<Object>(emptyPadding));
					paddingSelect.setEnabled(false);
				} 
			}
		});

		modeSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modeSelect.getSelectedItem().equals("CTR")) {
					paddingSelect.setModel(new DefaultComboBoxModel<Object>(noPadding));
					paddingSelect.setEnabled(true);
				} else if (modeSelect.getSelectedItem().equals("NONE")) {
					paddingSelect.setModel(new DefaultComboBoxModel<Object>(emptyPadding));
					paddingSelect.setEnabled(false);
				} else {
					paddingSelect.setModel(new DefaultComboBoxModel<Object>(paddingList));
					paddingSelect.setEnabled(true);
				}
			}
		});

		JButton startBtn = new JButton("");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String algorithm = (String) algoSelect.getSelectedItem();
				String keyFilePath = filePath.getText();
				String modeCrypt = "/" + (String) modeSelect.getSelectedItem();
				String padding = "/" + (String) paddingSelect.getSelectedItem();

				if (modeCrypt.equalsIgnoreCase("/NONE")) {
					modeCrypt = "";
					padding = "";
				}

				if(keyFilePath.equalsIgnoreCase("") || keyFilePath.equalsIgnoreCase("No file selected")) {
					JOptionPane.showMessageDialog(contentPane, "Please select a key file!");
				} else {
					if (mode == encMode) {
						String text = plainInput.getText();
						if(text == null || text.equalsIgnoreCase("")) {
							JOptionPane.showMessageDialog(contentPane, "Nothing to encrypt");
						} else {
							try {
								String cipherText = encrypt(text, algorithm, keyFilePath, modeCrypt, padding);
								cipherInput.setText(cipherText);
							} catch (Exception e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(contentPane, "Process failed. Check your key file, your input and the encrypt mode");
							}
						}
					} else {
						String text = cipherInput.getText();
						if(text == null || text.equalsIgnoreCase("")) {
							JOptionPane.showMessageDialog(contentPane, "Nothing to decrypt");
						} else {
							try {
								String plainText = decrypt(text, algorithm, keyFilePath, modeCrypt, padding);
								plainInput.setText(plainText);
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(contentPane, "Process failed. Check your key file, your input and the decrypt mode");
							}
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
		startBtn.setIcon(new ImageIcon(SymText.class.getResource("/image/start.png")));
		startBtn.setBorder(null);
		startBtn.setActionCommand("");
		startBtn.setBackground(Color.WHITE);
		startBtn.setBounds(364, 216, 73, 52);
		panelMenu.add(startBtn);
		
		JButton copyPlain = new JButton("Copy");
		copyPlain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String plain = plainInput.getText();
				if(plain == null || plain == "")
					return;
				else 
					copy(plain);
			}
		});
		copyPlain.setFocusPainted(false);
		copyPlain.setBounds(160, 397, 73, 23);
		panelMenu.add(copyPlain);
		
		JButton copyCipher = new JButton("Copy");
		copyCipher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cipher = cipherInput.getText();
				if(cipher == null || cipher == "")
					return;
				else 
					copy(cipher);
			}
		});
		copyCipher.setFocusPainted(false);
		copyCipher.setBounds(579, 397, 73, 23);
		panelMenu.add(copyCipher);
	}
}

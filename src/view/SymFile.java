package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class SymFile extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int xx, yy;
	private JTextField filePath;
	private JTextField modetxt;
	private int stateMode = 1;
	private String encMode = "Encrypt";
	private String decMode = "Decrypt";
	private String mode = encMode;
	private JTextField inputPath;
	private JTextField outPath;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
	LocalDateTime now = LocalDateTime.now();

	public static void encrypt(String sourceFile, String destFile, String algorithm, String keyFile, String mode, String padding) throws Exception {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(keyFile));
		byte[] originalBytes = Base64.getDecoder().decode(fileContent);
		SecretKey key = new SecretKeySpec(originalBytes, 0, originalBytes.length, algorithm);

		File file = new File(sourceFile);
		if(file.isFile()) {
			Cipher cipher = Cipher.getInstance(algorithm + mode + padding);

			if(mode.equalsIgnoreCase("/ECB") || algorithm.equalsIgnoreCase("RC4")) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
			} else if(mode.equalsIgnoreCase("")) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
			} else {
				if(algorithm.equalsIgnoreCase("AES")) {
					cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));
				}
			}

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte[] input = new byte[64];
			int bytesRead;

			while((bytesRead = fis.read(input)) != -1) {
				byte[] output = cipher.update(input	, 0, bytesRead);
				if(output != null)
					fos.write(output);
			}
			byte[] output = cipher.doFinal();
			if(output != null)
				fos.write(output);

			fis.close();
			fos.flush();
			fos.close();
		} else {
			sourceFile = "This is not a file";
		}

	}

	public static void decrypt(String sourceFile, String destFile, String algorithm, String keyFile, String mode, String padding) throws Exception {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(keyFile));
		byte[] originalBytes = Base64.getDecoder().decode(fileContent);
		SecretKey key = new SecretKeySpec(originalBytes, 0, originalBytes.length, algorithm);

		File file = new File(sourceFile);
		if(file.isFile()) {
			Cipher cipher = Cipher.getInstance(algorithm + mode + padding);

			if(mode.equalsIgnoreCase("/ECB") || algorithm.equalsIgnoreCase("RC4")) {
				cipher.init(Cipher.DECRYPT_MODE, key);
			} else if(mode.equalsIgnoreCase("")) {
				cipher.init(Cipher.DECRYPT_MODE, key);
			} else {
				if(algorithm.equalsIgnoreCase("AES")) {
					cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
				} else {
					cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));
				}
			}

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(destFile);

			byte[] input = new byte[64];
			int readByte = 0;

			while((readByte = fis.read(input)) != -1) {
				byte[] output = cipher.update(input	, 0, readByte);
				if(output != null)
					fos.write(output);
			}
			byte[] output = cipher.doFinal();
			if(output != null) {
				fos.write(output);
			}

			fis.close();
			fos.flush();
			fos.close();
		} else {
			sourceFile = "This is not a file";
		}

	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SymFile frame = new SymFile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SymFile() {
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

		JComboBox<Object> algoSelect = new JComboBox<Object>();
		algoSelect.setModel(new DefaultComboBoxModel<Object>(new String[] {"AES", "DES", "DESede", "Blowfish", "RC2", "RC4"}));
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
				inputPath.setText("");
				outPath.setText("");
				stateMode = 1 - stateMode;
				if(stateMode == 1) {
					mode = encMode;

				} else {
					mode = decMode;
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
				if(algoSelect.getSelectedItem().equals("RC4")) {
					modeSelect.setModel(new DefaultComboBoxModel<Object>(emptyMode));
					modeSelect.setEnabled(false);
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

		inputPath = new JTextField();
		inputPath.setText("");
		inputPath.setEditable(false);
		inputPath.setColumns(10);
		inputPath.setBackground(Color.WHITE);
		inputPath.setBounds(39, 225, 613, 29);
		panelMenu.add(inputPath);

		JLabel inputLb = new JLabel("Input File:");
		inputLb.setForeground(new Color(0, 100, 0));
		inputLb.setFont(new Font("Tahoma", Font.BOLD, 12));
		inputLb.setBackground(Color.WHITE);
		inputLb.setBounds(39, 200, 88, 22);
		panelMenu.add(inputLb);

		JButton browseIn = new JButton("Browse");
		browseIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int option = chooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION){
					File f = chooser.getSelectedFile();
					String filename = f.getAbsolutePath();
					inputPath.setText(filename);
				}else{
					inputPath.setText("No file selected");
				}
			}
		});
		browseIn.setForeground(Color.WHITE);
		browseIn.setFont(new Font("Tahoma", Font.BOLD, 12));
		browseIn.setFocusPainted(false);
		browseIn.setBackground(new Color(0, 128, 0));
		browseIn.setBounds(662, 224, 100, 29);
		panelMenu.add(browseIn);

		outPath = new JTextField();
		outPath.setText("");
		outPath.setEditable(false);
		outPath.setColumns(10);
		outPath.setBackground(Color.WHITE);
		outPath.setBounds(39, 290, 613, 29);
		panelMenu.add(outPath);

		JLabel outlb = new JLabel("Output Destination:");
		outlb.setForeground(new Color(0, 100, 0));
		outlb.setFont(new Font("Tahoma", Font.BOLD, 12));
		outlb.setBackground(Color.WHITE);
		outlb.setBounds(39, 265, 147, 22);
		panelMenu.add(outlb);

		JButton browseOut = new JButton("Browse");
		browseOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = chooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION){
					File f = chooser.getSelectedFile();
					String filename = f.getAbsolutePath();
					outPath.setText(filename);
				}else{
					outPath.setText("No path selected");
				}
			}
		});
		browseOut.setForeground(Color.WHITE);
		browseOut.setFont(new Font("Tahoma", Font.BOLD, 12));
		browseOut.setFocusPainted(false);
		browseOut.setBackground(new Color(0, 128, 0));
		browseOut.setBounds(662, 289, 100, 29);
		panelMenu.add(browseOut);

		JButton startBtn = new JButton("");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sourceFile = inputPath.getText();
				String destFile = outPath.getText();

				if(sourceFile.equalsIgnoreCase("") 
						|| sourceFile.equalsIgnoreCase("No file selected") 
						|| destFile.equalsIgnoreCase("") 
						|| destFile.equalsIgnoreCase("No path selected")) {
					JOptionPane.showMessageDialog(contentPane, "Choose paths for both input and output file");
				} else {
					String inputFileName = FilenameUtils.getBaseName(sourceFile);
					String inputFileExtension = FilenameUtils.getExtension(sourceFile);
					
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
							destFile += "\\" + inputFileName + "_encrypted_" + dtf.format(now) + "." + inputFileExtension;
							try {
								encrypt(sourceFile, destFile, algorithm, keyFilePath, modeCrypt, padding);
								JOptionPane.showMessageDialog(contentPane, "Encrypted Successfully");
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(contentPane, "Process failed. Check your key file, your input and the encrypt mode");
							}
						} else {
							destFile += "\\decrypted_from_" + inputFileName + "." + inputFileExtension;
							try {
								decrypt(sourceFile, destFile, algorithm, keyFilePath, modeCrypt, padding);
								JOptionPane.showMessageDialog(contentPane, "Decrypted Successfully");
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
		startBtn.setBounds(363, 347, 81, 52);
		panelMenu.add(startBtn);
	}
}

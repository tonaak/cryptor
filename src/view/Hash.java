package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;
import java.awt.Component;

public class Hash extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int xx, yy;

	private JTextField path;
	private JTextField txtText;
	private int stateMode = 1;
	private String fileMode = "File";
	private String textMode = "Text";
	private String mode = textMode;

	public String checksum(String input, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm, new BouncyCastleProvider());
		byte[] messageDigest = md.digest(input.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);

		return number.toString(16);
	}

	public String hash(String file, String algorithm) throws NoSuchAlgorithmException, IOException {
		byte[] b = Files.readAllBytes(Paths.get(file));
		byte[] hash = MessageDigest.getInstance(algorithm, new BouncyCastleProvider()).digest(b);
		
		return DatatypeConverter.printHexBinary(hash);
	}

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hash frame = new Hash();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Hash() {
		setTitle("Crypton 1.0");
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 523);
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
		panel.setBounds(0, 0, 679, 162);
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

		btnExit.setBounds(643, 0, 36, 40);
		panel.add(btnExit);

		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(GenkeyPair.class.getResource("/image/encrypted-data.png")));
		logo.setBounds(101, 11, 143, 140);
		panel.add(logo);

		JLabel title = new JLabel("HASH");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 29));
		title.setBounds(246, 39, 387, 91);
		panel.add(title);

		JButton btnReturn = new JButton("");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home();
				home.setVisible(true);

				Timer timer = new Timer( 100, new ActionListener(){
					public void actionPerformed( ActionEvent e ){
						dispose();
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
		btnReturn.setIcon(new ImageIcon(GenkeyPair.class.getResource("/image/return.png")));
		btnReturn.setMargin(new Insets(0, 0, 0, 0));
		btnReturn.setForeground(Color.WHITE);
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnReturn.setFocusPainted(false);
		btnReturn.setBorderPainted(false);
		btnReturn.setBackground(new Color(0, 128, 0));
		btnReturn.setBounds(0, 0, 46, 40);
		panel.add(btnReturn);

		JPanel panelMenu = new JPanel();
		panelMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelMenu.setBackground(Color.WHITE);
		panelMenu.setBorder(new LineBorder(new Color(0, 100, 0)));
		panelMenu.setBounds(0, 162, 679, 361);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		JScrollPane sp_1 = new JScrollPane((Component) null);
		sp_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 100, 0)), "Input Text", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 100, 0)));
		sp_1.setBackground(Color.WHITE);
		sp_1.setBounds(52, 84, 567, 93);
		panelMenu.add(sp_1);
		
		JTextArea textInput = new JTextArea();
		textInput.setMargin(new Insets(0, 4, 0, 4));
		textInput.setLineWrap(true);
		textInput.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sp_1.setViewportView(textInput);

		JComboBox<Object> algoSelect = new JComboBox<Object>();
		algoSelect.setMaximumRowCount(12);
		algoSelect.setModel(new DefaultComboBoxModel<Object>(new String[] {"MD5", "MD2", "MD4", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA-512/224", "SHA-512/256", "SHA3-224", "SHA3-256", "SHA3-384", "SHA3-512", "RIPEMD128", "RIPEMD160", "RIPEMD256", "RIPEMD320", "GOST3411", "TIGER", "WHIRLPOOL"}));
		algoSelect.setBackground(Color.WHITE);
		algoSelect.setBorder(null);
		algoSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		algoSelect.setBounds(54, 44, 264, 29);
		panelMenu.add(algoSelect);

		JLabel algo = new JLabel("Algorithms:");
		algo.setForeground(new Color(0, 100, 0));
		algo.setLabelFor(algoSelect);
		algo.setFont(new Font("Tahoma", Font.BOLD, 12));
		algo.setBackground(Color.WHITE);
		algo.setBounds(54, 21, 88, 22);
		panelMenu.add(algo);

		JLabel inputFilelb = new JLabel("Input File:");
		inputFilelb.setVisible(false);
		inputFilelb.setForeground(new Color(0, 100, 0));
		inputFilelb.setFont(new Font("Tahoma", Font.BOLD, 12));
		inputFilelb.setBackground(Color.WHITE);
		inputFilelb.setBounds(54, 84, 88, 22);
		panelMenu.add(inputFilelb);

		path = new JTextField();
		path.setVisible(false);
		path.setBackground(Color.WHITE);
		path.setEditable(false);
		inputFilelb.setLabelFor(path);
		path.setBounds(54, 109, 467, 29);
		panelMenu.add(path);
		path.setColumns(10);

		JButton browse = new JButton("Browse");
		browse.setVisible(false);
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int option = chooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION){
					File f = chooser.getSelectedFile();
					String filename = f.getAbsolutePath();
					path.setText(filename);
				}else{
					path.setText("No file selected");
				}
			}
		});
		browse.setFocusPainted(false);
		browse.setBackground(new Color(0, 128, 0));
		browse.setForeground(Color.WHITE);
		browse.setFont(new Font("Tahoma", Font.BOLD, 12));
		browse.setBounds(530, 109, 88, 29);
		panelMenu.add(browse);


		JTextArea result = new JTextArea();
		result.setEditable(false);
		result.setMargin(new Insets(0, 4, 0, 4));
		result.setLineWrap(true);
		result.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JScrollPane sp = new JScrollPane(result);
		sp.setBackground(Color.WHITE);
		sp.setBorder(new TitledBorder(new LineBorder(new Color(0, 100, 0)), "Hash Result", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 100, 0)));
		sp.setBounds(52, 188, 567, 91);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelMenu.add(sp);
		algoSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton hashBtn = new JButton("HASH");
		hashBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String algorithm = (String) algoSelect.getSelectedItem();
				
				if(stateMode == 1) {
					String input = textInput.getText();
					if(input == null || input.equalsIgnoreCase(""))
						JOptionPane.showMessageDialog(contentPane, "Nothing to hash");
					else
						try {
							result.setText(checksum(input, algorithm));
						} catch (NoSuchAlgorithmException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(contentPane, "Process failed. Please try again later");
						}
				} else {
					String file = path.getText();
					if(file.equalsIgnoreCase("") || file == null || file.equalsIgnoreCase("No file selected"))
						JOptionPane.showMessageDialog(contentPane, "Nothing to hash");
					else
						try {
							result.setText(hash(file, algorithm));
						} catch (NoSuchAlgorithmException | IOException e1) {
							JOptionPane.showMessageDialog(contentPane, "Process failed. Please try again later");
							e1.printStackTrace();
						}
				}
			}
		});
		hashBtn.setFocusPainted(false);
		hashBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		hashBtn.setForeground(Color.WHITE);
		hashBtn.setBackground(new Color(0, 128, 0));
		hashBtn.setBounds(279, 297, 115, 39);
		panelMenu.add(hashBtn);
		
		txtText = new JTextField();
		txtText.setText("Text");
		txtText.setMargin(new Insets(2, 5, 2, 2));
		txtText.setForeground(new Color(128, 0, 0));
		txtText.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
		txtText.setEditable(false);
		txtText.setColumns(10);
		txtText.setBorder(null);
		txtText.setBackground(Color.WHITE);
		txtText.setBounds(415, 44, 109, 29);
		panelMenu.add(txtText);
		
		JLabel lblChangeMode = new JLabel("Change mode:");
		lblChangeMode.setForeground(new Color(0, 100, 0));
		lblChangeMode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChangeMode.setBackground(Color.WHITE);
		lblChangeMode.setBounds(415, 19, 100, 22);
		panelMenu.add(lblChangeMode);
		
		JButton changeBtn = new JButton("");
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textInput.setText("");
				result.setText("");
				path.setText("");
				stateMode = 1 - stateMode;
				if(stateMode == 1) {
					mode = textMode;
					inputFilelb.setVisible(false);
					path.setVisible(false);
					browse.setVisible(false);
					sp_1.setVisible(true);
				} else {
					mode = fileMode;
					inputFilelb.setVisible(true);
					path.setVisible(true);
					browse.setVisible(true);
					sp_1.setVisible(false);
				}
				txtText.setText(mode);
			}
		});
		changeBtn.setIcon(new ImageIcon(Hash.class.getResource("/image/swap.png")));
		changeBtn.setMargin(new Insets(2, 0, 2, 0));
		changeBtn.setFocusPainted(false);
		changeBtn.setBorder(null);
		changeBtn.setBackground(SystemColor.menu);
		changeBtn.setBounds(566, 21, 52, 52);
		panelMenu.add(changeBtn);
	}
}


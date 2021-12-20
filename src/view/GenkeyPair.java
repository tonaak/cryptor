package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GenkeyPair extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int xx, yy;

	private JTextField path;
	private String keyPath;
	private JTextField successField;
	private static Base64.Encoder encoder = Base64.getEncoder();

	public static void doGenkey(String path, int keysize) throws IOException, NoSuchAlgorithmException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		path = path + "/key";

		File des = new File(path);
		if(!des.exists()) {
			des.mkdirs();
		}

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(keysize);
		KeyPair kp = kpg.generateKeyPair();

		try {
			File out = new File(path + "/private_" + dtf.format(now) + ".key");
			FileWriter writer = new FileWriter(out);
			String key = encoder.encodeToString(kp.getPrivate().getEncoded());

			writer.write(key);
			writer.flush();
			writer.close();
		} catch (Exception e){
			e.printStackTrace();
		}

		try {
			File out = new File(path + "/public_" + dtf.format(now) + ".pub");
			FileWriter writer = new FileWriter(out);
			String key = encoder.encodeToString(kp.getPublic().getEncoded());

			writer.write(key);
			writer.flush();
			writer.close();
		} catch (Exception e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenkeyPair frame = new GenkeyPair();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GenkeyPair() {
		setTitle("Crypt 1.0");
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

		JLabel title = new JLabel("KEY PAIR GENERATOR");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 29));
		title.setBounds(246, 39, 387, 91);
		panel.add(title);

		JButton btnReturn = new JButton("");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home();
				home.setVisible(true);

				Timer timer = new Timer( 60000, new ActionListener(){
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

		JComboBox<Object> algoSelect = new JComboBox<Object>();
		algoSelect.setModel(new DefaultComboBoxModel<Object>(new String[] {"RSA"}));
		algoSelect.setBackground(Color.WHITE);
		algoSelect.setBorder(null);
		algoSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		algoSelect.setBounds(54, 44, 264, 29);
		panelMenu.add(algoSelect);

		Integer[] rsaKey = new Integer[]{1024, 2048, 4096} ;

		JLabel algo = new JLabel("Algorithms:");
		algo.setForeground(new Color(0, 100, 0));
		algo.setLabelFor(algoSelect);
		algo.setFont(new Font("Tahoma", Font.BOLD, 12));
		algo.setBackground(Color.WHITE);
		algo.setBounds(54, 21, 88, 22);
		panelMenu.add(algo);

		JComboBox<Integer> keySelect = new JComboBox<>();
		keySelect.setModel(new DefaultComboBoxModel<Integer>(rsaKey));
		keySelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		keySelect.setBorder(null);
		keySelect.setBackground(Color.WHITE);
		keySelect.setBounds(354, 44, 264, 29);
		panelMenu.add(keySelect);

		JLabel lblKeySize = new JLabel("Key size:");
		lblKeySize.setLabelFor(keySelect);
		lblKeySize.setForeground(new Color(0, 100, 0));
		lblKeySize.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblKeySize.setBackground(Color.WHITE);
		lblKeySize.setBounds(354, 21, 88, 22);
		panelMenu.add(lblKeySize);

		JLabel destination = new JLabel("Save to:");
		destination.setForeground(new Color(0, 100, 0));
		destination.setFont(new Font("Tahoma", Font.BOLD, 12));
		destination.setBackground(Color.WHITE);
		destination.setBounds(54, 120, 88, 22);
		panelMenu.add(destination);

		path = new JTextField();
		path.setBackground(Color.WHITE);
		path.setEditable(false);
		destination.setLabelFor(path);
		path.setBounds(54, 145, 467, 29);
		panelMenu.add(path);
		path.setColumns(10);

		JButton browse = new JButton("Browse");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = chooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION){
					File f = chooser.getSelectedFile();
					String filename = f.getAbsolutePath();
					path.setText(filename);
					keyPath = filename;
				}else{
					path.setText("No path selected");
					keyPath = null;
				}
			}
		});
		browse.setFocusPainted(false);
		browse.setBackground(new Color(0, 128, 0));
		browse.setForeground(Color.WHITE);
		browse.setFont(new Font("Tahoma", Font.BOLD, 12));
		browse.setBounds(530, 145, 88, 29);
		panelMenu.add(browse);

		algoSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				keySelect.setModel(new DefaultComboBoxModel<Integer>(rsaKey));
			}
		});

		JButton genBtn = new JButton("GENERATE");
		genBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int keysize = (int) keySelect.getSelectedItem();
				if (keyPath == null) {
					successField.setText("Please choose a destination for the key file!");
				} else {
					successField.setText("Processing...");
					Timer timer = new Timer( 1000, new ActionListener(){
						public void actionPerformed( ActionEvent e ){
							try {				
								doGenkey(keyPath, keysize);
								successField.setText("Generated key successfully!");
							} catch (NoSuchAlgorithmException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					});
					timer.setRepeats(false);
					timer.start();
				}

			}
		});
		genBtn.setFocusPainted(false);
		genBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		genBtn.setForeground(Color.WHITE);
		genBtn.setBackground(new Color(0, 128, 0));
		genBtn.setBounds(278, 256, 115, 39);
		panelMenu.add(genBtn);

		successField = new JTextField();
		successField.setHorizontalAlignment(SwingConstants.CENTER);
		successField.setForeground(Color.RED);
		successField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		successField.setBackground(Color.WHITE);
		successField.setDisabledTextColor(Color.WHITE);
		successField.setEditable(false);
		successField.setBorder(null);
		successField.setBounds(184, 306, 308, 29);
		panelMenu.add(successField);
		successField.setColumns(10);
	}
}

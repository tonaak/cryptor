package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.security.Security;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int xx, yy;

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Home() {
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
		
		btnExit.setBounds(641, 0, 38, 40);
		panel.add(btnExit);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(Home.class.getResource("/image/encrypted-data.png")));
		logo.setBounds(101, 11, 143, 140);
		panel.add(logo);
		
		JLabel title = new JLabel("ENCRYPT & DECRYPT");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 29));
		title.setBounds(246, 39, 325, 91);
		panel.add(title);
		
		JButton btnSym = new JButton("SYMMETRIC");
		btnSym.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sym sym = new Sym();
				sym.setVisible(true);
				
				// delay dispose
				Timer timer = new Timer( 100, new ActionListener(){
				  public void actionPerformed( ActionEvent e ){
				      dispose();
				  }
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
		btnSym.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSym.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSym.setBackground(SystemColor.menu);
			}
		});
		btnSym.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSym.setFocusPainted(false);
		btnSym.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSym.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSym.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		btnSym.setIcon(new ImageIcon(Home.class.getResource("/image/symm.png")));
		btnSym.setForeground(new Color(0, 128, 0));
		btnSym.setBackground(SystemColor.menu);
		btnSym.setBounds(120, 187, 130, 130);
		btnSym.setBorder(new LineBorder(new Color(227, 227, 227), 0, true));
		contentPane.add(btnSym);
		
		JButton btnAsymmetric = new JButton("ASYMMETRIC");
		btnAsymmetric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Asym sym = new Asym();
				sym.setVisible(true);
				
				// delay dispose
				Timer timer = new Timer( 100, new ActionListener(){
				  public void actionPerformed( ActionEvent e ){
				      dispose();
				  }
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
		btnAsymmetric.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAsymmetric.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAsymmetric.setBackground(SystemColor.menu);
			}
		});
		btnAsymmetric.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAsymmetric.setIcon(new ImageIcon(Home.class.getResource("/image/asym.png")));
		btnAsymmetric.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAsymmetric.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAsymmetric.setForeground(new Color(0, 128, 0));
		btnAsymmetric.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAsymmetric.setFocusPainted(false);
		btnAsymmetric.setBorder(new LineBorder(new Color(227, 227, 227), 0, true));
		btnAsymmetric.setBackground(SystemColor.menu);
		btnAsymmetric.setBounds(271, 187, 130, 130);
		contentPane.add(btnAsymmetric);
		
		JButton btnHash = new JButton("HASH");
		btnHash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Hash sym = new Hash();
				sym.setVisible(true);
				
				// delay dispose
				Timer timer = new Timer( 100, new ActionListener(){
				  public void actionPerformed( ActionEvent e ){
				      dispose();
				  }
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
		btnHash.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnHash.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHash.setIcon(new ImageIcon(Home.class.getResource("/image/Key-Hash-64.png")));
		btnHash.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnHash.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnHash.setBackground(SystemColor.menu);
			}
		});
		btnHash.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHash.setForeground(new Color(0, 128, 0));
		btnHash.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnHash.setFocusPainted(false);
		btnHash.setBorder(new LineBorder(new Color(227, 227, 227), 0, true));
		btnHash.setBackground(SystemColor.menu);
		btnHash.setBounds(425, 187, 130, 130);
		contentPane.add(btnHash);
		
		JButton btnKeygen = new JButton("GENERATE KEY");
		btnKeygen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Genkey gen = new Genkey();
				gen.setVisible(true);
				// delay dispose
				Timer timer = new Timer( 100, new ActionListener(){
				  public void actionPerformed( ActionEvent e ){
				      dispose();
				  }
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
		btnKeygen.setIcon(new ImageIcon(Home.class.getResource("/image/key.png")));
		btnKeygen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnKeygen.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnKeygen.setBackground(SystemColor.menu);
			}
		});
		btnKeygen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnKeygen.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnKeygen.setHorizontalTextPosition(SwingConstants.CENTER);
		btnKeygen.setForeground(new Color(0, 128, 0));
		btnKeygen.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnKeygen.setFocusPainted(false);
		btnKeygen.setBorder(new LineBorder(new Color(227, 227, 227), 0, true));
		btnKeygen.setBackground(SystemColor.menu);
		btnKeygen.setBounds(120, 328, 130, 130);
		contentPane.add(btnKeygen);
		
		JButton btnKeypair = new JButton("GENKEY PAIR");
		btnKeypair.setIcon(new ImageIcon(Home.class.getResource("/image/2key.png")));
		btnKeypair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenkeyPair gen = new GenkeyPair();
				gen.setVisible(true);
				// delay dispose
				Timer timer = new Timer( 100, new ActionListener(){
				  public void actionPerformed( ActionEvent e ){
				      dispose();
				  }
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
		btnKeypair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnKeypair.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnKeypair.setBackground(SystemColor.menu);
			}
		});
		btnKeypair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnKeypair.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnKeypair.setHorizontalTextPosition(SwingConstants.CENTER);
		btnKeypair.setForeground(new Color(0, 128, 0));
		btnKeypair.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnKeypair.setFocusPainted(false);
		btnKeypair.setBorder(new LineBorder(new Color(227, 227, 227), 0, true));
		btnKeypair.setBackground(SystemColor.menu);
		btnKeypair.setBounds(271, 328, 130, 130);
		contentPane.add(btnKeypair);
		
		JButton btnAbout = new JButton("ABOUT");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "18130246 - Nguyen An Toan", "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnAbout.setIcon(new ImageIcon(Home.class.getResource("/image/about.png")));
		btnAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAbout.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAbout.setBackground(SystemColor.menu);
			}
		});
		btnAbout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAbout.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAbout.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAbout.setForeground(new Color(0, 128, 0));
		btnAbout.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAbout.setFocusPainted(false);
		btnAbout.setBorder(new LineBorder(new Color(227, 227, 227), 0, true));
		btnAbout.setBackground(SystemColor.menu);
		btnAbout.setBounds(425, 328, 130, 130);
		contentPane.add(btnAbout);
	}
}

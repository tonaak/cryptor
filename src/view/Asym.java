package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Asym extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int xx, yy;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Asym frame = new Asym();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Asym() {
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
		logo.setIcon(new ImageIcon(Asym.class.getResource("/image/encrypted-data.png")));
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
				Home home = new Home();
				home.setVisible(true);
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
		btnReturn.setIcon(new ImageIcon(Asym.class.getResource("/image/return.png")));
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
		panelMenu.setBounds(0, 162, 679, 361);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		JButton btnFile = new JButton("File");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AsymFile sym = new AsymFile();
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
		btnFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnFile.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnFile.setBackground(SystemColor.menu);
			}
		});
		btnFile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFile.setFocusPainted(false);
		btnFile.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnFile.setHorizontalTextPosition(SwingConstants.CENTER);
		btnFile.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		btnFile.setIcon(new ImageIcon(Asym.class.getResource("/image/file.png")));
		btnFile.setForeground(new Color(0, 128, 0));
		btnFile.setBackground(SystemColor.menu);
		btnFile.setBounds(350, 51, 216, 223);
		btnFile.setBorder(new LineBorder(new Color(227, 227, 227), 0, true));
		panelMenu.add(btnFile);
		
		JButton btnText = new JButton("Text");
		btnText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AsymText sym = new AsymText();
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
		btnText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnText.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnText.setBackground(SystemColor.menu);
			}
		});
		btnText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnText.setFocusPainted(false);
		btnText.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnText.setHorizontalTextPosition(SwingConstants.CENTER);
		btnText.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		btnText.setIcon(new ImageIcon(Asym.class.getResource("/image/text-box.png")));
		btnText.setForeground(new Color(0, 128, 0));
		btnText.setBackground(SystemColor.menu);
		btnText.setBounds(124, 51, 216, 223);
		btnText.setBorder(new LineBorder(new Color(227, 227, 227), 0, true));
		panelMenu.add(btnText);
		
	}
}

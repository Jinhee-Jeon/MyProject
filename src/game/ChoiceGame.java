package game;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ChoiceGame extends JFrame{
	private final String START_SOUND = "/res/baby.wav";
	private AudioClip backgroundSound;		// 게임 배경 음악
	private final int FWidth = 710; 		// 전체 frame의 폭
	private final int FHeight = 560; 	// 전체 frame의 높이
	private final String MAIN_PIC = "/res/gamemain.jpg";
	
	private final String icon1 = "src/p.png";
	private final String icon2 = "src/k.png";
	private final String icon3 = "src/뽀로로.png";
	private final String icon4 = "src/앵그리버드.png";
	
	private final String mini_icon1 = "src/minip.jpg";
	private final String mini_icon2 = "src/minik.jpg";
	private final String mini_icon3 = "src/mini뽀로로.jpg";
	private final String mini_icon4 = "src/mini앵그리버드.png";
	
	
	JButton ready = new JButton("READY");
	ImageIcon myIcon;
	ImageIcon icon1Pic = new ImageIcon(icon1);
	ImageIcon icon2Pic = new ImageIcon(icon2);
	ImageIcon icon3Pic = new ImageIcon(icon3);
	ImageIcon icon4Pic = new ImageIcon(icon4);
	
	ImageIcon mini_icon1Pic = new ImageIcon(mini_icon1);
	ImageIcon mini_icon2Pic = new ImageIcon(mini_icon2);
	ImageIcon mini_icon3Pic = new ImageIcon(mini_icon3);
	ImageIcon mini_icon4Pic = new ImageIcon(mini_icon4);
	
	
	
	
	JRadioButton level1,level2,level3;
	JLabel level;
	JLabel iconInfo;
	JRadioButton icon1Btn,icon2Btn,icon3Btn,icon4Btn;
	JLabel icon1show, icon2show, icon3show, icon4show; 
	public int levelSize;
	public int size;
	
	
	String name, choiceIcon;
	JPanel choicePanel;
	
	public ChoiceGame( String n) {
		
		try {
			backgroundSound = JApplet.newAudioClip(getClass().getResource(START_SOUND));
		}
		catch(Exception e){
			System.out.println("음향 파일 로딩 실패");
		}
		choicePanel = new choicePanel();
		//backgroundSound.loop();
	
		
		this.name = n;
		
		setTitle("Tic Tac Toe" ); 
		setBounds(0, 0, FWidth,FHeight);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		
		level = new JLabel("난이도");
		level.setBounds(40,30,50,20);
		add(level);
		level1 = new JRadioButton("3*3");
		level1.setBounds(90, 30, 50, 20);
		add(level1);
		level2 = new JRadioButton("4*4");
		level2.setBounds(150, 30, 50, 20);
		add(level2);
		level3 = new JRadioButton("6*6");
		level3.setBounds(210, 30, 50, 20);
		add(level3);
		
		iconInfo = new JLabel("아이콘선택");
		iconInfo.setBounds(40,58,70,20);
		add(iconInfo);
		icon1Btn = new JRadioButton();
		icon1Btn.setBounds(40,80,20,15);
		add(icon1Btn);
		icon2Btn = new JRadioButton();
		icon2Btn.setBounds(110,80,20,15);
		add(icon2Btn);
		icon3Btn = new JRadioButton();
		icon3Btn.setBounds(180,80,20,15);
		add(icon3Btn);
		icon4Btn = new JRadioButton();
		icon4Btn.setBounds(250,80,20,15);
		add(icon4Btn);
		
		icon1show = new JLabel();
		icon1show.setIcon(mini_icon1Pic);
		icon1show.setBounds(55,80,50,55);
		add(icon1show);
		icon2show = new JLabel();
		icon2show.setIcon(mini_icon2Pic);
		icon2show.setBounds(125,80,50,55);
		add(icon2show);
		icon3show = new JLabel();
		icon3show.setIcon(mini_icon3Pic);
		icon3show.setBounds(195,80,50,55);
		add(icon3show);
		icon4show = new JLabel();
		icon4show.setIcon(mini_icon4Pic);
		icon4show.setBounds(265,80,50,55);
		add(icon4show);
		
		
		ready.setBounds(265,30,75,25);
		ready.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
		add(ready);
		getContentPane().add (choicePanel);
	
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) {
				
		
				if(level1.isSelected() == true) {
					levelSize = 3;
	
				}
				else if(level2.isSelected() == true) {
					levelSize = 4;

				}
				else if(level3.isSelected() == true) {
					levelSize = 6;

				}
				if(icon1Btn.isSelected() == true){
					
					choiceIcon = icon1;
					myIcon = new ImageIcon(icon1);
					icon2Btn.setEnabled(false);
					icon3Btn.setEnabled(false);
					icon4Btn.setEnabled(false);
				}
				
				else if(icon2Btn.isSelected() == true){
					choiceIcon = icon2;
					myIcon = icon2Pic;
					icon1Btn.setEnabled(false);
					icon3Btn.setEnabled(false);
					icon4Btn.setEnabled(false);
				}
				
				else if(icon3Btn.isSelected() == true){
					myIcon = icon3Pic;
					icon1Btn.setEnabled(false);
					icon2Btn.setEnabled(false);
					icon4Btn.setEnabled(false);
				}
				
				else if(icon4Btn.isSelected() == true){
					myIcon = icon4Pic;
					icon2show.setEnabled(false);
					icon3show.setEnabled(false);
					icon4show.setEnabled(false);
				}
				GameStarter gs = new GameStarter(name, levelSize, myIcon); //게임화면 나온다 
			
				gs.setVisible(true);
				setVisible(false);
				
			}
		});
		
	}
	public class choicePanel extends JPanel{
	
		public void paintComponent(Graphics g) {
			Image img = new ImageIcon(getClass().getResource(MAIN_PIC)).getImage(); 
			g.drawImage(img,0,0,FWidth,FHeight,this);
			
		}
	}
}


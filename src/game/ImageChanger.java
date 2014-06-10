package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;


	public class ImageChanger extends JFrame {
		private final String NEXTICON = "src/next.png";
		private final String PREICON = "src/pre.png";
		private final String MAIN_PIC = "/res/gamemain.jpg"; //Icon 셋팅 
		JLabel intro;
		ImageIcon[] icon = new ImageIcon[6];
		int i=0;
		
		Icon nextIcon = new ImageIcon(NEXTICON);
		Icon preIcon = new ImageIcon(PREICON);
		
		public ImageChanger() {
			this.setContentPane(new MyPanel());
			setSize(330,370);
			setVisible(true);	
			setTitle("아이콘사용법" ); 
		}
		class MyPanel extends JPanel {
			public MyPanel() {
				setLayout(new BorderLayout());
				
				for(int i=0; i<icon.length; i++) {
					icon[i] = new ImageIcon("src/intro"+(i+1)+".jpg");
				}
				
				intro = new JLabel(icon[0]);
				add(intro, BorderLayout.CENTER);
				DownPanel downPanel = new DownPanel();
				//downPanel.setBackground(Color.white);
				//downPanel.setOpaque(true);
				add(downPanel, BorderLayout.SOUTH);
			}
		}
		class DownPanel extends JPanel {
			JButton next = new JButton();
			JButton pre = new JButton();
			
			
			
			
			public DownPanel() {
				add(pre);
				add(next);
				pre.setBackground(Color.white);
				next.setBackground(Color.white);
				pre.setIcon(preIcon);
				pre.setBounds(0,0,10,10);
				next.setBounds(0,0,10,10);
				next.setIcon(nextIcon);
				pre.setBorderPainted(false);
				pre.setContentAreaFilled(false);
				pre.setOpaque(false);
				next.setBorderPainted(false);
				next.setContentAreaFilled(false);
			
			pre.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					i--;
					i+=icon.length;
					i%=icon.length;
					intro.setIcon(icon[i]);
				}
			});
			next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					i++;
					i+=icon.length;
					i%=icon.length;
					intro.setIcon(icon[i]);
				}
			});
			}
			
			
			public void paintComponent(Graphics g) {
				Image img = new ImageIcon(getClass().getResource(MAIN_PIC)).getImage(); //배경 이미지 셋팅 
				g.drawImage(img,0,0,330,370,this);

			}
		}
	

	
}



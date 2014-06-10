package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;


public class GameUsing {
	JFrame frame = new JFrame();
	JPanel coverPanel;

	private final String MAIN_PIC = "/res/gamemain.jpg"; //게임 메인 이미지 
	private final String welcome = "/res/welcome.gif";
	private final int FWidth = 710; 		// 전체 frame의 폭
	private final int FHeight = 560; 	// 전체 frame의 높이

	ObjectInputStream reader;	// 수신용 스트림
	ObjectOutputStream writer;	// 송신용 스트림
	Socket sock;				// 서버 연결용 소켓

	JButton loginButton = new JButton("로그인");
	JButton joinButton = new JButton("회원가입");
	JButton searchButton = new JButton("ID/Password찾기");
	private JTextField idField;
	private JPasswordField passField;
	private String name;
	private String password;


	public static void main(String[] args) {

		new GameUsing().go();

	}
	public void go() {

		frame.setTitle("Tic Tac Toe" ); 
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FWidth,FHeight);

		coverPanel = new coverPanel();

		coverPanel.setBounds(0,0,FHeight,FWidth);

		frame.getContentPane().add(coverPanel);
		coverPanel.setLayout(null);


		JLabel id_Label = new JLabel("ID");
		id_Label.setBounds(220, 220, 20, 10);
		coverPanel.add(id_Label);


		JLabel pass_Label = new JLabel("Password");
		pass_Label.setBounds(220, 240, 60, 20);
		coverPanel.add(pass_Label);


		loginButton.setBounds(390, 217,80, 53);
		loginButton.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 25));
		coverPanel.add(loginButton);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //로그인 버튼 누르면 select문 넣기



				try {
					Connection conn = null;
					Statement stmt = null;


					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/game", "root", "qwert");
					stmt = conn.createStatement();

					ResultSet rs = stmt.executeQuery( "Select name, password FROM people WHERE email='" + idField.getText() + "'"+ "and password = '" + passField.getText() + "';");
					//email로 회원 이름, 비밀번호 검색한다


					while(rs.next()) {
						name = rs.getString("name");
						password = rs.getString("password");

					}


					rs.close();

					if(name != null ) {
						JOptionPane jo = new JOptionPane();   
						jo.showMessageDialog(null, name + "님 환영합니다^ㅇ^*");


						ChoiceGame  cg = new ChoiceGame(name);
						cg.setVisible(true);

						frame.setVisible(false);
					}

					else {
						JOptionPane jo2 = new JOptionPane();        
						jo2.showMessageDialog(null, "ID나 비밀번호를 다시 확인해주세요..ㅠㅠ");
						idField.setText("");
						passField.setText("");
					}

				}
				catch (ClassNotFoundException cnfe) {
					System.out.println("해당 클래스를 찾을 수 없습니다." +
							cnfe.getMessage());
				}
				catch (SQLException se) {
					System.out.println(" SQL에러. " + se.getMessage());
				}

				catch(NumberFormatException nfe) {


				}
			}
		}
		);


		joinButton.setBounds(220, 280,90, 20);
		joinButton.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 20));
		coverPanel.add(joinButton);


		joinButton.addActionListener(new ActionListener() { //가입버튼 누르면 실행
			public void actionPerformed(ActionEvent e) {
				people p = new people();
				p.setVisible(true);

			}
		});
		searchButton.setBounds(320,280,155, 20);
		searchButton.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 20));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search s = new search();
				s.setVisible(true);
			}
		});
		coverPanel.add(searchButton);

		idField = new JTextField();
		idField.setBounds(240, 215, 140, 20);
		coverPanel.add(idField);

		passField = new JPasswordField();
		passField.setEchoChar('*');

		passField.setBounds(285, 245, 90, 20);
		coverPanel.add(passField);
		frame.setVisible(true);


	}

	// 초기화면을 나타내는 패널
	public class coverPanel extends JPanel {
		public void paintComponent(Graphics g) {
			Image img = new ImageIcon(getClass().getResource(MAIN_PIC)).getImage(); 
			g.drawImage(img,0,0,FWidth,FHeight,this);

			Image img2 = new ImageIcon(getClass().getResource(welcome)).getImage();
			g.drawImage(img2,170,30,350,200,this);

		}
	}
}

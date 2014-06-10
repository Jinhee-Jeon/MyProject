package game;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameStarter extends JFrame {
	private final String START_SOUND = "/res/baby.wav";
	private AudioClip backgroundSound;						// 게임 배경 음악
	private final String WIN_SOUND = "/res/heart.wav";
	private AudioClip winSound;		
	private final String DRAW_SOUND = "/res/draw.wav";
	private AudioClip drawSound;		
	private final String LOSE_SOUND = "/res/lose.wav";
	private AudioClip loseSound;		
	private final String ITEM_SOUND = "/res/item.wav";
	private AudioClip itemSound;	
	private final String MAIN_PIC = "/res/gamemain.jpg"; 	//Icon 셋팅 
	private final String icon1 = "src/p.png";
	private final String rankImage = "/res/rankImage.jpg";
	private final String buyImage = "src/buy.png";
	private final String useImage = "src/use.png";
	private final String icon2 = "src/k.png";
	private final String icon3 = "src/뽀로로.png";
	private final String icon4 = "src/앵그리버드.png";
	private final String nullImage = "src/white.jpg";
	private final String mini_icon1 = "src/minip.jpg";
	private final String mini_icon2 = "src/minik.jpg";
	private final String mini_icon3 = "src/mini뽀로로.jpg";
	private final String mini_icon4 = "src/mini앵그리버드.png";
	private final String q = "src/question.png";

	private final String s = "src/send.png";
	

	private final int FWidth = 710; 				// 전체 frame의 폭
	private final int FHeight = 560; 			    // 전체 frame의 높이
	public int levelSize; 							//레벨 사이즈 
	public int size; 								//levelSize * levelSIze , 총 버튼의 개수 


	Icon nullIcon = new ImageIcon(nullImage); 		//아이콘 선택을 위한 변수들
	Icon myIcon;
	Icon yourIcon;
	Icon miniIcon;
	Icon sendIcon = new ImageIcon(s);
	Icon ques = new ImageIcon(q);
	Icon usePic = new ImageIcon(useImage);
	Icon buyPic = new ImageIcon(buyImage);
	Icon icon1Pic = new ImageIcon(icon1);
	Icon icon2Pic = new ImageIcon(icon2);
	Icon icon3Pic = new ImageIcon(icon3);
	Icon icon4Pic = new ImageIcon(icon4);
	Icon mini_icon1Pic = new ImageIcon(mini_icon1);
	Icon mini_icon2Pic = new ImageIcon(mini_icon2);
	Icon mini_icon3Pic = new ImageIcon(mini_icon3);
	Icon mini_icon4Pic = new ImageIcon(mini_icon4);
	JRadioButton level1,level2,level3;
	JLabel level;
	JLabel itemBuy;
	JLabel iconInfo;
	JLabel myItem;
	JRadioButton icon1Btn,icon2Btn,icon3Btn,icon4Btn;
	JLabel icon1show, icon2show, icon3show, icon4show; 
	JLabel myTurn; //turn을 표시하기 위한 변수
	JLabel turnShow;

	JButton btnEmpty[] = new JButton[50]; 			//게임시 사용될 버튼 
	JButton again = new JButton("AGAIN");
	JButton ranking = new JButton("랭킹보기");
	JButton start = new JButton("START");
	JButton buy = new JButton("구입");
	JButton use = new JButton("사용");
	JButton logOut = new JButton("로그아웃");
	JButton question = new JButton();

	JPanel gamePanel; //패널 
	JPanel btPanel;


	String balance;				//아이템 구입 후 잔액 
	String mesg; 				//승 패를 판단후 JOptionPane에 띄워줄 메세지 

	JComboBox num; 				//아이템을 나타내주는 변수
	JTextField itemPrice;
	JTextField myItemNum;
	String priceS; 				//아이템 가격을 표시 

	int itemNum, price, myMoney;
	int prize; 					//게임 승패시 주는 금액과 뺏는 금액
	int itemN; 					//선택한 아이템 개수를 받는 변수 
	int winCnt, loseCnt, drawCnt, rate; // 승,패,무승부 횟수를 세준다. 
	int turn = 0;//차례 
	int wonNumber1 = 1, wonNumber2 = 1, wonNumber3 = 1, wonNumber4 = 1, wonNumber5 = 1, wonNumber6 = 1;
	//winCombo 버튼을 확인하기 위한 변수
	boolean win = false; 											//승패 판단을 위해 필요한 변수 
	boolean btnEmptyClicked = false; 								//버튼이 모두 클릭되었는지 확인 


	ArrayList<RankInfo> rank = new ArrayList<RankInfo>();
	String number[] = {"선택","1","2","3","4","5","6","7","8","9","10"}; //아이템을 최대로 살 수 있는 갯수는 10개 


	String name; 			    //로그인 시 사용자 이름 

	int myI, yourI;			    //나의 버튼 인덱스와 상대의 버튼 인덱스 
	String setText; 			//버튼에 텍스트를 넣기 위해 필요한 변수 
	String[] users; 			//로그인한 사용자 목록 
	ArrayList<Integer> myList = new ArrayList<Integer>(); //user1이 누르는 인덱스들의 모임
	ArrayList<Integer> yourList = new ArrayList<Integer>(); //user2가 누르는 인덱스들의 모임

	ChatMessage message;   
	JTextArea incoming;			// 수신된 메시지를 출력하는 곳
	JTextArea outgoing;			// 송신할 메시지를 작성하는 곳
	JList counterParts;			// 현재 로그인한 채팅 상대목록을 나타내는 리스트.
	ObjectInputStream reader;	// 수신용 스트림
	ObjectOutputStream writer;	// 송신용 스트림
	Socket sock;				// 서버 연결용 소켓
	String user;				// 이 클라이언트로 로그인 한 유저의 이름


	int index; 					//버튼의 인덱스
	Icon newIcon; 				//아이콘을 읽어온다 
	int myIndex, yourIndex; 	//user1, user2의 버튼 인덱스를 각각 가져온다. 
	int nowTurn; 				//현재의 turn정보 
	String name2; 				//랭킹생성시 동일한 이름이 있으면 삭제해야할 때 필요한 변수 
	String inOut;


	final int winCombo3[][] = new int[][]	{ 					// 3*3 일 때 이기는 경우 
			{1, 2, 3}, 			{1, 4, 7}, 		{1, 5, 9},
			{4, 5, 6}, 			{2, 5, 8}, 		{3, 5, 7},
			{7, 8, 9}, 			{3, 6, 9}
			/*가로줄 채우기 */	/*세로줄 채우기 */ /*대각선 채우기*/
	};

	final int winCombo4[][] = new int[][]	{ 					// 4*4 일 때 이기는 경우 
			{1, 2, 3, 4}, 			{1, 5, 9, 13}, 		{1, 6, 11, 16},
			{5, 6, 7, 8}, 			{2, 6, 10, 14}, 	{4, 7, 10, 13},
			{9, 10, 11, 12}, 		{3, 7, 11, 15},		
			{13, 14, 15, 16},		{4, 8, 12, 16}

	};

	final int winCombo6[][] = new int[][]	{ 					//6*6 일떄 이기는 경우 
			{1, 2, 3, 4, 5, 6}, 			{1, 7, 13, 19, 25, 31}, 	{6, 11, 16, 21, 26, 31},
			{7, 8, 9, 10, 11, 12}, 			{2, 8, 14, 20, 26, 32}, 	{1, 8, 15, 22, 29, 36},
			{13, 14, 15, 16, 17, 18}, 	{3, 9, 15, 21, 27, 33}, 
			{19, 20, 21, 22, 23, 24}, 	{4, 10, 16, 22, 28, 34},
			{25, 26, 27, 28, 29, 30},   {5, 11, 17, 23, 29, 35},
			{31, 32, 33, 34, 35, 36},   {6, 12, 18, 24, 30, 36}

	};


	public GameStarter(final String name, int lev, Icon icon){


		try {

			backgroundSound = JApplet.newAudioClip(getClass().getResource(START_SOUND));
			winSound = JApplet.newAudioClip(getClass().getResource(WIN_SOUND));
			drawSound = JApplet.newAudioClip(getClass().getResource(DRAW_SOUND));
			loseSound = JApplet.newAudioClip(getClass().getResource(LOSE_SOUND));
			itemSound = JApplet.newAudioClip(getClass().getResource(ITEM_SOUND));

		}
		catch(Exception e){
			System.out.println("음향 파일 로딩 실패");
		}



		this.name = name;
		levelSize = lev;

		myIcon = icon;
		gamePanel = new gamePanel(levelSize);


		//gui셋팅 
		setTitle("Tic Tac Toe" ); 
		setBounds(0, 0, FWidth,FHeight);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		again.setBounds(170,460,87,25);
		again.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
		add(again);
		start.setBounds(265,30,75,25);
		start.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
		add(start);
		ranking.setBounds(75,460,87,25);
		ranking.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 20));
		add(ranking);
		logOut.setBounds(265,460,75,25);
		logOut.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 21));
		add(logOut);
		buy.setBounds(580,25,46,31);
		buy.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
		buy.setIcon(buyPic);
		buy.setBorderPainted(false);
		buy.setContentAreaFilled(false);
		add(buy);
		use.setBounds(580,55,46,31);
		use.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
		use.setIcon(usePic);
		use.setBorderPainted(false);
		use.setContentAreaFilled(false);
		add(use);
		question.setBounds(630,30,56,30);
		question.setBorderPainted(false);
		question.setContentAreaFilled(false);
		question.setIcon(ques);
		add(question);


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


		itemBuy = new JLabel("아이템 수");
		itemBuy.setBounds(400,30,60,20);
		add(itemBuy);

		myTurn = new JLabel("TURN");
		myTurn.setBounds(460, 90, 60, 50);
		myTurn.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 25));
		add(myTurn);
		turnShow = new JLabel();
		turnShow.setBounds(521,83,100,65);
		add(turnShow);

		myItemNum = new JTextField();
		myItemNum.setBounds(520,60,50,21);
		add(myItemNum);

		itemPrice = new JTextField();
		itemPrice.setBounds(520, 30, 50, 21);
		add(itemPrice);

		myItem = new JLabel("보유아이템 수");
		myItem.setBounds(435,60,90,20);
		add(myItem);
		num = new JComboBox(number);
		num.addActionListener(new ActionListener() { //아이템 구입버튼 클릭의 리스너 
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				itemNum =cb.getSelectedIndex(); //선택된 콤보박스의 인덱스를 가져온다

				price = 100 * itemNum;
				priceS = price + "원";
				itemPrice.setText(priceS);

			}
		});
		num.setBounds(460, 30, 56, 21);
		add(num);

		try { 										//사용자의 가지고 있는 아이템 개수를 select 한다. 
			Connection conn = null;
			Statement stmt = null;


			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/game", "root", "qwert");
			stmt = conn.createStatement();


			ResultSet rs = stmt.executeQuery( "Select item_1 FROM people WHERE name = '" + name + "';");


			while(rs.next()) {
				itemN = rs.getInt("item_1");


			}
			rs.close();
			myItemNum.setText(itemN + "");

			if(itemN <= 0) { //사용 가능한 item의 개수가 0일 경우 사용하지 못하게 한다.
				use.setEnabled(false);
			}
			else {
				use.setEnabled(true); //사용 가능한 item 개수가 0보다 클 경우 사용 가능하게 한다.
				if(size == 9) use.setEnabled(false); //3*3에서는 아이템 전 불가 

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



		buy.addActionListener(new ActionListener() { //구입 버튼의 리스너
			public void actionPerformed(ActionEvent click) {

				try {
					Connection conn = null;
					Statement stmt = null;


					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/game", "root", "qwert");
					stmt = conn.createStatement();


					ResultSet rs = stmt.executeQuery( "Select money FROM people WHERE name = '" + name + "';");
					//돈이 얼마 있는지 확인

					while(rs.next()) {
						myMoney = rs.getInt("money");
						//    System.out.println("myMoney = " + myMoney);

					}
					rs.close();
					if(myMoney >= price) { //가지고 있는 돈이 아이템의 값보다 많으면 
						stmt.executeUpdate( "UPDATE people SET money = money - " + price + " WHERE name = '" + name+"';");
						stmt.executeUpdate( "UPDATE people SET item_1 = item_1 + " + itemNum + " WHERE name = '" + name + "';");
						balance = myMoney - price + "";
						JOptionPane jo = new JOptionPane();        
						jo.showMessageDialog(null, "구입완료^^! 잔액은 " + balance + "입니다.");
						itemN = itemN + itemNum;
						myItemNum.setText(itemN + "");
					}

					else { //가지고 있는 돈이 부족하면 

						JOptionPane jo = new JOptionPane();        
						jo.showMessageDialog(null, "머니가 부족합니다 ㅠㅠㅠ");
					}


					if(itemN <= 0) {//아이템이 없으면 사용버튼 enabled 한다 
						use.setEnabled(false);
					}
					else use.setEnabled(true);

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
		});

		use.addActionListener(new ActionListener() { //사용버튼의 리스너
			public void actionPerformed(ActionEvent click) {
				itemSound.play();

				try {
					Connection conn = null;
					Statement stmt = null;


					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/game", "root", "qwert");
					stmt = conn.createStatement();



					stmt.executeUpdate( "UPDATE people SET item_1 = item_1 - 1 WHERE name = '" + name + "';");
					//내가 가지고 있는 아이템의 개수를 -1 해준다 

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

				turn++; //차례를 내 차례로 돌려준다 
			

				//   System.out.println("아이템 사용 후 turn = " + turn);
				itemN = itemN-1;
				myItemNum.setText( itemN + "");

				if(itemN <= 0) { //아이템이 없으면 사용버튼을 enabled 한다. 
					use.setEnabled(false);
				}
				else use.setEnabled(true);




			}
		});

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

		getContentPane().add(gamePanel);



		question.addActionListener(new ActionListener() { //?버튼 누를시 아이템 사용의 설명이 나온다.  
			public void actionPerformed(ActionEvent click) {

				new ImageChanger();

			}
		});



		again.addActionListener(new ActionListener() { //again 버튼 누르면 게임이 재실행 된다. 
			public void actionPerformed(ActionEvent click) {

				GameStarter gs = new GameStarter(name, levelSize, myIcon); //게임화면이 새로 나온다 
				myList.removeAll(myList);
				yourList.remove(yourList);

				gs.setVisible(true);
				setVisible(false);

			}
		});

		logOut.addActionListener(new ActionListener() { //logOut 버튼 누르면 첫 화면 나타난다 
			public void actionPerformed(ActionEvent click) {


				int option = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까??","로그아웃",JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)	{

					setVisible(false);
					processLogout();
					GameUsing gu = new GameUsing();

					gu.go();
				}
			}

		});

		ranking.addActionListener(new ActionListener() { //랭킹 버튼 클릭시 랭킹을 보여준다.
			public void actionPerformed(ActionEvent click) {

				JFrame ranking = new JFrame();
				RankPanel rp = new RankPanel();
				ranking.getContentPane().add(rp);
				ranking.setBackground(Color.white);
				ranking.setSize(400,420);
				ranking.setVisible(true);	
				ranking.setTitle("랭킹"); 


				try { //게임 종료후 승률을 update 해준다 
					Connection conn = null;
					Statement stmt = null;


					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/game", "root", "qwert");
					stmt = conn.createStatement();

					stmt.executeUpdate( "UPDATE people SET rate = win/(win+lose+draw) * 100 WHERE name = '" + name + "';");

					ResultSet rs = stmt.executeQuery( "Select email, rate FROM people;");


					while(rs.next()) {
						name2 = rs.getString("email");
						rate = rs.getInt("rate");

						rank.add(new RankInfo(name2,rate));

					}
					rs.close();

					int rankSize = rank.size();
					for(int i=0; i<rankSize; i++) {
						if(rank.get(i).name.equalsIgnoreCase(name2)){
							rank.remove(i);
						}

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
		});



		start.addActionListener(new ActionListener() { //시작버튼의 리스너 
			public void actionPerformed(ActionEvent click) {


				if(level1.isSelected() == true) { //3*3 버튼 클릭시 
					levelSize = 3;
				}
				else if(level2.isSelected() == true) { //4*4 버튼 클릭시 
					levelSize = 4;
				}
				else if(level3.isSelected() == true) { //6*6 버튼 클릭시 
					levelSize = 6;
				}		if(icon1Btn.isSelected() == true){ //선택한 아이콘에 따라 내 아이콘을 셋팅해준다.

					myIcon = new ImageIcon(icon1);
				}
				else if(icon2Btn.isSelected() == true){
					myIcon = icon2Pic;
				}
				else if(icon3Btn.isSelected() == true){
					myIcon = icon3Pic;
				}
				else if(icon4Btn.isSelected() == true){
					myIcon = icon4Pic;
				}
				GameStarter gs = new GameStarter(name, levelSize, myIcon); //게임화면 나온다 
				gs.setVisible(true);
				setVisible(false);

			}


		});


		processLogin();
		setVisible(true);

		System.out.println("users의 수 = " + users.length);

		start.setEnabled(false);
		if(users.length <2) start.setEnabled(true);



	} 

	private void setUpNetworking() {  
		try {
			//  sock = new Socket("220.69.203.11", 5000); //118.34.54.234
			sock = new Socket("127.0.0.1", 5000);			// 소켓 통신을 위한 포트는 5000번 사용키로 함
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "서버접속에 실패하였습니다. 접속을 종료합니다.");
			ex.printStackTrace();
			dispose();		// 네트워크가 초기 연결 안되면 클라이언트 강제 종료
		}
	} // close setUpNetworking   

	// 로그인 처리
	private void processLogin() {
		user = name;
		
		try {
			inOut = user + " 님이 입장하셨습니다.";
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, "", inOut));
			writer.flush(); //소켓이 비워진다 (다음 내용을 받아들이기 위함)

		} catch(Exception ex) {

			ex.printStackTrace();
		}
		
		
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN, user, "", ""));
			writer.flush(); //소켓이 비워진다 (다음 내용을 받아들이기 위함)
			// frame.setTitle(frameTitle + " (로그인 : " + user + ")");
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "로그인 중 서버접속에 문제가 발생하였습니다.");
			ex.printStackTrace();
		}
	}


	private void processLogout() {
		
		
		try {
			inOut = user + " 님이 퇴장하셨습니다.";
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, "", inOut));
			writer.flush(); //소켓이 비워진다 (다음 내용을 받아들이기 위함)

		} catch(Exception ex) {

			ex.printStackTrace();
		}
		
		
		

		try {
			
			
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGOUT, user, "", ""));
			writer.flush();
			// 연결된 모든 스트림과 소켓을 닫고 프로그램을 종료 함
			writer.close(); reader.close(); sock.close();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "로그아웃 중 서버접속에 문제가 발생하였습니다. 강제종료합니다");
			ex.printStackTrace();
		} finally {
			System.exit(100);			// 클라이언트 완전 종료 
		}

	}
	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String to = (String) counterParts.getSelectedValue();
			//String to = users[1];
			if (to == null) {
				JOptionPane.showMessageDialog(null, "송신할 대상을 선택한 후 메시지를 보내세요");
				return;
			}
			try {
				incoming.append(user + " : " + outgoing.getText() + "\n"); // 나의 메시지 창에 보이기
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
				writer.flush();
				outgoing.setText("");
				outgoing.requestFocus();
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
				ex.printStackTrace();
			}
		}
	}  // close SendButtonListener inner class

	// 서버에서 보내는 메시지를 받는 스레드 작업을 정의하는 클래스
	public class IncomingReader implements Runnable {
		public void run() {

			ChatMessage.MsgType type;
			try {
				while (true) {
					message = (ChatMessage) reader.readObject();      
					type = message.getType();

					if (type == ChatMessage.MsgType.LOGIN_FAILURE) {	 // 로그인이 실패한 경우라면
						JOptionPane.showMessageDialog(null, "Login이 실패하였습니다. 다시 로그인하세요");

					} else if (type == ChatMessage.MsgType.SERVER_MSG) { // 메시지를 받았다면 보여줌
						if (message.getSender().equals(user)) continue; 	// 내가 보낸 편지면 보일 필요 없음
						incoming.append(message.getSender() + " : " + message.getContents() + "\n");

					} 

					else if (type == ChatMessage.MsgType.ALL) {

						turn = message.getTurn();
						index = message.getIndex();
						System.out.println("turn = " + turn);
						System.out.println("myIcon = " + myIcon);
						turnShow.setIcon(message.getIcon());

						if(!(turn % 2 == 0)) { 						//user1의 차례 
							setText = "X"; 
							newIcon = message.getIcon();	
							myIndex = message.getIndex();
							System.out.println("나의인덱스 = " + myIndex);
							myList.add(myIndex);
							nowTurn = turn;
							turnShow.setIcon(yourIcon);

							for(int i=0; i<yourList.size(); i++) { //user2가 눌렀던 버튼을 또 누를 경우 
								if(yourList.get(i)==myIndex){
									setText = "O"; //user2의 아이콘으로 셋팅해주고 
									turn = nowTurn-1; //turn을 ++되지않고 원래대로 전달되도록 -해준다. 
								}
							}

						}
						else { 										//user2의차례 
							setText = "O";
							yourIcon = message.getIcon();
							yourIndex = message.getIndex();
							System.out.println("상대인덱스 = " + yourIndex);
							yourList.add(yourIndex);

							nowTurn = turn;
							for(int j=0; j<myList.size(); j++) { //user1이 눌렀던 버튼을 또 누를 경우 
								if(myList.get(j)==yourIndex){

									setText = "X";	
									turn = nowTurn-1;
								}
							}

							turnShow.setIcon(newIcon);

						}
						btnEmptyClicked = true;	
						btnEmpty[index].setText(setText);

						if(btnEmpty[index].getText()=="X") btnEmpty[index].setIcon(newIcon); //X면 user1의 아이콘 셋팅 
						else btnEmpty[index].setIcon(yourIcon); //O면 user2 아이콘

						if(btnEmptyClicked)	{ //버튼이 눌리면 승,패,무승부,를 체크하고 false로 셋팅
							checkWin();
							btnEmptyClicked = false;
						}

					}

					else if (type == ChatMessage.MsgType.CHECK){ //승부를 체크한다

						if(message.getCheck()==true) {
							if(message.getSender().equals(name)==true ) {
								mesg = message.getSender() + "님이 이겼습니다!";
								winSound.play();
								start.setEnabled(true);

							}
							else{		
								mesg = message.getReceiver() + "님이 졌습니다!";
								loseSound.play();
								start.setEnabled(true);

							}
							JOptionPane.showMessageDialog(null, mesg);
							break;
						}
						if(message.getCheck()==false) {
							if(message.getSender().equals(name)==false ) {
								mesg = message.getSender() + "님이 졌습니다!";
								winSound.play();
								start.setEnabled(true);
							}
							else{		
								mesg = message.getReceiver() + "님이 이겼습니다!";
								loseSound.play();
								start.setEnabled(true);

							}
							JOptionPane.showMessageDialog(null, mesg);
							break;


						}
					}
					else if (type == ChatMessage.MsgType.LOGIN_LIST) {
						// 유저 리스트를 추출 해서 counterParts 리스트에 넣어 줌.
						// 나는  빼고 (""로 만들어 정렬 후 리스트 맨 앞에 오게 함)
						users = message.getContents().split("/");

						for (int i=0; i<users.length; i++) {
							if (user.equals(users[i]))users[i] = "";

						}
						users = sortUsers(users);		// 유저 목록을 쉽게 볼 수 있도록 정렬해서 제공
						users[0] =  ChatMessage.ALL;	// 리스트 맨 앞에 "전체"가 들어가도록 함
						counterParts.setListData(users);
						repaint();
					} else if (type == ChatMessage.MsgType.NO_ACT){

						// 아무 액션이 필요없는 메시지. 그냥 스킵

					} else {
						// 정체가 확인되지 않는 이상한 메시지
						throw new Exception("서버에서 알 수 없는 메시지 도착했음");
					}
				} // close while
			} catch(Exception ex) {
				System.out.println("클라이언트 스레드 종료");		// 프레임이 종료될 경우 이를 통해 스레드 종료

			}
		} // close run

		// 주어진 String 배열을 정렬한 새로운 배열 리턴
		private String [] sortUsers(String [] users) {
			String [] outList = new String[users.length];
			ArrayList<String> list = new ArrayList<String>();
			for (String s : users) {
				list.add(s);
			}
			Collections.sort(list);				// Collections.sort를 사용해 한방에 정렬
			for (int i=0; i<users.length; i++) {
				outList[i] = list.get(i);
			}
			return outList;
		}
	} // close inner class     	


	public class RankPanel extends JPanel { //랭킹프레임에 더해줄 랭킹패널
		public void paintComponent(Graphics g) {

			Image img = new ImageIcon(getClass().getResource(rankImage)).getImage(); 
			g.drawImage(img,0,0,400,390,this);

			g.setColor(Color.black);
			g.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 25));

			Collections.sort(rank,Collections.reverseOrder());

			for(int i=0; i<rank.size(); i++) { //랭킹순서대로 출력해주는 부분 
				g.drawString(" "+(i+1), 30, 137+(i*25)); //랭킹 인덱스 출력 
				g.drawString(rank.get(i).name,75,137+(i*25)); // 이메일(id) 출력 
				g.drawString(""+rank.get(i).rankScore,320,137+(i*25)); //승률 출력


			}
		}
	}


	//랭킹정보를 담을 클래스
	public class RankInfo implements Comparable<RankInfo>{ 
		String name;
		int rankScore;

		RankInfo() {
			this.name = null;
			this.rankScore = 0;
		}
		RankInfo(String name, int rankScore) {
			this.name = name;
			this.rankScore = rankScore;
		}

		public int compareTo(RankInfo r) {
			return this.rankScore - r.rankScore;

		}

	}
	public class gamePanel extends JPanel implements ActionListener{ //실제 게임을 실행하는 게임 패널 

		public gamePanel(int s) {
			btPanel = new JPanel();
			getContentPane().add(btPanel);

			levelSize = s;
			size = levelSize * levelSize;

			setBounds(0,0,700,550);
			setFocusable(true);
			getContentPane().add (this);

			for(int i=1; i<=size; i++)	{ //버튼이 선택한 size 만큼 생성된다

				if(size == 9) { // 3*3에서는 아이템 전 불가 
					use.setEnabled(false);
					buy.setEnabled(false);
				}

				btnEmpty[i] = new JButton();	
				btnEmpty[i].setBackground(Color.WHITE);
				btnEmpty[i].addActionListener(this);
				btPanel.add(btnEmpty[i]);
			}
			btPanel.setBounds(50,150,300,300);		
			btPanel.setLayout(new GridLayout(levelSize, levelSize, 1, 1));


			// 메시지 디스플레이 창
			incoming = new JTextArea(20,15);
			incoming.setLineWrap(true);
			incoming.setWrapStyleWord(true);
			incoming.setEditable(false);
			incoming.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
			JScrollPane qScroller = new JScrollPane(incoming);
			qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			qScroller.setAutoscrolls(true);
			
			// 대화 상대 목록. 초기에는 "전체" - ChatMessage.ALL 만 있음
			String[] list = {ChatMessage.ALL};
			counterParts = new JList(list);
			JScrollPane cScroller = new JScrollPane(counterParts);
			cScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			cScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			cScroller.setAutoscrolls(true);
			counterParts.setVisibleRowCount(5);
			counterParts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			counterParts.setFixedCellWidth(100);

			// 메시지 전송을 위한 버튼
			JButton sendButton = new JButton();
			sendButton.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 20));
			sendButton.addActionListener(new SendButtonListener());
			sendButton.setIcon(sendIcon);
			sendButton.setBorderPainted(false);
			sendButton.setContentAreaFilled(false);

			// 메시지 디스플레이 창
			outgoing = new JTextArea(20,15);
			outgoing.setLineWrap(true);
			outgoing.setWrapStyleWord(true);
			outgoing.setEditable(true);
			outgoing.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
			JScrollPane oScroller = new JScrollPane(outgoing);
			oScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			oScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			oScroller.setAutoscrolls(true);

			// GUI 배치
			JPanel mainPanel = new JPanel();

			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


			JPanel upperPanel = new JPanel();
			upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
			upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			upperPanel.setBounds(400,600,50,50);
			upperPanel.setOpaque(false);

			JPanel lowerPanel = new JPanel();
			lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
			lowerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
			buttonPanel.setBounds(0,0,200,200);
			JPanel userPanel = new JPanel();
			userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));


			userPanel.add(new JLabel("대화상대목록"));
			userPanel.add(Box.createRigidArea(new Dimension(0,5)));
			userPanel.add(cScroller);
			userPanel.setOpaque(false);

			inputPanel.add(new JLabel("메시지입력"));
			inputPanel.add(Box.createRigidArea(new Dimension(0,5)));
			inputPanel.add(oScroller);
			inputPanel.setOpaque(false);


			buttonPanel.add(sendButton);
			buttonPanel.add(Box.createRigidArea(new Dimension(0,30)));
			//   buttonPanel.add(logButton);
			buttonPanel.setOpaque(false);

			lowerPanel.add(userPanel);
			lowerPanel.add(Box.createRigidArea(new Dimension(5,0)));
			lowerPanel.add(inputPanel);
			lowerPanel.add(Box.createRigidArea(new Dimension(10,0)));
			lowerPanel.add(buttonPanel);
			lowerPanel.setOpaque(false);
			upperPanel.add(qScroller);

			mainPanel.add(upperPanel);
			mainPanel.add(lowerPanel);

			// 네트워킹을 시동하고, 서버에서 메시지를 읽을 스레드 구동
			setUpNetworking();
			Thread readerThread = new Thread(new IncomingReader());
			readerThread.start();

			// 클라이언드 프레임 창 조정
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//getContentPane().add(BorderLayout.EAST, mainPanel);
			setLayout(null);
			mainPanel.setBounds(345,130,340,360);

			mainPanel.setOpaque(false);
			add(mainPanel);

		}

		public void paintComponent(Graphics g) {
			Image img = new ImageIcon(getClass().getResource(MAIN_PIC)).getImage(); //배경 이미지 셋팅 
			g.drawImage(img,0,0,FWidth,FHeight,this);

		}

		public void actionPerformed(ActionEvent click)	{ //버튼 클릭 횟수와 turn을 설정해주는 부분
			Object source = click.getSource();
			//nowTurn = turn;

			for(int i=1; i<=(size) ; i++)	{

				if(source == btnEmpty[i] && turn <	(levelSize * levelSize) + 1)	{ //i번째 버튼이 눌리고 turn이 버튼의 개수+1 보다 적은 경우
					btnEmptyClicked = true;									//                 (i=1부터 시작하므로 )
					if(!(turn % 2 == 0)) {//turn이 1,3,5,7,9일 경우 (홀수일경우) 
						btnEmpty[i].setText("X"); //i번째 버튼이 X로 셋팅된다

						myI = i; //내차례
						turnShow.setIcon(myIcon);
					}

					else { //turn이 2,4,6,8일 경우 (짝수일 경우)
						btnEmpty[i].setText("O");
						yourI = i; //상대방차례 
					}



					if(myI == yourI) return; //내가 누른 버튼의 인덱스와 상대가 누른 버튼의 인덱스가 같으면 turn을 그대로 둔다. 
					else turn++; //같지 않으면 다음 턴으로 넘겨준다.
					//nowTurn = turn;


					try {
						String to = users[1];
						writer.writeObject(new ChatMessage(ChatMessage.MsgType.ALL,name,to,"",turn,myIcon,i));
						writer.flush(); //소켓이 비워진다 (다음 내용을 받아들이기 위함)

					} catch(Exception ex) {

						ex.printStackTrace();
					}


					if(btnEmpty[i].getText()=="X") btnEmpty[i].setIcon(myIcon);
					if(btnEmpty[i].getText()=="O") btnEmpty[i].setIcon(yourIcon);
					gamePanel.requestFocus();

				}
			}

			if(btnEmptyClicked)	{ //버튼이 눌리면 승,패,무승부,를 체크하고 false로 셋팅
				checkWin();
				btnEmptyClicked = false;
			}
		}	



	}

	public void checkWin()	{
		if(levelSize == 3) {
			prize = 300;

			for(int i=0; i<(levelSize * levelSize) -1; i++)	{
				if(

						//처음 눌린 버튼과 두번째 눌린 버튼, 세번째 눌린 버튼의 text가 같으면 이긴다. 
						!btnEmpty[winCombo3[i][0]].getText().equals("") &&
						btnEmpty[winCombo3[i][0]].getText().equals(btnEmpty[winCombo3[i][1]].getText()) &&
						//							
						btnEmpty[winCombo3[i][1]].getText().equals(btnEmpty[winCombo3[i][2]].getText())

				)	
				{
					win = true;
					wonNumber1 = winCombo3[i][0]; //
					wonNumber2 = winCombo3[i][1];
					wonNumber3 = winCombo3[i][2];

					//		System.out.println("wonNumber1 = " + wonNumber1);
					//		System.out.println("wonNumber2 = " + wonNumber2);
					//		System.out.println("wonNumber3 = " + wonNumber3);
					break;


				}
			}
		}
		else if(levelSize == 4) {
			prize = 400;

			for(int i=0; i<(levelSize * levelSize) -6; i++)	{
				if(


						!btnEmpty[winCombo4[i][0]].getText().equals("") &&
						btnEmpty[winCombo4[i][0]].getText().equals(btnEmpty[winCombo4[i][1]].getText()) &&
						//								
						btnEmpty[winCombo4[i][1]].getText().equals(btnEmpty[winCombo4[i][2]].getText()) &&
						btnEmpty[winCombo4[i][2]].getText().equals(btnEmpty[winCombo4[i][3]].getText())

				)	
				{
					win = true;
					wonNumber1 = winCombo4[i][0];
					wonNumber2 = winCombo4[i][1];
					wonNumber3 = winCombo4[i][2];
					wonNumber4 = winCombo4[i][3];
					break;
				}
			}
		}

		else  { //levelSize == 6 이면 
			prize = 600;

			for(int i=0; i<(levelSize * levelSize) -22; i++)	{
				if(
						!btnEmpty[winCombo6[i][0]].getText().equals("") &&
						btnEmpty[winCombo6[i][0]].getText().equals(btnEmpty[winCombo6[i][1]].getText()) &&
						//								
						btnEmpty[winCombo6[i][1]].getText().equals(btnEmpty[winCombo6[i][2]].getText()) &&
						btnEmpty[winCombo6[i][2]].getText().equals(btnEmpty[winCombo6[i][3]].getText()) &&
						btnEmpty[winCombo6[i][3]].getText().equals(btnEmpty[winCombo6[i][4]].getText()) &&
						btnEmpty[winCombo6[i][4]].getText().equals(btnEmpty[winCombo6[i][5]].getText())

				)	
				{
					win = true;
					wonNumber1 = winCombo6[i][0];
					wonNumber2 = winCombo6[i][1];
					wonNumber3 = winCombo6[i][2];
					wonNumber4 = winCombo6[i][3];
					wonNumber5 = winCombo6[i][4];
					wonNumber6 = winCombo6[i][5];
					break;
				}
			}
		}

		if(win || (!win && turn>(size)))	{



			if(win)	{
				if(turn % 2 == 0) { //이긴경우 


					try {
						Connection conn = null;
						Statement stmt = null;


						Class.forName("com.mysql.jdbc.Driver");
						conn = DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/game", "root", "qwert");
						stmt = conn.createStatement();

						stmt.executeUpdate( "UPDATE people SET win = win+ 1 WHERE name = '" + name + "';");
						stmt.executeUpdate( "UPDATE people SET money = money + " + prize + " WHERE name = '" + name + "';");

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
				else	{

					win = false; //진 경우 


					try {
						Connection conn = null;
						Statement stmt = null;


						Class.forName("com.mysql.jdbc.Driver");
						conn = DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/game", "root", "qwert");
						stmt = conn.createStatement();

						stmt.executeUpdate( "UPDATE people SET lose = lose+ 1 WHERE name = '" + name + "';");
						stmt.executeUpdate( "UPDATE people SET money = money - " + prize + " WHERE name = '" + name + "';");

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




			else if(!win && turn>(size) )	{ //동점인 경우 
				mesg = "동점이에여";
				drawSound.play();
				JOptionPane.showMessageDialog(null, mesg);

				try {
					Connection conn = null;
					Statement stmt = null;

					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/game", "root", "qwert");
					stmt = conn.createStatement();

					stmt.executeUpdate( "UPDATE people SET draw = draw + 1 WHERE name = '" + name + "';");
					stmt.executeUpdate( "UPDATE people SET money = money + " + prize/2 + " WHERE name = '" + name + "';");
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
			try { //게임 종료후 승률을 update 해준다 
				Connection conn = null;
				Statement stmt = null;


				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/game", "root", "qwert");
				stmt = conn.createStatement();

				stmt.executeUpdate( "UPDATE people SET rate = win/(win+lose+draw) * 100 WHERE name = '" + name + "';");


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
			try {
				String to = users[1];
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CHECK,name,to,"",win));
				writer.flush(); //소켓이 비워진다 (다음 내용을 받아들이기 위함)

			} catch(Exception ex) {

				ex.printStackTrace();
			}
			for(int i=1; i<=(levelSize * levelSize); i++)	{
				btnEmpty[i].setEnabled(false); //게임종료후 더이상 클릭되지 못하게 한다.
			}
		}

	}
}
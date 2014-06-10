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
	private AudioClip backgroundSound;						// ���� ��� ����
	private final String WIN_SOUND = "/res/heart.wav";
	private AudioClip winSound;		
	private final String DRAW_SOUND = "/res/draw.wav";
	private AudioClip drawSound;		
	private final String LOSE_SOUND = "/res/lose.wav";
	private AudioClip loseSound;		
	private final String ITEM_SOUND = "/res/item.wav";
	private AudioClip itemSound;	
	private final String MAIN_PIC = "/res/gamemain.jpg"; 	//Icon ���� 
	private final String icon1 = "src/p.png";
	private final String rankImage = "/res/rankImage.jpg";
	private final String buyImage = "src/buy.png";
	private final String useImage = "src/use.png";
	private final String icon2 = "src/k.png";
	private final String icon3 = "src/�Ƿη�.png";
	private final String icon4 = "src/�ޱ׸�����.png";
	private final String nullImage = "src/white.jpg";
	private final String mini_icon1 = "src/minip.jpg";
	private final String mini_icon2 = "src/minik.jpg";
	private final String mini_icon3 = "src/mini�Ƿη�.jpg";
	private final String mini_icon4 = "src/mini�ޱ׸�����.png";
	private final String q = "src/question.png";

	private final String s = "src/send.png";
	

	private final int FWidth = 710; 				// ��ü frame�� ��
	private final int FHeight = 560; 			    // ��ü frame�� ����
	public int levelSize; 							//���� ������ 
	public int size; 								//levelSize * levelSIze , �� ��ư�� ���� 


	Icon nullIcon = new ImageIcon(nullImage); 		//������ ������ ���� ������
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
	JLabel myTurn; //turn�� ǥ���ϱ� ���� ����
	JLabel turnShow;

	JButton btnEmpty[] = new JButton[50]; 			//���ӽ� ���� ��ư 
	JButton again = new JButton("AGAIN");
	JButton ranking = new JButton("��ŷ����");
	JButton start = new JButton("START");
	JButton buy = new JButton("����");
	JButton use = new JButton("���");
	JButton logOut = new JButton("�α׾ƿ�");
	JButton question = new JButton();

	JPanel gamePanel; //�г� 
	JPanel btPanel;


	String balance;				//������ ���� �� �ܾ� 
	String mesg; 				//�� �и� �Ǵ��� JOptionPane�� ����� �޼��� 

	JComboBox num; 				//�������� ��Ÿ���ִ� ����
	JTextField itemPrice;
	JTextField myItemNum;
	String priceS; 				//������ ������ ǥ�� 

	int itemNum, price, myMoney;
	int prize; 					//���� ���н� �ִ� �ݾװ� ���� �ݾ�
	int itemN; 					//������ ������ ������ �޴� ���� 
	int winCnt, loseCnt, drawCnt, rate; // ��,��,���º� Ƚ���� ���ش�. 
	int turn = 0;//���� 
	int wonNumber1 = 1, wonNumber2 = 1, wonNumber3 = 1, wonNumber4 = 1, wonNumber5 = 1, wonNumber6 = 1;
	//winCombo ��ư�� Ȯ���ϱ� ���� ����
	boolean win = false; 											//���� �Ǵ��� ���� �ʿ��� ���� 
	boolean btnEmptyClicked = false; 								//��ư�� ��� Ŭ���Ǿ����� Ȯ�� 


	ArrayList<RankInfo> rank = new ArrayList<RankInfo>();
	String number[] = {"����","1","2","3","4","5","6","7","8","9","10"}; //�������� �ִ�� �� �� �ִ� ������ 10�� 


	String name; 			    //�α��� �� ����� �̸� 

	int myI, yourI;			    //���� ��ư �ε����� ����� ��ư �ε��� 
	String setText; 			//��ư�� �ؽ�Ʈ�� �ֱ� ���� �ʿ��� ���� 
	String[] users; 			//�α����� ����� ��� 
	ArrayList<Integer> myList = new ArrayList<Integer>(); //user1�� ������ �ε������� ����
	ArrayList<Integer> yourList = new ArrayList<Integer>(); //user2�� ������ �ε������� ����

	ChatMessage message;   
	JTextArea incoming;			// ���ŵ� �޽����� ����ϴ� ��
	JTextArea outgoing;			// �۽��� �޽����� �ۼ��ϴ� ��
	JList counterParts;			// ���� �α����� ä�� ������� ��Ÿ���� ����Ʈ.
	ObjectInputStream reader;	// ���ſ� ��Ʈ��
	ObjectOutputStream writer;	// �۽ſ� ��Ʈ��
	Socket sock;				// ���� ����� ����
	String user;				// �� Ŭ���̾�Ʈ�� �α��� �� ������ �̸�


	int index; 					//��ư�� �ε���
	Icon newIcon; 				//�������� �о�´� 
	int myIndex, yourIndex; 	//user1, user2�� ��ư �ε����� ���� �����´�. 
	int nowTurn; 				//������ turn���� 
	String name2; 				//��ŷ������ ������ �̸��� ������ �����ؾ��� �� �ʿ��� ���� 
	String inOut;


	final int winCombo3[][] = new int[][]	{ 					// 3*3 �� �� �̱�� ��� 
			{1, 2, 3}, 			{1, 4, 7}, 		{1, 5, 9},
			{4, 5, 6}, 			{2, 5, 8}, 		{3, 5, 7},
			{7, 8, 9}, 			{3, 6, 9}
			/*������ ä��� */	/*������ ä��� */ /*�밢�� ä���*/
	};

	final int winCombo4[][] = new int[][]	{ 					// 4*4 �� �� �̱�� ��� 
			{1, 2, 3, 4}, 			{1, 5, 9, 13}, 		{1, 6, 11, 16},
			{5, 6, 7, 8}, 			{2, 6, 10, 14}, 	{4, 7, 10, 13},
			{9, 10, 11, 12}, 		{3, 7, 11, 15},		
			{13, 14, 15, 16},		{4, 8, 12, 16}

	};

	final int winCombo6[][] = new int[][]	{ 					//6*6 �ϋ� �̱�� ��� 
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
			System.out.println("���� ���� �ε� ����");
		}



		this.name = name;
		levelSize = lev;

		myIcon = icon;
		gamePanel = new gamePanel(levelSize);


		//gui���� 
		setTitle("Tic Tac Toe" ); 
		setBounds(0, 0, FWidth,FHeight);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		again.setBounds(170,460,87,25);
		again.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 18));
		add(again);
		start.setBounds(265,30,75,25);
		start.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 18));
		add(start);
		ranking.setBounds(75,460,87,25);
		ranking.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 20));
		add(ranking);
		logOut.setBounds(265,460,75,25);
		logOut.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 21));
		add(logOut);
		buy.setBounds(580,25,46,31);
		buy.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 18));
		buy.setIcon(buyPic);
		buy.setBorderPainted(false);
		buy.setContentAreaFilled(false);
		add(buy);
		use.setBounds(580,55,46,31);
		use.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 18));
		use.setIcon(usePic);
		use.setBorderPainted(false);
		use.setContentAreaFilled(false);
		add(use);
		question.setBounds(630,30,56,30);
		question.setBorderPainted(false);
		question.setContentAreaFilled(false);
		question.setIcon(ques);
		add(question);


		level = new JLabel("���̵�");
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


		itemBuy = new JLabel("������ ��");
		itemBuy.setBounds(400,30,60,20);
		add(itemBuy);

		myTurn = new JLabel("TURN");
		myTurn.setBounds(460, 90, 60, 50);
		myTurn.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 25));
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

		myItem = new JLabel("���������� ��");
		myItem.setBounds(435,60,90,20);
		add(myItem);
		num = new JComboBox(number);
		num.addActionListener(new ActionListener() { //������ ���Թ�ư Ŭ���� ������ 
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				itemNum =cb.getSelectedIndex(); //���õ� �޺��ڽ��� �ε����� �����´�

				price = 100 * itemNum;
				priceS = price + "��";
				itemPrice.setText(priceS);

			}
		});
		num.setBounds(460, 30, 56, 21);
		add(num);

		try { 										//������� ������ �ִ� ������ ������ select �Ѵ�. 
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

			if(itemN <= 0) { //��� ������ item�� ������ 0�� ��� ������� ���ϰ� �Ѵ�.
				use.setEnabled(false);
			}
			else {
				use.setEnabled(true); //��� ������ item ������ 0���� Ŭ ��� ��� �����ϰ� �Ѵ�.
				if(size == 9) use.setEnabled(false); //3*3������ ������ �� �Ұ� 

			}


		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." +
					cnfe.getMessage());
		}
		catch (SQLException se) {
			System.out.println(" SQL����. " + se.getMessage());
		}

		catch(NumberFormatException nfe) {


		}



		buy.addActionListener(new ActionListener() { //���� ��ư�� ������
			public void actionPerformed(ActionEvent click) {

				try {
					Connection conn = null;
					Statement stmt = null;


					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/game", "root", "qwert");
					stmt = conn.createStatement();


					ResultSet rs = stmt.executeQuery( "Select money FROM people WHERE name = '" + name + "';");
					//���� �� �ִ��� Ȯ��

					while(rs.next()) {
						myMoney = rs.getInt("money");
						//    System.out.println("myMoney = " + myMoney);

					}
					rs.close();
					if(myMoney >= price) { //������ �ִ� ���� �������� ������ ������ 
						stmt.executeUpdate( "UPDATE people SET money = money - " + price + " WHERE name = '" + name+"';");
						stmt.executeUpdate( "UPDATE people SET item_1 = item_1 + " + itemNum + " WHERE name = '" + name + "';");
						balance = myMoney - price + "";
						JOptionPane jo = new JOptionPane();        
						jo.showMessageDialog(null, "���ԿϷ�^^! �ܾ��� " + balance + "�Դϴ�.");
						itemN = itemN + itemNum;
						myItemNum.setText(itemN + "");
					}

					else { //������ �ִ� ���� �����ϸ� 

						JOptionPane jo = new JOptionPane();        
						jo.showMessageDialog(null, "�Ӵϰ� �����մϴ� �ФФ�");
					}


					if(itemN <= 0) {//�������� ������ ����ư enabled �Ѵ� 
						use.setEnabled(false);
					}
					else use.setEnabled(true);

				}
				catch (ClassNotFoundException cnfe) {
					System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." +
							cnfe.getMessage());
				}
				catch (SQLException se) {
					System.out.println(" SQL����. " + se.getMessage());
				}

				catch(NumberFormatException nfe) {


				}




			}
		});

		use.addActionListener(new ActionListener() { //����ư�� ������
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
					//���� ������ �ִ� �������� ������ -1 ���ش� 

				}
				catch (ClassNotFoundException cnfe) {
					System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." +
							cnfe.getMessage());
				}
				catch (SQLException se) {
					System.out.println(" SQL����. " + se.getMessage());
				}

				catch(NumberFormatException nfe) {


				}

				turn++; //���ʸ� �� ���ʷ� �����ش� 
			

				//   System.out.println("������ ��� �� turn = " + turn);
				itemN = itemN-1;
				myItemNum.setText( itemN + "");

				if(itemN <= 0) { //�������� ������ ����ư�� enabled �Ѵ�. 
					use.setEnabled(false);
				}
				else use.setEnabled(true);




			}
		});

		iconInfo = new JLabel("�����ܼ���");
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



		question.addActionListener(new ActionListener() { //?��ư ������ ������ ����� ������ ���´�.  
			public void actionPerformed(ActionEvent click) {

				new ImageChanger();

			}
		});



		again.addActionListener(new ActionListener() { //again ��ư ������ ������ ����� �ȴ�. 
			public void actionPerformed(ActionEvent click) {

				GameStarter gs = new GameStarter(name, levelSize, myIcon); //����ȭ���� ���� ���´� 
				myList.removeAll(myList);
				yourList.remove(yourList);

				gs.setVisible(true);
				setVisible(false);

			}
		});

		logOut.addActionListener(new ActionListener() { //logOut ��ư ������ ù ȭ�� ��Ÿ���� 
			public void actionPerformed(ActionEvent click) {


				int option = JOptionPane.showConfirmDialog(null, "�α׾ƿ� �Ͻðڽ��ϱ�??","�α׾ƿ�",JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)	{

					setVisible(false);
					processLogout();
					GameUsing gu = new GameUsing();

					gu.go();
				}
			}

		});

		ranking.addActionListener(new ActionListener() { //��ŷ ��ư Ŭ���� ��ŷ�� �����ش�.
			public void actionPerformed(ActionEvent click) {

				JFrame ranking = new JFrame();
				RankPanel rp = new RankPanel();
				ranking.getContentPane().add(rp);
				ranking.setBackground(Color.white);
				ranking.setSize(400,420);
				ranking.setVisible(true);	
				ranking.setTitle("��ŷ"); 


				try { //���� ������ �·��� update ���ش� 
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
					System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." +
							cnfe.getMessage());
				}
				catch (SQLException se) {
					System.out.println(" SQL����. " + se.getMessage());
				}

				catch(NumberFormatException nfe) {


				}
			}
		});



		start.addActionListener(new ActionListener() { //���۹�ư�� ������ 
			public void actionPerformed(ActionEvent click) {


				if(level1.isSelected() == true) { //3*3 ��ư Ŭ���� 
					levelSize = 3;
				}
				else if(level2.isSelected() == true) { //4*4 ��ư Ŭ���� 
					levelSize = 4;
				}
				else if(level3.isSelected() == true) { //6*6 ��ư Ŭ���� 
					levelSize = 6;
				}		if(icon1Btn.isSelected() == true){ //������ �����ܿ� ���� �� �������� �������ش�.

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
				GameStarter gs = new GameStarter(name, levelSize, myIcon); //����ȭ�� ���´� 
				gs.setVisible(true);
				setVisible(false);

			}


		});


		processLogin();
		setVisible(true);

		System.out.println("users�� �� = " + users.length);

		start.setEnabled(false);
		if(users.length <2) start.setEnabled(true);



	} 

	private void setUpNetworking() {  
		try {
			//  sock = new Socket("220.69.203.11", 5000); //118.34.54.234
			sock = new Socket("127.0.0.1", 5000);			// ���� ����� ���� ��Ʈ�� 5000�� ���Ű�� ��
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "�������ӿ� �����Ͽ����ϴ�. ������ �����մϴ�.");
			ex.printStackTrace();
			dispose();		// ��Ʈ��ũ�� �ʱ� ���� �ȵǸ� Ŭ���̾�Ʈ ���� ����
		}
	} // close setUpNetworking   

	// �α��� ó��
	private void processLogin() {
		user = name;
		
		try {
			inOut = user + " ���� �����ϼ̽��ϴ�.";
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, "", inOut));
			writer.flush(); //������ ������� (���� ������ �޾Ƶ��̱� ����)

		} catch(Exception ex) {

			ex.printStackTrace();
		}
		
		
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN, user, "", ""));
			writer.flush(); //������ ������� (���� ������ �޾Ƶ��̱� ����)
			// frame.setTitle(frameTitle + " (�α��� : " + user + ")");
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "�α��� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
			ex.printStackTrace();
		}
	}


	private void processLogout() {
		
		
		try {
			inOut = user + " ���� �����ϼ̽��ϴ�.";
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, "", inOut));
			writer.flush(); //������ ������� (���� ������ �޾Ƶ��̱� ����)

		} catch(Exception ex) {

			ex.printStackTrace();
		}
		
		
		

		try {
			
			
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGOUT, user, "", ""));
			writer.flush();
			// ����� ��� ��Ʈ���� ������ �ݰ� ���α׷��� ���� ��
			writer.close(); reader.close(); sock.close();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "�α׾ƿ� �� �������ӿ� ������ �߻��Ͽ����ϴ�. ���������մϴ�");
			ex.printStackTrace();
		} finally {
			System.exit(100);			// Ŭ���̾�Ʈ ���� ���� 
		}

	}
	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String to = (String) counterParts.getSelectedValue();
			//String to = users[1];
			if (to == null) {
				JOptionPane.showMessageDialog(null, "�۽��� ����� ������ �� �޽����� ��������");
				return;
			}
			try {
				incoming.append(user + " : " + outgoing.getText() + "\n"); // ���� �޽��� â�� ���̱�
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
				writer.flush();
				outgoing.setText("");
				outgoing.requestFocus();
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
				ex.printStackTrace();
			}
		}
	}  // close SendButtonListener inner class

	// �������� ������ �޽����� �޴� ������ �۾��� �����ϴ� Ŭ����
	public class IncomingReader implements Runnable {
		public void run() {

			ChatMessage.MsgType type;
			try {
				while (true) {
					message = (ChatMessage) reader.readObject();      
					type = message.getType();

					if (type == ChatMessage.MsgType.LOGIN_FAILURE) {	 // �α����� ������ �����
						JOptionPane.showMessageDialog(null, "Login�� �����Ͽ����ϴ�. �ٽ� �α����ϼ���");

					} else if (type == ChatMessage.MsgType.SERVER_MSG) { // �޽����� �޾Ҵٸ� ������
						if (message.getSender().equals(user)) continue; 	// ���� ���� ������ ���� �ʿ� ����
						incoming.append(message.getSender() + " : " + message.getContents() + "\n");

					} 

					else if (type == ChatMessage.MsgType.ALL) {

						turn = message.getTurn();
						index = message.getIndex();
						System.out.println("turn = " + turn);
						System.out.println("myIcon = " + myIcon);
						turnShow.setIcon(message.getIcon());

						if(!(turn % 2 == 0)) { 						//user1�� ���� 
							setText = "X"; 
							newIcon = message.getIcon();	
							myIndex = message.getIndex();
							System.out.println("�����ε��� = " + myIndex);
							myList.add(myIndex);
							nowTurn = turn;
							turnShow.setIcon(yourIcon);

							for(int i=0; i<yourList.size(); i++) { //user2�� ������ ��ư�� �� ���� ��� 
								if(yourList.get(i)==myIndex){
									setText = "O"; //user2�� ���������� �������ְ� 
									turn = nowTurn-1; //turn�� ++�����ʰ� ������� ���޵ǵ��� -���ش�. 
								}
							}

						}
						else { 										//user2������ 
							setText = "O";
							yourIcon = message.getIcon();
							yourIndex = message.getIndex();
							System.out.println("����ε��� = " + yourIndex);
							yourList.add(yourIndex);

							nowTurn = turn;
							for(int j=0; j<myList.size(); j++) { //user1�� ������ ��ư�� �� ���� ��� 
								if(myList.get(j)==yourIndex){

									setText = "X";	
									turn = nowTurn-1;
								}
							}

							turnShow.setIcon(newIcon);

						}
						btnEmptyClicked = true;	
						btnEmpty[index].setText(setText);

						if(btnEmpty[index].getText()=="X") btnEmpty[index].setIcon(newIcon); //X�� user1�� ������ ���� 
						else btnEmpty[index].setIcon(yourIcon); //O�� user2 ������

						if(btnEmptyClicked)	{ //��ư�� ������ ��,��,���º�,�� üũ�ϰ� false�� ����
							checkWin();
							btnEmptyClicked = false;
						}

					}

					else if (type == ChatMessage.MsgType.CHECK){ //�ºθ� üũ�Ѵ�

						if(message.getCheck()==true) {
							if(message.getSender().equals(name)==true ) {
								mesg = message.getSender() + "���� �̰���ϴ�!";
								winSound.play();
								start.setEnabled(true);

							}
							else{		
								mesg = message.getReceiver() + "���� �����ϴ�!";
								loseSound.play();
								start.setEnabled(true);

							}
							JOptionPane.showMessageDialog(null, mesg);
							break;
						}
						if(message.getCheck()==false) {
							if(message.getSender().equals(name)==false ) {
								mesg = message.getSender() + "���� �����ϴ�!";
								winSound.play();
								start.setEnabled(true);
							}
							else{		
								mesg = message.getReceiver() + "���� �̰���ϴ�!";
								loseSound.play();
								start.setEnabled(true);

							}
							JOptionPane.showMessageDialog(null, mesg);
							break;


						}
					}
					else if (type == ChatMessage.MsgType.LOGIN_LIST) {
						// ���� ����Ʈ�� ���� �ؼ� counterParts ����Ʈ�� �־� ��.
						// ����  ���� (""�� ����� ���� �� ����Ʈ �� �տ� ���� ��)
						users = message.getContents().split("/");

						for (int i=0; i<users.length; i++) {
							if (user.equals(users[i]))users[i] = "";

						}
						users = sortUsers(users);		// ���� ����� ���� �� �� �ֵ��� �����ؼ� ����
						users[0] =  ChatMessage.ALL;	// ����Ʈ �� �տ� "��ü"�� ������ ��
						counterParts.setListData(users);
						repaint();
					} else if (type == ChatMessage.MsgType.NO_ACT){

						// �ƹ� �׼��� �ʿ���� �޽���. �׳� ��ŵ

					} else {
						// ��ü�� Ȯ�ε��� �ʴ� �̻��� �޽���
						throw new Exception("�������� �� �� ���� �޽��� ��������");
					}
				} // close while
			} catch(Exception ex) {
				System.out.println("Ŭ���̾�Ʈ ������ ����");		// �������� ����� ��� �̸� ���� ������ ����

			}
		} // close run

		// �־��� String �迭�� ������ ���ο� �迭 ����
		private String [] sortUsers(String [] users) {
			String [] outList = new String[users.length];
			ArrayList<String> list = new ArrayList<String>();
			for (String s : users) {
				list.add(s);
			}
			Collections.sort(list);				// Collections.sort�� ����� �ѹ濡 ����
			for (int i=0; i<users.length; i++) {
				outList[i] = list.get(i);
			}
			return outList;
		}
	} // close inner class     	


	public class RankPanel extends JPanel { //��ŷ�����ӿ� ������ ��ŷ�г�
		public void paintComponent(Graphics g) {

			Image img = new ImageIcon(getClass().getResource(rankImage)).getImage(); 
			g.drawImage(img,0,0,400,390,this);

			g.setColor(Color.black);
			g.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 25));

			Collections.sort(rank,Collections.reverseOrder());

			for(int i=0; i<rank.size(); i++) { //��ŷ������� ������ִ� �κ� 
				g.drawString(" "+(i+1), 30, 137+(i*25)); //��ŷ �ε��� ��� 
				g.drawString(rank.get(i).name,75,137+(i*25)); // �̸���(id) ��� 
				g.drawString(""+rank.get(i).rankScore,320,137+(i*25)); //�·� ���


			}
		}
	}


	//��ŷ������ ���� Ŭ����
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
	public class gamePanel extends JPanel implements ActionListener{ //���� ������ �����ϴ� ���� �г� 

		public gamePanel(int s) {
			btPanel = new JPanel();
			getContentPane().add(btPanel);

			levelSize = s;
			size = levelSize * levelSize;

			setBounds(0,0,700,550);
			setFocusable(true);
			getContentPane().add (this);

			for(int i=1; i<=size; i++)	{ //��ư�� ������ size ��ŭ �����ȴ�

				if(size == 9) { // 3*3������ ������ �� �Ұ� 
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


			// �޽��� ���÷��� â
			incoming = new JTextArea(20,15);
			incoming.setLineWrap(true);
			incoming.setWrapStyleWord(true);
			incoming.setEditable(false);
			incoming.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 18));
			JScrollPane qScroller = new JScrollPane(incoming);
			qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			qScroller.setAutoscrolls(true);
			
			// ��ȭ ��� ���. �ʱ⿡�� "��ü" - ChatMessage.ALL �� ����
			String[] list = {ChatMessage.ALL};
			counterParts = new JList(list);
			JScrollPane cScroller = new JScrollPane(counterParts);
			cScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			cScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			cScroller.setAutoscrolls(true);
			counterParts.setVisibleRowCount(5);
			counterParts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			counterParts.setFixedCellWidth(100);

			// �޽��� ������ ���� ��ư
			JButton sendButton = new JButton();
			sendButton.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 20));
			sendButton.addActionListener(new SendButtonListener());
			sendButton.setIcon(sendIcon);
			sendButton.setBorderPainted(false);
			sendButton.setContentAreaFilled(false);

			// �޽��� ���÷��� â
			outgoing = new JTextArea(20,15);
			outgoing.setLineWrap(true);
			outgoing.setWrapStyleWord(true);
			outgoing.setEditable(true);
			outgoing.setFont(new Font("�����ձ۾� ��",Font.PLAIN, 18));
			JScrollPane oScroller = new JScrollPane(outgoing);
			oScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			oScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			oScroller.setAutoscrolls(true);

			// GUI ��ġ
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


			userPanel.add(new JLabel("��ȭ�����"));
			userPanel.add(Box.createRigidArea(new Dimension(0,5)));
			userPanel.add(cScroller);
			userPanel.setOpaque(false);

			inputPanel.add(new JLabel("�޽����Է�"));
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

			// ��Ʈ��ŷ�� �õ��ϰ�, �������� �޽����� ���� ������ ����
			setUpNetworking();
			Thread readerThread = new Thread(new IncomingReader());
			readerThread.start();

			// Ŭ���̾�� ������ â ����
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//getContentPane().add(BorderLayout.EAST, mainPanel);
			setLayout(null);
			mainPanel.setBounds(345,130,340,360);

			mainPanel.setOpaque(false);
			add(mainPanel);

		}

		public void paintComponent(Graphics g) {
			Image img = new ImageIcon(getClass().getResource(MAIN_PIC)).getImage(); //��� �̹��� ���� 
			g.drawImage(img,0,0,FWidth,FHeight,this);

		}

		public void actionPerformed(ActionEvent click)	{ //��ư Ŭ�� Ƚ���� turn�� �������ִ� �κ�
			Object source = click.getSource();
			//nowTurn = turn;

			for(int i=1; i<=(size) ; i++)	{

				if(source == btnEmpty[i] && turn <	(levelSize * levelSize) + 1)	{ //i��° ��ư�� ������ turn�� ��ư�� ����+1 ���� ���� ���
					btnEmptyClicked = true;									//                 (i=1���� �����ϹǷ� )
					if(!(turn % 2 == 0)) {//turn�� 1,3,5,7,9�� ��� (Ȧ���ϰ��) 
						btnEmpty[i].setText("X"); //i��° ��ư�� X�� ���õȴ�

						myI = i; //������
						turnShow.setIcon(myIcon);
					}

					else { //turn�� 2,4,6,8�� ��� (¦���� ���)
						btnEmpty[i].setText("O");
						yourI = i; //�������� 
					}



					if(myI == yourI) return; //���� ���� ��ư�� �ε����� ��밡 ���� ��ư�� �ε����� ������ turn�� �״�� �д�. 
					else turn++; //���� ������ ���� ������ �Ѱ��ش�.
					//nowTurn = turn;


					try {
						String to = users[1];
						writer.writeObject(new ChatMessage(ChatMessage.MsgType.ALL,name,to,"",turn,myIcon,i));
						writer.flush(); //������ ������� (���� ������ �޾Ƶ��̱� ����)

					} catch(Exception ex) {

						ex.printStackTrace();
					}


					if(btnEmpty[i].getText()=="X") btnEmpty[i].setIcon(myIcon);
					if(btnEmpty[i].getText()=="O") btnEmpty[i].setIcon(yourIcon);
					gamePanel.requestFocus();

				}
			}

			if(btnEmptyClicked)	{ //��ư�� ������ ��,��,���º�,�� üũ�ϰ� false�� ����
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

						//ó�� ���� ��ư�� �ι�° ���� ��ư, ����° ���� ��ư�� text�� ������ �̱��. 
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

		else  { //levelSize == 6 �̸� 
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
				if(turn % 2 == 0) { //�̱��� 


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
						System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." +
								cnfe.getMessage());
					}
					catch (SQLException se) {
						System.out.println(" SQL����. " + se.getMessage());
					}

					catch(NumberFormatException nfe) {


					}
				}
				else	{

					win = false; //�� ��� 


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
						System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." +
								cnfe.getMessage());
					}
					catch (SQLException se) {
						System.out.println(" SQL����. " + se.getMessage());
					}

					catch(NumberFormatException nfe) {


					}


				}



			}




			else if(!win && turn>(size) )	{ //������ ��� 
				mesg = "�����̿���";
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
					System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." +
							cnfe.getMessage());
				}
				catch (SQLException se) {
					System.out.println(" SQL����. " + se.getMessage());
				}

				catch(NumberFormatException nfe) {


				}
			}
			try { //���� ������ �·��� update ���ش� 
				Connection conn = null;
				Statement stmt = null;


				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/game", "root", "qwert");
				stmt = conn.createStatement();

				stmt.executeUpdate( "UPDATE people SET rate = win/(win+lose+draw) * 100 WHERE name = '" + name + "';");


			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." +
						cnfe.getMessage());
			}
			catch (SQLException se) {
				System.out.println(" SQL����. " + se.getMessage());
			}

			catch(NumberFormatException nfe) {


			}
			try {
				String to = users[1];
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CHECK,name,to,"",win));
				writer.flush(); //������ ������� (���� ������ �޾Ƶ��̱� ����)

			} catch(Exception ex) {

				ex.printStackTrace();
			}
			for(int i=1; i<=(levelSize * levelSize); i++)	{
				btnEmpty[i].setEnabled(false); //���������� ���̻� Ŭ������ ���ϰ� �Ѵ�.
			}
		}

	}
}
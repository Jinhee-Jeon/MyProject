package game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;




public class people extends JFrame {
	
	private JPanel contentPane;
	private JTextField pNameField;
	private JTextField pEmailField;
	private JPasswordField pPasswordField;
	JRadioButton rdbtnNewRadioButton;
	JRadioButton rdbtnNewRadioButton_1;
	private final String join = "/res/join.jpg";
	
	String gender;
	String  qName, qEmail, qPassword;
	int qId;
	
	public people() {
		

		setBounds(100, 100, 400, 420);
		setTitle("회원가입");
		
		setBounds(70, 100, 400, 350);
		contentPane = new contentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
	
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel pName_label = new JLabel("이름");
		pName_label.setBounds(113, 103, 34, 15);
		contentPane.add(pName_label);
		
		JLabel pGender_label = new JLabel("성별");
		pGender_label.setBounds(113, 128, 44, 15);
		contentPane.add(pGender_label);
		
		JLabel pPassword_label = new JLabel("비밀번호");
		pPassword_label.setBounds(92, 210, 57, 15);
		contentPane.add(pPassword_label);
		
		JLabel pEmail_label = new JLabel("Email");
		pEmail_label.setBounds(108, 157, 57, 15);
		contentPane.add(pEmail_label);
		
		JLabel info_label = new JLabel("★★ Email을 id로 사용하게 됩니다.");
		info_label.setBounds(91,180,200,15);
		contentPane.add(info_label);
	
		
		pNameField = new JTextField();
		contentPane.add(pNameField);
		pNameField.setBounds(154, 100, 116, 21);
		pNameField.setColumns(10);
		
		pPasswordField = new JPasswordField();
		pPasswordField.setEchoChar('*');
		
		
		pPasswordField.setBounds(154, 210, 116, 21);
		contentPane.add(pPasswordField);
		
		
		pEmailField = new JTextField();
		pEmailField.setBounds(154, 153, 116, 21);
		contentPane.add(pEmailField);
		pEmailField.setColumns(10);
		
		rdbtnNewRadioButton = new JRadioButton("남자");
		rdbtnNewRadioButton.setBounds(154, 124, 57, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("여자");
		rdbtnNewRadioButton_1.setBounds(215, 124, 59, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		
		JButton joinButton = new JButton("가입완료");
		joinButton.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection conn = null;
	    	        Statement stmt = null;
	    	  

	    	        Class.forName("com.mysql.jdbc.Driver");
    	            conn = DriverManager.getConnection(
    	                "jdbc:mysql://localhost:3306/game", "root", "qwert");
    	            stmt = conn.createStatement();
    	         
	    	        
		     	       	if(rdbtnNewRadioButton.isSelected()) gender = "남자";
		     			if(rdbtnNewRadioButton_1.isSelected()) gender = "여자";

		     			
	        	            qName =pNameField.getText();
	        	            qEmail = pEmailField.getText();
	        	            qPassword = pPasswordField.getText();
	        	            
	        	            stmt.executeUpdate( "INSERT INTO people (name, email, password, gender, money) " +
	        	            		" VALUES ("+"'"+ qName +"'"+  ",'" + qEmail + "','" + qPassword + "'," +"'"+  gender + "' ,1000);");
	        	  
	        	        
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
		
	     	  	
	     			 JOptionPane jo = new JOptionPane();        
	     		     jo.showMessageDialog(null, qName + "님의 가입이 완료되었습니다^ㅇ^");
	     			 
	     		     setVisible(false);
			}

				
		}
		
		);
		
		
		joinButton.setBounds(161, 248, 97, 23);
		contentPane.add(joinButton);
		
		
		
		
		
		
	}
	
	
	
	
	
	
	public class contentPane extends JPanel {
		
		public void paintComponent(Graphics g) {
			
			Image img = new ImageIcon(getClass().getResource(join)).getImage(); 
			g.drawImage(img,0,0,400,390,this);
			
			
			
		}
	}
	
}


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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class search extends JFrame{
	private final String search = "/res/search.jpg";
	
	
	private JPanel contentPane;
	private JTextField pNameField, pNameField2;
	private JTextField pEmailField;
	private JTextField pPasswordField;
	private JLabel nameLabel;
	private JLabel emailLabel;
	private JLabel name2Label;
	
	private JLabel findIdLabel;
	private JLabel findPassLabel;
	private JButton idS, pS;
	
	String  qName, qEmail, qPassword;
	
	public search() {
		setTitle("ID / 비밀번호 찾기");
		
		setBounds(70, 100, 400, 350);
		contentPane = new contentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nameLabel= new JLabel("이름 ");
		nameLabel.setBounds(88, 80, 40, 21);
		contentPane.add(nameLabel);
		
		pNameField = new JTextField();
		pNameField.setBounds(130, 80, 116, 21);
		contentPane.add(pNameField);
	
		emailLabel= new JLabel("email ");
		emailLabel.setBounds(88, 160, 57, 15);
		contentPane.add(emailLabel);
		
		pEmailField = new JTextField();
		pEmailField.setBounds(130, 160, 116, 21);
		contentPane.add(pEmailField);
		
		name2Label= new JLabel("이름 ");
		name2Label.setBounds(88,185, 57, 15);
		contentPane.add(name2Label);
		
		pNameField2 = new JTextField();
		pNameField2.setBounds(130, 185, 116, 21);
		contentPane.add(pNameField2);
		
		idS = new JButton("찾기");
		idS.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
		
		idS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //id 찾기
				
				
					
					try {
						Connection conn = null;
		    	        Statement stmt = null;
		    	  

		    	        Class.forName("com.mysql.jdbc.Driver");
	    	            conn = DriverManager.getConnection(
	    	                "jdbc:mysql://localhost:3306/game", "root", "qwert");
	    	            stmt = conn.createStatement();
	    	         
	    	            ResultSet rs = stmt.executeQuery( "Select email FROM people WHERE name = '" + pNameField.getText() + "';");
	        	         //이름으로 회원 이메일 검색
	    	           
	    	            System.out.println(pNameField.getText());
        	           
	    	            
	    	            while(rs.next()) {
        	              qEmail = rs.getString("email");
        	            }
        	               
        	              
        	            rs.close();
		        	           
		        	    JOptionPane jo = new JOptionPane();    
		        	    jo.showMessageDialog(null, "ID는  " + qEmail + "입니다 ^ㅇ^*");
		        	    
		        	          
		        	        
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
		idS.setBounds(260, 80, 69, 20);
		
		contentPane.add(idS);
		
		
		pS = new JButton("찾기");
		pS.setFont(new Font("나눔손글씨 펜",Font.PLAIN, 18));
		pS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //id 찾기
				
				
					
					try {
						Connection conn = null;
		    	        Statement stmt = null;
		    	  

		    	        Class.forName("com.mysql.jdbc.Driver");
	    	            conn = DriverManager.getConnection(
	    	                "jdbc:mysql://localhost:3306/game", "root", "qwert");
	    	            stmt = conn.createStatement();
	    	         
	    	            ResultSet rs = stmt.executeQuery( "Select password FROM people WHERE name = '" + pNameField2.getText() + "' AND email = '" +  pEmailField.getText() + "';");
	        	         //이름으로 회원 이메일 검색
	    	           
        	           
	    	            
	    	            while(rs.next()) {
        	              qPassword = rs.getString("password");
        	            }
        	               
        	              
        	            rs.close();
        	            
        	            int pSize = qPassword.length();
        	            String hide = "";
        	            
        	            for(int i=0; i<pSize-2; i++) { //비밀번호의 앞글자 2개를 빼고 나머지는 * 처리한다. 
        	            	hide = hide + "*";
        	            }
        	  
		        	     qPassword = qPassword.substring(0,2);
		        	    JOptionPane jo2 = new JOptionPane();        
		        	    jo2.showMessageDialog(null, "비밀번호는  " + qPassword + hide + " 입니다 ^ㅇ^*");
		        	    setVisible(false);

		        	          
		        	        
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
		pS.setBounds(260, 160, 69, 20);
		contentPane.add(pS);

	}
	
	
	public class contentPane extends JPanel {
		public void paintComponent(Graphics g) {
			
			Image img = new ImageIcon(getClass().getResource(search)).getImage(); 
			g.drawImage(img,0,0,390,340,this);
			
		}
	}
	
	
} 

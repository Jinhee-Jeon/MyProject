package game;


import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.Icon;

public class ChatServer {
	// ������ Ŭ���̾�Ʈ�� ����� �̸��� ��� ��Ʈ���� �ؽ� ���̺��� ����
	// ���߿� Ư�� ����ڿ��� �޽����� ������ ���. ���� ������ �ִ� ������� ��ü ����Ʈ�� ���Ҷ��� ���
    HashMap<String, ObjectOutputStream> clientOutputStreams =
    	new HashMap<String, ObjectOutputStream>();

   
    
    
    public static void main (String[] args) {
	   new ChatServer().go();
   }

    private void go() {
	   try {
		   ServerSocket serverSock = new ServerSocket(5000);	// ä���� ���� ���� ��Ʈ 5000 ���

    	   while(true) {
    	
    		   Socket clientSocket = serverSock.accept();		// ���ο� Ŭ���̾�Ʈ ���� ���

    		   // Ŭ���̾�Ʈ�� ���� ����� ��Ʈ�� �� ������ ����
    		   Thread t = new Thread(new ClientHandler(clientSocket));
    		   t.start();									
    		   System.out.println("S : Ŭ���̾�Ʈ ���� ��");		// ���¸� �������� ��� �޽���
    	   }
       } catch(Exception ex) {
		   System.out.println("S : Ŭ���̾�Ʈ  ���� �� �̻�߻�");	// ���¸� �������� ���  �޽���
    	   ex.printStackTrace();
       }
    }

    // Client �� 1:1 �����ϴ� �޽��� ���� ������
    private class ClientHandler implements Runnable {
    	Socket sock;					// Ŭ���̾�Ʈ ����� ����
    	ObjectInputStream reader;		// Ŭ���̾�Ʈ�� ���� �����ϱ� ���� ��Ʈ��
    	ObjectOutputStream writer;		// Ŭ���̾�Ʈ�� �۽��ϱ� ���� ��Ʈ��

    	// ������. Ŭ���̾�Ʈ���� ���Ͽ��� �б�� ���� ��Ʈ�� ����� ��
		// ��Ʈ���� ���鶧 InputStream�� ���� ����� Hang��. �׷��� OutputStream���� �������.
		// Bug ����... � ������ �ִ� ������ ���߿� ã�ƺ���� ��
    	public ClientHandler(Socket clientSocket) {
    		try {
    			sock = clientSocket;
    			writer = new ObjectOutputStream(clientSocket.getOutputStream());
    			reader = new ObjectInputStream(clientSocket.getInputStream());
    		} catch(Exception ex) {
    			ex.printStackTrace();
    		}
    	}

    	// Ŭ���̾�Ʈ���� ���� �޽����� ���� �����ϴ� �۾��� ����
    	public void run() {
    		ChatMessage message;
    		ChatMessage.MsgType type;
    		try {
    			while (true) {
    				// ���� �޽����� ������ ���� ���� ������ ������ ����
           	 		message = (ChatMessage) reader.readObject();	  // Ŭ���̾�Ʈ�� ���� �޽��� ����
           	 		System.out.println("Ŭ���̾�Ʈ�� ���� �޽��� = " + message);
           	 		type = message.getType();
           	 		if (type == ChatMessage.MsgType.LOGIN) {		  // Ŭ���̾�Ʈ �α��� ��û
           	 			handleLogin(message.getSender(),writer);	  // Ŭ���̾�Ʈ �̸��� �׿��� �޽�����
           	 														  // ���� ��Ʈ���� ���
           	 		}
           	 		else if (type == ChatMessage.MsgType.LOGOUT) {	  // Ŭ���̾�Ʈ �α׾ƿ� ��û
           	 			handleLogout(message.getSender());			  // ��ϵ� �̸� �� �̿� ����� ��Ʈ�� ����
           	 			writer.close(); reader.close(); sock.close(); // �� Ŭ���̾�Ʈ�� ���õ� ��Ʈ���� �ݱ�
           	 			return;										  // ������ ����
           	 		}
           	 		else if (type == ChatMessage.MsgType.CLIENT_MSG) {
           	 			handleMessage(message.getSender(), message.getReceiver(), message.getContents());
           	 		}
           	 	else if (type == ChatMessage.MsgType.CHECK) { //���и� check �Ѵ� 
       	 			handleCheck(message.getSender(), message.getReceiver(), message.getCheck());
       	 		}
           	 		else if (type == ChatMessage.MsgType.NO_ACT) {
           	 		//  �����ص� �Ǵ� �޽���
           	 			continue;
           	 		}
           	 		
           	 		else if (type == ChatMessage.MsgType.ALL) { //turn, icon, ��ư index ������ �޴´�.
           	 			handleAll(message.getSender(),message.getReceiver(),message.getTurn(),message.getIcon(),message.getIndex());
       	 	
           	 		}
           	 		
           	 		
           	 		else {
           	 			// ��ü�� Ȯ�ε��� �ʴ� �̻��� �޽���?
           	 			throw new Exception("S : Ŭ���̾�Ʈ���� �˼� ���� �޽��� ��������");
           	 		}
    			}
    		} catch(Exception ex) {
    			System.out.println("S : Ŭ���̾�Ʈ ���� ����");			// ����� Ŭ���̾�Ʈ ����Ǹ� ���ܹ߻�
    																	// �̸� �̿��� ������ �����Ŵ
    		}
    	} // close run
    } // close inner class

    // ����� �̸��� Ŭ���̾�Ʈ���� ��� ��Ʈ���� �������� �ؽ� ���̺��� �־���.
    // �̹� ������ �̸��� ����ڰ� �ִٸ�, ������ �α����� ���� �Ѱ����� Ŭ���̾�Ʈ���� �˸�
    // �׸��� ���ο� ������ ����Ʈ�� ��� �����ڿ��� ������
    // �ؽ� ���̺��� ���ٿ����� �������� ����� ��� (not Thread-Safe. Synchronized�� ��ȣ���� ��.
    private synchronized void handleLogin(String user, ObjectOutputStream writer) {
	 // try {
		  //  �̹� ������ �̸��� ����ڰ� �ִٸ�, ������ �α����� ���� �Ѱ����� Ŭ���̾�Ʈ���� �˸�
		 //  if (clientOutputStreams.containsKey(user)) {
		//	   writer.writeObject(
		//		   new ChatMessage(ChatMessage.MsgType.LOGIN_FAILURE, "", "", "����� �̹� ����"));
		//	   return;
		//   }
	//   } catch (Exception ex) {
	//	   System.out.println("S : �������� �۽� �� �̻� �߻�");
	//	   ex.printStackTrace();
	//   }
	   // �ؽ����̺��� �����-���۽�Ʈ�� �� �߰��ϰ� ���ο� �α��� ����Ʈ�� ��ο��� �˸�
	   clientOutputStreams.put(user, writer);
	   // ���ο� �α��� ����Ʈ�� ��ü���� ���� ��
	   broadcastMessage(new ChatMessage(ChatMessage.MsgType.LOGIN_LIST, "", "", makeClientList()));
    }  // close handleLogin

    // �־��� ����ڸ� �ؽ����̺����� ���� (��� ��Ʈ���� ����)
    // �׸��� ������Ʈ�� ������ ����Ʈ�� ��� �����ڿ��� ������
    private synchronized void handleLogout(String user) {
	   clientOutputStreams.remove(user);
	   // ���ο� �α��� ����Ʈ�� ��ü���� ���� ��
	   broadcastMessage(new ChatMessage(ChatMessage.MsgType.LOGIN_LIST, "", "", makeClientList()));
    }  // close handleLogout

    // Ŭ���̾�Ʈ�� ��ȭ ���濡�� ������ �޽���. �� ��� Ȥ�� "��ü"���� ���� �־�� ��
    private synchronized void handleMessage(String sender, String receiver, String contents) {
	   // ���⼭ ��ο��� ������ ��츦 ó���ؾ� ��
	   if (receiver.equals(ChatMessage.ALL)) {			// "��ü"���� ������ �޽����̸�
		   broadcastMessage(new ChatMessage(ChatMessage.MsgType.SERVER_MSG, sender, "", contents));
		   return;
	   }
	   // Ư�� ��뿡�� ������ �����
	   ObjectOutputStream write = clientOutputStreams.get(receiver);
	   try {
		   write.writeObject(new ChatMessage(ChatMessage.MsgType.SERVER_MSG, sender, "", contents));
	   } catch (Exception ex) {
		   System.out.println("S : �������� �۽� �� �̻� �߻�");
		   ex.printStackTrace();
	   }
    }  // close handleIncomingMessage
    
    
    private synchronized void handleAll(String user, String receiver, int myTurn, Icon iconName, int num) {
 	   broadcastMessage(new ChatMessage(ChatMessage.MsgType.ALL, user, receiver ,"" , myTurn,iconName,num));
      }  
    private synchronized void handleCheck(String user,String receiver, boolean check) {
  	   broadcastMessage(new ChatMessage(ChatMessage.MsgType.CHECK, user, receiver, "", check));
      }  

    // �ؽ��ʿ� �ִ� ��� �����ڵ鿡�� �־��� �޽����� ������ �޼ҵ�.
    // �ݵ�� synchronized �� �޼ҵ忡���� ȣ���ϱ�� ��
    private void broadcastMessage(ChatMessage message) {
	   Set<String> s = clientOutputStreams.keySet();	// ���� ��ϵ� ����ڵ��� �����ϰ� �ϳ��ϳ��� �޽��� ����
	   													// �׷��� ���ؼ� ���� ����� ����Ʈ�� ����
       Iterator<String> it = s.iterator();
     
       String user;
       while(it.hasNext()) {
    	   user = it.next();
    	   try {
	           ObjectOutputStream writer = clientOutputStreams.get(user);	// ��� ����ڿ��� ��Ʈ�� ����
	           writer.writeObject(message);									// �� ��Ʈ���� ���
	          
	           
	           writer.flush();
    	   } catch(Exception ex) {
    		   System.out.println("S : �������� �۽� �� �̻� �߻�");
    		   ex.printStackTrace();
    	   }
    	   
    	 
       } // end while	   
    }	// end broadcastMessage

    private String makeClientList() {
	   Set<String> s = clientOutputStreams.keySet();	// ���� ��ϵ� ����ڵ��� ����
       Iterator<String> it = s.iterator();
       String userList = "";
       while(it.hasNext()) {
    	   userList += it.next() + "/";					// ��Ʈ�� ����Ʈ�� �߰��ϰ� ������ ����
  
    	   
       } // end while
       System.out.println("userList = " + userList);
     
       
       
       return userList;									 
    }	// makeClientList
}
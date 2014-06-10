package game;


import java.io.Serializable;
import javax.swing.Icon;

public class ChatMessage implements Serializable {
	// �޽��� Ÿ�� ����
	// 1���� �޽��� ���� �ʵ�� 3���� String�� �ʵ�.
	// NO_ACT�� ������ �� �ִ� Dummy �޽���. ������ ������ ����ϱ� ���� ����� ����
	// (1) Ŭ���̾�Ʈ�� ������ �޽��� ����
	//	- LOGIN  : CLIENT �α���.
	//		�޽��� ���� : LOGIN, "�۽���", "", ""
	//	- LOGOUT : CLIENT �α׾ƿ�.
	//		�޽��� ���� : LOGOUT, "�۽���", "", ""
	// 	- CLIENT_MSG : �������� ������  ��ȭ .
	// 		�޽�������  : CLIENT_MSG, "�۽���", "������", "����"
	// (2) ������ ������ �޽��� ����
	// 	- LOGIN_FAILURE  : �α��� ����
	//		�޽��� ���� : LOGIN_FAILURE, "", "", "�α��� ���� ����"
	// 	- SERVER_MSG : Ŭ���̾�Ʈ���� �������� ������ ��ȭ 
	//		�޽�������  : SERVER_MSG, "�۽���", "", "����" 
	// 	- LOGIN_LIST : ���� �α����� ����� ����Ʈ.
	//		�޽��� ���� : LOGIN_LIST, "", "", "/�� ���е� ����� ����Ʈ"
	public enum MsgType {NO_ACT, LOGIN, LOGOUT, CLIENT_MSG, LOGIN_FAILURE, SERVER_MSG, LOGIN_LIST,TURN,ICON,B_INDEX, ALL,CHECK};
	
	 // enum = ������ ���� 
	public static final String ALL = "��ü";	 // ����� �� �� �ڽ��� ������ ��� �α��εǾ� �ִ�
											 // ����ڸ� ��Ÿ���� �ĺ���
	private MsgType type;
	private String sender;
	private String receiver;
	private String contents;
	private int turn;
	private Icon iconName;
	private int index;
	private boolean checkInfo;
	
	

	public ChatMessage() {
		this(MsgType.NO_ACT, "", "", "");
	}
	public ChatMessage(MsgType t, String sID, String rID, String mesg) {
		type = t;
		sender = sID;
		receiver = rID;
		contents = mesg;
	}
	public ChatMessage(MsgType t, String sID, String rID, String mesg, int myTurn, Icon image, int num){
							//turn, icon, ��ư �ε��� ������ ó���ϱ� ���Ͽ� �����ڸ� �� �����. 
		type = t;
		sender = sID;
		receiver = rID;
		contents = mesg;
		turn = myTurn;
		iconName = image;
		index = num;
				
	}
	
	public ChatMessage(MsgType t, String sID, String rID, String mesg, boolean check){
											//���и� Ȯ���ϱ� ���Ͽ� �����ڸ� �ϳ� �� �����. 
		type = t;
		sender = sID;
		receiver = rID;
		contents = mesg;
		checkInfo = check;
	}

	public void setTurn (int myTurn) {
		turn = myTurn;
	}
	public void setIcon (Icon image) {
		iconName = image;
	}
	public void setIndex (int num) {
		index = num;
	}
	public void setType (MsgType t) {
		type = t;
	}

	public MsgType getType() {
		return type;
	}

	public void setSender (String id) {
		sender = id;
	}
	public String getSender() {
		return sender;
	}
	public void setCheck(boolean c) {
		checkInfo = c;
	}
	
	public void setReceiver (String id) {
		receiver = id;
	}
	public String getReceiver() {
		return receiver;
	}
	
	public void setContents (String mesg) {
		contents = mesg;
	}
	public String getContents() {
		return contents;
	}
	public int getTurn() {
		return turn;
	}
	public Icon getIcon() {
		return iconName;
	}
	public int getIndex() {
		return index;
	}
	public boolean getCheck() {
		return checkInfo;
	}
	
	public String toString() {
		return ("�޽��� ���� : " + type + "\n" +
				"�۽���         : " + sender + "\n" +
				"������         : " + receiver + "\n" +
				"�޽��� ���� : " + contents + "\n" + 
				"turn : " + turn + "\n" +
				"iconName : " + iconName + "\n" + 
				"index : " + index + "\n");
	}
}

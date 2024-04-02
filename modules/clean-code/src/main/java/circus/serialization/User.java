package circus.serialization;

import java.io.*;

public class User implements Serializable {

	private static final long serialVersionUID = 3659932210257138726L;
	private String userName;
	private String password;
	User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	@Serial
	private void writeObject(ObjectOutputStream oos) throws IOException {
		this.password = "xyz" + password;
		oos.defaultWriteObject();
	}
	@Serial
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		this.password = password.substring(3);
	}
}
package circus.serialization;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestUser {
	@Test
	public void testDeserializeObj_withDefaultReadObject() throws ClassNotFoundException, IOException {
		// Serialization
		FileOutputStream fos = new FileOutputStream("user.serialization.file");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		User actualObject = new User("abc", "123");
		oos.writeObject(actualObject);

		// Deserialization
		User deserializedUser = null;
		FileInputStream fis = new FileInputStream("user.serialization.file");
		ObjectInputStream ois = new ObjectInputStream(fis);
		deserializedUser = (User) ois.readObject();
		assertNotEquals(deserializedUser.hashCode(), actualObject.hashCode());
		assertEquals(deserializedUser.getUserName(), "abc");
		assertEquals(deserializedUser.getPassword(), "123");
	}
}

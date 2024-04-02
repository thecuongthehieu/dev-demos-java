package circus.serialization;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSingleton {
	@Test
	public void testSingletonObj_withNoReadResolve() throws ClassNotFoundException, IOException {
		// Serialization
		FileOutputStream fos = new FileOutputStream("singleton.serialization.file");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Singleton actualSingletonObject = Singleton.getInstance();
		oos.writeObject(actualSingletonObject);

		// Deserialization
		Singleton deserializedSingletonObject = null;
		FileInputStream fis = new FileInputStream("singleton.serialization.file");
		ObjectInputStream ois = new ObjectInputStream(fis);
		deserializedSingletonObject = (Singleton) ois.readObject();
		assertEquals(actualSingletonObject.hashCode(), deserializedSingletonObject.hashCode());
	}
}

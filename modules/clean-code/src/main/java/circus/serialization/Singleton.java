package circus.serialization;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;

public class Singleton implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Singleton INSTANCE = new Singleton();

	private Singleton() {
	}

	public static Singleton getInstance() {
		return INSTANCE;
	}
	@Serial
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
}
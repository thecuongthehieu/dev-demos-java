package jdbc.demos;

public final class DatabaseConfig {
	public static final String DB_HOST = "localhost";
	public static final String DB_PORT = "5432";
	public static final String DB_NAME = "testdb";
	public static final String CONNECTION_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
	public static final String USER = "cuong.ntt";
	public static final String PASSWORD = "cuong.ntt";
	public static final String JDBC_DRIVER = "org.postgresql.Driver";
}

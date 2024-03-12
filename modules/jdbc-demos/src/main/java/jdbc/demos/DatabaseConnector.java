package jdbc.demos;

import java.sql.*;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DatabaseConnector {
	private static final String DEMO_QUERY = "SELECT * FROM myschema.family_member";

	public static void main(String[] args) {
		System.out.println("Hello World!");
		// simpleConnectionDemo();
		connectionPoolDemo();
	}

	public static void simpleConnectionDemo() {
		// Open a connection

		// Class.forName(DatabaseConfig.JDBC_DRIVER);
		try(Connection conn = DriverManager.getConnection(DatabaseConfig.CONNECTION_URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(DEMO_QUERY);
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				System.out.print("id: " + rs.getInt("member_id"));
				System.out.println(", name: " + rs.getString("member_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void connectionPoolDemo() {
		PoolProperties p = new PoolProperties();
		p.setUrl(DatabaseConfig.CONNECTION_URL);
		p.setDriverClassName(DatabaseConfig.JDBC_DRIVER);
		p.setUsername(DatabaseConfig.USER);
		p.setPassword(DatabaseConfig.PASSWORD);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors(
				"org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" + 
						"org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);

		Connection con = null;
		try {
			con = datasource.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(DEMO_QUERY);
			int cnt = 1;
			while (rs.next()) {
				System.out.println((cnt++) + ". id:" + rs.getInt("member_id") + ", name:" + rs.getString("member_name"));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception ignore) {

				}
			}
		}
	}
}

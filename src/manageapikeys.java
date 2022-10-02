
/*
This class manages the API key necessary to connect to the database. 

*/



import static java.nio.charset.StandardCharsets.UTF_8;

// initializations required to get the data from the database to the program 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class manageapikeys {
	Connection conn;
	// To generate MD5 hashvalue
	public String generateMD5Hashvalue(String userName)
	{
		LocalDate dateObj = LocalDate.now();
		DateTimeFormatter formatter
			= DateTimeFormatter.ofPattern("yyyyMMdd");
		String date = dateObj.format(formatter);

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
		String secretPhase
			= "geeks"; // exclusively to set for geeks
		System.out.println("Current Date : " + date);
		System.out.println("Login Id : " + userName);
		System.out.println("Secret Phase : " + secretPhase);

		// By using the current date, userName(emailId) and
		// the secretPhase , it is generated
		byte[] hashResult
			= md.digest((date + userName + secretPhase)
							.getBytes(UTF_8));
		// convert the value to hex
		String password = bytesToHex(hashResult);
		System.out.println("Generated password.."
						+ password);

		return password;
	}
	private String bytesToHex(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static void main(String[] args)
	{
		// let us assume there is a table 'users' available
		// By passing as an argument, we can have that for
		// any table
		new CreateAndUpdateAPIKey("users");
	}

	public CreateAndUpdateAPIKey(String tableName)
	{
		try {
			// connecting to mySQL
			Class.forName("com.mysql.jdbc.Driver")
				.newInstance();
			String url
				= "jdbc:mysql://localhost/geeksforgeeks?useUnicode=true&characterEncoding=utf-8";
			conn = DriverManager.getConnection(url, "root",
											"admin");
			doSelectAndUpdate(tableName);
			conn.close();
		}
		catch (ClassNotFoundException ex) {
			System.err.println(ex.getMessage());
		}
		catch (IllegalAccessException ex) {
			System.err.println(ex.getMessage());
		}
		catch (InstantiationException ex) {
			System.err.println(ex.getMessage());
		}
		catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private void doSelectAndUpdate(String tableName)
	{
		doSelect(tableName);
	}

	private void doSelect(String tableName)
	{
		String query = null, userName = null;
		// Query the respective table. If multiple tables
		// are possible, then we should have separate if or
		// switch statements
		query = "SELECT * FROM users";

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				// loginId is the unique column to identify
				// the user
				userName = rs.getString("loginId");
				// Get the MD5 value and get as password
				String password
					= generateMD5Hashvalue(userName);
				// update the password
				doUpdate(password, userName, tableName);
				System.out.println(userName + ":"
								+ password);
			}
		}
		catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private void doUpdate(String apiKey, String userName,
						String tableName)
	{
		System.out.print("\n[Performing UPDATE] ... ");
		try {
			Statement st = conn.createStatement();
		
			// Use the generated password for apiKey
			String sqlUpdate = null;
			sqlUpdate = "UPDATE users "
						+ "SET apikey = ? "
						+ "WHERE loginId = ?";

			PreparedStatement pstmt
				= conn.prepareStatement(sqlUpdate);

			pstmt.setString(1, apiKey);
			pstmt.setString(2, userName);

			int rowAffected = pstmt.executeUpdate();
			System.out.println(String.format(
				"Row affected %d", rowAffected));
		}
		catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
}

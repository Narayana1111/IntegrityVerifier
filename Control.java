import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Control {

	private String username, password;
	Connection con;

	void connect() {
		try {//com.mysql.jdbc.Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TernaryHashTree", "root", "Narayana@0011");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean SignIn(String name, String pass) {
		try {
			String s = "select*from SignIn Where username=? and password=?";
			PreparedStatement pstmt = con.prepareStatement(s);
			pstmt.setString(1, name);
			pstmt.setString(2, pass);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	boolean SignUp(String name, String pass) {
		try {
			String s = "insert into SignIn values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(s);
			pstmt.setString(1, name);
			pstmt.setString(2, pass);
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

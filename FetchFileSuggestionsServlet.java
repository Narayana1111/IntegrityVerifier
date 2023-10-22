import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FetchFileSuggestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		// Database connection properties
		String jdbcUrl = "jdbc:mysql://localhost:3306/ternaryhashtree";
		String username = "root";
		String password = "Narayana@0011";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Fetch file names from the database
		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			Statement statement = connection.createStatement();
			String query = "SELECT name FROM files";
			ResultSet resultSet = statement.executeQuery(query);

			List<String> fileNames = new ArrayList<>();
			while (resultSet.next()) {
				String fileName = resultSet.getString("name");
				fileNames.add(fileName);
			}

			out.print(fileNames);
			out.flush();

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			out.print("Error fetching file suggestions");
			out.flush();
		}
	}
}

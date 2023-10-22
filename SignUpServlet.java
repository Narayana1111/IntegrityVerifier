
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("username");
		String password = req.getParameter("password");
		Control c = new Control();
		c.connect();
		boolean output = c.SignUp(name, password);
		if (output) {
			res.sendRedirect("/TernaryHashTree/Login.jsp");
		} else {
			res.sendRedirect("/TernaryHashTree/SignUp.jsp");
		}
	}

}

package control.auth;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UserModelDS;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if(email != null && password != null) {
			UserModelDS userModel = new UserModelDS();
			int accessCode = 0;
			try {
				accessCode = userModel.checkPsw(email, password);
			} catch (SQLException e) {
				response.getWriter().append("Errore: \n" + e);
				e.printStackTrace();
			}
			if(accessCode != 0) {
				if(accessCode == 2) {
					request.getSession().setAttribute("isAdmin", new Boolean(true));
				}
				
				response.sendRedirect(request.getContextPath() + "/home");

			}else {
				response.getWriter().append("Dati d'accesso errati...");
			}	
		}else {
			response.getWriter().append("Compila tutti i campi nel login!");
		}	
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/components/pages/auth/Login.jsp");
		dispatcher.forward(request, response);
	}

}

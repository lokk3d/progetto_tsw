package control.category;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryModelDS;

/**
 * Servlet implementation class DeleteCategory
 */
@WebServlet("/category/delete")
public class DeleteCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			CategoryModelDS categoryModel = new CategoryModelDS();

			categoryModel.delete(id);
			response.sendRedirect(request.getContextPath() + "/admin/dashboard/categories");

		} catch (Exception e) {
			response.setStatus(400);
			response.getWriter().append("Errore");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("pageName", "/components/pages/admin/DeleteCategory.jsp");
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/components/pages/admin/AdminPage.jsp");
		dispatcher.forward(request, response);
	}

}
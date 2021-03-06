package control.category;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.BelongsModelDS;
import model.dao.CategoryModelDS;

/**
 * Servlet implementation class ShowCategory
 */
@WebServlet("/shop/category")
public class ShowCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String category = request.getParameter("id");
		System.out.println(category);
		if(category != null && !category.isEmpty()) {
			CategoryModelDS categoryModel = new CategoryModelDS();
			BelongsModelDS belongsModel = new BelongsModelDS();
			
			try {
				request.setAttribute("id", category);
				request.setAttribute("categories", categoryModel.get());
				request.setAttribute("products", belongsModel.getByCategory(category));

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/components/pages/shop/CategoryView.jsp");
				dispatcher.forward(request, response);	
				
			} catch (SQLException e) {
				response.getWriter().append("Errore \n"+e);

			}
		}else {
			response.getWriter().append("Errore");
		}
		
		
	}


}

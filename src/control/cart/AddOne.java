package control.cart;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.ChoosenProduct;

/**
 * Servlet implementation class AddOne
 */
@WebServlet("/AddOne")
public class AddOne extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOne() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ChoosenProduct> cart = (ArrayList<ChoosenProduct>) request.getSession().getAttribute("cart");
		int id = (int) request.getAttribute("id");
		for(int i=0; i<cart.size(); i++) {
			if(cart.get(i).getProduct().getId() == id) {
				cart.get(i).setQuantity(cart.get(i).getQuantity()+1);
			}
		}
		request.getSession().setAttribute("cart", cart);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
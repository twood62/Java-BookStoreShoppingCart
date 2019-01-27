package com.pluralsight;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.inject.Inject;
/**
 * Servlet implementation class HelloWorld
 */

public class CartController extends HttpServlet {
		private static final long serialVersionUID = 1L;
		private DBConnection dbConnection;

		@Inject
    private BookDAO bookDAO;

    public void init() {
			dbConnection = new DBConnection();
			bookDAO = new BookDAO(dbConnection.getConnection());
    }

		public void destroy() {
			dbConnection.disconnect();
		}

    public CartController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		// The requested URL path
		String action = request.getPathInfo();

		// Do different things depending on the action (or path requested)
		try {
			switch(action) {
				case "/addcart":
					 addToCart(request, response);
					 break;
				case "/delete":
					deleteFromCart(request, response);
					break;
				case "/update":
					 updateCart(request, response);
					 break;
        default:
           break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.sendRedirect("../ShoppingCart.jsp");
	}
	
	
	//UPDATE CART
	protected void updateCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	   HttpSession session = request.getSession();
	   
	  // HttpSession session = request.getSession();
		  int index = Integer.parseInt(request.getParameter("index"));
		  int quantity = Integer.parseInt(request.getParameter("quantity"));
		  
		  System.out.println("index" + index + "quantity" + quantity);
		  // Check if a ShoppingCart exists in the Session variable (built in object, really)
			 // If not create one
		  // ShoppingCart shoppingCart = null;

		  ShoppingCart cart = (ShoppingCart)session.getAttribute("cart");
		   
		   
		   
		 // Add this item and quantity to the ShoppingCart
		  
		  
	   cart.updateCartItem(index, quantity);
	  }
	
	
	
	
	
	//the session variable is managing the shopping cart instead of the DB
  protected void addToCart(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
   HttpSession session = request.getSession();
   String idStr = request.getParameter("id");
   int id = Integer.parseInt(idStr);
   String quantityStr = request.getParameter("quantity");
   int quantity = Integer.parseInt(quantityStr);

	 // Get the book from the database
   //This method returns a 
   Book existingBook = bookDAO.getBook(id);

	 // Check if a ShoppingCart exists in the Session variable (built in object, really)
	 // If not create one
   ShoppingCart shoppingCart = null;
   Object objCartBean = session.getAttribute("cart");

   if(objCartBean!=null) {
    shoppingCart = (ShoppingCart) objCartBean ;
   } else {
    shoppingCart = new ShoppingCart();
    session.setAttribute("cart", shoppingCart);
   }

	 // Add this item and quantity to the ShoppingCart
   shoppingCart.addCartItem(existingBook, quantity);
  }
  	
  
 //DELETE FROM CART
//the session variable is managing the shopping cart instead of the DB
  protected void deleteFromCart(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
   
	  HttpSession session = request.getSession();
	  int index = Integer.parseInt(request.getParameter("index"));
	  // Check if a ShoppingCart exists in the Session variable (built in object, really)
		 // If not create one
	   ShoppingCart cart = (ShoppingCart)session.getAttribute("cart");

	   cart.deleteCartItem(index);
	  	
  }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

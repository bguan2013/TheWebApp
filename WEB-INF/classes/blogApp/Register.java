package blogApp;


import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.HashMap;
import java.sql.*;
import javax.servlet.annotation.*;


////NEED TO MODIFTY!!!!!

 @WebServlet("/Register")
public class Register extends HttpServlet{


	ServletContext context;


	@Override
	public void init() throws ServletException{

		context = getServletContext();


	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		

		String title = "First time?";

		response.setContentType("text/html");

		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String pageType = "Register";
		
		String notification;
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();

	
		if(userName == null || password == null){

			notification = "";
			request.setAttribute("notification", notification);
			RequestDispatcher rd = context.getRequestDispatcher("/RegisterPage.jsp");
			rd.forward(request, response);

		}
	
		else if(userName.equals("") || password.equals("")){

			notification = "Please enter your information!";
			request.setAttribute("notification", notification);
			RequestDispatcher rd = context.getRequestDispatcher("/RegisterPage.jsp");
			rd.forward(request, response);
		}
		else{
			if(checkIfUserNameExists(userName)){
				
				notification = "User Already Exists!";
				request.setAttribute("notification", notification);
				RequestDispatcher rd = context.getRequestDispatcher("/RegisterPage.jsp");
				rd.forward(request, response);
				
			}
			else{
				
				session.setAttribute("user-information", registerUserToMySQL(userName, password));
				response.sendRedirect("ViewBlog");
				
			}

		}

		

		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


		doGet(request,response);
	}


	private boolean checkIfUserNameExists(String usr){

		try{

			MySQLDatabase mysql = (MySQLDatabase)getServletContext().getAttribute("mysql");
			String selectUser = "SELECT * FROM " + mysql.getUserTableName() + " WHERE " + mysql.getUserUsername() + " = " + "'" + usr + "'";
			ResultSet resultUser = mysql.getResultSetUsingCommand(selectUser);
			if(resultUser.next()){

				resultUser.close();
				return true;
				
			}
			else{
				resultUser.close();
				return false;
			}
		}
		catch(SQLException e){

			e.printStackTrace();
			return true;
		}
	}

	
	private User registerUserToMySQL(String usr, String pwd){

		try{
			MySQLDatabase mysql = (MySQLDatabase)getServletContext().getAttribute("mysql"); 
			String insertUser = "INSERT INTO " + mysql.getUserTableName() +" ( " + mysql.getUserUsername() + ", "
																				 + mysql.getUserPassword() + " ) " 
																			+ " VALUES( " + "'" + usr + "'" + ", " 
																			   			  + "'" + pwd + "'" + ")";
			mysql.updateUsingCommand(insertUser);
			String selectUser = "SELECT " + mysql.getUserId()+ " FROM " + mysql.getUserTableName() + " WHERE " + mysql.getUserUsername() + " = " + "'" + usr + "'";
			ResultSet currentUserId = mysql.getResultSetUsingCommand(selectUser);
			currentUserId.next();
			int user_id = currentUserId.getInt(mysql.getUserId());

			currentUserId.close();
			return new User(user_id, usr, pwd);
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}




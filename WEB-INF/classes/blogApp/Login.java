package blogApp;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.HashMap;
import java.sql.*;
import javax.servlet.annotation.*;



 @WebServlet("/Login")
public class Login extends HttpServlet{



	ServletContext context;

	@Override
	public void init() throws ServletException{

		context = getServletContext();

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

	

		String title = "Simple Blog";

		response.setContentType("text/html");

		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String pageType = "Login";
		
		String notification;
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();

		if(userName == null || password == null){

			notification = "";
			request.setAttribute("notification", notification);
			RequestDispatcher rd = context.getRequestDispatcher("/LoginPage.jsp");
			rd.forward(request, response);

		}
		else if(userName.equals("") || password.equals("")){

			notification = "Please enter your information!";
			request.setAttribute("notification", notification);
			RequestDispatcher rd = context.getRequestDispatcher("/LoginPage.jsp");
			rd.forward(request, response);
		}

		else{

			if(checkIfCombinationExists(userName,password)){
				
				session.setAttribute("user-information", retrieveUserFromMySQL(userName, password));
				response.sendRedirect("ViewBlog");

			}

			else{
				notification = "Wrong Combination!";
				request.setAttribute("notification", notification);
				RequestDispatcher rd = context.getRequestDispatcher("/LoginPage.jsp");
				rd.forward(request, response);
			}
		}

		

		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


		doGet(request,response);
	}

	private User retrieveUserFromMySQL(String username, String password){

		try{

			MySQLDatabase mysql = (MySQLDatabase)getServletContext().getAttribute("mysql");
			String selectUser = "SELECT * FROM " + mysql.getUserTableName() + " WHERE " + mysql.getUserUsername() + " = " + "'" + username + "'";
			ResultSet resultUser = mysql.getResultSetUsingCommand(selectUser);
			resultUser.next();
			int tempUser_id = resultUser.getInt(mysql.getUserId());
			String tempUser_username = resultUser.getString(mysql.getUserUsername());
			String tempUser_password = resultUser.getString(mysql.getUserPassword());
			User tempUser = new User(tempUser_id, tempUser_username, tempUser_password);

			resultUser.close();
			return tempUser;
		}
		catch(SQLException e){

			e.printStackTrace();
			return null;
		}
	}

	private boolean checkIfCombinationExists(String usr, String pwd){

		try{
			MySQLDatabase mysql = (MySQLDatabase)getServletContext().getAttribute("mysql");

			String selectUser = "SELECT * FROM " + mysql.getUserTableName() + " WHERE " + mysql.getUserUsername() + " = " + "'" + usr + "'";
			ResultSet resultUser = mysql.getResultSetUsingCommand(selectUser);
			if(resultUser.next()){

				if(pwd.equals(resultUser.getString(mysql.getUserPassword()))){
					resultUser.close();
					return true;
				}
				else{
					resultUser.close();
					return false;
				}
			}
			else{
				resultUser.close();
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
}




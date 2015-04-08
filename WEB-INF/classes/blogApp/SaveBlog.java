package blogApp;


import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.HashMap;
import java.sql.*;
import javax.servlet.annotation.*;



 @WebServlet("/SaveBlog")
public class SaveBlog extends HttpServlet{


	ServletContext context;

	@Override
	public void init(){
		context = getServletContext();
	} 

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


		String title = convertToMySQLString(request.getParameter("blog_title"));
		String post = convertToMySQLString(request.getParameter("blog_post"));
		User tempUser = (User)request.getSession(true).getAttribute("user-information");
		

		String saveResponse = postBlogToMySQL(title, post, tempUser);

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		response.setHeader("refresh" , "3; ViewBlog");

		out.println("<!doctype html>\n" +
								"<html>\n" +
									"<head>\n" +
										"<link rel=\"stylesheet\" type=\"text/css\" href=\"LogoutStyleSheet.css\">\n" +
									"</head>\n" +
									"<body>\n" +
										"<div class = \"title\">\n" +
											"<p>" + saveResponse + "</p>\n" +
											"<p>Redirecting...</p>\n" +
										"</div>\n" +
									"</body>\n" +
								"</html>");




	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		doGet(request,response);

	}

	private String postBlogToMySQL(String title, String post, User user){
		
			if(title == null || post == null || user == null){

				return "Fields missing, returning...";
				

			}
			else{
				int userId = user.getId();
				MySQLDatabase mysql = (MySQLDatabase)context.getAttribute("mysql"); 
				String insertBlog = "INSERT INTO " + mysql.getBlogTableName() + " ( " + mysql.getBlogTitle() + ", "
																					  + mysql.getBlogPost() + ", "
																					  + mysql.getBlogTime() + ", "
																					  + mysql.getBlogUserId()+ " ) "
																			 		  + " VALUES( " + "'" + title + "'" + ", " 
																									+ "'" + post + "'" + ", "
																									+ "NOW()" + ", "
																									+ userId + ")";

				mysql.updateUsingCommand(insertBlog);

			return "Saved successfully...";
			}
		
		
	}


	private String convertToMySQLString(String virgin){	

		String oldLady = virgin.replace("'", "\\'");
	
		return oldLady;
	}	
}
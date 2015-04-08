package blogApp;


import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.HashMap;
import java.sql.*;
import javax.servlet.annotation.*;



 @WebServlet(urlPatterns = "/WriteBlog", loadOnStartup = 2)
public class WriteBlog extends HttpServlet{

	ServletContext context;

	@Override
	public void init() throws ServletException{

		context = getServletContext();

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


		HttpSession session = request.getSession(true);
		User currentUser = (User)session.getAttribute("user-information");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		


		if(currentUser != null){
			
			RequestDispatcher rd = context.getRequestDispatcher("/WriteBlogPage.jsp");
			rd.forward(request, response);

			
		}
		else{
			response.setHeader("refresh", "3; URL = LoginPage.jsp");
			out.println("<html>\n" +
							"<head>\n" +
								"<link rel=\"stylesheet\" type=\"text/css\" href=\"writeABlogPageRedirect.css\">\n" +
							"</head>\n" +
							"<body>\n" +
							"You are not logged-in, redirecting...\n" +
							"</body>\n" +
							"<html>");
			
		}

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		doGet(request,response);

	}

	

}
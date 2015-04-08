package blogApp;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.HashMap;
import java.sql.*;
import javax.servlet.annotation.*;


	@WebServlet("/Logout")
	public class Logout extends HttpServlet{


		@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
			HttpSession session = request.getSession(true);
			if(session.getAttribute("user-information") != null){
				request.getSession(true).removeAttribute("user-information");
				response.setHeader("Refresh" ,"3; URL = Login");
				response.setContentType("text/html");

				PrintWriter out = response.getWriter();
				out.println("<!doctype html>\n" +
								"<html>\n" +
									"<head>\n" +
										"<link rel=\"stylesheet\" type=\"text/css\" href=\"LogoutStyleSheet.css\">\n" +
									"</head>\n" +
									"<body>\n" +
										"<div class = \"title\">\n" +
											"<p>You have been logged out!</p>\n" +
											"<p>Redirecting...</p>\n" +
										"</div>\n" +
									"</body>\n" +
								"</html>");

			}
			else{
				response.setHeader("Refresh" ,"1; URL = Login");
				response.setContentType("text/html");
			}

			
		}

		@Override
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

			doGet(request, response);
		}

}
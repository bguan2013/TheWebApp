package blogApp;

import javax.servlet.annotation.*;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.sql.*;


@WebServlet("/CommentBlog")
public class CommentBlog extends HttpServlet{

	ServletContext context;

	@Override
	public void init(){

		context = getServletContext();

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		


	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		doGet(request,response);
	}

	private void saveCommentToMySQL(Blog blog, Comment comment){

		MySQLDatabase mysql = (MySQLDatabase)context.getAttribute("mysql");
		String addCommment = "INSERT INTO " + mysql.getCommentTableName() + " (" + ") " + " VALUES ()";
		mysql.updateUsingCommand(addCommment);
	}
	

}
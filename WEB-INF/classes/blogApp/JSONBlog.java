
package blogApp;


import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;


@WebServlet("/JSON/ViewBlog")
public class JSONBlog extends HttpServlet{

	ServletContext context;

	@Override 
	public void init(){
		context = getServletContext();
	}


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Blog[] tempBlogs = getNewestBlogsWithSize(10);
		
		request.setAttribute("JSON", tempBlogs);

		RequestDispatcher rd = request.getRequestDispatcher("/JSON/ViewBlog/View");
		rd.forward(request, response);

	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}

		public Blog[] getNewestBlogsWithSize(int size){

		try{
			MySQLDatabase mysql = (MySQLDatabase)context.getAttribute("mysql");
			String getBlogsFromMySQL = "SELECT * FROM " + mysql.getBlogTableName() + " ORDER BY " + mysql.getBlogId() + " DESC LIMIT " + size;
			Statement tempStatement = mysql.getConnection().createStatement();
			ResultSet blogSet = tempStatement.executeQuery(getBlogsFromMySQL);
			
		
			Blog[] blogArray = new Blog[size];
			boolean empty = true;


			for(int i = 0; i < blogArray.length; i++){

				if(blogSet.next()){
					empty = false;
					int blogId = blogSet.getInt(mysql.getBlogId());
					String title = blogSet.getString(mysql.getBlogTitle());
					String post = blogSet.getString(mysql.getBlogPost());
					//getTimestamp gets the time
					String date = blogSet.getTimestamp(mysql.getBlogTime()).toString();
					int blogUserId = blogSet.getInt(mysql.getBlogUserId());

					blogArray[i] = new Blog(retrieveUserFromMySQLById(blogUserId), blogId,title,post,date);
				}
			}
			blogSet.close();
			tempStatement.close();
			
			if(empty){
				return null;
			}
			else{
				return blogArray;
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}

		

	}

	private int numberOfBlogsInMySQL(){


		try{
			MySQLDatabase mysql = (MySQLDatabase)context.getAttribute("mysql");
			String getRows = "SELECT COUNT(*) FROM " + mysql.getBlogTableName();

			Statement statement = mysql.getConnection().createStatement();
			ResultSet set = statement.executeQuery(getRows);

			int numberOfBlogs = set.getInt("COUNT(*)");

			set.close();
			statement.close();
			

		return numberOfBlogs;
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
	
	}

	private User retrieveUserFromMySQLById(int userId){

		try{

			MySQLDatabase mysql = (MySQLDatabase)context.getAttribute("mysql");
			String selectUser = "SELECT * FROM " + mysql.getUserTableName() + " WHERE " + mysql.getUserId() + " = "  + userId;
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

}
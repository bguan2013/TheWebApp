package blogApp;


import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.feed.synd.SyndEntry;




@WebServlet(urlPatterns = "/ViewBlog", loadOnStartup = 1)
public class ViewBlog extends HttpServlet{

	MySQLDatabase mysql;
	ServletContext context; 

	@Override
	public void init() throws ServletException{
	

		context = getServletContext();
		try{
			if(context.getAttribute("mysql") == null){

				mysql = new MySQLDatabase();
				context.setAttribute("mysql", mysql);
				if(context.getAttribute("mysql") == null){
					System.out.println("didn't create mysql successfully.");
				}

			}
			else{
				System.out.println("Retrieving MYSQL!!!");
				mysql = (MySQLDatabase)context.getAttribute("mysql");
			}
		}
		catch(SQLException e){

			mysql = null;
			System.out.println("Database created UNSUCCESSFUL!!!");
			e.printStackTrace();

		}
		catch(Exception e){

			System.out.println("Driver initiation unseccessful...");
			e.printStackTrace();

		}

	}


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

			

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		Blog[] currentBlogArray = getNewestBlogsWithSize(10);
		request.setAttribute("blog-array", currentBlogArray);
		RequestDispatcher rd = context.getRequestDispatcher("/ViewBlogPage.jsp");
		rd.forward(request, response);
	
	

					


	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


		doGet(request,response);
	}


	@Override
	public void destroy(){
		try{
			mysql.closeConnections();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public Blog[] getNewestBlogsWithSize(int size){

		try{
			
			String getBlogsFromMySQL = "SELECT * FROM " + mysql.getBlogTableName() + " ORDER BY " + mysql.getBlogId() + " DESC LIMIT " + size;
			Statement tempStatement = mysql.getConnection().createStatement();
			ResultSet blogSet = tempStatement.executeQuery(getBlogsFromMySQL);
			//ResultSet blogSet = mysql.getResultSetUsingCommand(getBlogsFromMySQL);
		
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


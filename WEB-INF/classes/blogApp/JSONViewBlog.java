


package blogApp;


import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;


@WebServlet("/JSON/ViewBlog/View")
public class JSONViewBlog extends HttpServlet{

	ServletContext context;

	@Override 
	public void init(){
		context = getServletContext();
	}


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
		
		Blog[] tempBlogs = (Blog[])request.getAttribute("JSON");
		PrintWriter out = response.getWriter();

		if(tempBlogs != null){

			response.setContentType("application/json; charset=UTF-8");
			out.println("{ \"Blogs\":[\n");
			
			for(int i = 0; i < tempBlogs.length; i++){
				if(tempBlogs[i] != null){
					Blog temp = tempBlogs[i];
					out.println("{\n");
					out.println("\"title\": " + "\"" + temp.getTitle() + "\"" + ",\n");
					out.println("\"Content\": " + "\"" + temp.getContent() + "\"" + ",\n");
					out.println("\"Date\": " + "\"" + temp.getDate() + "\"" + ",\n");
					out.println("\"User\": " + "\"" + temp.getUser().getUserName() + "\"" + "\n");
					out.println("}\n");
				}
			}
			out.println("]}\n");
		}

		else{

			response.setContentType("text/html");
			out.println("<html><head></head><body><p>no content<p></body></html>");

		}
		

	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}



}
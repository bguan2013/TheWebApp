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


	@WebServlet("/RSSNews")
	public class RSSNews extends HttpServlet{

		ServletContext context; 
		@Override
		public void init() throws ServletException{

			context = getServletContext();
			
		}

		

		@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{


			response.setContentType("text/html");

			PrintWriter out = response.getWriter();

			Rss[] currentRSSArray = getRSSNewsArrayWithSize(10);
			request.setAttribute("rss-array", currentRSSArray);
			RequestDispatcher rd = context.getRequestDispatcher("/RSSNewsPage.jsp");
			rd.forward(request, response);
		}
		@Override
		public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
			doGet(request, response);
		}

		@SuppressWarnings("unchecked")
		private List<SyndEntry> getLastestNewsFromYahoo()throws IOException{

			URL url = new URL("http://rss.news.yahoo.com/rss/us");
			SyndFeedInput syndFeedInput = new SyndFeedInput();
			SyndFeed syndFeed = null;
			XmlReader xmlReader = new XmlReader(url);
			try{
				syndFeed = syndFeedInput.build(xmlReader);
				List<SyndEntry> rss = syndFeed.getEntries();
				return rss;
			}
			catch(IllegalArgumentException e){
				e.printStackTrace();
				return null;
			}
			catch(FeedException e){
				e.printStackTrace();
				return null;
			}

	}

		private Rss[] getRSSNewsArrayWithSize(int size){

			Rss[] rssArray = new Rss[size];

			try{
				Iterator iterator = getLastestNewsFromYahoo().iterator();
				for(int i = 0; i < size; i++){
					if(iterator.hasNext()){
						SyndEntry syndEntry = (SyndEntry)iterator.next();
						String author = syndEntry.getAuthor();
						String title = syndEntry.getTitle();
						String description = syndEntry.getDescription().getValue();
						String date = syndEntry.getPublishedDate().toString();

						//Temporary RSS ID
						rssArray[i] = new Rss(0, author, title, description, date);
					}
				}	
				return rssArray;
			}
			catch(IOException e){
				e.printStackTrace();
				return null;
			}	
		}
	}
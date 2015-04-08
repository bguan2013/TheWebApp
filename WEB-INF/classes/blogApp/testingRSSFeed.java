package blogApp;


import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.feed.synd.SyndEntry;

import java.io.*;
import java.net.URL;
import java.util.Iterator;


public class testingRSSFeed{


	public static void main(String[] args) throws IOException{

		URL url = new URL("http://rss.news.yahoo.com/rss/tech");
		SyndFeedInput syndFeedInput = new SyndFeedInput();
		SyndFeed syndFeed = null;
		XmlReader xmlReader = new XmlReader(url);	

		try{	
			syndFeed = syndFeedInput.build(xmlReader);			
		}

		catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		catch(FeedException e){
			e.printStackTrace();
		}
		finally{
			Iterator it = syndFeed.getEntries().iterator();
			while(it.hasNext()){
				SyndEntry entry = (SyndEntry)it.next();
				System.out.println(entry.getTitle());
			}
		}

	}

}
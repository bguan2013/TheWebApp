
package blogApp;

public class Rss{


	int rssId;
	String rssAuthor;
	String rssTitle;
	String rssDescription;
	String rssDate;


	public Rss(int rssId, String rssAuthor, String rssTitle, String rssDescription, String rssDate){
		this.rssId = rssId;
		this.rssAuthor = rssAuthor;
		this.rssTitle = rssTitle;
		this.rssDescription = rssDescription;
		this.rssDate = rssDate;
	}

	public int getRssId(){

		return rssId;
	}
	public String getRssAuthor(){

		return rssAuthor;
	}
	public String getRssTitle(){

		return rssTitle;
	}
	public String getRssDescription(){

		return rssDescription;
	}
	public String getRssDate(){

		return rssDate;
	}

}
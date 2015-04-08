
package blogApp;


public class Blog{


	User user;

	int id;
	String title;
	String content;
	String date;
	
	public Blog(User user, int id, String title, String content, String date){

		this.user = user;
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
		
		
	}

	public int getId(){
		return this.id;
	}
	public String getTitle(){

		return this.title;
	}

	public String getContent(){
		return this.content;
	}
	public String getDate(){
		return this.date;
	}
	public User getUser(){
		return this.user;
	}

}
package blogApp;


import javax.servlet.http.*;



public class User{

	private int id;
	private String user_name;
	private String password;
	
	

	public User(int id, String user_name, String password){

		this.id = id;
		this.user_name = user_name;
		this.password = password;
		

	}

	public String getUserName(){

		return user_name;

	}

	public String getPassword(){

		return password;

	}
	public int getId(){

		return id;
	}

}
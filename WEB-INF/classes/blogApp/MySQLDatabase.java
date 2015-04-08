package blogApp;

import java.sql.*;



public class MySQLDatabase{
	
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost/";
	
	private final String DefaultSQLUserName = "bguan";
	private final String DefaultSQLPassword = "gb920907";

	//Database information
	private final String DefaultSQLDatabaseName = "blog_app";
	private final String DefaultSQLTableNameForUsers = "user";
	private final String DefaultSQLTableNameForBlogs = "blog";
	private final String DefaultSQLTableNameForComments = "comment";

	//user table information
	private final String DefaultSQLNameForUserId = "user_id";
	private final String DefaultSQLNameForUserUsername = "user_username";
	private final String DefaultSQLNameForUserPassword = "user_password";


	//blog table information
	private final String DefaultSQLNameForBlogId = "blog_id";
	private final String DefaultSQLNameForBlogTitle = "blog_title";
	private final String DefaultSQLNameForBlogPost = "blog_post";
	private final String DefaultSQLNameForBlogTime = "blog_time";
	private final String DefaultSQLNameForBlogUserId = "blog_user_id";

	//comment table information
	private final String DefaultSQLNameForCommentId = "comment_id";
	private final String DefaultSQLNameForCommentPost = "comment_post";
	private final String DefaultSQLNameForCommentTime = "commment_time";
	private final String DefaultSQLNameForCommentUserId = "comment_user_id";
	private final String DefaultSQLNameForCommentBlogId = "comment_blog_id";

	

	private Connection connection = null;
	private Statement statement = null;

	private String mySQLUserName;
	private String mySQLPassword;	
	private String mySQLDatabaseName;
	private String mySQLTableNameForUsers;
	private String mySQLTableNameForBlogs;
	private String mySQLTableNameForCommments;

	private String mySQLUserUserId;
	private String mySQLUserUsername;
	private String mySQLUserUserPassword;
	
	private String mySQLBlogBlogId;
	private String mySQLBlogBlogTitle;
	private String mySQLBlogBlogPost;
	private String mySQLBlogBlogTime;
	private String mySQLBlogUserId;

	private String mySQLCommentCommentId;
	private String mySQLCommentCommentPost;
	private String mySQLCommentCommentTime;
	private String mySQLCommentUserId;
	private String mySQLCommentBlogId;

	public MySQLDatabase() throws Exception{
		
		
		Class.forName(JDBC_DRIVER);

		mySQLUserName = DefaultSQLUserName;
		mySQLPassword = DefaultSQLPassword;
		mySQLDatabaseName = DefaultSQLDatabaseName;
		mySQLTableNameForUsers = DefaultSQLTableNameForUsers;
		mySQLTableNameForBlogs = DefaultSQLTableNameForBlogs;

		mySQLUserUserId = DefaultSQLNameForUserId;
		mySQLUserUsername = DefaultSQLNameForUserUsername;
		mySQLUserUserPassword = DefaultSQLNameForUserPassword;

		mySQLBlogBlogId = DefaultSQLNameForBlogId;
		mySQLBlogBlogTitle = DefaultSQLNameForBlogTitle;
		mySQLBlogBlogPost = DefaultSQLNameForBlogPost;
		mySQLBlogBlogTime = DefaultSQLNameForBlogTime;
		mySQLBlogUserId = DefaultSQLNameForBlogUserId;

		mySQLCommentCommentId = DefaultSQLNameForCommentId;
		mySQLCommentCommentPost = DefaultSQLNameForCommentPost;
		mySQLCommentCommentTime = DefaultSQLNameForCommentTime;
		mySQLCommentUserId = DefaultSQLNameForCommentUserId;
		mySQLCommentBlogId = DefaultSQLNameForCommentBlogId;

		connection = DriverManager.getConnection(DB_URL, mySQLUserName, mySQLPassword);
		statement = connection.createStatement();

		createDatabaseAndTables(mySQLDatabaseName,mySQLTableNameForUsers, mySQLTableNameForBlogs, mySQLTableNameForCommments);
		
		
	}	
	
	public MySQLDatabase(String username, String password, String databaseName, String userTableName, String blogTableName, String commentTableName) throws Exception{
		
		Class.forName(JDBC_DRIVER);

		mySQLUserName = username;
		mySQLPassword = password;
		mySQLDatabaseName = databaseName;
		mySQLTableNameForUsers = userTableName;
		mySQLTableNameForBlogs = blogTableName;
		mySQLTableNameForCommments = commentTableName;
		connection = DriverManager.getConnection(DB_URL, mySQLUserName, mySQLPassword);
		statement = connection.createStatement();

		createDatabaseAndTables(mySQLDatabaseName,mySQLTableNameForUsers, mySQLTableNameForBlogs, mySQLTableNameForCommments);
	
	}

	public ResultSet getResultSetUsingCommand(String command){

		try{

			ResultSet result = statement.executeQuery(command);

			return result;
		}
		catch(SQLException e){

			e.printStackTrace();
			return null;
		}
	}

	public void updateUsingCommand(String update){

		try{
			statement.executeUpdate(update);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}


	public void closeConnections() throws Exception{
		
		if(connection != null){

			connection.close();

		}
		if(statement != null){

			statement.close();

		}
	
	
	}


	//Update here for the comment table
	private void createDatabaseAndTables(String databaseName, String userTableName, String blogTableName, String commentTableName){

		try{
			ResultSet temp = connection.getMetaData().getCatalogs();
			boolean databaseExists = false;
			while(temp.next()){
				if(temp.getString(1).equals(databaseName)){
					databaseExists = true;
				}
			}

			if(databaseExists){
				System.out.println("Database exists.");
				String useDatabase = "USE " + databaseName;
				statement.executeUpdate(useDatabase);

				boolean userTableExists = false;
				temp = connection.getMetaData().getTables(databaseName, null, userTableName, null);
				if(temp.next()){
					userTableExists = true;
				}


				if(userTableExists){
					System.out.println("User table exists.");

					boolean blogTableExists = false;
					temp = connection.getMetaData().getTables(databaseName, null, blogTableName, null);
					if(temp.next()){
						blogTableExists = true;
					}
						if(blogTableExists){

							System.out.println("Blog table exists.");


							//creating comments
							boolean commentTableExists = false;
							temp = connection.getMetaData().getTables(databaseName, null, commentTableName, null);
							if(temp.next()){
								commentTableExists = true;
							}
							if(commentTableExists){
								System.out.println("Comment table exists.");
							}
							else{
								System.out.println("Creating comment table.");
								String createCommentTable = "CREATE TABLE " +  commentTableName + "("
														    + getCommentId() +  " INTEGER not NULL auto_increment, "
															+ getCommentPost() + " TEXT not NULL, "
															+ getCommentTime() + " DATETIME, "
															+ getCommentUserId() + " INTEGER not NULL, "
															+ getCommentBlogId() + " INTEGER not NULL, "
															+ "PRIMARY KEY(" + getCommentId() + "))";

								statement.executeUpdate(createCommentTable);


							}
							
						}
						//create the blog table
						else{
							System.out.println("Creating blog table.");
							String createBlogTable = "CREATE TABLE " + blogTableName + " ("
													+ "blog_id INTEGER not NULL auto_increment, "
													+ "blog_title VARCHAR(100) not NULL, "
													+ "blog_post TEXT not NULL, "
													+ "blog_time DATETIME, "
													+ "blog_user_id INTEGER not NULL, "
													+ "PRIMARY KEY(blog_id))";		
							statement.executeUpdate(createBlogTable);

							//creating Comments
							boolean commentTableExists = false;
							temp = connection.getMetaData().getTables(databaseName, null, commentTableName, null);
							if(temp.next()){
								commentTableExists = true;
							}
							if(commentTableExists){
								System.out.println("Comment table exists.");
							}
							else{
								System.out.println("Creating comment table.");
								String createCommentTable = "CREATE TABLE " +  commentTableName + "("
														    + getCommentId() +  " INTEGER not NULL auto_increment, "
															+ getCommentPost() + " TEXT not NULL, "
															+ getCommentTime() + " DATETIME, "
															+ getCommentUserId() + " INTEGER not NULL, "
															+ getCommentBlogId() + " INTEGER not NULL, "
															+ "PRIMARY KEY(" + getCommentId() + "))";

								statement.executeUpdate(createCommentTable);


							}
						}
						
			


				}
				//create user table
				else{

					System.out.println("Creating user table.");
					///////////FIX ALL OF THE CREATE TABLE COMMANDS
					String createUserTable = "CREATE TABLE " + userTableName + "("
											 + "user_id INTEGER not NULL auto_increment, "
											 + "user_username VARCHAR(25) not NULL, "
											 + "user_password VARCHAR(25) not NULL, "
											 + "PRIMARY KEY(user_id))";		
					statement.executeUpdate(createUserTable);

					boolean blogTableExists = false;
					temp = connection.getMetaData().getTables(databaseName, null, blogTableName, null);
					if(temp.next()){

						blogTableExists = true;
					}
						if(blogTableExists){
							System.out.println("Blog table exists.");

							//Creating Comments
							boolean commentTableExists = false;
							temp = connection.getMetaData().getTables(databaseName, null, commentTableName, null);
							if(temp.next()){
								commentTableExists = true;
							}
							if(commentTableExists){
								System.out.println("Comment table exists.");
							}
							else{
								System.out.println("Creating comment table.");
								String createCommentTable = "CREATE TABLE " +  commentTableName + "("
														    + getCommentId() +  " INTEGER not NULL auto_increment, "
															+ getCommentPost() + " TEXT not NULL, "
															+ getCommentTime() + " DATETIME, "
															+ getCommentUserId() + " INTEGER not NULL, "
															+ getCommentBlogId() + " INTEGER not NULL, "
															+ "PRIMARY KEY(" + getCommentId() + "))";

								statement.executeUpdate(createCommentTable);


							}
							
						}
						//create the blog table
						else{
							System.out.println("Creating blog table.");
							String createBlogTable = "CREATE TABLE " + blogTableName + " ("
													+ "blog_id INTEGER not NULL auto_increment, "
													+ "blog_title VARCHAR(100) not NULL, "
													+ "blog_post TEXT not NULL, "
													+ "blog_time DATETIME, "
													+ "blog_user_id INTEGER not NULL, "
													+ "PRIMARY KEY(blog_id))";		
							statement.executeUpdate(createBlogTable);

							//creating comments
							boolean commentTableExists = false;
							temp = connection.getMetaData().getTables(databaseName, null, commentTableName, null);
							if(temp.next()){
								commentTableExists = true;
							}
							if(commentTableExists){
								System.out.println("Comment table exists.");
							}
							else{
								System.out.println("Creating comment table.");
								String createCommentTable = "CREATE TABLE " +  commentTableName + "("
														    + getCommentId() +  " INTEGER not NULL auto_increment, "
															+ getCommentPost() + " TEXT not NULL, "
															+ getCommentTime() + " DATETIME, "
															+ getCommentUserId() + " INTEGER not NULL, "
															+ getCommentBlogId() + " INTEGER not NULL, "
															+ "PRIMARY KEY(" + getCommentId() + "))";

								statement.executeUpdate(createCommentTable);


							}
						}
						
					

				}
				

			}	

			//create the database and tables
			else{
				System.out.println("Create database.");
				String createDatabase = "CREATE DATABASE " + databaseName;
				statement.executeUpdate(createDatabase);
				String useDatabase = "USE " + databaseName;
				statement.executeUpdate(useDatabase);
				

				boolean userTableExists = false;
				temp = connection.getMetaData().getTables(databaseName, null, userTableName, null);
				if(temp.next()){
					userTableExists = true;
				}


				if(userTableExists){
					System.out.println("User table exists.");
					boolean blogTableExists = false;
					temp = connection.getMetaData().getTables(databaseName, null, blogTableName, null);
					if(temp.next()){
						blogTableExists = true;
					}
						if(blogTableExists){
							System.out.println("Blog table exists.");


							//creating comments
							boolean commentTableExists = false;
							temp = connection.getMetaData().getTables(databaseName, null, commentTableName, null);
							if(temp.next()){
								commentTableExists = true;
							}
							if(commentTableExists){
								System.out.println("Comment table exists.");
							}
							else{
								System.out.println("Creating comment table.");
								String createCommentTable = "CREATE TABLE " +  commentTableName + "("
														    + getCommentId() +  " INTEGER not NULL auto_increment, "
															+ getCommentPost() + " TEXT not NULL, "
															+ getCommentTime() + " DATETIME, "
															+ getCommentUserId() + " INTEGER not NULL, "
															+ getCommentBlogId() + " INTEGER not NULL, "
															+ "PRIMARY KEY(" + getCommentId() + "))";

								statement.executeUpdate(createCommentTable);


							}
							
						}
						//create the blog table
						else{
							System.out.println("Creating blog table.");
							String createBlogTable = "CREATE TABLE " + blogTableName + " ("
													+ "blog_id INTEGER not NULL auto_increment, "
													+ "blog_title VARCHAR(100) not NULL, "
													+ "blog_post TEXT not NULL, "
													+ "blog_time DATETIME, "
													+ "blog_user_id INTEGER not NULL, "
													+ "PRIMARY KEY(blog_id))";		
							statement.executeUpdate(createBlogTable);


							//creating comments
							boolean commentTableExists = false;
							temp = connection.getMetaData().getTables(databaseName, null, commentTableName, null);
							if(temp.next()){
								commentTableExists = true;
							}
							if(commentTableExists){
								System.out.println("Comment table exists.");
							}
							else{
								System.out.println("Creating comment table.");
								String createCommentTable = "CREATE TABLE " +  commentTableName + "("
														    + getCommentId() +  " INTEGER not NULL auto_increment, "
															+ getCommentPost() + " TEXT not NULL, "
															+ getCommentTime() + " DATETIME, "
															+ getCommentUserId() + " INTEGER not NULL, "
															+ getCommentBlogId() + " INTEGER not NULL, "
															+ "PRIMARY KEY(" + getCommentId() + "))";

								statement.executeUpdate(createCommentTable);


							}
						}
						
					


				}
				//create user table
				else{

					System.out.println("Creating user table.");
					String createUserTable = "CREATE TABLE " + userTableName + "("
											 + "user_id INTEGER not NULL auto_increment, "
											 + "user_username VARCHAR(25) not NULL, "
											 + "user_password VARCHAR(25) not NULL, "
											 + "PRIMARY KEY(user_id))";	

					boolean blogTableExists = false;
					temp = connection.getMetaData().getTables(databaseName, null, blogTableName, null);
					if(temp.next()){
						
						blogTableExists = true;
					}
						if(blogTableExists){
							System.out.println("Blog table exists.");


							//creating comments
							boolean commentTableExists = false;
							temp = connection.getMetaData().getTables(databaseName, null, commentTableName, null);
							if(temp.next()){
								commentTableExists = true;
							}
							if(commentTableExists){
								System.out.println("Comment table exists.");
							}
							else{
								System.out.println("Creating comment table.");
								String createCommentTable = "CREATE TABLE " +  commentTableName + "("
														    + getCommentId() +  " INTEGER not NULL auto_increment, "
															+ getCommentPost() + " TEXT not NULL, "
															+ getCommentTime() + " DATETIME, "
															+ getCommentUserId() + " INTEGER not NULL, "
															+ getCommentBlogId() + " INTEGER not NULL, "
															+ "PRIMARY KEY(" + getCommentId() + "))";

								statement.executeUpdate(createCommentTable);


							}
							
						}
						//create the blog table
						else{
							System.out.println("Creating blog table.");
							String createBlogTable = "CREATE TABLE " + blogTableName + " ("
													+ "blog_id INTEGER not NULL auto_increment, "
													+ "blog_title VARCHAR(100) not NULL, "
													+ "blog_post TEXT not NULL, "
													+ "blog_time DATETIME, "
													+ "blog_user_id INTEGER not NULL, "
													+ "PRIMARY KEY(blog_id))";		
							statement.executeUpdate(createBlogTable);


							//creating comments
							boolean commentTableExists = false;
							temp = connection.getMetaData().getTables(databaseName, null, commentTableName, null);
							if(temp.next()){
								commentTableExists = true;
							}
							if(commentTableExists){
								System.out.println("Comment table exists.");
							}
							else{
								System.out.println("Creating comment table.");
								String createCommentTable = "CREATE TABLE " +  commentTableName + "("
														    + getCommentId() +  " INTEGER not NULL auto_increment, "
															+ getCommentPost() + " TEXT not NULL, "
															+ getCommentTime() + " DATETIME, "
															+ getCommentUserId() + " INTEGER not NULL, "
															+ getCommentBlogId() + " INTEGER not NULL, "
															+ "PRIMARY KEY(" + getCommentId() + "))";

								statement.executeUpdate(createCommentTable);


							}
						}
						
					

				}

			}
		}
		catch(SQLException e){

			e.printStackTrace();
		}


	}


	public Connection getConnection(){

		return this.connection;
	}

	public Statement getStatement(){

		return this.statement;
	}

	public String getDatabaseName(){


		return this.mySQLDatabaseName;
	}

	public String getUserTableName(){

		return this.mySQLTableNameForUsers;
	}

	public String getBlogTableName(){

		return this.mySQLTableNameForBlogs;
	}

	public String getCommentTableName(){

		return this.mySQLTableNameForCommments;
	}

	public String getUserId(){

		return this.mySQLUserUserId;
	}

	public String getUserUsername(){

		return this.mySQLUserUsername;
	}

	public String getUserPassword(){

		return this.mySQLUserUserPassword;
	}

	public String getBlogId(){

		return this.mySQLBlogBlogId;
	}
	public String getBlogTitle(){

		return this.mySQLBlogBlogTitle;
	}
	public String getBlogPost(){

		return this.mySQLBlogBlogPost;
	}
	public String getBlogTime(){

		return this.mySQLBlogBlogTime;
	}
	public String getBlogUserId(){

		return this.mySQLBlogUserId;
	}

	public String getCommentId(){

		return this.mySQLCommentCommentId;
	}
	public String getCommentPost(){

		return this.mySQLCommentCommentPost;
	}
	public String getCommentTime(){

		return this.mySQLCommentCommentTime;
	}
	public String getCommentUserId(){

		return this.mySQLCommentUserId;
	}
	public String getCommentBlogId(){

		return this.mySQLCommentBlogId;
	}

	//Write get methods for mysql literals
}
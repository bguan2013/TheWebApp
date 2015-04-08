package blogApp;


import java.sql.*;

public class testMySQL{
	


	public static void main(String[] args){

		try{
			/*
				MySQLDatabase database = new MySQLDatabase();
				ResultSet databaseNames = database.getConnection().getMetaData().getCatalogs();
				ResultSet tableNames = database.getConnection().getMetaData().getTables("test",null,"user",null);
				while(databaseNames.next()){

					System.out.println(databaseNames.getString(1));

				}
			*/
			MySQLDatabase database = new MySQLDatabase();

			String selectUser = "SELECT * FROM " + database.getUserTableName() + " WHERE " + database.getUserUsername() + " = " + "'" + "bguan@oswego.edu'";
			System.out.println(selectUser);
			ResultSet resultUser = database.getResultSetUsingCommand(selectUser);
				
			if(resultUser.next()){
				System.out.println("Exists");

			}
			else{
				System.out.println("Does not Exist");
			}

			database.closeConnections();
		}
		catch(SQLException e){

			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}

}
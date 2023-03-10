package employee;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class EmployeeApi {
	
	private static final Logger logger=Logger.getLogger("HelloWorld.class");
	private Connection con;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
	}
	
	public EmployeeApi() throws Exception{
		Properties props=new Properties();
		InputStream inStream=new FileInputStream("employee.properties");
		props.load(inStream);
		
		Class.forName(props.getProperty("jdbc.driver")); 
		con=DriverManager.getConnection(  
				props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
	}
	
	public void insert() throws SQLException
	{
		Statement stmt=con.createStatement();
		logger.info("Inserting Employees : ");
		for(int i=0;i<3;i++)
		{
			int id=i;
			String name="name "+i;
			int age=30+i;
			String query="Insert into employee values("+id+", '"+name+"', "+age+")";
			logger.info(query);
			stmt.executeUpdate(query);
		}
		stmt.close();
	}
	public ResultSet select() throws SQLException
	{
		logger.info("Selecting Employees : ");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select * from employee");  
		return rs;
//		while(rs.next())  
//			logger.info(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3));  
//		stmt.close(); 
	}
	public void delete() throws SQLException
	{
		logger.info("Deleting all Employees : ");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select * from employee");  
		List<Integer> idList=new ArrayList<Integer>();
		while(rs.next())  
			idList.add(rs.getInt(1));
		for(int i=0;i<idList.size();i++)
		{
			stmt.executeUpdate("delete from employee where id="+idList.get(i));
		}
		stmt.close(); 
	}
}

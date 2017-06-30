package Sql;

import java.sql.*;

import org.apache.commons.net.ftp.FTPFile;

public class MySql {
	
	public Connection connect;
	public Statement statement;
	
	public MySql(){
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			// Class.forName("org.gjt.mm.mysql.Driver");
//			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ftpdisk", "root", "Anjianwei111");
			// 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码
			
			
//			System.out.println("Success connect Mysql server!");
			statement = connect.createStatement();
		
//			初始化user表
//			String sql1 = "INSERT INTO user VALUES('FtpUser','Anjianwei')";
//			String sql2 = "INSERT INTO user VALUES('test','test')";
//			String sql3 = "INSERT INTO user VALUES('user','pass')";
//			statement.execute(sql1);
//			statement.execute(sql2);
//			statement.execute(sql3);
//			ResultSet rs = statement.executeQuery("select * from user");
//			// user 为你表的名称
//			while (rs.next()) {
//				System.out.println(rs.getString("name"));
//			}
			
//			String sql2 = ("Delete from user where id="+244+"");
//			statement.execute(sql2);
//			rs = statement.executeQuery("select * from user");
//			// user 为你表的名称
//			while (rs.next()) {
//				System.out.println("..."+rs.getString("name"));
//			}
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws SQLException {
		MySql mysql = new MySql();
//		String str1 = "user0";
//		String str2 = "pass0";
//		String sql = "INSERT INTO user VALUES('"+str1+"/','"+str2+"');";
//		mysql.statement.execute(sql);
		
//		String sql2 = "update user set password='zhele' where name='user0'";
//		mysql.statement.execute(sql2);
		
//		ResultSet rs = mysql.statement.executeQuery("select * from user");
//		// user 为你表的名称
//		while (rs.next()) {
//			System.out.println(rs.getString("password"));
//		}
		
//		System.out.println(mysql.addFileNote("testPath", "testName", "testText"));
		
//		String path = "Path0";
//		String name = "Name0";
//		String note = "Text0";
//		
//		String sql = "INSERT INTO file VALUES('path0','name0','text0');";
//		System.out.println(mysql.statement.execute(sql));
		ResultSet rs = mysql.statement.executeQuery("select * from file");
		// user 为你表的名称
		while (rs.next()) {
			System.out.println(rs.getString("filepath"));
		}
		
		
		
	}
	
	public boolean addFileNote(String path, String name, String note) throws SQLException{
		String sql = "INSERT INTO file VALUES('"+path+"','"+name+"','"+note+"');";
		return statement.execute(sql);
	}
	
	public boolean isNoted(String path) throws SQLException{
		ResultSet rs=statement.executeQuery("select * from file where filepath='"+path+"'");  
		return rs.next();
	}
	
	public String getNote(String path) throws SQLException{
		ResultSet rs=statement.executeQuery("select * from file where filepath='"+path+"'");  
		if(rs.next())
			return rs.getString("note");
		else
			return null;
	}
	
	public boolean changeNote(String path, String newNote) throws SQLException{
		ResultSet rs=statement.executeQuery("select * from file where filepath='"+path+"'");  
		//update 表名称 set 列名称=新值 where 更新条件;
		if(rs.next()){
			String sql = "update file set note='"+newNote+"' where filepath='"+path+"'";
			return statement.execute(sql);
		}
		return false;
	}
	public boolean addExistFileNote(String path, String newNote) throws SQLException{
		ResultSet rs=statement.executeQuery("select * from file where filepath='"+path+"'");  
		//update 表名称 set 列名称=新值 where 更新条件;
		if(rs.next()){
			String sql = "update file set note='"+rs.getString("note")+newNote+"' where filepath='"+path+"'";
			return statement.execute(sql);
		}
		return false;
	}
}
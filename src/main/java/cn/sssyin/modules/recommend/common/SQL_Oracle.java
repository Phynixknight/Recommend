package cn.sssyin.modules.recommend.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import org.apache.log4j.Logger;

/**
 * 链接Oracle的SQL父类
 * 所有链接不同数据库的子类继承自此父类
 * 不同数据库的密码等也固化在各个子类中，请注意安全管理
 * 根据业务需求只支持一次执行，执行完毕关闭连接
 * */
public class SQL_Oracle {
//	private static Logger logger = Logger.getLogger(SQL_Oracle.class); 
	
	private String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	protected String DATABASE_URL = null;
	protected String DATABASE_USER = null;
	protected String DATABASE_PASSWORD = null;
	protected Connection con = null;

	public SQL_Oracle(){}

	protected Connection getConnection() {
		try {
			Class.forName(DRIVER_CLASS);
			con = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
			return con;
		} catch (Exception ex) {
			System.out.println("2:" + ex.getMessage());
		}
		return con;
	}
	
	public ResultSet execute(String sql) throws SQLException{
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
//			logger.info(sql);
			
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void close() throws SQLException{
		con.close();
	}
	
}
package cn.sssyin.modules.recommend.common;

//import org.apache.log4j.Logger;

/**
 * 连接ssorder数据库的子类
 */
public class SQL_Oracle_ssorder extends SQL_Oracle {
	// private Logger logger = Logger.getLogger(SQL_Oracle_ssorder.class);

//	private String DATABASE_URL = "jdbc:oracle:thin:@192.168.2.108:1521:orcl";
//	private String DATABASE_USER = "ssorder";
//	private String DATABASE_PASSWORD = "ssorder";

	public SQL_Oracle_ssorder() {
		DATABASE_URL = Manager.Database_URL;
		DATABASE_USER = Manager.Database_Order_User;
		DATABASE_PASSWORD = Manager.Database_Order_Password;
		con = getConnection();
	}

}
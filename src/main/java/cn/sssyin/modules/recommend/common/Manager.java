package cn.sssyin.modules.recommend.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

/**
 * 提供时间管理
 * 文件夹管理的工具
 * */
public class Manager {
	private static Properties prop = new Properties();
	static{
		try {
			prop.load(new FileInputStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String DB_sep = ",";
	public static final String UserIterm_sep = ",";
	public static final String DB_Dir = "spark/sql/";
	public static final String Stores = "spark/stores/";
	public static final String Model_Dir = "spark/models/";
	public static final String UserItem_Dir= "spark/user_item/";
	public static final String UserProduct_Dir= "spark/user_product/";
	public static final String UserIterm_Similarity_Dir= "spark/user_item_sim/";
	public static final String Sys_sep = FileSystems.getDefault().getSeparator();
	
	public static final String Database_Order_User = prop.getProperty("jdbc.username");//"ssorder";
	public static final String Database_Order_Password = prop.getProperty("jdbc.password");//"ssorder";
	public static final String Database_URL = prop.getProperty("jdbc.url");//"jdbc:oracle:thin:@192.168.2.108:1521:orcl";
	public static final String Server_Domain = prop.getProperty("cxf.domain");

	public static String time_yymmdd(){
	      Date dNow = new Date( );
	      SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
	      return ft.format(dNow);
	}
	
	public static String get_max_folder(String path) throws IOException{
		Optional<Path> maxFile = Files.list(Paths.get(path)).max((x,y) -> x.compareTo(y));
		return maxFile.get().toString();
	}
	
	public static void write2File(ArrayList<String> al,String dir) throws IOException{
		Files.write(Paths.get(dir + Sys_sep + al.get(0)), (al.get(1) + "," + al.get(2)).getBytes());
	}
	
	public static void main(String args[]) throws IOException{
//		Paths.get("spark/").forEach(System.out::println);
//		Files.list(Paths.get("spark/")).forEach(System.out::println);
		System.out.println(get_max_folder("spark/sql"));
		
	}
	
}

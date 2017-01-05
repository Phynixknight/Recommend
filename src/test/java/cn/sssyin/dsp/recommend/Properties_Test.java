package cn.sssyin.dsp.recommend;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Properties_Test {
	public static void main(String[] args) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream("src/main/resources/dev/application.properties"));

			System.out.println(prop.getProperty("jdbc.testSql"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
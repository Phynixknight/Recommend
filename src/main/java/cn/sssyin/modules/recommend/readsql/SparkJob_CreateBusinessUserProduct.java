package cn.sssyin.modules.recommend.readsql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import cn.sssyin.modules.recommend.common.InitSpark;
import cn.sssyin.modules.recommend.common.Manager;
import cn.sssyin.modules.recommend.common.SQL_Oracle;
import cn.sssyin.modules.recommend.common.SQL_Oracle_ssorder;

public class SparkJob_CreateBusinessUserProduct {
	private SQL_Oracle sql;

	private static JavaSparkContext sc = InitSpark.getInstance();

	public SparkJob_CreateBusinessUserProduct() {
		sql = new SQL_Oracle_ssorder();
	}

	public void PullTheTable() throws SQLException {
		String sql_stat = "select a.BUYER_ID,b.PRODUCT_ID,a.MALL_ID from T_ORDER_DETAIL b join T_ORDER_INFO a on b.ORDER_ID = a.ID";
		List<String> res = new ArrayList<String>();
		//read from db
		ResultSet rs = sql.execute(sql_stat);
		while (rs.next())
			res.add(rs.getString("MALL_ID") + "," + rs.getString("BUYER_ID") + "," + rs.getString("PRODUCT_ID"));
		JavaRDD<String> jr = sc.parallelize(res);
		jr.saveAsTextFile(Manager.DB_Dir + Manager.time_yymmdd());
		sql.close();
	}

	public static void main(String[] args) throws SQLException {
		SparkJob_CreateBusinessUserProduct sjb = new SparkJob_CreateBusinessUserProduct();
		sjb.PullTheTable();
	}

}
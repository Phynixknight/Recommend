package cn.sssyin.modules.recommend.common;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * 单例模式的Spark主程序
 * */
public class InitSpark {
	private final static SparkConf conf = new SparkConf().setMaster("local").setAppName("Recommend");
	private static volatile JavaSparkContext jsc = null;
	
	
	private InitSpark(){}
	
	public static JavaSparkContext getInstance(){
		if(jsc == null)
			synchronized(InitSpark.class){
				if(jsc == null) 
					jsc = new JavaSparkContext(conf);
			}
		return jsc;
	}
}

package cn.sssyin.modules.recommend.sparkjob;

import java.io.IOException;
import java.util.List;

//import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

import cn.sssyin.modules.recommend.common.InitSpark;
import cn.sssyin.modules.recommend.common.Manager;

public class SparkJob_SplitBusinessUserItem{
//	Logger logger = Logger.getLogger(SparkJob_UserItem.class);
	
	JavaSparkContext jsc = InitSpark.getInstance();
	
	public void split2business() throws IOException{
		JavaRDD<String[]> jr = jsc.textFile(Manager.get_max_folder(Manager.DB_Dir)).map(line->line.split(Manager.DB_sep)).persist(StorageLevel.MEMORY_AND_DISK());
		JavaRDD<String> storesrdd = jr.map(x -> x[0]).distinct().persist(StorageLevel.MEMORY_ONLY());
		storesrdd.saveAsTextFile(Manager.Stores+Manager.time_yymmdd());
		List<String> stores = storesrdd.collect();
		for(String store:stores){
			JavaRDD<String> sjr = jr.filter(x->x[0].equals(store)).map(y->y[1] + Manager.UserIterm_sep+y[2]);
			sjr.saveAsTextFile(Manager.UserItem_Dir+Manager.time_yymmdd()+Manager.Sys_sep+store);
		}
	}

	public static void main(String[] args) throws IOException {
		SparkJob_SplitBusinessUserItem sjui = new SparkJob_SplitBusinessUserItem();
		sjui.split2business();
	}

}

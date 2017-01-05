package cn.sssyin.modules.recommend.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import cn.sssyin.modules.recommend.common.InitSpark;
import cn.sssyin.modules.recommend.common.Manager;
import scala.Tuple2;

public class CF_User {

//	Logger logger = Logger.getLogger(CF_User.class);
	JavaSparkContext sc = InitSpark.getInstance();
	
	public static void main(String args[]) throws IOException{
		CF_User cf = new CF_User();
		cf.cf();
	}
	
	List<String> stores;
	public void get_stores() throws IOException{
		stores = new ArrayList<String>();
		JavaRDD<String> storesRDD = sc.textFile(Manager.get_max_folder(Manager.Stores));
		stores = storesRDD.collect();
	}
	
	int Rank = 10;
	int Iterations = 10;
	public void user_cf(JavaRDD<String> data,String store){
		JavaRDD<Rating> ratings = data.map(line -> line.split(","))
				.map(s -> new Rating(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 1));
		
		//生成ALS推荐模型
		MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), Rank, Iterations,0.01);
		
		//评估模型
		JavaPairRDD<Integer,Integer> u_i = ratings.mapToPair(r -> new Tuple2(r.user(),r.product()));
//		JavaPairRDD<Tuple2<Integer, Integer>,Double> predictions = JavaPairRDD.fromJavaRDD(
//				model.predict(u_i).map(r -> new Tuple2<>(new Tuple2<>(r.user(),r.product()),r.rating()))
//		);
		JavaRDD<String> predictions = model.predict(u_i).map(r -> ""+r.user() + "," + r.product() + "," + r.rating());
		
		//保存用户物品相似度结果
		predictions.saveAsTextFile(Manager.UserIterm_Similarity_Dir + Manager.time_yymmdd() + Manager.Sys_sep+store);

		// Save models
		model.save(sc.sc(), Manager.Model_Dir + Manager.time_yymmdd() + Manager.Sys_sep + store);
		
	}

	public void cf() throws IOException {
		get_stores();
		for(String store:stores){
			String path2 = Manager.get_max_folder(Manager.get_max_folder(Manager.UserItem_Dir)+Manager.Sys_sep+store);
			JavaRDD<String> user_item = sc.textFile(path2);
			user_cf(user_item,store);
		}
		
		
//		String path = "spark/data/mllib/test.data";
//		JavaRDD<String> data = sc.textFile(path);
//		JavaRDD<Rating> ratings = data.map(line -> line.split(","))
//				.map(s -> new Rating(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Double.parseDouble(s[2])));
//
//		// Build the recommendation model using ALS
//		int rank = 10;
//		int numIterations = 10;
//		MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), rank, numIterations, 0.01);
//
//		// Evaluate the model on rating data
//		JavaRDD<Tuple2<Object, Object>> userProducts = ratings.map(r -> new Tuple2(r.user(), r.product()));
//		JavaPairRDD<Tuple2<Integer, Integer>, Double> predictions = JavaPairRDD.fromJavaRDD(
//						model.predict(JavaRDD.toRDD(userProducts)).toJavaRDD()
//						.map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating()))
//				);
//		JavaRDD<Tuple2<Double, Double>> ratesAndPreds = JavaPairRDD.fromJavaRDD(
//						ratings.map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating())								)
//				).join(predictions).values();
//		double MSE = JavaDoubleRDD.fromRDD(
//				ratesAndPreds.map(s -> (Object) Math.pow(s._1 - s._2,2)).rdd()
//				).mean();
//
//		System.out.println("Mean Squared Error = " + MSE);
//
//		// Save and load model
//		model.save(sc.sc(), "spark/tmp/myCollaborativeFilter");
//		MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(sc.sc(),
//				"spark/tmp/myCollaborativeFilter");
	}
}

package cn.sssyin.modules.recommend.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import cn.sssyin.modules.recommend.api.Recommend_Api;
import cn.sssyin.modules.recommend.common.InitSpark;
import cn.sssyin.modules.recommend.common.Manager;
import scala.Tuple2;

public class Offline_Recommend extends Recommend_Api {

	JavaSparkContext jsc = InitSpark.getInstance();
	List<String> stores = new ArrayList<String>();
	HashMap<String, MatrixFactorizationModel> map_model = new HashMap<String, MatrixFactorizationModel>();
	HashMap<String, JavaPairRDD<Tuple2<String, String>, String>> map_rdd = new HashMap<String, JavaPairRDD<Tuple2<String, String>, String>>();

	public Offline_Recommend() throws IOException {
		JavaRDD<String> storesRDD = jsc.textFile(Manager.get_max_folder(Manager.Stores));
		stores = storesRDD.collect();
		init();
	}

	private void init() throws IOException {
		for (String store : stores) {
			MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(jsc.sc(),Manager.get_max_folder(Manager.Model_Dir) + Manager.Sys_sep + store + Manager.Sys_sep);
			map_model.put(store, sameModel);
			// JavaRDD<String[]> jr =jj
			// jsc.textFile(Manager.get_max_folder(Manager.UserIterm_Similarity_Dir)).map(line
			// -> line.split(","));
		}
	}

	public static void main(String[] args) throws IOException {
		Offline_Recommend off_rec = new Offline_Recommend();
		String store = "559";
		Integer user = 1374;
		List<Integer> prods = off_rec.recommend(store, user);
		prods.stream().forEach(System.out::println);
	}

	@Override
	public Map<Integer, List<Integer>> recommend(String store, Integer[] users) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> recommend(String store, Integer user) {
		Rating[] rate = map_model.get(store).recommendProducts(user, 100);
		List<Integer> products = new ArrayList<Integer>(rate.length);
		for (Rating rat : rate) {
			products.add(rat.product());
		}
		return products;
	}

}

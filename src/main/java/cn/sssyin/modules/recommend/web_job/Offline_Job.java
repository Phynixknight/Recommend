package cn.sssyin.modules.recommend.web_job;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import cn.sssyin.modules.recommend.api.Recommend_Api;
import cn.sssyin.modules.recommend.api.impl.Offline_Recommend;
import cn.sssyin.modules.recommend.readsql.SparkJob_CreateBusinessUserProduct;
import cn.sssyin.modules.recommend.sparkjob.SparkJob_SplitBusinessUserItem;
import cn.sssyin.modules.recommend.tools.CF_User;

public class Offline_Job implements Runnable {

	int step = 0;

	public Offline_Job(int step) {
		if (step < 0 || step > 4)
			System.err.println(step);
		this.step = step;
	}

	public static void main(String[] args) {
		Offline_Job job = new Offline_Job(0);
		job.run();
	}

	// step 1 Offline read from oracle datebase to local
	public Boolean step1_readTable() {
		SparkJob_CreateBusinessUserProduct sjb = new SparkJob_CreateBusinessUserProduct();
		try {
			sjb.PullTheTable();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// step 2 Offline split user_item by business
	public Boolean step2_split_to_business() {
		SparkJob_SplitBusinessUserItem sjui = new SparkJob_SplitBusinessUserItem();
		try {
			sjui.split2business();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// step 3 Offline calcuate CF
	public Boolean step3_cf() {
		CF_User cf = new CF_User();
		try {
			cf.cf();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// step 4 Online recommend user itmes
	public Boolean step4_rec() {
		Recommend_Api rec = null;
		try {
			rec = new Offline_Recommend();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		String store_test = "559";
		Integer user_test = 1374;
		List<Integer> products = rec.recommend(store_test, user_test);
		return true;
	}

	@Override
	public void run() {
		if (step == 1) step1_readTable();
		if (step == 2) step2_split_to_business();
		if (step == 3) step3_cf();
		if (step == 4) step4_rec();
		if (step == 0) {
			Boolean s2 = false;
			Boolean s3 = false;
			Boolean s4 = false;
			Boolean s1 = step1_readTable();
			if(s1) s2 = step2_split_to_business();
			if(s2) s3 = step3_cf();
			if(s3) s4 = step4_rec();
		}
	}

}

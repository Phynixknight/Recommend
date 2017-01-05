package cn.sssyin.modules.recommend.api;

import java.util.List;
import java.util.Map;

public abstract class Recommend_Api {

	public Recommend_Api(){};
	
	public abstract List<Integer> recommend(String store,Integer user);
	public abstract Map<Integer,List<Integer>> recommend(String store,Integer[] users);

}

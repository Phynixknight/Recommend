package cn.sssyin.server.recommend;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.sssyin.modules.recommend.api.Recommend_Api;
import cn.sssyin.modules.recommend.api.impl.Offline_Recommend;

public class ProductServiceImpl implements ProductService {

	Recommend_Api rec = null;

	public ProductServiceImpl() throws IOException {
		rec = new Offline_Recommend();
	}

	public List<Integer> recommend(String store, Integer user) {
		List<Integer> products = rec.recommend(store, user);
		return products;
	}

	@Override
	public List<Integer> retrieveProductsByName(String name) {
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(11);
		lst.add(22);
		return lst;
	}

	@Override
	public List<Integer> retrieveAllProducts(String store) {
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(11);
		lst.add(22);
		return lst;
	}

	@Override
	public List<Integer> retrieveProductById(String store, int id) {
		List<Integer> products = rec.recommend(store, id);
		return products;
	}

}
package cn.sssyin.modules.recommend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sssyin.modules.recommend.api.Recommend_Api;
import cn.sssyin.modules.recommend.api.impl.Offline_Recommend;

public class Business_Recommend_Service_Controller {

	Recommend_Api rec = null;

	public Business_Recommend_Service_Controller() throws IOException {
		rec = new Offline_Recommend();
	}

	
	@RequestMapping(value = "/get")
	public Map<Integer, List<Integer>> recommend_user_products(String store, Integer user) {
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		String store_test = "559";
		Integer user_test = 1374;
		List<Integer> products = rec.recommend(store_test, user_test);
		map.put(user, products);
		return map;
	}
	
//	@Controller
//	@RequestMapping("/hello")
//	@RequestMapping(method = RequestMethod.GET)
//	public String printHello(ModelMap model) {
//		model.addAttribute("message", "Hello Spring MVC Framework!");
//		return "hello";
//	}

}

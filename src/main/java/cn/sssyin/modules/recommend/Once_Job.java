package cn.sssyin.modules.recommend;

import cn.sssyin.modules.recommend.web_job.Offline_Job;

public class Once_Job {
	public static void main(String args[]){
		Offline_Job off_job = new Offline_Job(0);
		off_job.run();
	}
}

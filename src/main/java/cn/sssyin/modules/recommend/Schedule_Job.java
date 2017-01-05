package cn.sssyin.modules.recommend;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.sssyin.modules.recommend.web_job.Offline_Job;

public class Schedule_Job {
  public static void main(String[] args) {
	  Schedule_Job sj = new Schedule_Job();
	  sj.start_job_on_service_start();
  }
  
  public void start_job_on_service_start(){
	  ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	  service.scheduleAtFixedRate(new Offline_Job(0), 0 , 7, TimeUnit.DAYS);
  }
  
}

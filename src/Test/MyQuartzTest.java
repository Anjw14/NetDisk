package Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MyQuartzTest {

	public static void main(String[] args) throws Exception
	{
		//创建scheduler工厂类，并生成scheduler对象
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler sche = factory.getScheduler();
		Scheduler sche_2 = factory.getScheduler();
		
		//先输出当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(new Date()));
		
		//何时执行这个计划？需要先生成一个Date对象,表示计划要执行的时间
		//DateBuilder是Quartz提供的类，evenMinuteDateAfterNow是从现在起下一分钟
		Date runTime = DateBuilder.evenMinuteDateAfterNow();
		Date runTime_2 = DateBuilder.todayAt(17, 31, 50);
		
		//分别创建JobDetail和Trigger对象，方式类似，都是静态构造，XXXBuilder.newXXX().build()
		JobDetail job = JobBuilder.newJob(BaseJob.class).build();
		Trigger trigger = TriggerBuilder.newTrigger().startAt(runTime).build();
		JobDetail job_2 = JobBuilder.newJob(Job_2.class).build();
		Trigger trigger_2 = TriggerBuilder.newTrigger().startAt(runTime_2).build();
		
		//把任务和触发器加到scheduler中
		sche.scheduleJob(job, trigger);
		sche_2.scheduleJob(job_2, trigger_2);
		//开始任务
		sche.start();
		sche_2.start();
		
		//此时Quartz应该是另起线程去获取时间了，所以应该在主线程等待一段时间，等计划任务执行完毕
		//要不然main方法一结束，Quartz也就不运行了。
		Thread.sleep(60*1000);
		sche.shutdown();
		sche_2.shutdown();
	}
}
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
		//����scheduler�����࣬������scheduler����
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler sche = factory.getScheduler();
		Scheduler sche_2 = factory.getScheduler();
		
		//�������ǰʱ��
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(new Date()));
		
		//��ʱִ������ƻ�����Ҫ������һ��Date����,��ʾ�ƻ�Ҫִ�е�ʱ��
		//DateBuilder��Quartz�ṩ���࣬evenMinuteDateAfterNow�Ǵ���������һ����
		Date runTime = DateBuilder.evenMinuteDateAfterNow();
		Date runTime_2 = DateBuilder.todayAt(17, 31, 50);
		
		//�ֱ𴴽�JobDetail��Trigger���󣬷�ʽ���ƣ����Ǿ�̬���죬XXXBuilder.newXXX().build()
		JobDetail job = JobBuilder.newJob(BaseJob.class).build();
		Trigger trigger = TriggerBuilder.newTrigger().startAt(runTime).build();
		JobDetail job_2 = JobBuilder.newJob(Job_2.class).build();
		Trigger trigger_2 = TriggerBuilder.newTrigger().startAt(runTime_2).build();
		
		//������ʹ������ӵ�scheduler��
		sche.scheduleJob(job, trigger);
		sche_2.scheduleJob(job_2, trigger_2);
		//��ʼ����
		sche.start();
		sche_2.start();
		
		//��ʱQuartzӦ���������߳�ȥ��ȡʱ���ˣ�����Ӧ�������̵߳ȴ�һ��ʱ�䣬�ȼƻ�����ִ�����
		//Ҫ��Ȼmain����һ������QuartzҲ�Ͳ������ˡ�
		Thread.sleep(60*1000);
		sche.shutdown();
		sche_2.shutdown();
	}
}
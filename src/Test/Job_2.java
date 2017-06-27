package Test;


import java.text.SimpleDateFormat;
import java.util.Date;  
import org.quartz.Job;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  
  
public class Job_2 implements Job
{
    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException
    {
        //在这里编写要执行的计划任务，这里为了简单起见，写了一行打印语句
        //实际可能是数据库的CRUD操作，或者定时对操作系统环境进行优化，等等
        System.out.println("mmp");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Job Time is:"+format.format(new Date()));
    }
}

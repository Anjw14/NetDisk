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
        //�������дҪִ�еļƻ���������Ϊ�˼������д��һ�д�ӡ���
        //ʵ�ʿ��������ݿ��CRUD���������߶�ʱ�Բ���ϵͳ���������Ż����ȵ�
        System.out.println("mmp");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Job Time is:"+format.format(new Date()));
    }
}

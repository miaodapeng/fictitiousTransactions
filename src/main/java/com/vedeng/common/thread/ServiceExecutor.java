
package com.vedeng.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.vedeng.common.thread.ext.ThreadExt;
import com.vedeng.common.util.SmsUtil;
import com.vedeng.system.dao.MessageMapper;
import com.vedeng.system.model.Message;

/**
 * <b>Description: 业务处理线程</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName ServiceThread.java
 * <br><b>Date: 2018年7月31日 上午9:01:59 </b> 
 *
 */
public class ServiceExecutor
{	
	/**
	 * 发送短信 
	 * 1
	 */
	public static final Integer SEND_SMS_TYPE = 1;
	
	/**
	 * 线程数据模型
	 */
	private ThreadExt ext;
	
	/**
	 * 等待时间
	 */
	private Long waitTime;
	
	/**
	 * 等待时间单位 TimeUnit.SECONDS/TimeUnit.MINUTES
	 */
	private TimeUnit unit;
	
	/**
	 * 
	 */
	private MessageMapper messageMapper;
	
	/**
	 * 
	 * <b>Description: 可做延时执行线程池</b><br> 
	 * 
	 * @param messageMapper
	 * @param ext
	 * @param waitTime
	 * @param unit TimeUnit.SECONDS/TimeUnit.MINUTES
	 * <b>Author: Franlin</b>
	 * <br><b>Date: 2018年7月31日 上午9:41:29</b>
	 */
	public ServiceExecutor(MessageMapper messageMapper, ThreadExt ext, long waitTime, TimeUnit unit)
	{
		this.messageMapper = messageMapper;
		this.waitTime = waitTime;
		this.unit = unit;
		this.ext = ext;
	}

	/**
	 * 
	 * <b>Description:</b><br> 
	 * @param messageMapper
	 * @param ext
	 * <b>Author: Franlin</b>
	 * <br><b>Date: 2018年7月31日 下午5:38:22</b>
	 */
	public ServiceExecutor(MessageMapper messageMapper, ThreadExt ext)
	{
		this.messageMapper = messageMapper;
		this.ext = ext;
	}
	
	
	/**
	 * 
	 * <b>Description: 延迟执行</b><br> 
	 * @param type 1--发送短信
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月31日 上午10:02:40 </b>
	 */
	public void delayExecute(int type)
	{
		// 延迟执行
		if(null != ext && null != waitTime && null != unit && null != messageMapper)
		{
			ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
			scheduledThreadPool.schedule(new Runnable()
			{
				public void run()
				{
					System.out.println("延时任务执行开始 ..............................");
					if(1 == type)
					{
						Message msg = messageMapper.queryBussinessStatus(ext);
						if(null != msg)
						{
							// 存在未读商机信息,则发送
							sendSms();
						}
					}
					
					System.out.println("延时任务执行结束 ..............................");
				}
			}, waitTime, unit);
			// 执行完后立马关闭
			scheduledThreadPool.shutdown();
		}
	}
	
	/**
	 * 
	 * <b>Description: 立即执行</b><br> 
	 * @param type
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月31日 上午11:23:06 </b>
	 */
	public void execute(int type)
	{
		// 执行
		if(null != ext && null != messageMapper)
		{
			ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
			singleThreadPool.execute(new Runnable()
			{
				public void run()
				{
					System.out.println("任务执行 开始  ..............................");
					if(1 == type)
					{
						Message msg = messageMapper.queryBussinessStatus(ext);
						if(null != msg)
						{
							// 存在未读商机信息,则发送
							sendSms();
						}
					}
					System.out.println("任务执行 结束  ..............................");
				}
			});
			
		}
	}
	
	/**
	 * 
	 * <b>Description: 发送短信</b><br> 
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月31日 上午10:03:27 </b>
	 */
	private void sendSms()
	{
		if(null == ext)
		{
			return;
		}
		String acceptTelPhone = ext.getAcceptTelPhone();
		if(null == acceptTelPhone)
		{
			acceptTelPhone = "";
		}
		// 联系人名称
		String traderContactName = ext.getTraderContactName();
		if(null == traderContactName)
		{
			traderContactName = "";
		}
		// 联系人电话
		String telPhone = ext.getTraderContactTelephone();
		if(null == telPhone)
		{
			telPhone = "";
		}
		// 产品名称
		String productName = ext.getProductName();
		if(null == productName)
		{
			productName = "";
		}
		// 短信模板参数
		String content = "@1@=" + traderContactName + ",@2@=" + telPhone + ",@3@=" + productName;
		Boolean sendTplSms = SmsUtil.sendTplSms(acceptTelPhone, "JSM40187-0036", content);
		if(!sendTplSms)
		{
			System.out.println("JSM40187-0036短信模板发送失败，发送手机号为:" + acceptTelPhone);
			return;
		}
	}
	
	
	
	
	

}


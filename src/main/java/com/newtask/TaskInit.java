package com.newtask;

import java.util.concurrent.atomic.AtomicBoolean;

import com.common.constants.Contant;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskInit extends XxlJobSpringExecutor{
	public static Logger logger = LoggerFactory.getLogger(XxlJobSpringExecutor.class);

	public static AtomicBoolean started=new AtomicBoolean(false);
	 @Override
	    public void start() throws Exception {
	 		try{
	       if(started.compareAndSet(false, true)) {
	    	   // super start
		        super.start();
	       }}catch(Exception e){
	 			logger.error(Contant.ERROR_MSG, e);
			}
	    }
}

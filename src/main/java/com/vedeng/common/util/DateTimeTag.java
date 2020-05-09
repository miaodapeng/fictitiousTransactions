package com.vedeng.common.util;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * <b>Description:</b><br> 毫秒时间戳转天时分秒
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> DateTag
 * <br><b>Date:</b> 2017年8月25日 上午17:43:33
 */
public class DateTimeTag extends TagSupport {
    public static Logger logger = LoggerFactory.getLogger(DateTimeTag.class);

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer value;

	
	
	@Override
    public int doStartTag() throws JspException {
    	if(value==null){
    		value=0;
    	}
    	long day = 0;  
        long hour = 0;  
        long min = 0;  
        long sec = 0;
        try {
        	day = value / (24 * 60 * 60 * 1000);  
            hour = (value / (60 * 60 * 1000) - day * 24);  
            min = ((value / (60 * 1000)) - day * 24 * 60 - hour * 60);  
            sec = (value/1000-day*24*60*60-hour*60*60-min*60);  
            
            String outTime = "";
            if(day > 0 ){
            	outTime += day + "天";
            }
            if(hour > 0 ){
            	outTime += hour + "小时";
            }
            if(min > 0 ){
            	outTime += min + "分";
            }
            outTime += sec + "秒";
            pageContext.getOut().write(outTime);
            
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return super.doStartTag();
    }



	public Integer getValue() {
		return value;
	}



	public void setValue(Integer value) {
		this.value = value;
	}




}

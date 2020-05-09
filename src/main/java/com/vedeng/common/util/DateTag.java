package com.vedeng.common.util;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * <b>Description:</b><br> 13位时间戳转时间格式 yyyy-MM-dd HH:mm:ss
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> DateTag
 * <br><b>Date:</b> 2017年4月25日 上午11:14:33
 */
public class DateTag extends TagSupport{
	public static Logger logger = LoggerFactory.getLogger(TagSupport.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 5576056142729710167L;
	
	
	private String value;
	
	private String format="yyyy-MM-dd HH:mm:ss";

    @Override
    public int doStartTag() throws JspException {
    	if(value==null||"".equals(value.trim())){
    		value="0";
    	}
        String vv = "" + value;
		try {
			if(vv.contains("-") || vv.contains(":")){
				pageContext.getOut().write(vv);
			}else{
    			long time = Long.valueOf(vv.trim());
    			if(time == 0){
    				pageContext.getOut().write("");
    			}else{
    				Calendar c = Calendar.getInstance();
    				c.setTimeInMillis(time);
    				SimpleDateFormat dateformat = new SimpleDateFormat(format);
    				String s = dateformat.format(c.getTime());
    				pageContext.getOut().write(s);
    			}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
        return super.doStartTag();
    }

    public void setValue(String value) {
        this.value = value;
    }

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}

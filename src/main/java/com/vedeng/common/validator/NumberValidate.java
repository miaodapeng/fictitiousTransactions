package com.vedeng.common.validator;

 
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import org.apache.commons.lang3.StringUtils;

public class NumberValidate  extends  ValidatorHandler<Object>{
	
	private String tip="不为数字类型";
	
	public NumberValidate(String tip) {
		this.tip=tip;
	}
	
	@Override
    public boolean validate(ValidatorContext context, Object t) {
		if(!isNumber(String.valueOf(t))) {
			context.addErrorMsg(tip);
			return false;
		}
        return true;
    }
	/**
	 * 是否是数字或小数
	 * @tags @return
	 * @exception
	 * @return boolean
	 */
	private boolean isNumber(String str){
		if(StringUtils.isBlank(str)){
			return false;
		}
		String reg = "\\d+(\\.\\d+)?";
		return str.matches(reg);

	}
}

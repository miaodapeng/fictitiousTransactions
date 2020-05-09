package com.vedeng.common.validator;

 
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
/**
 * 
 * <p>注意点：
 * new String[]{"",""}  为空，验证不会通过
 * @author Hank
 *
 */
public class NullValidate  extends  ValidatorHandler<Object>{
	
	private String tip="必填";
	
	public NullValidate(String tip) {
		this.tip=tip;
	} 
	
	@Override
    public boolean validate(ValidatorContext context, Object t) {
		if(t==null) {
			context.addErrorMsg(tip);
			return false;
		} 
		if(t instanceof Object[]) {
			if(ArrayUtils.isEmpty((Object[])t)) {
				context.addErrorMsg(tip);
				return false;
			}
			boolean allNull=true;
			for(Object obj:(Object[])t) {
				if(StringUtils.isNotBlank(String.valueOf(obj))){
					allNull=false;
				}
			}
			if(allNull) {
				context.addErrorMsg(tip);
				return false;
			}
		}else {
			if(StringUtils.isBlank(String.valueOf(t))){
				context.addErrorMsg(tip);
				return false;
			}
		}
        return true;
    }
}

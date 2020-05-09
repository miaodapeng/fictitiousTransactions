package com.vedeng.goods.model.vo;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.goods.GoodsCheckStatusEnum;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.model.dto.CoreSkuBaseDTO;
import com.vedeng.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CoreSkuBaseVO extends CoreSkuBaseDTO {

	public String getIsStockupShow() {
		isStockupShow = NumberUtils.toInt(getIsStockup() + "") > 0 ? "是" : "否";
		return isStockupShow;
	}

	public void setIsStockupShow(String isStockupShow) {
		this.isStockupShow = isStockupShow;
	}

	private String isStockupShow;
	private String checkStatusShow;
	private String modTimeShow;
	private String operateInfoIdShow;

	public String getAssignmentManager() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		UserService service=	context.getBean(UserService.class);
		User user=service.getUserById(getAssignmentManagerId());
		assignmentManager=user==null?"":user.getUsername();
		return assignmentManager;
	}

	public void setAssignmentManager(String assignmentManager) {
		this.assignmentManager = assignmentManager;
	}


	public String getAssignmentAssistant() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		UserService service=	context.getBean(UserService.class);
		User user=service.getUserById(getAssignmentAssistantId());

		assignmentAssistant=user==null?"":user.getUsername();

		return assignmentAssistant;
	}

	public void setAssignmentAssistant(String assignmentAssistant) {
		this.assignmentAssistant = assignmentAssistant;
	}

	private String assignmentManager;
	private String assignmentAssistant;

	public String getAssignment() {
		String assistant=getAssignmentAssistant();
		String manager=getAssignmentManager();
		StringBuilder result=new StringBuilder();

		if(StringUtils.isNotBlank(manager)){
			result.append("&"+manager);
		}

		if(StringUtils.isNotBlank(assistant)){
		 	result.append("&"+assistant);
		}

		if(StringUtils.isNotBlank(result.toString())){
			return result.substring(1);
		}else{
			return "待完善";
		}
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	private String assignment;

	public String getSkuInfoShow() {
		 if(StringUtils.isNotBlank(getModel())){
		 	return getModel();
		 }
		 return getSpec();
	}

	public void setSkuInfoShow(String skuInfoShow) {
		this.skuInfoShow = skuInfoShow;
	}

	private String skuInfoShow;


	public String getCheckStatusShow() {
		checkStatusShow = GoodsCheckStatusEnum.statusName(getCheckStatus());
		return checkStatusShow;
	}

	public void setCheckStatusShow(String checkStatusShow) {
		this.checkStatusShow = checkStatusShow;
	}

	public String getModTimeShow() {
		if (getModTime() != null) {
			modTimeShow = DateFormatUtils.format(getModTime(), DateUtil.TIME_FORMAT);
		}
		return modTimeShow;
	}

	public void setModTimeShow(String modTimeShow) {
		this.modTimeShow = modTimeShow;
	}

	public String getOperateInfoIdShow() {
		operateInfoIdShow = NumberUtils.toInt(getOperateInfoId() + "") > 0 ? "已添加" : "未添加";
		return operateInfoIdShow;
	}

	public void setOperateInfoIdShow(String operateInfoIdShow) {
		this.operateInfoIdShow = operateInfoIdShow;
	}
}

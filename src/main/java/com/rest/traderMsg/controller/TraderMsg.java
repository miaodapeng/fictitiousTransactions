package com.rest.traderMsg.controller;

import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.trader.model.WebAccount;
import org.activiti.engine.ActivitiException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Description: 耗材商城向erp推送消息
 * @Param:
 * @return:
 * @Author: scott.zhu
 * @Date: 2019/5/14
 */
@RestController
@RequestMapping("/tradermsg/sendMsg")
public class TraderMsg extends BaseController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(TraderMsg.class);

	@Autowired
	@Qualifier("saleorderService")
	protected SaleorderService saleorderService;

	/**
	 * @Description: 发送消息
	 * @Param: [request, traderId, orderNo, status]
	 * @return: void
	 * @Author: scott.zhu
	 * @Date: 2019/5/14
	 */
	@RequestMapping(value = {"/sendTraderMsg"}, method = RequestMethod.GET)
	public ResultInfo sendTraderMsg(HttpServletRequest request, String traderId, String saleorderNo, String messageId) {
		try {
			// 参数验证
			if (StringUtil.isBlank(messageId)) {
				return new ResultInfo(-1, "模板编号不允许为空");
			}
			if (StringUtil.isBlank(traderId) && StringUtil.isBlank(saleorderNo)) {
				return new ResultInfo(-1, "客户id和订单编号不允许同时为空");
			} else {
				Saleorder saleorder = new Saleorder();

				// 参数传递
				if (StringUtil.isNotBlank(traderId)) {
					saleorder.setTraderId(Integer.parseInt(traderId));
				}
				if (StringUtil.isNotBlank(saleorderNo)) {
					saleorder.setSaleorderNo(saleorderNo);
				}

				// 获取所有要发送的当前客户下的耗材订单/耗材订单数据,未生效的
				List<Saleorder> hcOrderList = saleorderService.getHcOrderList(saleorder);

				// 遍历发送消息
				if (CollectionUtils.isNotEmpty(hcOrderList)) {

					// 获取要发送的人员id
					SysOptionDefinition userIds = getSysOptionDefinition(SysOptionConstant.ID_995);
					if(userIds == null){
						return new ResultInfo(-1, "要发送的人员id不允许为空");
					}

					List<String> userIdList = Arrays.asList(userIds.getTitle().split(","));
					List<Integer> IntegerList = new ArrayList<Integer>();
					for (String x : userIdList) {
						Integer z = Integer.parseInt(x);
						IntegerList.add(z);
					}

					for (int i = 0; i < hcOrderList.size(); i++) {
                        // 发送
						Map<String,String> map = new HashMap<>();
						map.put("saleorderNo", hcOrderList.get(i).getSaleorderNo());
						String [] str = {"njadmin","2"};
						MessageUtil.sendMessage(Integer.parseInt(messageId), IntegerList, map, "./order/saleorder/view.do?saleorderId="+hcOrderList.get(i).getSaleorderId(),str);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error sendTraderMsg", e);
			throw new ActivitiException("Error sendTraderMsg", e);
		}
		return new ResultInfo(0, "操作成功");
	}

	@RequestMapping(value = "/sendWebaccountCertificateMsg",method = RequestMethod.POST)
	public ResultInfo sendWebaccountCertificateMsg(@RequestBody WebAccountCertificateMsg msg){
		try {
			String[] str = {"njadmin", "2"};
			Map<String, String> map = new HashMap<>();
			map.put("mobile", msg.getMobile());
			MessageUtil.sendMessage(msg.getMessageTemplateId(), msg.getUserIds(), map, msg.getUrl(), str);
			return new ResultInfo(0,"操作成功");
		}catch (Exception ex){
			return new ResultInfo(-1,"操作失败");
		}
	}
}
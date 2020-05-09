package com.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.apache.axis2.databinding.types.soapencoding.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.finance.model.AccountPeriod;

public class CustomerAccountPeriodTask extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(CustomerAccountPeriodTask.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	public void CustomerAccountPeriodPush() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AccountPeriod>>> TypeRef = new TypeReference<ResultInfo<List<AccountPeriod>>>() {};
		String url = httpUrl + "finance/accountperiod/customerAccountPeriodTask.htm";
		try {
			List<Long> dateList = new ArrayList<>();
			Calendar today = Calendar.getInstance();
			//将时分秒置0
			today.setTimeInMillis(DateUtil.convertLong(DateUtil.DateToString(new java.util.Date(),"yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			if (today.get(Calendar.DAY_OF_WEEK)-1 == 1) {
				
				/**当前日期为周一**/
				
				//周二
				today.add(Calendar.DATE, 1);
				dateList.add(today.getTimeInMillis());
				//周四
				today.add(Calendar.DATE, 2);
				dateList.add(today.getTimeInMillis());
			}else if (today.get(Calendar.DAY_OF_WEEK)-1 == 2) {

				/**当前日期为周二**/
				
				//周三
				today.add(Calendar.DATE, 1);
				dateList.add(today.getTimeInMillis());
				//周五
				today.add(Calendar.DATE, 2);
				dateList.add(today.getTimeInMillis());
				//周六
				today.add(Calendar.DATE, 1);
				dateList.add(today.getTimeInMillis());
				//周日
				today.add(Calendar.DATE, 1);
				dateList.add(today.getTimeInMillis());
			}else if (today.get(Calendar.DAY_OF_WEEK)-1 == 3) {

				/**当前日期为周三**/
				
				//周四
				today.add(Calendar.DATE, 1);
				dateList.add(today.getTimeInMillis());
				//下周一
				today.add(Calendar.DATE, 4);
				dateList.add(today.getTimeInMillis());
			}else if (today.get(Calendar.DAY_OF_WEEK)-1 == 4) {

				/**当前日期为周四**/
				
				//周五
				today.add(Calendar.DATE, 1);
				dateList.add(today.getTimeInMillis());
				//周六
				today.add(Calendar.DATE, 1);
				dateList.add(today.getTimeInMillis());
				//周日
				today.add(Calendar.DATE, 1);
				dateList.add(today.getTimeInMillis());
				//下周二
				today.add(Calendar.DATE, 2);
				dateList.add(today.getTimeInMillis());
			}else if (today.get(Calendar.DAY_OF_WEEK)-1 == 5) {

				/**当前日期为周五**/
				
				//下周一
				today.add(Calendar.DATE, 3);
				dateList.add(today.getTimeInMillis());
				//下周三
				today.add(Calendar.DATE, 2);
				dateList.add(today.getTimeInMillis());
			}
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, dateList, clientId, clientKey,TypeRef);
			Integer times = 0;
			if(result != null){
			if (result.getCode() == -1 && times <= 10) {// 失败后再次调用
				
				Thread.sleep(10000);// 休眠10s后再次执行
				HttpClientUtils.post(url, dateList, clientId, clientKey, TypeRef);
				
				times++;
			}
			
				if(result.getCode() == 0 && result.getData() != null){
					//快逾期的客户账期列表
					List<AccountPeriod> periodList = (List<AccountPeriod>) result.getData();
					for(AccountPeriod list : periodList){
						//获取该账期的负责人
						User user = userMapper.getUserByTraderId(list.getTraderId(),ErpConst.ONE);
						List<Integer> users = new ArrayList<>();
						users.add(user.getUserId());
						//发送消息
						Map<String, String> map = new HashMap<>();
						map.put("saleorderNo", list.getSaleorderNo());//订单单号
						map.put("traderName", list.getTraderName());//客户名称
						BigDecimal arrears = list.getAccountPeriodAmount().subtract(list.getAmount());//未归还金额
						map.put("arrears", arrears+"");
						//获取当前日期
						Calendar now = Calendar.getInstance();
						//将时分秒置0
						now.setTimeInMillis(DateUtil.convertLong(DateUtil.DateToString(new java.util.Date(),"yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
						String nowDay = DateUtil.convertString(now.getTimeInMillis(), null);//当前日期
						String expireDay = DateUtil.convertString(list.getAccountEndTime(), null);//到期日期
						String separatedDay = DateUtil.getDistanceTimeDays(nowDay,expireDay)+"";//距离到期时间还剩几天
						map.put("separatedDay", separatedDay);
						MessageUtil.sendMessage(79, users, map, "./trader/customer/baseinfo.do?traderId="+list.getTraderId(),"njadmin","2");
					}
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	public static void main(String[] args) {
		CustomerAccountPeriodTask c=new CustomerAccountPeriodTask();
		c.CustomerAccountPeriodPush();
	}
}

$(function() 
{
	// 业务场景类型 1业绩 2.品牌 3.客户 4.通话时长 5.转化率
	var sortType = $("#sortType").val();
	// 当前登陆用户id
	var userId = $("#others_userId_id").val();
	
	// 数据请求
	ajaxRequest(sortType, userId);
});

/**
 * 五行剑法之 请求数据
 * @returns
 */
function ajaxRequest(sortType, userId)
{
	// 历史数据的月份默认12个月
	var historyMonthNum = $("#historyMonthNum_id").val();
	
	requestBase(sortType, userId, historyMonthNum);
}


/**
 * 业绩/品牌/...
 * @param sortType 类型
 * @param userId   用户ID
 * @param historyMonthNum 历史数据月份
 * @returns
 */
function requestBase(sortType, userId, historyMonthNum)
{
	// 本月概况
	baseRequest(sortType, userId);
	// 当月明细
	requestDetail(sortType, userId, 1);
	// 当为客户类型,再次请求requestDetail tremType=2为150天的类型数据
	if(sortType == 3)
	{
		requestDetail(sortType, userId, 2);
	}
	
	if(sortType == 1)
	{
		var pageNo = 1;
		var pageSize = 10;
		requestOrderDetailForOne(userId, pageNo, pageSize);
		// 可发货&未计入业绩
		undeliverAndUncounted(userId, pageNo, pageSize);
		requestOrderDetailForThree(userId, pageNo, pageSize);
	}
	
	// 过往数据
	requestHistory(sortType, userId, historyMonthNum);
}

/**
 * 已计入业绩&未到全款
 * @param userId
 * @param pageNo
 * @param pageSize
 * @returns
 */
function requestOrderDetailForOne(userId, pageNo, pageSize)
{
	var companyId = $("#companyId").val();
	
	var jumpPageNo_ID1 = 111111111111111;
	var jumpPageSize1 = 'jumpPageSize1';
	
	var jumpPageNo = $("#" + jumpPageNo_ID1).val();
	// 点击跳转
	if(undefined != jumpPageNo && '' != jumpPageNo)
	{
		pageNo = jumpPageNo;
	}
	
	var jumpPageSize = $('select[name="'+jumpPageSize1+'"]').val();
	
	if(undefined != jumpPageSize && '' != jumpPageSize)
	{
		pageSize = jumpPageSize;
	}
	
	$("#perf_yes_1_page").html('');
	$("#perf_yes_1").html('');
	
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/queryOrderDetails.do",
		data : 
		{
			'orderUserId' : userId,
			'companyId' : companyId,
			'pageNo' : pageNo,
			'pageSize' : pageSize,
			'perfStatus' : 1
		},
		dataType : 'json',
		success : function(data) 
		{
			var list = data.data;
			var ht = '';
			var emptyFlag = false;
			
			// 如果list为空
			if(null == list || list.length == 0){
				emptyFlag = true;
			}
			
			// 如果空状态不为空
			if(!emptyFlag){
				for(var i = 0; i < list.length; i++){
					var mod = list[i];
					if(null == mod || undefined == mod)
						continue;
					ht += '<tr>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewsaleorder' + mod.saleorderId + '","link":"./order/saleorder/view.do?saleorderId=' +  mod.saleorderId + '","title":"订单信息"}\'>' + mod.saleorderNo + '</a></td>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewcustomer' + mod.traderId + '","link":"./trader/customer/baseinfo.do?traderId=' +  mod.traderId + '","title":"客户信息"}\'>' + mod.traderName + '</a></td>';

//						ht += '<td>' + mod.saleorderNo + '</td>';
//						ht += '<td>' + mod.traderName + '</td>';
						ht += '<td>' + mod.validTimeStr + '</td>';
						ht += '<td>' + mod.actualOrderAmount + '</td>';
						ht += '<td>' + mod.actualPayAmount + '</td>';
					ht += '</tr>';
					emptyFlag = false;
				}
			}
			
			if(emptyFlag)
			{						
				ht += '<tr><td colspan="5">暂无数据</td></tr>';
				$("#perf_yes_1").html(ht);
			}
			else
			{
				$("#perf_yes_1").html(ht);
				var page = data.page;
				var html = setPage(page, 'requestOrderDetailForOne(' + userId + ', '+ pageNo +', '+ pageSize + ')', jumpPageNo_ID1, jumpPageSize1);
				$("#perf_yes_1_page").html(html);
				loadMoreAddTitle();
			}	
			
			
		},
		error : function(data) 
		{
			if (data.status == 1001) 
			{
				layer.alert("当前操作无权限");
			}
		}
	});
		
}


/**
 * 可发货&未计入业绩
 * @param pageNo
 * @param pageNoId
 * @returns
 */
function undeliverAndUncounted(userId, pageNo, pageSize){
	
	var jumpPageNo_ID2 = 22222222222222222;
	var jumpPageSize2 = 'jumpPageSize2';
	var jumpPageNo = $("#" + jumpPageNo_ID2).val();
	// 点击跳转
	if(undefined != jumpPageNo && '' != jumpPageNo)
	{
		pageNo = jumpPageNo;
	}
	
	var jumpPageSize = $('select[name="'+jumpPageSize2+'"]').val();
	
	if(undefined != jumpPageSize && '' != jumpPageSize)
	{
		pageSize = jumpPageSize;
	}
	
	$("#perf_no_undeliver_1_page").html('');
	$("#perf_no_undeliver_1").html('');
	
	var companyId = $("#companyId").val();
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/queryOrderDetails.do",
		data : 
		{
			'orderUserId' : userId,
			'companyId' : companyId,
			'pageNo' : pageNo,
			'pageSize' : pageSize,
			'perfStatus' : 0,
			'satisfyDeliveryTime' : 1
		},
		dataType : 'json',
		success : function(data) 
		{
			var list = data.data;
			var ht = '';
			var emptyFlag = false;
			
			if(null == list || list.length == 0){
				emptyFlag = true;
			}
			
			if(!emptyFlag){
				for(var i = 0; i < list.length; i++) {
					var mod = list[i];
					if(null == mod || undefined == mod)
						continue;
					ht += '<tr>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewsaleorder' + mod.saleorderId + '","link":"./order/saleorder/view.do?saleorderId=' +  mod.saleorderId + '","title":"订单信息"}\'>' + mod.saleorderNo + '</a></td>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewcustomer' + mod.traderId + '","link":"./trader/customer/baseinfo.do?traderId=' +  mod.traderId + '","title":"客户信息"}\'>' + mod.traderName + '</a></td>';
						ht += '<td>' + mod.validTimeStr + '</td>';
						ht += '<td>' + mod.actualOrderAmount + '</td>';
						ht += '<td>' + mod.actualPayAmount + '</td>';
					ht += '</tr>';
					emptyFlag = false;
				}
			}
			
			if(emptyFlag)
			{						
				ht += '<tr><td colspan="5">暂无数据</td></tr>';
				$("#perf_no_undeliver_1").html(ht);
			}
			else
			{
				$("#perf_no_undeliver_1").html(ht);
				var page = data.page;
				var html = setPage(page, 'undeliverAndUncounted(' + userId + ', '+ pageNo +', '+ pageSize + ')', jumpPageNo_ID2, jumpPageSize2);
				$("#perf_no_undeliver_1_page").html(html);
				loadMoreAddTitle();
				
			}	
			
		},
		error : function(data) 
		{
			if (data.status == 1001) 
			{
				layer.alert("当前操作无权限");
			}
		}
	});
	
}




/**
 * 未计入业绩&已发货&未到全款
 * @param userId
 * @param pageNo
 * @param pageSize
 * @returns
 */
//function requestOrderDetailForTwo(userId, pageNo, pageSize)
//{
//	
//	var jumpPageNo_ID2 = 22222222222222222;
//	var jumpPageSize2 = 'jumpPageSize2';
//	var jumpPageNo = $("#" + jumpPageNo_ID2).val();
//	// 点击跳转
//	if(undefined != jumpPageNo && '' != jumpPageNo)
//	{
//		pageNo = jumpPageNo;
//	}
//	
//	var jumpPageSize = $('select[name="'+jumpPageSize2+'"]').val();
//	
//	if(undefined != jumpPageSize && '' != jumpPageSize)
//	{
//		pageSize = jumpPageSize;
//	}
//	
//	$("#perf_no_1_page").html('');
//	$("#perf_no_1").html('');
//	
//	var companyId = $("#companyId").val();
//	$.ajax(
//	{
//		type : "POST",
//		url : page_url + "/sales/fiveSales/queryOrderDetails.do",
//		data : 
//		{
//			'orderUserId' : userId,
//			'companyId' : companyId,
//			'pageNo' : pageNo,
//			'pageSize' : pageSize,
//			'perfStatus' : 0,
//			'orderDeliveryStatus' : 2
//		},
//		dataType : 'json',
//		success : function(data) 
//		{
//			var list = data.data;
//			var ht = '';
//			var emptyFlag = true;
//			for(var i = 0; i < list.length; i++)
//			{
//				var mod = list[i];
//				if(null == mod || undefined == mod)
//					continue;
//				ht += '<tr>';
//					ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewsaleorder' + mod.saleorderId + '","link":"./order/saleorder/view.do?saleorderId=' +  mod.saleorderId + '","title":"订单信息"}\'>' + mod.saleorderNo + '</a></td>';
//					ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewcustomer' + mod.traderId + '","link":"./trader/customer/baseinfo.do?traderId=' +  mod.traderId + '","title":"客户信息"}\'>' + mod.traderName + '</a></td>';
//					ht += '<td>' + mod.validTimeStr + '</td>';
//					ht += '<td>' + mod.actualOrderAmount + '</td>';
//					ht += '<td>' + mod.actualPayAmount + '</td>';
//				ht += '</tr>';
//				emptyFlag = false;
//			}
//			if(emptyFlag)
//			{						
//				ht += '<tr><td colspan="5">暂无数据</td></tr>';
//				$("#perf_no_1").html(ht);
//			}
//			else
//			{
//				$("#perf_no_1").html(ht);
//				var page = data.page;
//				var html = setPage(page, 'requestOrderDetailForTwo(' + userId + ', '+ pageNo +', '+ pageSize + ')', jumpPageNo_ID2, jumpPageSize2);
//				$("#perf_no_1_page").html(html);
//				loadMoreAddTitle();
//				
//			}	
//			
//		},
//		error : function(data) 
//		{
//			if (data.status == 1001) 
//			{
//				layer.alert("当前操作无权限");
//			}
//		}
//	});
//	
//}

/**
 * 已生效&未付款 15天
 * @param userId
 * @param pageNo
 * @param pageSize
 * @returns
 */
function requestOrderDetailForThree(userId, pageNo, pageSize)
{
	var companyId = $("#companyId").val();
	
	var jumpPageNo_ID3 = 333333333333333;
	var jumpPageSize3 = 'jumpPageSize3';
	var jumpPageNo = $("#" + jumpPageNo_ID3).val();
	// 点击跳转
	if(undefined != jumpPageNo && '' != jumpPageNo)
	{
		pageNo = jumpPageNo;
	}
	
	var jumpPageSize = $('select[name="'+jumpPageSize3+'"]').val();
	
	if(undefined != jumpPageSize && '' != jumpPageSize)
	{
		pageSize = jumpPageSize;
	}
	
	$("#perf_no_2_page").html('');
	$("#perf_no_2").html('');
	
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/queryOrderDetails.do",
		data : 
		{
			'orderUserId' : userId,
			'companyId' : companyId,
			'pageNo' : pageNo,
			'pageSize' : pageSize,
			'validStatus' : 1
		},
		dataType : 'json',
		success : function(data) 
		{
			var list = data.data;
			var ht = '';
			var emptyFlag = false;
			
			// 空
			if(null == list || list.length == 0)
			{
				emptyFlag = true;
			}
			
			// 如果list不为空
			if(!emptyFlag){
				for(var i = 0; i < list.length; i++) {
					var mod = list[i];
					if(null == mod || undefined == mod)
						continue;
					ht += '<tr>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewsaleorder' + mod.saleorderId + '","link":"./order/saleorder/view.do?saleorderId=' +  mod.saleorderId + '","title":"订单信息"}\'>' + mod.saleorderNo + '</a></td>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewcustomer' + mod.traderId + '","link":"./trader/customer/baseinfo.do?traderId=' +  mod.traderId + '","title":"客户信息"}\'>' + mod.traderName + '</a></td>';
						ht += '<td>' + mod.validTimeStr + '</td>';
						ht += '<td>' + mod.actualOrderAmount + '</td>';
						ht += '<td>' + mod.actualPayAmount + '</td>';
					ht += '</tr>';
					emptyFlag = false;
				}
			}
			
			if(emptyFlag)
			{						
				ht += '<tr><td colspan="5">暂无数据</td></tr>';
				$("#perf_no_2").html(ht);
			}
			else
			{
				$("#perf_no_2").html(ht);
				var page = data.page;
				var html = setPage(page, 'requestOrderDetailForThree(' + userId + ', '+ pageNo +', '+ pageSize + ')', jumpPageNo_ID3, jumpPageSize3);
				$("#perf_no_2_page").html(html);
								
				loadMoreAddTitle();
			}	
		},
		error : function(data) 
		{
			if (data.status == 1001) 
			{
				layer.alert("当前操作无权限");
			}
		}
	});
	
}


/**
 * 请求历史数据
 * @param sortType
 * @param userId
 * @param historyMonthNum
 * @returns
 */
function requestHistory(sortType, userId, historyMonthNum)
{
	var companyId = $("#companyId").val();
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/historyDataDetails.do",
		data : 
		{
			'sortType' : sortType,
			'userId' : userId,
			'companyId' : companyId,
			'historyMonthNum' : historyMonthNum
		},
		dataType : 'json',
		success : function(data) 
		{
			if (data.code == 0) 
			{
				console.log("data:",data);
				var list = data.data;
				var ht = '';
				
				// 业绩--过往数据
				if(sortType == 1)
				{
					var emptyFlag = true;
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];
						if(null == mod || undefined == mod)
							continue;
						var tRate = mod.treamRate;
						tRate = (null == tRate || '' == tRate ? '0.00%' : (tRate + '%'));
						var rate = mod.scheduleRate;
						rate = (null == rate || '' == rate ? '0.00%' : (rate + '%'));
						ht += '<tr>';
							ht += '<td>'+mod.yearMonthTimeStr+'</td>';
							ht += '<td>'+subAmount(mod.monthGoalAmount)+'</td>';
							ht += '<td>'+subAmount(mod.performanceAmount)+'</td>';
							ht += '<td>'+rate+'</td>';
							ht += '<td>'+tRate+'</td>';
							ht += '<td>'+mod.score+'</td>';
							ht += '<td>'+mod.sort+'</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="7">暂无数据</td></tr>';
					}
					$("#perf_histroy_tb").html(ht);
				} 
				// 品牌 
				else if(sortType == 7)
				{
					var emptyFlag = true;
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];
						if(null == mod || undefined == mod)
							continue;
						ht += '<tr>';
							ht += '<td>'+mod.yearMonthTimeStr+'</td>';
							ht += '<td>'+mod.specBrandNum1+'</td>';
							ht += '<td>'+mod.brandNum1+'</td>';
							ht += '<td>'+mod.gooDecimal+'</td>';
							ht += '<td>'+mod.averageNum+'</td>';
							ht += '<td>'+mod.score+'</td>';
							ht += '<td>'+mod.sort+'</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="7">暂无数据</td></tr>';
					}
					$("#brands_histroy_tb").html(ht);
				}
				// 客户
				else if(sortType == 3)
				{
					var emptyFlag = true;
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];
						if(null == mod || undefined == mod)
							continue;
						var averageNum = mod.averageNum;
						averageNum = null == averageNum || 'null' == averageNum ? 0 : averageNum;
						var customerCopNum = mod.customerCopNum;
						customerCopNum = null == customerCopNum || 'null' == customerCopNum ? 0 : customerCopNum;
						ht += '<tr>';
							ht += '<td>'+mod.yearMonthTimeStr+'</td>';
							ht += '<td>'+customerCopNum+'</td>';
							ht += '<td>'+averageNum+'</td>';
							ht += '<td>'+mod.score+'</td>';
							ht += '<td>'+mod.sort+'</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="5">暂无数据</td></tr>';
					}
					$("#customer_history_tb").html(ht);
				}
				// 通话时长
				else if(sortType == 4)
				{
					var emptyFlag = true;
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];
						if(null == mod || undefined == mod)
							continue;
						ht += '<tr>';
							ht += '<td>'+mod.yearMonthTimeStr+'</td>';
							ht += '<td>'+mod.callTimeStr+'</td>';
							ht += '<td>'+mod.callTreamAvgCallTimeStr+'</td>';
							ht += '<td>'+mod.averageNum+'</td>';
							ht += '<td>'+mod.score+'</td>';
							ht += '<td>'+mod.sort+'</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="6">暂无数据</td></tr>';
					}
					$("#call_history_tb").html(ht);
				}
				// 转化率
				else if(sortType == 5)
				{
					var emptyFlag = true;
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];
						if(null == mod || undefined == mod)
							continue;
								
							
							ht += '<tr>';
							ht += '<td>'+mod.yearMonthTimeStr+'</td>';
							ht += '<td>'+mod.newDealToPriceNum+'</td>';
							ht += '<td>'+mod.monthToPriceNum+'</td>';
							ht += '<td>'+mod.rate+'</td>';
							ht += '<td>'+mod.treamRate+'</td>';
							ht += '<td>'+mod.score+'</td>';
							ht += '<td>'+mod.sort+'</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="7">暂无数据</td></tr>';
					}
					$("#rate_histroy_tb").html(ht);
				}
				else if (sortType == 8){
					var emptyFlag = true;
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];
						if(null == mod || undefined == mod)
							continue;


						ht += '<tr>';
						ht += '<td>'+mod.yearMonthTimeStr+'</td>';
						ht += '<td>'+mod.sortValue+'</td>';
						ht += '<td>'+mod.sort+'</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
					if(emptyFlag)
					{
						ht += '<tr><td colspan="3">暂无数据</td></tr>';
					}
					$("#bd_history_tb").html(ht);
				}
			}
		},
		error : function(data) 
		{
			if (data.status == 1001) 
			{
				layer.alert("当前操作无权限");
			}
		}
	});
}

/**
 * 当月明细
 * @param sortType
 * @param userId
 * @param termType
 * @returns
 */
function requestDetail(sortType, userId, termType)
{
	// 默认1
	var detailType = 1;
	var companyId = $("#companyId").val();
	// 本月业绩明细
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/sceneDataDetails.do",
		data : 
		{
			'sortType' : sortType,
			'userId' : userId,
			'companyId' : companyId,
			'termType' : termType,
			'type' : detailType
		},
		dataType : 'json',
		success : function(data) 
		{
			if (data.code == 0)
			{
				var list = data.data;
				
				var ht = '';
				var emptyFlag = false;
				
				// 空
				if(null == list || list.length == 0)
				{
					emptyFlag = true;
				}
				
				// 业绩 本月业绩明细 
				if(sortType == 1)
				{
					if(!emptyFlag)
					{						
						for(var i = 0; i < list.length; i++)
						{
							var mod = list[i];
							
							if(null == mod)
								continue;
							ht += '<tr>';
							ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewsaleorder' + mod.saleorderId + '","link":"./order/saleorder/view.do?saleorderId=' +  mod.saleorderId + '","title":"订单信息"}\'>' + mod.saleorderNo + '</a></td>';
							//ht += '<td>'+mod.traderName+'</td>';
							ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewcustomer' + mod.traderId + '","link":"./trader/customer/baseinfo.do?traderId=' +  mod.traderId + '","title":"客户信息"}\'>' + mod.traderName + '</a></td>';
							ht += '<td>'+mod.orderTimeStr+'</td>';
							ht += '<td>'+mod.orderAmount+'</td>';
							ht += '<td>'+mod.arrivalAmount+'</td>';
							ht += '</tr>';
							emptyFlag = false;
						}
					}
					
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="5">暂无数据</td></tr>';
					}
					
					$("#perf_details_tb").html(ht);
				}
				// 品牌
				else if(sortType == 2)
				{
					if(!emptyFlag)
					{
						var orgConfigUserNum = list[0].orgConfigUserNum;
						orgConfigUserNum = (null == orgConfigUserNum || 'null' == orgConfigUserNum ? 0 : orgConfigUserNum);
						$("#brans_org_config_total_num").text(orgConfigUserNum);
						for(var i = 0; i < list.length; i++)
						{
							var mod = list[i];
							
							if(null == mod)
								continue;
							var firstName = (null == mod.saleName || 'null' == mod.saleName ? "" : mod.saleName);
							var brandFirstAmount = (null == mod.brandFirstAmount || 'null' == mod.brandFirstAmount ? "" : mod.brandFirstAmount);
							var brandAmount = mod.brandAmount;
							
							if('' == brandAmount || undefined == brandAmount || null == brandAmount || 'null' == brandAmount)
							{
								brandAmount = 0;
							}
							if(brandAmount > 0)
							{
								ht += '<tr style="background-color:#ceeace;" >';
							}
							else
							{
								ht += '<tr>';
							}
							ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"brand' + mod.brandId + '","link":"./goods/brand/viewBrandDetail.do?brandId=' +  mod.brandId + '","title":"品牌详情"}\'>' + mod.brandName + '</a></td>';
							ht += '<td>'+subAmount(brandAmount)+'</td>';
							ht += '<td>'+mod.brandNum+'</td>';
							ht += '<td>'+firstName+'</td>';
							ht += '<td>'+subAmount(brandFirstAmount)+'</td>';
							ht += '</tr>';
							emptyFlag = false;
						}
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="5">暂无数据</td></tr>';
					}
					
					$("#brands_details_tb").append(ht);
				}
				// 客户
				else if(sortType == 3)
				{
					if(!emptyFlag)
					{
						for(var i = list.length - 1; i >= 0; i--)
						{
							var mod = list[i];
							
							if(null == mod)
								continue;
							var lastOrderTimeStr = mod.lastOrderTimeStr;
							if('null' == lastOrderTimeStr || null == lastOrderTimeStr)
							{
								lastOrderTimeStr = '';
							}
							var lastCallTimeStr = mod.lastCallTimeStr;
							if('null' == lastCallTimeStr || null == lastCallTimeStr)
							{
								lastCallTimeStr = '';
							}
							var validOrderNum = mod.validOrderNum;
							if('null' == validOrderNum || null == validOrderNum)
							{
								validOrderNum = '';
							}
							var isOld = mod.isOldCutomer;
							if('null' == isOld || null == isOld)
							{
								isOld = '';
							}
							var subDays = mod.subDays;
							if('null' == subDays || null == subDays)
							{
								subDays = '';
							}
							ht += '<tr>';
							ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewcustomer' + mod.traderId + '","link":"./trader/customer/baseinfo.do?traderId=' +  mod.traderId + '","title":"客户信息"}\'>' + mod.traderName + '</a></td>';
							ht += '<td>'+validOrderNum+'</td>';
							ht += '<td>'+lastOrderTimeStr+'</td>';
							ht += '<td>'+lastCallTimeStr+'</td>';
							ht += '<td>'+subDays+'</td>';
							ht += '<td>'+subAmount(mod.validDealAmount)+'</td>';
							ht += '<td>'+isOld+'</td>';
							ht += '</tr>';
							emptyFlag = false;
						}
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="7">暂无数据</td></tr>';
					}
					if(termType == 1)
					{
						$("#detail_customer_tb_90").html(ht);
					}
					else if(termType == 2)
					{
						
						$("#detail_customer_tb_150").html(ht);
					}
				}
				// 通话时长
				else if(sortType == 4)
				{
					if(!emptyFlag)
					{
						for(var i = 0; i < list.length; i++)
						{
							var mod = list[i];
							
							if(null == mod)
								continue;
							var statusType = mod.statusType;
							ht += '<tr>';
							if(1 == statusType)
							{
								ht += '<td>未成交客户</td>';							
							}
							else if(2 == statusType)
							{
								ht += '<td>初次成交客户</td>';							
							}
							else if(3 == statusType)
							{
								ht += '<td>老客户</td>';							
							}
							ht += '<td>'+mod.callTraderNum+'</td>';
							ht += '<td>'+mod.callInNum+'</td>';
							ht += '<td>'+mod.callInTimeStr+'</td>';
							ht += '<td>'+mod.averageCallInTimeStr+'</td>';
							ht += '<td>'+mod.callOutNum+'</td>';
							ht += '<td>'+mod.callOutTimeStr+'</td>';
							ht += '<td>'+mod.averageCallOutTimeStr+'</td>';
							ht += '</tr>';
							emptyFlag = false;
						}
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="8">暂无数据</td></tr>';
					}
					
					$("#call_details_tb").html(ht);
				}
				// 商机明细
				else if(sortType == 5)
				{
					var emptyFlag = true;
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];

						if(null == mod)
							continue;
					
						ht += '<tr>';
						ht += '<td>'+ (i+1) + '</td>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"view' + mod.bussinessId + '","link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=' +  mod.bussinessId + '&traderId=' + mod.traderId + '","title":"销售商机详情"}\'>' + mod.bussinessNo + '</a></td>';
//						ht += '<td>'+mod.bussinessNo+'</td>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewsaleorder' + mod.saleorderId + '","link":"./order/saleorder/view.do?saleorderId=' +  mod.saleorderId + '","title":"订单信息"}\'>' + mod.saleorderNo + '</a></td>';
//						ht += '<td>'+mod.saleorderNo+'</td>';
						ht += '<td>'+mod.showTimeStr+'</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
					if(emptyFlag)
					{						
						ht += '<tr><td colspan="4">暂无数据</td></tr>';
					}
					
					$("#detail_rate_tb").html(ht);
				}

				else if (sortType == 8){
					var emptyFlag = true;
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];
						if(null == mod)
							continue;

						ht += '<tr>';
						ht += '<td>'+ (i+1) + '</td>';
						ht += '<td>' + mod.traderName + '</td>';
						// ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"view' + mod.saleorderNo + '","link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=' +  mod.bussinessId + '&traderId=' + mod.traderId + '","title":"销售商机详情"}\'>' + mod.bussinessNo + '</a></td>';
						ht += '<td>'+mod.saleorderNo+'</td>';
						ht += '<td>'+mod.validDealAmount+'</td>';
						ht += '<td>'+mod.orderTimeStr+'</td>';
                        ht += '<td>'+mod.yearMonthTimeStr+'</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
					if(emptyFlag)
					{
						ht += '<tr><td colspan="6">暂无数据</td></tr>';
					}

					$("#detail_bd_tb").html(ht);
				}
			} 
			
			loadMoreAddTitle();
		},
		error : function(data) 
		{
			if (data.status == 1001) 
			{
				layer.alert("当前操作无权限");
			}
		}
	});
}

/**
 * 本月概况--请求数据
 */
function baseRequest(sortType, userId)
{
	var companyId = $("#companyId").val();
	// 默认1
	var detailType = 1;
	
	if(sortType == 1)
	{
		// 业绩 3 业绩额 
		detailType = 3;
	}
	// 本月概况
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/monthlySceneDetails.do",
		data : 
		{
			'sortType' : sortType,
			'userId' : userId,
			'companyId' : companyId,
			'type' : detailType
		},
		dataType : 'json',
		success : function(data) 
		{
			if (data.code == 0)
			{
				console.log("2:sortType:" + sortType);
				var list = data.data;
				// 业绩
				if(sortType == 1)
				{			
					showPerfData(list);
				}
				// 品牌
				else if(sortType == 7)
				{
					showBrandsData(list);
				}
				// 客户
				else if(sortType == 3)
				{
					showCustomerData(list);
				}
				// 通话时长
				else if(sortType == 4)
				{
					showCallData(list);
				}
				// Bd
				else if(sortType == 8)
				{
					showBdData(list);
				}
			} 

		},
		error : function(data) 
		{
			if (data.status == 1001) 
			{
				layer.alert("当前操作无权限");
			}
		}
	});
}

/**
 * 转化率--本月概况
 * @param list
 * @returns
 */
function showRateData(list)
{
	for(var i = 0; i < list.length; i++)
	{
		var model = list[i];
		var sceneType = model.sceneType;
		
		var score = model.score;
		var sort = model.sort;
		score = null == score || "null" == score ? 0 : score;
		sort = null == sort || "null" == sort ? 0 : sort;
		if(sceneType >= 1 && sceneType <= 5)
		{
			$("#newDealToPriceNum_"+sceneType).text(model.newDealToPriceNum);
			$("#monthToPriceNum_"+sceneType).text(model.monthToPriceNum);
			var rate = model.rate;
			rate = (null == rate || '' == rate || undefined == rate ? '0.00' : rate); 
			$("#rate_"+sceneType).text(rate);
			$("#score_"+sceneType).text(score);
			$("#sort_"+sceneType).text(sort);
			
		}
//		if(sceneType == 1)
//		{
//			$("#now_sales_name_td").text(model.saleName);
//		}
		if(sceneType == 3)
		{
			// 榜首人姓名
			$("#first_one_span_name").text(model.saleName);
		}
	}
}


function showBdData(list) {
	for (var i = 0; i < list.length; i++){
		var model = list[i];
		var sceneType = model.sceneType;
		var score = model.score;
		var sort = model.sort;

		score = null == score || "null" == score ? 0 : score;
		sort = null == sort || "null" == sort ? 0 : sort;
		if(sceneType >= 1 && sceneType <= 5)
		{
			var bdCount = model.sortValue;

			console.log("sortValue:" + bdCount == null ? 0 : bdCount);
			bdCount = (null == bdCount || '' == bdCount || undefined == bdCount ? 0 : bdCount);
			$("#sortValue_"+sceneType).text(bdCount);
			$("#score_"+sceneType).text(score);
			$("#sort_"+sceneType).text(sort);

		}
		if(sceneType == 1)
		{
			$("#now_sales_name_td").text(model.saleName);
		}
		if(sceneType == 3)
		{
			// 榜首人姓名
			$("#first_one_span_name").text(model.saleName);
		}
	}
}


/**
 * 通话时长--本月概况
 * @param list
 * @returns
 */
function showCallData(list)
{
	for(var i = 0; i < list.length; i++)
	{
		var model = list[i];
		var sceneType = model.sceneType;
		var score = model.score;
		var sort = model.sort;
		score = null == score || "null" == score ? 0 : score;
		sort = null == sort || "null" == sort ? 0 : sort;
		if(sceneType >= 1 && sceneType <= 5)
		{
			$("#callTime_"+sceneType).text(model.callTimeStr);
			$("#score_"+sceneType).text(score);
			$("#sort_"+sceneType).text(sort);
		}
//		if(sceneType == 1)
//		{
//			$("#now_sales_name_td").text(model.saleName);
//		}
		if(sceneType == 3)
		{
			// 榜首人姓名
			$("#first_one_span_name").text(model.saleName);
		}
	}
}

/**
 * 客户 本月概况
 * @param list
 * @returns
 */
function showCustomerData(list)
{
	for(var i = 0; i < list.length; i++)
	{
		var model = list[i];
		var sceneType = model.sceneType;
		var score = model.score;
		var sort = model.sort;
		score = null == score || "null" == score ? 0 : score;
		sort = null == sort || "null" == sort ? 0 : sort;
		if(sceneType >= 1 && sceneType <= 5)
		{
			$("#customerCopNum_"+sceneType).text(model.customerCopNum);
			$("#score_"+sceneType).text(score);
			$("#sort_"+sceneType).text(sort);
			
		}
//		if(sceneType == 1)
//		{
//			$("#now_sales_name_td").text(model.saleName);
//		}
		if(sceneType == 2)
		{
			$("#customerCopNum_"+sceneType).text(model.customerTreamCopNumAvg);
		}
		else if(sceneType == 3)
		{
			// 榜首人姓名
			$("#first_one_span_name").text(model.saleName);
		}
	}
}
/**
 * 品牌页面
 * @param list
 * @returns
 */
function showBrandsData(list)
{
	for(var i = 0; i < list.length; i++)
	{
		var model = list[i];
		var sceneType = model.sceneType;
		var score = model.score;
		var sort = model.sort;
		score = null == score || "null" == score ? 0 : score;
		sort = null == sort || "null" == sort ? 0 : sort;
		var monthGoalAmount = model.monthGoalAmount;
		monthGoalAmount = null == monthGoalAmount || "null" == monthGoalAmount ? 0 : monthGoalAmount;
		
		if(sceneType >= 1 && sceneType <= 5)
		{
			$("#performanceAmount_td_"+sceneType).text(subAmount(model.performanceAmount));
			
			$("#monthGoalAmount_td_"+sceneType).text(subAmount(monthGoalAmount));
			var rate = model.scheduleRate;
			rate = (null == rate || '' == rate || undefined == rate ? '0.00' : rate); 
			$("#scheduleRate_td_"+sceneType).text(rate);
			$("#score_td_"+sceneType).text(score);
			$("#sort_td_"+sceneType).text(sort);

			if(sceneType == 2)
			{
				$("#performanceAmount_td_"+sceneType).text(subAmount(model.treamPerfAmount));
				var tRate = model.treamRate;
				tRate = (null == tRate || '' == tRate || undefined == tRate ? '0.00' : tRate); 
				$("#scheduleRate_td_"+sceneType).text(tRate);
			}
			else if(sceneType == 3)
			{
				// 榜首人姓名
				$("#first_one_span_name").text(model.saleName);
			}
		}
		
	}
}

function subAmount(amount)
{
	var am = 0.00;
	
	if('' != amount && 'null' != amount && 'undefined' != amount)
	{
		am = amount / 10000;
		am = am.toFixed(2);
	}
	
	return am;
}

/**
 * 业绩页面 本月概况的数据展示
 * @param list
 * @returns
 */
function showPerfData(list)
{
	for(var i = 0; i < list.length; i++)
	{
		var model = list[i];
		var sceneType = model.sceneType;
		var score = model.score;
		var sort = model.sort;
		score = null == score || "null" == score ? 0 : score;
		sort = null == sort || "null" == sort ? 0 : sort;
		var monthGoalAmount = model.monthGoalAmount;
		monthGoalAmount = null == monthGoalAmount || "null" == monthGoalAmount ? 0 : monthGoalAmount;
		
		if(sceneType >= 1 && sceneType <= 5)
		{
			$("#performanceAmount_td_"+sceneType).text(subAmount(model.performanceAmount));
			
			$("#monthGoalAmount_td_"+sceneType).text(subAmount(monthGoalAmount));
			var rate = model.scheduleRate;
			rate = (null == rate || '' == rate || undefined == rate ? '0.00' : rate); 
			$("#scheduleRate_td_"+sceneType).text(rate);
			$("#score_td_"+sceneType).text(score);
			$("#sort_td_"+sceneType).text(sort);

			if(sceneType == 2)
			{
				$("#performanceAmount_td_"+sceneType).text(subAmount(model.treamPerfAmount));
				var tRate = model.treamRate;
				tRate = (null == tRate || '' == tRate || undefined == tRate ? '0.00' : tRate); 
				$("#scheduleRate_td_"+sceneType).text(tRate);
			}
			else if(sceneType == 3)
			{
				// 榜首人姓名
				$("#first_one_span_name").text(model.saleName);
			}
		}
		
	}
	
}


function setPage(page, methodName, pageNoId, pageSizeName)
{
	// 总条数
	var totalRecord = page.totalRecord;
	// 总页数
	var totalPage = page.totalPage;
	// url
	var searchUrl = page.searchUrl;
	//  每页数量
	var pageSize = page.pageSize;
	// 页数
	var pageNo = page.pageNo;
	var html = '';
			html += '<div class="pages_container">';
				html += '<div class="pages">';
					html += '<div class="numbers">';
						html += '共<span>' + totalRecord + '</span>条记录';
						html += '<span class="ml8">共</span><span>' + totalPage + '</span>页';
						html += '<span class="ml8">跳转</span><input style="width:30px;" id="'+pageNoId+'" class="jump-to" onkeyup="this.value=this.value.replace(/\D/g,\''+'\')" onafterpaste="this.value=this.value.replace(/\D/g,\''+'\')" value="' + pageNo + '" onkeydown="javascript:if(event.keyCode==13){' + methodName + ';return false;}" type="text">页';
						html += '<span class="bt-middle bt-bg-style bg-light-blue ml8" onclick="' + methodName + '">跳转</span>';
					html += '</div>';
					html += '<ul class="pages_num">';
						if(totalPage >= 1)
						{
							// 上一页
							var prePageNo = pageNo - 1;
							// 下一页
							var nextPageNo = pageNo + 1;
							if(totalPage == 1)
							{
								html += '<span id="'+pageNoId+'_1"  class="li_wid" >首页</span>';
								html += '<span id="'+pageNoId+'_'+totalPage+'"  class="li_wid" >上一页</span>';
								html += '<span id="'+pageNoId+'_'+totalPage+'"  class="li_wid lastpage canotclick" >下一页</span>';
								html += '<span id="'+pageNoId+'_'+totalPage+'"  class="li_wid lastpage canotclick" >尾页</span>';
							}
							else if(totalPage > 1 && pageNo == 1)
							{
								html += '<span id="'+pageNoId+'_1"  class="li_wid" >首页</span>';
								html += '<span id="'+pageNoId+'_1"  class="li_wid" >上一页</span>';
								html += '<span id="'+pageNoId+'_'+nextPageNo+'" onclick="setPageNo('+nextPageNo+', '+pageNoId+');' + methodName + '" class="li_wid lastpage canclick">下一页</span>';
								html += '<span id="'+pageNoId+'_'+totalPage+'" onclick="setPageNo('+totalPage+', '+pageNoId+');' + methodName + '" class="li_wid lastpage canclick">尾页</span>';
							}
							else if(totalPage > 1 && pageNo == totalPage)
							{
								html += '<span id="'+pageNoId+'_1" onclick="setPageNo(1, '+pageNoId+');' + methodName + '" class="li_wid firstpage canclick" >首页</span>';
								html += '<span id="'+pageNoId+'_'+prePageNo+'" onclick="setPageNo('+prePageNo+', '+pageNoId+');' + methodName + '" class="li_wid firstpage canclick" >上一页</span>';
								html += '<span id="'+pageNoId+'_'+totalPage+'"  class="li_wid lastpage canotclick" >下一页</span>';
								html += '<span id="'+pageNoId+'_'+totalPage+'"  class="li_wid lastpage canotclick" >尾页</span>';
							}
							else
							{
								html += '<span id="'+pageNoId+'_1" onclick="setPageNo(1, '+pageNoId+');' + methodName + '" class="li_wid firstpage canclick">首页</span>';
								html += '<span id="'+pageNoId+'_'+prePageNo+'" onclick="setPageNo('+prePageNo+', '+pageNoId+');' + methodName + '" class="li_wid firstpage canclick" >上一页</span>';
								html += '<span id="'+pageNoId+'_'+nextPageNo+'" onclick="setPageNo('+nextPageNo+', '+pageNoId+');' + methodName + '"  class="li_wid lastpage canclick" >下一页</span>';
								html += '<span id="'+pageNoId+'_'+totalPage+'" onclick="setPageNo('+totalPage+', '+pageNoId+');' + methodName + '"  class="li_wid lastpage canclick">尾页</span>';
							}

						}
						html += '<div class="clear"></div>';
					html += '</ul>';
					html += '<select style=""  name="'+pageSizeName+'" onchange="' + methodName + '" >';
					var pageSizeArr = [10, 20, 50, 100];
					for(var i = 0; i < 4; i++)
					{
						var showPageSize = pageSizeArr[i];
						if(pageSize == showPageSize)
						{
							html += '<option selected="selected" value="' + showPageSize + '" >' + showPageSize + '/页</option>';
						}
						else
						{
							html += '<option  value="' + showPageSize + '" >' + showPageSize + '/页</option>';
						}
					}
					html += "</select>";						
					html += '<div class="clear"></div>';
				html += '</div>';
				html += '<div class="clear"></div>';
			html += '</div>';
		return html;								
}	


function setPageNo(pageNo, pageNoId)
{
	$("#"+pageNoId).val(pageNo);
}
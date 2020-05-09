$(function() 
{
	
	var orgId = $("#pageOrgId").val();
	var groupId = $("#groupId").val();
	$.ajax({
		type : "POST",
		url : page_url + "/director/performance/getMembersHistoryData.do",
		data :{'orgId3' : orgId, 'groupId' : groupId},
		dataType : 'json',
		success : function(data) 
		{			
			var ht = '';
			var emptyFlag = true;
			for(var i=0;i<data.length;i++)
			{
				var vo = data[i];
				var sort = vo.sort;//业绩排名
				var deptAvgBrandNum = vo.deptAvgBrandNum;//已合作品牌数
				var brandSort = vo.brandSort;//品牌排名
				var deptAvgCustomerNum = vo.deptAvgCustomerNum;//已合作客户数
				var customerSort = vo.customerSort;//合作客户数排名
				var deptAvgCommLength = vo.deptAvgCommLength;//通话时长
				var commSort = vo.commSort;//通话时长排名
				var deptAvgBussinessChanceRate = vo.deptAvgBussinessChanceRate;// 平均转化率
				var bussinessChanceRateSort = vo.bussinessChanceRateSort;//转化率排名
				var totalSort = vo.totalSort;//综合排名
				var deptAmountRate = vo.deptAmountRate;
				if(null == deptAmountRate || 'null' == deptAmountRate)
				{
					deptAmountRate = '0.00';
				}
				if(null == vo.sort || 'null' == vo.sort)
				{
					sort = '-';
				}
				if(null == deptAvgBrandNum || 'null' == deptAvgBrandNum)
				{
					deptAvgBrandNum = '-';
				}
				if(null == vo.brandSort || 'null' == vo.brandSort)
				{
					brandSort = '-';
				}
				if(null == deptAvgCustomerNum || 'null' == deptAvgCustomerNum)
				{
					deptAvgCustomerNum = '-';
				}
				if(null == vo.customerSort || 'null' == vo.customerSort)
				{
					customerSort = '-';
				}
				if(null == deptAvgCommLength || 'null' == deptAvgCommLength)
				{
					deptAvgCommLength = '-';
				}
				if(null == vo.commSort || 'null' == vo.commSort)
				{
					commSort = '-';
				}
				if(null == deptAvgBussinessChanceRate || 'null' == deptAvgBussinessChanceRate)
				{
					deptAvgBussinessChanceRate = '0.00';
				}
				if(null == vo.bussinessChanceRateSort || 'null' == vo.bussinessChanceRateSort)
				{
					bussinessChanceRateSort = '-';
				}
				if(null == vo.totalSort || 'null' == vo.totalSort)
				{
					totalSort = '-';
				}
				ht += '<tr>';
					ht += '<td class="font-blue" >' + vo.timeStr + '</td>';
					ht += '<td>' + subAmount(vo.goalMonth) + '</td>';
					ht += '<td>' + subAmount(vo.amount) + '</td>';
					ht += '<td>' + deptAmountRate + '</td>';
					ht += '<td>' + sort + '</td>';
					ht += '<td>' + deptAvgBrandNum + '</td>';
					ht += '<td>' + brandSort + '</td>';
					ht += '<td>' + deptAvgCustomerNum + '</td>';
					ht += '<td>' + customerSort + '</td>';
					ht += '<td>' + deptAvgCommLength + '</td>';
					ht += '<td>' + commSort + '</td>';	
					ht += '<td>' + deptAvgBussinessChanceRate  + '</td>';		
					ht += '<td>' + bussinessChanceRateSort + '</td>';
					ht += '<td>' + totalSort + '</td>';
				ht += '</tr>';
				var emptyFlag = false;
			}
			if(emptyFlag)
			{						
				ht += '<tr><td colspan="14">暂无数据</td></tr>';
			}
			$("#historyDatas").html(ht);
		
		},
		error : function(data) 
		{
//			alert("无数据111111111");	
		}
	});
});


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
$(function() 
{
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/queryAllSalesData.do",
		data : 
		{
			'companyId' : 1
		},
		dataType : 'json',
		success : function(data) 
		{
			if(0 == data.code)
			{
				var list = data.data;
				
				var ht = '';
				
				var emptyFlag = true;
				
				var j = 0;
				
				if(null != list && 'null' != list)
				{
					for(var i = 0; i < list.length; i++)
					{
						var mod = list[i];
						if(null == mod || undefined == mod)
							continue;
						j++;
						ht += '<tr>';
						var perScore = mod.score;
						if(null == perScore || 'null' == perScore)
						{
							perScore = 0;
						}
						var perSort = mod.sort;
						if(null == perSort || 'null' == perSort)
						{
							perSort = '-';
						}
						var brandScore = mod.brandScore;
						if(null == brandScore || 'null' == brandScore)
						{
							brandScore = 0;
						}
						var brandSort = mod.brandSort;
						if(null == brandSort || 'null' == brandSort)
						{
							brandSort = '-';
						}
						var customerScore = mod.customerScore;
						if(null == customerScore || 'null' == customerScore)
						{
							customerScore = 0;
						}
						var customerSort = mod.customerSort;
						if(null == customerSort || 'null' == customerSort)
						{
							customerSort = '-';
						}
						var commScore = mod.commScore;
						if(null == commScore || 'null' == commScore)
						{
							commScore = 0;
						}
						var commSort = mod.commSort;
						if(null == commSort || 'null' == commSort)
						{
							commSort = '-';
						}
						var bussinessChanceRateScore = mod.bussinessChanceRateScore;
						if(null == bussinessChanceRateScore || 'null' == bussinessChanceRateScore)
						{
							bussinessChanceRateScore = 0;
						}
						var bussinessChanceRateSort = mod.bussinessChanceRateSort;
						if(null == bussinessChanceRateSort || 'null' == bussinessChanceRateSort)
						{
							bussinessChanceRateSort = '-';
						}
						var totalScore = mod.totalScore;
						if(null == totalScore || 'null' == totalScore)
						{
							totalScore = 0;
						}
						var totalSort = mod.totalSort;
						if(null == totalSort || 'null' == totalSort)
						{
							totalSort = '-';
						}
						var yesterDayTotalSort = mod.yesterDayTotalSort;
						if(null == yesterDayTotalSort || 'null' == yesterDayTotalSort)
						{
							yesterDayTotalSort = '-';
						}
						var preMonthLastDayTotalSort = mod.preMonthLastDayTotalSort;
						if(null == preMonthLastDayTotalSort || 'null' == preMonthLastDayTotalSort)
						{
							preMonthLastDayTotalSort = '-';
						}
						ht += '<td>' + j + '</td>';
						ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"sale_five_per_' + mod.userId + '","link":"./sales/fiveSales/detailsPage.do?sortType=1&companyId=1&userId=' +  mod.userId + '","title":"销售五行"}\'>' + mod.username + '</a></td>';
						ht += '<td>' + perScore + '</td>';
						ht += '<td>' + perSort + '</td>';
						ht += '<td>' + brandScore + '</td>';
						ht += '<td>' + brandSort + '</td>';
						ht += '<td>' + customerScore + '</td>';
						ht += '<td>' + customerSort + '</td>';
						ht += '<td>' + commScore + '</td>';
						ht += '<td>' + commSort + '</td>';
						ht += '<td>' + bussinessChanceRateScore + '</td>';
						ht += '<td>' + bussinessChanceRateSort + '</td>';
						ht += '<td>' + totalScore + '</td>';
						ht += '<td>' + totalSort + '</td>';
						ht += '<td>' + yesterDayTotalSort + '</td>';
						ht += '<td>' + preMonthLastDayTotalSort + '</td>';
						ht += '</tr>';
						emptyFlag = false;
					}
				}
				
				if(emptyFlag)
				{						
					ht += '<tr><td colspan="16">暂无数据</td></tr>';
				}
				$("#five_total_page_tb").html(ht);
				
				loadMoreAddTitle();
			}
		},
		error : function(data) 
		{
			if (data.status == 1001) 
			{
				layer.alert("查询部门本月概况，暂无操作权限，请设置.");
			}
		}
	});
	
});
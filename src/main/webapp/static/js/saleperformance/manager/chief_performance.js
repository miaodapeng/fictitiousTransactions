$(function() 
{
	
	var orgId = $("#pageOrgId").val();
	var groupId = $("#groupId").val();
	$.ajax({
		type : "POST",
		url : page_url + "/director/performance/getAreaDetailsList.do",
		data :{'orgId' : orgId,'groupId' : groupId},
		dataType : 'json',
		success : function(data) 
		{			
			var ht = '';
			var emptyFlag = true;
			for(var i=0;i<data.length;i++)
			{
				var mod = data[i];
				var yearMonth = mod.monthYear[0];
				ht += '<tr>';
					ht += '<td class="font-blue" >' + yearMonth + '</td>';
					ht += '<td>' + mod.goalMonth + '</td>';
					ht += '<td>' + mod.orderDetails + '</td>';
					ht += '<td>' + mod.completionDegree + '</td>';
					ht += '<td>' + mod.brandCount + '</td>';
					ht += '<td>' + mod.traderCount + '</td>';
					ht += '<td>' + mod.commCount + '</td>';
					ht += '<td>' + mod.rate + '</td>';
				ht += '</tr>';
				var emptyFlag = false;
			}
			if(emptyFlag)
			{						
				ht += '<tr><td colspan="8">暂无数据</td></tr>';
			}
			$("#historyDatas").html(ht);
		
		},
		error : function(data) 
		{
//			alert("无数据111111111");	
		}
	});
});
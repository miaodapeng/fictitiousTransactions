$(function() 
{
	var groupList = $(".groupIdClass");
	
	if(groupList && groupList.length > 0){
		var array = [];
		for (var k = 0; k < groupList.length; k++) {
			$.ajax({
				type : "POST",
				url : page_url + "/director/performance/getAreaDetailsList.do",
				dataType : 'json',
				data : {groupId : $(groupList)[k].value},
				success : function(data) 
				{			
					var ht = '';
					if(data)
					{	
						if(data.groupList && data.groupList.length > 0){
							ht += '<div class="title-container">';
							ht += '<div class="table-title nobor">';
							ht += data.groupeName + '历史数据';
							ht += '</div>';
							ht += '</div>';
							ht += '<table class="table">';
							ht += '<thead>';
							ht += '<tr>';
			                ht += '<th class="">时间</th>';
			                ht += '<th>本月目标（万）</th>';
			                ht += '<th>本月业绩额（万）</th>';
							ht += '<th>本月完成度</th>';
							ht += '<th class="wid18">核心商品成交金额数（万）</th>';
							ht += '<th class="wid18">已合作客户数</th>';
							ht += '<th>本月通话时长</th>';
							ht += '<th>本月BD新客数</th>';
			                ht += '</tr>';
			                ht += '</thead>';
							for(var i=0;i<data.groupList.length;i++)
							{
								var mod = data.groupList[i];
								ht += '<tr>';
								ht += '<td class="font-blue" >' + mod.date + '</td>';
								ht += '<td>' + mod.goalMonth + '</td>';
								ht += '<td>' + mod.orderDetails + '</td>';
								ht += '<td>' + mod.completionDegree + '</td>';
								ht += '<td>' + mod.goodsDecimal + '</td>';
								ht += '<td>' + mod.traderBigDecimal + '</td>';
								ht += '<td>' + mod.commCount + '</td>';
								ht += '<td>' + mod.bdCountAvg + '</td>';
								ht += '</tr>';
							}
						}else{
							ht += '<div class="title-container">';
							ht += '<div class="table-title nobor">';
							ht += data.groupeName + '历史数据';
							ht += '</div>';
							ht += '</div>';
							ht += '<table class="table">';
							ht += '<thead>';
							ht += '<tr>';
			                ht += '<th class="">时间</th>';
			                ht += '<th>本月目标（万）</th>';
			                ht += '<th>本月业绩额（万）</th>';
							ht += '<th>本月完成度</th>';
							ht += '<th class="wid18">核心商品成交金额数（万）</th>';
							ht += '<th class="wid18">已合作客户数</th>';
							ht += '<th>本月通话时长</th>';
							ht += '<th>本月BD新客数</th>';
			                ht += '</tr>';
			                ht += '</thead>';
			                ht += '<tbody id="historyDatas111">';
			                ht += '</tbody>';
			                ht += '<tr><td colspan="8">查询无结果</td></tr>';
			                ht += '</table>';
						}
						
					}
					else{
						ht += '<div class="title-container">';
						ht += '<div class="table-title nobor">';
						ht += '部门本月五行剑法概况历史数据';
						ht += '</div>';
						ht += '</div>';
						ht += '<table class="table">';
						ht += '<thead>';
						ht += '<tr>';
		                ht += '<th class="">时间</th>';
		                ht += '<th>本月目标（万）</th>';
		                ht += '<th>本月业绩额（万）</th>';
						ht += '<th>本月完成度</th>';
						ht += '<th class="wid18">核心商品成交金额数（万）</th>';
						ht += '<th class="wid18">已合作客户数</th>';
						ht += '<th>本月通话时长</th>';
						ht += '<th>本月BD新客数</th>';
		                ht += '</tr>';
		                ht += '</thead>';
		                ht += '<tbody id="historyDatas111">';
		                ht += '</tbody>';
		                ht += '<tr><td colspan="8">查询无结果</td></tr>';
		                ht += '</table>';
					}
					$("#historyDatas1").append(ht);
				}
			});
		}
	}else{
		var ht = '';
		
		ht += '<div class="title-container">';
		ht += '<div class="table-title nobor">';
		ht += '部门本月五行剑法概况历史数据';
		ht += '</div>';
		ht += '</div>';
		ht += '<table class="table">';
		ht += '<thead>';
		ht += '<tr>';
		ht += '<th class="">时间</th>';
		ht += '<th>本月目标（万）</th>';
		ht += '<th>本月业绩额（万）</th>';
		ht += '<th>本月完成度</th>';
		ht += '<th class="wid18">核心商品成交金额数（万）</th>';
		ht += '<th class="wid18">已合作客户数</th>';
		ht += '<th>本月通话时长</th>';
		ht += '<th>本月BD新客数</th>';
		ht += '</tr>';
		ht += '</thead>';
		ht += '<tbody id="historyDatas111">';
		ht += '</tbody>';
		ht += '<tr><td colspan="8">查询无结果</td></tr>';
		ht += '</table>';
		
		$("#historyDatas1").html(ht);
	}
	
	var deptList = $(".deptIdClass");
	
	if(deptList && deptList.length > 0){
		for (var k = 0; k < deptList.length; k++) {

			$.ajax({
				type : "POST",
				url : page_url + "/director/performance/getMembersHistoryData.do",
				dataType : 'json',
				data : {deptId : $(deptList)[k].value},
				success : function(data) 
				
				{			
					var ht = '';
					if(data)
					{	
						if(data.deptMapList && data.deptMapList.length > 0){
							ht += '<div class="title-container">';
							ht += '<div class="table-title nobor">';
							ht += data.deptName + '历史数据';
							ht += '</div>';
							ht += '</div>';
							ht += '<table class="table">';
							ht += '<thead>';
							ht += '<tr>';
							ht += '<th class="">时间</th>';
							ht += '<th>本月目标（万）</th>';
							ht += '<th>本月业绩额（万）</th>';
							ht += '<th>本月完成度</th>';
							ht += '<th class="wid18">核心商品成交金额数（万）</th>';
							ht += '<th class="wid18">已合作客户数</th>';
							ht += '<th>本月通话时长</th>';
							ht += '<th>本月BD新客数</th>';
							ht += '</tr>';
							ht += '</thead>';
							for(var i=0;i<data.deptMapList.length;i++)
							{
								var mod = data.deptMapList[i];
								ht += '<tr>';
								ht += '<td class="font-blue" >' + mod.date + '</td>';
								ht += '<td>' + mod.goal + '</td>';
								ht += '<td>' + mod.yjVal + '</td>';
								ht += '<td>' + mod.wcDegree + '</td>';
								ht += '<td>' + mod.goodsCj + '</td>';
								ht += '<td>' + mod.traderBigDecimal + '</td>';
								ht += '<td>' + mod.thVal + '</td>';
								ht += '<td>' + mod.bdCount + '</td>';
								ht += '</tr>';
							}
						}else{
							ht += '<div class="title-container">';
							ht += '<div class="table-title nobor">';
							ht += data.deptName + '历史数据';
							ht += '</div>';
							ht += '</div>';
							ht += '<table class="table">';
							ht += '<thead>';
							ht += '<tr>';
							ht += '<th class="">时间</th>';
							ht += '<th>本月目标（万）</th>';
							ht += '<th>本月业绩额（万）</th>';
							ht += '<th>本月完成度</th>';
							ht += '<th class="wid18">核心商品成交金额数（万）</th>';
							ht += '<th class="wid18">已合作客户数</th>';
							ht += '<th>本月通话时长</th>';
							ht += '<th>本月BD新客数</th>';
							ht += '</tr>';
							ht += '</thead>';
							ht += '<tbody id="historyDatas111">';
							ht += '</tbody>';
							ht += '<tr><td colspan="8">查询无结果</td></tr>';
							ht += '</table>';
						}
						
					}
					else{
						ht += '<div class="title-container">';
						ht += '<div class="table-title nobor">';
						ht += '部门本月五行剑法概况历史数据';
						ht += '</div>';
						ht += '</div>';
						ht += '<table class="table">';
						ht += '<thead>';
						ht += '<tr>';
						ht += '<th class="">时间</th>';
						ht += '<th>本月目标（万）</th>';
						ht += '<th>本月业绩额（万）</th>';
						ht += '<th>本月完成度</th>';
						ht += '<th class="wid18">核心商品成交金额数（万）</th>';
						ht += '<th class="wid18">已合作客户数</th>';
						ht += '<th>本月通话时长</th>';
						ht += '<th>本月BD新客数</th>';
						ht += '</tr>';
						ht += '</thead>';
						ht += '<tbody id="historyDatas111">';
						ht += '</tbody>';
						ht += '<tr><td colspan="8">查询无结果</td></tr>';
						ht += '</table>';
					}
					
					$("#historyDatas").append(ht);
				}
			});

		}
	}else{
		var ht = '';
		
		ht += '<div class="title-container">';
		ht += '<div class="table-title nobor">';
		ht += '部门本月五行剑法概况历史数据';
		ht += '</div>';
		ht += '</div>';
		ht += '<table class="table">';
		ht += '<thead>';
		ht += '<tr>';
		ht += '<th class="">时间</th>';
		ht += '<th>本月目标（万）</th>';
		ht += '<th>本月业绩额（万）</th>';
		ht += '<th>本月完成度</th>';
		ht += '<th class="wid18">核心商品成交金额数（万）</th>';
		ht += '<th class="wid18">已合作客户数</th>';
		ht += '<th>本月通话时长</th>';
		ht += '<th>本月BD新客数</th>';
		ht += '</tr>';
		ht += '</thead>';
		ht += '<tbody id="historyDatas">';
		ht += '</tbody>';
		ht += '<tr><td colspan="8">查询无结果</td></tr>';
		ht += '</table>';
		
		$("#historyDatas").html(ht);
	}
});









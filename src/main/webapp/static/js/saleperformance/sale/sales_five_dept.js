$(function() 
{
	var userId = $("#others_userId_id").val();
	var companyId = $("#companyId").val();
	// 隐藏
	$("#five_sale_span_button").hide();
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/queryDeptSoreAndSort.do",
		data : 
		{
			'userId' : userId,
			'companyId' : companyId
		},
		dataType : 'json',
		success : function(data) {
			if(0 == data.code){
				// 展示五行按钮
				$("#five_sale_span_button").show();
				
				var dataRes = data.data;
				// 如果结果不为空
				var emptyFlag = false;
				// 如果list为空
				if(null == dataRes || dataRes.length == 0){
					emptyFlag = true;
				}
				
				var ht = '';
				// 如果各个团队都没有数据
				if(emptyFlag){		
					ht += '<div class="title-container">';
					ht += '<div class="table-title nobor">部门本月概况</div>';
					ht += '</div>';
					ht += '<table class="table">';
					ht += '<thead>';
					ht += '<tr>';
					ht += '<th class="">总排名</th>';		
					ht += '<th class="">人员</th>';
					ht += '<th class="">业绩得分</th>';
					ht += '<th class="">业绩排名</th>';
					ht += '<th class="">核心商品得分</th>';
					ht += '<th class="">核心商品排名</th>';
					ht += '<th class="">客户得分</th>';
					ht += '<th class="">客户排名</th>';
					ht += '<th class="">通话得分</th>';
					ht += '<th class="">通话排名</th>';
					ht += '<th class="">BD新客数</th>';
					ht += '<th class="">BD新客数排名</th>';
					ht += '<th class="">总得分</th>';
					ht += '<th class="">昨天总排名</th>	';				
					ht += '<th class="">上月总排名</th>	';				
					ht += '</tr>';
					ht += '</thead>';
					ht += '<tbody>';
					ht += '<tr><td colspan="15">暂无数据</td></tr>';
					ht += '</tbody>';
					ht += '</table>';
				}
				
				else {
					
					// 遍历各个部门
					for(var j = 0; j < dataRes.length; j++){
						ht += '<div class="title-container">';
						ht += '<div class="table-title nobor">'+ dataRes[j].groupName +'</div>';
						ht += '</div>';
						ht += '<table class="table">';
						ht += '<thead>';
						ht += '<tr>';
						ht += '<th class="">总排名</th>';	
						ht += '<th class="">人员</th>';
						ht += '<th class="">业绩得分</th>';
						ht += '<th class="">业绩排名</th>';
						ht += '<th class="">核心商品得分</th>';
						ht += '<th class="">核心商品排名</th>';
						ht += '<th class="">客户得分</th>';
						ht += '<th class="">客户排名</th>';
						ht += '<th class="">通话得分</th>';
						ht += '<th class="">通话排名</th>';
						ht += '<th class="">BD新客数</th>';
						ht += '<th class="">BD新客数排名</th>';
						ht += '<th class="">总得分</th>';
						ht += '<th class="">昨天总排名</th>	';				
						ht += '<th class="">上月总排名</th>	';				
						ht += '</tr>';
						ht += '</thead>';
						ht += '<tbody>';
						// 部门人员
						var list = dataRes[j].resultList;
						
						// 如果list为空
						if(null == list || list.length == 0){
							ht += '<tr><td colspan="15">暂无数据</td></tr>';
							ht += '</tbody>';
							ht += '</table>';
						}
						// 如果团队业绩信息不为空
						else{
							for(var i = 0; i < list.length; i++){
								var mod = list[i];
								if(null == mod || undefined == mod)
									continue;
								if(userId == mod.userId){
									ht += '<tr style="background-color:#ceeace;" >';
								}else{
									ht += '<tr>';
								}
								var perScore = mod.score;
								if(null == perScore || 'null' == perScore){
									perScore = 0;
								}
								var perSort = mod.sort;
								if(null == perSort || 'null' == perSort){
									perSort = 0;
								}
								var brandScore = mod.brandScore;
								if(null == brandScore || 'null' == brandScore){
									brandScore = 0;
								}
								var brandSort = mod.brandSort;
								if(null == brandSort || 'null' == brandSort){
									brandSort = 0;
								}
								var customerScore = mod.customerScore;
								if(null == customerScore || 'null' == customerScore){
									customerScore = 0;
								}
								var customerSort = mod.customerSort;
								if(null == customerSort || 'null' == customerSort){
									customerSort = 0;
								}
								var commScore = mod.commScore;
								if(null == commScore || 'null' == commScore){
									commScore = 0;
								}
								var commSort = mod.commSort;
								if(null == commSort || 'null' == commSort){
									commSort = 0;
								}
								// var bussinessChanceRateScore = mod.bussinessChanceRateScore;
								// if(null == bussinessChanceRateScore || 'null' == bussinessChanceRateScore){
								// 	bussinessChanceRateScore = 0;
								// }
								// var bussinessChanceRateSort = mod.bussinessChanceRateSort;
								// if(null == bussinessChanceRateSort || 'null' == bussinessChanceRateSort){
								// 	bussinessChanceRateSort = 0;
								// }

								var bdScore = mod.bdScore;
								if(null == bdScore || 'null' == bdScore){
									bdScore = 0;
								}
								var bdSort = mod.bdSort;
								if(null == bdSort || 'null' == bdSort){
									bdSort = 0;
								}


								var totalScore = mod.totalScore;
								if(null == totalScore || 'null' == totalScore){
									totalScore = 0;
								}
								var totalSort = mod.totalSort;
								if(null == totalSort || 'null' == totalSort){
									totalSort = 0;
								}
								var yesterDayTotalSort = mod.yesterDayTotalSort;
								if(null == yesterDayTotalSort || 'null' == yesterDayTotalSort){
									yesterDayTotalSort = 0;
								}
								var preMonthLastDayTotalSort = mod.preMonthLastDayTotalSort;
								if(null == preMonthLastDayTotalSort || 'null' == preMonthLastDayTotalSort){
									preMonthLastDayTotalSort = 0;
								}
								ht += '<td>' + totalSort + '</td>';
								ht += '<td>' + mod.username + '</td>';
								ht += '<td>' + perScore + '</td>';
								ht += '<td>' + perSort + '</td>';
								ht += '<td>' + brandScore + '</td>';
								ht += '<td>' + brandSort + '</td>';
								ht += '<td>' + customerScore + '</td>';
								ht += '<td>' + customerSort + '</td>';
								ht += '<td>' + commScore + '</td>';
								ht += '<td>' + commSort + '</td>';
								ht += '<td>' + bdScore + '</td>';
								ht += '<td>' + bdSort + '</td>';
								ht += '<td>' + totalScore + '</td>';
								ht += '<td>' + yesterDayTotalSort + '</td>';
								ht += '<td>' + preMonthLastDayTotalSort + '</td>';
								ht += '</tr>';
							}
							ht += '</tbody>';
							ht += '</table>';
						}
					}
				}
				
				$("#five_sale_div_show").html(ht);
			}
			// 不参与销售五行
			else if(2 == data.code){
				// 隐藏
				$("#five_sale_div_show").hide();
			}
		},
		error : function(data) {
			if (data.status == 1001) {
				layer.alert("查询部门本月概况，暂无操作权限，请设置.");
			}
		}
	});
	
});
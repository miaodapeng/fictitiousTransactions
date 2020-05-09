$(function() 
{
	var userId = $("#others_userId_id").val();
	var companyId = $("#companyId").val();
	
	$.ajax(
	{
		type : "POST",
		url : page_url + "/sales/fiveSales/monthlySceneDetails.do",
		data : 
		{
			'userId' : userId,
			'totalFlag' : 1,
			'sortType' : 6,
			'companyId' : companyId
		},
		dataType : 'json',
		success : function(data) 
		{
			if (data.code == 0) 
			{
				var mod = data.data[0];
				var score = mod.score;
				var sort = mod.sort;
				
				score = null == score || "null" == score ? 0 : score;
				sort = null == sort || "null" == sort ? 0 : sort;
				$("#total_score_five").text(score);
				$("#total_sort_five").text(sort);
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
	
});
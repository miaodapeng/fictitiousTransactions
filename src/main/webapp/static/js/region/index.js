$(function(){
	$("select[name='province']").change(function(){
		checkLogin();
		var regionId = $(this).val();
		if(regionId > 0){
			$.ajax({
				type : "POST",
				url : page_url+"/system/region/getregion.do",
				data :{'regionId':regionId},
				dataType : 'json',
				success : function(data) {
					$option = "<option value='0'>请选择</option>";
					$.each(data.listData,function(i,n){
						$option += "<option value='"+data.listData[i]['regionId']+"'>"+data.listData[i]['regionName']+"</option>";
					});
					$("select[name='city'] option:gt(0)").remove();
					$("select[name='zone'] option:gt(0)").remove();
                    $("#zone").val("0").trigger("change");
                    $("#city").val("0").trigger("change");

					$("select[name='city']").html($option);
					$("select[name='zone']").html("<option value='0'>请选择</option>");
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}else if(regionId==0){
			$("select[name='city'] option:gt(0)").remove();
			$("select[name='zone'] option:gt(0)").remove();
		}
	});
	
	$("select[name='city']").change(function(){
		checkLogin();
		var regionId = $(this).val();
		if(regionId > 0){
			$.ajax({
				type : "POST",
				url : page_url+"/system/region/getregion.do",
				data :{'regionId':regionId},
				dataType : 'json',
				success : function(data) {
					$option = "<option value='0'>请选择</option>";
					$.each(data.listData,function(i,n){
						$option += "<option value='"+data.listData[i]['regionId']+"'>"+data.listData[i]['regionName']+"</option>";
					});
					$("select[name='zone'] option:gt(0)").remove();

					$("select[name='zone']").html($option);
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
	});
});

/**
 * 当页面存在多个地区时修改省市区
 * @param selectIdPrefix  [select的id前缀][province/city/zone]
 * @param regionType      类型[1--省/ 2--市/ 3--区]
 * @returns
 */
function changeArea(selectIdPrefix, areaId, regionType)
{
	console.log("select的id前缀: " + selectIdPrefix + ", regionType: " + regionType);
	
	var regionId = 0;
	
	var nowSelectId = selectIdPrefix;
	// 重置地区最小地址ID
	$("#" + nowSelectId).val("0");
	
	// 地区
	var area = $("#" + areaId).val();
	
	// 改变省
	if(1 == regionType)
	{
		regionId = $("#"+selectIdPrefix +"-province").val();
		
		var province = $("#"+selectIdPrefix +"-province").find("option:selected").text();
		// 设置省
		$("#" + areaId).val(province);
		
		// 随之改变市
		selectIdPrefix += '-city';
		// 清空
		$("#" + nowSelectId + "-zone option:gt(0)").remove();
	}
	// 改变市
	else if(2 == regionType)
	{
		regionId = $("#"+selectIdPrefix +"-city").val();
		var city = $("#"+selectIdPrefix +"-city").find("option:selected").text();
		// 设置市
		$("#" + areaId).val(area + " " + city);
		// 随之改变区
		selectIdPrefix += '-zone';
	}
	// 改变区
	else if(3 == regionType)
	{
		regionId = $("#"+selectIdPrefix +"-zone").val();
		
		var zone = $("#"+selectIdPrefix +"-zone").find("option:selected").text();
		// 设置区
		$("#" + areaId).val(area + " " + zone);
		
		// 随之改变区的值
		$("#" + nowSelectId).val(regionId);
	}
	
	if(regionId > 0 && (1 == regionType || 2 == regionType))
	{
		$.ajax
		({
			type : "POST",
			url : page_url+"/system/region/getregion.do",
			data :{'regionId':regionId},
			dataType : 'json',
			success : function(data) {
				$option = "<option value='0'>请选择</option>";
				$.each(data.listData,function(i,n){
					$option += "<option value='"+data.listData[i]['regionId']+"'>"+data.listData[i]['regionName']+"</option>";
				});
				$("#" + selectIdPrefix).empty();
				$("#" + selectIdPrefix).html($option);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限");
				}
			}
		});
	}
	
}

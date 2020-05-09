$(function(){
	$("#close-layer").click(function(){
		 return false;
	});
	$("#disableForm").submit(function(){
		
		var dr=$("#enableComment").val();
		if(dr==""){
			warnTips("enableComment","禁用原因不允许为空");
			return false;
		}else if(dr.length>200){
			warnTips("enableComment","禁用原因不能超过200个字符");
			return false;
		}
		$.ajax({
			url:page_url+'/warehouse/warehouses/upDisableWarehouse.do',
			data:$('#disableForm').serialize(),
			type:"POST",
			dataType : "json",
			async: false ,
			success:function(data)
			{
				parent.location.reload();
			},
			error: function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}else
				layer.alert("操作失败")
				}
		});
		return false;
	})
	$("#start").click(function() {
		var wareHouseId = $("#ware_HouseId").val();
		var isEnable = $("#is_Enable").val();
		$.ajax({
			url : page_url + '/warehouse/warehouses/upDisableWarehouse.do',
			data : {
				"warehouseId" : wareHouseId,
				"isEnable" : isEnable
			},
			type : "POST",
			async: false ,
			dataType : "json",
			success : function(data) {
				location.reload();
			},
			error: function(){if(data.status ==1001){
				layer.alert("当前操作无权限")
			}else
			layer.alert("操作失败")}
		});
	});
})


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
			url:page_url+'/warehouse/storageRoomMag/upDisableStorageRoom.do',
			data:$('#disableForm').serialize(),
			type:"POST",
			dataType : "json",
			success:function(data)
			{
				parent.location.reload();
			},
			error: function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}else
				layer.alert("操作失败")}
		});
		return false;
	})
	$("#start").click(function() {
		if($("#wareIsEnable").val()==0){
			layer.alert("上级单位已被禁用 ，无法启用该单位！");
			return false;
		}
		var storageroomId = $("#storage_roomId").val();
		var isEnable = $("#is_Enable").val();
		$.ajax({
			url : page_url + '/warehouse/storageRoomMag/upDisableStorageRoom.do',
			data : {
				"storageroomId" : storageroomId,
				"isEnable" : isEnable
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				location.reload();
			},
			error: function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}else
				layer.alert("操作失败")}
		});
	});
})


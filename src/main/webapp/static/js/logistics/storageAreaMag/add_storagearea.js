$(function(){
	checkLogin();
	$("#warehouseId").change(function(){
		var wareHouseId=$("#warehouseId").val();
		var storageroomId = $("#storageRoomId").val();
		$.ajax({
			type: "POST",
			url: page_url+"/warehouse/warehouses/getWarehouseByWId.do",
			data: {'warehouseId':wareHouseId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#storageRoomId").empty();
					var storagerooms=data.data;
					var option = "<option value='0'>全部</option>";
					for(var i=0;i<storagerooms.length;i++){
						if(storageroomId !=""&&storageroomId==storagerooms[i].storageroomId){
							option +="<option value='"+storagerooms[i].storageroomId+"' selected='selected'>"+storagerooms[i].storageRoomName+"</option>";
						}else{
							option +="<option value='"+storagerooms[i].storageroomId+"'>"+storagerooms[i].storageRoomName+"</option>";
						}
					}
					$("#storageRoomId").append(option);
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	})
	
	$("#addStorageareaForm").submit(function() {
		checkLogin();
		var sb =true;
		var storageAreaName = $("#storageAreaName").val();
		if (storageAreaName.length == 0) {
			warnTips("storageAreaName","货区名称不以允许为空");
			return false;
		}
		if (storageAreaName.length<1 || storageAreaName.length >16) {
			warnTips("storageAreaName","货区名称为1-16个汉字长度");
			return false;
		}
		var storageAreaNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
		if(!storageAreaNameReg.test(storageAreaName)){
			warnTips("storageAreaName","货区名称不允许使用特殊字符");
			return false;
		}
		var warehouseId = $("#warehouseId").val();
		if (warehouseId == 0) {
			var id =  "storageRoomId";
			var txt = "所属库房不允许为空";
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+id).siblings('.warning').remove();
			$("#"+id).after('<div class="warning">'+txt+'</div>');
			$("#warehouseId").focus();
			$("#warehouseId").addClass("errorbor");
			return false;
		}
		var storageroomId = $("#storageRoomId").val();
		if (storageroomId == 0) {
			var id =  "storageRoomId";
			var txt = "所属库房需选择最小级";
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+id).siblings('.warning').remove();
			$("#"+id).after('<div class="warning">'+txt+'</div>');
			$("#"+id).focus();
			$("#"+id).addClass("errorbor");
			return false;
		}
		var comments = $("#comments").val();
		if (comments.length >128) {
			warnTips("comments","货区备注不允许超过128个字符");
			return false;
		}
		
		//货区是否存在
		$.ajax({
			type : "POST",
			url : page_url+"/warehouse/storageAreaMag/getStorageAreaByName.do",
			data : {"storageAreaName":storageAreaName,"storageRoomId":storageroomId},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.code == 0){
					//库房已经存在
					if(data.data.storageAreaId != null 
							&& data.data.storageAreaId !=undefined
							&& data.data.storageAreaId >0 
							&& data.data.storageAreaId != $("input[name='storageArea.storageAreaId']").val()){
						sb = false;
						return false;
					}
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		if(!sb){
			warnTips("storageAreaName","同一库房下货区名称不允许重复");
			return false;
		}
		return sb;
	});
})

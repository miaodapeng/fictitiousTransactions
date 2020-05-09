$(function(){
	
	$("#wareHouseId").change(function(){
		var wareHouseId=$("#wareHouseId").val();
		var storageroomId = $("#storageroomId").val();
		$.ajax({
			type: "POST",
			url: page_url+"/warehouse/warehouses/getWarehouseByWId.do",
			data: {'warehouseId':wareHouseId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#storageroomId").empty();
					$("#storageAreaId").empty();
					var storagerooms=data.data;
					var option = "<option value='0'>全部</option>";
					for(var i=0;i<storagerooms.length;i++){
						if(storageroomId !=""&&storageroomId==storagerooms[i].storageroomId){
							option +="<option value='"+storagerooms[i].storageroomId+"' selected='selected'>"+storagerooms[i].storageRoomName+"</option>";
						}else{
							option +="<option value='"+storagerooms[i].storageroomId+"'>"+storagerooms[i].storageRoomName+"</option>";
						}
					}
					$("#storageroomId").append(option);
					$("#storageAreaId").append("<option value='0'>全部</option>");
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
	
	$("#storageroomId").change(function(){
		var storageroomId=$("#storageroomId").val();
		var storageareaId = $("#storageAreaId").val();
		$.ajax({
			type: "POST",
			url: page_url+"/warehouse/storageRoomMag/getStorageroomBySId.do",
			data: {'storageroomId':storageroomId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#storageAreaId").empty();
					var storageareas=data.data;
					var option = "<option value='0'>全部</option>";
					for(var i=0;i<storageareas.length;i++){
						if(storageareaId !=""&&storageareaId==storageareas[i].storageareaId){
							option +="<option value='"+storageareas[i].storageAreaId+"' selected='selected'>"+storageareas[i].storageAreaName+"</option>";
						}else{
							option +="<option value='"+storageareas[i].storageAreaId+"'>"+storageareas[i].storageAreaName+"</option>";
						}
					}
					$("#storageAreaId").append(option);
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
	
	$("#addStoragerackForm").submit(function() {
		checkLogin();
		var sb =true;
		var storageRackName = $("#storageRackName").val();
		if (storageRackName.length == 0) {
			warnTips("storageRackName","货架名称不允许为空");
			return false;
		}
		if (storageRackName.length<1 || storageRackName.length >16) {
			warnTips("storageRackName","货架名称为1-16个汉字长度");
			return false;
		}
		var storageRackNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
		if(!storageRackNameReg.test(storageRackName)){
			warnTips("storageRackName","货架名称不允许使用特殊字符");
			return false;
		}
		var wareHouseId = $("#wareHouseId").val();
		if (wareHouseId == 0) {
			var id="storageAreaId";
			var txt="所属货区不允许为空";
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+id).siblings('.warning').remove();
			$("#"+id).after('<div class="warning">'+txt+'</div>');
			$("#wareHouseId").focus();
			$("#wareHouseId").addClass("errorbor");
			return false;
		}
		var storageroomId = $("#storageroomId").val();
		if (storageroomId == 0) {
			var id="storageAreaId";
			var txt="所属货区需选择最小级";
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+id).siblings('.warning').remove();
			$("#"+id).after('<div class="warning">'+txt+'</div>');
			$("#storageroomId").focus();
			$("#storageroomId").addClass("errorbor");
			return false;
		}
		var storageAreaId = $("#storageAreaId").val();
		if (storageAreaId == 0) {
			var id="storageAreaId";
			var txt="所属货区需选择最小级";
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
			warnTips("comments","货架备注不允许超过128个字符");
			return false;
		}
		//货架是否存在
		$.ajax({
			type : "POST",
			url : page_url+"/warehouse/storageRackMag/getStorageRackByName.do",
			data : {"storageRackName":storageRackName,"storageAreaId":storageAreaId},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.code == 0){
					//货架已经存在
					if(data.data.storageRackId != null 
							&& data.data.storageRackId !=undefined
							&& data.data.storageRackId >0 
							&& data.data.storageRackId != $("input[name='storageRack.storageRackId']").val()){
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
			warnTips("storageRackName","同一货区下货架名称不允许重复");
			return false;
		}
		return sb;
	});
})

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
					var option = "<option value='0'>请选择库房</option>";
					for(var i=0;i<storagerooms.length;i++){
						if(storageroomId !=""&&storageroomId==storagerooms[i].storageroomId){
							option +="<option value='"+storagerooms[i].storageroomId+"' selected='selected'>"+storagerooms[i].storageRoomName+"</option>";
						}else{
							option +="<option value='"+storagerooms[i].storageroomId+"'>"+storagerooms[i].storageRoomName+"</option>";
						}
					}
					$("#storageroomId").append(option);
					$("#storageAreaId").append("<option value='0'>请选择货区</option>");
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
					var option = "<option value='0'>请选择货区</option>";
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
	
	$("#storageAreaId").change(function(){
		var storageAreaId=$("#storageAreaId").val();
		var storageRackId = $("#storageRackId").val();
		$.ajax({
			type: "POST",
			url: page_url+"/warehouse/storageAreaMag/getStorageRackByAId.do",
			data: {'storageAreaId':storageAreaId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#storageRackId").empty();
					var storageRacks=data.data;
					var option = "<option value='0'>请选择货架</option>";
					for(var i=0;i<storageRacks.length;i++){
						if(storageRackId !=""&&storageRackId==storageRacks[i].storageRackId){
							option +="<option value='"+storageRacks[i].storageRackId+"' selected='selected'>"+storageRacks[i].storageRackName+"</option>";
						}else{
							option +="<option value='"+storageRacks[i].storageRackId+"' >"+storageRacks[i].storageRackName+"</option>";
						}
					}
					$("#storageRackId").append(option);
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
	
	$("#addStoragelocationForm").submit(function() {
		checkLogin();
		var sb =true;
		var storageLocationName = $("#storageLocationName").val();
		if (storageLocationName.length == 0) {
			warnTips("storageLocationName","库位名称不允许为空");
			return false;
		}
		if (storageLocationName.length<1 || storageLocationName.length >16) {
			warnTips("storageLocationName","库位名称为1-16个汉字长度");
			return false;
		}
		var storageLocationNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
		if(!storageLocationNameReg.test(storageLocationName)){
			warnTips("storageLocationName","库位名称不允许使用特殊字符");
			return false;
		}
		var wareHouseId = $("#wareHouseId").val();
		if (wareHouseId == 0) {
			var id="storageRackId";
			var txt="所属货架不允许为空";
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
			var id="storageRackId";
			var txt="所属货架需选择最小级";
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
			var id="storageRackId";
			var txt="所属货架需选择最小级";
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+id).siblings('.warning').remove();
			$("#"+id).after('<div class="warning">'+txt+'</div>');
			$("#storageAreaId").focus();
			$("#storageAreaId").addClass("errorbor");
			return false;
		}
		var storageRackId = $("#storageRackId").val();
		if (storageRackId == 0) {
			var id="storageRackId";
			var txt="所属货架需选择最小级";
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
			warnTips("comments","库位备注不允许超过128个字符");
			return false;
		}
		//货架是否存在
		$.ajax({
			type : "POST",
			url : page_url+"/warehouse/storageLocationMag/getStorageLocationByName.do",
			data : {"storageLocationName":storageLocationName,"storageRackId":storageRackId},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.code == 0){
					//货架已经存在
					if(data.data.storageLocationId != null 
							&& data.data.storageLocationId !=undefined
							&& data.data.storageLocationId >0 
							&& data.data.storageLocationId != $("input[name='storageLocationInfo.storageLocationId']").val()){
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
			warnTips("storageLocationName","同一货架下库位名称不允许重复");
			return false;
		}
		return sb;
	});
})

$(function(){
	
	$("#addWarehouseForm").submit(function() {
		checkLogin();
		var isTrue = $('input:radio:checked').val();
		//查询临时仓库是否存在
		var is="-1";
		if(isTrue=="1"){
			$.ajax({
				type : "POST",
				url : page_url+"/warehouse/warehouses/getLsWarehouse.do",
				data : '',
				dataType : 'json',
				async : false,
				success : function(data) {
					if(data.code == 0){
						if(data.data.length>0){
							is=1;
						}
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
		//临时仓库已经存在
		if(is==1) {
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#no").after('<div class="warning">临时库已经存在！</div>');
			return false;
		}
		var sb =true;
		var warehouseName = $("#warehouseName").val();
		if (warehouseName.length == 0) {
			warnTips("warehouseName","仓库名称不能为空");
			return false;
		}
		if (warehouseName.length<1 || warehouseName.length >16) {
			warnTips("warehouseName","仓库名称为1-16个汉字长度");
			return false;
		}
		var warehouseNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
		if(!warehouseNameReg.test(warehouseName)){
			warnTips("warehouseName","仓库名称不允许使用特殊字符");
			return false;
		}
		//仓库是否存在
		$.ajax({
			type : "POST",
			url : page_url+"/warehouse/warehouses/getWarehouseByName.do",
			data : {warehouseName:warehouseName},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.code == 0){
					//仓库已经存在
					if(data.data.warehouseId != null 
							&& data.data.warehouseId !=undefined
							&& data.data.warehouseId >0 
							&& data.data.warehouseId != $("input[name='warehouses.warehouseId']").val()){
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
			warnTips("warehouseName","同一公司下仓库名称不允许重复");
			return false;
		}
		if($("select[name='province']").val() == 0){
			var id ="zone";
			var txt="仓库所在地不允许为空";
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+id).siblings('.warning').remove();
			$("#"+id).after('<div class="warning">'+txt+'</div>');
			$("#province").focus();
			$("#province").addClass("errorbor");
			return false;
		}
		if($("select[name='city']").val() == 0 ){
			var id ="zone";
			var txt="仓库地区需选择最小级";
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+id).siblings('.warning').remove();
			$("#"+id).after('<div class="warning">'+txt+'</div>');
			$("#city").focus();
			$("#city").addClass("errorbor");
			return false;
		}
		if(($("select[name='zone']").val() == 0 && $("select[name='zone'] option").length > 1)){
			var id ="zone";
			var txt="仓库地区需选择最小级";
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+id).siblings('.warning').remove();
			$("#"+id).after('<div class="warning">'+txt+'</div>');
			$("#zone").focus();
			$("#zone").addClass("errorbor");
			return false;
		}
		var wareAddress = $("#wareAddress").val();
		if (wareAddress.length == 0) {
			warnTips("wareAddress","仓库地址不允许为空");
			return false;
		}
		if (wareAddress.length<1 || wareAddress.length >128) {
			warnTips("wareAddress","仓库地址不允许超过128个字符");
			return false;
		}
		var comments = $("#comments").val();
		if ( comments.length >128) {
			warnTips("comments","仓库备注不允许超过128个字符");
			return false;
		}
		return sb;
	});
})

function search() {
	$("#search").submit();
}
//打印出货单
function printInOrder(id){
	checkLogin();
	var wlogIds = "";
	$.each($("input[name='"+id+"']:checked"),function(){
		wlogIds += $(this).val()+"_";
	});
	if(wlogIds == ""){
		layer.alert('请选择需要打印的拣货记录');
		return false;
	}
	$("#wdlIds").val(wlogIds);
	$("#searchc").submit();
}
//批量验收
function checkPass() {
	checkLogin();
	var wlogIds = "";
	$.each($("input[name='warehouseGoodsOperateLogId']:checked"), function() {
		wlogIds += $(this).val() + "_";
	});
	if (wlogIds == "") {
		layer.alert('请选择需要验收的入库记录');
		return false;
	}

	layer.confirm('确定批量验收？', function(index) {
		$.ajax({
			async : false,
			url : page_url + '/logistics/warehousein/editCheckStatusWlog.do',
			data : {
				"wlogIds" : wlogIds,
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					layer.alert("验收成功", {
						icon : 1
					}, function(index) {
						location.reload();
					});
				} else {
					layer.alert(data.message, {
						icon : 2
					});
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});

}
/*function canelAll(obj) {
	var status = 0;
	$.each($("input[name='warehouseGoodsOperateLogId']"), function() {
		var datestatus = 0;
		if ($(this).is(":checked") == false) {
			status = 1;
		}
		// 日期选择
		var alt = $(this).attr("alt");
		$.each($("input[alt='" + alt + "']"), function() {
			if ($(this).is(":checked") == false) {
				datestatus = 1;
			}
		})
		if (datestatus == 1) {
			$("input[name='" + alt + "']").prop('checked', false);
		} else {
			$("input[name='" + alt + "']").prop("checked", true);
		}

	});
	if (status == 1) {
		$("input[name='allcheck']").prop('checked', false);
	} else {
		$("input[name='allcheck']").prop("checked", true);
	}

}
// 全选
function selectall(obj) {
	if ($(obj).is(":checked")) {
		$("input[type='checkbox']").not(':disabled').prop("checked", true);
	} else {
		$("input[type='checkbox']").not(':disabled').prop('checked', false);
	}
}
// 根据日期选择
function checkbarcode(m, f) {
	if (!m)
		return false;
	$("input[name='warehouseGoodsOperateLogId']").each(function() {
		if ($(this).attr("alt") == m) {
			$(this).prop('checked', f);
		}
	});
}*/

// 单个撤销
function cancelHh(wlogIds,type) {
	checkLogin();
	if (wlogIds > 0) {
		layer.confirm('确定撤销？', function(index) {
			$.ajax({
				async : false,
				url : page_url + '/logistics/warehousein/editIsEnableWlog.do',
				data : {
					"wlogIds" : wlogIds,
					ywType : type
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.code == 0) {
						layer.alert("撤销成功", {
							icon : 1
						}, function(index) {
							location.reload();
						});
					} else {
						layer.alert(data.message, {
							icon : 2
						});
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		})
	}
}
// 批量撤销
function cancelHhAll(type) {
	checkLogin();
	var wlogIds = "";
	$.each($("input[name='warehouseGoodsOperateLogId']:checked"), function() {
		wlogIds += $(this).val() + "_";
	});
	if (wlogIds == "") {
		layer.alert('请选择需要撤销的入库记录');
		return false;
	}

	layer.confirm('确定批量撤销？', function(index) {
		$.ajax({
			async : false,
			url : page_url + '/logistics/warehousein/editIsEnableWlog.do',
			data : {
				"wlogIds" : wlogIds,
				ywType : type
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					layer.alert("撤销成功", {
						icon : 1
					}, function(index) {
						location.reload();
					});
				} else {
					layer.alert(data.message, {
						icon : 2
					});
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});

}
//批量生成售后条码并导出
function exportShBatchBarcode(orderId,bussnissType){
	checkLogin();
	location.href = page_url + '/report/logistics/exportShBatchBarcode.do?orderId='+orderId+"&bussnissType="+bussnissType;
}
//批量导入入库
function batchWarehouseIn(orderId){
	checkLogin();
	location.href = page_url + '/report/logistics/batchWarehouseIn.do?orderId='+orderId+"&bussnissType=2";
}

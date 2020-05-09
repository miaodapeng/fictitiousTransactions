function search() {
	checkLogin();
	$("#search").submit();
}
var startTime = "",endTime = "";
$(function(){
	startTime = $("#de_startTime").val();
	endTime = $("#de_endTime").val();
})
function resetPage(){
	checkLogin();
	reset();
	$("#_startTime").val(startTime);
	$("#_endTime").val(endTime);
}

// 打印入库单
function printInOrderById(id,type, formId)
{
	checkLogin();
	var wlogIds = "";
	$.each($("input[name='"+id+"']:checked"),function(){
		wlogIds += $(this).val()+"_";
	});
	if(wlogIds == ""){
		layer.alert('请选择需要打印的拣货记录');
		return false;
	}
	$("#in_wdlIds").val(wlogIds);
	$("#in_type_f").val(type);
	$("#" + formId).submit();
}

//打印入库单
function printInOrder(id,type){
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
	$("#type_f").val(type);
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
				"beforeParams": wlogIds
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
function canelAll(obj,name) {
	checkLogin();
	var status = 0;
	$.each($("input[name='"+name+"']"), function() {
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
		$("input[name='allcheck_"+name+"']").prop('checked', false);
	} else {
		$("input[name='allcheck_"+name+"']").prop("checked", true);
	}

}
//全选
function selectall(obj,name) {
	checkLogin();
	if($(obj).is(":checked")){
		$("input[name='"+name+"']").not(':disabled').prop("checked",true);
		$("input[class='"+name+"']").not(':disabled').prop("checked",true);
		}else{
		$("input[name='"+name+"']").not(':disabled').prop('checked',false);
		$("input[class='"+name+"']").not(':disabled').prop("checked",false);
	}
}
function canelAllCheck(obj,name) {
	checkLogin();
	var status = 0;
	$.each($("input[name='"+name+"']"), function() {
		var datestatus = 0;
		if ($(this).is(":checked") == false) {
			status = 1;
		}
	});
	if (status == 1) {
		$("input[name='allcheck']").prop('checked', false);
	} else {
		$("input[name='allcheck']").prop("checked", true);
	}

}
//全选
function selectall(obj,name) {
	checkLogin();
	if($(obj).is(":checked")){
		$("input[name='"+name+"']").not(':disabled').prop("checked",true);
		$("input[class='"+name+"']").not(':disabled').prop("checked",true);
		}else{
		$("input[name='"+name+"']").not(':disabled').prop('checked',false);
		$("input[class='"+name+"']").not(':disabled').prop("checked",false);
	}
}
// 根据日期选择
function checkbarcode(m, f) {
	checkLogin();
	if (!m)
		return false;
	$("input[name='warehouseGoodsOperateLogId']").each(function() {
		if ($(this).attr("alt") == m) {
			$(this).prop('checked', f);
		}
	});
}

// 单个撤销
function cancelWlog(wlogIds,type) {
	checkLogin();
	if (wlogIds > 0) {
		layer.confirm('确定撤销？', function(index) {
			$.ajax({
				async : false,
				url : page_url + '/logistics/warehousein/editIsEnableWlog.do',
				data : {
					"wlogIds" : wlogIds,
					ywType : type,
					"beforeParams":wlogIds
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
function cancelWlogAll(type) {
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
			}
		});
	});

}
/*******退换货入库*********/
//全选
function selectallsh(obj,name){
	if($(obj).is(":checked")){
		$("input[name='"+$(obj).val()+"']").not(':disabled').prop("checked",true);
		$("input[id='"+name+"']").not(':disabled').prop("checked",true);
		}else{
		$("input[name='"+$(obj).val()+"']").not(':disabled').prop('checked',false);
		$("input[id='"+name+"']").not(':disabled').prop('checked',false);
			}
}
//根据日期选择
function checkbarcodesh(m,f,id){
	var flag=0;
	if(!m)	return false;
	$("input[name='"+id+"']").each(function(){
		if($(this).attr("alt") == m){
			$(this).prop('checked',f);
		}
		if(!$(this).is(":checked")){
			flag=1;
		}
	});
	if(flag==1){
		$("input[name='all_"+id+"']").prop('checked',false);
	}else{
		$("input[name='all_"+id+"']").prop('checked',true);
	}
}
function canelAllsh(obj,name) {
	var status = 0;
	$.each($("input[name='"+name+"']"), function() {
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
		$("input[name='all_"+name+"']").prop('checked', false);
	} else {
		$("input[name='all_"+name+"']").prop("checked", true);
	}
}

function exportWareHouseOutList(){
	checkLogin();
	location.href = page_url + '/report/logistics/exportWareHouseOutList.do?' + $("#search").serialize();
}

function exportWareHouseInList(){
	checkLogin();
	location.href = page_url + '/report/logistics/exportWareHouseInList.do?' + $("#search").serialize();
}

function exportBatchBarcode(){
	checkLogin();
	var wlogIds = "";
	$.each($("input[name='buyorderGoodsId']:checked"), function() {
		wlogIds += $(this).val() + "_";
	});
	if (wlogIds == "") {
		layer.alert('请选择需要生产条码的商品');
		return false;
	}
	$("#exportBatchBarcode").removeAttr("onclick");
	location.href = page_url + '/report/logistics/exportBatchBarcode.do?buyorderId=' + $("#buyorderId").val()+'&buyorderGoodsIds=' + wlogIds +'&formToken=' + $("#formToken").val();
}
function contractReturnDel(attachmentId){
	checkLogin();
	layer.confirm("确认删除该条入库附件吗？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./contractReturnDel.do",
				data: {'attachmentId':attachmentId},
				dataType:'json',
				success: function(data){
					if (data.code == -1) {
						layer.alert(data.message);
					} else {
						window.location.reload();
					}
					/*
					layer.alert(data.message,{
						closeBtn: 0,
						btn: ['确定'] //按钮
					}, function(){
						window.location.reload();
					});*/
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function(){
	});
}

$(function(){
	$('.J-order-wrap').each(function(){
		var skus = {};
		$(this).find('.J-order-item').each(function(){
			var skuno = $.trim($(this).html());

			if(skus[skuno]){
				$(this).remove();
			}else{
				skus[skuno] = true;
			}
		})
	})
});
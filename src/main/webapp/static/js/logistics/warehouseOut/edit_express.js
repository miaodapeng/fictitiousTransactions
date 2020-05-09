function search() {
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
	$("#startTime").val(startTime);
	$("#endTime").val(endTime);
}
//批量改为已签收
function changeSt(){
	checkLogin();
	var expressIds = "";
	$.each($("input[name='b_checknox']:checked"),function(){
		expressIds += $(this).val()+"_";
	});
	if(expressIds == ""){
		layer.alert('请选择需要修改物流状态的记录！');
		return false;
	}
	
	layer.confirm('确定修改物流状态？', function(index){
		$.ajax({
			async : false,
			url : page_url + '/warehouse/warehousesout/editExpressStatus.do',
			data : {
				"expressIds" : expressIds,
				"beforeParams" : expressIds
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
					layer.alert("修改成功", { icon : 1},function(index){
						location.reload();
					});
				}else{
					layer.alert(data.message, { icon : 2});
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

//全选
function selectall(obj){
	checkLogin();
	if($(obj).is(":checked")){
		$("input[type='checkbox']").not(':disabled').prop("checked",true);
		}else{
		$("input[type='checkbox']").not(':disabled').prop('checked',false);
		}
}
//单选
function canelAll(obj,name) {
	checkLogin();
	var status = 0;
	$.each($("input[name='"+name+"']"), function() {
		if ($(this).is(":checked") == false) {
			status = 1;
		}
	});
	if (status == 1) {
		$("input[name='all_"+name+"']").prop('checked', false);
	} else {
		$("input[name='all_"+name+"']").prop("checked", true);
	}
}
function queryState(){
	checkLogin();
	var URL="/warehouse/warehousesout/queryState.do";
	layer.confirm('是否开始查询？查询将消耗免费次数。', function(index){
		$.ajax({
			async : false,
			url : page_url + URL,
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
						location.reload();
				}else{
					layer.alert("接口错误！", { icon : 2});
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
$(function(){
	$("input[type='checkbox']").not(':disabled').prop('checked',false);
})

/**
 * 导出快递列表
 * @returns
 */
function exportExpresslist(){
	checkLogin();
	location.href = page_url + '/report/logistics/exportexpresslist.do?' + $("#search").serialize();
}
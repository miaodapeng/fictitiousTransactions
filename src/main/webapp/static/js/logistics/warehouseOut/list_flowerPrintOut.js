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

$(function(){
	$("input[type='checkbox']").not(':disabled').prop('checked',false);
})

